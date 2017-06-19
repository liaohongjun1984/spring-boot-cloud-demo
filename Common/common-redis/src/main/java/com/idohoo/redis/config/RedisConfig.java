package com.idohoo.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 读取redis配置文件
 * @author henser
 *
 */
@Configuration
@ConfigurationProperties(ignoreUnknownFields = true)
public class RedisConfig {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${spring.redis.database}")
	private String database;

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.redis.pool.maxWait}")
	private int pool_maxWait;

	@Value("${spring.redis.pool.maxIdle}")
	private int pool_maxIdle;

	@Value("${spring.redis.pool.minIdle}")
	private int pool_minIdle;
	
	@Value("${spring.redis.pool.maxTotal}")
	private int pool_maxTotal;

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger=logger;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database=database;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host=host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port=port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password=password;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout=timeout;
	}

	public int getPool_maxWait() {
		return pool_maxWait;
	}

	public void setPool_maxWait(int pool_maxWait) {
		this.pool_maxWait=pool_maxWait;
	}

	public int getPool_maxIdle() {
		return pool_maxIdle;
	}

	public void setPool_maxIdle(int pool_maxIdle) {
		this.pool_maxIdle=pool_maxIdle;
	}

	public int getPool_minIdle() {
		return pool_minIdle;
	}

	public void setPool_minIdle(int pool_minIdle) {
		this.pool_minIdle=pool_minIdle;
	}

	public int getPool_maxTotal() {
		return pool_maxTotal;
	}

	public void setPool_maxTotal(int pool_maxTotal) {
		this.pool_maxTotal=pool_maxTotal;
	}
}