## User Guide

### Using FuzzSource
FuzzSource is an [argument source](https://junit.org/junit5/docs/5.3.0/api/org/junit/jupiter/params/provider/ArgumentsSource.html)
as defined in JUnit 5. So please look at the [JUnit 5 documentation ](https://junit.org/junit5/docs/current/user-guide/)
for details about how to use argument sources.

Currently, FuzzUnit is not yet available on Maven Central. To use FuzzUnit, download the jar [here](https://gitlab.com/fuzzdbunit/fuzzdbunit/-/jobs/artifacts/master/raw/build/libs/fuzzdbunit-0.1-SNAPSHOT.jar?job=build)
and copy it in a directory belonging to the classpath of the tests.

FuzzSource takes the following parameters:

| Parameter | Status |Description |
| --- | --- | --- |
| files | Mandatory | A list of files to be used to provide values to the {@code ParameterizedTest} method. A java Enum is provided for representing the available files. |
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