package com.geored.servicios.json;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ImagenJSON {
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
