package com.io.webee.smart.devices;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.io.webee.smart.datastore.CloudDatastore;

@WebServlet(
    name = "RegisterAppEngine",
    urlPatterns = {"/devices/register"}
)
public class RegisterAppEngine extends HttpServlet {
	
 private CloudDatastore cd = new CloudDatastore();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");

    response.getWriter().print("Register action: \r\n" + request.getParameter("mac") );    
    cd.registerDevice(request.getParameter("mac"));
  }
  
}