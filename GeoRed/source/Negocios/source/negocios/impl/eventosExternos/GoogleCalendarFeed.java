package negocios.impl.eventosExternos;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import persistencia.Evento;

public class GoogleCalendarFeed {
	
	private final static String BASEURL = "http://www.google.com/calendar/feeds/%1/public/full?futureevents=true&orderby=starttime&sortorder=ascending&max-results=15"; 
	
	static final String ENTRY = "entry";
	static final String WHEN = "when";
	static final String TITLE = "title";
	static final String CONTENT = "content";
	static final String GD = "gd";
	
	static final int longFmtDateLgth = 29;
	static final int shortFmtDateLgth = 10;
	
	static final String ENDTIME = "endTime";
	static final String STARTTIME = "startTime";
	
	private String calendarId;
	
	public List<Evento> obtenerEventos() throws IOException, XMLStreamException, ParseException {
		String urlStr = BASEURL.replace("%1", calendarId);
		URL url = new URL(urlStr);
		
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		// Setup a new eventReader
		InputStream in = url.openStream();
		XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
		// Read the XML document
		
		List<Evento> ret = new ArrayList<Evento>();
		Evento evento = null;		
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			if (event.isStartElement()) {
				StartElement element = event.asStartElement();
				if (element.getName().getLocalPart() == ENTRY) {
					event = eventReader.nextEvent();
					evento = new Evento();
					continue;
					
				}
				if ((element.getName().getPrefix() == GD) && (element.getName().getLocalPart() == WHEN)) {
					Iterator<Attribute> atributos = element.getAttributes();
					while (atributos.hasNext()) {
						Attribute attr = atributos.next();
						if (attr.getName().toString() == STARTTIME) {
							Calendar cal = this.parseGoogleDate(attr.getValue());
							evento.setInicio(cal);
							continue;
						}
						if (attr.getName().toString() == ENDTIME) {
							Calendar cal = this.parseGoogleDate(attr.getValue());
							evento.setFin(cal);
							continue;
						}
					}
				}
				if ((element.getName().getPrefix() == GD) && (element.getName().getLocalPart() == TITLE)) {
					event = eventReader.nextEvent();
					evento.setNombre(event.asCharacters().getData());
					continue;
				}
				if ((element.getName().getPrefix() == GD) && (element.getName().getLocalPart() == CONTENT)) {
					event = eventReader.nextEvent();
					evento.setDescripcion(event.asCharacters().getData());
					continue;
				}
			}
			
			if (event.isEndElement()) { 
				if ((event.asEndElement().getName().getLocalPart() == ENTRY) && (evento != null)){
					ret.add(evento);
					evento = null;
					continue;
				}
			}
			
		}
		return ret;
	}
	
	private Calendar parseGoogleDate(String strDate) throws ParseException { 
		Calendar cal = Calendar.getInstance();
		if (strDate.length() == longFmtDateLgth) {
			strDate = strDate.replace("T", "");
			strDate = strDate.replaceFirst("(\\d\\d):(\\d\\d)$", "$1$2");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss.SSSZ");
			cal.setTime(sdf.parse(strDate));
		}
		else if (strDate.length() == shortFmtDateLgth) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			cal.setTime(sdf.parse(strDate));
		}
		return cal;
	}

	public String getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}
	
	
	

}
