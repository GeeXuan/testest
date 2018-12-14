/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Product;
import Entity.Stock;
import Entity.ProductService;
import Entity.StockService;
import java.io.IOException;
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
@WebServlet(name = "newStock", urlPatterns = {"/newStock"})
public class newStock extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    RequestDispatcher rd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StockService stockService = new StockService(em);
        List<Stock> stockList = stockService.findAll();
        HttpSession session = request.getSession();
        session.setAttribute("stockList", stockList);
        rd = request.getRequestDispatcher("addStock.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int stockid = Integer.parseInt(request.getParameter("stockid"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        
        ProductService productService = new ProductService(em);
        Product product = productService.findProductByCode(productId);
        
        int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));

        Stock stock = new Stock(stockid, stockQuantity, product); 

        HttpSession session = request.getSession();

        session.setAttribute("stock", stock);
        rd = request.getRequestDispatcher("addStockConfirmation.jsp");
        rd.forward(request, response);
    }
}
