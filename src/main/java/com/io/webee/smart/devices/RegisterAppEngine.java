package com.io.webee.smart.devices;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.io.webee.smart.datastore.CloudDatastore;
import com.io.webee.smart.utils.Translator;

@WebServlet(
    name = "RegisterAppEngine",
    urlPatterns = {"/devices/register"}
)
public class RegisterAppEngine extends HttpServlet {
	
 private final static CloudDatastore cd = new CloudDatastore();
 private final static Translator tr = new Translator();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    
    if(!tr.isValidMACAddress(request.getParameter("mac"))) {
    	response.getWriter().print(
        		tr.PrintJsonResponse("3003"));
    	return;
    }
    
    response.getWriter().print(    
    		tr.PrintJsonResponse(
    				cd.registerDevice(request.getParameter("mac"))));
  }
  
}