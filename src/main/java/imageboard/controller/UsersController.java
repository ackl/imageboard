package imageboard.controller;

import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import imageboard.model.UsersModel;
import imageboard.dao.UsersDao;

//TODO:
//Check authentication for certain requests
//Reduce precision loss from expiryDate
//Keep Map for RequestParam?
//Hash the password parameter from PUT requests
//Run checks before accepting changes from PUT requests
//PUT is not working
//Exception handling

@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersDao dao;

    @Autowired
    public UsersController(UsersDao dao) {
        this.dao = dao;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String getLoggedIn(Principal principal) {
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //System.out.println("User has authorities: " + userDetails.getAuthorities());
        return principal.getName();
    }
    //@RequestMapping(method=RequestMethod.GET)
    //public List<UsersModel> getAllUsers() {
        //return dao.selectAllUsers();
    //}
    @RequestMapping(method=RequestMethod.POST)
    public String postUser(@RequestParam Map<String, String> params) {
        //int timeLimit = (int) (TimeUnit.HOURS.toMillis(
                    //Integer.parseInt("24")
                    //) + (new Date()).getTime());
        dao.insertUser(params.get("username"), params.get("password"));
        String role = "ROLE_" + params.get("role").toUpperCase();
        dao.insertRole(params.get("username"), role);

        return "redirect:/";
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public UsersModel getUser(@PathVariable int id) {
        return dao.selectUserById(id);
    }
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String putUser(@PathVariable int id, @RequestParam Map<String, String> params) {
        dao.updateUser(id, params.get("name"),
                   params.get("pass"),
                   params.get("imageUrl"));

        return "redirect:/" + id;
    }
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable int id) {
        dao.removeUserById(id);

        return "redirect:/";
    }

}
