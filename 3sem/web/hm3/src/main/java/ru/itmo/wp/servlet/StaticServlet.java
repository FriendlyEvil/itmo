package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticServlet extends HttpServlet {
    private static final String staticPath = "./src\\main\\webapp\\static\\";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();

        getFiles(uri, response);
    }

    private File getFile(String uri) {
        File file = new File(staticPath + uri);
        if (!file.isFile()) {
            file = new File(getServletContext().getRealPath("/static" + uri));
        }
        return file;
    }

    private void getFiles(String file_in, HttpServletResponse response) throws IOException {
        String[] strings = file_in.split("\\+");
        response.setContentType(getContentTypeFromName(strings[0]));
        OutputStream outputStream = response.getOutputStream();
        for (String fileName : strings) {
            File file = getFile(fileName);
            if (file.isFile()) {
                Files.copy(file.toPath(), outputStream);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        outputStream.flush();
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }


}
