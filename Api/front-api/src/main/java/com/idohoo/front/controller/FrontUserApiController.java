package com.idohoo.front.controller;

import com.idohoo.front.service.FrontUserApiService;
import com.idohoo.front.vo.inputVO.UserIdInputVO;
import com.idohoo.front.vo.inputVO.UserInsertInputVO;
import com.idohoo.front.vo.inputVO.UserUpdateInputVO;
import com.idohoo.front.vo.outVO.UserOutVO;
import com.idohoo.user.protocol.ApiResponse;
import com.idohoo.x.XMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class FrontUserApiController {

    private Logger logger =LoggerFactory.getLogger(FrontUserApiController.class);

    @Autowired
	private FrontUserApiService frontUserApiService;

    @RequestMapping(value = "/front/api/user/insert.do", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ApiResponse insert(UserInsertInputVO inputVO) {

		ApiResponse apiResponse = ApiResponse.getDefaultResponse().setMsg(XMsg.SUCCESS.getMessage());

    	if(inputVO == null) {
    		return apiResponse.setCode(XMsg.PARAM_ERROR.getCode()).setMsg(XMsg.PARAM_ERROR.getMessage());
		}

		//业务处理
		frontUserApiService.insert(inputVO);

		return apiResponse;

    }

	@RequestMapping(value = "/front/api/user/update.do", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public ApiResponse update(UserUpdateInputVO inputVO) {

		ApiResponse apiResponse = ApiResponse.getDefaultResponse().setMsg(XMsg.SUCCESS.getMessage());

		if(inputVO == null) {
			return apiResponse.setCode(XMsg.PARAM_ERROR.getCode()).setMsg(XMsg.PARAM_ERROR.getMessage());
		}

		//业务处理
		frontUserApiService.update(inputVO);

		return apiResponse;

	}

	@RequestMapping(value = "/front/api/user/get.do",produces="application/json;charset=UTF-8")
	public ApiResponse get(UserIdInputVO inputVO) {

		ApiResponse apiResponse = ApiResponse.getDefaultResponse().setMsg(XMsg.SUCCESS.getMessage());

		if(inputVO == null) {
			return apiResponse.setCode(XMsg.PARAM_ERROR.getCode()).setMsg(XMsg.PARAM_ERROR.getMessage());
		}

		//业务处理
		UserOutVO userOutVO = frontUserApiService.get(inputVO);

		if(userOutVO == null) {
			apiResponse.setCode(XMsg.DATA_NOT_EXIST.getCode()).setMsg(XMsg.DATA_NOT_EXIST.getMessage());

			return apiResponse;
		}

		apiResponse.setData(userOutVO);

		return apiResponse;

	}

	@RequestMapping(value = "/front/api/user/delete.do", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	public ApiResponse delete(UserIdInputVO inputVO) {

		ApiResponse apiResponse = ApiResponse.getDefaultResponse().setMsg(XMsg.SUCCESS.getMessage());

		if(inputVO == null) {
			return apiResponse.setCode(XMsg.PARAM_ERROR.getCode()).setMsg(XMsg.PARAM_ERROR.getMessage());
		}

		//业务处理
		frontUserApiService.delete(inputVO);

		return apiResponse;

	}

}