package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DAOFacade;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DTOFactory;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.AbstractEntity;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.EntityNotFoundException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.WrongParameterException;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.Service;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.ServiceFacade;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class AbstractService<E extends AbstractEntity, D extends DTO<E>> implements Service<D> {

    private static final Pattern simpleNamePattern = Pattern.compile("^[a-zA-Z0-9 _-]{1,45}$");
    private static final Pattern longNamePattern = Pattern.compile("^[a-zA-Z0-9 ~!@\"#№$;%^:&?*()-_=+\\[{\\]}\\\\'|,<.>/]{1,255}$");
    private final Class<E> entityClass;
    protected DAOFacade dao;
    protected ServiceFacade service;

    protected AbstractService(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Autowired
    public void setDao(DAOFacade dao) {
        this.dao = dao;
    }

    @Autowired
    public void setService(ServiceFacade service) {
        this.service = service;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    @Loggable
    public List<D> getAll() throws BusinessException {
        List<D> res = (List<D>) dao.getDAO(entityClass)
                .selectAll()
                .stream()
                .map(x -> {
                    DTO<E> dto = DTOFactory.createDTO(x);
                    dto.fill(x);
                    return dto;
                })
                .collect(Collectors.toList());
        if (res.isEmpty()) {
            throw new EntityNotFoundException("No one " + entityClass.getSimpleName().toLowerCase() + " found");
        }
        return res;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    @Loggable
    public D findById(long id) throws BusinessException {
        if (id <= 0) {
            throw new WrongParameterException("ID must be positive");
        }
        E entity = dao.getDAO(entityClass).selectById(id);
        if (entity == null) {
            throw new EntityNotFoundException(entityClass.getSimpleName() + " #" + id + " not found");
        }
        D dto = (D) DTOFactory.createDTO(entity);
        dto.fill(entity);
        return dto;
    }

    @Loggable
    protected void removeByParam(String param, Object value) throws BusinessException {
        if (dao.getDAO(entityClass).deleteByParam(param, value) == 0) {
            throw new EntityNotFoundException(entityClass.getSimpleName() + " with " + param + " = " + value + " not found");
        }
    }

    protected static boolean isSimpleName(String str) {
        return simpleNamePattern.matcher(str).matches();
    }

    protected static boolean isLongName(String str) {
        return longNamePattern.matcher(str).matches();
    }
}
