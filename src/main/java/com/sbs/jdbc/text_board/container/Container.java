package com.sbs.jdbc.text_board.container;

import com.sbs.jdbc.text_board.boundedContext.board.controller.BoardController;
import com.sbs.jdbc.text_board.boundedContext.board.repository.BoardRepository;
import com.sbs.jdbc.text_board.boundedContext.board.service.BoardService;
import com.sbs.jdbc.text_board.global.session.Session;
import com.sbs.jdbc.text_board.boundedContext.article.controller.ArticleController;
import com.sbs.jdbc.text_board.boundedContext.article.repository.ArticleRepository;
import com.sbs.jdbc.text_board.boundedContext.article.service.ArticleService;
import com.sbs.jdbc.text_board.boundedContext.member.controller.MemberController;
import com.sbs.jdbc.text_board.boundedContext.member.repository.MemberRepository;
import com.sbs.jdbc.text_board.boundedContext.member.service.MemberService;

import java.util.Scanner;

public class Container {
  public static Scanner sc;
  public static Session session;

  public static BoardRepository boardRepository;
  public static MemberRepository memberRepository;
  public static ArticleRepository articleRepository;

  public static BoardService boardService;
  public static MemberService memberService;
  public static ArticleService articleService;

  public static BoardController boardController;
  public static MemberController memberController;
  public static ArticleController articleController;

  static {
    sc = new Scanner(System.in);
    session = new Session();

    boardRepository = new BoardRepository();
    memberRepository = new MemberRepository();
    articleRepository = new ArticleRepository();

    boardService = new BoardService();
    memberService = new MemberService();
    articleService = new ArticleService();

    boardController = new BoardController();
    memberController = new MemberController();
    articleController = new ArticleController();
  }
}
