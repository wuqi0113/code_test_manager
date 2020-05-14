package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.ProductTraceability;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.service.ProductBounsService;
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
@RequestMapping("/productBouns")
public class ProductBounsController {

    @Autowired
    private ProductBounsService productBounsService;


}
