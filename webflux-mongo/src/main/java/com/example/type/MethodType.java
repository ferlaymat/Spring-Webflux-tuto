package com.example.type;

import java.util.Arrays;

public enum MethodType {
      TEMPLATE(0),
      REACTIVE_TEMPLATE(1),
      REPOSITORY(2);

      private final int value;

      MethodType(int value) {
          this.value = value;
      }

      public int getValue() {
          return value;
      }

      public static MethodType fromValue(int value) {
          return Arrays.stream(values())
                  .filter(m -> m.value == value)
                  .findFirst()
                  .orElseThrow(() -> new IllegalArgumentException("Method must be 0, 1 or 2"));
      }
  }