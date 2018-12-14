/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Product;
import Entity.Promotion;
import Entity.Promotiondetails;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Michelle Ooi
 */
@WebServlet(name = "newPromo", urlPatterns = {"/newPromo"})
public class newPromo extends HttpServlet {

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
            List<Product> productList = (List<Product>) session.getAttribute("productList");
            Promotion promotion = (Promotion) session.getAttribute("promotion");
            List<Promotiondetails> promotiondetailsList = new LinkedList();
            for (Product product : productList) {
                Promotiondetails promotiondetails = new Promotiondetails();
                promotiondetails.setPromotionid(promotion);
                promotiondetails.setProductid(product);
                promotiondetails.setPromoprice(Double.parseDouble(request.getParameter("price" + product.getProductid())));
                promotiondetailsList.add(promotiondetails);
            }
            promotion.setPromotiondetailsList(promotiondetailsList);
            utx.begin();
            em.persist(promotion);
            utx.commit();

            rd = request.getRequestDispatcher("promoConfirm.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
        }

    }

}
