package com.allen.graphql.vertx.service.quote;

import com.allen.graphql.vertx.service.quote.impl.QuoteServiceImpl;
import com.allen.graphql.vertx.service.quote.model.Quote;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public interface QuoteService {

  static QuoteService create(Vertx vertx) {
    return new QuoteServiceImpl(vertx);
  }

  public QuoteService getQuote(String id, Handler<AsyncResult<Quote>> resultHandler);
}
