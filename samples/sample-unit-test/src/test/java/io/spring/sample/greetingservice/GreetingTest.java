package io.spring.sample.greetingservice;

import com.github.fuzzdbunit.params.provider.FuzzFile;
import com.github.fuzzdbunit.params.provider.FuzzSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.*;

class GreetingTest {

    @ParameterizedTest(name = "Fuzz testing validator")
    @FuzzSource(file = FuzzFile.XSS_XSS_URI)
    void testValidationWithFuzzUnit(String content) {
        Assertions.assertThrows(ValidationException.class, () -> {
            Greeting g = Greeting.build(10, content);
        }, "Failed with ["+content+"]");
    }

}