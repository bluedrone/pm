package com.wdeanmedical.pm.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wdeanmedical.pm.entity.Appointment;
import com.wdeanmedical.pm.entity.Patient;
import com.wdeanmedical.pm.entity.PatientClinician;
import com.wdeanmedical.pm.entity.PatientMessage;

public class ClinicianDTO extends AuthorizedDTO {
  private int id;
  private List<PatientMessage> patientMessages;
  private List<Appointment> appointments;
  private List<PatientClinician> clinicianPatients;

  public Map<String,List> dashboard = new HashMap<String,List>();

  public ClinicianDTO() {
  }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }
  
  public List<PatientMessage> getPatientMessages() { return patientMessages; }
  public void setPatientMessages(List<PatientMessage> patientMessages) { this.patientMessages = patientMessages; }
  
  public List<Appointment> getAppointments() { return appointments; }
  public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
  
  public List<PatientClinician> getClinicianPatients() { return clinicianPatients; }
  public void setClinicianPatients(List<PatientClinician> clinicianPatients) { this.clinicianPatients = clinicianPatients; }

}
