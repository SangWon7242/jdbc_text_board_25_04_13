package com.sbs.jdbc.text_board;

import com.sbs.jdbc.text_board.base.Rq;
import com.sbs.jdbc.text_board.boundedContext.article.dto.Article;
import com.sbs.jdbc.text_board.container.Container;
import com.sbs.jdbc.text_board.dbUtil.MysqlUtil;
import com.sbs.jdbc.text_board.dbUtil.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {
  public List<Article> articles;

  public App() {
    articles = new ArrayList<>();
  }

  private static boolean isDevMode() {
    // 이 부분을 false로 바꾸면 production 모드 이다.
    // true는 개발자 모드이다.(개발할 때 좋다.)
    return true;
  }

  public void run() {
    System.out.println("== 자바 텍스트 게시판 시작 ==");

    try {
      while (true) {
        System.out.print("명령어) ");
        String cmd = Container.sc.nextLine();

        Rq rq = new Rq();
        rq.setCommand(cmd);

        // DB 세팅
        // root, ""
        MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "text_board");
        MysqlUtil.setDevMode(isDevMode());
        // DB 끝

        doAction(rq);
      }
    } finally {
      System.out.println("== 프로그램을 종료합니다. ==");
      Container.sc.close();
    }

  }

  private void doAction(Rq rq) {
    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println("== 게시물 작성 ==");

      System.out.print("제목 : ");
      String subject = Container.sc.nextLine();

      if (subject.trim().isEmpty()) {
        System.out.println("subject(을)를 입력해주세요.");
        return;
      }

      System.out.print("내용 : ");
      String content = Container.sc.nextLine();

      if (content.trim().isEmpty()) {
        System.out.println("content(을)를 입력해주세요.");
        return;
      }

      SecSql sql = new SecSql();
      sql.append("INSERT INTO article");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", subject = ?", subject);
      sql.append(", content = ?", content);

      int id = MysqlUtil.insert(sql);

      System.out.printf("%d번 게시물이 생성되었습니다.\n", id);

    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("ORDER BY id DESC");

      List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql);

      if (articleListMap.isEmpty()) {
        System.out.println("게시물이 존재하지 않습니다.");
        return;
      }

      System.out.println("== 게시물 리스트 ==");
      System.out.println("제목 | 내용");
      articleListMap.forEach(
          articleMap
              -> System.out.printf("%d | %s\n", (int) articleMap.get("id"), articleMap.get("subject"))
      );
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      int id = rq.getIntParam("id", 0);

      if(id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) > 0");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      boolean articleIsEmpty = MysqlUtil.selectRowBooleanValue(sql);

      if(!articleIsEmpty) {
        System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
        return;
      }

      System.out.printf("== %d번 게시물 수정 ==\n", id);
      System.out.print("새 제목 : ");
      String subject = Container.sc.nextLine();

      System.out.print("새 내용 : ");
      String content = Container.sc.nextLine();

      sql = new SecSql();
      sql.append("UPDATE article");
      sql.append("SET updateDate = NOW()");
      sql.append(", subject = ?", subject);
      sql.append(", content = ?", content);
      sql.append("WHERE id = ?", id);

      MysqlUtil.update(sql);

      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("프로그램을 종료합니다.");
      System.exit(0); // 프로그램 강제종룔
    } else {
      System.out.println("잘못 입력 된 명령어입니다.");
    }
  }
}

