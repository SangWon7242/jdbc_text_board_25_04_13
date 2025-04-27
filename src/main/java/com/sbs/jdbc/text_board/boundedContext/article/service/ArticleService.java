package com.sbs.jdbc.text_board.boundedContext.article.service;

import com.sbs.jdbc.text_board.boundedContext.article.dto.Article;
import com.sbs.jdbc.text_board.boundedContext.article.repository.ArticleRepository;
import com.sbs.jdbc.text_board.container.Container;

import java.util.List;

public class ArticleService {
  private ArticleRepository articleRepository;

  public ArticleService() {
    articleRepository = Container.articleRepository;
  }

  public int write(int memberId, int boardId, String subject, String content, int hit) {
    return articleRepository.write(memberId, boardId, subject, content, hit);
  }

  public List<Article> findAll() {
    return articleRepository.findAll(null);
  }

  public List<Article> findByBoardId(int boardId) {
    return articleRepository.findAll(boardId);
  }

  public Article findById(int id) {
    return articleRepository.findById(id);
  }

  public void modify(int id, String subject, String content) {
    articleRepository.modify(id, subject, content);
  }

  public void delete(int id) {
    articleRepository.delete(id);
  }

  public void increaseHit(int id) {
    articleRepository.increaseHit(id);
  }
}
