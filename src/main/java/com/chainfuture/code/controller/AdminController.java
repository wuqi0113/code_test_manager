package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.SysMenu;
import com.chainfuture.code.bean.SysUserMenu;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.mapper.SysMenuMapper;
import com.chainfuture.code.mapper.SysUserMenuMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.wp.usermodel.Paragraph;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/")
public class AdminController {

   @Autowired
    private SysMenuMapper sysMenuMapper;
   @Autowired
    private SysUserMenuMapper sysUserMenuMapper;


    @RequestMapping("toMenuList")
    public ModelAndView toMenuList(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("see/admin/menuList");
        return mv;
    }
    @RequestMapping("toSaveMenu/{menuId}")
    public ModelAndView  toSaveMenu(@PathVariable("menuId") Integer menuId, Model model,
                                    HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        if(menuId!=null&&menuId!=1){
           SysMenu  menus = new SysMenu();
            menus.setMenuId(menuId);
            mv.addObject("menu",menus);
            mv.addObject("flag","1");
//            return "page/admin/menuForm";
        }else{
            model.addAttribute("msg","不允许操作！");
//            return "page/active";
        }

        mv.setViewName("see/admin/menuAdd");
        return mv;
    }
    @RequestMapping("menuList")
    @ResponseBody
    public JSONObject menuList(HttpServletRequest request, HttpServletResponse response){

        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "成功");
        List<SysMenu> list = null;
        int count=0;
        try{
            list = sysMenuMapper.selMenuList();
            res.put("data",list);
            count = sysMenuMapper.selMenuCount();
            res.put("count",count);
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }
    @ResponseBody
    @RequestMapping("getMenus")
    public List<SysMenu> getMenus(HttpServletRequest req, HttpServletResponse resp){
        List<SysMenu> results = new ArrayList<>();
        String openId = WwSystem.ToString(req.getParameter("openId"));
        if (StringUtils.isEmpty(openId)){
            return results;
        }
        List<SysMenu> menus = sysMenuMapper.getMenus(openId);
        for (int i = 0; i < menus.size(); i++) {
            if (menus.get(i).getParentId() == 0) {
                SysMenu menu = new SysMenu();
                menu.setMenuId(menus.get(i).getMenuId());
                menu.setParentId(menus.get(i).getParentId());
                menu.setTitle(menus.get(i).getTitle());
                menu.setHref(menus.get(i).getHref());
                menu.setSpread(menus.get(i).getSpread());
                List<SysMenu> menus2 = new ArrayList<>();
                for (int j = 0; j < menus.size(); j++) {
                    if (menus.get(j).getParentId() == menus.get(i).getMenuId()) {
                        SysMenu menu2 = new SysMenu();
                        menu2.setTitle(menus.get(j).getTitle());
                        menu2.setHref(menus.get(j).getHref());
                        menu2.setSpread(menus.get(j).getSpread());
                        menu2.setMenuId(menus.get(j).getMenuId());
                        menu2.setParentId(menus.get(j).getParentId());
                        menus2.add(menu2);
                    }
                }
                menu.setChildren(menus2);
                results.add(menu);
            }
        }
        return results;
    }


   /* public void addContent() throws IOException, DocumentException {
        String filePath = "C:\\Users\\fan\\Desktop\\测试模板变量_多页.pdf";
        String savePath = "C:\\Users\\fan\\Desktop\\测试模板变量6.pdf";

        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
        Font font = new Font(baseFont);

        PdfReader reader = new PdfReader(new FileInputStream(filePath));
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(savePath));


        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            PdfContentByte over = stamper.getOverContent(i);
            ColumnText columnText = new ColumnText(over);
            // llx 和 urx  最小的值决定离左边的距离. lly 和 ury 最大的值决定离下边的距离
            columnText.setSimpleColumn(272, 760, 350, 300);
            Paragraph elements = new Paragraph(0, new Chunk("我是甲方"));
            // 设置字体，如果不设置添加的中文将无法显示
            elements.setFont(font);
            columnText.addElement(elements);
            columnText.go();
        }
        stamper.close();
    }
*/



}
