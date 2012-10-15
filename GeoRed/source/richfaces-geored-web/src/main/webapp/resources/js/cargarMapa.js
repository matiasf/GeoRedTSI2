 	var map;
    function initialize() {
      var myLatlng = new google.maps.LatLng(-34.91834202183006,-56.166390037536644);
      var mapOptions = {
        zoom: 17,
        center: myLatlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      }
      map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);      
      map.markers = new Array();
      google.maps.event.addListener(map, 'click', function(event) {    	
        placeMarker(event.latLng);
      });
    }

    function placeMarker(location) {      
      //alert(document.getElementById("form").getAttribute("coordX"));      
      document.getElementById("form:coordX").value = location.lat();
      document.getElementById("form:coordY").value = location.lng();            
      
      var marker = new google.maps.Marker({
          position: location,
          map: map
      });
      for(var i=0; i<map.markers.length; i++){
  		map.markers[i].setMap(null);
      }  	  
      map.markers = new Array();
      map.markers[0] = marker;
      map.setCenter(location);
    }
    