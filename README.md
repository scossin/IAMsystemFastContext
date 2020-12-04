# IAMsystemFastContext

It combines [IAMsystem](https://github.com/scossin/IAMsystem) (dictionary look-up algorithm) and [FastContext](https://github.com/jianlins/FastContext) (context detection)

## Getting started

To build it, you will need Java 1.8 (or higher) JDK a recent version of Maven (https://maven.apache.org/download.cgi) and put the `mvn` command on your path. Now you can run `mvn clean install` to compile the project. 

then add to your pom: 

```XML
<dependency>
 	<groupId>fr.erias</groupId>
	<artifactId>IAMsystemFastContext</artifactId>
	<version>0.0.1</version>
</dependency>
```

## FastContext Rules

See the 'Example.java' for an example.  
You need a file containing FastContext rules but this repository doesn't contain any.   

English rules for FastContext are available here: https://github.com/jianlins/FastContext/blob/master/conf/context.txt


