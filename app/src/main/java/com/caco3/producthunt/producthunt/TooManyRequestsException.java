package com.caco3.producthunt.producthunt;

public class TooManyRequestsException extends ProductHuntApiException {
  public TooManyRequestsException(String message) {
    super(message);
  }
}
