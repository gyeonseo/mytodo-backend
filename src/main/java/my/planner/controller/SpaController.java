package my.planner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {
    @GetMapping({
            "/",                          // 루트
            "/{x:[\\w\\-]+}",             // 1단계
            "/{x:[\\w\\-]+}/{y:[\\w\\-]+}/**" // 2단계+
    })
    public String forward() {
        return "forward:/index.html";
    }
}
