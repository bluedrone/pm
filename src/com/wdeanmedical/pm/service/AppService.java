/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */
 
package com.wdeanmedical.pm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wdeanmedical.pm.service.ActivityLogService;
import com.wdeanmedical.pm.entity.Credentials;
import com.wdeanmedical.pm.entity.Demographics;
import com.wdeanmedical.pm.entity.MedicalHistory;
import com.wdeanmedical.pm.entity.PFSH;
import com.wdeanmedical.pm.entity.PatientStatus;
import com.wdeanmedical.pm.dto.MessageDTO;
import com.wdeanmedical.pm.core.Core;
import com.wdeanmedical.pm.dto.AppointmentDTO;
import com.wdeanmedical.pm.dto.AuthorizedDTO;
import com.wdeanmedical.pm.dto.ClinicianDTO;
import com.wdeanmedical.pm.dto.LoginDTO;
import com.wdeanmedical.pm.dto.PatientDTO;
import com.wdeanmedical.pm.dto.UserDTO;
import com.wdeanmedical.pm.entity.Appointment;
import com.wdeanmedical.pm.entity.AppointmentType;
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
import com.wdeanmedical.pm.entity.UserSession;
import com.wdeanmedical.pm.persistence.AppDAO;
import com.wdeanmedical.pm.util.UserSessionData;
import com.wdeanmedical.pm.util.MailHandler;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AppService {

  private static Log log = LogFactory.getLog(AppService.class);
  private static int RECENT_PATIENT_SIZE = 5;
  public static int RETURN_CODE_DUP_EMAIL = -1;
  public static int RETURN_CODE_INVALID_PASSWORD = -2;

  private ServletContext context;
  private WebApplicationContext wac;
  private AppDAO appDAO;
  private ActivityLogService activityLogService;


  public AppService() throws MalformedURLException {
    context = Core.servletContext;
    wac = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
    appDAO = (AppDAO) wac.getBean("appDAO");
    activityLogService = new ActivityLogService();
  }

  public  List<Patient> getRecentPatients(PatientDTO dto) throws Exception {
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, null, "GetRecentPatients");
    return appDAO.getRecentPatients(RECENT_PATIENT_SIZE);
  }
  
  public  List<PatientMedication> getPatientMedications(PatientDTO dto) throws Exception {
    Patient patient = appDAO.findPatientById(dto.getId());
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, patient.getId(), "GetPatientMedications");
    return appDAO.getPatientMedications(patient);
  }
  
  public  List<Patient> getPatients(PatientDTO dto) throws Exception {
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, null, "GetPatients");
    return appDAO.getPatients();
  }

  public  List<PatientImmunization> getPatientImmunizations(PatientDTO dto) throws Exception {
    Patient patient = appDAO.findPatientById(dto.getId());
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, patient.getId(), "GetPatientImmunizations");
    return appDAO.getPatientImmunizations(patient);
  }

  public  List<PatientMedicalTest> getPatientMedicalTests(PatientDTO dto) throws Exception {
    Patient patient = appDAO.findPatientById(dto.getId());
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, patient.getId(), "GetPatientMedicalTests");
    return appDAO.getPatientMedicalTests(patient);
  }

  public  List<PatientMedicalProcedure> getPatientMedicalProcedures(PatientDTO dto) throws Exception {
    Patient patient = appDAO.findPatientById(dto.getId());
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, patient.getId(), "GetMedicalProcedures");
    return appDAO.getPatientMedicalProcedures(patient);
  }

  public  List<PatientLetter> getPatientLetters(PatientDTO dto) throws Exception {
    Patient patient = appDAO.findPatientById(dto.getId());
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, patient.getId(), "GetPatientLetters");
    return appDAO.getPatientLetters(patient);
  }

  public  List<PatientMessage> getPatientMessages(PatientDTO dto, Boolean fromClinician) throws Exception {
    Patient patient = appDAO.findPatientById(dto.getId());
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, patient.getId(), "GetPatientMessages");
    return appDAO.getPatientMessages(patient, fromClinician);
  }

  public  List<PatientMessage> getPatientToClinicianMessages(UserDTO dto, Boolean fromClinician) throws Exception {
    User user = appDAO.findUserById(dto.getId());
    Integer userId = user.getId();
    activityLogService.logViewPatient(userId, userId, null, "GetPatientToClinicianMessages");
    return appDAO.getPatientToClinicianMessages();
  }

  public boolean getClinicianMessage(MessageDTO dto) throws Exception {
    PatientMessage patientMessage = appDAO.findClinicianMessageById(dto.getId());
    dto.setContent(patientMessage.getContent());
    dto.setPatient(patientMessage.getPatient());
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, patientMessage.getPatient().getId(), "GetClinicianMessage");
    return true;
  }

  public List<Appointment> getAppointments(PatientDTO dto, boolean isPast) throws Exception {
    Patient patient = appDAO.findPatientById(dto.getId());
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, patient.getId(), "GetAppointments");
    return appDAO.getAppointments(patient, isPast);
  }



  public List<Appointment> getAllAppointments() throws Exception {
    return appDAO.getAllAppointments();
  }



  public void suggestApptSlot(AppointmentDTO dto) throws Exception{
    int nextInterval = 15;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Integer apptLengthInMinutes = dto.getApptLengthInMinutes();
    Date startTime; try { startTime = sdf.parse(dto.getStartTime()); } catch (ParseException pe) {startTime = null;}
    Calendar cal = Calendar.getInstance();
    cal.setTime(startTime);
    cal.add(Calendar.MINUTE, apptLengthInMinutes); 
    Date endTime = cal.getTime();
    Date originalStartTime = new Date(startTime.getTime());
    Date originalEndTime = new Date(endTime.getTime());
    
    List<Appointment> appointments =  appDAO.getAppointmentsByDay(startTime);
    
    for (Iterator<Appointment> iter = appointments.iterator(); iter.hasNext(); ) {
      Appointment appt = iter.next();
      if (endTime.before(appt.getStartTime())) {
        dto.setNewApptStartTime(startTime);
        dto.setNewApptEndTime(endTime);
        return;
      }
      if (iter.hasNext()) {
        Appointment nextAppt = iter.next();
        if (startTime.after(appt.getEndTime()) && endTime.before(nextAppt.getStartTime())) {
          dto.setNewApptStartTime(startTime);
          dto.setNewApptEndTime(endTime);
          return;
        }
      }
      else if (startTime.after(appt.getEndTime())) {
        dto.setNewApptStartTime(startTime);
        dto.setNewApptEndTime(endTime);
        return;
      }
      else {
        dto.setNewApptStartTime(appt.getEndTime());
        cal.setTime(appt.getEndTime());
        cal.add(Calendar.MINUTE, apptLengthInMinutes); 
        dto.setNewApptEndTime(cal.getTime());
        return;
      }
      
      cal.setTime(startTime);
      cal.add(Calendar.MINUTE, nextInterval); 
      startTime = cal.getTime();
      
      cal.setTime(endTime);
      cal.add(Calendar.MINUTE, nextInterval); 
      endTime = cal.getTime();
    }
    
    dto.setNewApptStartTime(originalStartTime);
    dto.setNewApptEndTime(originalEndTime);
  }
  
  
    
  public boolean changeApptTime(AppointmentDTO dto) throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Appointment appt = appDAO.findAppointmentById(dto.getId());
    Date startTime; try { startTime = sdf.parse(dto.getStartTime()); } catch (ParseException pe) {startTime = null;}
    appt.setStartTime(startTime);
    Date endTime; try { endTime = sdf.parse(dto.getEndTime()); } catch (ParseException pe) {endTime = null;}
    appt.setEndTime(endTime);
    appDAO.update(appt);
    return true;
  }
  
  
  
  public void newAppt(AppointmentDTO dto) throws Exception{
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    User user = appDAO.findUserBySessionId(dto.getSessionId());
    AppointmentType apptType = appDAO.findAppointmentTypeById(AppointmentType.OFFICE_VISIT);
    Clinician clinician = appDAO.findClinicianById(dto.getClinician());
    Patient patient = appDAO.findPatientById(dto.getPatient());
    String patientFullName = patient.getCred().getFirstName() + " " + patient.getCred().getLastName();
    String clinicianFullName = clinician.getFirstName() + " " + clinician.getLastName();
    Appointment appt = new Appointment();
    appt.setAppointmentType(apptType);
    appt.setClinician(clinician);
    appt.setDepartment(user.getDepartment());
    Date startTime; try { startTime = sdf.parse(dto.getStartTime()); } catch (ParseException pe) {startTime = null;}
    appt.setStartTime(startTime);
    Date endTime; try { endTime = sdf.parse(dto.getEndTime()); } catch (ParseException pe) {endTime = null;}
    appt.setEndTime(endTime);
    appt.setOverride(false);
    appt.setPatient(patient);
    appt.setTitle(patientFullName);
    appt.setDesc(dto.getDesc());
    appDAO.create(appt);
    
    String apptTimeString = sdf.format(startTime); 
    String title = "Appointment on " + apptTimeString + " with " + clinicianFullName;
    String templatePath = context.getRealPath("/WEB-INF/email_templates");
    StringTemplateGroup group = new StringTemplateGroup("underwebinf", templatePath, DefaultTemplateLexer.class);
    StringTemplate st = group.getInstanceOf("appt_scheduled");
    String from = Core.mailFrom;
 
    st.setAttribute("patient", patientFullName);
    st.setAttribute("clinician", clinicianFullName);
    st.setAttribute("email", patient.getCred().getEmail());
    st.setAttribute("phone", patient.getDemo().getPrimaryPhone());
    st.setAttribute("apptTime", apptTimeString);
    
    MailHandler handler = new MailHandler();
    boolean isHtml = true;
    String stString = st.toString();    
    Integer userId = user.getId();
    activityLogService.logViewPatient(userId, clinician.getId(), patient.getId(), "NewAppt");    
    
    handler.sendMimeMessage(patient.getCred().getEmail(), from, stString, title, isHtml);
  }
  
  
  
  public  boolean getPatientSummary(PatientDTO dto) throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    Patient patient = appDAO.findPatientById(dto.getId());
    dto.setFirstName(patient.getCred().getFirstName());
    dto.setMiddleName(patient.getCred().getMiddleName());
    dto.setLastName(patient.getCred().getLastName());
    dto.setDob(sdf.format(patient.getDemo().getDob()));
    dto.setGender(patient.getDemo().getGender().getCode());
    dto.setMrn(patient.getCred().getMrn());
    dto.setPrimaryPhone(patient.getDemo().getPrimaryPhone());
    dto.setSecondaryPhone(patient.getDemo().getSecondaryPhone());
    dto.setProfileImagePath(patient.getDemo().getProfileImagePath());
    patient.setLastAccessed(new Date());
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, null, patient.getId(), "getPatientSummary");
    appDAO.update(patient);
    return true;
  }
  
  public void getFile(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, 
                      String filePath, String fileName) throws Exception {
    String mime = servletContext.getMimeType(fileName);
    if (mime == null) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }
    response.setContentType(mime);  
    File file = new File(filePath + fileName);
    response.setContentLength((int) file.length());
    FileInputStream in = new FileInputStream(file);
    OutputStream out = response.getOutputStream();
    byte[] buf = new byte[1024];
    int count = 0;
    while ((count = in.read(buf)) >= 0) {
      out.write(buf, 0, count);
    }
    out.close();
    in.close();
  }
    
  public void saveNewPatient(PatientDTO dto, HttpServletRequest request) throws Exception{
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    
    if(appDAO.checkEmail(dto.getEmail()) == false) {
      dto.setResult(false);
      dto.setErrorMsg("Email already in system");
      dto.setReturnCode(RETURN_CODE_DUP_EMAIL);
      return;
    }
    
    Patient patient = new Patient();
    appDAO.create(patient);
    
    Demographics demo = new Demographics();
    demo.setGender(appDAO.findGenderByCode(dto.getGender()));
    Date dob; try { dob = sdf.parse(dto.getDob()); } catch (ParseException pe) {dob = null;}
    demo.setDob(dob);
    demo.setRace(appDAO.findRaceById(dto.getRace()));
    demo.setEthnicity(appDAO.findEthnicityById(dto.getEthnicity()));
    demo.setMaritalStatus(appDAO.findMaritalStatusById(dto.getMaritalStatus()));
    demo.setPatientId(patient.getId());
    demo.setPrimaryPhone(dto.getPrimaryPhone());
    demo.setSecondaryPhone(dto.getSecondaryPhone());
    demo.setStreetAddress1(dto.getAddress1());
    demo.setStreetAddress2(dto.getAddress2());
    demo.setCity(dto.getCity());
    demo.setUsState(appDAO.findUSStateById(dto.getUSState()));
    demo.setPostalCode(dto.getPostalCode());
    demo.setProfileImagePath(dto.getProfileImageTempPath());
    demo.setEmploymentStatus(dto.getEmployed());
    demo.setEmployer(dto.getEmployer());
    demo.setSchoolStatus(dto.getSchool());
    demo.setSchoolName(dto.getSchoolName());
    appDAO.create(demo);
    patient.setDemo(demo);
    
    Credentials cred = new Credentials();
    cred.setFirstName(dto.getFirstName());
    cred.setMiddleName(dto.getMiddleName());
    cred.setLastName(dto.getLastName());
    cred.setPassword("not a password"); 
    cred.setStatus(appDAO.findPatientStatusById(PatientStatus.ACTIVE));
    cred.setGovtId(dto.getSsn());
    cred.setEmail(dto.getEmail());
    cred.setUsername(dto.getEmail());
    cred.setActivationCode(UUID.randomUUID().toString());
    cred.setPatientId(patient.getId());
    appDAO.create(cred);
    patient.setCred(cred);
    
    PFSH pfsh = new PFSH();
    pfsh.setPatientId(patient.getId());
    appDAO.create(pfsh);
    patient.setPfsh(pfsh);
    
    MedicalHistory hist = new MedicalHistory();
    hist.setPatientId(patient.getId());
    appDAO.create(hist);
    patient.setHist(hist);
    
    patient.setCreatedDate(new Date());
    patient.setLastAccessed(new Date());
    appDAO.update(patient);
    
    if(dto.getProfileImageTempPath() != null){
    
      String profileImageTempPath = Core.appBaseDir + Core.imagesDir + "/" + dto.getProfileImageTempPath();
      
      String filesHomePatientDirPath =  Core.filesHome  + Core.patientDirPath + "/" + patient.getId() + "/";
           
      new File(filesHomePatientDirPath).mkdirs(); 
      
      File profileImageTempPathFile =new File(profileImageTempPath);
      profileImageTempPathFile.renameTo(new File(filesHomePatientDirPath + "/" + dto.getProfileImageTempPath()));     
          
    }   
      
    PatientClinician pc = new PatientClinician();
    pc.setPatient(patient);
    Clinician anyClinician = appDAO.findClinicianById(Clinician.ANY_CLINICIAN);
    pc.setClinician(anyClinician);
    appDAO.create(pc);
    
   // send out invitation to sign up for the portal 
    String url = 
    "http://" + request.getServerName() + ":" + request.getServerPort() + 
    "/portal/?activateUser=true&activationCode=" + patient.getCred().getActivationCode();
    //url = URLEncoder.encode(url, "ISO-8859-1");
    
    String patientFullName = patient.getCred().getFirstName() + " " + patient.getCred().getLastName();
    String title = "Patient Portal Invitation for  " + patientFullName;
    String templatePath = context.getRealPath("/WEB-INF/email_templates");
    StringTemplateGroup group = new StringTemplateGroup("underwebinf", templatePath, DefaultTemplateLexer.class);
    StringTemplate st = group.getInstanceOf("portal_invitation");
    String from = Core.mailFrom;
    st.setAttribute("patient", patientFullName);
    st.setAttribute("link", url);
    st.setAttribute("email", patient.getCred().getEmail());
    st.setAttribute("phone", patient.getDemo().getPrimaryPhone());
    
    MailHandler handler = new MailHandler();
    boolean isHtml = true;
    String stString = st.toString();
    
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, anyClinician.getId(), patient.getId(), "SaveNewPatient");
    
    handler.sendMimeMessage(patient.getCred().getEmail(), from, stString, title, isHtml);
  }
  
  public List<PatientClinician> getClinicianPatients(ClinicianDTO dto) throws Exception {
    Clinician clinician = appDAO.findClinicianById(dto.getId());
    activityLogService.logViewPatient(clinician.getId(), clinician.getId(), null, "ClinicianPatients");
    return appDAO.getClinicianPatients(clinician);
  }
  
  public List<PatientClinician> getUnassignedPatients(ClinicianDTO dto) throws Exception {
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, null, "GetUnassignedPatients");  
    return appDAO.getUnassignedPatients();
  }
  
  public List<Clinician> getClinicians(UserDTO dto) throws Exception {
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, null, "GetClinicians");
    return appDAO.getClinicians();
  }



  public boolean getAppointment(AppointmentDTO dto) throws Exception {
    Appointment appointment = appDAO.findAppointmentById(dto.getId());
    dto.setAppointment(appointment);
    return true;
  }



  public boolean processMessage(PatientDTO dto) throws Exception {
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, dto.getId(), "ProcessMessage");  
    return true;
  }

  public void logout(AuthorizedDTO dto) throws Exception {
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    String clinicianName = userSession.getUser().getUsername(); 
    Integer userId = userSession.getUser().getId();
    log.info("======= logout() of user: " + clinicianName); 
    appDAO.unparkUserSession(dto.getSessionId());
    appDAO.deleteUserSession(dto.getSessionId());
    activityLogService.logLogout(userId);
  }

  public void park(AuthorizedDTO dto) throws Exception {
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, null, "park");  
    appDAO.parkUserSession(dto.getSessionId());
  }
  
  public  void unpark(AuthorizedDTO dto) throws Exception {
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, null, "unpark");  
    appDAO.unparkUserSession(dto.getSessionId());
  }

  public  List<PatientClinician> getPatientClinicians(PatientDTO dto) throws Exception {
    Patient patient = appDAO.findPatientById(dto.getId());
    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());
    Integer userId = userSession.getUser().getId();
    activityLogService.logViewPatient(userId, userId, patient.getId(), "GetPatientClinicians");  
    return appDAO.getPatientClinicians(patient);
  }


  public User login(LoginDTO loginDTO, String ipAddress) throws Exception {
    User user = appDAO.authenticateUser(loginDTO.getUsername(), loginDTO.getPassword());
    if (user.getAuthStatus() == User.STATUS_AUTHORIZED) {
      UserSession userSession = new UserSession();
      userSession.setUser(user);
      userSession.setSessionId(user.getSessionId());
      userSession.setIpAddress(ipAddress);
      userSession.setLastAccessTime(new Date());
      userSession.setParked(false);
      appDAO.create(userSession);
      UserSessionData userSessionData = new UserSessionData();
      userSessionData.setUserSession(userSession);
      // Core.getUserSessionMap().put(userSession.getSessionId(), userSessionData);
      log.info("======= Added " + userSession.toString()); 
      activityLogService.logLogin(user.getId());
    }
    return user;
  }


  public  boolean isValidSession(AuthorizedDTO dto, String ipAddress, String path) throws Exception {
    String username = "";

    appDAO.deleteExpiredUserSessions();

    if (dto == null || dto.getSessionId() == null) {
      log.info("======= isValidSession() no session id submitted by user at ip address of " + ipAddress); 
      return false;
    }

    UserSession userSession = appDAO.findUserSessionBySessionId(dto.getSessionId());

    if (userSession == null) {
      log.info("======= isValidSession() no session found for : " + dto.getSessionId()); 
      return false;
    }


    if (userSession.getIpAddress().equals(ipAddress) == false) {
      log.info("======= isValidSession() submitted IP address is of " + ipAddress + " does not match the one found in current session"); 
      return false;
    }

    // check for proper access level
    int accessLevel = userSession.getUser().getRole().getId();
    log.info("======= isValidSession() checking " + path); 
    if (Core.userPermissionsMap.get(path) != null) {
      username = userSession.getUser().getUsername(); 
      log.info("======= isValidSession() checking " + path + " for user " + username + " with a permissions level of " + accessLevel); 
      
      if (Core.userPermissionsMap.get(path)[accessLevel] == false) {
        log.info("======= isValidSession() user " + username + " lacks permission level to execute " + path); 
        return false;
      }
    }

    // update session timestamp to current time 
    userSession.setLastAccessTime(new Date());
    appDAO.update(userSession);
    log.info("======= isValidSession() user " + username + "'s timestamp updated to " + userSession.getLastAccessTime()); 
    return true;
  }
  
  public String getStaticLists() throws Exception{
    Map<String,List> map = new HashMap<String,List>();
    Gson gson = new Gson();
    map.put("usStates", appDAO.getUSStates());
    return gson.toJson(map);
  }
  
        
  public String uploadProfileImage(HttpServletRequest request, HttpServletResponse response) throws Exception{
    InputStream is = null;
    FileOutputStream fos = null;
    String returnString = "";
    is = request.getInputStream();
    String submittedFilename = request.getHeader("X-File-Name");
    String extension = submittedFilename.substring(submittedFilename.indexOf("."));
    File f = File.createTempFile("headshot", extension, new File(Core.appBaseDir + Core.imagesDir));
    String filename = f.getName();
    fos = new FileOutputStream(new File(Core.appBaseDir + Core.imagesDir + "/" + filename));
    IOUtils.copy(is, fos);
    response.setStatus(HttpServletResponse.SC_OK);
    fos.close();
    is.close();
   
    String[] imageMagickArgs = {
      Core.imageMagickHome + "convert", 
      Core.appBaseDir + Core.imagesDir + "/" + filename, 
      "-resize", 
      "160x160", 
      Core.appBaseDir + Core.imagesDir + "/" + filename
    };
    Runtime runtime = Runtime.getRuntime();
    Process process = runtime.exec(imageMagickArgs);
    
    InputStream pis = process.getInputStream();
    InputStreamReader isr = new InputStreamReader(pis);
    BufferedReader br = new BufferedReader(isr);
    String line;
    log.info("Output of running "+ Arrays.toString(imageMagickArgs) + "is: ");

    while ((line = br.readLine()) != null) {
      log.info(line);
    }
    log.info("\n" + filename + " uploaded");
    returnString = "{\"filename\":\""+filename+"\"}";
    String sessionId = request.getParameter("sessionId");  
    UserSession userSession = appDAO.findUserSessionBySessionId(sessionId);
    Integer userId = userSession.getUser().getId(); 
    activityLogService.logViewPatient(userId, userId, null, "UploadProfileImage");      
    return returnString;
 }
 
}
