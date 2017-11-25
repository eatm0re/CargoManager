package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.CheckpointDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.Checkpoint;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.CheckpointService;
import org.springframework.stereotype.Service;

@Service
public class CheckpointServiceImpl extends AbstractService<Checkpoint, CheckpointDTO> implements CheckpointService {

    public CheckpointServiceImpl() {
        super(Checkpoint.class);
    }
}
