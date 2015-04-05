package imageboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import imageboard.dao.ThreadsDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;


@Controller
public class ErrorController {

    //@RequestMapping("/403")
    //public String hello(ModelMap model) {
        //return "403";
    //}

    @RequestMapping("/404")
    public String uploadRoute(ModelMap model) {
        return "404";
    }
}


