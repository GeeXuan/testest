<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<link href="hp.css" type="text/css" rel="stylesheet"/>
<%
    String id = request.getParameter("id");
    String host = "jdbc:derby://localhost:1527/FioreFlowershop";
    String userid = "nbuser";
    String password = "12345";

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
%>
<%
    try {
        connection = DriverManager.getConnection(host, userid, password);
        statement = connection.createStatement();
        String sql = "select * from PRODUCT where productId=" + id;
        resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
%>
<!DOCTYPE html>
<html>
    <body>
        <div class="header">
            <center><h2>Update Product</h2></center>
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
        
        <form method="post" action="updateProductProcess.jsp">
            <div style="padding: 30px"></div>
            
            <fieldset>
                <legend><b>Product Information</b></legend>

                <center><table border="0" width="100%">
                    <tbody> 
                        <tr>
                            <td>Product ID : </td>
                            <td><input type="text" name="productId" size="55" value="<%=resultSet.getString("productId")%>" readonly></td>
                        </tr>

                        <tr>
                            <td>Product Name : </td>
                            <td><input type="text" name="productName" value="<%=resultSet.getString("productName")%>"></td>
                        </tr>
                        
                        <tr>
                            <td>Product Description : </td>
                            <td><input type="text" name="productDescription" value="<%=resultSet.getString("productDescription")%>"</td>
                        </tr>
                        
                        <tr>
                            <td>Product Price : </td>
                            <td><input type="text" name="productPrice" value="<%=resultSet.getString("productPrice")%>" readonly></td>
                        </tr>

                        <tr>
                            <td>Product Type : </td>
                            <td><input type="text" name="productType" value="<%=resultSet.getString("productType")%>" readonly></td>
                        </tr>
                    </tbody>
                </table></center>
            </fieldset>

            <br>
            
            <center>
                <input type="submit" value="Confirm" style="background-color: white; color: black; border: 2px solid #555555; font-size: 20px;">
                <input type="reset" value="Reset" style="background-color: white; color: black; border: 2px solid #555555; font-size: 20px;">
            </center>
        </form>
        <%
                }
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </body>
</html>
