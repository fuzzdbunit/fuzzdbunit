## FuzzDbUnit
FuzzDbUnit is a [JUnit 5 argument source](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources)
intended to be used in parameterized tests. Its purpose is to execute fuzz testing during unit or
integration testing.

### What is JUnit
"JUnit is a unit testing framework for the Java programming language. JUnit has been important in
the development of test-driven development, and is one of a family of unit testing frameworks which
is collectively known as xUnit that originated with SUnit." *Source: Wikipedia*

FuzzDbUnit, as an argument source of JUnit, brings fuzz testing to the unit and integration phase.

### What is fuzzing?
According to Wikipedia: "Fuzzing or fuzz testing is an automated software testing technique that
involves providing invalid, unexpected, or random data as inputs to a computer program. The program
is then monitored for exceptions such as crashes, failing built-in code assertions, or potential
memory leaks. Typically, fuzzers are used to test programs that take structured inputs."

Fuzzing is also used by bad guys for finding weaknesses in software code. Therefore, every piece of
software should be fuzzed in order to find bugs before bad guys do. Usually, fuzzing is executed
by penetration testers during application security testing (DAST), that is when the application
is tested in a preprod environment. However, this is inefficient and time consuming, for several
reasons:
* bugs are found late in the SDLC
* DAST tools take time to put in place
* Fuzz testing with DAST tools is not efficient

FuzzDbUnit brings the power of fuzzing early in the development process, during the unit or integration
tests. It requires no more than JUnit, which is already in place in most software projects, and which is mastered by
developers. As such, it allows developers to take the responsability of the security of their software product. Also,
it fits perfectly to DevSecOps.

### FuzzDB: the source of fuzzing data
The fuzzing data used in FuzzUnit come from [FuzzDB](https://github.com/fuzzdb-project/fuzzdb), "the
first and most comprehensive open dictionary of fault injection patterns, predictable resource
locations, and regex for matching server responses. FuzzDB was created to increase the likelihood of
finding application security vulnerabilities through dynamic application security testing." 

FuzzDbUnit allows developers to carry on fuzz testing during the unit or integration phase, when bugs
are easier to fix. Because


### Links
* [User Guide](docs/user-guide.adoc)
* [Developer Guide](./docs/developer-guide.md)
* [Release notes](./docs/release-notes.md)
* [License](./LICENSE.md)

### External links
* [JUnit 5](https://junit.org/junit5/)
* [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
