package com.chainfuture.code.filter;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.common.FileUploadUtil;
import com.chainfuture.code.servlet.AccessVerify;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OpenApiFilter implements Filter {
    public static final Logger LOGGER = Logger.getLogger(OpenApiFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)servletRequest;
        HttpServletResponse res=(HttpServletResponse)servletResponse;
        HttpSession session=req.getSession();
        String URI=req.getRequestURI();
        session.setAttribute("ww_raw_url", URI);


        if(URI.indexOf("/openapi/novalid/")>=0){
            filterChain.doFilter(req, res);
            return;
        }

        //访问验证
        AccessVerify av=new AccessVerify();
        JSONObject verify = av.verify(req);
        if((boolean) verify.get("success")){
//            filterChain.doFilter(servletRequest, servletResponse);
            filterChain.doFilter(req, res);
            return ;
        }
        String token=req.getParameter("token");
        if(token==null){
            String content_type=req.getContentType();
            if(FileUploadUtil.isMultipartContent(req)){
//                filterChain.doFilter(servletRequest, servletResponse);
                filterChain.doFilter(req, res);
                return;
            }
        }
        if(token==null || token.isEmpty() || OpenApiLoginContext.getUser(token)==null){//未登陆
            res.addHeader("Access-Control-Allow-Origin", "*"); //本次回复允许跨域
            servletResponse.setContentType("application/json;charset=utf-8");
            servletResponse.getWriter().write("{\"success\":false,\"code\":101,\"msg\":\"已过期请重新登录！\"}");
        }else if(!token.equals(OpenApiLoginContext.getToken(OpenApiLoginContext.getUser(token).getAddress()))){//未登陆
            res.addHeader("Access-Control-Allow-Origin", "*"); //本次回复允许跨域
            servletResponse.setContentType("application/json;charset=utf-8");
            servletResponse.getWriter().write("{\"success\":false,\"code\":101,\"msg\":\"已过期请重新登录。\"}");
        }else{//已登陆
//            filterChain.doFilter(servletRequest, servletResponse);
            filterChain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
