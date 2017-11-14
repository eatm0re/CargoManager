package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.CheckpointDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.Checkpoint;
import org.springframework.stereotype.Repository;

@Repository
public class CheckpointDAOImpl extends AbstractDAO<Checkpoint> implements CheckpointDAO {

    public CheckpointDAOImpl() {
        super(Checkpoint.class);
    }
}
