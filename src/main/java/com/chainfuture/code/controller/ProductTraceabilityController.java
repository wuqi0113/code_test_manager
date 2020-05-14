package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.ProductTraceability;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.service.ProductTraceabilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/productTraceability")
public class ProductTraceabilityController {

    @Autowired
    private ProductTraceabilityService productTraceabilityService;

    @RequestMapping("toProductTracePage")
    @ResponseBody
    public ModelAndView toProductTracePage(String proAddress, HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv =new ModelAndView("view/product_tarce_view");
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        try{}catch (Exception e){
            e.printStackTrace();
            mv.addObject("code",1);
            mv.addObject("msg","失败");
        }
        mv.addObject("proAddress",proAddress);
        return mv;
    }

    @RequestMapping("getProductTracePage")
    @ResponseBody
    public ModelAndView getProductTracePage(String proAddress, HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv =new ModelAndView("view/product_tarce_view");
        mv.addObject("code",0);
        mv.addObject("msg","成功");

        String proAddress1 = request.getParameter(WwSystem.ToString("proAddress"));
        try{}catch (Exception e){
            e.printStackTrace();
            mv.addObject("code",1);
            mv.addObject("msg","失败");
        }
        mv.addObject("proAddress",proAddress);
        return mv;
    }

  /*  @RequestMapping("productTracePage")
    @ResponseBody
    public ModelAndView productTracePage(ProductTraceability model, HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv =new ModelAndView("view/product_tarce_view");
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        List<ProductTraceability> list = null;
        Integer total = 0;
        try{
            list = productTraceabilityService.listProductTraceability(model);
            total = productTraceabilityService.queryCount(model);
        }catch (Exception e){
            e.printStackTrace();
            mv.addObject("code",1);
            mv.addObject("msg","失败");
        }
        mv.addObject("count",total); // 总条数
        mv.addObject("data", list); // 列表数据
        return mv;
    }*/


    @RequestMapping("listproductTraceability")
    @ResponseBody
    public JSONObject listproductTraceability(ProductTraceability model, HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        List<ProductTraceability> list = null;
        Integer total = 0;
        try{
            list = productTraceabilityService.listProductTraceability(model);
            total = productTraceabilityService.queryCount(model);
        }catch (Exception e){
            e.getMessage();
            res.put("code",1);
            res.put("msg","失败");
        }
        res.put("count",total); // 总条数
        res.put("data", list); // 列表数据
        res.put("proAddress", model.getProAddress()); // 列表数据
        return res;
    }

    @RequestMapping("getById")
    @ResponseBody
    public ModelAndView getById(Integer id,String proAddress, HttpServletRequest request, HttpServletResponse response){
        ModelAndView res = new ModelAndView();
        res.addObject("code", 0);
        res.addObject("msg", "成功");
        ProductTraceability  model = new ProductTraceability();
        try{
                model =productTraceabilityService.getById(id);

        }catch (Exception e){
            e.printStackTrace();
            res.addObject("code", 1);
            res.addObject("msg", "失败");
        }
        res.addObject("data",model);
        res.addObject("proAddress",proAddress);
        res.addObject("success",0);
        res.setViewName("view/product_trace_edit");
        return  res;
    }

    @RequestMapping("save")
    @ResponseBody
    public  ModelAndView save(ProductTraceability model,HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.addObject("code", 0);
        mv.addObject("msg", "保存成功");

        String fullRoot = WwSystem.getFullRoot(request);
        try {

        } catch (Exception e) {
            e.getMessage();
            mv.addObject("code", 1);
            mv.addObject("msg", "保存失败，错误信息：" + e.getMessage());
        }
        mv.setViewName("redirect:/productTraceability/toProductTracePage?proAddress="+model.getProAddress());
        return mv;
    }
}
