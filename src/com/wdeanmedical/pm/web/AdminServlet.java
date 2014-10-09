/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */

package com.wdeanmedical.pm.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wdeanmedical.pm.dto.AdminDTO;
import com.wdeanmedical.pm.entity.User;
import com.wdeanmedical.pm.service.AdminService;
import com.google.gson.Gson;

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
          returnString = getAdminServiceData(request, "/activateClinician");  
        }
        else if (pathInfo.equals("/activateUser")) {
          returnString = getAdminServiceData(request, "/activateUser");  
        }
        else if (pathInfo.equals("/deactivateClinician")) {
          returnString = getAdminServiceData(request, "/deactivateClinician");  
        }
        else if (pathInfo.equals("/deactivateUser")) {
          returnString = getAdminServiceData(request, "/deactivateUser");  
        }
        else if (pathInfo.equals("/getUsers")) {
          returnString = getAdminServiceData(request, "/getUsers");  
        }
        else if (pathInfo.equals("/purgeClinician")) {
          returnString = getAdminServiceData(request, "/purgeClinician");  
        }
        else if (pathInfo.equals("/purgeUser")) {
          returnString = getAdminServiceData(request, "/purgeUser");  
        }
        else if (pathInfo.equals("/saveNewClinician")) {
          returnString = getAdminServiceData(request, "/saveNewClinician");  
        }
        else if (pathInfo.equals("/saveNewUser")) {
          returnString = getAdminServiceData(request, "/saveNewUser");  
        }
        else if (pathInfo.equals("/updateClinician")) {
          returnString = getAdminServiceData(request, "/updateClinician");  
        }
        else if (pathInfo.equals("/updateUser")) {
          returnString = getAdminServiceData(request, "/updateUser");  
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
  
  public String getAdminServiceData(HttpServletRequest request, String pathAction) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AdminDTO dto = gson.fromJson(data, AdminDTO.class); 
    if(pathAction.equals("/saveNewClinician")) {
      adminService.saveNewClinician(dto);
    }
    else if(pathAction.equals("/updateClinician")) {
      adminService.updateClinician(dto);
    }
    else if(pathAction.equals("/activateClinician")) {
      adminService.activateClinician(dto);
    }
    else if(pathAction.equals("/deactivateClinician")) {
      adminService.deactivateClinician(dto);
    }
    else if(pathAction.equals("/purgeClinician")) {
      adminService.purgeClinician(dto);
    }
    else if(pathAction.equals("/saveNewUser")) {
      adminService.saveNewUser(dto);
    }
    else if(pathAction.equals("/updateUser")) {
      adminService.updateUser(dto);
    }
    else if(pathAction.equals("/activateUser")) {
      adminService.activateUser(dto);
    }
    else if(pathAction.equals("/deactivateUser")) {
      adminService.deactivateUser(dto);
    }
    else if(pathAction.equals("/purgeUser")) {
      adminService.purgeUser(dto);
    }
    else if(pathAction.equals("/getUsers")) {
      List<User> users = adminService.getUsers(dto); 
      dto.setUsers(users);
    }
    String json = gson.toJson(dto);
    return json;
  } 
}