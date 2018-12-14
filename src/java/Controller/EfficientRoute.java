/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Orders;
import Entity.OrdersService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Saw
 */
@WebServlet(name = "EfficientRoute", urlPatterns = {"/EfficientRoute"})
public class EfficientRoute extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    RequestDispatcher rd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrdersService ordersService = new OrdersService(em);
        List<Orders> orderList = ordersService.findAll();
        List<Orders> orderToday = new LinkedList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        for (Orders orders : orderList) {
            Date pickupdate = orders.getOrderpickupdate();
            Date todaydate = new Date();
            if (sdf.format(pickupdate).equals(sdf.format(todaydate)) && orders.getOrderpickupmethod().equals("Delivery")) {
                orderToday.add(orders);
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("orderToday", orderToday);

        rd = request.getRequestDispatcher("efficientRoute.jsp");
        rd.forward(request, response);
    }
}
