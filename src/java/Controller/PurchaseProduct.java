package Controller;

import Entity.Account;
import Entity.AccountService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entity.Product;
import Entity.ProductService;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

@WebServlet(name = "PurchaseProduct", urlPatterns = {"/PurchaseProduct"})
public class PurchaseProduct extends HttpServlet {

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
        String userType = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("LoggedInId")) {
                    accountid = Integer.parseInt(cookie.getValue());
                }
                if (cookie.getName().equals("UserType")) {
                    userType = cookie.getValue();
                }
            }
        }
        if (userType.equals("Corporate")) {
            Calendar calendar = Calendar.getInstance();
            if (calendar.get(Calendar.DAY_OF_MONTH) < 7) {
                calendar.add(Calendar.MONTH, -1);
            }
            calendar.set(Calendar.DAY_OF_MONTH, 7);
            AccountService accountService = new AccountService(em);
            Account account = accountService.findAccountByCode(accountid);
            Date lastpayment = account.getCorporateid().getLastpayment();
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastpayment);
            if ((account.getCorporateid().getDebt() != 0 && cal.compareTo(calendar) < 0) || account.getCorporateid().getDebt() > account.getCorporateid().getAccountlimit()) {
                rd = request.getRequestDispatcher("Debt.jsp");
                rd.forward(request, response);
            }
        }
        ProductService productService = new ProductService(em);
        List<Product> productList = productService.findAll();
        HttpSession session = request.getSession();
        session.setAttribute("productList", productList);
        rd = request.getRequestDispatcher("PurchaseProduct.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!request.getParameter("addcart").equals("Proceed")) {
            HttpSession session = request.getSession();
            int productId = Integer.parseInt(request.getParameter("addcart"));
            List<Product> cartList = new LinkedList<>();
            if (session.getAttribute("cartList") != null) {
                cartList = (List<Product>) session.getAttribute("cartList");
            }
            ProductService productService = new ProductService(em);
            Product product = productService.findProductByCode(productId);
            cartList.add(product);
            session.setAttribute("cartList", cartList);
            doGet(request, response);
        } else {
            response.sendRedirect("ViewCart");
        }
    }
}
