package com.czipeter.prio.service;

import com.czipeter.prio.dataaccess.TaskRepository;
import com.czipeter.prio.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class PrioService {

  private static int taskCounter = 0;

  private static final String REDIRECT_TO_INDEX = "redirect:/";
  private TaskRepository taskRepository;

  @Autowired
  public PrioService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public String index(Model model) {
    model.addAttribute("tasks", taskRepository.findAllByOrderBySequence());
    return "index";
  }

  public String prioritize(Model model, int ordered, int current) {
    if (taskRepository.count() < 2) {
      model.addAttribute("noneed", true);
      return REDIRECT_TO_INDEX;
    }
    int startingSequence = (current == 0) ? taskRepository.findAllByOrderBySequence().size() : current;
    model.addAttribute("firstone",
            taskRepository.findOneBySequence(startingSequence));
    model.addAttribute("secondone",
            taskRepository.findOneBySequence(startingSequence - 1));
    model.addAttribute("ordered", ordered);
    return "prioritize";
  }

  public String decide(Model model ,int moreImportantId, int lessImportantId, int ordered) {
    Task moreImportantTask = taskRepository.findOneById(moreImportantId);
    Task lessImportantTask = taskRepository.findOneById(lessImportantId);

    swapPlaces(moreImportantTask, lessImportantTask);

    if (moreImportantTask.getSequence() == ordered + 1) {
      if (ordered >= (int) taskRepository.count() - 2) {
        model.addAttribute("done", true);
        return REDIRECT_TO_INDEX;
      }
      return "redirect:/prioritize?ordered=" + (ordered + 1) + "&current=" + taskRepository.findAllByOrderBySequence().size();
    }
    return "redirect:/prioritize?ordered=" + ordered + "&current=" + moreImportantTask.getSequence();
  }

  private void swapPlaces(Task moreImportantTask, Task lessImportantTask) {
    if (moreImportantTask.getSequence() > lessImportantTask.getSequence()) {
      int temp = moreImportantTask.getSequence();
      moreImportantTask.setSequence(lessImportantTask.getSequence());
      lessImportantTask.setSequence(temp);
      taskRepository.save(moreImportantTask);
      taskRepository.save(lessImportantTask);
    }
  }

  public String addTask(String description) {
    taskRepository.save(new Task(description));
    return REDIRECT_TO_INDEX;
  }

  public String editTask(Model model, int id) {
    model.addAttribute("task", taskRepository.findOneById(id));
    return "edit";
  }

  public String deleteTask(Model model, int id) {
    Task toDelete = taskRepository.findOneById(id);
    for (Task examinedTask :
            taskRepository.findAllByOrderBySequence()) {
      if (examinedTask.getSequence() > toDelete.getSequence()) {
        examinedTask.setSequence(examinedTask.getSequence() - 1);
      }
    }
    PrioService.setTaskCounter(PrioService.getTaskCounter() - 1);
    taskRepository.delete(id);
    model.addAttribute("mealRepo", taskRepository.findAllByOrderBySequence());
    return REDIRECT_TO_INDEX;
  }

  public String saveTask(Model model, int id, String description) {
    Task currentTask = taskRepository.findOneById(id);
    model.addAttribute("task", currentTask);
    currentTask.setDescription(description);
    taskRepository.save(currentTask);
    return REDIRECT_TO_INDEX;
  }

  public static int getTaskCounter() {
    return taskCounter;
  }

  public static void setTaskCounter(int taskCounter) {
    PrioService.taskCounter = taskCounter;
  }
}
