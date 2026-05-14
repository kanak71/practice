package com.min.edu.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {


    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }
}