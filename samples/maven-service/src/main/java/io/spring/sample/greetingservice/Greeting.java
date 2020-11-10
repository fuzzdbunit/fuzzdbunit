package io.spring.sample.greetingservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

public class Greeting {

    private final long id;
    private final String content;

    private Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static Greeting build(long id, String content) throws ValidationException {
        if (!content.matches("[a-zA-Z]{0,30}")) {
            throw new ValidationException("Invalid input");
        }
        return new Greeting(id, "Hello, "+content);
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}