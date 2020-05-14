package com.chainfuture.code.filter;

import com.chainfuture.code.bean.SysMenu;
import com.chainfuture.code.mapper.SysMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 2018/12/10.
 */
public class LoginFilter implements Filter {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();

        String URI=request.getRequestURI();
        session.setAttribute("ww_raw_url", URI);


        if(URI.indexOf("/login")>=0||URI.indexOf("/dologin")>=0){
            filterChain.doFilter(request, response);
            return;
        }
        String openId = request.getParameter("openId");
        if (openId!=null && openId.length()>0){
            filterChain.doFilter(servletRequest, servletResponse);
            return ;
        }
        if(session.getAttribute("admin")==null
                && URI.indexOf("/login/dologin") == -1){
            // 没有登录
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }else{
            // 已经登录，继续请求下一级资源（继续访问）
            String admin = session.getAttribute("admin").toString();
            List<SysMenu> menus = sysMenuMapper.getMenus(admin);
            for (int i = 0; i < menus.size(); i++) {
                if ( URI.indexOf(menus.get(i).getHref())!=-1) {
//                    session.setAttribute("username", admin);
//                    request.getSession().setAttribute("username",openId);
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
            response.sendRedirect(request.getContextPath()+"/refuse.jsp");
        }
    }

    @Override
    public void destroy() {

    }
}
