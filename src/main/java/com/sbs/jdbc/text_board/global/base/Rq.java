package com.sbs.jdbc.text_board.global.base;

import com.sbs.jdbc.text_board.boundedContext.member.dto.Member;
import com.sbs.jdbc.text_board.global.session.Session;
import com.sbs.jdbc.text_board.global.util.Util;
import com.sbs.jdbc.text_board.container.Container;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class Rq {
  private String url;

  @Getter
  private Map<String, String> params;

  @Getter
  private String urlPath;

  @Getter
  @Setter
  String controllerTypeCode;

  @Getter
  @Setter
  String controllerName;

  @Getter
  @Setter
  String actionMethodName;

  @Getter
  private Session session;

  private String loginedMember = "loginedMember";

  public Rq() {
    session = Container.session;
  }

  public String getActionPath() {
    String[] commandBits = urlPath.split("/");

    if(commandBits.length < 4) {
      return null;
    }
    // /usr/article/list
    // [, "usr", "article", "list"]
    controllerTypeCode = commandBits[1];
    controllerName = commandBits[2];
    actionMethodName = commandBits[3];

    return "/%s/%s/%s".formatted(controllerTypeCode, controllerName, actionMethodName);
  }

  public void setCommand(String url) {
    this.url = url;
    params = Util.getParamsFromUrl(this.url);
    urlPath = Util.getPathFromUrl(this.url);
  }

  public int getIntParam(String paramName, int defaultValue) {
    if (!params.containsKey(paramName)) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(params.get(paramName));
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public String getParam(String paramName, String defaultValue) {
    if (!params.containsKey(paramName)) return defaultValue;

    return params.get(paramName);
  }

  public boolean isLogined() {
    return hasSessionAttr(loginedMember);
  }

  public boolean isLogouted() {
    return !isLogined();
  }

  public void login(Member member) {
    setSessionAttr(loginedMember, member);
  }

  public void logout() {
    removeSessionAttr(loginedMember);
  }

  public Object getSessionAttr(String attrName) {
    return session.getAttribute(attrName);
  }

  public void setSessionAttr(String attrName, Object value) {
    session.setAttribute(attrName, value);
  }

  public boolean hasSessionAttr(String attrName) {
    return session.hasAttribute(attrName);
  }

  public void removeSessionAttr(String attrName) {
    session.removeAttribute(attrName);
  }

  public Member getLoginedMember() {
    return (Member) getSessionAttr(loginedMember);
  }
}
