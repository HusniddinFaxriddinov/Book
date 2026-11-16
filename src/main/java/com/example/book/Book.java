package com.example.book;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
@Table("BOOK_TABLE")
public record Book(@Id Long id, double price, String author) {
}
