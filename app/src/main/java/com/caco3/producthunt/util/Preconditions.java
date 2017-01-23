package com.caco3.producthunt.util;

/**
 * Created by caco3 on 1/23/17.
 */

public class Preconditions {
  private Preconditions(){
    throw new AssertionError("No instances");
  }

  /** Throws {@link NullPointerException} with detail message
   * if provided reference is null*/
  public static <T> T checkNotNull(T reference, String message) {
    if (reference == null) {
      throw new NullPointerException(message);
    } else {
      return reference;
    }
  }

  /** Throws {@link NullPointerException} if provided reference is null */
  public static <T> T checkNotNull(T reference) {
    return checkNotNull(reference, null);
  }

  /** Throws {@link IllegalArgumentException} with detail message if provided condition is false.*/
  public static void checkArgument(boolean condition, String message) {
    if (!condition) {
      throw new IllegalArgumentException(message);
    }
  }

  /** Throws {@link IllegalArgumentException} if provided condition is false. */
  public static void checkArgument(boolean condition) {
    checkArgument(condition, null);
  }

  /** Throws {@link IllegalStateException} with detail message if provided state is false */
  public static void checkState(boolean state, String message) {
    if (!state) {
      throw new IllegalStateException(message);
    }
  }

  /** Throws {@link IllegalStateException} if provided state is false */
  public static void checkState(boolean state) {
    checkState(state, null);
  }
}
