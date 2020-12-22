package io.spring.sample.greetingservice;

import com.github.fuzzdbunit.params.provider.FuzzFile;
import com.github.fuzzdbunit.params.provider.FuzzSource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.github.fuzzdbunit.params.provider.FuzzFile.*;

@SpringBootTest(classes = GreetingServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GreetingServiceApplicationIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGreeting_OK() {
		ResponseEntity<String> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/greeting?name=Peter", String.class);
		assertEquals(200, responseEntity.getStatusCodeValue());
	}

	@ParameterizedTest(name = "Fuzz testing")
	@FuzzSource(files = {
			SQL_INJECTION_DETECT_GENERIC_SQLI,
			SQL_INJECTION_DETECT_GENERICBLIND },
			paddingValues = { "", ""})
	void testWithFuzzUnit(String firstname, String name) {
		ResponseEntity<String> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/greeting?name=" + name, String.class);
		assertEquals(500, responseEntity.getStatusCodeValue());
	}
}