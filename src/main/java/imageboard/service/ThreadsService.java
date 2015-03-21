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

    public boolean isThread(int id) {
        ThreadsModel thread = threadsDao.selectThreadById(id);
        return thread.isThread();
    }

    public ThreadsModel getThread(int id) {
        ThreadsModel thread = threadsDao.selectThreadById(id);
        List<PostsModel> replies = postsDao.selectPostsByParentId(thread.getId());
        thread.setReplies(replies);

        return thread;
    }

    public long lastActive(Integer id) {
        if (id == null) {
            return threadsDao.selectLastActiveDate();
        } else {
            long date = threadsDao.selectLastActiveDateByThreadId(id);
            return (date == 0) ? postsDao.selectPostDateById(id) : date;
        }
    }

    public int lastActiveThread() {
        return threadsDao.selectLastActiveThreadId();
    }

    public int countReplies(int id) {
        return threadsDao.countReplies(id);
    }

    public int countRepliesModel(int id) {
        ThreadsModel thread = getThread(id);
        return thread.getReplies().size();
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

    public List<ThreadsModel> getPaginatedThreads(Integer replylimit, Integer page, Integer perPage) {
        List<ThreadsModel> threads = getAllThreads(replylimit);

        double threadsCount = new Double(threads.size());
        int pagesCount = (int) Math.floor(Math.ceil(threadsCount/perPage));

        if (page > pagesCount) {
            page = pagesCount;
        }

        int startIndex = perPage*(page-1);
        int endIndex = startIndex + perPage;

        threads = threads.subList(startIndex, endIndex > threads.size() ? threads.size() : endIndex);
        return threads;
    }

    public int countThreads() {
        return threadsDao.countThreads();
    }

    public void createThread(ThreadsModel thread) {
		threadsDao.insertThread(thread.getUserId(),
			       thread.getParentId(),
                   new Date().getTime(),
			       thread.getImageUrl(),
			       thread.getContent(),
                   thread.getSubject());
    }
}
