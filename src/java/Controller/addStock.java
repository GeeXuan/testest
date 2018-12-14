/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Stock;
import Entity.StockService;
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
@WebServlet(name = "addStock", urlPatterns = {"/addStock"})
public class addStock extends HttpServlet {
    
    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            Stock stock = (Stock) session.getAttribute("stock");
            StockService stockService = new StockService(em);
            utx.begin();
            stockService.addStock(stock);
            utx.commit();
            
            RequestDispatcher rd = request.getRequestDispatcher("addStockConfirm.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("exception", ex);
            RequestDispatcher rd = request.getRequestDispatcher("ExceptionView.jsp");
            rd.forward(request, response);
        }
    }
}
