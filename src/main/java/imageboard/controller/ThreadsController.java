package imageboard.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.net.URI;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import imageboard.model.PostsModel;
import imageboard.model.ThreadsModel;
import imageboard.dao.PostsDao;
import imageboard.dao.ThreadsDao;
import imageboard.service.ThreadsService;

@RestController
@RequestMapping("/api/threads")
public class ThreadsController {

	private PostsDao dao;
	private ThreadsDao threadsDao;
	private ThreadsService threadsService;

	@Autowired
	public ThreadsController(PostsDao dao, ThreadsDao threadsDao, ThreadsService threadsService) {
		this.dao = dao;
		this.threadsDao = threadsDao;
		this.threadsService = threadsService;
	}

    @RequestMapping(method = RequestMethod.GET)
    public List<ThreadsModel> getAllThreads(@RequestParam(value="replylimit", required=false) Integer replylimit) {
        return threadsService.getAllThreads(replylimit);
    }

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> postThread(@RequestBody ThreadsModel thread, UriComponentsBuilder b) {
        //TODO: Get id of created post;
        this.threadsService.createThread(thread);
        return buildCreateResponse(b, 1);
	}

    @RequestMapping(value="/{id}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<String> getThread(@PathVariable int id) throws JSONException {
        boolean validThreadId = threadsService.isThread(id);

        if (validThreadId) {
            ThreadsModel thread = threadsService.getThread(id);
            JSONObject threadJSON = new JSONObject();
            threadJSON = buildPostJSON(threadJSON, thread);
            threadJSON.put("subject", thread.getSubject());

            JSONArray repliesJSON = new JSONArray();

            for (PostsModel reply : thread.getReplies()) {
                JSONObject replyJSON = new JSONObject();
                buildPostJSON(replyJSON, reply);
                replyJSON.put("parent_id", reply.getParentId());
                repliesJSON.put(replyJSON);
            }

            threadJSON.put("replies", repliesJSON);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("yo", "sup")
                    .body(threadJSON.toString());
        } else {
            JSONObject resp = new JSONObject();
            resp.put("error", "File not found.");
            resp.put("message", "No thread exists with this id.");
            resp.put("status", 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("yo", "sup")
                    .body(resp.toString());
        }

    }


	private ResponseEntity<Map<String, String>> buildCreateResponse(UriComponentsBuilder b, int resourceId) {
        // Build URI to created resource for response header.
        UriComponents uriComponents = b.path("/posts/{id}").buildAndExpand(resourceId);

        return ResponseEntity.created(uriComponents.toUri())
                .header("resourceId", Integer.toString(resourceId))
                .body(buildJSONReponseBody("create"));
	}

    private Map<String, String> buildJSONReponseBody(String method) {
        Map<String, String> responseMap = new HashMap<String, String>();
        String messsage;

        if (method == "create") {
            responseMap.put("message", "Post created.");
        }

        return responseMap;
    }

    private JSONObject buildPostJSON(JSONObject json, PostsModel post) throws JSONException {
        json.put("id", post.getId());
        json.put("image_url", post.getImageUrl());
        json.put("content", post.getContent());
        json.put("date", post.getDate());
        json.put("user_id", post.getUserId());
        return json;
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject resp = new JSONObject();
        resp.put("error", "Bad Request.");
        resp.put("message", "Malformed request syntax.");
        resp.put("status", 400);

        return new ResponseEntity<String>(resp.toString(), headers, HttpStatus.BAD_REQUEST);
    }
}

