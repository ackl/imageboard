package imageboard.controller;

import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.security.Principal;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;

import imageboard.model.UsersModel;
import imageboard.model.PostsModel;
import imageboard.service.UsersService;
import imageboard.service.PostsService;
import imageboard.util.FileWriter;

//TODO:
//Check authentication for certain requests
//Reduce precision loss from expiryDate
//Keep Map for RequestParam?
//Hash the password parameter from PUT requests
//Run checks before accepting changes from PUT requests
//PUT is not working
//Exception handling

@Controller
@RequestMapping("/users")
public class UsersController {

    @Value("${imageUploadDirectory}")
    private String imageUploadDirectory;
    @Value("${imageDownloadDirectory}")
    private String imageDownloadDirectory;

    private UsersService usersService;
    private PostsService postsService;
    private static final Logger logger = Logger.getLogger(ThreadsController.class.getName() );

    @Autowired
    public UsersController(UsersService usersService, PostsService postsService) {
        this.usersService = usersService;
        this.postsService = postsService;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String getLoggedIn(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return authentication.getName();
    }

    @RequestMapping(method=RequestMethod.POST)
    public String postUser(@RequestParam Map<String, String> params) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(params.get("password"));

        usersService.createUser(params.get("username"), hashedPassword);
        String role = "ROLE_" + params.get("role").toUpperCase();
        usersService.createRole(params.get("username"), role);
        return "redirect:/";
    }

    @RequestMapping(value="/{id}", method=RequestMethod.POST)
    @ResponseBody
    public String updateProfileImage(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        logger.log(Level.WARNING, "trying to put");

        UsersModel userModel = usersService.selectUserByUsername(id);

        if (!file.isEmpty()) {
            String downloadPath = FileWriter.writeFile(file, imageUploadDirectory, imageDownloadDirectory);
            usersService.updateUserProfileImage(id, downloadPath);
        }
        return "ok";
    }

    @RequestMapping(value="/register/{code}", method=RequestMethod.GET)
    public String registrationPage (@RequestParam Map<String, String> params, @PathVariable String code, ModelMap model, Principal principal) {
        boolean valid = false;
        try {
            valid = usersService.checkKeycodeValid(code);
        } catch (EmptyResultDataAccessException e) {
            logger.log( Level.WARNING, e.toString(), e );
        }

        if (valid) {
            model.addAttribute("message", "Registration your account:");
        } else {
            model.addAttribute("message", "Invalid keycode supplied.");
        }

        model.addAttribute("valid", valid);

        if (principal == null) {
            return "registration";
        } else {
            return "index";
        }

    }

    @RequestMapping(value="/keycode", method=RequestMethod.POST)
    @ResponseBody
    public String createKeycode(@RequestParam Map<String, String> params) {
        long millis = TimeUnit.HOURS.toMillis(24);
        long date = new Date().getTime() + millis;
        String keycode = String.valueOf(UUID.randomUUID());

        return usersService.createRegistrationKeycode(keycode, date);
    }

    @RequestMapping(value="/profile/{id}", method=RequestMethod.GET)
    public String getUser(@PathVariable String id, ModelMap model) {
        UsersModel userModel = usersService.selectUserByUsername(id);
        List<PostsModel> postsByUser = postsService.getPostsByUsername(id);
        model.addAttribute("message", "Profile");
        model.addAttribute("request_username", id);
        model.addAttribute("response_user", userModel);
        model.addAttribute("posts", postsByUser);
        return "profile";
    }

    @RequestMapping(value="/profile/{id}/json", method=RequestMethod.GET)
    @ResponseBody
    public UsersModel getUser(@PathVariable String id) {
        return usersService.selectUserByUsername(id);
    }

    //@RequestMapping(value="/{id}", method=RequestMethod.PUT)
    //public String putUser(@PathVariable int id, @RequestParam Map<String, String> params) {
        //dao.updateUser(id, params.get("name"),
                   //params.get("pass"),
                   //params.get("imageUrl"));

        //return "redirect:/" + id;
    //}
    //@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    //public String deleteUser(@PathVariable int id) {
        //dao.removeUserById(id);

        //return "redirect:/";
    //}

    //@ExceptionHandler(EmptyResultDataAccessException.class)
    //public String handleEmptyTable(Exception ex, ModelMap model) {
        ////logger.log( Level.WARNING, ex.toString(), ex );
    //}
}
