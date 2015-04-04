package imageboard.controller;

import java.io.IOException;
import java.util.List;
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


@RestController
@RequestMapping("/api/threads")
public class ThreadsController {

    private static final Logger logger = Logger.getLogger(ThreadsController.class.getName() );
	private ThreadsService threadsService;

    @Value("${imageUploadDirectory}")
    private String imageUploadDirectory;
    @Value("${imageDownloadDirectory}")
    private String imageDownloadDirectory;

	@Autowired
	public ThreadsController(ThreadsService threadsService) {
		this.threadsService = threadsService;
	}

	@RequestMapping(method = RequestMethod.POST, produces="application/json")
    public ResponseEntity<String> uploadFileHandler(
            @RequestParam("file") MultipartFile file,
            @RequestParam("subject") String subject,
            @RequestParam("content") String content,
            @RequestParam("image_url") String imageUrl,
            UriComponentsBuilder b,
            Principal principal) throws JSONException, IOException {

        String s3Key;
        ThreadsModel thread = new ThreadsModel();

        thread.setSubject(subject);
        thread.setContent(content);
        thread.setUserId(principal.getName());

        if (!file.isEmpty()) {
            s3Key = FileWriter.uploadFileToS3(file);
        } else {
            s3Key = FileWriter.downloadFileToS3(imageUrl);
        }

        thread.setImageUrl("http://clanboard-1234.s3-us-west-2.amazonaws.com/"+s3Key);

        this.threadsService.createThread(thread);
        return JSONResponse.buildCreateResponse(b, threadsService.lastActiveThread());
    }

    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<String> getAllThreads(
            @RequestParam(value="replylimit", required=false) Integer replylimit,
            @RequestParam(value="paginate", required=false) Boolean paginate,
            @RequestParam(value="page", required=false, defaultValue="1") Integer page,
            @RequestParam(value="sortby", required=false) String sortBy,
            @RequestParam(value="perpage", required=false, defaultValue="3") Integer perPage) throws JSONException {

        List<ThreadsModel> threads;
        logger.log(Level.WARNING, sortBy);
        if (sortBy.equals("lastactive")) {
            threads = (paginate == null) ?
                    threadsService.getAllThreads(replylimit) : threadsService.getPaginatedThreads(replylimit, page, perPage);
        } else {
            threads = (paginate == null) ?
                    threadsService.getAllThreadsByPopularity(replylimit) : threadsService.getPaginatedThreadsByPopularity(replylimit, page, perPage);
        }

        return JSONResponse.buildGetAllResponse(threads);
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

