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
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wdeanmedical.pm.dto.MessageDTO;
import com.wdeanmedical.pm.core.Core;
import com.wdeanmedical.pm.dto.AppointmentDTO;
import com.wdeanmedical.pm.dto.AuthorizedDTO;
import com.wdeanmedical.pm.dto.ClinicianDTO;
import com.wdeanmedical.pm.dto.LoginDTO;
import com.wdeanmedical.pm.dto.PatientDTO;
import com.wdeanmedical.pm.dto.SiteDTO;
import com.wdeanmedical.pm.dto.UserDTO;
import com.wdeanmedical.pm.entity.Appointment;
import com.wdeanmedical.pm.entity.Clinician;
import com.wdeanmedical.pm.entity.Patient;
import com.wdeanmedical.pm.entity.PatientClinician;
import com.wdeanmedical.pm.entity.PatientImmunization;
import com.wdeanmedical.pm.entity.PatientLetter;
import com.wdeanmedical.pm.entity.PatientMedicalProcedure;
import com.wdeanmedical.pm.entity.PatientMedicalTest;
import com.wdeanmedical.pm.entity.PatientMedication;
import com.wdeanmedical.pm.entity.PatientMessage;
import com.wdeanmedical.pm.entity.User;
import com.wdeanmedical.pm.service.AppService;
import com.wdeanmedical.pm.util.UserSessionData;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;


public class AppServlet extends HttpServlet  {

  private static final long serialVersionUID = 5141268230082988870L;
  private static final Logger log = Logger.getLogger(AppServlet.class);

  private AppService appService;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    ServletContext context = getServletContext();
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
    Core.ehrHome = context.getInitParameter("ehrHome");
    Core.portalHome = context.getInitParameter("portalHome");
    Core.buildUserPermissionsMap();
    try{
      appService = new AppService();
    }
    catch(MalformedURLException e){
      e.printStackTrace();
    }
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
          if (pathInfo.equals("/getPatientMedicalTests")) {
            returnString = getPatientMedicalTests(request, response);  
          }
          else if (pathInfo.equals("/getPatientProcedures")) {
            returnString = getPatientProcedures(request, response);  
          }
          else if (pathInfo.equals("/getPatientLetters")) {
            returnString = getPatientLetters(request, response);  
          }
          else if (pathInfo.equals("/getPatientMessages")) {
            returnString = getPatientMessages(request, response);  
          }
          else if (pathInfo.equals("/getPatientToClinicianMessages")) {
            returnString = getPatientToClinicianMessages(request, response);  
          }
          else if (pathInfo.equals("/getClinicianMessage")) {
            returnString = getClinicianMessage(request, response);  
          }
          else if (pathInfo.equals("/getMedicalAdvice")) {
            returnString = getMedicalAdvice(request, response);  
          }
          else if (pathInfo.equals("/getPatientClinicians")) {
            returnString = getPatientClinicians(request, response);  
          }
          else if (pathInfo.equals("/getPatientSentMessages")) {
            returnString = getPatientSentMessages(request, response);  
          }
          else if (pathInfo.equals("/getPastAppointments")) {
            returnString = getPastAppointments(request, response);  
          }
          else if (pathInfo.equals("/getUpcomingAppointments")) {
            returnString = getUpcomingAppointments(request, response);  
          }
          else if (pathInfo.equals("/getClinicians")) {
            returnString = getClinicians(request, response);  
          }
          else if (pathInfo.equals("/getPatients")) {
            returnString = getPatients(request, response);  
          }
          else if (pathInfo.equals("/getAppointments")) {
            returnString = getAppointments(request, response);  
          }
          else if (pathInfo.equals("/getRecentPatients")) {
            returnString = getRecentPatients(request, response);  
          }
          else if (pathInfo.equals("/patientSearch")) {
            returnString = patientSearch(request, response);  
          }
          else if (pathInfo.equals("/logout")) {
            returnString = logout(request, response);  
          }
          else if (pathInfo.equals("/park")) {
            returnString = park(request, response);  
          }
          else if (pathInfo.equals("/unpark")) {
            returnString = unpark(request, response);  
          }
          else if (pathInfo.equals("/newAppt")) {
            returnString = newAppt(request, response);  
          }
          else if (pathInfo.equals("/uploadProfileImage")) {
            returnString = uploadProfileImage(request, response);  
          }
          else if (pathInfo.equals("/saveNewPatient")) {
            returnString = saveNewPatient(request, response);  
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
    return appService.isValidSession(dto, ipAddress, request.getPathInfo());
  }

  public String getClinicianMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    MessageDTO dto = gson.fromJson(data, MessageDTO.class); 
    boolean result = appService.getClinicianMessage(dto); 
    String json = gson.toJson(dto);
    return (json);
  }

  public String getPatientMedicalTests(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientMedicalTest> patientMedicalTests = appService.getPatientMedicalTests(dto); 
    dto.setPatientMedicalTests(patientMedicalTests);
    String json = gson.toJson(dto);
    return json;
  }

  public String patientSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<Patient> patients = appService.getPatients(dto); 
    dto.setPatients(patients);
    String json = gson.toJson(dto);
    return json;
  }
  public String getPatientProcedures(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientMedicalProcedure> patientProcedures = appService.getPatientMedicalProcedures(dto); 
    dto.setPatientMedicalProcedures(patientProcedures);
    String json = gson.toJson(dto);
    return json;
  }


  public String getPatientLetters(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientLetter> patientLetters = appService.getPatientLetters(dto); 
    dto.setPatientLetters(patientLetters);
    String json = gson.toJson(dto);
    return json;
  }


  public String getPatientMessages(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientMessage> patientMessages = appService.getPatientMessages(dto, true); 
    dto.setPatientMessages(patientMessages);
    String json = gson.toJson(dto);
    return json;
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

  public String getPatientClinicians(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientClinician> patientClinicians = appService.getPatientClinicians(dto); 
    dto.setPatientClinicians(patientClinicians);
    String json = gson.toJson(dto);
    return json;
  }


  public String getPatientSentMessages(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<PatientMessage> patientMessages = appService.getPatientMessages(dto, false); 
    dto.setPatientMessages(patientMessages);
    String json = gson.toJson(dto);
    return json;
  }


  public String getPastAppointments(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<Appointment> appointments = appService.getAppointments(dto, true); 
    dto.setAppointments(appointments);
    String json = gson.toJson(dto);
    return json;
  }
  
  public String getRecentPatients(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<Patient> patients = appService.getRecentPatients(dto); 
    dto.setPatients(patients);
    String json = gson.toJson(dto);
    return json;
  }

  public String getUpcomingAppointments(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    List<Appointment> appointments = appService.getAppointments(dto, false); 
    dto.setAppointments(appointments);
    String json = gson.toJson(dto);
    return json;
  }

  public String getMedicalAdvice(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class); 
    boolean result = appService.processMessage(dto); 
    String json = gson.toJson(dto);
    return (json);
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
    String data = request.getParameter("data");
    Gson gson = new Gson();
    UserDTO appointments = gson.fromJson(data, UserDTO.class); 
        
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
  
  
  public String newAppt(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    AppointmentDTO dto = gson.fromJson(data, AppointmentDTO.class);  
    appService.newAppt(dto);
    String json = gson.toJson(dto);
    return json;
  }
  
 
  public String saveNewPatient(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String data = request.getParameter("data");
    Gson gson = new Gson();
    PatientDTO dto = gson.fromJson(data, PatientDTO.class);  
    appService.saveNewPatient(dto, request);
    String json = gson.toJson(dto);
    return json;
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


