package com.idohoo.user.controller;

import com.idohoo.user.protocol.req.UserDeleteMsReq;
import com.idohoo.user.protocol.req.UserGetMsReq;
import com.idohoo.user.protocol.req.UserInsertMsReq;
import com.idohoo.user.protocol.req.UserUpdateMsReq;
import com.idohoo.user.protocol.resq.UserMsResq;
import com.idohoo.user.service.UserService;
import com.idohoo.user.protocol.MSRequest;
import com.idohoo.user.protocol.MSResponse;
import com.idohoo.x.XMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


	@RequestMapping(value="/ms/user/insert.do", method = RequestMethod.POST)
	public MSResponse insert(@RequestBody MSRequest<UserInsertMsReq> msReq){

	    MSResponse msResponse = MSResponse.getDefaultResponse();

	    if(msReq == null
                || msReq.getData() == null) {

            msResponse.setCode(XMsg.PARAM_ERROR.getCode()).setMsg(XMsg.PARAM_ERROR.getMessage());

            return msResponse;
        }

        UserInsertMsReq userInsertMsReq = msReq.getData();

        //业务逻辑实现
        userService.insert(userInsertMsReq);

        msResponse.setMsg(XMsg.SUCCESS.getMessage());

        return msResponse;
	}

    @RequestMapping(value = "/ms/user/update.do", method = RequestMethod.POST)
    public MSResponse update(@RequestBody MSRequest<UserUpdateMsReq> msReq){

        MSResponse msResponse = MSResponse.getDefaultResponse();

        if(msReq == null
                || msReq.getData() == null) {

            msResponse.setCode(XMsg.PARAM_ERROR.getCode()).setMsg(XMsg.PARAM_ERROR.getMessage());

            return msResponse;
        }

        UserUpdateMsReq userUpdateMsReq = msReq.getData();

        //业务逻辑实现
        userService.update(userUpdateMsReq);

        msResponse.setMsg(XMsg.SUCCESS.getMessage());

        return msResponse;
    }
    

    @RequestMapping(value="/ms/user/get.do", method = RequestMethod.POST)
    public MSResponse<UserMsResq> get(@RequestBody MSRequest<UserGetMsReq> msReq){

        MSResponse msResponse = MSResponse.getDefaultResponse();

        if(msReq == null
                || msReq.getData() == null) {

            msResponse.setCode(XMsg.PARAM_ERROR.getCode()).setMsg(XMsg.PARAM_ERROR.getMessage());

            return msResponse;
        }

        UserGetMsReq userGetMsReq = msReq.getData();

        //业务逻辑实现
        UserMsResq userMsResq = userService.get(userGetMsReq);

        msResponse.setData(userMsResq);
        msResponse.setMsg(XMsg.SUCCESS.getMessage());

        return msResponse;
    }
    

    @RequestMapping(value="/ms/user/delete.do", method=RequestMethod.POST)
    public MSResponse delete(@RequestBody MSRequest<UserDeleteMsReq> msReq){

        MSResponse msResponse = MSResponse.getDefaultResponse();

        if(msReq == null
                || msReq.getData() == null) {

            msResponse.setCode(XMsg.PARAM_ERROR.getCode()).setMsg(XMsg.PARAM_ERROR.getMessage());

            return msResponse;
        }

        UserDeleteMsReq userDeleteMsReq = msReq.getData();

        //业务逻辑实现
        userService.delete(userDeleteMsReq);

        msResponse.setMsg(XMsg.SUCCESS.getMessage());
        return msResponse;
    }
	
}