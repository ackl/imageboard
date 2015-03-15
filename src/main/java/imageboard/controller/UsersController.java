package imageboard.controller;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dao.UsersDao;
import model.UsersModel;

@RestController
@RequestMapping("/users")
//TODO: Authentication information
public class UsersController {
	
	private UsersDao dao = new UsersDao();

	@RequestMapping(method=RequestMethod.GET)
	public List<UsersModel> getAllUsers() {
		return dao.selectAllUsers();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public UsersModel getUser(@PathVariable long id) {
		return dao.selectUserById(id);
	}

	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String test() {
		return "Hello World!";
	}
	
//	@RequestMapping(method=RequestMethod.POST)
//	public String postUser(@RequestParam(value="keycode") String keycode, 
//				       @RequestParam(value="timeLimit", defaultValue=24) long timeLimit) {
//		dao.insertUser(keycode, (new Date()).getTime() + TimeUnit.HOURS.toMillis(timeLimit));
//
//		return "redirect:/"; //TODO: Confirmation message?
//	}
//
//	@RequestMapping(value="/register", method=RequestMethod.GET)
//	public String getRegisterForm(@RequestParam(value="keycode", method=) String keycode, ModelMap model) {
//		User user = dao.selectUserByKeycode(keycode);
//
//		if (user.checkRegistered()) return "registered"; //TODO: Define view
//		else if (user.checkExpired()) return "expired"; //TODO: Define view
//		else {
//			model.addAttribute("keycode", keycode);
//			return "registerForm"; //TODO: Define view
//		}
//	}
//
//	@RequestMapping(value="/{id}", method=RequestMethod.GET)
//	public User getUser(@PathVariable int id) {
//		return dao.getUserById(id);
//	}
//	
//	@RequestMapping(value = "/settings", method = RequestMethod.POST)
//	public String userSettingsSubmit(@RequestParam("keycode") String keycode,
//					 @RequestParam("name") String name,
//					 @RequestParam("pass") String pass, 
//					 @RequestParam("image_url") String imageUrl) {
//		dao.updateUser(keycode, name, pass, imageUrl);
//
//		return "redirect:/settings"; //TODO: Confirmation message?
//	}

}
