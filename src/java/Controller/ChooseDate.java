/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Account;
import Entity.AccountService;
import Entity.Corporate;
import Entity.CorporateService;
import Entity.Orderlist;
import Entity.Orders;
import Entity.OrdersService;
import Entity.Payment;
import Entity.PaymentService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "ChooseDate", urlPatterns = {"/ChooseDate"})
public class ChooseDate extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    RequestDispatcher rd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        rd = request.getRequestDispatcher("ChooseDate.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String datetime = request.getParameter("date") + " " + request.getParameter("time");
            SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date pickupDate = sdfdate.parse(datetime);
            HttpSession session = request.getSession();

            String service = (String) session.getAttribute("service");
            String paymentmethod = (String) session.getAttribute("paymentmethod");
            List<Orderlist> orderlists = new LinkedList<>();
            Orders order = new Orders();
            OrdersService orderService = new OrdersService(em);
            if (session.getAttribute("orderlists") != null) {
                orderlists = (List<Orderlist>) session.getAttribute("orderlists");
            }
            for (Orderlist orderlist : orderlists) {
                orderlist.setOrderid(order);
            }
            Date date = new Date();
            order.setOrderlistList(orderlists);
            order.setOrderdate(date);
            order.setOrderpickupmethod(service);
            order.setOrderprice((double) session.getAttribute("totalPrice"));
            order.setOrderpickupdate(pickupDate);
            Cookie[] cookies = request.getCookies();
            int accountid = 0;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("LoggedInId")) {
                        accountid = Integer.parseInt(cookie.getValue());
                    }
                }
            }
            Payment payment = new Payment();
            payment.setPaymentmethod(paymentmethod);
            payment.setPaymentstatus(0);
            payment.setOrderid(order);
            order.setPaymentid(payment);
            AccountService accountService = new AccountService(em);;
            Account account = accountService.findAccountByCode(accountid);
            order.setAccountid(account);
            utx.begin();
            orderService.addOrders(order);
            utx.commit();
            if (paymentmethod.equals("climit")) {
                CorporateService corporateService = new CorporateService(em);
                Corporate corporate = account.getCorporateid();
                double debt = (double) session.getAttribute("totalPrice") + corporate.getDebt();
                corporate.setDebt(debt);
                utx.begin();
                corporateService.updateCorporate(corporate);
                utx.commit();
            }
            response.sendRedirect("PurchaseSuccessful.jsp");
        } catch (Exception ex) {
            Logger.getLogger(ChooseAddress.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
