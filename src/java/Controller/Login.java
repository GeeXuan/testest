package Controller;

import Entity.Account;
import Entity.AccountService;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    RequestDispatcher rd;
    @Resource
    UserTransaction utx;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        rd = request.getRequestDispatcher("Login.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AccountService accountService = new AccountService(em);
        if (accountService.findAll() != null) {
            List<Account> accountList = accountService.findAll();
            if (!accountList.isEmpty()) {
                for (Account account : accountList) {
                    if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                        if (account.getCorporateid() == null) {
                            Cookie namecookie = new Cookie("LoggedInName", account.getUsersid().getName());
                            response.addCookie(namecookie);
                            Cookie accountidcookie = new Cookie("LoggedInId", account.getAccountid() + "");
                            response.addCookie(accountidcookie);
                            Cookie typecookie = new Cookie("UserType", account.getUsersid().getRoles());
                            response.addCookie(typecookie);
                        } else {
                            Cookie namecookie = new Cookie("LoggedInName", account.getCorporateid().getCorporatename());
                            response.addCookie(namecookie);
                            Cookie accountidcookie = new Cookie("LoggedInId", account.getAccountid() + "");
                            response.addCookie(accountidcookie);
                            Cookie typecookie = new Cookie("UserType", "Corporate");
                            response.addCookie(typecookie);
                        }
                        response.sendRedirect("index.jsp");
                        return;
                    }
                }
            }
        }
        request.setAttribute("relogin", "Incorrect username or password.");
        request.setAttribute("username", username);
        rd = request.getRequestDispatcher("Login.jsp");
        rd.forward(request, response);
    }

}
