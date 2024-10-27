package com.shop.admin.export;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.http.HttpServletResponse;


public abstract class AbstractExporter {
	
	public abstract void exportPdf();
	
	public void setResponseHeader(HttpServletResponse response, String contentType, String extention, String entityName) throws IOException {
		DateFormat dateFormater = new SimpleDateFormat("yyyy_MM-dd_HH-mm-ss");
		String timeStamp = dateFormater.format(new Date()),
		fileName = entityName  + timeStamp + extention;
		response.setContentType(contentType);
		
		String headerKey = "Content-Disposition",
		headerValue = "attachment; filename=" + fileName;
		response.setHeader(headerKey, headerValue);
	}

}
