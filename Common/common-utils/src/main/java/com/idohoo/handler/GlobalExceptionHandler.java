package com.idohoo.handler;

import com.idohoo.exception.GJJFException;
import com.idohoo.exception.RemoteServiceException;
import com.idohoo.msg.ExceptionMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger("GlobalExceptionHandler");

    @ExceptionHandler(value = RuntimeException.class)
    public Map<String,Object> baseErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        logger.error("--- RuntimeException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());

        Map<String,Object> result = new HashMap<String, Object>();

        int code = ExceptionMsg.INTERNAL_ERROR.getCode();
        String msg = e.getMessage();

        if (e instanceof GJJFException) {
            code = ((GJJFException) e).getCode();
        }

        result.put("code",code);
        result.put("message",msg);

        logger.error("--- 系统异常处理后返回参数 result: {}", result.toString());

        return result;
    }

    @ExceptionHandler(value = Exception.class)
    public Map<String,Object> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        logger.error("--- Exception Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());

        Map<String,Object> result = new HashMap<String, Object>();

        int code = ExceptionMsg.INTERNAL_ERROR.getCode();
        String msg = e.getMessage();

        if (e instanceof RemoteServiceException) {
            code = ((RemoteServiceException) e).getCode();
        }

        result.put("code",code);
        result.put("message",msg);

        logger.error("--- 业务异常处理后返回参数 result: {}", result.toString());

        return result;

    }
}
