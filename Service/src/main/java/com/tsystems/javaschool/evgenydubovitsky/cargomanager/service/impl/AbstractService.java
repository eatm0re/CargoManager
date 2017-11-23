package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DAOFacade;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.DTOFactory;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.AbstractEntity;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.Service;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.ServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class AbstractService<E extends AbstractEntity, D extends DTO<E>> implements Service<D> {

    private Class<E> entityClass;
    private static Pattern simpleNamePattern = Pattern.compile("^[a-zA-Z0-9 _-]{1,45}$");
    //private static Pattern longNamePattern = Pattern.compile("^[a-zA-Z0-9 ()_:,./-]{1,255}$");
    private static Pattern longNamePattern = Pattern.compile("^[a-zA-Z0-9 ~!@\"#№$;%^:&?*()-_=+\\[{\\]}\\\\'|,<.>/]{1,255}$");
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
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<D> getAll() {
        List<D> res = (List<D>) dao.getDAO(entityClass)
                .selectAll()
                .stream()
                .map(x -> {
                    DTO<E> dto = DTOFactory.createDTO(x);
                    dto.fill(x);
                    return dto;
                })
                .collect(Collectors.toList());
        if (res.size() == 0) {
            throw new EntityNotFoundException("No one " + entityClass.getSimpleName().toLowerCase() + " found");
        }
        return res;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    public D findById(long id) {
        E entity = dao.getDAO(entityClass).selectById(id);
        if (entity == null) {
            throw new EntityNotFoundException(entityClass.getSimpleName() + " #" + id + " not found");
        }
        D dto = (D) DTOFactory.createDTO(entity);
        dto.fill(entity);
        return dto;
    }

//    @Override
//    @SuppressWarnings("unchecked")
//    @Transactional(rollbackFor = Exception.class, readOnly = true)
//    public List<D> findByParam(String param, Object value) {
//        List<D> res = (List<D>) dao.getDAO(entityClass)
//                .selectByParam(param, value)
//                .stream()
//                .map(x -> {
//                    DTO<E> dto = DTOFactory.createDTO(x);
//                    dto.fill(x);
//                    return dto;
//                })
//                .collect(Collectors.toList());
//        if (res.size() == 0) {
//            throw new EntityNotFoundException(entityClass.getSimpleName() + " with " + param + " = " + value + " not found");
//        }
//        return res;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByParam(String param, Object value) {
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
