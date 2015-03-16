package imageboard.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import imageboard.model.PostsModel;

@RestController
@RequestMapping("/posts")
public class PostsController {

	@RequestMapping(method = RequestMethod.GET)
	public List<PostsModel> getAllPosts() {
		return new ArrayList<PostsModel>();
	}
	@RequestMapping(method = RequestMethod.POST)
	public String postPost(@RequestParam Map<String, String> params) {
		return "redirect:/";
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public PostsModel getPost(@PathVariable int id) {
		return new PostsModel();
	}
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String putPost(@PathVariable int id, @RequestParam Map<String, String> params) {
		return "redirect:/" + id;
	}
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public String deletePost(@PathVariable int id) {
		return "redirect:/";
	}

}

