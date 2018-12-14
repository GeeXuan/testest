<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<link href="hp.css" type="text/css" rel="stylesheet"/>

<meta http-equiv="refresh" content="3; url=index.jsp">

<%!String host = "jdbc:derby://localhost:1527/FioreFlowershop";%>
<%!String user = "nbuser";%>
<%!String psw = "12345";%>
<%
    int productId = Integer.parseInt(request.getParameter("productId"));
    String productName = request.getParameter("productName");
    String productDescription = request.getParameter("productDescription");
    double productPrice = Double.parseDouble(request.getParameter("productPrice"));
    String productType = request.getParameter("productType");

    Connection con = null;
    PreparedStatement ps = null;
    int personID = productId;

    try {
            con = DriverManager.getConnection(host, user, psw);
            String sql = "Update PRODUCT set productName=?, productDescription=? where productId=" + productId;
            ps = con.prepareStatement(sql);
            ps.setString(1, productName);
            ps.setString(2, productDescription);
            
            int i = ps.executeUpdate();
            if (i > 0) {
                out.print("Product updated successfully. Redirecting in 3 seconds...");
            } else {
                out.print("Error: Unable to update Product.");
            }
        } catch (SQLException sql) {
            request.setAttribute("error", sql);
            out.println(sql);
        }
%>