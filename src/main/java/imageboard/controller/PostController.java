package imageboard.controller;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

import model.Posts;


@RestController
public class PostController {
    private final AtomicLong counter = new AtomicLong();
    private ArrayList<Posts> postModels = new ArrayList<Posts>();

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public @ResponseBody ArrayList<Posts> getPostModels() {
        return postModels;
    }

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public @ResponseBody Posts createPost(@RequestBody Posts postInstance) {
        postInstance.setTime();
        postInstance.setId(counter.incrementAndGet());
        postModels.add(postInstance);
        return postInstance;
    }
}

