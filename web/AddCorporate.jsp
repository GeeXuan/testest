<%@page import="java.util.LinkedList"%>
<%@page import="Entity.Customer"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link href="hp.css" type="text/css" rel="stylesheet"/>
<html>
    <body>
        <%List<Customer> customerList = new LinkedList();
            if (session.getAttribute("customerList") != null) {
                customerList = (List< Customer>) session.getAttribute("customerList");
            }%>
        <div class="header">
            <center><h2>Add Corporate</h2></center>
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

        <form action="AddCorporate" method="post">
            Debt Limit: RM<input type="number" name="limit" min="0.01" step="0.01"><br><br>
            <table border="1">
                <tr>
                    <th>Customer ID</th>
                    <th>Customer Name</th>
                    <th>Customer IC</th>
                    <th>Customer Phone Number</th>
                    <th>Customer Email</th>
                    <th>Set as Corporate</th>
                </tr>
                <%for (Customer customer : customerList) {%>
                <tr>
                    <td><%=customer.getCustomerid()%></td>
                    <td><%=customer.getCustomername()%></td>
                    <td><%=customer.getCustomeric()%></td>
                    <td><%=customer.getCustomerphonenum()%></td>
                    <td><%=customer.getCustomeremail()%></td>
                    <td><button type="submit" name="setCorporate" value="<%=customer.getCustomerid()%>">Set as Corporate</button></td>
                </tr>
                <%}
                %>
            </table>
        </form>
    </body>
</html>
