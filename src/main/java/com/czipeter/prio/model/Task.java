package com.czipeter.prio.model;

import com.czipeter.prio.service.PrioService;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String description;

  private int sequence;

  public Task() {
  }

  public Task(String description) {
    this.description = description;
    PrioService.setTaskCounter(PrioService.getTaskCounter() + 1);
    sequence = PrioService.getTaskCounter();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getSequence() {
    return sequence;
  }

  public void setSequence(int sequence) {
    this.sequence = sequence;
  }
}
