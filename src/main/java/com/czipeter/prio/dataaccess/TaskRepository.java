package com.czipeter.prio.dataaccess;

import com.czipeter.prio.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
