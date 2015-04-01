package imageboard.service;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.json.JSONException;

import imageboard.model.PostsModel;
import imageboard.model.ThreadsModel;
import imageboard.model.UsersModel;
import imageboard.dao.PostsDao;
import imageboard.dao.ThreadsDao;

import java.util.logging.Logger;
import java.util.logging.Level;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import java.security.Principal;
import org.springframework.security.core.userdetails.UserDetails;

import imageboard.model.ThreadsModel;
import imageboard.service.ThreadsService;
import imageboard.util.JSONResponse;
import imageboard.util.FileWriter;

@Service
public class ThreadsService {

    private PostsDao postsDao;
    private ThreadsDao threadsDao;
    private UsersService usersService;
    private static final Logger logger = Logger.getLogger(ThreadsService.class.getName() );

    @Autowired
    public ThreadsService(PostsDao postsDao, ThreadsDao threadsDao, UsersService usersService) {
        this.postsDao = postsDao;
        this.threadsDao = threadsDao;
        this.usersService = usersService;
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

    public List<PostsModel> getAllReplies (int id) {
        List<PostsModel> replies = postsDao.selectPostsByParentId(id);
        return replies;
    }

    public List<ThreadsModel> getAllThreads(Integer replylimit) {
        List<ThreadsModel> threadsModels = threadsDao.selectAllThreads();
        for (ThreadsModel thread : threadsModels) {
            List<PostsModel> replies = postsDao.selectPostsByParentId(thread.getId());
            if (replylimit != null && replylimit >= 0 && replies.size() >= replylimit) {
                replies = replies.subList(0, replylimit);
            }

            for (PostsModel reply : replies) {
                try {
                    UsersModel user = usersService.selectUserByUsername(reply.getUserId());
                    reply.setUser(user);
                } catch (EmptyResultDataAccessException e) {
                    logger.log( Level.WARNING, e.toString(), e );
                    logger.log( Level.WARNING, "HELLO");
                }
            }

            thread.setReplies(replies);

            try {
                UsersModel user = usersService.selectUserByUsername(thread.getUserId());
                thread.setUser(user);
            } catch (EmptyResultDataAccessException e) {
                logger.log( Level.WARNING, e.toString(), e );
                logger.log( Level.WARNING, "HELLO");
            }
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

    public void updateLastActive(int id, long lastActive) {
        threadsDao.setLastActive(id, lastActive);
    }

    public void createThread(ThreadsModel thread) {
        long date = new Date().getTime();
        thread.setDate(date);
        threadsDao.insertThread(thread);
    }

    //@ExceptionHandler(EmptyResultDataAccessException.class)
    //public ResponseEntity<String> handleEmptyTable(Exception ex) throws JSONException {
        //logger.log( Level.WARNING, ex.toString(), ex );

        //return JSONResponse.buildNotFoundResponse();
    //}
}
