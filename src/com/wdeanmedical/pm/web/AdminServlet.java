/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */

package com.wdeanmedical.pm.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wdeanmedical.pm.dto.AdminDTO;
import com.wdeanmedical.pm.dto.PatientDTO;
import com.wdeanmedical.pm.entity.Patient;
import com.wdeanmedical.pm.entity.User;
import com.wdeanmedical.pm.service.AdminService;
import com.wdeanmedical.pm.core.Core;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;


public class AdminServlet extends AppServlet  {
  
  private static final Logger log = Logger.getLogger(AdminServlet.class);
  
  private AdminService adminService;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ServletContext context = getServletContext();
    try{
      adminService = new AdminService();
    }
    catch(MalformedURLException e){
      e.printStackTrace();
    }
  }
    
  @Override
  public void doPost( HttpServletRequest request, HttpServletResponse response) {
    String returnString = "";
    String pathInfo = request.getPathInfo();
    String servletPath = request.getServletPath();
    boolean isUploadResponse = false;
     
    try { 
      if (isValidSession(request, response) == false) {
        returnString = logout(request, response);  
      }
      else { 
        if (pathInfo.equals("/activateClinician")) {
          returnString = activateClinician(request, response);  
        }
        else if (pathInfo.equals("/activateUser")) {
          returnString = activateUser(request, response);  
        }
        else if (pathInfo.equals("/deactivateClinician")) {
          returnString = deactivateClinician(request, response);  
        }
        else if (pathInfo.equals("/deactivateUser")) {
          returnString = deactivateUser(request, response);  
        }
        else if (pathInfo.equals("/getUsers")) {
          returnString = getUsers(request, response);  
        }
        else if (pathInfo.equals("/purgeClinician")) {
          returnString = purgeClinician(request, response);  
        }
        else if (pathInfo.equals("/purgeUser")) {
          returnString = purgeUser(request, response);  
        }
        else if (pathInfo.equals("/saveNewClinician")) {
          returnString = saveNewClinician(request, response);  
        }
        else if (pathInfo.equals("/saveNewUser")) {
          returnString = saveNewUser(request, response);  
        }
        else if (pathInfo.equals("/updateClinician")) {
          returnString = updateClinician(request, response);  
        }
        else if (pathInfo.equals("/updateUser")) {
          returnString = updateUser(request, response);  
        }
      }
     
      ServletOutputStream  out = null;
      response.setContentType("text/plain");
     
      if (isUploadResponse == false) { 
        out = response.getOutputStream();
        out.println(returnString);
        out.close();
      }
      else { 
        PrintWriter ajaxOut = response.getWriter();
        ajaxOut.write(returnString);
        ajaxOut.close();
      }
    
    }  
    catch( IOException ioe ) {
      ioe.printStackTrace();
    } 
    catch( Exception e ) {
      e.printStackTrace();
    }
  }
  
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    doPost(request, response);  
  }
    
  public String saveNewClinician(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AdminDTO dto = gson.fromJson(data, AdminDTO.class); 
    adminService.saveNewClinician(dto);
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String updateClinician(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AdminDTO dto = gson.fromJson(data, AdminDTO.class); 
    adminService.updateClinician(dto);
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String activateClinician(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AdminDTO dto = gson.fromJson(data, AdminDTO.class); 
    adminService.activateClinician(dto);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String deactivateClinician(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AdminDTO dto = gson.fromJson(data, AdminDTO.class); 
    adminService.deactivateClinician(dto);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String purgeClinician(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AdminDTO dto = gson.fromJson(data, AdminDTO.class); 
    adminService.purgeClinician(dto);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String saveNewUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AdminDTO dto = gson.fromJson(data, AdminDTO.class); 
    adminService.saveNewUser(dto);
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AdminDTO dto = gson.fromJson(data, AdminDTO.class); 
    adminService.updateUser(dto);
    String json = gson.toJson(dto);
    return json;
  }
  
  
  public String activateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AdminDTO dto = gson.fromJson(data, AdminDTO.class); 
    adminService.activateUser(dto);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String deactivateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AdminDTO dto = gson.fromJson(data, AdminDTO.class); 
    adminService.deactivateUser(dto);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String purgeUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AdminDTO dto = gson.fromJson(data, AdminDTO.class); 
    adminService.purgeUser(dto);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String getUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AdminDTO dto = gson.fromJson(data, AdminDTO.class); 
    List<User> users = adminService.getUsers(dto); 
    dto.setUsers(users);
    String json = gson.toJson(dto);
    return json;
  }
 
}
 
 
