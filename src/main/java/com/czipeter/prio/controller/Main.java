package com.czipeter.prio.controller;

import com.czipeter.prio.service.PrioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Main {

  private PrioService prioService;

  @Autowired
  public Main(PrioService prioService) {
    this.prioService = prioService;
  }

  @GetMapping(value = "/")
  public String index(Model model) {
    return prioService.index(model);
  }

  @GetMapping
  public String prioritize(Model model,
          @RequestParam(value = "ordered", required = false, defaultValue = "0") int ordered,
          @RequestParam(value = "current", required = false, defaultValue = "0") int current) {
    return prioService.prioritize(model, ordered, current);
  }

  // when a decision is made about a pair of tasks:
  @GetMapping(value = "/decide")
  public String decide(Model model,
          @RequestParam("id1") int id1,
          @RequestParam("id2") int id2,
          @RequestParam("ordered") int ordered) {
    return prioService.decide(model, id1, id2, ordered);
  }

  @GetMapping(value = "/addtask")
  public String addTask(@RequestParam("description") String description) {
    return prioService.addTask(description);
  }

  @GetMapping("/{id}/delete")
  public String delete(@PathVariable int id, Model model) {
    return prioService.deleteTask(model, id);
  }

  @GetMapping("/{id}/edit")
  public String editElement(@PathVariable int id, Model model) {
    return prioService.editTask(model, id);
  }

  @PostMapping("/save")
  public String save(Model model,
          @RequestParam int id,
          @RequestParam("description") String description) {
    return prioService.saveTask(model, id, description);
  }
}
