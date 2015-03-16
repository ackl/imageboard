package imageboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String hello(ModelMap model) {
        model.addAttribute("message", "TODO: Some posts go here by default?");
        return "index";
    }
}

