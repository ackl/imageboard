package imageboard.controller;

import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import imageboard.dao.UsersDao;
import imageboard.model.UsersModel;

/* TODO:
 * Check authentication for certain requests
 * Reduce precision loss from expiryDate
 * Keep Map for RequestParam?
 */

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersDao dao;

	@RequestMapping(method=RequestMethod.GET)
	public List<UsersModel> getAllUsers() {
		return dao.selectAllUsers();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public UsersModel getUser(@PathVariable int id) {
		return dao.selectUserById(id);
	}

	@RequestMapping(method=RequestMethod.POST)
	public String postUser(@RequestParam Map<String, String> params) {
		int timeLimit = (int) (TimeUnit.HOURS.toMillis(Integer.parseInt(params.get("timeLimit"))) +
				(new Date()).getTime());
		dao.insertUser(params.get("keycode"), timeLimit);

		return "redirect:/";
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String putUser(@PathVariable int id, @RequestParam Map<String, String> params) {
		dao.updateUser(id, params.get("name"), params.get("pass"), params.get("imageUrl"));

		return "redirect:/" + id;
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public String deleteUser(@PathVariable int id) {
		dao.removeUserById(id);

		return "redirect:/";
	}

}
