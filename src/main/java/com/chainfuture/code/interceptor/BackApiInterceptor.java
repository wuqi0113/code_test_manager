package com.chainfuture.code.interceptor;

import com.chainfuture.code.common.FileUploadUtil;
import com.chainfuture.code.filter.BackApiFilter;
import com.chainfuture.code.filter.BackApiLoginContext;
import com.chainfuture.code.filter.BackApiValid;
import com.chainfuture.code.filter.OpenApiLoginContext;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BackApiInterceptor implements HandlerInterceptor {
    public static final Logger LOGGER = Logger.getLogger(BackApiInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String URI=request.getRequestURI();
        String token=request.getParameter("backLogin");
//        LOGGER.info("BackApiInterceptor："+token);
        if(token==null){
            String content_type=request.getContentType();
            if(FileUploadUtil.isMultipartContent(request)){
//                LOGGER.info("ContentType:"+content_type);
                return true;
            }
        }
        if(token==null || token.isEmpty() || BackApiLoginContext.getUser(token)==null){//未登陆
            response.addHeader("Access-Control-Allow-Origin", "*"); //本次回复允许跨域
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"success\":false,\"code\":101,\"msg\":\"已过期请重新登录！\"}");
            return false;
        }else if(!token.equals(BackApiLoginContext.getToken(BackApiLoginContext.getUser(token).getAddress()))){//未登陆
            response.addHeader("Access-Control-Allow-Origin", "*"); //本次回复允许跨域
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"success\":false,\"code\":101,\"msg\":\"已过期请重新登录。\"}");
            return false;
        }else{//已登陆
           /* String servletPath = request.getServletPath();
            BackApiFilter baf = new BackApiFilter();
            boolean valid = baf.Valid(token, servletPath, request, response);
            LOGGER.info("权限验证:"+valid);
            if (!valid){
                LOGGER.info("应该进这里啊:"+valid);
                response.addHeader("Access-Control-Allow-Origin", "*"); //本次回复允许跨域
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write("{\"success\":false,\"code\":102,\"msg\":\"无权访问。\"}");
                return false;
            }
            LOGGER.info("还是不能进这里啊:"+valid);*/
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
