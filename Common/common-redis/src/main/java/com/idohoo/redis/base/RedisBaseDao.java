package com.idohoo.redis.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Description redis操作基类
 */
public abstract class RedisBaseDao
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JedisPool jedisPool;

    /**
     * @Description 获取jedis对象
     * @date 2016年7月7日 上午9:20:06
     * @return 不存在则返回null，存在返回jedis对象
     * @lastModifier
     */
    public Jedis getJedis()
    {
        if (jedisPool != null)
        {
            return jedisPool.getResource();
        }
        return null;
    }

    /**
     * @Description 将jedis对象释放回连接池中
     * @param jedis 使用完毕的Jedis对象
     * @return true 释放成功；否则返回false
     */
    public boolean release(Jedis jedis)
    {
        if (jedisPool != null && jedis != null)
        {
            jedisPool.returnResource(jedis);
            return true;
        }
        return false;
    }

    /**
     * @Description 释放jedis对象。如果Jedis在使用过程中出错，则也需要调用returnBrokenResource方法将jedis还给JedisPool。
     *                        否则下次通过getResource得到的instance的缓冲区可能还存在数据，出现问题！请在catch中调用该方法
     * @author bm
     * @date 2016年7月7日 上午9:26:04
     * @param jedis
     * @return 释放成功返回true，否则返回false
     * @lastModifier
     */
    public boolean returnBroken(Jedis jedis)
    {
        if (jedisPool != null && jedis != null)
        {
            jedisPool.returnBrokenResource(jedis);
            return true;
        }
        return false;
    }

}
