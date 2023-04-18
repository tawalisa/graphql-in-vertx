package com.allen.graphql.vertx.service.author;

import com.allen.graphql.vertx.service.author.impl.AuthorServiceImpl;
import com.allen.graphql.vertx.service.author.model.Author;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;


public interface AuthorService {

  static AuthorService create(Vertx vertx) {
    return new AuthorServiceImpl(vertx);
  }

  public AuthorService getAuthorByID(String id, Handler<AsyncResult<Author>> resultHandler);


}
