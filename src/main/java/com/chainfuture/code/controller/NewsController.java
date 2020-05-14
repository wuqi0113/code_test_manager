package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.Medal;
import com.chainfuture.code.bean.News;
import com.chainfuture.code.bean.Product;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.service.MedalService;
import com.chainfuture.code.service.NewsService;
import com.chainfuture.code.service.ProductService;
import com.chainfuture.code.utils.ApiUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

@Controller
@RequestMapping("/news/")
public class NewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MedalService medalService;
    public static final Logger LOGGER = Logger.getLogger(NewsController.class);

    @ResponseBody
    @RequestMapping("delNews/{id}")
    public JSONObject del(@PathVariable("id") Integer id,HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        try{
            Map<String,String> map =  new HashMap<>();
            String params = "";
            String  url = "api/updateNews";
            map.put("id",String.valueOf(id));
            params += "&id="+id;
            map.put("status","0");
            params += "&status=0";
            map.put("needPush","2");
            params += "&needPush=2";
            String sign = ApiUtil.getSign(map);
            System.out.println("sign："+sign);
            params += "&signCode="+sign;
            if (params.startsWith("&")){
                params.replaceFirst("&","");
            }
            String s1 = ApiUtil.sendPost(url, params);
            JSONObject jsonObject = JSON.parseObject(s1);
            if ((boolean)jsonObject.get("status")){
                int i = newsService.delNews(id);
                if (i<=0){
                    res.put("code",1);
                    res.put("msg","失败");
                }
            }else {
                res.put("code",1);
                res.put("msg",jsonObject.get("result"));
                return res;
            }
        }catch (Exception e){
            e.getMessage();
            res.put("code",1);
            res.put("msg","失败");
        }
        return res;
    }
    @RequestMapping("getNewsById/{id}")
    @ResponseBody
    public ModelAndView getNewsById(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        ModelAndView res = new ModelAndView();
        res.addObject("code",0);
        res.addObject("msg","成功");
        News  model=null;
        try{
            model =newsService.getById(id);
        }catch (Exception e){
            e.getMessage();
            res.addObject("code",1);
            res.addObject("msg","失败");
        }

        res.addObject("data",model);
        res.setViewName("see/news/newsEdit");
        return  res;
    }
    @ResponseBody
    @RequestMapping("newsList")
    public JSONObject newsList(News news,HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        List<News> list = null;
        Integer count = 0;
        try{
            list = newsService.listNews(news);
            count = newsService.queryCount(news);
        }catch (Exception e){
            e.getMessage();
            res.put("code",1);
            res.put("msg","失败");
        }
        res.put("count",count); // 总条数
        res.put("data", list); // 列表数据
        return res;
    }

    @RequestMapping("toAddPage")
    @ResponseBody
    public ModelAndView toAddPage(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv =new ModelAndView();
//        mv.setViewName("page/news_edit");
        mv.setViewName("see/news/newsEdit");
        return mv;
    }

    @ResponseBody
    @RequestMapping("save")
    public JSONObject save(News news,HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        Map<String,String> map =  new HashMap<>();
        String params = "";
        String fullRoot = WwSystem.getFullRoot(request);
        try{
            String title = news.getTitle();
            if (!StringUtils.isEmpty(title)){
                map.put("title",title);
                params += "&title="+title;
            }
            String summary = news.getSummary();
            if (!StringUtils.isEmpty(summary)){
                map.put("summary",summary);
                params += "&summary="+summary;
            }
            String summaryImg = news.getSummaryImg();
            LOGGER.info("summaryImg"+summaryImg);
            if (!StringUtils.isEmpty(summaryImg)){
                summaryImg =fullRoot+"public/file_list/images/"+summaryImg;
                map.put("summaryImg",summaryImg);
                params += "&summaryImg="+summaryImg;
                news.setSummaryImg(summaryImg);
            }
            String content = news.getContent();
            if (!StringUtils.isEmpty(content)){
                String s = content.replaceAll("src=\"public","src=\""+fullRoot+"public");
                map.put("content",s);
                params += "&content="+s;
                news.setContent(s);
            }
            int tokenTotal = news.getTokenTotal();
            if (tokenTotal<0){
                tokenTotal=0;
            }
            map.put("tokenTotal",tokenTotal+"");
            params += "&tokenTotal="+tokenTotal;
//            String tokenTotal = String.valueOf(news.getTokenTotal());

            String parentPromot = news.getParentPromot();
            if (!StringUtils.isEmpty(parentPromot)){
                map.put("parentPromot",parentPromot);
                params += "&parentPromot="+parentPromot;
            }
            String promot = news.getPromot();
            if (!StringUtils.isEmpty(promot)){
                map.put("promot",promot);
                params += "&promot="+promot;
            }
            String needPush = news.getNeedPush();
            if (!StringUtils.isEmpty(needPush)){
                map.put("needPush",news.getNeedPush());
                params += "&needPush="+news.getNeedPush();
            }else {
                map.put("needPush", "2");
                params += "&needPush=2";
            }
            Integer status = news.getStatus();
            if (status==null) {
                news.setStatus(1);
                map.put("status",String.valueOf(news.getStatus()));
                params += "&status="+String.valueOf(news.getStatus());
            }else {
                if (news.getStatus()==0) {
                    map.put("needPush", "2");
                    params += "&needPush=2";
                }
                map.put("status",String.valueOf(news.getStatus()));
                params += "&status="+String.valueOf(news.getStatus());
            }
            int readAmount = news.getReadAmount();
            if (readAmount<0){
                map.put("readAmount","0");
                params += "&readAmount="+0;
            }else {
                map.put("readAmount",readAmount+"");
                params += "&readAmount="+readAmount;
            }
            String assetName = news.getAssetName();
            if (!StringUtils.isEmpty(assetName)){
                map.put("assetName",assetName);
                params += "&assetName="+assetName;
            }
            String amount = news.getAmount();
            if (!StringUtils.isEmpty(amount)){
                map.put("amount",amount);
                params += "&amount="+amount;
            }
            NrcMain nm =new NrcMain();
            nm.initRPC();
            String primeaddr = nm.getPrimeaddr();
            map.put("address",primeaddr);
            params += "&address="+primeaddr;
            Object admin = request.getSession().getAttribute("admin");
            String publisher = news.getPublisher();
            if (StringUtils.isEmpty(publisher)){
                 map.put("publisher",admin.toString());
                 params += "&publisher="+admin;
            }
            String s = "";
            if (StringUtils.isEmpty(news.getCreateTime())){
                s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            }else {
                s = news.getCreateTime();
            }
            Integer id = news.getId();
            String url = "";
            if (id!=null){
                map.put("id",id.toString());
                params += "&id="+news.getNewsId();
                url = "api/updateNews";
                map.put("tokenSurplus",news.getTokenSurplus()+"");
                params += "&tokenSurplus="+news.getTokenSurplus();
            }else {
                url = "api/addNews";
                map.put("tokenSurplus",tokenTotal+"");
                params += "&tokenSurplus="+tokenTotal;

            }
            map.put("createTime",s);
            params += "&createTime="+s;
            String sign = ApiUtil.getSign(map);
            System.out.println("sign："+sign);
            params += "&signCode="+sign;
            String so = "";
            if (params.startsWith("&")){
                so = params.replaceFirst("&","");
            }
            String s1 = ApiUtil.sendPost(url, so);
            if (StringUtils.isEmpty(s1)){
                res.put("code",1);
                res.put("msg","链接错误");
                return res;
            }
            JSONObject jsonObject = JSON.parseObject(s1);
            String ss = JSONObject.toJSONString(jsonObject.get("status"));
            if (ss.equals("true")){
                int i =0;
                if (id!=null){
                    i = newsService.updateNews(news);
               }else {
                    news.setNewsId((int)jsonObject.get("result"));
                    i = newsService.saveNews(news);
               }
                if (i<=0){
                    res.put("code",1);
                    res.put("msg","编辑失败");
                }
            }else {
                res.put("code",1);
                res.put("msg",jsonObject.get("message"));
            }
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }
    @ResponseBody
    @RequestMapping("toNewsList")
    public ModelAndView toProductList(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv =new ModelAndView();
//        mv.setViewName("page/news_list");
        mv.setViewName("see/news/newsList");
        return mv;
    }

    @ResponseBody
    @RequestMapping("getNewsList")
    public JSONObject  getNewsList(News news,HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        int pageIndex = news.getPageIndex();
        int limit = news.getLimit();
        Map<String,String> map =  new HashMap<>();
        map.put("assetName","DEMO");
        try{
            //查询总数
            String signCount = ApiUtil.getSign(map);
            System.out.println("sign："+signCount);
            String urlCount = "api/getNewsCount";
            String paramsCount="signCode="+signCount;
            paramsCount += "&assetName=DEMO";
            String s = ApiUtil.sendPost(urlCount, paramsCount);
            JSONObject jsonObjectCount = JSON.parseObject(s);
            JSONObject result1 = (JSONObject) jsonObjectCount.get("result");
            Object newsCount = result1.get("newsCount");
            res.put("count", newsCount);
            //查询所有
            map.put("pageIndex",String.valueOf(pageIndex));
            map.put("limit",String.valueOf(limit));
            String sign = ApiUtil.getSign(map);
            System.out.println("sign："+sign);
            String url = "api/getNews";
            String params="assetName=DEMO";
            params += "&signCode="+sign;
            params += "&pageIndex="+pageIndex;
            params += "&limit="+limit;
            String s1 = ApiUtil.sendPost(url, params);
            JSONObject jsonObject = JSON.parseObject(s1);
            Object result = jsonObject.get("result");
            res.put("data",result);
        }catch (Exception e){
            e.printStackTrace();
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }



    //*************************************************************************************************************************************************************
    @RequestMapping("/tolistNews")
    public ModelAndView tolistNews(Model model, HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        List<Product> list = null;
       /* NrcMain nm=new NrcMain();
        nm.initRPC();
        Object balance = null;*/
        try{
            list = productService. getAssetList();
//            balance = nm.getBalance();
        }catch (Exception e){
            e.getMessage();
            mv.addObject("code",1);
            mv.addObject("msg","失败");
        }
        mv.addObject("list",list);
//        mv.addObject("balance",balance.toString());
        mv.setViewName("view/comunity");
        return mv;
    }

    @RequestMapping("/listNews")
    @ResponseBody
    public JSONObject listNews(News news, HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        List<News> list = null;
        Integer count = 0;
        try{
            list = newsService.listNews(news);
            count = newsService.queryCount(news);
        }catch (Exception e){
            e.getMessage();
            res.put("code",1);
            res.put("msg","失败");
        }
        res.put("count",count); // 总条数
        res.put("data", list); // 列表数据
        return res;
    }

    @RequestMapping("/saveAddToken")
    @ResponseBody
    public Map<String, Object> saveAddToken(HttpServletRequest request, News news){
        Map<String,Object> map = Maps.newHashMap();
        map.put("result", true);
        map.put("msg", "保存成功");
        try {
            if (news.getNews_add() > 0){
                newsService.saveAddToken(news);
            }
        } catch (Exception e) {
            map.put("result", false);
            map.put("msg", "保存失败，错误信息：" + e.getMessage());
        }
        return map;
    }

    @RequestMapping("/banNews")
    @ResponseBody
    public Map<String, Object> banNews(HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();
        map.put("result",true);
        map.put("msg","禁用成功");
        String id = request.getParameter("id");
        String status = request.getParameter("status");
        try{
            if(id != null || !id.equals("")){
                newsService.banNews(Integer.parseInt(id),Integer.parseInt(status));
            }
        }catch (Exception e){
            map.put("result",false);
            map.put("msg","禁用失败");
        }
        return map;
    }

    @RequestMapping("getById")
    @ResponseBody
    public ModelAndView getById(Integer id, HttpServletRequest request, HttpServletResponse response){
        ModelAndView res = new ModelAndView();
        res.addObject("code",0);
        res.addObject("msg","成功");
        News  model=null;
        List<Product> list = null;
        List<Medal> medalList = null;
        try{
            model =newsService.getById(id);
            list = productService. getAssetList();
            medalList = medalService.getMedalList();
        }catch (Exception e){
            e.getMessage();
            res.addObject("code",1);
            res.addObject("msg","失败");
        }

        res.addObject("list",list);
        res.addObject("medalList",medalList);
        res.addObject("data",model);
        res.setViewName("view/comunity-addinfo");
        return  res;
    }

    @RequestMapping("/saveNews")
    @ResponseBody
    public ModelAndView saveNews(HttpServletRequest request, News news){
        ModelAndView map = new ModelAndView();
        map.addObject("code", 0);
        map.addObject("msg", "保存成功");
        try {
            String fullRoot = WwSystem.getFullRoot(request);
            String content = news.getContent();

            if (content!=null){
                String s = content.replaceAll("<span id=\"aaa\"></span>","");
                String s1 = s.replaceAll("src=\"public","src=\""+fullRoot+"public");
                news.setContent(s1);
            }
            newsService.saveNews(news);
        } catch (Exception e) {
            map.addObject("code", 1);
            map.addObject("msg", "保存失败，错误信息：" + e.getMessage());

        }
        map.setViewName("redirect:/news/tolistNews");
        return map;
    }
}
