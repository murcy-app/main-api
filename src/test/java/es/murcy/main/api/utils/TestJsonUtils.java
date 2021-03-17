package es.murcy.main.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJsonUtils {

  public static String toJson(final Object obj) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
