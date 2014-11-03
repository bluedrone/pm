package com.wdeanmedical.pm.dto;

import java.util.Date;
import java.util.List;

import com.wdeanmedical.pm.entity.Appointment;
import com.wdeanmedical.pm.entity.Patient;
import com.wdeanmedical.pm.entity.PatientClinician;
import com.wdeanmedical.pm.entity.PatientLetter;
import com.wdeanmedical.pm.entity.PatientMedicalProcedure;
import com.wdeanmedical.pm.entity.PatientMedicalTest;
import com.wdeanmedical.pm.entity.PatientMedication;
import com.wdeanmedical.pm.entity.PatientMessage;

public class PatientDTO extends AuthorizedDTO {
  private Integer id;
  private String mrn;
  private String firstName;
  private String middleName;
  private String lastName;
  private String ssn;
  private String dob;
  private String gender;
  private Integer maritalStatus;
  private Integer race;
  private Integer ethnicity;
  private String address1;
  private String address2;
  private String city;
  private Integer usState;
  private String postalCode;
  private Integer status;
  private String employed;
  private String employer;
  private String school;
  private String schoolName;
  private String primaryPhone;
  private String secondaryPhone;
  private String email;
  private boolean active;
  private boolean purged;
  private String salt;
  private Integer authStatus;
  private String sessionId;
  private Date lastLoginTime;
  private List<PatientMedication> patientMedications;
  private List<PatientMedicalTest> patientMedicalTests;
  private List<PatientMedicalProcedure> patientMedicalProcedures;
  private List<PatientLetter> patientLetters;
  private List<PatientMessage> patientMessages;
  private List<PatientClinician> patientClinicians;
  private List<Appointment> appointments;
  private List<Patient> patients;
  private String profileImageTempPath;
  private String profileImagePath;


  public PatientDTO() {
  }


  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public Integer getId() { return id; }
  public void setId(Integer id) { this.id = id; }
  
  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  
  public String getMiddleName() { return middleName; }
  public void setMiddleName(String middleName) { this.middleName = middleName; }
  
  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  
  public String getPrimaryPhone() { return primaryPhone; }
  public void setPrimaryPhone(String primaryPhone) { this.primaryPhone = primaryPhone; }
  
  public String getSecondaryPhone() { return secondaryPhone; }
  public void setSecondaryPhone(String secondaryPhone) { this.secondaryPhone = secondaryPhone; }

  public boolean isActive() { return active; }
  public void setActive(boolean active) { this.active = active; }

  public String getSalt() { return salt; }
  public void setSalt(String salt) { this.salt = salt; }
  
  public Integer getAuthStatus() { return authStatus; }
  public void setAuthStatus(Integer authStatus) { this.authStatus = authStatus; }

  public boolean isPurged() { return purged; }
  public void setPurged(boolean purged) { this.purged = purged; }

  public String getSessionId() { return sessionId; }
  public void setSessionId(String sessionId) { this.sessionId = sessionId; }

  public Date getLastLoginTime() { return lastLoginTime; }
  public void setLastLoginTime(Date lastLoginTime) { this.lastLoginTime = lastLoginTime; }

  public List<PatientMedicalTest> getPatientMedicalTests() { return patientMedicalTests; }
  public void setPatientMedicalTests(List<PatientMedicalTest> patientMedicalTests) { this.patientMedicalTests = patientMedicalTests; }
  
  public List<PatientMedicalProcedure> getPatientMedicalProcedures() { return patientMedicalProcedures; }
  public void setPatientMedicalProcedures(List<PatientMedicalProcedure> patientProcedures) { this.patientMedicalProcedures = patientProcedures; }
  
  public List<PatientLetter> getPatientLetters() { return patientLetters; }
  public void setPatientLetters(List<PatientLetter> patientLetters) { this.patientLetters = patientLetters; }

  public List<PatientMessage> getPatientMessages() { return patientMessages; }
  public void setPatientMessages(List<PatientMessage> patientMessages) { this.patientMessages = patientMessages; }
  
  public List<Appointment> getAppointments() { return appointments; }
  public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }

  public List<PatientClinician> getPatientClinicians() { return patientClinicians; }
  public void setPatientClinicians(List<PatientClinician> patientClinicians) { this.patientClinicians = patientClinicians; }
  
  public String getMrn() { return mrn; }
  public void setMrn(String mrn) { this.mrn = mrn; }

  public List<Patient> getPatients() { return patients; }
  public void setPatients(List<Patient> patients) { this.patients = patients; }
  
  public String getDob() { return dob; }
  public void setDob(String dob) { this.dob = dob; }

  public String getSsn() { return ssn; }
  public void setSsn(String ssn) { this.ssn = ssn; }

  public String getGender() { return gender; }
  public void setGender(String gender) { this.gender = gender; }

  public Integer getMaritalStatus() { return maritalStatus; }
  public void setMaritalStatus(Integer maritalStatus) { this.maritalStatus = maritalStatus; }

  public Integer getRace() { return race; }
  public void setRace(Integer race) { this.race = race; }

  public Integer getEthnicity() { return ethnicity; }
  public void setEthnicity(Integer ethnicity) { this.ethnicity = ethnicity; }

  public String getAddress1() { return address1; }
  public void setAddress1(String address1) { this.address1 = address1; }
  
  public String getAddress2() { return address2; }
  public void setAddress2(String address2) { this.address2 = address2; }

  public String getCity() { return city; } 
  public void setCity(String city) { this.city = city; }

  public Integer getUSState() { return usState; }
  public void setUSState(Integer usState) { this.usState = usState; }

  public String getPostalCode() { return postalCode; }
  public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

  public Integer getStatus() { return status; }
  public void setStatus(Integer status) { this.status = status; }

  public String getEmployed() { return employed; }
  public void setEmployed(String employed) { this.employed = employed; }

  public String getEmployer() { return employer; }
  public void setEmployer(String employer) { this.employer = employer; }

  public String getSchool() { return school; }
  public void setSchool(String school) { this.school = school; }

  public String getSchoolName() { return schoolName; }
  public void setSchoolName(String schoolName) { this.schoolName = schoolName; }

  public List<PatientMedication> getPatientMedications() { return patientMedications; }
  public void setPatientMedications(List<PatientMedication> patientMedications) { this.patientMedications = patientMedications; }
  
  public String getProfileImageTempPath() { return profileImageTempPath; }
  public void setProfileImageTempPath(String profileImageTempPath) { this.profileImageTempPath = profileImageTempPath; }
  
  public String getProfileImagePath() { return profileImagePath; }
  public void setProfileImagePath(String profileImagePath) { this.profileImagePath = profileImagePath; }
  
}
