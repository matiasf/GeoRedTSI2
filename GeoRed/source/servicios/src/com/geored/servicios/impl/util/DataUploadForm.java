package com.geored.servicios.impl.util;

import java.io.InputStream;
import java.io.Serializable;

import javax.ws.rs.FormParam;

public class DataUploadForm implements Serializable {

	static final long serialVersionUID = 1L;

	@FormParam("header")
	private String head;

	@FormParam("payload")
	private InputStream payload;

	public DataUploadForm() {
		super();
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public InputStream getPayload() {
		return payload;
	}

	public void setPayload(InputStream payload) {
		this.payload = payload;
	}
	
}
