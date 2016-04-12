package com.fererlab.core.dao;


import com.fererlab.core.model.Model;

import javax.ejb.Local;

@Local
public interface CommandDAO<T extends Model<PK>, PK> {

    void create(T t);

    void update(T t);

    void delete(T t);

    void delete(Class<T> entityClass, PK id);

    PK createAndReturnId(T t);

}