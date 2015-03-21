package imageboard.controller;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.EmptyResultDataAccessException;

import imageboard.model.ThreadsModel;
import imageboard.service.ThreadsService;
import imageboard.util.JSONResponse;

@RestController
@RequestMapping("/api/threads")
public class ThreadsController {

    private static final Logger logger = Logger.getLogger( ThreadsController.class.getName() );
	private ThreadsService threadsService;

	@Autowired
	public ThreadsController(ThreadsService threadsService) {
		this.threadsService = threadsService;
	}

    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<String> getAllThreads(
            @RequestParam(value="replylimit", required=false) Integer replylimit,
            @RequestParam(value="paginate", required=false) Boolean paginate,
            @RequestParam(value="page", required=false, defaultValue="1") Integer page,
            @RequestParam(value="perpage", required=false, defaultValue="3") Integer perPage) throws JSONException {

        System.out.println(replylimit);
        List<ThreadsModel> threads = (paginate == null) ?
                threadsService.getAllThreads(replylimit) : threadsService.getPaginatedThreads(replylimit, page, perPage);

        return JSONResponse.buildGetAllResponse(threads);
    }

	@RequestMapping(method = RequestMethod.POST, produces="application/json")
	public ResponseEntity<String> postThread(@RequestBody ThreadsModel thread, UriComponentsBuilder b) throws JSONException {

        //TODO: Get id of created post;
        this.threadsService.createThread(thread);
        logger.log(Level.INFO, thread.getSubject());

        return JSONResponse.buildCreateResponse(b, 1);
	}

    @RequestMapping(value="/{id}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<String> getThread(@PathVariable int id) throws JSONException {

        if (threadsService.isThread(id)) {
            ThreadsModel thread = threadsService.getThread(id);
            return JSONResponse.buildGetResponse(thread);
        } else {
            return JSONResponse.buildNotFoundResponse();
        }

    }

    @RequestMapping(value="/{id}/meta", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<String> getMetaInfoById(@PathVariable int id) throws JSONException {

        return JSONResponse.buildMetaIdResponse(threadsService.countReplies(id), threadsService.lastActive(id));
    }

    @RequestMapping(value="/meta", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<String> getMetaInfo() throws JSONException {

        return JSONResponse.buildMetaInfoResponse(threadsService.countThreads(), threadsService.lastActive(null), threadsService.lastActiveThread());
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(Exception ex) throws JSONException {
        logger.log( Level.WARNING, ex.toString(), ex );

        return JSONResponse.buildBadRequestResponse();
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEmptyTable(Exception ex) throws JSONException {
        logger.log( Level.WARNING, ex.toString(), ex );

        return JSONResponse.buildNotFoundResponse();
    }
}

