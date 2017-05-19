package com.agtech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import java.util.Map;

@Controller
//@EnableAutoConfiguration
public class HelloController {
    @RequestMapping("/index")
    public String index(){
        return "welcome";
    }
}
