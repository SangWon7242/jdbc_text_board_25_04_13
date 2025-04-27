package com.sbs.jdbc.text_board.boundedContext.member.repository;

import com.sbs.jdbc.text_board.boundedContext.member.dto.Member;
import com.sbs.jdbc.text_board.global.util.dbUtil.MysqlUtil;
import com.sbs.jdbc.text_board.global.util.dbUtil.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemberRepository {
  public Member findByUsername(String username) {
    SecSql sql = new SecSql();
    sql.append("SELECT M.*");
    sql.append("FROM `member` AS M");
    sql.append("WHERE username = ?", username);

    Map<String, Object> memberMap = MysqlUtil.selectRow(sql);

    if(memberMap.isEmpty()) return null;

    Member member = new Member(memberMap);

    return member;
  }

  public int join(String username, String password, String name) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", username = ?", username);
    sql.append(", password = ?", password);
    sql.append(", name = ?", name);

    int id = MysqlUtil.insert(sql);

    return id;
  }
}
