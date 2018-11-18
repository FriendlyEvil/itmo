package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageServlet extends HttpServlet {
    private static List<Message> messages = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = "";

        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        String find = uri.substring(uri.lastIndexOf('/') + 1);

        if (find.equals("auth")) {
            String user = request.getParameter("user");
            if (user == null) {
                user = "";
            }
            session.setAttribute("user", user);
            json = new Gson().toJson(user);
        } else if (find.equals("findAll")) {
            json = new Gson().toJson(messages.toArray());
        } else {
            String text = request.getParameter("text");
            String user = (String) session.getAttribute("user");

            messages.add(new Message(user, text));
        }
        response.getWriter().print(json);
        response.getWriter().flush();
    }

    static class Message {
        String user;
        String text;

        Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }
}
