package com.sbs.jdbc.text_board;

import com.sbs.jdbc.text_board.boundedContext.common.controller.Controller;
import com.sbs.jdbc.text_board.boundedContext.member.dto.Member;
import com.sbs.jdbc.text_board.container.Container;
import com.sbs.jdbc.text_board.global.base.Rq;
import com.sbs.jdbc.text_board.global.util.dbUtil.MysqlUtil;

public class App {
  private static boolean isDevMode() {
    // 이 부분을 false로 바꾸면 production 모드 이다.
    // true는 개발자 모드이다.(개발할 때 좋다.)
    return true;
  }

  public void run() {
    System.out.println("== 자바 텍스트 게시판 시작 ==");

    try {
      while (true) {
        Rq rq = new Rq();

        Member member = rq.getLoginedMember();

        String promptName = "명령어";

        if(member != null) {
          promptName = member.getUsername();
        }

        System.out.printf("%s) ", promptName);
        String cmd = Container.sc.nextLine();

        rq.setCommand(cmd);

        // DB 세팅
        // root, ""
        MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "text_board");
        MysqlUtil.setDevMode(isDevMode());
        // DB 끝

        String getActionPath = rq.getActionPath();

        Controller controller = null;

        if(getActionPath != null) {
          controller = getControllerByRequestUri(rq);
        }

        if(controller != null) {
          controller.performAction(rq);
        } else if (cmd.equals("exit")) {
          System.out.println("== 프로그램을 종료합니다. ==");
          break;
        } else {
          System.out.println("잘못 된 명령어입니다.");
        }

      }
    } finally {
      Container.sc.close();
    }
  }

  private Controller getControllerByRequestUri(Rq rq) {
    switch (rq.getControllerTypeCode()) {
      case "usr":
        switch (rq.getControllerName()) {
          case "article":
            return Container.articleController;
          case "member":
            return Container.memberController;
        }
    }

    return null;
  }
}

