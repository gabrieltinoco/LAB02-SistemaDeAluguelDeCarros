package com.laboratorio02.rentwheels.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public ModelAndView hello() {
        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("nome", "SamukaZezinho");
        return mv;
    }

    @GetMapping("/hello-model")
    public String hello(Model model) {
        model.addAttribute("nome", "SamukaZe");
        return "hello";
    }

    @GetMapping("/hello-servlet")
    public String hello(HttpServletRequest request) {
        request.setAttribute("nome", "Samuka");
        return "hello";
    }
}
