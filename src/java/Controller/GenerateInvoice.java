/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Payment;
import Entity.PaymentService;
import com.sun.java.swing.plaf.windows.resources.windows;
import java.io.IOException;
import java.util.LinkedList;
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
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author Saw
 */
@WebServlet(name = "GenerateInvoice", urlPatterns = {"/GenerateInvoice"})
public class GenerateInvoice extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    RequestDispatcher rd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie cookies[] = request.getCookies();
        int accountid = 0;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("LoggedInId")) {
                    accountid = Integer.parseInt(cookie.getValue());
                }
            }
        }
        List<Payment> paymentList = new LinkedList();
        PaymentService paymentService = new PaymentService(em);
        paymentList = paymentService.findAll();
        for (Payment payment : paymentList) {
            if (payment.getOrderid().getAccountid().getAccountid() != accountid
                    && !payment.getPaymentmethod().equals("climit")
                    && payment.getPaymentstatus() != 0) {
                paymentList.remove(payment);
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("paymentList", paymentList);

        response.sendRedirect("Invoice.jsp");
    }

}
