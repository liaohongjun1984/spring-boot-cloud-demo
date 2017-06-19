package com.idohoo.user.client;

import com.idohoo.user.protocol.MSRequest;
import com.idohoo.user.protocol.MSResponse;
import com.idohoo.user.protocol.UserMicroServiceProperty;

import com.idohoo.user.protocol.req.UserDeleteMsReq;
import com.idohoo.user.protocol.req.UserGetMsReq;
import com.idohoo.user.protocol.req.UserInsertMsReq;
import com.idohoo.user.protocol.req.UserUpdateMsReq;
import com.idohoo.user.protocol.resq.UserMsResq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = UserMicroServiceProperty.SERVICE_NAME, fallback = UserClientHystrix.class)
@Component
public interface UserMSClient {
    /**
     * 用户新增
     * @param msRequest
     * @return
     */
    @RequestMapping(value = UserInsertMsReq.URI, method = RequestMethod.POST)
    public MSResponse insert(@RequestBody MSRequest<UserInsertMsReq> msRequest);
    
    /**
     * 用户修改
     * @param msRequest
     * @return
     */
    @RequestMapping(value = UserUpdateMsReq.URI, method = RequestMethod.POST)
    public MSResponse update(@RequestBody MSRequest<UserUpdateMsReq> msRequest);

    /**
     * 用户查询
     * @param msRequest
     * @return
     */
    @RequestMapping(value = UserGetMsReq.URI, method = RequestMethod.POST)
    public MSResponse<UserMsResq> get(@RequestBody MSRequest<UserGetMsReq> msRequest);
    
    /**
     * 用户删除
     * @param msRequest
     * @return
     */
    @RequestMapping(value = UserDeleteMsReq.URI, method = RequestMethod.POST)
    public MSResponse delete(@RequestBody MSRequest<UserDeleteMsReq> msRequest);

}