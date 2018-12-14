/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Account;
import Entity.AccountService;
import Entity.Users;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    RequestDispatcher rd;
    @Resource
    UserTransaction utx;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        rd = request.getRequestDispatcher("Register.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String ic = request.getParameter("ic");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            request.setAttribute("username", username);
            request.setAttribute("name", name);
            request.setAttribute("ic", ic);
            request.setAttribute("phone", phone);
            request.setAttribute("email", email);
            AccountService accountService = new AccountService(em);
            if (accountService.findAll() != null) {
                List<Account> accountList = accountService.findAll();
                if (!accountList.isEmpty()) {
                    for (Account account : accountList) {
                        if (account.getUsername().equals(username)) {
                            request.setAttribute("reregister", "exist");
                            rd = request.getRequestDispatcher("Register.jsp");
                            rd.forward(request, response);
                        }
                    }
                }
            }
            if (password.length() < 6) {
                request.setAttribute("reregister", "wrong");
                rd = request.getRequestDispatcher("Register.jsp");
                rd.forward(request, response);
            }
            Account account = new Account(username, password);
            Users users = new Users(name, ic, phone, email, "Customer");
            account.setUsersid(users);
            users.setAccountid(account);
            utx.begin();
            accountService.addAccount(account);
            utx.commit();
            rd = request.getRequestDispatcher("RegisterConfirm.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
