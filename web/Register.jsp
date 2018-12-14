<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <link href="hp.css" type="text/css" rel="stylesheet"/>
    </head>
    <body>
        <div class="header">
            <center><h2>Register</h2></center>
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
        <fieldset>
            <legend><b>Register</b></legend>
            <p><% if (request.getAttribute("reregister") == "exist") {
                    out.print("<h4 style=\"color: red\">Username already exist!</h4 >");
                }
                if (request.getAttribute("reregister") == "wrong") {
                    out.print("<h4 style=\"color: red\">Password does not meet the minimum requirement!</h4 >");
                }
                %>
            </p>
            <form action="Register" method="post">
                <label>Username</label><br>
                <input type="text" name="username" maxlength="10" size="20"
                       <%if (request.getAttribute("reregister") != null) {
                               String username = (String) request.getAttribute("username");
                       %>
                       value="<%=username%>"<%}%> 
                       required>

                <br><br>

                <label>Password</label><br>
                <input type="password" name="password" maxlength="16"  size="20" required>
                <font size="-5" style="color: grey"> (Minimum 6 characters.)</font>

                <br><br>

                <label>Name</label><br>
                <input type="text" name="name" maxlength="50"  size="55" 
                       <%if (request.getAttribute("reregister") != null) {
                               String name = (String) request.getAttribute("name");
                       %>
                       value="<%=name%>"<%}%> 
                       required>

                <br><br>

                <label>IC</label><br>
                <input type="text" name="ic" maxlength="12"  size="14" 
                       <%if (request.getAttribute("reregister") != null) {
                               String ic = (String) request.getAttribute("ic");
                       %>
                       value="<%=ic%>"<%}%> 
                       required>

                <br><br>

                <label>Phone Number</label><br>
                <input type="text" name="phone" maxlength="11"  size="13" 
                       <%if (request.getAttribute("reregister") != null) {
                               String phone = (String) request.getAttribute("phone");
                       %>
                       value="<%=phone%>"<%}%> 
                       required>

                <br><br>

                <label>Email</label><br>
                <input type="email" name="email" maxlength="30" size="32" 
                       <%if (request.getAttribute("reregister") != null) {
                               String email = (String) request.getAttribute("email");
                       %>
                       value="<%=email%>"<%}%> 
                       required>

                <br><br>

                <input type="submit" value="Submit">
                <input type="reset" value="Reset">
            </form> 
        </fieldset>
    </body>
</html>
