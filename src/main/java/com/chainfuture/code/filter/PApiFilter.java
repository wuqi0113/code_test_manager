package com.chainfuture.code.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by admin on 2019/9/23.
 */
public class PApiFilter implements Filter {
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse res=(HttpServletResponse)response;
        //HttpSession session=req.getSession();

        //访问验证
        AccessVerify av=new AccessVerify();
        String result=av.verify(req);
        if(result.equals("ok")){
            chain.doFilter(req, res);
        }else{
            //res.addHeader("Access-Control-Allow-Origin", "*"); //本次回复允许跨域
            response.setContentType("application/json;charset=utf-8");//("text/html;charset=utf-8");("application/json;charset=utf-8")
            response.getWriter().write("{\"success\":false,\"code\":1001,\"message\":\"非法调用\"}");
        }

    }


    public void init(FilterConfig config) throws ServletException {

    }

}
