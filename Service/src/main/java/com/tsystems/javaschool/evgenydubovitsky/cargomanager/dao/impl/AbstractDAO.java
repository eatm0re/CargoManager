package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.AbstractEntity;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Loggable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractDAO<E extends AbstractEntity> implements DAO<E> {

    private Class<E> entityClass;
    protected SessionFactory sessionFactory;

    protected AbstractDAO(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Class<E> getEntityClass() {
        return entityClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    @Loggable
    public List<E> selectAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from " + entityClass.getSimpleName()).list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    @Loggable
    public E selectById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    @Loggable
    protected List<E> selectByParam(String param, Object value) {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery("from " + entityClass.getSimpleName() + " x where x." + param + " = ?1")
                .setParameter(1, value)
                .list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    @Loggable
    public void insert(E obj) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(obj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    @Loggable
    public void update(E obj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(obj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    @Loggable
    public int deleteByParam(String param, Object value) {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery("delete from " + entityClass.getSimpleName() + " x where x." + param + " = ?1")
                .setParameter(1, value)
                .executeUpdate();
    }
}
