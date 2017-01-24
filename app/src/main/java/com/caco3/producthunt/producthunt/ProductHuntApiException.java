package com.caco3.producthunt.producthunt;


public abstract class ProductHuntApiException extends RuntimeException {
  public ProductHuntApiException() {
    super();
  }

  public ProductHuntApiException(String message) {
    super(message);
  }

  public ProductHuntApiException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProductHuntApiException(Throwable cause) {
    super(cause);
  }
}
