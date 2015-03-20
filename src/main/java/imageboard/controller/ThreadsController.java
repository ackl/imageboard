package imageboard.controller;

import java.util.List;

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

import imageboard.model.ThreadsModel;
import imageboard.service.ThreadsService;
import imageboard.util.JSONResponse;

@RestController
@RequestMapping("/api/threads")
public class ThreadsController {

	private ThreadsService threadsService;

	@Autowired
	public ThreadsController(ThreadsService threadsService) {
		this.threadsService = threadsService;
	}

    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<String> getAllThreads(@RequestParam(value="replylimit", required=false) Integer replylimit) throws JSONException {
        return JSONResponse.buildGetAllResponse(threadsService.getAllThreads(replylimit));
    }

	@RequestMapping(method = RequestMethod.POST, produces="application/json")
	public ResponseEntity<String> postThread(@RequestBody ThreadsModel thread, UriComponentsBuilder b) throws JSONException {
        //TODO: Get id of created post;
        this.threadsService.createThread(thread);
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

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch() throws JSONException {
        return JSONResponse.buildBadRequestResponse();
    }
}

