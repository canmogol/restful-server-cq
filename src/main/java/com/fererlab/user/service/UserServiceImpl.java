package com.fererlab.user.service;

import com.fererlab.core.dao.BaseDAO;
import com.fererlab.core.service.AbstractService;
import com.fererlab.user.dao.UserDAO;
import com.fererlab.user.model.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "UserServiceImpl", mappedName = "UserServiceImpl")
public class UserServiceImpl extends AbstractService<User, Integer> implements UserService {

    @EJB(beanName = "UserDAOImpl")
    private UserDAO userDAO;

    @Override
    public BaseDAO<User, Integer> getBaseDAO() {
        return userDAO;
    }

}
