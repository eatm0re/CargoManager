package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.TaskDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Task;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.TaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends AbstractService<Task, TaskDTO> implements TaskService {

    public TaskServiceImpl() {
        super(Task.class);
    }
}
