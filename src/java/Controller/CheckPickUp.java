/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Orders;
import Entity.OrdersService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Date;
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
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Saw
 */
@WebServlet(name = "CheckPickUp", urlPatterns = {"/CheckPickUp"})
public class CheckPickUp extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    RequestDispatcher rd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrdersService ordersService = new OrdersService(em);
        List<Orders> orderList = ordersService.findAll();
        List<Orders> orderToday = new LinkedList<>();
        List<Orders> orderTodayHistory = new LinkedList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        for (Orders orders : orderList) {
            Date pickupdate = orders.getOrderpickupdate();
            Date todaydate = new Date();
            if (sdf.format(pickupdate).equals(sdf.format(todaydate)) && orders.getOrderpickupmethod().equals("Pickup") && orders.getOrderreceivedtime() == null) {
                orderToday.add(orders);
            }
            if (sdf.format(pickupdate).equals(sdf.format(todaydate)) && orders.getOrderpickupmethod().equals("Pickup") && orders.getOrderreceivedtime() != null) {
                orderTodayHistory.add(orders);
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("orderToday", orderToday);
        session.setAttribute("orderTodayHistory", orderTodayHistory);

        rd = request.getRequestDispatcher("CheckPickUp.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            OrdersService ordersService = new OrdersService(em);
            int id = Integer.parseInt(request.getParameter("pickedup"));
            Orders order = ordersService.findOrdersByCode(id);
            order.setOrderreceivedtime(new Date());
            utx.begin();
            ordersService.updateOrders(order);
            utx.commit();
        } catch (Exception ex) {
        }
        doGet(request, response);
    }

}
