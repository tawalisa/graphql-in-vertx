package com.allen.graphql.vertx.service.book;


import com.allen.graphql.vertx.service.book.impl.BookServiceImpl;
import com.allen.graphql.vertx.service.book.model.Book;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import java.util.List;

public interface BookService {

  static BookService create(Vertx vertx) {
    return new BookServiceImpl(vertx);
  }

  BookService getBookById(String id, Handler<AsyncResult<Book>> resultHandler);

  BookService getBooks(Handler<AsyncResult<List<Book>>> resultHandler);


}
