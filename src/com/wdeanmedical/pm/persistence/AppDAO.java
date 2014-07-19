/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */
 
package com.wdeanmedical.pm.persistence;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.wdeanmedical.pm.entity.Gender;
import com.wdeanmedical.pm.core.Core;
import com.wdeanmedical.pm.entity.Appointment;
import com.wdeanmedical.pm.entity.AppointmentType;
import com.wdeanmedical.pm.entity.BaseEntity;
import com.wdeanmedical.pm.entity.Clinician;
import com.wdeanmedical.pm.entity.Credentials;
import com.wdeanmedical.pm.entity.Ethnicity;
import com.wdeanmedical.pm.entity.MaritalStatus;
import com.wdeanmedical.pm.entity.Patient;
import com.wdeanmedical.pm.entity.PatientClinician;
import com.wdeanmedical.pm.entity.PatientImmunization;
import com.wdeanmedical.pm.entity.PatientLetter;
import com.wdeanmedical.pm.entity.PatientMedicalProcedure;
import com.wdeanmedical.pm.entity.PatientMedicalTest;
import com.wdeanmedical.pm.entity.PatientMedication;
import com.wdeanmedical.pm.entity.PatientMessage;
import com.wdeanmedical.pm.entity.PatientStatus;
import com.wdeanmedical.pm.entity.Race;
import com.wdeanmedical.pm.entity.USState;
import com.wdeanmedical.pm.entity.User;
import com.wdeanmedical.pm.entity.UserSession;
import com.wdeanmedical.pm.util.OneWayPasswordEncoder;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AppDAO extends SiteDAO {

  private static final Logger log = Logger.getLogger(AppDAO.class);

  private SessionFactory sessionFactory;

  public AppDAO() {
    super();
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  protected Session getSession() {
    return this.sessionFactory.getCurrentSession();
  }

  public void create(BaseEntity item) throws Exception {
    this.createEntity(item);
  }

  public void update(BaseEntity item) throws Exception {
    this.updateEntity(item);
  }

  public void delete(BaseEntity item) throws Exception {
    this.deleteEntity(item);
  }

  public Boolean checkEmail(String email) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(Credentials.class);
    crit.add(Restrictions.eq("email", email));
    Credentials cred = (Credentials) crit.uniqueResult();
    return (cred == null);
  }

  public List<PatientMedication> getPatientMedications(Patient patient) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientMedication.class);
    crit.add(Restrictions.eq("patient", patient));
    List<PatientMedication> list =  crit.list();
    return list;
  }

  public List<PatientImmunization> getPatientImmunizations(Patient patient) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientImmunization.class);
    crit.add(Restrictions.eq("patient", patient));
    List<PatientImmunization> list =  crit.list();
    return list;
  }

  public List<PatientMedicalTest> getPatientMedicalTests(Patient patient) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientMedicalTest.class);
    crit.add(Restrictions.eq("patient", patient));
    List<PatientMedicalTest> list =  crit.list();
    return list;
  }

  public List<PatientMedicalProcedure> getPatientMedicalProcedures(Patient patient) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientMedicalProcedure.class);
    crit.add(Restrictions.eq("patient", patient));
    List<PatientMedicalProcedure> list =  crit.list();
    return list;
  }
  
  public List<Patient> getRecentPatients(int limit) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(Patient.class);
    crit.addOrder(Order.desc("lastAccessed"));
    crit.setMaxResults(limit);
    List<Patient> list =  crit.list();
    return list;
  }

  public List<PatientLetter> getPatientLetters(Patient patient) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientLetter.class);
    crit.add(Restrictions.eq("patient", patient));
    List<PatientLetter> list =  crit.list();
    return list;
  }

  public List<PatientMessage> getPatientMessages(Patient patient, Boolean fromClinician) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientMessage.class);
    crit.add(Restrictions.eq("patient", patient));
    crit.add(Restrictions.eq("fromClinician", fromClinician));
    List<PatientMessage> list =  crit.list();
    return list;
  }

  public List<Clinician> getClinicians() throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(Clinician.class);
    List<Clinician> list =  crit.list();
    return list;
  }
  
  
  public List<Clinician> getUserCliniciansX(User user) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(Clinician.class);
    crit.add(Restrictions.eq("adminUser", user));
    List<Clinician> list =  crit.list();
    return list;
  }
  
  public List<PatientMessage> getPatientToClinicianMessagesByAdminUser(User user) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientMessage.class);
    List<Clinician> clinicians = getUserCliniciansX(user);
    Clinician clinician = new Clinician();
    clinician.setId(Clinician.ANY_CLINICIAN);
    clinicians.add(clinician);
    crit.add(Restrictions.in("clinician", clinicians));
    crit.add(Restrictions.eq("fromClinician", false));
    crit.addOrder(Order.desc("date"));
    List<PatientMessage> list =  crit.list();
    return list;
  }

  public List<Patient> getPatients() throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(Patient.class);
    List<Patient> list =  crit.list();
    return list;
  }
  
  public List<PatientMessage> getPatientToClinicianMessages() throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientMessage.class);
    crit.add(Restrictions.eq("fromClinician", false));
    crit.addOrder(Order.desc("date"));
    List<PatientMessage> list =  crit.list();
    return list;
  }

  public PatientMessage findClinicianMessageById(int id) throws Exception {
    PatientMessage patientMessage = (PatientMessage) this.findById(PatientMessage.class, id);
    patientMessage.setReadByRecipient(true);
    update(patientMessage);
    return patientMessage;
  }

  public List<Appointment> getAllAppointments() throws Exception {
    return this.findAll(Appointment.class);
  }
  
  
  
  public List<Appointment> getAppointmentsByDay(Date startTime) throws Exception {
    Calendar cal = Calendar.getInstance();
    cal.setTime(startTime);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    cal.add(Calendar.DATE, 1); 
    Date endTime = cal.getTime();
        
    Session session = this.getSession();
    Criteria crit = session.createCriteria(Appointment.class);
    crit.add(Restrictions.ge("startTime", startTime));
    crit.add(Restrictions.lt("endTime", endTime));
    crit.addOrder(Order.asc("startTime"));
    List<Appointment> list =  crit.list();
    return list;
  }



  public List<Appointment> getAppointments(Patient patient, boolean isPast) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(Appointment.class);
    crit.add(Restrictions.eq("patient", patient));
    if (isPast) {
      crit.add(Restrictions.lt("startTime", new Date()));
    }
    else {
      crit.add(Restrictions.ge("endTime", new Date()));
    }
    List<Appointment> list =  crit.list();
    return list;
  }

  public Clinician findClinicianById(Integer id) throws Exception {
    return (Clinician) this.findById(Clinician.class, id);
  }
  
  public USState findUSStateById(int id) throws Exception {
    return (USState) this.findById(USState.class, id);
  }
  
  public PatientStatus findPatientStatusById(int id) throws Exception {
    return (PatientStatus) this.findById(PatientStatus.class, id);
  }
  
  public AppointmentType findAppointmentTypeById(int id) throws Exception {
    return (AppointmentType) this.findById(AppointmentType.class, id);
  }

  public Patient findPatientById(int id) throws Exception {
    return (Patient) this.findById(Patient.class, id);
  }
  
  public Race findRaceById(Integer id) throws Exception {
    return (Race) this.findById(Race.class, id);
  }
  
  public MaritalStatus findMaritalStatusById(Integer id) throws Exception {
    return (MaritalStatus) this.findById(MaritalStatus.class, id);
  }
  
  public Ethnicity findEthnicityById(Integer id) throws Exception {
    return (Ethnicity) this.findById(Ethnicity.class, id);
  }
  

  public User findUserById(int id) throws Exception {
    return (User) this.findById(User.class, id);
  }

  public Patient findPatientBySessionId(String sessionId ) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(UserSession.class);
    crit.add(Expression.eq("sessionId", sessionId));
    UserSession userSession = (UserSession) crit.uniqueResult();
    return (Patient) this.findById(Patient.class, userSession.getUser().getId());
  }
  
  public User findUserBySessionId(String sessionId) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(UserSession.class);
    crit.add(Expression.eq("sessionId", sessionId));
    UserSession userSession = (UserSession) crit.uniqueResult();
    return (User) this.findById(User.class, userSession.getUser().getId());
  }

  public void deleteUserSession(String sessionId) {
    Session session = this.getSession();
    String hql = "delete from UserSession u where u.sessionId = :sessionId";
    Query query = session.createQuery(hql);
    query.setParameter("sessionId", sessionId);
    query.executeUpdate();
  }


  public void deleteExpiredUserSessions() throws Exception {
    Session session = getSession(); 
    Calendar timeoutThreshold = Calendar.getInstance();
    timeoutThreshold.add(Calendar.MINUTE, 0 - Core.sessionTimeout);
    Date  expireTime = timeoutThreshold.getTime();
    String hql = "delete from UserSession u where u.parked = false and u.lastAccessTime < :expireTime";
    Query query = session.createQuery(hql);
    query.setParameter("expireTime", expireTime);
    query.executeUpdate();
  }
  
  public void parkUserSession(String sessionId)  throws Exception {
    UserSession userSession = findUserSessionBySessionId(sessionId);
    userSession.setParked(true);
    update(userSession);
  }
  
  public void unparkUserSession(String sessionId)  throws Exception {
    UserSession userSession = findUserSessionBySessionId(sessionId);
    userSession.setParked(false);
    update(userSession);
  }
  


  public User authenticateUser(String username, String password) throws Exception {
    log.info("testing username: " + username);
    User user = findUserByUsername(username);
    if (user == null) {
      user = new User();
      user.setAuthStatus(User.STATUS_NOT_FOUND);
      return user;
    }
    String encodedPassword = OneWayPasswordEncoder.getInstance().encode(password, user.getSalt());

    Session session = this.getSession();
    Criteria crit = session.createCriteria(User.class);
    crit.add(Expression.eq("username", username));
    crit.add(Expression.eq("password", encodedPassword));
    user = (User) crit.uniqueResult();
    if (user != null) {
      user.setAuthStatus(User.STATUS_AUTHORIZED);
      if (user.getActive() == false) {
        user.setAuthStatus(User.STATUS_INACTIVE);
      } 
      else {
        DateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
        user.setPreviousLoginTime(user.getLastLoginTime() != null ? df.format(user.getLastLoginTime().getTime()) : "");
        user.setLastLoginTime(new Date());
        user.setSessionId(UUID.randomUUID().toString());
        update(user);
      }
    } 
    else {
      user = new User();
      user.setAuthStatus(User.STATUS_INVALID_PASSWORD);
    }
    return user;
  }

  public User findUserByUsername(String username) throws Exception {
    Session session = this.getSession();
    User user = null;
    Criteria crit = session.createCriteria(User.class);
    crit.add(Expression.eq("username", username));
    user = (User) crit.uniqueResult();
    return user;
  }

  public UserSession findUserSessionBySessionId(String sessionId ) throws Exception {
    Session session = this.getSession();
    UserSession item = null;
    Criteria crit = session.createCriteria(UserSession.class);
    crit.add(Expression.eq("sessionId", sessionId));
    item = (UserSession) crit.uniqueResult();
    return item;
  }

  public List<PatientClinician> getPatientClinicians(Patient patient) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientClinician.class);
    crit.add(Restrictions.eq("patient", patient));
    List<PatientClinician> list =  crit.list();
    return list;
  }
  
  public List<PatientClinician> getUnassignedPatientsOrig() throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientClinician.class);
    Clinician anyClinician = findClinicianById(Clinician.ANY_CLINICIAN);
    crit.add(Restrictions.eq("clinician", anyClinician));
    List<PatientClinician> list =  crit.list();
    return list;
  }
  
  public List<PatientClinician> getUnassignedPatients() throws Exception {
    Session session = this.getSession();
    String hql = "from PatientClinician pc where pc.clinician = null";
    Query query = session.createQuery(hql);
    List<PatientClinician> list = query.list();
    return list; 
  }
  
  public List<PatientClinician> getClinicianPatients(Clinician clinician) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(PatientClinician.class);
    crit.add(Restrictions.eq("clinician", clinician));
    List<PatientClinician> list =  crit.list();
    return list;
  }
  
  public List<USState> getUSStates() throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(USState.class);
    crit.addOrder(Order.asc("name"));
    List<USState> list =  crit.list();
    return list;
  }
  
  public Gender findGenderByCode(String code) throws Exception {
    Session session = getSession();
    Criteria crit = session.createCriteria(Gender.class);
    crit.add(Restrictions.eq("code", code));
    return (Gender)crit.uniqueResult();
  }
}
