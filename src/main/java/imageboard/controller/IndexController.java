package imageboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import imageboard.dao.ThreadsDao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.security.Principal;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class IndexController {

	private ThreadsDao threadsDao;

    private static final Logger logger = LoggerFactory
            .getLogger(IndexController.class);

	@Autowired
	public IndexController(ThreadsDao threadsDao) {
		this.threadsDao = threadsDao;
	}

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage() {

        //ModelAndView model = new ModelAndView();
        //model.addObject("message", "Admin page stuff will go here.");
        //model.setViewName("admin");
        return "admin";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
		return model;
	}

    @RequestMapping("/")
    public String hello(ModelMap model, Principal principal) {
        if (principal == null) {
            System.out.println("nothing");
        } else {
            System.out.println("something");
            System.out.println(principal.getName());
        }
        model.addAttribute("message", "TODO: go to bed");
        return "index";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadRoute() {
        return "upload";
    }

    @RequestMapping("/threads/{id}")
    public String renderPostPage(@PathVariable int id, ModelMap model) {
        return "thread";
    }
}


