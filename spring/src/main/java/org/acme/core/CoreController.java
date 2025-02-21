package org.acme.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class CoreController {


    @Value("${server.port}")
    private int port;


    @GetMapping("/*")
    public String swagger() {
        return "redirect:http://localhost:"+port+"/swagger-ui/index.html";
    }


}
