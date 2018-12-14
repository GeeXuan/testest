<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<link href="hp.css" type="text/css" rel="stylesheet"/>
<%
    String host = "jdbc:derby://localhost:1527/FioreFlowershop";
    String userid = "nbuser";
    String password = "12345";

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product out of stock list</title>
        <link href="hp.css" type="text/css" rel="stylesheet"/>
    </head>

    <body>
        
        <div class="header">
            <center><h2>Product out of stock list</h2></center>
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
            <li class="dropdown">
                <a href="javascript:void(0)" class="dropbtn">Promotion</a>
                <div class="dropdown-content">
                    <a href="flowerPromo?promotype=Flower">Add Flower Promotion</a>
                    <a href="flowerPromo?promotype=Bouquets">Add Bouquet Promotion</a>
                </div>
            </li>
            <%
                String LoggedInName = null;
                String userType = null;
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("LoggedInName")) {
                            LoggedInName = cookie.getValue();
                        }
                        if (cookie.getName().equals("UserType")) {
                            userType = cookie.getValue();
                        }
                    }
                }
                if (LoggedInName != null) {%>
            <li class="dropdown" style="float:right">
                <a href="javascript:void(0)" class="dropbtn"><%=LoggedInName%><%if (userType.equals("Staff")) {%>(Staff)<%}%>&#x25BC;</a>
                <div class="dropdown-content">
                    <a href="Profile">View Profile</a>
                    <a href="SignOut">Sign Out</a>
                    <%if (userType.equals("Staff") || userType.equals("admin")) {%><a href="AddCorporate">Add Corporate Customer</a><%}%>
                    <%if (userType.equals("admin")) {%><a href="AddStaff">Add Staff</a><%}%>
                </div></li>
                <%} else {%>
            <li style="float:right"><a href="Login">Login</a></li>
            <li style="float:right"><a href="Register">Register</a></li>
                <%}%>
        </ul>
        
        <div style="padding: 30px"></div>

        <center><table border="1">
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Product Description</th>
                    <th>Product Price</th>
                    <th>Product Type</th>
                    <th>Stock Quantity</th>
                </tr>
                <%
                    try {
                        connection = DriverManager.getConnection(host, userid, password);
                        statement = connection.createStatement();
                        String sql = "select product.productid, product.productname, product.productdescription, product.productprice, product.producttype, stock.stockquantity from STOCK inner join PRODUCT on stock.productid = product.productid where stockquantity = 0";
                        resultSet = statement.executeQuery(sql);
                        while (resultSet.next()) {
                %>
                <tr>
                    <td><%=resultSet.getString("productId")%></td>
                    <td><%=resultSet.getString("productName")%></td>
                    <td><%=resultSet.getString("productDescription")%></td>
                    <td><%=resultSet.getString("productPrice")%></td>
                    <td><%=resultSet.getString("productType")%></td>
                    <td><%=resultSet.getString("stockQuantity")%></td>
                </tr>
                <%
                        }
                        connection.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                %>
            </table></center>
    </body>
</html>