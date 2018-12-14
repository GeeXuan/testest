<%@page import="Entity.Address"%>
<%@page import="Entity.Orders"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check Delivery</title>
        <link href="hp.css" type="text/css" rel="stylesheet"/>
        <style>
            #map{
                height:500px;
                width:650px;
            }
            #infowindow-content .title {
                font-weight: bold;
            }

            #infowindow-content {
                display: none;
            }

            #map #infowindow-content {
                display: inline;
            }
        </style>
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
                <legend><b>Add Address</b></legend>
                <form action="AddAddress" method="post">
                    <%if (request.getParameter("purchase") != null && request.getParameter("purchase").equals("true")) {%>
                    <input type="hidden" name="purchase" value="true">
                    <%}%>
                    Lot No<br>
                    <input type="text" name="lotno" required><br>

                    Address<br>
                    <input type="text" id="mapinput" name="address" size="60" required><br><br>
                    <div id="map"></div>
                    <div id="infowindow-content">
                        <img src="" width="16" height="16" id="place-icon">
                        <span id="place-name"  class="title"></span><br>
                        <span id="place-address"></span>
                    </div>
                    <input type="submit" id="btnsubmit">
                </form>
            </fieldset>    
        </div>
        <script>
            var IsplaceChange = false;
            function initMap() {
                var map = new google.maps.Map(document.getElementById('map'), {
                    center: {lat: 3.2162302, lng: 101.7267724},
                    zoom: 13
                });
                var input = document.getElementById('mapinput');
                var autocomplete = new google.maps.places.Autocomplete(input);
                autocomplete.bindTo('bounds', map);
                autocomplete.setComponentRestrictions({'country': 'MY'});
                autocomplete.setFields(
                        ['address_components', 'geometry', 'icon', 'name']);
                var infowindow = new google.maps.InfoWindow();
                var infowindowContent = document.getElementById('infowindow-content');
                infowindow.setContent(infowindowContent);
                var marker = new google.maps.Marker({
                    map: map,
                    anchorPoint: new google.maps.Point(0, -29)
                });
                autocomplete.addListener('place_changed', function () {
                    infowindow.close();
                    marker.setVisible(false);
                    var place = autocomplete.getPlace();
                    IsplaceChange = true;
                    if (!place.geometry) {
                        // User entered the name of a Place that was not suggested and
                        // pressed the Enter key, or the Place Details request failed.
                        window.alert("No details available for input: '" + place.name + "'");
                        return;
                    }

                    // If the place has a geometry, then present it on a map.
                    if (place.geometry.viewport) {
                        map.fitBounds(place.geometry.viewport);
                    } else {
                        map.setCenter(place.geometry.location);
                        map.setZoom(17); // Why 17? Because it looks good.
                    }
                    marker.setPosition(place.geometry.location);
                    marker.setVisible(true);
                    var address = '';
                    if (place.address_components) {
                        address = [
                            (place.address_components[0] && place.address_components[0].short_name || ''),
                            (place.address_components[1] && place.address_components[1].short_name || ''),
                            (place.address_components[2] && place.address_components[2].short_name || '')
                        ].join(' ');
                    }

                    infowindowContent.children['place-icon'].src = place.icon;
                    infowindowContent.children['place-name'].textContent = place.name;
                    infowindowContent.children['place-address'].textContent = address;
                    infowindow.open(map, marker);
                });
                $("#txtlocation").keydown(function () {
                    IsplaceChange = false;
                });

                $("#btnsubmit").click(function () {

                    if (IsplaceChange == false) {
                        $("#txtlocation").val('');
                        alert("please Enter valid location");
                    } else {
                        alert($("#txtlocation").val());
                    }

                });
            }
        </script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBNdq7ySYnUUV4OFZerEdpfvFIwnzHa8bw&libraries=places&callback=initMap"
        async defer></script>
    </body>
</html>
