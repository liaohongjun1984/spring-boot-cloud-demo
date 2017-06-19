package com.idohoo.user.redis;

import com.idohoo.redis.base.RedisBaseDao;
import com.idohoo.user.model.User;
import com.idohoo.util.IntUtil;
import com.idohoo.util.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class UserRedisDao  extends RedisBaseDao {

	private Logger logger = LoggerFactory.getLogger(UserRedisDao.class);
	
	//会员的key %s 为用户ID
	private final static String USER_KEY = "USER_KEY_%s";


	public void saveUser(User user) {

		if(user == null){
			return;
		}

		Jedis jedis = null;
		try {
			jedis 			= getJedis();
			String key  	= String.format(USER_KEY, user.getUser_id());
			
			String value = JSONUtils.toJsonString(user);
			
			logger.info("缓存会员数据的 key ：{}，value: {}",key,value);
			
			jedis.set(key, value);
		} catch (Exception e) {
			returnBroken(jedis);
			logger.error("------ RedisDaoException: {}", e);
		} finally {
			release(jedis);
		}
	}


	public User getUser(Integer user_id) {

		if(IntUtil.isZero(user_id)){
			return null;
		}

		Jedis jedis = null;
		try {
			jedis = getJedis();
			String key   = String.format(USER_KEY,user_id);
			String value = jedis.get(key);

			//不存在时返回空
			if(value == null){
				return null;
			}
			
			User user = JSONUtils.toObject(value, User.class);
			
			return user;
		} catch (Exception e) {
			returnBroken(jedis);
			logger.error("------ RedisDaoException: {}", e);
		} finally {
			release(jedis);
		}
		return null;
	}

	public void delUser(Integer user_id) {

		if(IntUtil.isZero(user_id)){
			return;
		}

		Jedis jedis = null;
		try {
			jedis = getJedis();
			String key   = String.format(USER_KEY,user_id);

			jedis.del(key);
		} catch (Exception e) {
			returnBroken(jedis);
			logger.error("------ RedisDaoException: {}", e);
		} finally {
			release(jedis);
		}
	}

}
