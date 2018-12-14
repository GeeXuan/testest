<%@page import="Entity.Stock"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Stock> stockList = (List<Stock>) session.getAttribute("stockList");
    int stockid = 1;
    if (stockList.size() != 0) {
        stockid = stockList.get(stockList.size() - 1).getStockid() + 1;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Stock</title>
        <link href="hp.css" type="text/css" rel="stylesheet"/>
    </head>
    <body>
        
        <div class="header">
            <center><h2>Add Stock</h2></center>
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

        <div style="padding:20px;margin-top:30px;height:1500px;">
            <form action="newStock" method="post">

                <div style="padding: 20px"></div>
                <fieldset>
                    <legend><b>Add Stock</b></legend>

                    <aside>
                        <center><table border="0" width="70%">
                                <tbody>
                                    <tr>
                                        <td>Stock ID : </td>
                                        <td><input type="text" name="stockid" size="55" required value="<%=stockid%>" readonly="readonly"></td>
                                    </tr>

                                    <tr>
                                        <td>Product ID : </td>
                                        <td><input type="text" name="productId" size="55" required></td>
                                    </tr>

                                    <tr>
                                        <td>Stock Quantity : </td>
                                        <td><input type="text" name="stockQuantity" size="55" required ></td>
                                    </tr>                 
                                </tbody>
                            </table></center>
                    </aside>
                </fieldset>    

                <br>

                <center>
                    <input type="submit" value="Confirm" style="background-color: white; color: black; border: 2px solid #555555; font-size: 20px;">
                    <input type="reset" value="Reset" style="background-color: white; color: black; border: 2px solid #555555; font-size: 20px;">
                </center>
            </form>
        </div>
    </body>
</html>