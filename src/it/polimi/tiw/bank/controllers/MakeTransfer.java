package it.polimi.tiw.bank.controllers;

import it.polimi.tiw.bank.beans.User;
import it.polimi.tiw.bank.utils.ClientHandler;
import it.polimi.tiw.bank.utils.MultiPathMessageResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/MakeTransfer")
public class MakeTransfer extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection connection;
    private TemplateEngine templateEngine;

    public MakeTransfer() {
        super();
    }

    @Override
    public void init() throws ServletException {
        connection = ClientHandler.getConnection(getServletContext());
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver( servletContext );
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        this.templateEngine.setMessageResolver(new MultiPathMessageResolver(servletContext, "i18n"));
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Get user from the session
        User user = new User();
        HttpSession httpSession = req.getSession();
        user = (User) httpSession.getAttribute("user");

        String originAccountIdString, DestinationUserIdString, destinationAccountIdString, causal, amountString;
        int originAccountId, DestinationUserId, destinationAccountId;
        long amount;

        // Get transfer's information from the request
        try {
            originAccountIdString = req.getParameter("origin");
            DestinationUserIdString = req.getParameter("user");
            destinationAccountIdString = req.getParameter("account");
            causal = req.getParameter("causal");
            amountString = req.getParameter("amount");
        } catch (NullPointerException e) {
            e.printStackTrace(); // TODO: remove after test

            // Redirect to accountStatus.html with error message
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", "Parameters can't be empty");
            String path = "/accountStatus.html";
            templateEngine.process(path, ctx, resp.getWriter());
            return;
        }

        // Check origin account id and parseInt
        if (originAccountIdString!=null && !originAccountIdString.equals("")) {
            try {
                originAccountId = Integer.parseInt(originAccountIdString);
            } catch (NumberFormatException e) {
                // Redirect to transferError.html with error message
                ServletContext servletContext = getServletContext();
                final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
                ctx.setVariable("errorMessage", "Origin Account ID must be an integer");
                String path = "/transferError.html";
                templateEngine.process(path, ctx, resp.getWriter());
                return;
            }
        } else {
            // Redirect to transferError.html with error message
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", "Origin Account ID can't be empty");
            String path = "/transferError.html";
            templateEngine.process(path, ctx, resp.getWriter());
            return;
        }

        // check user id and parseInt
        if (DestinationUserIdString!=null && !DestinationUserIdString.equals("")) {
            try {
                DestinationUserId = Integer.parseInt(DestinationUserIdString);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // TODO: remove after test

                // Redirect to accountStatus.html with error message
                ServletContext servletContext = getServletContext();
                final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
                ctx.setVariable("originAccount", originAccountId);
                ctx.setVariable("userIdErrorMessage", "Destination User ID must be an integer");
                ctx.setVariable("destinationAccount", destinationAccountIdString);
                ctx.setVariable("causal", causal);
                ctx.setVariable("amount", amountString);
                String path = "/accountStatus.html";
                templateEngine.process(path, ctx, resp.getWriter());
                return;
            }
        } else {
            // Redirect to accountStatus.html with error message
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("originAccount", originAccountId);
            ctx.setVariable("userIdErrorMessage", "Destination User ID can't be empty");
            ctx.setVariable("destinationAccount", destinationAccountIdString);
            ctx.setVariable("causal", causal);
            ctx.setVariable("amount", amountString);
            String path = "/accountStatus.html";
            templateEngine.process(path, ctx, resp.getWriter());
            return;
        }

        // Check account id and parseInt
        if (destinationAccountIdString!=null && !destinationAccountIdString.equals("")) {
            try {
                destinationAccountId = Integer.parseInt(destinationAccountIdString);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // TODO: remove after test

                // Redirect to accountStatus.html with error message
                ServletContext servletContext = getServletContext();
                final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
                ctx.setVariable("originAccount", originAccountId);
                ctx.setVariable("userId", DestinationUserId);
                ctx.setVariable("destinationAccountErrorMessage", "Destination Account ID must be an integer");
                ctx.setVariable("causal", causal);
                ctx.setVariable("amount", amountString);
                String path = "/accountStatus.html";
                templateEngine.process(path, ctx, resp.getWriter());
                return;
            }
        } else {
            // Redirect to accountStatus.html with error message
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("originAccount", originAccountId);
            ctx.setVariable("userId", DestinationUserId);
            ctx.setVariable("destinationAccountErrorMessage", "Destination Account ID can't be empty");
            ctx.setVariable("causal", causal);
            ctx.setVariable("amount", amountString);
            String path = "/accountStatus.html";
            templateEngine.process(path, ctx, resp.getWriter());
            return;
        }

        // Check causal
        if (causal==null || causal.equals("")) {
            // Redirect to accountStatus.html with error message
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("originAccount", originAccountId);
            ctx.setVariable("userId", DestinationUserId);
            ctx.setVariable("destinationAccount", destinationAccountId);
            ctx.setVariable("causalErrorMessage", "Causal can't be empty");
            ctx.setVariable("amount", amountString);
            String path = "/accountStatus.html";
            templateEngine.process(path, ctx, resp.getWriter());
            return;
        }

        // Check amount and parseDouble
        if (amountString!=null && !amountString.equals("")) {
            try {
                amount = Long.parseLong(amountString);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // TODO: remove after test

                // Redirect to accountStatus.html with error message
                ServletContext servletContext = getServletContext();
                final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
                ctx.setVariable("originAccount", originAccountId);
                ctx.setVariable("userId", DestinationUserId);
                ctx.setVariable("destinationAccount", destinationAccountId);
                ctx.setVariable("causal", causal);
                ctx.setVariable("amountErrorMessage", "Amount must be a long");
                String path = "/accountStatus.html";
                templateEngine.process(path, ctx, resp.getWriter());
                return;
            }
        } else {
            // Redirect to accountStatus.html with error message
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("originAccount", originAccountId);
            ctx.setVariable("userId", DestinationUserId);
            ctx.setVariable("destinationAccount", destinationAccountId);
            ctx.setVariable("causal", causal);
            ctx.setVariable("amountErrorMessage", "Amount can't be empty");
            String path = "/accountStatus.html";
            templateEngine.process(path, ctx, resp.getWriter());
            return;
        }

        // Check that destination account's owner is destination user




    }

    @Override
    public void destroy() {
        try {
            ClientHandler.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: remove after test
        }
    }
}
