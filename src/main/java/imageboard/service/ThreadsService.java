package imageboard.service;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            if (replylimit != null && replylimit >= 0 && replies.size() >= replylimit) {
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
