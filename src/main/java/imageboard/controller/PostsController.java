package imageboard.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import imageboard.model.PostsModel;
import imageboard.dao.PostsDao;

@RestController
@RequestMapping("/posts")
public class PostsController {

	private PostsDao dao;

	@Autowired
	public PostsController(PostsDao dao) {
		this.dao = dao;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<PostsModel> getAllPosts() {
		return dao.selectAllPosts();
	}
	@RequestMapping(method = RequestMethod.POST)
	public String postPost(@RequestParam Map<String, String> params) {
		dao.insertPost(Integer.parseInt(params.get("userId")), 
			       Integer.parseInt(params.get("parentId")),
			       Integer.parseInt(params.get("date")), 
			       params.get("imageUrl"),
			       params.get("content"));

		return "redirect:/";
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public PostsModel getPost(@PathVariable int id) {
		return dao.selectPostById(id);
	}
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String putPost(@PathVariable int id, @RequestParam Map<String, String> params) {
		dao.updatePost(id, Integer.parseInt(params.get("userId")), 
			           Integer.parseInt(params.get("parentId")),
			           Integer.parseInt(params.get("date")), 
				   params.get("imageUrl"),
				   params.get("content"));

		return "redirect:/" + id;
	}
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public String deletePost(@PathVariable int id) {
		dao.removePostById(id);

		return "redirect:/";
	}

}

