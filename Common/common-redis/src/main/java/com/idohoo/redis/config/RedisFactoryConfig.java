package com.idohoo.redis.config;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties(RedisConfig.class)
@EnableTransactionManagement
public class RedisFactoryConfig {

    @Autowired
    private RedisConfig redisConfig;
    
    private JedisPoolConfig jedisPoolConfig = null;
    
    private JedisPool jedisPool = null;
    
    @Bean(destroyMethod = "close")
	public JedisPool jedisPoolConfig() {	
    	
    	jedisPoolConfig = new JedisPoolConfig();
    	jedisPoolConfig.setMaxIdle(redisConfig.getPool_maxIdle());
    	jedisPoolConfig.setMinIdle(redisConfig.getPool_minIdle());
    	jedisPoolConfig.setMaxTotal(redisConfig.getPool_maxTotal());
    	jedisPoolConfig.setMaxWaitMillis(redisConfig.getPool_maxWait());
    	
    	jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort(),
    			redisConfig.getTimeout(), redisConfig.getPassword());
    	
		return jedisPool;
	}

    @PreDestroy
    public void close() {
        if(jedisPool != null){
        	jedisPool.destroy();
        }
    }
    
}
