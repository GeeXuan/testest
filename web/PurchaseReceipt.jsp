<%-- 
    Document   : PurchaseReceipt
    Created on : Dec 10, 2018, 3:41:30 PM
    Author     : hong1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
<%@page import="java.util.ArrayList"%>
<%@page import="Entity.Product"%>
<%@page import="java.util.List"%>
<link href="hp.css" type="text/css" rel="stylesheet"/>

<html>
    <%
        String orderpickupdate = (String) session.getAttribute("orderpickupdate");
    
    %>
    <body>
        <div class="header">
            <center><h2>Payment Status</h2></center>
        </div>

        <ul>
            <li><a class="active" href="index.jsp">Home</a></li>
            <li class="dropdown">
                <a href="javascript:void(0)" class="dropbtn">Product</a>
                <div class="dropdown-content">
                    <a href="NewProduct">Add Product</a>
                    <a href="loadProductList.jsp">Modify Product</a>
                </div>
            </li>
            <li class="dropdown">
                <a href="javascript:void(0)" class="dropbtn">Stock</a>
                <div class="dropdown-content">
                    <a href="newStock">Add Stock</a>
                    <a href="checkStock.jsp">Check Stock</a>
                </div>
            </li>
            <li style="float:right"><a href="#">Login</a></li>
            <li style="float:right"><a href="#">Signup</a></li>
        </ul>

        <div style="padding: 30px"></div>

        <h3>Address method: ${ orders.orderpickupdate }</h3>
       

        <br>  <br>  <br>

    <center>
        <input type="submit" value="Confirm" style="background-color: white; color: black; border: 2px solid #555555; font-size: 20px;">

    </center>


    <br><br>
</body>

</html>
