<%@page import="java.util.LinkedList"%>
<%@page import="Entity.Product"%>
<%@page import="java.util.List"%>
<link href="hp.css" type="text/css" rel="stylesheet"/>
<%
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
        <%if (cartList.size() == 0 || cartList == null) {%>
        <h3>Cart is empty!</h3>
        <%  } else {
        %>
        <form action="ViewCart" method="post">
            <center><table border="1" id="cartTable">
                    <tr>
                        <th></th>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Product Description</th>
                        <th>Product Price</th>
                        <th>Product Type</th>
                        <th>Stock Quantity</th>
                        <th>Quantity</th>
                    </tr>
                    <%int i = 0;
                        for (Product product : cartList) {%>

                    <tr>
                        <td><button type="submit" style="border:0;background-color: transparent" name="cancel<%=i%>" value="cancel"><img src="img/Delete.png" height="25" width="25" alt="cancel"></button></td>
                        <td><%=product.getProductid()%></td>
                        <td><%=product.getProductname()%></td>
                        <td><%=product.getProductdescription()%></td>
                        <td><input type="text" id="price<%=i%>" value="<%=product.getProductprice()%>" style="border:none" readonly></td>
                        <td><%=product.getProducttype()%></td>
                        <td><%if (product.getStockid() == null) {%>
                            0
                            <%} else {%>
                            <%=product.getStockid().getStockquantity()%>
                            <%}%></td>
                        <td><input type="number" onkeyup="calculate()" name="quantity<%=i%>" id="quantity<%=i%>" min="1" max="<%=product.getStockid().getStockquantity()%>"></td>
                        <td><input type="text" id="totalPrice<%=i%>" style="border:none" readonly></td>
                    </tr>
                    <% i++;
                        }
                    %>
                </table></center>
            <br><br>
            Total Price = <input type="text" name="totalPrice" id="sum" style="border:none" readonly>
            <br>
            <input type="submit" value="Proceed"/>
        </form>
        <%}%>
    </body>
    <script>
        function calculate() {
            var sum = 0;
            for (var i = 0; i < document.getElementById("cartTable").rows.length-1; i++) {
                var price = document.getElementById('price' + i).value;
                var quantity = document.getElementById('quantity' + i).value;
                if (price !== null && quantity !== null) {
                    var totalPrice = price * quantity;
                    sum = sum + totalPrice;
                    document.getElementById('totalPrice' + i).value = totalPrice.toFixed(2);
                }
            debugger;
            }
            document.getElementById('sum').value = sum.toFixed(2);
        }
    </script>
</html>
