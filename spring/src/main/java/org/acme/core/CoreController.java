package org.acme.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class CoreController
{

    @GetMapping("/*")
    public String swagger(){
        return "redirect:http://localhost:8080/swagger-ui/index.html";
    }



}
