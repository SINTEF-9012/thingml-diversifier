# thingml-diversifier

A model-based tool to automatically diversify protocols.

[![DOI](https://zenodo.org/badge/118929049.svg)](https://zenodo.org/badge/latestdoi/118929049)

The science behind this artefact is decribed in:

> B. Morin, J. Høgenes, H. Song, N. Harrand and B. Baudry.<br/>*Engineering Software Diversity: a Model-Based Approach to Systematically Diversify Communications.*<br/>**ACM/IEEE MODELS'18 Conference**, Copenhagen, Danmark.<br/>

```
@inproceedings{morin2018engineering,
  title={Engineering Software Diversity: a Model-Based Approach to Systematically Diversify Communications},
  author={Morin, Brice and H{\o}genes, Jakob and Song, Hui and Harrand, Nicolas and Baudry, Benoit},
  booktitle={Proceedings of the 21th ACM/IEEE International Conference on Model Driven Engineering Languages and Systems},
  pages={155--165},
  year={2018},
  organization={ACM}
}
```

Please cite this paper if you reuse or mention the underlying approach in a scientific paper. Please use the DOI if you reuse or mention the tool itself in a scientific paper.

## 1. Specify protocols

A complete protocol is available in [`src/main/resources/experiments1`](https://github.com/SINTEF-9012/thingml-diversifier/tree/master/src/main/resources/experiments1). We show below how to model protocols on a simpler example.

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

## 2. Diversify, compile and run protocols

The recommended way of using this tool is through Docker.

> On Linux, you may need to run the docker commands with `sudo`

1. `cd thingml-diversifier && docker build -t thingml-div` This will build a docker image containing ThingML and the ThingML-diversifier tool.
2. `docker run thingml-div` will provide you with information about how to use the ThingML-diversifier and ThingML through CLI.
3. `cd <into dir containing base-file.thingml> && docker run -v $(pwd):/thingml-div thingml-div -i /thingml-div/<base-file.thingml> -r 1 -n 10 -m dynamic -o /thingml-div/target/models -s shuff-msg -s dup-msg` will for example call the ThingML-diversifier and produce 10 dynamically-diversified protocols using the shuffle message and duplicate message operators. The diversified protocols will be available in the current directory, in sub-folder `target/models`. 
4. `docker run -v $(pwd):/thingml-div thingml-div thingml -c java -s /thingml-div/target/models/<div-file.thingml> -o /thingml-div/target/code/java` will for example generate Java code from the diversified protocol
5. `cd target/code/java/<my-config> && docker build -t java-div && docker run java-div` will build a docker image for the Java code you have just generated from the diverfied model, and run it into a docker container. You can also compile and run this code outside Docker, locally on your computer. In the case of Java: `mvn clean install exec:java`, assuming you have Maven and Java properly configured.

> For this last step to work, you need [to annotate your configuration with `@docker`](https://github.com/SINTEF-9012/thingml-diversifier/blob/master/src/main/resources/experiments1/java.thingml) 

## 3. Evaluate the results

> You need Docker and Jupyter Notebook!

We provide [a set of scripts](https://github.com/SINTEF-9012/thingml-diversifier/tree/master/src/main/bash) to run some experiments automatically with Docker:

- `00_build_thingml.sh` builds ThingML and the ThingML-diversifier into a Docker image.
- `01_diversify_models.sh` diversifies our test protocol by generating `N` versions in different modes
- `02_generate_code.sh` generates code for a number of target platforms
- `03_run_in_docker.sh` runs the generated code and produce logs, which we exploit later on

> You may want to configure `setup.sh` before running an experiment. The more protocols you generate (`N`), the more langauges you target (`LANGUGES`) and the more modes you apply (`MODES`), the longer it will take. It is probably a good idea to start with `N=3` :-)

Two Jupyter Notebooks analyze the genrated logs to provide [some insight about our approach](https://github.com/SINTEF-9012/thingml-diversifier/tree/master/src/main/python/experiment1):

- `performances.ipynb`: how much time and memory did different versions of the protocol use
- `tensorflow.ipynb`: uses TensorFlow (you need to install TensorFlow 1.x) to learn about each individual protocol and make predictions about all protocols
