package com.example.web;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component("helloBean")
@RequestScope
public class HelloBean {

  private String name = "Mundo";

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String greet() {
    return "Olá, " + name + "!";
  }
}