<%@page import="Entity.Address"%>
<%@page import="Entity.Orders"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Address> addressList = (List<Address>) session.getAttribute("addressList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check Delivery</title>
        <link href="hp.css" type="text/css" rel="stylesheet"/>
    </head>

    <body>
        <div class="header">
            <center><h2>Check Delivery</h2></center>
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

            <fieldset>
                <legend><b>Choose Address</b></legend>
                <form action="ChooseAddress" method="post">
                    <table>
                        <tr>
                            <th></th>
                            <th>Lot No.</th>
                            <th>Address</th>
                        </tr>
                        <%for (Address address : addressList) {%>
                        <tr>
                            <td><input type="radio" name="address" value="<%=address.getAddressid()%>"></td>
                            <td><%=address.getLotno()%></td>
                            <td><%=address.getAddressdetail()%></td>
                        </tr>
                        <%   }%>
                    </table><br>
                    <a href="AddAddress.jsp?purchase=true">Add new address</a><br><br>
                    <input type="submit" value="Proceed">
                </form>
            </fieldset>    
        </div>
    </body>
</html>
