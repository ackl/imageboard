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
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.CannedAccessControlList;


import org.springframework.web.multipart.MultipartFile;

public class FileWriter {
    private static final Logger logger = Logger.getLogger(FileWriter.class.getName() );


    /**
     * Get file uploaded in form by user and save to s3 bucket.
     */
    public static String uploadFileToS3(MultipartFile formFile) throws IOException {
        String key = String.valueOf(UUID.randomUUID());

        String[] original = formFile.getOriginalFilename().split("\\.");
        String fileExt = original[original.length - 1];

        File file = FileWriter.createTempFile(formFile.getBytes(), "form", fileExt);

        FileWriter.s3PutObject("clanboard-1234", key, file);
        return key;
    }

    /**
     * Get remote file from user specified url and save to s3 bucket.
     */
    public static String downloadFileToS3(String imageUrl) throws IOException {

        InputStream inputStream = null;

        String name = String.valueOf(UUID.randomUUID());
        String[] original = imageUrl.split("\\.");
        String fileExt = original[original.length - 1];


        try {
            URL url = new URL(imageUrl);
            inputStream = url.openStream();
            byte[] buffer = new byte[2048];

            File file = FileWriter.createTempFile(buffer, "url", fileExt, inputStream);
            FileWriter.s3PutObject("clanboard-1234", name, file);

        } catch (MalformedURLException e) {
            logger.log( Level.WARNING, e.toString(), e );
        } catch (FileNotFoundException e) {
            logger.log( Level.WARNING, e.toString(), e );
        } finally {
            inputStream.close();
        }

        return name;
    }


    public static File createTempFile(byte[] bytes, String source, String fileExt) throws IOException {
        return FileWriter.createTempFile(bytes, source, fileExt, null);
    }

    public static File createTempFile(byte[] bytes, String source, String fileExt, InputStream inputStream) throws IOException {

        BufferedOutputStream stream = null;

        File file = File.createTempFile("tmp_image", "." + fileExt);
        file.deleteOnExit();

        stream = new BufferedOutputStream(new FileOutputStream(file));

        if (source == "url") {
            int length;
            while ((length = inputStream.read(bytes)) != -1) {
                stream.write(bytes, 0, length);
            }
        } else if (source == "form") {
            stream.write(bytes);
        }

        stream.close();

        return file;
    }

    public static void s3PutObject(String bucket, String key, File file) {
        AmazonS3 s3 = new AmazonS3Client();
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        s3.setRegion(usWest2);

        try {
            s3.putObject(new PutObjectRequest(bucket, key, file).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonServiceException ase) {
            logger.log(Level.WARNING, "Error Message:    " + ase.getMessage());
            logger.log(Level.WARNING, "AWS Error Code:   " + ase.getErrorCode());
            logger.log(Level.WARNING, "Error Type:       " + ase.getErrorType());
        } catch (AmazonClientException ace) {
            logger.log(Level.WARNING, "Error Message: " + ace.getMessage());
        }
    }
}
