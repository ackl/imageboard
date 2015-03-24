package imageboard.controller;

import java.util.List;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.UUID;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.beans.factory.annotation.Value;

import imageboard.model.ThreadsModel;
import imageboard.service.ThreadsService;
import imageboard.util.JSONResponse;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/threads")
public class ThreadsController {

    private static final Logger logger = Logger.getLogger( ThreadsController.class.getName() );
	private ThreadsService threadsService;

    @Value("${key1}")
    private String foo;

	@Autowired
	public ThreadsController(ThreadsService threadsService) {
		this.threadsService = threadsService;
	}

    @RequestMapping(value="/setactives", method = RequestMethod.GET)
    public String TestingYo() {
        List<ThreadsModel> threadsModels = threadsService.getAllThreads(null);

        for (ThreadsModel thread : threadsModels) {
            long lastActive = threadsService.lastActive(thread.getId());
            thread.setLastActive(lastActive);
            logger.info(Long.toString(lastActive));
            threadsService.updateLastActive(thread.getId(), lastActive);
        }

        return "hithere";
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    String uploadFileHandler(@RequestParam("file") MultipartFile file, @RequestParam("subject") String subject, @RequestParam("content") String content) {


        logger.info(foo);
        logger.info("in uploadf ile method");
        String name = String.valueOf(UUID.randomUUID());
        if (!file.isEmpty()) {
            logger.info("file is not empty!");
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("user.dir");
                //rootPath += "/src/main/webapp/public/dist/img";
                //String rootPath = ("/home/vagrant/image_uploads");
                File dir = new File(rootPath + File.separator + "src/main/webapp/public/dist/img");
                if (!dir.exists())
                    dir.mkdirs();

                //System.out.println(file.getOriginalFilename());
                String ogFileName = file.getOriginalFilename();
                String[] ogFileNameSplit = ogFileName.split("\\.");
                String fileExt = ogFileNameSplit[ogFileNameSplit.length - 1];
                name += "." + fileExt;
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());

                logger.info(subject);
                logger.info(content);
                logger.info(name);

                ThreadsModel thread = new ThreadsModel();
                thread.setSubject(subject);
                thread.setContent(content);
                thread.setImageUrl("/public/dist/img/" + name);
                this.threadsService.createThread(thread);
                logger.log(Level.INFO, thread.getSubject());
                return "You successfully uploaded file=" + name;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }

        } else {
                logger.info(subject);
                logger.info(content);
                logger.info(name);
            return "You failed to upload " + name
                    + " because the file was empty.";
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<String> getAllThreads(
            @RequestParam(value="replylimit", required=false) Integer replylimit,
            @RequestParam(value="paginate", required=false) Boolean paginate,
            @RequestParam(value="page", required=false, defaultValue="1") Integer page,
            @RequestParam(value="perpage", required=false, defaultValue="3") Integer perPage) throws JSONException {

        List<ThreadsModel> threads = (paginate == null) ?
                threadsService.getAllThreads(replylimit) : threadsService.getPaginatedThreads(replylimit, page, perPage);

        return JSONResponse.buildGetAllResponse(threads);
    }

	@RequestMapping(method = RequestMethod.POST, produces="application/json")
	public ResponseEntity<String> postThread(@RequestBody ThreadsModel thread, UriComponentsBuilder b) throws JSONException {

        //TODO: Get id of created post;
        long date = new Date().getTime();
        thread.setDate(date);
        this.threadsService.createThread(thread);
        this.threadsService.updateLastActive(thread.getId(), thread.getDate());
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

