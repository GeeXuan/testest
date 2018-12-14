package Controller;

import Entity.Orderlist;
import Entity.OrderlistService;
import Entity.Orders;
import Entity.OrdersService;
import Entity.Payment;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@WebServlet(name = "ChoosePayment", urlPatterns = {"/ChoosePayment"})
public class ChoosePayment extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    RequestDispatcher rd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        rd = request.getRequestDispatcher("ChoosePayment.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String service = request.getParameter("service");
        String paymentmethod = request.getParameter("payment");
        HttpSession session = request.getSession();
        session.setAttribute("service", service);
        session.setAttribute("paymentmethod", paymentmethod);
        if (service.equals("Delivery")) {
            response.sendRedirect("ChooseAddress");
        } else {
            response.sendRedirect("ChooseDate");
        }
    }
}
