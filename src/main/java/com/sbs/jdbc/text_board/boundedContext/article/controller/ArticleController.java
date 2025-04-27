package com.sbs.jdbc.text_board.boundedContext.article.controller;

import com.sbs.jdbc.text_board.boundedContext.board.dto.Board;
import com.sbs.jdbc.text_board.boundedContext.board.service.BoardService;
import com.sbs.jdbc.text_board.boundedContext.common.controller.Controller;
import com.sbs.jdbc.text_board.boundedContext.member.dto.Member;
import com.sbs.jdbc.text_board.global.base.Rq;
import com.sbs.jdbc.text_board.boundedContext.article.dto.Article;
import com.sbs.jdbc.text_board.boundedContext.article.service.ArticleService;
import com.sbs.jdbc.text_board.container.Container;

import java.util.List;

public class ArticleController implements Controller {
  private BoardService boardService;
  private ArticleService articleService;

  public ArticleController() {
    boardService = Container.boardService;
    articleService = Container.articleService;
  }

  @Override
  public void performAction(Rq rq) {
    if (rq.getActionPath().equals("/usr/article/write")) {
      doWrite(rq);
    } else if (rq.getActionPath().equals("/usr/article/list")) {
      showList(rq);
    } else if (rq.getActionPath().equals("/usr/article/detail")) {
      showDetail(rq);
    } else if (rq.getActionPath().equals("/usr/article/modify")) {
      doModify(rq);
    } else if (rq.getActionPath().equals("/usr/article/delete")) {
      doDelete(rq);
    }
  }

  public void doWrite(Rq rq) {
    if(rq.isLogouted()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    List<Board> boards = boardService.findAll();

    if(boards.isEmpty()) {
      System.out.println("작성 가능한 게시판이 없습니다.");
      return;
    }

    System.out.println("== 게시판 목록 ==");
    System.out.println("번호 | 이름 | 코드");
    System.out.println("-".repeat(30));

    boards.forEach(
        board -> System.out.printf("%d | %s | %s\n", board.getId(), board.getName(), board.getCode())
    );

    System.out.print("게시판 번호를 선택해주세요 : ");
    int boardId = Integer.parseInt(Container.sc.nextLine().trim());

    Board selectedBoard = boardService.findById(boardId);

    if(selectedBoard == null) {
      System.out.println("존재하지 않는 게시판입니다.");
      return;
    }

    System.out.printf("== '%s' 게시물 작성 ==\n", selectedBoard.getName());

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

    Member member = rq.getLoginedMember();
    int memberId = member.getId();

    int id = articleService.write(memberId, boardId, subject, content, 0);

    System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
  }

  public void showList(Rq rq) {
    List<Board> boards = boardService.findAll();

    if(boards.isEmpty()) {
      System.out.println("등록된 게시판이 없습니다.");
      return;
    }

    System.out.println("== 게시판 목록 ==");
    System.out.println("번호 | 이름 | 코드");
    System.out.println("-".repeat(30));

    boards.forEach(
        board -> System.out.printf("%d | %s | %s\n", board.getId(), board.getName(), board.getCode())
    );

    System.out.print("게시판 번호를 선택해주세요 (전체 : 0) : ");
    int boardId = Integer.parseInt(Container.sc.nextLine().trim());

    List<Article> articles;
    String boardName = "전체";

    if(boardId == 0) {
      articles = articleService.findAll();
    }
    else {
      // 선택한 게시판 존재 유무 확인
      Board selectedBoard = boardService.findById(boardId);

      if(selectedBoard == null) {
        System.out.println("존재하지 않는 게시판입니다.");
        return;
      }

      articles = articleService.findByBoardId(boardId);
      boardName = selectedBoard.getName();
    }

    if (articles.isEmpty()) {
      if(boardId == 0) {
        System.out.println("게시물이 존재하지 않습니다.");
      }
      else {
        System.out.printf("'%s' 게시판에 게시물이 없습니다.", boardName);
      }

      return;
    }

    System.out.printf("== '%s' 게시물 리스트 ==\n", boardName);
    System.out.println("번호 | 게시판 | 제목 | 작성 날짜 | 작성자 | 조회수");
    articles.forEach(
        article
            -> System.out.printf("%d | %s | %s | %s | %s | %d\n", article.getId(), article.getBoardName(), article.getSubject(), article.getFormatRegDate(), article.getWriterName(), article.getHit())
    );
  }

  public void showDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    articleService.increaseHit(id);

    Article article = articleService.findById(id);

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
    }

    System.out.printf("== %d번 게시물 상세보기 ==\n", id);
    System.out.printf("번호 : %d\n", article.getId());
    System.out.printf("작성자 : %s\n", article.getWriterName());
    System.out.printf("작성날짜 : %s\n", article.getFormatRegDate());
    System.out.printf("수정날짜 : %s\n", article.getFormatUpdateDate());
    System.out.printf("조회수 : %d\n", article.getHit());
    System.out.printf("제목 : %s\n", article.getSubject());
    System.out.printf("내용 : %s\n", article.getContent());
  }

  public void doModify(Rq rq) {
    if(rq.isLogouted()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article article = articleService.findById(id);
    Member member = rq.getLoginedMember(); // 로그인한 사용자의 대한 정보를 세션에서 가져옴

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    if(article.getMemberId() != member.getId()) {
      System.out.println("해당 게시물에 대한 접근 권한이 없습니다.");
      return;
    }

    System.out.printf("== %d번 게시물 수정 ==\n", id);
    System.out.print("새 제목 : ");
    String subject = Container.sc.nextLine();

    System.out.print("새 내용 : ");
    String content = Container.sc.nextLine();

    articleService.modify(id, subject, content);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void doDelete(Rq rq) {
    if(rq.isLogouted()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article article = articleService.findById(id);
    Member member = rq.getLoginedMember();

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    if(article.getMemberId() != member.getId()) {
      System.out.println("해당 게시물에 대한 접근 권한이 없습니다.");
      return;
    }

    articleService.delete(id);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }
}
