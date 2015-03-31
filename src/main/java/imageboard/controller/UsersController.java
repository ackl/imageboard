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
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;

import imageboard.model.UsersModel;
import imageboard.service.UsersService;

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

    private UsersService usersService;
    private static final Logger logger = Logger.getLogger(ThreadsController.class.getName() );

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String getLoggedIn(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    //@RequestMapping(method=RequestMethod.GET)
    //public List<UsersModel> getAllUsers() {
        //return dao.selectAllUsers();
    //}
    @RequestMapping(method=RequestMethod.POST)
    public String postUser(@RequestParam Map<String, String> params) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);

        usersService.createUser(params.get("username"), hashedPassword);
        String role = "ROLE_" + params.get("role").toUpperCase();
        usersService.createRole(params.get("username"), role);
        return "redirect:/";
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
        model.addAttribute("message", "Profile");
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
