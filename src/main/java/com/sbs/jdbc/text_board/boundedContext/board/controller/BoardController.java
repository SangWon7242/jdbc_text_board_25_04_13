package com.sbs.jdbc.text_board.boundedContext.board.controller;

import com.sbs.jdbc.text_board.boundedContext.board.dto.Board;
import com.sbs.jdbc.text_board.boundedContext.board.service.BoardService;
import com.sbs.jdbc.text_board.boundedContext.common.controller.Controller;
import com.sbs.jdbc.text_board.boundedContext.member.dto.Member;
import com.sbs.jdbc.text_board.container.Container;
import com.sbs.jdbc.text_board.global.base.Rq;

public class BoardController implements Controller {
  private BoardService boardService;

  public BoardController() {
    boardService = Container.boardService;
  }

  @Override
  public void performAction(Rq rq) {
    if(rq.getActionPath().equals("/usr/board/makeBoard")) {
      doMakeBoard(rq);
    }
  }

  private void doMakeBoard(Rq rq) {
    System.out.println("== 게시판 생성 ==");

    if(rq.isLogouted()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    Member member = rq.getLoginedMember();

    if(!member.isAdmin()) {
      System.out.println("관리자만 게시판을 생성할 수 있습니다.");
      return;
    }

    System.out.print("게시판 이름 : ");
    String boardName = Container.sc.nextLine();

    if(boardName.trim().isEmpty()) {
      System.out.println("boardName(을)를 입력해주세요.");
      return;
    }

    Board boardByName = boardService.findByBoardName(boardName);

    if(boardByName != null) {
      System.out.printf("'%s' 게시판은 이미 존재하는 게시판입니다.\n", boardName);
      return;
    }

    System.out.println("=== 게시판 코드 예시 ===");
    System.out.println("예: free, notice, qna, etc...");
    System.out.println("=".repeat(10));

    System.out.print("게시판 코드(영문) : ");
    String boardCode = Container.sc.nextLine();

    if(boardCode.trim().isEmpty()) {
      System.out.println("boardCode(을)를 입력해주세요.");
      return;
    }

    Board boardByCode = boardService.findByBoardCode(boardCode);

    if(boardByCode != null) {
      System.out.printf("'%s' 코드는 이미 사용중인 코드입니다.\n", boardCode);
      return;
    }

    boardService.makeBoard(boardCode, boardName);

    System.out.printf("'%s' 게시판이 생성되었습니다.\n", boardName);
  }
}
