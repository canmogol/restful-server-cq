package com.fererlab.core.dao;


import com.fererlab.core.model.BaseModel;
import com.fererlab.core.model.Model;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless(name = "QueryDAOImpl", mappedName = "QueryDAOImpl")
public class QueryDAOImpl<T extends Model<PK>, PK> implements QueryDAO<T, PK> {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @PostConstruct
    private void initialize() {
        EntityManager entityManagerFactoryInstance = entityManagerFactory.createEntityManager();
        this.entityManager = entityManagerFactoryInstance;
    }

    private EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public T findById(Class<T> entityClass, PK id) {
        EntityManager entityManager = getEntityManager();
        T t = entityManager.find(entityClass, id);
        if (t != null && t instanceof BaseModel && ((BaseModel) t).isDeleted()) {
            // this entity is marked as deleted, return null
            return null;
        }
        return t;
    }

    @Override
    public List<T> list(Class<T> entityClass) {
        // What kind of a method is this "list" method? shouldn't it has some sort of a limit?
        // Hibernate implementation also thinks in the same way:
        // org.hibernate.ejb.AbstractQueryImpl   getMaxResults(){
        //      return maxResults == -1
        //          ? Integer.MAX_VALUE // stupid spec... MAX_VALUE??
        //          : maxResults;
        // }
        return list(entityClass, 0, Integer.MAX_VALUE);
    }

    @Override
    public List<T> list(Class<T> entityClass, Integer index, Integer numberOfRecords) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        List<T> list = entityManager.createQuery(criteriaQuery).setFirstResult(index).setMaxResults(numberOfRecords)
                .getResultList();
        return list;
    }

    @Override
    public Long count(Class<T> entityClass) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(entityClass);
        if (BaseModel.class.isAssignableFrom(entityClass)) {
            criteriaQuery.where(criteriaBuilder.equal(root.get("deleted"), Boolean.FALSE));
        }
        criteriaQuery.select(criteriaBuilder.count(root));
        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        Long count = query.getSingleResult();
        return count;
    }

    @Override
    public void detach(T t) {
        EntityManager entityManager = getEntityManager();
        entityManager.detach(t);
    }

}