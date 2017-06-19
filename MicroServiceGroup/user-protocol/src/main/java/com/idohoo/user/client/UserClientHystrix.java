package com.idohoo.user.client;

import com.idohoo.user.protocol.MSRequest;
import com.idohoo.user.protocol.MSResponse;
import com.idohoo.x.XMsg;
import com.idohoo.user.protocol.req.UserDeleteMsReq;
import com.idohoo.user.protocol.req.UserGetMsReq;
import com.idohoo.user.protocol.req.UserInsertMsReq;
import com.idohoo.user.protocol.req.UserUpdateMsReq;
import com.idohoo.user.protocol.resq.UserMsResq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class UserClientHystrix implements UserMSClient{

    private final static Logger logger = LoggerFactory.getLogger(UserClientHystrix.class);

	public MSResponse insert(MSRequest<UserInsertMsReq> msRequest) {
		return MSResponse.getResponse(XMsg.NETWORK_ERROR).setMsg("用户微服务访问出错！");
	}

	public MSResponse update(MSRequest<UserUpdateMsReq> msRequest) {
		return MSResponse.getResponse(XMsg.NETWORK_ERROR).setMsg("用户微服务访问出错！");
	}

	public MSResponse<UserMsResq> get(MSRequest<UserGetMsReq> msRequest) {
		return MSResponse.getResponse(XMsg.NETWORK_ERROR).setMsg("用户微服务访问出错！");
	}

	public MSResponse delete(MSRequest<UserDeleteMsReq> msRequest) {
		return MSResponse.getResponse(XMsg.NETWORK_ERROR).setMsg("用户微服务访问出错！");
	}

}