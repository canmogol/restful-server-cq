package com.fererlab.core.dao;


import com.fererlab.core.model.BaseModel;
import com.fererlab.core.model.Model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Stateless(name = "CommandDAOImpl", mappedName = "CommandDAOImpl")
public class CommandDAOImpl<T extends Model<PK>, PK> implements CommandDAO<T, PK> {


    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    @Override
    public void create(T t) {
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();
        if (!entityManager.contains(t)) {
            entityManager.merge(t);
        }
        entityManager.persist(t);
    }

    @Override
    public void update(T t) {
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();
        entityManager.merge(t);
    }

    @Override
    public void delete(T t) {
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();
        if (!entityManager.contains(t)) {
            t = entityManager.merge(t);
        }
        if (t instanceof BaseModel) {
            ((BaseModel) t).setDeleted(Boolean.TRUE);
            entityManager.merge(t);
        } else {
            entityManager.remove(t);
        }
    }

    @Override
    public void delete(Class<T> entityClass, PK id) {
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();
        T t = entityManager.getReference(entityClass, id);
        if (t instanceof BaseModel) {
            ((BaseModel) t).setDeleted(Boolean.TRUE);
            entityManager.merge(t);
        } else {
            entityManager.remove(t);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public PK createAndReturnId(T t) {
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();
        entityManager.persist(t);
        return (PK) getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(t);
    }

}