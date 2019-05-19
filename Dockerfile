#Get ThingML (at a given commit) and compile it
FROM maven:3-jdk-8-slim

ARG SHA=a4948b3ec565d8bf5ae80ef7ee951f5b9b5dae92

COPY ./settings.xml settings.xml
RUN curl --silent -O -J -L https://github.com/TelluIoT/ThingML/archive/$SHA.zip \
    && unzip -qq ThingML-$SHA.zip -d . && rm ThingML-$SHA.zip

RUN cd ThingML-$SHA && mvn -q -s ../settings.xml -DskipTests clean install \
    && mkdir /thingml && mv compilers/official-network-plugins/target/*-jar-with-dependencies.jar /thingml/thingml.jar

ADD . .
RUN rm pom.xml && mv pom-docker.xml pom.xml && mv MANIFEST-DOCKER.MF /thingml/MANIFEST-DOCKER.MF \
    && cd / && mvn -s settings.xml clean install
RUN (cd /tmp; unzip -uo /target/all-*.jar) && (cd /tmp; unzip -uo /target/*-jar-with-dependencies.jar) && jar -cvf thingml-div.jar -C /tmp . && mv thingml-div.jar /thingml/thingml-div.jar
RUN cd /thingml && jar umf MANIFEST-DOCKER.MF thingml-div.jar

FROM openjdk:8-jre-slim
COPY --from=0 /thingml/thingml-div.jar thingml-div.jar
RUN chmod +x thingml-div.jar

ENTRYPOINT ["java", "-jar", "thingml-div.jar"]
CMD ["-h"]
