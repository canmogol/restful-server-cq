package com.fererlab.core.dao;

import com.fererlab.core.model.Model;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractDAO<T extends Model<PK>, PK> implements BaseDAO<T, PK> {

    private final Class<T> entityClass;

    @Resource
    private EJBContext ejbContext;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @EJB(beanName = "CommandDAOImpl")
    private CommandDAO<T, PK> commandDAO;

    @EJB(beanName = "QueryDAOImpl")
    private QueryDAO<T, PK> queryDAO;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        EntityManager entityManagerFactoryInstance = entityManagerFactory.createEntityManager();
        return entityManagerFactoryInstance;
    }

    @Override
    public void create(T t) {
        commandDAO.create(t);
    }

    @Override
    public void update(T t) {
        commandDAO.update(t);
    }

    @Override
    public void delete(T t) {
        commandDAO.delete(t);
    }

    @Override
    public void delete(PK id) {
        commandDAO.delete(entityClass, id);
    }

    @Override
    public T findById(PK id) {
        return queryDAO.findById(entityClass, id);
    }

    @Override
    public List<T> list() {
        return queryDAO.list(entityClass);
    }

    @Override
    public List<T> list(Integer index, Integer numberOfRecords) {
        return queryDAO.list(entityClass, index, numberOfRecords);
    }

    @Override
    public Long count() {
        return queryDAO.count(entityClass);
    }

}
