package com.sbs.jdbc.text_board.boundedContext.board.service;

import com.sbs.jdbc.text_board.boundedContext.board.dto.Board;
import com.sbs.jdbc.text_board.boundedContext.board.repository.BoardRepository;
import com.sbs.jdbc.text_board.container.Container;

import java.util.List;

public class BoardService {
  private BoardRepository boardRepository;

  public BoardService() {
    boardRepository = Container.boardRepository;
  }

  public Board findByBoardName(String name) {
    return boardRepository.findByBoardName(name);
  }

  public Board findByBoardCode(String code) {
    return boardRepository.findByBoardCode(code);
  }

  public void makeBoard(String code, String name) {
    boardRepository.makeBoard(code, name);
  }

  public List<Board> findAll() {
    return boardRepository.findAll();
  }

  public Board findById(int id) {
    return boardRepository.findById(id);
  }
}
