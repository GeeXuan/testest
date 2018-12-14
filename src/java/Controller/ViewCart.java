package Controller;

import Entity.Orderlist;
import Entity.OrderlistService;
import Entity.Orders;
import Entity.OrdersService;
import Entity.Product;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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
import javax.transaction.UserTransaction;

@WebServlet(name = "ViewCart", urlPatterns = {"/ViewCart"})
public class ViewCart extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    RequestDispatcher rd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Product> cartList = new LinkedList();
        if (session.getAttribute("cartList") != null) {
            cartList = (List<Product>) session.getAttribute("cartList");
        }
        session.setAttribute("cartList", cartList);
        rd = request.getRequestDispatcher("ViewCart.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Product> cartList = new LinkedList();
        if (session.getAttribute("cartList") != null) {
            cartList = (List<Product>) session.getAttribute("cartList");
        }
        int i = 0;
        for (i = 0; i < cartList.size(); i++) {
            if (request.getParameter("cancel" + i) != null) {
                cartList.remove(cartList.get(i));
                session.setAttribute("cartList", cartList);
                doGet(request, response);
            }
        }
        List<Orderlist> orderlists = new LinkedList();
        i = 0;
        for (Product product : cartList) {
            Orderlist orderlist = new Orderlist();
            orderlist.setProductid(product);
            orderlist.setProductquantity(Integer.parseInt(request.getParameter("quantity" + i)));
            orderlists.add(orderlist);
            i++;
        }
        session.setAttribute("orderlists", orderlists);
        session.setAttribute("totalPrice", Double.parseDouble(request.getParameter("totalPrice")));
        response.sendRedirect("ChoosePayment");
    }
}
