## User Guide

### Using FuzzSource
FuzzSource is an [argument source](https://junit.org/junit5/docs/5.3.0/api/org/junit/jupiter/params/provider/ArgumentsSource.html)
as defined in JUnit 5. So please look at the [JUnit 5 documentation ](https://junit.org/junit5/docs/current/user-guide/)
for details about how to use argument sources.

To use FuzzUnit, just add the following dependency to the pom.xml your project:

```
<dependency>
  <groupId>com.github.fuzzdbunit</groupId>
  <artifactId>fuzzdbunit</artifactId>
  <version>0.2</version>
  <scope>test</scope>
</dependency>
```

Then, annotate your test with ```@ParameterizedTest```, then add ```@FuzzSource```. The latter takes the following parameters:

| Parameter | Status |Description |
| --- | --- | --- |
| files | Mandatory | A list of files to be used to provide values to the {@code ParameterizedTest} method. A java Enum is provided for representing the available files. The first file of the array will feed the first parameter of the test function, the second file feeds the second parameter, and so on|
| paddingValues | Optional |  A list of strings for padding if the files given in the ```files``` parameter do not have the same length. The first item in the list pads the first parameter, the second pads the second and so on. Per default, the empty string "" will be used. |

Example:
```
import com.github.fuzzdbunit.params.provider.FuzzSource;
import static com.github.fuzzdbunit.params.provider.FuzzFile.*;

@ParameterizedTest(name = "Fuzz testing")
@FuzzSource(files = {BUSINESS_LOGIC_COMMONMETHODNAMES, BUSINESS_LOGIC_DEBUGPARAMS_JSON_FUZZ},
            paddingValues = { null, "" } )
void testWithFuzzUnit(String first, String second) {
	JsonApi api = new JsonApi();
	assertNotNull( api.call(first, second) );
}
```

### Finding an attack pattern

FuzzDb offers various attack patterns. The table below summarizes these patterns.

| Pattern | Link |
| ------ | ------ |
| authentication     | [authentication](./fuzzDb/attack/authentication/README.md)    |
| disclosure-source     | [disclosure-source](./fuzzDb/attack/disclosure-source/README.md)    |
| file-upload     | [file-upload](./fuzzDb/attack/file-upload/README.md)    |
| malicious-images     | [malicious-images](./fuzzDb/attack/file-upload/malicious-images/README.md)    |
| http-protocol     | [http-protocol](./fuzzDb/attack/http-protocol/README.md)   |
| ldap     | [ldap](./fuzzDb/attack/ldap/README.md)    |
| lfi     | [lfi](./fuzzDb/attack/lfi/README.md)    |
| no-sql-injection     | [no-sql-injection](./fuzzDb/attack/no-sql-injection/README.md)    |
| os-cmd-execution     | [os-cmd-execution](./fuzzDb/attack/os-cmd-execution/README.md)    |
| path-traversal     | [path-traversal](./fuzzDb/attack/path-traversal/README.md)    |
| redirect     | [redirect](./fuzzDb/attack/redirect/README.md)    |
| rfi     | [rfi](./fuzzDb/attack/rfi/README.md)    |
| sql-injection / detect     | [sql-injection / detect](./fuzzDb/attack/sql-injection/detect/README.md)    |
| sql-injection / exploit     | [sql-injection / exploit](./fuzzDb/attack/sql-injection/exploit/README.md)    |
| sql-injection / payloads-sql-blind     | [sql-injection / payloads-sql-blind](./fuzzDb/attack/sql-injection/payloads-sql-blind/README.md)    |
| unicode     | [unicode](./fuzzDb/attack/unicode/README.md)    |
| xpath     | [xpath](./fuzzDb/attack/xpath/README.md)    |
| xss     | [xss](./fuzzDb/attack/xss/README.md)    |