package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.Asset;
import com.chainfuture.code.bean.HomePage;
import com.chainfuture.code.bean.Product;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.service.AssetService;
import com.chainfuture.code.service.HomePageService;
import com.chainfuture.code.service.ProductService;
import com.chainfuture.code.utils.ApiUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


@Slf4j
@Controller
@RequestMapping("/homepage/")
public class HomePageController {

    @Autowired
    private HomePageService homePageService;
    @Autowired
    private AssetService assetService;
    @Autowired
    private ProductService productService;
    //引入apache的log4j日志包
    public static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ApiController.class);

    @RequestMapping("toHomePage")
    @ResponseBody
    public ModelAndView  toHomePage(HttpServletRequest request, HttpServletResponse response){
        ModelAndView  mv = new ModelAndView();
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        try{
            Map<String,String> map =  new HashMap<>();
            String params = "";
            map.put("assetName","DEMO");
            params+="&assetName=DEMO";
            String sign = ApiUtil.getSign(map);
            System.out.println("sign："+sign);
            String url = "api/getHome";
            params += "&signCode="+sign;
            String s="";
            if (params.trim().startsWith("&")){
                s = params.replaceFirst("&", "");
            }
            String s1 = ApiUtil.sendPost(url, s);
            JSONObject jsonObject = JSON.parseObject(s1);
            mv.addObject("data",jsonObject.get("result"));
        }catch (Exception e){
            mv.addObject("code",1);
            mv.addObject("msg","失败");
            return mv;
        }
        mv.setViewName("see/admin/setPage");
        return mv;
    }
    @RequestMapping("savePage")
    @ResponseBody
    public JSONObject savePage(HomePage homePage,HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        try{
            Map<String,String> map =  new HashMap<>();
            String params = "";
            String fullRoot = WwSystem.getFullRoot(request);
            String headImg = homePage.getHeadImg();
            if (!StringUtils.isEmpty(headImg)){
                headImg=fullRoot+"public/file_list/images/"+headImg;
                map.put("headImg",headImg);
                params +="&headImg="+headImg;
                homePage.setHeadImg(headImg);
            }
            String assetName = homePage.getAssetName();
            if (!StringUtils.isEmpty(assetName)){
                map.put("assetName",assetName);
                params +="&assetName="+assetName;
                homePage.setAssetName(assetName);
            }
            String companyIntroduce = homePage.getCompanyIntroduce();
            if (!StringUtils.isEmpty(companyIntroduce)){
                String s = companyIntroduce.replaceAll("src=\"public", "src=\"" + fullRoot + "public");
                map.put("companyIntroduce",s);
                params +="&companyIntroduce="+s;
                homePage.setCompanyIntroduce(s);
            }
            String contactInfo = homePage.getContactInfo();
            if (!StringUtils.isEmpty(contactInfo)){
                map.put("contactInfo",contactInfo);
                params +="&contactInfo="+contactInfo;
                homePage.setContactInfo(contactInfo);
            }
            String phone = homePage.getPhone();
            if (!StringUtils.isEmpty(phone)){
                map.put("phone",phone);
                params +="&phone="+phone;
                homePage.setPhone(phone);
            }
            String companyAddress = homePage.getCompanyAddress();
            if (!StringUtils.isEmpty(companyAddress)){
                map.put("companyAddress",companyAddress);
                params +="&companyAddress="+companyAddress;
                homePage.setCompanyAddress(companyAddress);
            }
            String website = homePage.getWebsite();
            if (!StringUtils.isEmpty(website)){
                map.put("website",website);
                params +="&website="+website;
                homePage.setWebsite(website);
            }
            String email = homePage.getEmail();
            if (!StringUtils.isEmpty(email)){
                map.put("email",email);
                params +="&email="+email;
                homePage.setEmail(email);
            }
            String companyPublic = homePage.getCompanyPublic();
            if (!StringUtils.isEmpty(companyPublic)){
                companyPublic=fullRoot+"public/file_list/images/"+companyPublic;
                map.put("companyPublic",companyPublic);
                params +="&companyPublic="+companyPublic;
                homePage.setCompanyPublic(companyPublic);
            }
            String colorPickerHolder = homePage.getColorPickerHolder();
            if (StringUtils.isEmpty(colorPickerHolder)){
                colorPickerHolder="#ee7474";
                homePage.setColorPickerHolder(colorPickerHolder);
            }
            map.put("colorPickerHolder",colorPickerHolder);
            params +="&colorPickerHolder="+colorPickerHolder;
            String honorBanner = homePage.getHonorBanner();
            if (!StringUtils.isEmpty(honorBanner)){
                honorBanner=fullRoot+"public/file_list/images/"+honorBanner;
                map.put("honorBanner",honorBanner);
                params +="&honorBanner="+honorBanner;
                homePage.setHonorBanner(honorBanner);
            }
            String honorContent = homePage.getHonorContent();
            if (!StringUtils.isEmpty(honorContent)){
                String s = honorContent.replaceAll("src=\"public", "src=\"" + fullRoot + "public");
                map.put("honorContent",s);
                params +="&honorContent="+s;
                homePage.setHonorContent(s);
            }
            if (StringUtils.isEmpty(String.valueOf(homePage.getId()))){
                String sign = ApiUtil.getSign(map);
                LOGGER.info("sign："+sign);
                String url = "api/setHome";
                params += "&signCode="+sign;
                String s="";
                if (params.trim().startsWith("&")){
                    s = params.replaceFirst("&", "");
                }
                String s1 = ApiUtil.sendPost(url, s);
                LOGGER.info("s1："+s1);
                JSONObject jsonObject = JSON.parseObject(s1);
                if ((boolean)jsonObject.get("status")){
                    int i = homePageService.insertHomePage(homePage);
                    if (i<0){
                        res.put("code",1);
                        res.put("msg","新增失败");
                        return res;
                    }
                } else {
                    res.put("code",1);
                    res.put("msg",jsonObject.get("message"));
                    return res;
                }
            }else {
                String sign = ApiUtil.getSign(map);
                LOGGER.info("sign："+sign);
                String url = "api/updateHome";
                params += "&signCode="+sign;
                String s="";
                if (params.trim().startsWith("&")){
                    s = params.replaceFirst("&", "");
                }
                String s1 = ApiUtil.sendPost(url, s);
                LOGGER.info("s1："+s1);
                JSONObject jsonObject = JSON.parseObject(s1);
                String ss = JSONObject.toJSONString(jsonObject.get("status"));
                if (ss.equals("true")){
                    int i = homePageService.updateHomePage(homePage);
                    if (i<0){
                        res.put("code",1);
                        res.put("msg","修改失败");
                        return res;
                    }
                }else {
                    res.put("code",1);
                    res.put("msg",jsonObject.get("message"));
                    return res;
                }
            }
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失败："+e.getMessage());
            return res;
        }
        return res;
    }


    @RequestMapping("getAssetList")
    @ResponseBody
    public ModelAndView getAssetList(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
//        List<Asset>  list=assetService.getAssetList();
        List<Product> list = productService. getAssetList();
        mv.addObject("list",list);
        mv.setViewName("homepage_list");
        return mv;
    }

    @RequestMapping("getHomepage")
    @ResponseBody
    public ModelAndView getHomepage(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        HomePage data= homePageService.getHomePage();
        mv.addObject("data",data);
        mv.setViewName("view/set-page");
        return mv;
    }


    @RequestMapping("tolistHomepage")
    @ResponseBody
    public JSONObject tolistHomepage(HomePage homepage, HttpServletRequest request, HttpServletResponse response){
        JSONObject mv = new JSONObject();
        HomePage data= homePageService.listHomePage(homepage.getAssetName());
        if(data==null||data.equals("")){
            mv.put("success",false);
            return mv;
        }
        mv.put("data",data);
        mv.put("success",true);
        return mv;
    }
    @RequestMapping(value = "save")
    public ModelAndView save(HomePage model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("redirect:/homepage/getHomepage");
        mv.addObject("result", true);
        mv.addObject("msg", "保存成功");
        try {
            String fullRoot = WwSystem.getFullRoot(request);
            String companyIntroduce = model.getCompanyIntroduce();
            if (companyIntroduce!=null){
                String s = companyIntroduce.replaceAll("<p><span id=\"aaa\"></span></p>","").replaceAll("<p><br/></p>","");
                String s1 = s.replaceAll("src=\"public","src=\""+fullRoot+"public");
                model.setCompanyIntroduce(s1);
            }
            String colorPickerHolder = model.getColorPickerHolder();
            if (colorPickerHolder==null){
                colorPickerHolder="#ee7474";
                model.setColorPickerHolder(colorPickerHolder);
            }
            String companyPublic = model.getCompanyPublic();
            if (companyPublic!=null){
                companyPublic=fullRoot+"public/file_list/images/"+companyPublic;
                model.setCompanyPublic(companyPublic);
            }

            String honorBanner = model.getHonorBanner();
            if (honorBanner!=null){
                honorBanner=fullRoot+"public/file_list/images/"+honorBanner;
                model.setHonorBanner(honorBanner);
            }
            String headImg = model.getHeadImg();
            if (headImg!=null){
                headImg=fullRoot+"public/file_list/images/"+headImg;
                model.setHeadImg(headImg);
            }
            String honorContent = model.getHonorContent();
            if (honorContent!=null){
                String s = honorContent.replaceAll("<p><span id=\"aaa\"></span></p>","").replaceAll("<p><br/></p>","");
                String s1 = s.replaceAll("src=\"public","src=\""+fullRoot+"public");
                model.setHonorContent(s1);
            }
            homePageService.updateHomePage(model);
        } catch (Exception e) {
            e.getMessage();
            mv.addObject("result", false);
            mv.addObject("msg", "保存失败，错误信息：" + e.getMessage());
        }
        return mv;
    }

    public static void main(String[] args){
        String  aa="true";
        String bb=null;
        String cc="null";
        if (aa.equals(true)){
            System.out.println("12312312312");
        }

    }
}
