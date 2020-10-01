package com.io.webee.smart.devices;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.io.webee.smart.datastore.CloudDatastore;

@WebServlet(
    name = "QueryAppEngine",
    urlPatterns = {"/devices/info"}
)
public class QueryAppEngine extends HttpServlet {
	
  private CloudDatastore cd = new CloudDatastore();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");

    System.out.print("test");
    
    response.getWriter().print("Query action\r\n: " + cd.readDevice(request.getParameter("mac")));

  }
  
}