package com.chainfuture.code.interceptor;

import com.chainfuture.code.bean.SysManage;
import com.chainfuture.code.bean.SysMenu;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.filter.OpenApiLoginContext;
import com.chainfuture.code.mapper.SysManageMapper;
import com.chainfuture.code.mapper.SysMenuMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.omg.PortableInterceptor.LOCATION_FORWARD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


public class LoginInterceptor implements HandlerInterceptor{
    public static final Logger LOGGER = Logger.getLogger(OpenApiInterceptor.class);
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysManageMapper sysManageMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在拦截点执行前拦截，如果返回true则不执行拦截点后的操作（拦截成功）
        // 返回false则不执行拦截
        HttpSession session = request.getSession();
        String uri = request.getRequestURI(); // 获取登录的uri，这个是不进行拦截的
        if (uri.indexOf("login/dologin")!=-1){
            return true;
        }
        if(uri.indexOf("openapi/")!=-1){
            return true;
        }
        if(uri.indexOf("papi/")!=-1){
            return true;
        }
        if(uri.indexOf("backapi/")!=-1){
            return true;
        }
        session.setAttribute("ww_raw_url", uri);
        if(session.getAttribute("admin")!=null || uri.indexOf("login/dologin")!=-1) {// 说明登录成功 或者 执行登录功能
            // 登录成功  检测权限
          /*  NrcMain nm =new NrcMain();
            nm.initRPC();
            String primeaddr = nm.getPrimeaddr();
            session.setAttribute("primeaddr",primeaddr);
            SysManage sysManage = sysManageMapper.selSysManageByManageAddr(primeaddr);
            if (sysManage==null){
                response.sendRedirect(request.getContextPath()+"/refuse.jsp");
                return false;
            }*/
            String admin = session.getAttribute("admin").toString();
            List<SysMenu> menus = sysMenuMapper.getMenus(admin);
            for (int i = 0; i < menus.size(); i++) {
                String href = menus.get(i).getHref();
                if (StringUtils.isEmpty(href)){
                    continue;
                }
                if (uri.indexOf(href)!=-1) {
                    return true;
                }
            }
            response.sendRedirect(request.getContextPath()+"/refuse.jsp");
            return false;
        }else {
            // 拦截后进入登录页面
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
