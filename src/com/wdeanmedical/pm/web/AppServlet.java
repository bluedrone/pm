/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */


package com.wdeanmedical.pm.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wdeanmedical.pm.dto.MessageDTO;
import com.wdeanmedical.pm.core.Core;
import com.wdeanmedical.pm.core.Permissions;
import com.wdeanmedical.pm.dto.AppointmentDTO;
import com.wdeanmedical.pm.dto.AuthorizedDTO;
import com.wdeanmedical.pm.dto.ClinicianDTO;
import com.wdeanmedical.pm.dto.LoginDTO;
import com.wdeanmedical.pm.dto.PatientDTO;
import com.wdeanmedical.pm.dto.UserDTO;
import com.wdeanmedical.pm.entity.Appointment;
import com.wdeanmedical.pm.entity.Clinician;
import com.wdeanmedical.pm.entity.Patient;
import com.wdeanmedical.pm.entity.PatientClinician;
import com.wdeanmedical.pm.entity.PatientLetter;
import com.wdeanmedical.pm.entity.PatientMessage;
import com.wdeanmedical.pm.entity.User;
import com.wdeanmedical.pm.service.AppService;
import com.wdeanmedical.pm.util.DataEncryptor;
import com.google.gson.Gson;

import org.apache.log4j.Logger;

public class AppServlet extends HttpServlet  {

  private static final long serialVersionUID = 5141268230082988870L;
  private static final Logger log = Logger.getLogger(AppServlet.class);

  private AppService appService;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ServletContext context = getServletContext();
    try { DataEncryptor.setEncryptionKey(context.getInitParameter("encryptionKey")); } catch (Exception e1) { e1.printStackTrace();}
    Core.servletContext = context;
    Core.timeZone = context.getInitParameter("timeZone");
    Core.sendMail = new Boolean(context.getInitParameter("mail.send"));
    Core.mailFrom = context.getInitParameter("mail.from");
    Core.smtphost = context.getInitParameter("mail.smtp.host");
    Core.smtpport = context.getInitParameter("mail.smtp.port");
    Core.sessionTimeout = Integer.parseInt(context.getInitParameter("appSessionTimeout"));
    Core.imageMagickHome = context.getInitParameter("IMAGE_MAGICK_HOME");
    Core.appBaseDir = context.getRealPath("/");
    Core.appDefaultHeadshot = context.getInitParameter("appDefaultHeadshot");
    Core.mailAuthenticationUser = context.getInitParameter("mailAuthenticationUser");
    Core.mailAuthenticationPassword = context.getInitParameter("mailAuthenticationPassword");
    Core.patientDirPath = context.getInitParameter("patientDirPath");
    Core.imagesDir = context.getInitParameter("imagesDir");
    Core.filesHome = context.getInitParameter("filesHome");
    Permissions.buildUserPermissionsMap();
    try{ appService = new AppService(); } catch(MalformedURLException e){ e.printStackTrace(); }
  }

  public void doPost( HttpServletRequest request, HttpServletResponse response) {
    String returnString = "";
    String pathInfo = request.getPathInfo();
    String servletPath = request.getServletPath();

    try { 
      if (pathInfo.equals("/getStaticLists")) {
        returnString = getStaticLists(request, response);  
      }
      else if (pathInfo.equals("/login")) {
        returnString = login(request, response);  
      }
      else { 
        if (isValidSession(request, response) == false) {
          returnString = logout(request, response);  
        }
        else {
          if (pathInfo.equals("/changeApptTime")) {
            returnString = getAppointmentServiceData(request, "/changeApptTime");  
          }
          else if (pathInfo.equals("/deleteAppt")) {
            returnString = getAppointmentServiceData(request, "/deleteAppt");  
          }
          else if (pathInfo.equals("/getAppointment")) {
            returnString = getAppointmentServiceData(request, "/getAppointment");  
          }
          else if (pathInfo.equals("/getAppointments")) {
            returnString = getAppointments(request, response);  
          }
          else if (pathInfo.equals("/getClinicianMessage")) {
            returnString = getClinicianMessage(request, response);  
          }
          else if (pathInfo.equals("/getClinicians")) {
            returnString = getClinicians(request, response);  
          }
          else if (pathInfo.equals("/getMedicalAdvice")) {
            returnString = getPatientServiceData(request, "/getMedicalAdvice");  
          }
          else if (pathInfo.equals("/getPastAppointments")) {
            returnString = getPatientServiceData(request, "/getPastAppointments");  
          }
          else if (pathInfo.equals("/getPatientClinicians")) {
            returnString = getPatientServiceData(request, "/getPatientClinicians");  
          }
          else if (pathInfo.equals("/getPatientLetters")) {
            returnString = getPatientServiceData(request, "/getPatientLetters");  
          }
          else if (pathInfo.equals("/getPatientToClinicianMessages")) {
            returnString = getPatientToClinicianMessages(request, response);  
          }
          else if (pathInfo.equals("/getPatientMessages")) {
            returnString = getPatientServiceData(request, "/getPatientMessages");  
          }
          else if (pathInfo.equals("/getPatientProfileImage")) {
            returnString = getPatientProfileImage(request, response);  
          }
          else if (pathInfo.equals("/getPatients")) {
            returnString = getPatients(request, response);  
          }
          else if (pathInfo.equals("/getPatientSentMessages")) {
            returnString = getPatientServiceData(request, "/getPatientSentMessages");  
          }
          else if (pathInfo.equals("/getPatientSummary")) {
            returnString = getPatientServiceData(request, "/getPatientSummary");  
          }
          else if (pathInfo.equals("/getRecentPatients")) {
            returnString = getPatientServiceData(request, "/getRecentPatients");  
          }
          else if (pathInfo.equals("/getUpcomingAppointments")) {
            returnString = getPatientServiceData(request, "/getUpcomingAppointments");  
          }
          else if (pathInfo.equals("/logout")) {
            returnString = logout(request, response);  
          }
          else if (pathInfo.equals("/newAppt")) {
            returnString = getAppointmentServiceData(request, "/newAppt");  
          }
          else if (pathInfo.equals("/park")) {
            returnString = park(request, response);  
          }
          else if (pathInfo.equals("/patientSearch")) {
            returnString = getPatientServiceData(request, "/patientSearch");  
          }
          else if (pathInfo.equals("/saveNewPatient")) {
            returnString = getPatientServiceData(request, "/saveNewPatient");  
          }
          else if (pathInfo.equals("/suggestApptSlot")) {
            returnString = getAppointmentServiceData(request, "/suggestApptSlot");  
          }
          else if (pathInfo.equals("/unpark")) {
            returnString = unpark(request, response);  
          }
          else if (pathInfo.equals("/updateAppt")) {
            returnString = getAppointmentServiceData(request, "/updateAppt");  
          }
          else if (pathInfo.equals("/uploadProfileImage")) {
            returnString = uploadProfileImage(request, response);  
          }
        }
      }
      ServletOutputStream  out = null;
      response.setContentType("text/plain");
      out = response.getOutputStream();
      out.println(returnString);
      log.debug(returnString);
      out.close();
    }  
    catch( IOException ioe ) {
      ioe.printStackTrace();
    } 
    catch( Exception e ) {
      e.printStackTrace();
    }
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    doPost(request, response);  
  }

  protected  boolean isValidSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String ipAddress = request.getRemoteHost();
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AuthorizedDTO dto = gson.fromJson(data, AuthorizedDTO.class);  
    if (dto == null){
      dto = new AuthorizedDTO();
      dto.setSessionId(request.getParameter("sessionId"));
    }
    String path = request.getPathInfo();
    if(path.substring(1).split("/").length > 1) {
      path = path.substring(1).split("/")[1];
    } 
    path = request.getServletPath() + path;
    return appService.isValidSession(dto, ipAddress, path);
  }
  
  
  public String getPatientProfileImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    String profileImagePath = request.getParameter("profileImagePath"); 
    Gson gson = new Gson();
    String patientId = request.getParameter("patientId");
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    String filesHomePatientDirPath =  Core.filesHome  + Core.patientDirPath + "/" + patientId + "/";
	appService.getFile(request, response, getServletContext(), filesHomePatientDirPath, profileImagePath);  
    String json = gson.toJson(dto);
    return json;
  }
 
  public String getPatientServiceData(HttpServletRequest request, String pathAction) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    if(pathAction.equals("/getPatientSummary")) {
      boolean result = appService.getPatientSummary(dto);
    }
    else if(pathAction.equals("/patientSearch")) {
      List<Patient> patients = appService.getPatients(dto); 
      dto.setPatients(patients);
    }
    else if(pathAction.equals("/getPatientLetters")) {
      List<PatientLetter> patientLetters = appService.getPatientLetters(dto); 
      dto.setPatientLetters(patientLetters);
    }
    else if(pathAction.equals("/getPatientMessages")) {
      List<PatientMessage> patientMessages = appService.getPatientMessages(dto, true); 
      dto.setPatientMessages(patientMessages);
    }
    else if(pathAction.equals("/getPatientClinicians")) {
      List<PatientClinician> patientClinicians = appService.getPatientClinicians(dto); 
      dto.setPatientClinicians(patientClinicians);
    }
    else if(pathAction.equals("/getPatientMessages")) {
      List<PatientMessage> patientMessages = appService.getPatientMessages(dto, false); 
      dto.setPatientMessages(patientMessages);
    }
    else if(pathAction.equals("/getAppointments")) {
      List<Appointment> appointments = appService.getAppointments(dto, true); 
      dto.setAppointments(appointments);
    }
    else if(pathAction.equals("/getRecentPatients")) {
      List<Patient> patients = appService.getRecentPatients(dto); 
      dto.setPatients(patients);
    }
    else if(pathAction.equals("/getUpcomingAppointments")) {
      List<Appointment> appointments = appService.getAppointments(dto, false); 
      dto.setAppointments(appointments);
    }
    else if(pathAction.equals("/getMedicalAdvice")) {
      boolean result = appService.processMessage(dto);
    }
    else if(pathAction.equals("/saveNewPatient")) {
      appService.saveNewPatient(dto, request);
    }
    String json = gson.toJson(dto);
    return json;
  } 
  
  public String getAppointmentServiceData(HttpServletRequest request, String pathAction) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AppointmentDTO dto = gson.fromJson(data, AppointmentDTO.class); 
    if(pathAction.equals("/getAppointment")) {
      boolean result = appService.getAppointment(dto);
    }
    else if(pathAction.equals("/changeApptTime")) {
      boolean result = appService.changeApptTime(dto);
    }
    else if(pathAction.equals("/suggestApptSlot")) {
      appService.suggestApptSlot(dto);
    }
    else if(pathAction.equals("/newAppt")) {
      appService.newAppt(dto);
    }
    else if(pathAction.equals("/updateAppt")) {
      appService.updateAppt(dto);
    }
    else if(pathAction.equals("/deleteAppt")) {
      appService.deleteAppt(dto);
    }
    String json = gson.toJson(dto);
    return json;
  } 

  public String getClinicianMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    MessageDTO dto = gson.fromJson(data, MessageDTO.class); 
    boolean result = appService.getClinicianMessage(dto); 
    String json = gson.toJson(dto);
    return (json);
  }

  public String getPatientToClinicianMessages(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    UserDTO dto = gson.fromJson(data, UserDTO.class); 
    List<PatientMessage> patientMessages = appService.getPatientToClinicianMessages(dto, true); 
    dto.setPatientMessages(patientMessages);
    String json = gson.toJson(dto);
    return json;
  }

  public String getClinicians(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    UserDTO dto = gson.fromJson(data, UserDTO.class); 
    List<Clinician> clinicians = appService.getClinicians(dto); 
    dto.setClinicians(clinicians);
    String json = gson.toJson(dto);
    return json;
  }

  public String getPatients(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    ClinicianDTO dto = gson.fromJson(data, ClinicianDTO.class); 
    List<PatientClinician> patients = appService.getClinicianPatients(dto); 
    List<PatientClinician> unassignedPatients = appService.getUnassignedPatients(dto); 
    patients.addAll(unassignedPatients);
    dto.setClinicianPatients(patients);
    String json = gson.toJson(dto);
    return json;
  }

  public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    LoginDTO loginDTO = gson.fromJson(data, LoginDTO.class);  
    String ipAddress = request.getRemoteHost();
    User user = appService.login(loginDTO, ipAddress); 
    String json = gson.toJson(user);
    return (json);
  }

  public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AuthorizedDTO dto = gson.fromJson(data, AuthorizedDTO.class);  
    appService.logout(dto);
    dto.setAuthenticated(false);
    String json = gson.toJson(dto);
    return json;
  }

  public String checkSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AuthorizedDTO dto = gson.fromJson(data, AuthorizedDTO.class);  
    if (dto == null) {
      dto = new AuthorizedDTO();
    }
    dto.setAuthenticated(isValidSession(request, response));
    String json = gson.toJson(dto);
    return json;
  }
 
  public String getAppointments(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Gson gson = new Gson();
    List<Appointment> bookedAppts = null;
    bookedAppts = appService.getAllAppointments();
        
    ArrayList<Map<String, Object>> visitsList = new ArrayList<Map<String, Object>>();
    Map<String, Object> visitInstance = null;
    if(bookedAppts != null) {
      for(Appointment event : bookedAppts) {
        visitInstance = new HashMap<String, Object>();
        visitInstance.put("id", event.getId());
        visitInstance.put("title", event.getTitle());
        visitInstance.put("start", formatDate(event.getStartTime()));
        visitInstance.put("end", formatDate(event.getEndTime()));
        visitInstance.put("desc", event.getDesc());
        visitInstance.put("allDay", Boolean.FALSE);
        visitsList.add(visitInstance);
      }
    }
    return gson.toJson(visitsList);
  }
 
  public static String formatDate(Date date){
    String value = null;
    if (date != null){
      SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      value = dateformat.format(date);
    }
    return value;
  }
 
  public String park(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AuthorizedDTO dto = gson.fromJson(data, AuthorizedDTO.class);  
    appService.park(dto);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String unpark(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AuthorizedDTO dto = gson.fromJson(data, AuthorizedDTO.class);  
    appService.unpark(dto);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String getStaticLists(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String json = appService.getStaticLists(); 
    return json;
  }
  
  public String uploadProfileImage(HttpServletRequest request, HttpServletResponse response) throws Exception{
    return appService.uploadProfileImage(request, response);
  }

}
