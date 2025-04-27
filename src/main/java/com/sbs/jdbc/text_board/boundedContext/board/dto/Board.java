package com.sbs.jdbc.text_board.boundedContext.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Board {
  private final int id;
  private String regDate;
  private String updateDate;
  private String code;
  private String name;

  public Board(Map<String, Object> boardMap) {
    this.id = (int) boardMap.get("id");
    this.regDate = (String) boardMap.get("regDate");
    this.updateDate = (String) boardMap.get("updateDate");
    this.code = (String) boardMap.get("code");
    this.name = (String) boardMap.get("name");
  }
}
