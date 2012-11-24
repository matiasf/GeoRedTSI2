 	var map;
    function initialize() {
      var myLatlng = new google.maps.LatLng(-34.91834202183006,-56.166390037536644);
      var mapOptions = {
        zoom: 15,
        center: myLatlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      }
      map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);      
      map.markers = new Array();
      google.maps.event.addListener(map, 'click', function(event) {    	
        placeMarker(event.latLng);
      });
    }
    
    
    function initializeConPunto() {
    	var lat = document.getElementById("form:coordX").value;
    	var len = document.getElementById("form:coordY").value;
        var myLatlng = new google.maps.LatLng(lat,len);
        var mapOptions = {
          zoom: 15,
          center: myLatlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
        
        var markerIni = new google.maps.Marker({
            position: myLatlng,
            map: map
        });
        map.markers = new Array();
        map.markers[map.markers.length] = markerIni;
        google.maps.event.addListener(map, 'click', function(event) {    	
          placeMarker(event.latLng);
        });
      }
    
     function initializeConMuchosPuntos() {
    	 var myCenter = new google.maps.LatLng(-34.91834202183006,-56.166390037536644);
         var mapOptions = {
           zoom: 15,
           center: myCenter,
           mapTypeId: google.maps.MapTypeId.ROADMAP
         }
         map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
        
        var cant =  document.getElementById("formReporte:cantDatos").value;
   	 
        var image = 'flag_green.png';
        map.markers = new Array();
    	for(var i=0; i<cant; i++){      			
      			var lat = document.getElementById("formReporte:tablaReporte:"+i+":latitud").value;
      	    	var len = document.getElementById("formReporte:tablaReporte:"+i+":longitud").value;
      	    	var name = document.getElementById("formReporte:tablaReporte:"+i+":nombre").value;
      	    	var number = document.getElementById("formReporte:tablaReporte:"+i+":cantidad").value;
      	    	if (number > 5){
      	    		image = 'flag_yellow.png';
      	    	}  else {
      	    		image = 'flag_green.png';
      	    	}
      	    	if (number > 10){
      	    		image = 'flag_red.png';
      	    	}
      	        var myLatlng = new google.maps.LatLng(lat,len);
      			var markerPaPoner = new google.maps.Marker({
      				position: myLatlng,
      				title:name,     
      				icon:image,
      				map: map
      			});
      			map.markers[map.markers.length] = markerPaPoner;      		
          }
        
        
        
        
        /*google.maps.event.addListener(map, 'click', function(event) {    	
          placeMarker(event.latLng);
        });*/
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
    
    function cargarPuntoUnico() {    	  
    	  var lat = document.getElementById("form:coordX").value;
    	  var len = document.getElementById("form:coordY").value;
    	  var centro = new google.maps.LatLng(lat,len);
    	  placeMarker(centro);
    }
    
    function placeMarkers(location) {     
        alert("llame a place Markers con " + location);
        var marker = new google.maps.Marker({
            position: location,
            map: map
        });
        //for(var i=0; i<map.markers.length; i++){
    	//	map.markers[i].setMap(null);
        //}
        if (map.markers==null){
        	map.markers = new Array();
        }        
        map.markers[map.markers.length] = marker;
        map.setCenter(location);
      }
    
    function cargarPuntoIterativo(lat, len) {    	  
  	  var centro = new google.maps.LatLng(lat,len);
  	  placeMarkers(centro);
  }
    
    var estado = 1;
    function mostrarOcultar() {   
    	if (estado == 1){
    		document.getElementById("map_canvas").style.display = "none";
    		document.getElementById("mostrador").innerHTML = 'Mostrar mapa';
    		estado = 0;
    	} else {
    		document.getElementById("map_canvas").style.display = "block";
    		document.getElementById("mostrador").innerHTML = 'Ocultar mapa';
    		estado = 1;
    	}
    }
    