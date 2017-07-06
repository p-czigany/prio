package com.czipeter.prio.controller;

import com.czipeter.prio.service.PrioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Main {

  private PrioService prioService;

  @Autowired
  public Main(PrioService prioService) {
    this.prioService = prioService;
  }

  @GetMapping(value = "/")
  public String index() {
    return prioService.index();
  }
}
