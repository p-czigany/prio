package com.czipeter.prio.dataaccess;

import com.czipeter.prio.model.Task;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {

  Task findOneBySequence(int sequence);

  Task findOneById(int id);

  List<Task> findAllByOrderBySequence();
}
