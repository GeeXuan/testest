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
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Saw
 */
@WebServlet(name = "AddAddress", urlPatterns = {"/AddAddress"})
public class AddAddress extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    RequestDispatcher rd;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String lotno = request.getParameter("lotno");
            String addressdetail = request.getParameter("address");

            int accountid = 0;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (javax.servlet.http.Cookie cookie : cookies) {
                    if (cookie.getName().equals("LoggedInId")) {
                        accountid = Integer.parseInt(cookie.getValue());
                    }
                }
            }
            AccountService accountService = new AccountService(em);;
            Account account = accountService.findAccountByCode(accountid);
            Address address = new Address(lotno, addressdetail, "Normal");
            AddressService addressService = new AddressService(em);
            address.setAccountid(account);
            address.setLotno(lotno);
            address.setAddressdetail(addressdetail);
            utx.begin();
            addressService.addAddress(address);
            utx.commit();
            if (request.getParameter("purchase") != null && request.getParameter("purchase").equals("true")) {
                response.sendRedirect("ChooseAddress");
            }
        } catch (Exception ex) {
            Logger.getLogger(AddAddress.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
