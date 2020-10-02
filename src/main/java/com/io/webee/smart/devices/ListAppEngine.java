package com.io.webee.smart.devices;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.io.webee.smart.datastore.CloudDatastore;
import com.io.webee.smart.utils.Translator;

@WebServlet(
    name = "ListAppEngine",
    urlPatterns = {"/devices/list"}
)
public class ListAppEngine extends HttpServlet {

  final static CloudDatastore cd = new CloudDatastore();
  final static Translator tr = new Translator();
	
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    
    String responseContent = cd.listDevices();
    
    try
    {
    	Integer.parseInt(responseContent);
        response.getWriter().print(
        		tr.PrintJsonResponse(responseContent));
    }
    catch (NumberFormatException e) {
        response.getWriter().print(responseContent);
    }

  }
  
}