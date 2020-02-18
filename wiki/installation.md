# Installation

## Maven

```bash
# build the JAR file; default path 'target/rest-api-sdk-1.0.0.jar'
mvn package -Dmaven.test.skip=true

# install the jar locally
mvn install:install-file -Dfile=<jar file path>/rest-api-sdk-1.0.0.jar -DgroupId=de.bcservices.bsynced -DartifactId=rest-api-sdk -Dversion=1.0.0 -Dpackaging=jar
```

Then add the following dependency to your pom.xml

```xml
    <dependency>
        <groupId>de.bcservices.bsynced</groupId>
        <artifactId>rest-api-sdk</artifactId>
        <version>1.0.0</version>
    </dependency>
```
