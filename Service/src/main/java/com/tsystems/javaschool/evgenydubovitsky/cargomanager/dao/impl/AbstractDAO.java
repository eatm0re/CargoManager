package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.DAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.AbstractEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractDAO<E extends AbstractEntity> implements DAO<E> {

    private Class<E> clazz;
    protected SessionFactory sessionFactory;

    protected AbstractDAO(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> selectAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from " + clazz.getSimpleName()).list();
    }

    @Override
    public E selectById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(clazz, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> selectByParam(String param, Object value) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from " + clazz.getSimpleName() + " x where x." + param + " = ?1")
                .setParameter(1, value)
                .list();
    }

    @Override
    public void insert(E obj) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(obj);
    }

    @Override
    public void update(E obj) {
        Session session = sessionFactory.getCurrentSession();
        session.update(obj);
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from " + clazz.getSimpleName() + " x where x.id = ?1")
                .setParameter(1, id)
                .executeUpdate();
    }
}
