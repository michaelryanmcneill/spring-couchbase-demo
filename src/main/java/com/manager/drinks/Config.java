package com.manager.drinks;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
public class Config extends AbstractCouchbaseConfiguration {
    
    @Override
    protected List<String> getBootstrapHosts() {
        return Collections.singletonList(System.getenv("COUCHBASE_SERVICE_NAME"));
    }
    
    @Override
    protected String getBucketName() {
        return System.getenv("COUCHBASE_BUCKET");
    }
    
    @Override
    protected String getBucketPassword() {
        return System.getenv("COUCHBASE_PASSWORD");
    }
    
    @Override
    protected String getUsername() {
        return System.getenv("COUCHBASE_USERNAME");
    }
}