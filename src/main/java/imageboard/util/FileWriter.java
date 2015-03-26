package imageboard.util;

import java.util.UUID;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class FileWriter {
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
}
