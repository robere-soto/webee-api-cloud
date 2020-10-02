package com.io.webee.smart.devices;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.io.webee.smart.datastore.CloudDatastore;
import com.io.webee.smart.utils.Translator;

@WebServlet(
    name = "DeregisterAppEngine",
    urlPatterns = {"/devices/deregister"}
)
public class DeregisterAppEngine extends HttpServlet {

  final static CloudDatastore cd = new CloudDatastore();
  final static Translator tr = new Translator();
	
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

    cd.deregisterDevice(request.getParameter("mac"));
    
    response.getWriter().print(
    		tr.PrintJsonResponse(
    				cd.deregisterDevice(request.getParameter("mac"))));
  }
  
}