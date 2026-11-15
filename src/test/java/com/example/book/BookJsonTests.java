package com.example.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
public class BookJsonTests {
    @Autowired
    JacksonTester<Book> json;

    @Test
    void bookSerializationTest() throws IOException {
        Book book = new Book(99L, 123.00, "sarah1");
        assertThat(json.write(book)).isStrictlyEqualToJson("/single.json");
    }
}
