package imageboard.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import imageboard.model.PostsModel;
import imageboard.model.ThreadsModel;
import imageboard.dao.PostsDao;
import imageboard.dao.ThreadsDao;

@Service
public class ThreadsService {

    private PostsDao postsDao;
    private ThreadsDao threadsDao;

    @Autowired
    public ThreadsService(PostsDao postsDao, ThreadsDao threadsDao) {
        this.postsDao = postsDao;
        this.threadsDao = threadsDao;
    }

    public List<ThreadsModel> getAllThreads(Integer replylimit) {
        List<ThreadsModel> threadsModels = threadsDao.selectAllThreads();
        for (ThreadsModel thread : threadsModels) {
            List<PostsModel> replies = postsDao.selectPostsByParentId(thread.getId());
            if (replylimit != null && replylimit > 0 && replies.size() >= replylimit) {
                replies = replies.subList(0, replylimit);
            }

            thread.setReplies(replies);
        }

        return threadsModels;
    }

    public void createThread(ThreadsModel thread) {
		threadsDao.insertThread(thread.getUserId(),
			       thread.getParentId(),
                   new Date().getTime(),
			       thread.getImageUrl(),
			       thread.getContent(),
                   thread.getSubject());
    }

    public ThreadsModel getThread(int id) {
        ThreadsModel thread = threadsDao.selectThreadById(id);
        List<PostsModel> replies = postsDao.selectPostsByParentId(thread.getId());
        thread.setReplies(replies);

        return thread;
    }

    public boolean isThread(int id) {
        ThreadsModel thread = threadsDao.selectThreadById(id);
        return thread.isThread();
    }
}






        //ThreadsModel thread = threadsDao.selectThreadById(id);
        //JSONObject threadJSON = new JSONObject();

        //if (thread.isThread()) {
            //List<PostsModel> replies = postsDao.selectPostsByParentId(thread.getId());
            //thread.setReplies(replies);

            //threadJSON = buildPostJSON(threadJSON, thread);
            //threadJSON.put("subject", thread.getSubject());

            //JSONArray repliesJSON = new JSONArray();

            //for (PostsModel reply : replies) {
                //JSONObject replyJSON = new JSONObject();
                //buildPostJSON(replyJSON, reply);
                //replyJSON.put("parent_id", reply.getParentId());
                //repliesJSON.put(replyJSON);
            //}

            //threadJSON.put("replies", repliesJSON);

        //} else {
            //threadJSON.put("error", "File not found.");
            //threadJSON.put("message", "No thread exists with this id.");
            //threadJSON.put("status", 404);
        //}

        //return threadJSON.toString();
