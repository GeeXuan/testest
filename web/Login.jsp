<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="hp.css" type="text/css" rel="stylesheet"/>
    </head>

    <body>

        <div class="header">
            <center><h2>Login</h2></center>
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
        <div style="padding:20px;margin-top:30px;height:1500px;margin-left: 40%;margin-right: 40%">

            <fieldset>
                <legend><b>Login</b></legend>
                <form action="Login" method="post">
                    <%if (request.getAttribute("relogin") != null) {
                            String errormsg = (String) request.getAttribute("relogin");%>
                    <span style="color: red"><%=errormsg%></span>
                    <%}
                    %>
                    <label>Username:</label><br>
                    <input type="text" placeholder="Enter username" name="username" 
                           <%if (request.getAttribute("relogin") != null) {
                                   String username = (String) request.getAttribute("username");%>
                           value="<%=username%>"
                           <%}
                           %> 
                           size="35"><br><br>
                    <label>Password:</label><br>
                    <input type="password" placeholder="Enter password" name="password" size="35"><br><br>
                    <button type="submit">Submit</button>
                </form>
            </fieldset>    

        </div>
    </body>
</html>
