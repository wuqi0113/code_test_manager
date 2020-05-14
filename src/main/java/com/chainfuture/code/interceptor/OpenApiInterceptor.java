package com.chainfuture.code.interceptor;

import com.chainfuture.code.common.FileUploadUtil;
import com.chainfuture.code.filter.OpenApiFilter;
import com.chainfuture.code.filter.OpenApiLoginContext;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by admin on 2019/6/28.
 */
public class OpenApiInterceptor implements HandlerInterceptor {
    public static final Logger LOGGER = Logger.getLogger(OpenApiInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        HttpSession session = request.getSession();
        String uri = request.getRequestURI(); // 获取登录的uri，这个是不进行拦截的
        if(uri.indexOf("backapi/")!=-1){
            return true;
        }
        session.setAttribute("ww_raw_url", uri);
        String token=request.getParameter("token");
        if(token==null){
            String content_type=request.getContentType();
            if(FileUploadUtil.isMultipartContent(request)){
                return true;
            }
        }
        if(token==null || token.isEmpty() || OpenApiLoginContext.getUser(token)==null){//未登陆
            response.addHeader("Access-Control-Allow-Origin", "*"); //本次回复允许跨域
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"success\":false,\"code\":101,\"msg\":\"已过期请重新登录！\"}");
            return false;
        }else if(!token.equals(OpenApiLoginContext.getToken(OpenApiLoginContext.getUser(token).getAddress()))){//未登陆
            response.addHeader("Access-Control-Allow-Origin", "*"); //本次回复允许跨域
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"success\":false,\"code\":101,\"msg\":\"已过期请重新登录。\"}");
            return false;
        }else{//已登陆
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
