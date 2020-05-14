package com.chainfuture.code.filter;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.*;
import com.chainfuture.code.common.FileUploadUtil;
import com.chainfuture.code.controller.ApiController;
import com.chainfuture.code.mapper.SysCarteMapper;
import com.chainfuture.code.service.SysManageService;
import com.chainfuture.code.service.SysModuleService;
import com.chainfuture.code.servlet.AccessVerify;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class BackApiFilter implements Filter {
    @Autowired
    private SysManageService sysManageService;
    @Autowired
    private SysCarteMapper sysCarteMapper;
    @Autowired
    private SysModuleService sysModuleService;
    public static final Logger LOGGER = Logger.getLogger(BackApiFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,      filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)servletRequest;
        HttpServletResponse res=(HttpServletResponse)servletResponse;
        HttpSession session=req.getSession();
        String URI=req.getRequestURI();
        session.setAttribute("ww_raw_url", URI);
        String servletPath = req.getServletPath();

        /*String contextPath = req.getContextPath();
        LOGGER.info("contextPath:"+contextPath);
        String requestURI = req.getRequestURI();
        LOGGER.info("requestURI:"+requestURI);
        StringBuffer requestURL = req.getRequestURL();
        LOGGER.info("requestURL:"+requestURL);
        String servletPath = req.getServletPath();
        LOGGER.info("servletPath:"+servletPath);
        String realPath = req.getSession().getServletContext().getRealPath(req.getRequestURI());
        LOGGER.info("realPath:"+realPath);
        String realPath1 = req.getSession().getServletContext().getRealPath("/");
        LOGGER.info("realPath1:"+realPath1);*/

        if(URI.indexOf("/backapi/admin/")>=0){
            filterChain.doFilter(req, res);
            return;
        }

        String token=req.getParameter("backLogin");
        if(token==null){
            String content_type=req.getContentType();
            if(FileUploadUtil.isMultipartContent(req)){
                if (servletPath.equals("/backapi/vipadmin/getRolesList") || servletPath.equals("/backapi/vipadmin/getUserAuth")){
                    filterChain.doFilter(req, res);
                    return;
                }
                boolean valid = Valid(token, servletPath, req, res);
                if (!valid){
                    res.addHeader("Access-Control-Allow-Origin", "*"); //本次回复允许跨域
                    servletResponse.setContentType("application/json;charset=utf-8");
                    servletResponse.getWriter().write("{\"success\":false,\"code\":102,\"msg\":\"无权访问。\"}");
                    return;
                }
                filterChain.doFilter(req, res);
                return;
            }
        }
        if(token==null || token.isEmpty() || BackApiLoginContext.getUser(token)==null){//未登陆
            res.addHeader("Access-Control-Allow-Origin", "*"); //本次回复允许跨域
            servletResponse.setContentType("application/json;charset=utf-8");
            servletResponse.getWriter().write("{\"success\":false,\"code\":101,\"msg\":\"已过期请重新登录！\"}");
        }else if(!token.equals(BackApiLoginContext.getToken(BackApiLoginContext.getUser(token).getAddress()))){//未登陆
            res.addHeader("Access-Control-Allow-Origin", "*"); //本次回复允许跨域
            servletResponse.setContentType("application/json;charset=utf-8");
            servletResponse.getWriter().write("{\"success\":false,\"code\":101,\"msg\":\"已过期请重新登录。\"}");
        }else{//已登陆
            if (servletPath.equals("/backapi/vipadmin/getRolesList") || servletPath.equals("/backapi/vipadmin/getUserAuth")){
                filterChain.doFilter(req, res);
                return;
            }
            boolean valid = Valid(token, servletPath, req, res);
            if (!valid){
                res.addHeader("Access-Control-Allow-Origin", "*"); //本次回复允许跨域
                servletResponse.setContentType("application/json;charset=utf-8");
                servletResponse.getWriter().write("{\"success\":false,\"code\":102,\"msg\":\"无权访问。\"}");
                return;
            }
            filterChain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }

    public  boolean  Valid(String token,String uri,HttpServletRequest request, HttpServletResponse response){
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            return false;
        }
        int i = uri.lastIndexOf("/");
        String perms = uri.substring(0, i);
        String address = user.getAddress();
        SysModule sysModule = sysModuleService.selSysModuleByPerms(perms);
        if (sysModule==null){
            return false;
        }
        SysManage  sysManage = new SysManage();
        sysManage.setAddress(address);
        sysManage.setManageAddr(sysModule.getAddress());
        SysManage  sm = sysManageService.selSysManagerByUserAndModule(sysManage);
        if (sm==null){
            SysCarteExample  sysCarteExample = sysCarteMapper.selSysCarte(uri);
            if (sysCarteExample==null){
                return false;
            }
        }
        /*List<SysManage> list =sysManageService.selSysManageListByAddr(address);
        LOGGER.info("管理权限："+list);
        if (list.size()<=0){
            List<SysCarteExample> list1 = sysCarteMapper.getSysCartes(address);
            LOGGER.info("菜单列表："+list1);
            if (list1.size()<=0){
                return false;
            }else {
                for (int j=0;j<list1.size();j++){
                    if (uri.contains(list1.get(j).getHref())){
                        return true;
                    }
                }
            }
        }else {
            for (int i=0;i<list.size();i++){
                SysModule sysModule = sysModuleService.moduleInfo(list.get(i).getManageAddr());
                LOGGER.info("模块权限："+sysModule);
                if (uri.contains(sysModule.getPerms())){
                    return true;
                }
            }
        }*/
        return true;
    }

}
