package com.sbs.jdbc.text_board.boundedContext.common.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class BaseDto {
  protected LocalDateTime regDate;
  protected LocalDateTime updateDate;

  public String getFormatRegDate() {
    return regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
  public String getFormatUpdateDate() {
    return updateDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

}
