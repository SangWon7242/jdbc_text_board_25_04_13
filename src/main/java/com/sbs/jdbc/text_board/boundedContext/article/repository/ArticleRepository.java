package com.sbs.jdbc.text_board.boundedContext.article.repository;

import com.sbs.jdbc.text_board.boundedContext.article.dto.Article;
import com.sbs.jdbc.text_board.global.util.dbUtil.MysqlUtil;
import com.sbs.jdbc.text_board.global.util.dbUtil.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleRepository {
  public int write(int memberId, int boardId, String subject, String content, int hit) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", memberId = ?", memberId);
    sql.append(", boardId = ?", boardId);
    sql.append(", subject = ?", subject);
    sql.append(", content = ?", content);
    sql.append(", hit = ?", hit);

    int id = MysqlUtil.insert(sql);

    return id;
  }

  public List<Article> findAll() {
    SecSql sql = new SecSql();
    sql.append("SELECT A.id,");
    sql.append("DATE_FORMAT(A.regDate, '%Y-%m-%d %H:%i:%s') AS regDate,");
    sql.append("DATE_FORMAT(A.updateDate, '%Y-%m-%d %H:%i:%s') AS updateDate,");
    sql.append("A.memberId,");
    sql.append("A.subject,");
    sql.append("A.content,");
    sql.append("A.hit,");
    sql.append("M.name AS writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("ORDER BY id DESC");

    List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql);

    if(articleListMap.isEmpty()) return null;

    List<Article> articles = new ArrayList<>();

    for(Map<String, Object> articleMap : articleListMap) {
      articles.add(new Article(articleMap));
    }

    return articles;
  }

  public Article findById(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT A.id,");
    sql.append("DATE_FORMAT(A.regDate, '%Y-%m-%d %H:%i:%s') AS regDate,");
    sql.append("DATE_FORMAT(A.updateDate, '%Y-%m-%d %H:%i:%s') AS updateDate,");
    sql.append("A.memberId,");
    sql.append("A.subject,");
    sql.append("A.content,");
    sql.append("A.hit,");
    sql.append("M.name AS writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("WHERE A.id = ?", id);

    Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

    if(articleMap.isEmpty()) return null;

    Article article = new Article(articleMap);

    return article;
  }

  public void modify(int id, String subject, String content) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", subject = ?", subject);
    sql.append(", content = ?", content);
    sql.append("WHERE id = ?", id);

    MysqlUtil.update(sql);
  }

  public void delete(int id) {
    SecSql sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append("WHERE id = ?", id);

    MysqlUtil.delete(sql);
  }

  public void increaseHit(int id) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET hit = hit + 1");
    sql.append("WHERE id = ?", id);

    MysqlUtil.update(sql);
  }
}
