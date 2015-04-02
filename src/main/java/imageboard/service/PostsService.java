package imageboard.service;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.List;
import java.util.Date;
import java.security.Principal;

import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
import org.springframework.security.core.userdetails.UserDetails;

import imageboard.model.PostsModel;
import imageboard.model.ThreadsModel;
import imageboard.model.UsersModel;
import imageboard.dao.PostsDao;
import imageboard.dao.ThreadsDao;
import imageboard.service.ThreadsService;
import imageboard.util.JSONResponse;
import imageboard.util.FileWriter;

@Service
public class PostsService {

    private PostsDao postsDao;
    private ThreadsDao threadsDao;
    private UsersService usersService;

    private static final Logger logger = Logger.getLogger(ThreadsService.class.getName() );

    @Autowired
    public PostsService(PostsDao postsDao, ThreadsDao threadsDao, UsersService usersService) {
        this.postsDao = postsDao;
        this.threadsDao = threadsDao;
        this.usersService = usersService;
    }

    //public PostsModel getPostById(int id) {
        //PostsModel post = PostsDao.selectPostById(id);
        //try {
            //UsersModel user = usersService.selectUserByUsername(post.getUserId());
            //post.setUser(user);
        //} catch (EmptyResultDataAccessException e) {
            //logger.log( Level.WARNING, e.toString(), e );
            //logger.log( Level.WARNING, "HELLO");
        //}

        //return post;
    //}
    public List<PostsModel> getPostsByUsername(String username) {
        List<PostsModel> postsModels = postsDao.selectPostsByUsername(username);
        return postsModels;
    }

    public PostsModel getPostById(int id) {
        PostsModel post = postsDao.selectPostById(id);
        try {
            UsersModel user = usersService.selectUserByUsername(post.getUserId());
            post.setUser(user);
        } catch (EmptyResultDataAccessException e) {
            logger.log( Level.WARNING, e.toString(), e );
            logger.log( Level.WARNING, "HELLO");
        }
        return post;
    }

    public List<PostsModel> getAllPosts() {
        List<PostsModel> postsModels = postsDao.selectAllPosts();
        for (PostsModel post : postsModels) {
            try {
                UsersModel user = usersService.selectUserByUsername(post.getUserId());
                post.setUser(user);
            } catch (EmptyResultDataAccessException e) {
                logger.log( Level.WARNING, e.toString(), e );
                logger.log( Level.WARNING, "HELLO");
            }
        }
        return postsModels;
    }
}
