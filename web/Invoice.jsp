<%@page import="Entity.Payment"%>
<%@page import="Entity.Orderlist"%>
<%@page import="java.util.List"%>
<%@page import="Entity.Orders"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            List<Payment> paymentList = (List<Payment>) session.getAttribute("paymentList");
            Date date = new Date();
        %>
        <h1>Invoice</h1>
        <span>Generated on: <%=date%></span>
        <table>
            <tr>
                <th>No</th>
                <th>Order</th>
                <th>Total</th>
            </tr>
            <%int i = 1;
                double sum = 0;
                for (Payment payment : paymentList) {%>
            <tr>
                <td><%=i%></td>
                <td><%=payment.getOrderid().getOrderid()%> - <%=payment.getOrderid().getOrderdate()%><br>
                    <%for (Orderlist orderlist : payment.getOrderid().getOrderlistList()) {%>
                    - <%=orderlist.getProductid().getProductname()%> x <%=orderlist.getProductquantity()%><br>
                    <%}
                    %>
                </td>
                <td><%=payment.getOrderid().getOrderprice()%></td>
            </tr>
            <%i++;
                    sum += payment.getOrderid().getOrderprice();
                }
            %>
            <tr>
                <td colspan="2">Sum:</td>
                <td><%=sum%></td>
            </tr>
        </table>
    </body>
</html>
