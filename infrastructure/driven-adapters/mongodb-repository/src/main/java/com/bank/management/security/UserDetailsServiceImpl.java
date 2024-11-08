package com.bank.management.security;

import com.bank.management.data.CustomerDocument;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MongoTemplate mongoTemplate;

    public UserDetailsServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerDocument customer = mongoTemplate.findOne(
                Query.query(Criteria.where("user.username").is(username)),
                CustomerDocument.class, "customer"
        );

        if (customer == null || customer.getUser() == null) {
            throw new UsernameNotFoundException("Invalid credentials");
        }
        UserDetails user = customer.getUser();
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
