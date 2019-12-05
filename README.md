# b-synced Java SDK

This repository contains the open source Java SDK for [b-synced](https://b-synced.io/en/home/) RESTful API.

## Prerequisites

- Java 8 or above

## Features

- Authentication
- Manage supported API resources, including sending and retrieving GDSN messages

## Integration

### Maven

```bash
# build the JAR file; default path 'target/rest-api-sdk-1.0.0.jar'
mvn package -Dmaven.test.skip=true

# install the jar locally
mvn install:install-file -Dfile=<jar file path>/rest-api-sdk-1.0.0.jar -DgroupId=de.bcservices.bsynced -DartifactId=rest-api-sdk -Dversion=1.0.0 -Dpackaging=jar
```

Then add the following dependency to your pom.xml

```xml
    <dependency>
        <groupId>de.bctechnologies.bsynced</groupId>
        <artifactId>rest-api-sdk</artifactId>
        <version>1.0.0</version>
    </dependency>
```

## Getting Started

- [Making the first request](wiki/making-first-request.md)

## Javadoc

- Please visit the documents under [docs/](/docs).

## Reference

- [b-synced RESTful API Documentation](https://test.b-synced.io/docs/#/getting-started)

## Contact

If you are interested in b-synced data pool, please visit our official page at [https://www.b-synced.io/](https://www.b-synced.io/) or directly contact us at [support@b-synced.io](mailto:support@b-synced.io).

## License

Please check our [license file](/LICENSE.md).

## Contributions

All suggestions are welcome! Please make a pull request or create issues to discuss possible improvements and usage scenarios.
