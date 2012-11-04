 

function armarLaTorta() {
	//var myData = new Array(['Sector 1', 2], ['Sector 2', 1], ['Sector 3', 3], ['Sector 4', 6], ['Sector 5', 8.5], ['Sector 6', 10]);
	//var colors = ['#FACC00', '#FB9900', '#FB6600', '#FB4800', '#CB0A0A', '#F8F933'];
	var colorsPre = ['#FFCC00', '#FFFF00', '#CCFF00', 
	                 '#99FF00', '#33FF00', '#00FF66', 
	                 '#00FF99', '#00FFCC', '#FF0000', '#FF3300'];
	
	var cant =  document.getElementById("formReporte:cantDatos").value;
	//alert("cant vale " + cant);
	var myData = new Array();
	var colors = new Array();
	for(var i=0; i<cant; i++){
		var name = document.getElementById("formReporte:tablaReporte:"+i+":nombre").value;
		//alert("name vale " + name);
	    var cantidad = document.getElementById("formReporte:tablaReporte:"+i+":cantidadVentas").value;
	    //alert("name vale " + cantidad);
	    
	    //var pto = new Array(name, cantidad);
	    //var pto = [name, cantidad];
	    myData[i] = [name, parseInt(cantidad)];
	    colors[i] = colorsPre[i%10];
	    //alert("pto vale " + pto);
	    
	    //var myData[i][0] = name;
	    //var myData[i][1] = cantidad;
	    //myData.push(pto);
	}
	
	//alert("myData vale :  " + myData);
	
	
	
	var myChart = new JSChart('chartcontainer', 'pie');
	
	myChart.setDataArray(myData);
	myChart.colorizePie(colors);
	myChart.setTitleColor('#857D7D');
	myChart.setPieUnitsColor('#9B9B9B');
	myChart.setPieValuesColor('#6A0000');
	myChart.draw();
}
