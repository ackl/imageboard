package imageboard.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import imageboard.model.PostsModel;
import imageboard.model.ThreadsModel;
import imageboard.dao.PostsDao;
import imageboard.dao.ThreadsDao;


@Controller
public class IndexController {

	private PostsDao dao;
	private ThreadsDao threadsDao;

	@Autowired
	public IndexController(ThreadsDao threadsDao) {
		this.dao = dao;
		this.threadsDao = threadsDao;
	}

    @RequestMapping("/")
    public String hello(ModelMap model) {
        model.addAttribute("message", "TODO: go to bed");
        return "index";
    }

    @RequestMapping("/threads/{id}")
    public String renderPostPage(@PathVariable int id, ModelMap model) {
        //System.out.println(id);
        //model.addAttribute("message", "TODO: go to bed");
        //model.addAttribute("postId", id);

        //ThreadsModel thread = threadsDao.selectThreadById(id);
        //List<PostsModel> replies = dao.selectPostsByParentId(thread.getId());
        //thread.setReplies(replies);
        //model.addAttribute("thread", thread);

        return "thread";
    }
}

