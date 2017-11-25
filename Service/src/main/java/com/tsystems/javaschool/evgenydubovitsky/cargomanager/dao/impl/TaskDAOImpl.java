package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.TaskDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Task;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDAOImpl extends AbstractDAO<Task> implements TaskDAO {

    public TaskDAOImpl() {
        super(Task.class);
    }
}
