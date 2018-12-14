/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Product;
import Entity.ProductService;
import Entity.Promotion;
import Entity.PromotionService;
import java.io.IOException;
import java.util.LinkedList;
import java.util.LinkedList;
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
 * @author Michelle Ooi
 */
@WebServlet(name = "flowerPromo", urlPatterns = {"/flowerPromo"})
public class flowerPromo extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    RequestDispatcher rd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String promotype = request.getParameter("promotype");
        HttpSession session = request.getSession();
        PromotionService promotionService = new PromotionService(em);
        List<Promotion> promotionList = promotionService.findAll();
        ProductService productService = new ProductService(em);
        List<Product> products = productService.findAll();
        List<Product> productList = new LinkedList();
        for (Product product : products) {
            if (product.getProducttype().equals(promotype)) {
                productList.add(product);
            }
        }
        session.setAttribute("promoList", promotionList);
        session.setAttribute("productList", productList);
        session.setAttribute("promotype", promotype);

        rd = request.getRequestDispatcher("flowerPromo.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String promoName = request.getParameter("promoName");
        String promoType = request.getParameter("promoType");
        String productid[] = request.getParameterValues("promoProduct");
        List<Product> productList = new LinkedList();
        ProductService productService = new ProductService(em);
        for (String string : productid) {
            Product product = productService.findProductByCode(Integer.parseInt(string));
            productList.add(product);
        }
        Promotion promotion = new Promotion(promoName, promoType);

        HttpSession session = request.getSession();

        session.setAttribute("promotion", promotion);
        session.setAttribute("productList", productList);

        rd = request.getRequestDispatcher("promoDetails.jsp");
        rd.forward(request, response);

    }

}
