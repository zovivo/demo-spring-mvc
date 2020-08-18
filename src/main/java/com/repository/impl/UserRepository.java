package com.repository.impl;

import com.model.User;
import com.repository.BaseRepository;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

public class UserRepository extends BaseRepository<User,Long> {

    public UserRepository() {
        super(User.class);
    }

    public User findByEmail(String email){
        String queryStr = "from User u where u.email = :email";
        Map<String,Object> params = new HashMap<>();
        params.put("email",email);
        Query query = buildQueryHasParameters(queryStr, false, params);
        User user = (User) query.getSingleResult();
        return user;
    }

}
