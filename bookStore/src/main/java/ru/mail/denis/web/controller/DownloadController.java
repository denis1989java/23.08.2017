package ru.mail.denis.web.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mail.denis.service.NewService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by Denis Monich on 17.08.2017.
 */

@Controller
public class DownloadController {

    private static final Logger logger = Logger.getLogger(DownloadController.class);

    private final NewService newService;

    @Autowired
    public DownloadController(NewService newService) {
        this.newService = newService;
    }

    @RequestMapping(value = {"/download/{id}"}, method = RequestMethod.GET)
    public void download(HttpServletResponse response, @PathVariable Integer id) throws IOException {

        File file = newService.findNewsFotoById(id);
        if (!file.exists()) {
            String errorMessage = "Sorry. The file you are looking for does not exist";
            logger.info(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }

        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            logger.info("Mime type is not detectable, will take default");
            mimeType = "application/octet-stream";
        }

        logger.info("Mime type : " + mimeType);
        response.setContentType(mimeType);

        response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", file.getName()));
        response.setContentLength((int) file.length());

        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStream inputStream = new BufferedInputStream(fileInputStream)
        ) {
            //Copy bytes from source to destination(outputstream in this example), closes both streams.
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }
}
