package com.idohoo.front.service;

import com.idohoo.front.vo.inputVO.UserIdInputVO;
import com.idohoo.front.vo.inputVO.UserInsertInputVO;
import com.idohoo.front.vo.inputVO.UserUpdateInputVO;
import com.idohoo.front.vo.outVO.UserOutVO;
import com.idohoo.user.client.UserMSClient;
import com.idohoo.user.protocol.MSRequest;
import com.idohoo.user.protocol.MSResponse;
import com.idohoo.user.protocol.req.UserDeleteMsReq;
import com.idohoo.user.protocol.req.UserGetMsReq;
import com.idohoo.user.protocol.req.UserInsertMsReq;
import com.idohoo.user.protocol.req.UserUpdateMsReq;
import com.idohoo.user.protocol.resq.UserMsResq;
import com.idohoo.util.IntUtil;
import com.idohoo.x.XMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 第三方调用业务逻辑实现层
 */
@Service
@Transactional
public class FrontUserApiService {

    private static Logger logger = LoggerFactory.getLogger(FrontUserApiService.class);

    @Autowired
    private UserMSClient userMSClient;


    public void insert(UserInsertInputVO inputVO) {


        if(inputVO == null) {
            return;
        }

        UserInsertMsReq user = new UserInsertMsReq();

        user.setPhone(inputVO.getPhone());
        user.setPassword(inputVO.getPassword());
        user.setUser_name(inputVO.getUser_name());
        user.setNick_name(inputVO.getNick_name());
        user.setUser_level_id(inputVO.getUser_level_id());
        user.setEmail(inputVO.getEmail());

        MSRequest<UserInsertMsReq> msRequest = new MSRequest<UserInsertMsReq>(user);

        userMSClient.insert(msRequest);

    }

    public void update(UserUpdateInputVO inputVO) {


        if(inputVO == null) {
            return;
        }

        UserUpdateMsReq user = new UserUpdateMsReq();

        user.setUser_id(inputVO.getUser_id());
        user.setPhone(inputVO.getPhone());
        user.setPassword(inputVO.getPassword());
        user.setUser_name(inputVO.getUser_name());
        user.setNick_name(inputVO.getNick_name());
        user.setUser_level_id(inputVO.getUser_level_id());
        user.setEmail(inputVO.getEmail());

        MSRequest<UserUpdateMsReq> msRequest = new MSRequest<UserUpdateMsReq>(user);

        userMSClient.update(msRequest);

    }

    public UserOutVO get(UserIdInputVO inputVO) {

        if(inputVO == null
                || IntUtil.isZero(inputVO.getUser_id())) {
            return null;
        }

        UserGetMsReq userGetMsReq = new UserGetMsReq();

        userGetMsReq.setUser_id(inputVO.getUser_id());

        MSRequest<UserGetMsReq> msRequest = new MSRequest<UserGetMsReq>(userGetMsReq);

        MSResponse<UserMsResq> msResponse = userMSClient.get(msRequest);


        if(msResponse == null
                || msResponse.getCode() != XMsg.SUCCESS.getCode()) {
            return null;
        }

        UserMsResq userMsResq = msResponse.getData();

        if(userMsResq == null) {
            return null;
        }

        UserOutVO user = new UserOutVO();

        user.setEmail(userMsResq.getEmail());
        user.setPhone(userMsResq.getPhone());
        user.setUser_id(userMsResq.getUser_id());
        user.setPassword(userMsResq.getPassword());
        user.setUser_name(userMsResq.getUser_name());
        user.setNick_name(userMsResq.getNick_name());
        user.setUser_level_id(userMsResq.getUser_level_id());
        user.setCreate_time(userMsResq.getCreate_time());
        user.setStatus(userMsResq.getStatus());
        user.setStatus_time(userMsResq.getStatus_time());

        return user;

    }

    public void delete(UserIdInputVO inputVO) {

        if(inputVO == null
                || IntUtil.isZero(inputVO.getUser_id())) {
            return;
        }

        UserDeleteMsReq userDeleteMsReq = new UserDeleteMsReq();

        userDeleteMsReq.setUser_id(inputVO.getUser_id());

        MSRequest<UserDeleteMsReq> msRequest = new MSRequest<UserDeleteMsReq>(userDeleteMsReq);

        userMSClient.delete(msRequest);

    }

}