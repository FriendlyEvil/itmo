package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class CaptchaFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Object captcha = session.getAttribute("captcha");
        if (captcha == null) {
            session.setAttribute("back_url", request.getRequestURL().toString());
        }
        if (captcha == null || !captcha.equals("true")) {
            String uri = request.getRequestURI();
            if (uri.equals("/captcha")) {
                int ans = Integer.parseInt(request.getParameter("captcha"));
                if (ans == (int) session.getAttribute("captcha")) {
                    session.setAttribute("captcha", "true");
                    doneCaptcha(session, response);
//                    response.sendRedirect();
                } else {
                    setCaptcha(session, response);
                }
            } else if (uri.startsWith("/captcha.png")) {
                getCaptchaImage(session, response);
            } else {
                setCaptcha(session, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private void getCaptchaImage(HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        OutputStream stream = response.getOutputStream();
        int ans = (int) session.getAttribute("captcha");
        stream.write(ImageUtils.toPng(String.valueOf(ans)));
        stream.flush();
    }

    private void setCaptcha(HttpSession session, HttpServletResponse response) throws IOException {
        int rnd = getRandom();
        session.setAttribute("captcha", rnd);
        Writer writer = response.getWriter();
        writer.write(HtmlGenerate.generateCaptchaHtml());
        writer.flush();
    }

    private void doneCaptcha(HttpSession session,HttpServletResponse response) throws IOException {
        Writer writer = response.getWriter();
        writer.write(HtmlGenerate.doneCaptcha((String) session.getAttribute("back_url")));
        writer.flush();
    }

    private int getRandom() {
        return 100 + (int) (Math.random() * 899);
    }

    static class HtmlGenerate {
        static String generateCaptchaHtml() {
            return getCode("<img src=\"captcha.png\"/>\n" +
                    "<form action=\"captcha\" method=\"post\">\n" +
                    "<label>Enter number:</label>\n" +
                    "<input name=\"captcha\" type=\"number\" maxlength=\"3\">\n" +
                    "</form>\n");
        }

        static String doneCaptcha(String href) {
            return getCode("<a href=\"" + href + "\">Click to continue</a>\n");
        }

        private static String getCode(String str) {
            return start() + str + end();
        }

        private static String start() {
            return "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "<meta charset=\"UTF-8\">\n" +
                    "</head>\n" +
                    "<title>Captcha</title>\n" +
                    "<body>\n";
        }

        private static String end() {
            return "</body>\n" +
                    "</html>\n";
        }
    }
}
