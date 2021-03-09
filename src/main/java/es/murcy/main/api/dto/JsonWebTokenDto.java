package es.murcy.main.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JsonWebTokenDto {

  private String value;

  private Long timeToExpire;
}
