package imageboard.util;

import java.util.UUID;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import java.util.logging.Level;


import org.springframework.web.multipart.MultipartFile;

public class FileWriter {
    private static final Logger logger = Logger.getLogger(FileWriter.class.getName() );

    public static String writeFile(MultipartFile file, String imageUploadDirectory, String imageDownloadDirectory) {
        try {
            String name = String.valueOf(UUID.randomUUID());
            byte[] bytes = file.getBytes();

            String rootPath = System.getProperty("user.dir");
            File dir = new File(rootPath + File.separator + imageUploadDirectory);
            if (!dir.exists())
                dir.mkdirs();

            // Get file extension
            String[] original = file.getOriginalFilename().split("\\.");
            String fileExt = original[original.length - 1];

            name += "." + fileExt;

            // Create the file on server
            File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
            return imageDownloadDirectory + name;
        } catch (Exception e) {
            return "fail";
        }
    }

    public static String downloadFile(String imageUrl, String imageUploadDirectory, String imageDownloadDirectory) {
        //String imageUrl = "http://www.technicalkeeda.com/img/images/article/spring-file-upload-eclipse-setup.png";
        InputStream inputStream = null;
        OutputStream outputStream = null;

        String name = String.valueOf(UUID.randomUUID());
        String rootPath = System.getProperty("user.dir");
        File dir = new File(rootPath + File.separator + imageUploadDirectory);
        if (!dir.exists())
            dir.mkdirs();

        String[] original = imageUrl.split("\\.");
        String fileExt = original[original.length - 1];
        name += "." + fileExt;

        try {
            URL url = new URL(imageUrl);
            inputStream = url.openStream();
            outputStream = new FileOutputStream(dir.getAbsolutePath() + "/" + name);

            byte[] buffer = new byte[2048];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } catch (MalformedURLException e) {
            logger.log( Level.WARNING, e.toString(), e );

        } catch (FileNotFoundException e) {
            logger.log( Level.WARNING, e.toString(), e );

        } catch (IOException e) {
            logger.log( Level.WARNING, e.toString(), e );

        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                logger.log( Level.WARNING, e.toString(), e );
            }
        }
        return imageDownloadDirectory + name;
    }
}
