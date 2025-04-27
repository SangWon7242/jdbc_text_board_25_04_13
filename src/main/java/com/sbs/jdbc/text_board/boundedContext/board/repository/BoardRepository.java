package com.sbs.jdbc.text_board.boundedContext.board.repository;

import com.sbs.jdbc.text_board.boundedContext.article.dto.Article;
import com.sbs.jdbc.text_board.boundedContext.board.dto.Board;
import com.sbs.jdbc.text_board.global.util.dbUtil.MysqlUtil;
import com.sbs.jdbc.text_board.global.util.dbUtil.SecSql;

import java.util.Map;

public class BoardRepository {

  public void makeBoard(String code, String name) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO board");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", `code` = ?", code);
    sql.append(", `name` = ?", name);

    MysqlUtil.insert(sql);
  }

  public Board findByBoardName(String name) {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM board");
    sql.append("WHERE `name` = ?", name);

    Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

    if(boardMap.isEmpty()) return null;

    return new Board(boardMap);
  }

  public Board findByBoardCode(String code) {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM board");
    sql.append("WHERE `code` = ?", code);

    Map<String, Object> boardMap = MysqlUtil.selectRow(sql);

    if(boardMap.isEmpty()) return null;

    return new Board(boardMap);
  }
}
