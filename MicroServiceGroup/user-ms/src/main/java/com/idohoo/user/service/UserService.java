package com.idohoo.user.service;

import com.idohoo.user.constants.StatusConstants;
import com.idohoo.user.dao.autocode.UserMapper;
import com.idohoo.user.model.User;
import com.idohoo.user.model.UserExample;

import com.idohoo.user.protocol.req.UserDeleteMsReq;
import com.idohoo.user.protocol.req.UserGetMsReq;
import com.idohoo.user.protocol.req.UserInsertMsReq;
import com.idohoo.user.protocol.req.UserUpdateMsReq;
import com.idohoo.user.protocol.resq.UserMsResq;
import com.idohoo.user.redis.UserRedisDao;
import com.idohoo.util.IntUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
	private UserRedisDao userRedisDao;

    public void insert(UserInsertMsReq msReq) {

    	if(msReq == null) {
    		return;
		}

		User user = new User();

		user.setEmail(msReq.getEmail());
		user.setPhone(msReq.getPhone());
		user.setPassword(msReq.getPassword());
		user.setUser_name(msReq.getUser_name());
		user.setNick_name(msReq.getNick_name());
		user.setUser_level_id(msReq.getUser_level_id());
		user.setCreate_time(new Date());
		user.setStatus(StatusConstants.IS_NOT_DEL);
		user.setStatus_time(new Date());

		userMapper.insertSelective(user);

		userRedisDao.saveUser(user);

    }

	public void update(UserUpdateMsReq msReq) {

		if(msReq == null) {
			return;
		}

		User user = new User();

		user.setUser_id(msReq.getUser_id());
		user.setEmail(msReq.getEmail());
		user.setPhone(msReq.getPhone());
		user.setPassword(msReq.getPassword());
		user.setUser_name(msReq.getUser_name());
		user.setNick_name(msReq.getNick_name());
		user.setUser_level_id(msReq.getUser_level_id());

		UserExample example = new UserExample();
		example.createCriteria()
				.andUser_idEqualTo(msReq.getUser_id());

		userMapper.updateByExampleSelective(user,example);

		userRedisDao.saveUser(user);

	}

    public UserMsResq get(UserGetMsReq msReq) {

    	if (msReq == null
				|| IntUtil.isZero(msReq.getUser_id())) {
    		return null;
		}

		User user = userRedisDao.getUser(msReq.getUser_id());

    	if (user == null) {
			user = userMapper.selectByPrimaryKey(msReq.getUser_id());
			if(user.getStatus().equals(StatusConstants.IS_DEL)) {
				return null;
			}
		}

    	if(user == null) {
    		return null;
		}

		UserMsResq userMsResq = new UserMsResq();

		userMsResq.setUser_id(user.getUser_id());
		userMsResq.setPhone(user.getPhone());
		userMsResq.setPassword(user.getPassword());
		userMsResq.setUser_name(user.getUser_name());
		userMsResq.setNick_name(user.getNick_name());
		userMsResq.setUser_level_id(user.getUser_level_id());
		userMsResq.setEmail(user.getEmail());
		userMsResq.setCreate_time(user.getCreate_time());
		userMsResq.setStatus(user.getStatus());
		userMsResq.setStatus_time(user.getStatus_time());

    	return  userMsResq;

    }

    public void delete(UserDeleteMsReq msReq) {

		if (msReq == null
				|| IntUtil.isZero(msReq.getUser_id())) {
			return;
		}

		UserExample example = new UserExample();
		example.createCriteria()
				.andUser_idEqualTo(msReq.getUser_id());

		User user = new User();
		user.setStatus(StatusConstants.IS_DEL);
		user.setStatus_time(new Date());

		userMapper.updateByExampleSelective(user,example);

		userRedisDao.delUser(msReq.getUser_id());

	}


	
}