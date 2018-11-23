# thingml-diversifier

A model-based tool to automatically diversify communications.

The science behind this artefact is decribed in:

> B. Morin, J. Høgenes, H. Song, N. Harrand and B. Baudry.<br/>*Engineering Software Diversity: a Model-Based Approach to Systematically Diversify Communications.*<br/>**ACM/IEEE MODELS'18 Conference**, Copenhagen, Danmark.<br/>

Please cite this paper if you reuse or mention this tool or underlying approach in a scientific paper.

## 1. Specify protocols

We model communication protocols as a set of communicating state-machines, encapsulated into components. 
We use [ThingML](https://github.com/TelluIoT/ThingML) to model those protocols. 

> It is probably a good idea to read the ThingML README first.

A protocol is typically composed of

1. A structural view, defining the API to be used by the client and the server
2. A behavioral view, specifying how messages are exchanged between the client and the server

### Structural view

The structural view basically defines the messages to be exchanged between the client and the server.
This is specify like this:

```
import "datatypes.thingml" from stl

//The messages to be exchanged by the client and the server
thing fragment Msgs {
   message m1(a:Byte,b:Byte,c:Integer,
              d:Byte,e:Byte)
   message m2(a:Byte,b:Byte,c:Byte)
   message m3(a:Byte)
}
```

### Behavioral view 

The behavior view is composed of two state machines. One for the client and one for the server. The example described below is also available [here](https://github.com/SINTEF-9012/thingml-diversifier/tree/master/docs/mymodel.thingml).

A simple client:
```
thing Client includes Msgs {
    
    required port app {
        sends m1, m2
        receives m3
    }

    statechart init RUN {        
        state RUN {
            on entry do
                app!m1(0x01, 0x02, 3, 0x04, 0x05)
                app!m2(0x01, 0x02, 0x03)
            end
            
            transition -> STOP
            event e : app?m3
            guard e.a == 1
            
            transition -> ERROR
            event e : app?m3
            guard e.a != 1
        }
        
        final state STOP {
            on entry println "Success!"
        }
        
        final state ERROR {
            on entry println "Failure...!"
        }
    }
}
```

A simple server:
```
thing Server includes Msgs {
    
    property id : Byte

    provided port app {
        receives m1, m2
        sends m3
    }

    statechart init WaitForM1 {                
        state WaitForM1 {
            transition -> WaitForM2
            event e : app?m1
            action id = e.a
        }
            
        state WaitForM2 {
            transition -> SendAck
            event e : app?m2
            guard e.a == id
            
            transition -> ERROR
            event e : app?m2
            guard e.a != id
        }
            
        state SendAck {
            on entry do
                println "Success!"
                app!m3(id)
            end
                
            transition -> WaitForM1
        }
        
        state ERROR {
            on entry println "Error...!"
                
            transition -> WaitForM1
        }        
    }
}
```

Congratulations! You have implemented the following protocol:

![simpleProtocol](docs/simpleProtocol.png)

## 2. Diversify protocols

1. `cd thingml-diversifier`
2. `mvn clean install`
3. `cd target`
4. `java -jar thingml.diversifier-1.0.0-SNAPSHOT-jar-with-dependencies.jar` with the following parameters:

    - *input model* (a valid ThingML file). Mandatory.
    - *number* of diversified model to generate. Mandatory.
    - *mode*. Mandatory. *runtime* or *default*.
    - *output directory* to store diversified models. Optional. Default = current directory
    - *random seed* to generate repeatable outputs. Optional. Default = a magic random seed (likely to be different every time)
    
For example: 

`java -jar thingml.diversifier-1.0.0-SNAPSHOT-jar-with-dependencies.jar mymodel.thingml 100 runtime /tmp/thingml-diversifier 1`
    
## 3. Compile

Please have a look at the [ThingML README](https://github.com/TelluIoT/ThingML), explaining how to compiler models to different platforms: 

- C (Arduino and Linux)
- Java
- JavaScript (Node.JS and Browser)
- Go
- UML (PlantUML diagrams)

## 4. Evaluate the results

This repository contain scripts for two experiments:
1) To evaluate the diversification of the protocols, that log the actual data sent between components. [Here](src/main/bash/bytes)
2) To evaluate the impact of diversifying protocols in terms of overhead in execution time, used memory, and bytes sent. [Here](src/main/bash/summary)

> Note: The bash-scripts mentioned in this section has only been tested on a single machine with a specific setup (Git bash on Windows 10).
> So if you can't get them to work on your machine, please contact @jakhog or post an issue in this repo so we can help you.

### 4.1 Run diversification experiments
> These scripts live in the `src/main/bash/bytes` directory

First edit the `setup.sh` script to fit your needs, and make sure you have the proper environment variables set up, with a copy of the ThingML compiler, and that the Java part of this repository is built.

Then run the scripts:
1) `generate_models.sh`
2) `generate_platform_code.sh`
3) `run_generated_code.sh`

This should produce ThingML models, plaform code, binary files, and logs in the `target` directory in the root of the repo. These will be used to produce the statistics table with Jupyter Notebook later.

### 4.2 Run diversification overhead experiments
> These scripts live in the `src/main/bash/summary` directory

First edit the `setup.sh` script to fit your needs, and make sure you have the proper environment variables set up, with a copy of the ThingML compiler, and that the Java part of this repository is built.

Then run the scripts:
1) `generate_models.sh`
2) `generate_platform_code.sh`
3) `run_generated_code.sh`

This should produce ThingML models, plaform code, binary files, and logs in the `target` directory in the root of the repo. These will be used to produce the plots with Jupyter Notebook later.

### 4.3 Run Jupyter Notebooks to produce results
> The Jupyter notebooks live in the `src/main/python/` directory

#### `images.ipynb`
This script produces two images that 1) visualize the actual data that is sent between components by representing values as colors, 2) visualize the type and position of a simulated _weak_ parameter in the exchanged data. Uses the results from 4.1.

#### `graphs.ipynb`
This script produces two graphs that visualize the quantitative difference between datas sent between components. Uses the results from 4.1.

#### `stastistics.ipynb`
This script produces a table that shows the overhead of the diversification techniques, compared to no diversifications. Uses the results from 4.2.
