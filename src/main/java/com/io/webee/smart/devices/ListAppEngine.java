package com.io.webee.smart.devices;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.io.webee.smart.datastore.CloudDatastore;

@WebServlet(
    name = "ListAppEngine",
    urlPatterns = {"/devices/list"}
)
public class ListAppEngine extends HttpServlet {

  final static private CloudDatastore cd = new CloudDatastore();
	
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    response.getWriter().print("List action 2\r\n");

  }
  
}