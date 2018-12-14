/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Product;
import Entity.ProductService;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author Michelle Ooi
 */
@WebServlet(name = "NewProduct", urlPatterns = {"/NewProduct"})
public class NewProduct extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    RequestDispatcher rd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductService productService = new ProductService(em);
        List<Product> productList = productService.findAll();
        HttpSession session = request.getSession();
        session.setAttribute("productList", productList);
        rd = request.getRequestDispatcher("AddNewProd.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        String productName = request.getParameter("productName");
        String productDescription = request.getParameter("productDescription");
        double productPrice = Double.parseDouble(request.getParameter("productPrice"));
        String productType = request.getParameter("productType");

        Product product = new Product(productId, productName, productDescription, productPrice, productType);

        HttpSession session = request.getSession();

        session.setAttribute("product", product);

        rd = request.getRequestDispatcher("AddProductConfirmation.jsp");
        rd.forward(request, response);
    }

}
