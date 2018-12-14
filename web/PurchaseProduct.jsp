<%@page import="java.util.LinkedList"%>
<%@page import="Entity.Product"%>
<%@page import="java.util.List"%>
<link href="hp.css" type="text/css" rel="stylesheet"/>
<%
    List<Product> productList = new LinkedList();
    if (session.getAttribute("productList") != null) {
        productList = (List<Product>) session.getAttribute("productList");
    }
    List<Product> cartList = new LinkedList();
    if (session.getAttribute("cartList") != null) {
        cartList = (List<Product>) session.getAttribute("cartList");
    }
%>
<html>
    <body>
        <div class="header">
            <center><h2>Product List</h2></center>
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
        <%if (productList.size() == 0 || productList == null) {%>
        <h3>There is no product!</h3>
        <%  } else {
        %>
        <form action="PurchaseProduct" method="post">
            <center><table border="1">
                    <tr>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Product Description</th>
                        <th>Product Price</th>
                        <th>Product Type</th>
                        <th>Add to cart</th>
                    </tr>
                    <%for (Product product : productList) {%>

                    <tr>
                        <td><%=product.getProductid()%></td>
                        <td><%=product.getProductname()%></td>
                        <td><%=product.getProductdescription()%></td>
                        <td><%=product.getProductprice()%></td>
                        <td><%=product.getProducttype()%></td>

                        <td><%if (product.getStockid() == null || product.getStockid().getStockquantity() == 0) {%>
                            Out of stock!!!
                            <%} else {%>
                            <button type="submit" name="addcart" value="<%=product.getProductid()%>"
                                    <%for (Product cartProd : cartList) {

                                            if (product.equals(cartProd)) {%>
                                    disabled
                                    <%
                                            }
                                        }
                                    %>>Add to cart</button>
                            <%}%></td>
                    </tr>
                    <%
                        }
                    %>
                </table></center><br/>
            <input type="submit" value="Proceed" name="addcart">
        </form>
        <%}%>
    </body>
</html>
