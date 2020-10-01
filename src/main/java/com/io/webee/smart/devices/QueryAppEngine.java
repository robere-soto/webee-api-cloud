package com.io.webee.smart.devices;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.io.webee.smart.datastore.CloudDatastore;
import com.io.webee.smart.utils.Translator;

@WebServlet(
    name = "QueryAppEngine",
    urlPatterns = {"/devices/info"}
)
public class QueryAppEngine extends HttpServlet {
	
  final static CloudDatastore cd = new CloudDatastore();
  final static Translator tr = new Translator();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
 
    String responseCode = cd.readDevice(request.getParameter("mac"));
    
    try
    {
    	Integer.parseInt(responseCode);
        response.getWriter().print(
        		tr.PrintJsonResponse(responseCode));
    }
    catch (NumberFormatException e) {
        response.getWriter().print(
        		tr.PrintJsonResponse("0","Timestamp",responseCode));
    }
   
  }
  
}