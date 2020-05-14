package com.chainfuture.code.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2019/2/27.
 */
public class StudyTest {


    public static  void  main(String[] args){

        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("time","teims");
        JSONObject json = JSON.parseObject("{\"code\":1001,\"timestamp\":\"2018-11-05 03:40:54\"}");
        System.out.println(json);
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before Java8 ");
            }
        }).start();*/
//        new Thread( () -> System.out.println("In Java8!") ).start();


        //Prior Java 8 :
       /* List features = Arrays.asList("Lambdas", "Default Method",
                "Stream API", "Date and Time API");
        for (Object featurea : features) {
            System.out.println(featurea);
        }*/

        //In Java 8:
       /* List features1 = Arrays.asList("Lambdas", "Default Method", "Stream API",
                "Date and Time API");
        features1.forEach(n -> System.out.println(n));*/

        // Even better use Method reference feature of Java 8
        // method reference is denoted by :: (double colon) operator
        // looks similar to score resolution operator of C++
//        features1.forEach(System.out::println);

    }

}
