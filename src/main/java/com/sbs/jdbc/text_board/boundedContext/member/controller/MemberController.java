package com.sbs.jdbc.text_board.boundedContext.member.controller;

import com.sbs.jdbc.text_board.boundedContext.common.controller.Controller;
import com.sbs.jdbc.text_board.global.base.Rq;
import com.sbs.jdbc.text_board.boundedContext.member.dto.Member;
import com.sbs.jdbc.text_board.boundedContext.member.service.MemberService;
import com.sbs.jdbc.text_board.container.Container;

public class MemberController implements Controller {
  private MemberService memberService;

  public MemberController() {
    memberService = Container.memberService;
  }

  @Override
  public void performAction(Rq rq) {
    if (rq.getActionPath().equals("/usr/member/join")) {
      doJoin(rq);
    } else if (rq.getActionPath().equals("/usr/member/login")) {
      doLogin(rq);
    } else if (rq.getActionPath().equals("/usr/member/logout")) {
      doLogout(rq);
    } else if (rq.getActionPath().equals("/usr/member/myPage")) {
      showMyPage(rq);
    }
  }

  public void doJoin(Rq rq) {
    String username;
    String password;
    String passwordConfirm;
    String name;
    Member member;

    if (rq.isLogined()) {
      System.out.println("로그아웃 후 이용해주세요.");
      return;
    }

    System.out.println("== 회원 가입 ==");

    // 로그인 아이디 입력
    while (true) {
      System.out.print("로그인 아이디 : ");
      username = Container.sc.nextLine();

      if (username.trim().isEmpty()) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      member = memberService.findByUsername(username);

      if (member != null) {
        System.out.printf("'%s'(은)는 이미 가입된 로그인 아이디입니다.\n", username);
        continue;
      }

      break;
    }

    // 비밀번호 입력
    while (true) {
      System.out.print("로그인 비밀번호 : ");
      password = Container.sc.nextLine();

      if (password.trim().isEmpty()) {
        System.out.println("비밀번호를 입력해주세요.");
        continue;
      }

      while (true) {
        System.out.print("로그인 비밀번호 확인 : ");
        passwordConfirm = Container.sc.nextLine();

        if (passwordConfirm.trim().isEmpty()) {
          System.out.println("비밀번호 확인을 입력해주세요.");
          continue;
        }

        if (!passwordConfirm.equals(password)) {
          System.out.println("비밀번호가 일치하지 않습니다.");
          continue;
        }

        break;
      }

      break;
    }

    // 이름 입력
    while (true) {
      System.out.print("이름 : ");
      name = Container.sc.nextLine();

      if (name.trim().isEmpty()) {
        System.out.println("이름을 입력해주세요.");
        continue;
      }

      break;
    }

    int id = memberService.join(username, password, name);

    System.out.printf("'%s'님 회원가입 되었습니다.\n", name);
  }

  public void doLogin(Rq rq) {
    String username;
    String password;
    Member member;

    if (rq.isLogined()) {
      System.out.println("로그아웃 후 이용해주세요.");
      return;
    }

    System.out.println("== 로그인 ==");

    // 로그인 아이디 입력
    while (true) {
      System.out.print("로그인 아이디 : ");
      username = Container.sc.nextLine();

      if (username.trim().isEmpty()) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      member = memberService.findByUsername(username);

      if (member == null) {
        System.out.printf("'%s'(은)는 존재하지 않는 아이디입니다.\n", username);
        continue;
      }

      break;
    }

    // 비밀번호 입력
    while (true) {
      System.out.print("로그인 비밀번호 : ");
      password = Container.sc.nextLine();

      if (password.trim().isEmpty()) {
        System.out.println("비밀번호를 입력해주세요.");
        continue;
      }

      if (!member.getPassword().equals(password)) {
        System.out.println("비밀번호가 일치하지 않습니다.");
        continue;
      }

      break;
    }

    rq.login(member);

    System.out.printf("'%s'님 로그인 되었습니다.\n", member.getUsername());
  }

  public void doLogout(Rq rq) {
    if (rq.isLogouted()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    rq.logout();

    System.out.println("로그아웃 되었습니다.");
  }

  public void showMyPage(Rq rq) {
    if (rq.isLogouted()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    Member member = rq.getLoginedMember();

    System.out.printf("== '%s' 회원의 정보 ==\n", member.getUsername());
    System.out.printf("아이디 : %s\n", member.getUsername());
    System.out.printf("가입 날짜 : %s\n", member.getRegDate());
    System.out.printf("수정 날짜 : %s\n", member.getUpdateDate());
    System.out.printf("이름 : %s\n", member.getName());
  }
}
