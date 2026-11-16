package com.example.book;

import com.jayway.jsonpath.DocumentContext;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
public class BookJsonTests {
    @Autowired
    JacksonTester<Book> json;

    @Autowired
    JacksonTester<Book[]> jsonList;

    private Book[] arrayBook;

    @BeforeEach
    void setUp(){
        arrayBook = Arrays.array(
                new Book(99L, 123.00, "Qodiriy"),
                new Book(100L, 1.00, "Qodiriy"),
                new Book(101L, 150.00, "Qodiriy")
        );
    }


    @Test
    void bookSerializationTest() throws IOException {
        Book book = arrayBook[0];
        assertThat(json.write(book)).isStrictlyEqualToJson("/single.json");
        assertThat(json.write(book)).hasJsonPathValue("@.id");
        assertThat(json.write(book)).extractingJsonPathValue("@.id").isEqualTo(99);
        assertThat(json.write(book)).hasJsonPathValue("@.price");
        assertThat(json.write(book)).extractingJsonPathValue("@.price").isEqualTo(123.00);
        assertThat(json.write(book)).hasJsonPathValue("@.author");
        assertThat(json.write(book)).extractingJsonPathValue("@.author").isEqualTo("Qodiriy");
    }

    @Test
    void bookDeserializationTest() throws IOException{
        String expected = """
                {
                  "id": 99,
                  "price": 123.00,
                  "author": "Qodiriy"
                }
                """;
        assertThat(json.parse(expected)).isEqualTo(arrayBook[0]);
        assertThat(json.parseObject(expected).id()).isEqualTo(99);
        assertThat(json.parseObject(expected).price()).isEqualTo(123.00);
        assertThat(json.parseObject(expected).author()).isEqualTo("Qodiriy");
    }

    // Tests for serialization and deserialization for list of books
    @Test
    void bookSerializationListTest() throws IOException{
        assertThat(jsonList.write(arrayBook)).isStrictlyEqualToJson("/list.json");
    }

    @Test
    void bookDeserializationListTest() throws IOException{
        String expected = """
                [
                  {"id": 99,"price": 123.00, "author": "Qodiriy"},
                  {"id": 100,"price": 1.00, "author": "Qodiriy"},
                  {"id": 101, "price": 150.00, "author": "Qodiriy"}
                ]
                """;
        assertThat(jsonList.parse(expected)).isEqualTo(arrayBook);
    }
}
