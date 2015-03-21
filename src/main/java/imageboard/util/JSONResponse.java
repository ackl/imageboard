package imageboard.util;

import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import imageboard.model.PostsModel;
import imageboard.model.ThreadsModel;

public class JSONResponse {

    public static ResponseEntity<String> buildMetaInfoResponse(int count, long date, int threadId) throws JSONException {
        JSONObject resp = new JSONObject();
        resp.put("thread_count", count);
        resp.put("last_active_timestamp", date);
        resp.put("last_active_thread_id", threadId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(resp.toString());
    }

    public static ResponseEntity<String> buildMetaIdResponse(int count, long date) throws JSONException {
        JSONObject resp = new JSONObject();
        resp.put("reply_count", count);
        resp.put("last_active_timestamp", date);

        return ResponseEntity.status(HttpStatus.OK)
                .body(resp.toString());
    }


    public static ResponseEntity<String> buildGetResponse(ThreadsModel thread) throws JSONException {

        String resp = JSONResponse.populateThreadJSON(thread).toString();

        return ResponseEntity.status(HttpStatus.OK)
                .body(resp);
    }

    public static ResponseEntity<String> buildGetAllResponse(List<ThreadsModel> threads) throws JSONException {

        JSONArray resp = new JSONArray();
        for (ThreadsModel thread: threads) {
            resp.put(JSONResponse.populateThreadJSON(thread));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(resp.toString());
    }

    public static JSONObject populateThreadJSON(ThreadsModel thread) throws JSONException {

        JSONObject threadJSON = new JSONObject();
        threadJSON = JSONResponse.populatePostJSON(threadJSON, thread);
        threadJSON.put("subject", thread.getSubject());

        JSONArray repliesJSON = new JSONArray();

        for (PostsModel reply : thread.getReplies()) {
            JSONObject replyJSON = new JSONObject();
            JSONResponse.populatePostJSON(replyJSON, reply);
            replyJSON.put("parent_id", reply.getParentId());
            repliesJSON.put(replyJSON);
        }

        threadJSON.put("replies", repliesJSON);

        return threadJSON;
    }

    public static JSONObject populatePostJSON(JSONObject json, PostsModel post) throws JSONException {

        json.put("id", post.getId());
        json.put("image_url", post.getImageUrl());
        json.put("content", post.getContent());
        json.put("date", post.getDate());
        json.put("user_id", post.getUserId());

        return json;
    }

	public static ResponseEntity<String> buildCreateResponse(UriComponentsBuilder b, int resourceId) throws JSONException {

        // Build URI to created resource for response header.
        UriComponents uriComponents = b.path("/posts/{id}").buildAndExpand(resourceId);

        JSONObject resp = new JSONObject();
        resp.put("message", "Post created.");
        resp.put("status", 200);

        return ResponseEntity.created(uriComponents.toUri())
                .header("resourceId", Integer.toString(resourceId))
                .body(resp.toString());
	}

    public static ResponseEntity<String> buildNotFoundResponse() throws JSONException {

        JSONObject resp = new JSONObject();
        resp.put("error", "File not found.");
        resp.put("message", "No thread exists with this id.");
        resp.put("status", 404);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header("yo", "sup")
                .body(resp.toString());
    }

    public static ResponseEntity<String> buildBadRequestResponse() throws JSONException {

        JSONObject resp = new JSONObject();
        resp.put("error", "Bad Request.");
        resp.put("message", "Malformed request syntax.");
        resp.put("status", 400);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(resp.toString());
    }
}
