/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Product;
import Entity.ProductService;
import java.io.IOException;
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

/**
 *
 * @author Michelle Ooi
 */
@WebServlet(name = "AddProduct", urlPatterns = {"/AddProduct"})
public class AddProduct extends HttpServlet {
    
    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
     RequestDispatcher rd;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            Product product = (Product) session.getAttribute("product");
            ProductService productService = new ProductService(em);
            utx.begin();
            productService.addProduct(product);
            utx.commit();
            
            RequestDispatcher rd = request.getRequestDispatcher("AddProductConfirm.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("exception", ex);
            RequestDispatcher rd = request.getRequestDispatcher("ExceptionView.jsp");
            rd.forward(request, response);
        }
    }
}