package javaBB;
import javax.faces.bean.SessionScoped;

import com.googlecode.gmaps4jsf.component.map.Map;  
import com.googlecode.gmaps4jsf.component.marker.Marker;  
import com.googlecode.gmaps4jsf.component.eventlistener.EventListener;  

@SessionScoped  
public class GoogleBean {  
    private Map map;  
  
    public void setMap(Map map) {  
        this.map = map;  
        map.setLatitude("52.05");  
        map.setLongitude("5.11");  
  
         
        Marker mark = new Marker();  
        mark.setLatitude("52.05");  
        mark.setLongitude("5.11");  
        mark.setJsVariable("office1");  
        mark.setId("mark1");  
        EventListener event = new EventListener();  
        event.setEventName("click");  
        event.setJsFunction("marker1ClickHandler");  
        mark.getChildren().add(event);  
        map.getChildren().add(mark);  
  
        Marker mark2 = new Marker();  
        mark2.setLatitude("53.19");  
        mark2.setLongitude("6.53");  
        mark2.setJsVariable("office2");  
        mark.setId("mark2");  
        EventListener event2 = new EventListener();  
        event2.setEventName("click");  
        event2.setJsFunction("marker2ClickHandler");  
        mark2.getChildren().add(event2);  
        map.getChildren().add(mark2);  
  
    }  
  
    public Map getMap() {  
        return map;  
    }  
  
}