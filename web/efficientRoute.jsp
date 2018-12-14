<%@page import="Entity.Orders"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HomePage</title>
        <link href="hp.css" type="text/css" rel="stylesheet"/>
        <style>
            /* Always set the map height explicitly to define the size of the div
             * element that contains the map. */
            #map {
                height: 500px;
                width: 600px;
            }
        </style>
    </head>

    <body>
        <%
            List<Orders> orderToday = (List<Orders>) session.getAttribute("orderToday");
        %>
        <div style="padding:20px;margin-top:30px;height:1500px;">
            <div class="header">
                <center><h2>Homepage</h2></center>
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
            <%
                for (Orders order : orderToday) {%>
            <input type="hidden" name="addresses" value="<%=order.getAddressid().getAddressdetail()%>">
            <%                }%>
            <div id="map"></div>    <br>
            <div id="directions-panel"></div>

        </div>
        <script>
            // This example requires the Places library. Include the libraries=places
            // parameter when you first load the API. For example:
            // <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=places">

            function initMap() {
                var map = new google.maps.Map(document.getElementById('map'), {
                    center: {lat: 3.2162302, lng: 101.7267724},
                    zoom: 17
                });
                var directionsService = new google.maps.DirectionsService();

                var directionDisplay = new google.maps.DirectionsRenderer();
                directionDisplay.setMap(map);

//build the waypoints
//free api allows a max of 9 total stops including the start and end address
//premier allows a total of 25 stops. 
                var items = document.getElementsByName("addresses");
                var waypoints = [];
                for (var i = 0; i < items.length; i++) {
                    var address = items[i].value;
                    if (address !== "") {
                        waypoints.push({
                            location: address,
                            stopover: true
                        });
                    }
                }

//set the starting address and destination address
                var originAddress = "Tunku Abdul Rahman University College, Jalan Genting Kelang, Kuala Lumpur, Federal Territory of Kuala Lumpur";
                var destinationAddress = "Tunku Abdul Rahman University College, Jalan Genting Kelang, Kuala Lumpur, Federal Territory of Kuala Lumpur";

//build directions request
                var request = {
                    origin: originAddress,
                    destination: destinationAddress,
                    waypoints: waypoints, //an array of waypoints
                    optimizeWaypoints: true, //set to true if you want google to determine the shortest route or false to use the order specified.
                    travelMode: 'DRIVING'
                };

//get the route from the directions service
                directionsService.route(request, function (response, status) {
                    if (status == 'OK') {
                        directionDisplay.setDirections(response);
                        var route = response.routes[0];
                        var totalDistance = 0;
                        var summaryPanel = document.getElementById('directions-panel');
                        summaryPanel.innerHTML = '';
                        // For each route, display summary information.
                        for (var i = 0; i < route.legs.length; i++) {
                            var routeSegment = i + 1;
                            summaryPanel.innerHTML += '<b>Route Segment: ' + routeSegment +
                                    '</b><br>';
                            summaryPanel.innerHTML += route.legs[i].start_address + '<br>to<br>';
                            summaryPanel.innerHTML += route.legs[i].end_address + '<br>';
                            summaryPanel.innerHTML += route.legs[i].distance.text + '<br><br>';
                            totalDistance += route.legs[i].distance.value;
                        }
                        summaryPanel.innerHTML += 'Total Distance: ' + totalDistance / 1000 + 'km';
                    } else {
                        window.alert('Directions request failed due to ' + status);
                    }
                });
            }
        </script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBNdq7ySYnUUV4OFZerEdpfvFIwnzHa8bw&libraries=places&callback=initMap"
        async defer></script>
    </body>
</html>
