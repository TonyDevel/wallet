FROM adoptopenjdk/openjdk11:alpine

LABEL maintainer="elyuta.directline@gmail.com"

# to make layer thinner we use jar file, not the whole source code of project, and run gradle build inside of docker
ADD app.jar /srv/app/wallet.jar

ENTRYPOINT ["java", "-jar", "/srv/app/wallet.jar"]