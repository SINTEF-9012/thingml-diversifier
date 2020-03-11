FROM maven:3-jdk-8-slim


ARG SHA=75e94c1368a099cbc6d6a40d6a81ed884fd3fd33

RUN apt-get update && apt-get install -y unzip && rm -rf /var/lib/apt/lists/*

#Get ThingML (at a given commit) and compile it
COPY ./settings.xml settings.xml
RUN curl -O -J -L https://github.com/TelluIoT/ThingML/archive/$SHA.zip \
    && unzip -qq ThingML-$SHA.zip -d . && rm ThingML-$SHA.zip

RUN cd ThingML-$SHA && mvn -s ../settings.xml -DskipTests clean install \
    && mkdir /thingml && mv compilers/official-network-plugins/target/*-jar-with-dependencies.jar /thingml/thingml.jar

#Add the ThingML diversifier
ADD . .
RUN rm pom.xml && mv pom-docker.xml pom.xml && mv MANIFEST-DOCKER.MF /thingml/MANIFEST-DOCKER.MF \
    && cd / && mvn -s settings.xml clean install
RUN (cd /tmp; unzip -uo /target/all-*.jar) && (cd /tmp; unzip -uo /target/*-jar-with-dependencies.jar) && jar -cvf thingml-div.jar -C /tmp . && mv thingml-div.jar /thingml/thingml-div.jar
RUN cd /thingml && jar umf MANIFEST-DOCKER.MF thingml-div.jar

FROM openjdk:8-jre-slim
RUN apt-get update && apt-get install -y cloc && rm -rf /var/lib/apt/lists/*
COPY --from=0 /thingml/thingml-div.jar thingml-div.jar
RUN chmod +x thingml-div.jar

ENTRYPOINT ["java", "-jar", "thingml-div.jar"]
CMD ["-h"]
