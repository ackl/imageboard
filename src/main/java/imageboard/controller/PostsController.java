package imageboard.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import imageboard.model.PostsModel;
import imageboard.model.ThreadsModel;
import imageboard.dao.PostsDao;
import imageboard.dao.ThreadsDao;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

	private PostsDao dao;
	private ThreadsDao threadsDao;

	@Autowired
	public PostsController(PostsDao dao, ThreadsDao threadsDao) {
		this.dao = dao;
		this.threadsDao = threadsDao;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<PostsModel> getAllPosts() {
		return dao.selectAllPosts();
	}

    @RequestMapping(value="/threads", method = RequestMethod.GET)
    public List<ThreadsModel> getAllThreads() {
        List<ThreadsModel> threadsModels = threadsDao.selectAllThreads();
        for (ThreadsModel thread : threadsModels) {
            List<PostsModel> replies = dao.selectPostsByParentId(thread.getId());
            thread.setReplies(replies);
        }

        return threadsModels;
    }

	@RequestMapping(value="/threads", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> postThread(@RequestBody ThreadsModel thread, UriComponentsBuilder b) {
		threadsDao.insertThread(thread.getUserId(),
			       thread.getParentId(),
                   new Date().getTime(),
			       thread.getImageUrl(),
			       thread.getContent(),
                   thread.getSubject());

        //TODO: Get id of created post;
        return buildCreateResponse(b, 1);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> postTesting(@RequestBody PostsModel post, UriComponentsBuilder b) {
		dao.insertPost(post.getUserId(),
			       post.getParentId(),
                   new Date().getTime(),
			       post.getImageUrl(),
			       post.getContent());

        //TODO: Get id of created post;
        return buildCreateResponse(b, 1);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public PostsModel getPost(@PathVariable int id) {
		return dao.selectPostById(id);
	}

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity putPost(@PathVariable int id, @RequestBody PostsModel post) {
        dao.updatePost(id, post.getUserId(),
                   post.getParentId(),
                   post.getDate(),
                   post.getImageUrl(),
                   post.getContent());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity deletePost(@PathVariable int id) {
		dao.removePostById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
	}


    //TODO: Add handling for returning error statuses to client.
    private Map<String, String> buildJSONReponseBody(String method) {
        Map<String, String> responseMap = new HashMap<String, String>();
        String messsage;

        if (method == "create") {
            responseMap.put("message", "Post created.");
        }

        return responseMap;
    }

	private ResponseEntity<Map<String, String>> buildCreateResponse(UriComponentsBuilder b, int resourceId) {
        // Build URI to created resource for response header.
        UriComponents uriComponents = b.path("/posts/{id}").buildAndExpand(resourceId);

        return ResponseEntity.created(uriComponents.toUri())
                .header("resourceId", Integer.toString(resourceId))
                .body(buildJSONReponseBody("create"));
	}
}

