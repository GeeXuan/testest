/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Account;
import Entity.AccountService;
import Entity.Address;
import Entity.AddressService;
import Entity.Corporate;
import Entity.CorporateService;
import Entity.Orderlist;
import Entity.Orders;
import Entity.OrdersService;
import Entity.Payment;
import java.io.IOException;
import java.util.Calendar;
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

@WebServlet(name = "ChooseAddress", urlPatterns = {"/ChooseAddress"})
public class ChooseAddress extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    RequestDispatcher rd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        int accountid = 0;
        if (cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().equals("LoggedInId")) {
                    accountid = Integer.parseInt(cookie.getValue());
                }
            }
        }
        AccountService accountService = new AccountService(em);
        Account account = accountService.findAccountByCode(accountid);
        AddressService addressService = new AddressService(em);
        List<Address> addressList = addressService.findAll();
        for (Address address : addressList) {
            if (!address.getAccountid().equals(account)) {
                addressList.remove(address);
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("addressList", addressList);

        rd = request.getRequestDispatcher("ChooseAddress.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            int addressid = Integer.parseInt(request.getParameter("address"));

            String service = (String) session.getAttribute("service");
            String paymentmethod = (String) session.getAttribute("paymentmethod");

            AddressService addressService = new AddressService(em);
            Address address = addressService.findAddressByCode(addressid);

            List<Orderlist> orderlists = new LinkedList<>();
            Orders order = new Orders();
            OrdersService orderService = new OrdersService(em);
            if (session.getAttribute("orderlists") != null) {
                orderlists = (List<Orderlist>) session.getAttribute("orderlists");
            }
            for (Orderlist orderlist : orderlists) {
                orderlist.setOrderid(order);
            }
            int accountid = 0;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("LoggedInId")) {
                        accountid = Integer.parseInt(cookie.getValue());
                    }
                }
            }
            Date date = new Date();
            order.setOrderlistList(orderlists);
            order.setOrderdate(date);
            order.setAddressid(address);
            AccountService accountService = new AccountService(em);;
            Account account = accountService.findAccountByCode(accountid);
            order.setAccountid(account);
            order.setOrderpickupmethod(service);
            order.setOrderprice((double) session.getAttribute("totalPrice"));
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 5);
            order.setOrderpickupdate(cal.getTime());
            Payment payment = new Payment();
            payment.setPaymentmethod(paymentmethod);
            payment.setPaymentstatus(0);
            payment.setOrderid(order);
            order.setPaymentid(payment);
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
