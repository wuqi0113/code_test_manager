package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.MedalAward;
import com.chainfuture.code.service.MedalAwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/medalaward")
public class MedalAwardController {


    @Autowired
    private MedalAwardService medalAwardService;

    @ResponseBody
    @RequestMapping("getMedalAwardList")
    public JSONObject  getMedalAwardList(MedalAward model, HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        List<MedalAward> list = null;
        Integer count = 0;
        try{
            list = medalAwardService.getMedalAwardList(model);
            count = medalAwardService.queryCount(model);
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        res.put("data",list);
        res.put("count",count);
        return res;
    }

    @RequestMapping("addMedalAward")
    @ResponseBody
    public ModelAndView  addMedalAward(MedalAward model,Integer id, HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        try{
            medalAwardService.saveMedalAward(model);
        }catch (Exception e){
            mv.addObject("code",1);
            mv.addObject("msg","失败"+e.getMessage());
        }
        if (model.getType()==1){//1--文章
            mv.setViewName("redirect:news/getById?id="+id);
        }
        if (model.getType()==2){//2--勋章
            mv.setViewName("redirect:medal/getById?id="+id);
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping("delete")
    public JSONObject  delete(Integer id, HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        try{
            medalAwardService.delete(id);
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }

}
