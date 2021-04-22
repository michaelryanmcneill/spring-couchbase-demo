package com.manager.drinks;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
public class Config extends AbstractCouchbaseConfiguration {
    
    @Override
    protected List<String> getBootstrapHosts() {
        return Collections.singletonList("db");
    }
    
    @Override
    protected String getBucketName() {
        return "default";
    }
    
    @Override
    protected String getBucketPassword() {
        return "password";
    }
    
    @Override
    protected String getUsername() {
        return "admin";
    }
}


