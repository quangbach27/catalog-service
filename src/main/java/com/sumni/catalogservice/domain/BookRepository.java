package com.sumni.catalogservice.domain.exception;

import com.sumni.catalogservice.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, String> {

}
