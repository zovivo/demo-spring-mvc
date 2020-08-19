package com.repository.impl;

import com.model.User;
import com.repository.BaseRepository;
import org.apache.log4j.Logger;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

public class UserRepository extends BaseRepository<User,Long> {

    Logger logger = Logger.getLogger(UserRepository.class);

    public UserRepository() {
        super(User.class);
    }

    public User findByEmail(String email){
        String queryStr = "from User u where u.email = :email";
        Map<String,Object> params = new HashMap<>();
        params.put("email",email);
        Query query = buildQueryHasParameters(queryStr, false, params);
        User user = null;
        try {
            user = (User) query.getSingleResult();
        }catch (NoResultException e){
            logger.info(e);
        }
        return user;
    }

    public User findByUserName(String userName){
        String queryStr = "from User u where u.userName = :userName";
        Map<String,Object> params = new HashMap<>();
        params.put("userName",userName);
        Query query = buildQueryHasParameters(queryStr, false, params);
        User user = null;
        try {
            user = (User) query.getSingleResult();
        }catch (NoResultException e){
            logger.info(e);
        }
        return user;
    }

}
