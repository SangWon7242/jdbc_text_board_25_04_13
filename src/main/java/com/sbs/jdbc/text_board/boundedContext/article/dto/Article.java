package com.sbs.jdbc.text_board.boundedContext.article.dto;

import com.sbs.jdbc.text_board.boundedContext.common.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Article extends BaseDto {
  private final int id;
  private int memberId;
  private String subject;
  private String content;
  private int hit;

  private String writerName;
  private String boardName;

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (LocalDateTime) articleMap.get("regDate");
    this.updateDate = (LocalDateTime) articleMap.get("updateDate");
    this.memberId = (int) articleMap.get("memberId");
    this.subject = (String) articleMap.get("subject");
    this.content = (String) articleMap.get("content");
    this.hit = (int) articleMap.get("hit");

    if(articleMap.get("writerName") != null) {
      this.writerName = (String) articleMap.get("writerName");
    }

    if(articleMap.get("boardName") != null) {
      this.boardName = (String) articleMap.get("boardName");
    }
  }
}
