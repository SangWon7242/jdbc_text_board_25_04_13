package com.sbs.jdbc.text_board.boundedContext.member.dto;

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
public class Member {
  private final int id;
  private String regDate;
  private String updateDate;
  private String username;
  private String password;
  private String name;

  public Member(Map<String, Object> memberMap) {
    this.id = (int) memberMap.get("id");
    this.regDate = (String) memberMap.get("regDate");
    this.updateDate = (String) memberMap.get("updateDate");
    this.username = (String) memberMap.get("username");
    this.password = (String) memberMap.get("password");
    this.name = (String) memberMap.get("name");
  }

  public String getType() {
    return isAdmin() ? "관리자" : "일반회원";
  }

  private boolean isAdmin() {
    return username.equals("admin");
  }
}
