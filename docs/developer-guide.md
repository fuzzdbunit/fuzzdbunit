## Developer Guide

### Requirements
* JDK 11

### Building
Building the jar:
```
./gradlew jar
```

### Updating fuzz data

Updating the fuzz data from FuzzDB:
```
./gradlew generateFuzzEnum
```
Please note that an internet connection is required for downloading fuzz data from FuzzDB.

### Style Guide

The Google Style Guide is used for formatting.