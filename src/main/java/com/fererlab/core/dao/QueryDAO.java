package com.fererlab.core.dao;


import com.fererlab.core.model.Model;

import javax.ejb.Local;
import java.util.List;

@Local
public interface QueryDAO<T extends Model<PK>, PK> {

    T findById(Class<T> entityClass, PK id);

    List<T> list(Class<T> entityClass);

    List<T> list(Class<T> entityClass, Integer index, Integer numberOfRecords);

    Long count(Class<T> entityClass);

    void detach(T t);

}