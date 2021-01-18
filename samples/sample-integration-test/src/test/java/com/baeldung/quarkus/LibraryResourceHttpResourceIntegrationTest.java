package com.baeldung.quarkus;

import com.github.fuzzdbunit.params.provider.FuzzFile;
import com.github.fuzzdbunit.params.provider.FuzzSource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.io.IOException;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static java.nio.charset.Charset.defaultCharset;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class LibraryResourceHttpResourceIntegrationTest {

    @TestHTTPEndpoint(LibraryResource.class)
    @TestHTTPResource("book")
    URL libraryEndpoint;

    @ParameterizedTest
    @FuzzSource(file = FuzzFile.SQL_INJECTION_DETECT_GENERICBLIND)
    void whenGetBooksByTitle_thenBookShouldBeFound(String name) {
        System.out.println("Testing: "+name);
        given().contentType(ContentType.JSON).param("query", name)
                .when().get(libraryEndpoint)
                .then().statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void whenGetBooks_thenBooksShouldBeFound() throws IOException {
        assertTrue(IOUtils.toString(libraryEndpoint.openStream(), defaultCharset()).contains("Asimov"));
    }
}
