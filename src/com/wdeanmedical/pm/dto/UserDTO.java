package com.wdeanmedical.pm.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wdeanmedical.pm.entity.Appointment;
import com.wdeanmedical.pm.entity.Clinician;
import com.wdeanmedical.pm.entity.PatientMessage;

public class UserDTO extends AuthorizedDTO {
	private int id;
	private List<PatientMessage> patientMessages;
	private List<Appointment> appointments;
	private List<Clinician> clinicians;
	public Map<String,List> dashboard = new HashMap<String,List>();

	public UserDTO() {
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public List<PatientMessage> getPatientMessages() { return patientMessages; }
	public void setPatientMessages(List<PatientMessage> patientMessages) { this.patientMessages = patientMessages; }

	public List<Appointment> getAppointments() { return appointments; }
	public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }

	public List<Clinician> getClinicians() { return clinicians; }
	public void setClinicians(List<Clinician> clinicians) {	this.clinicians = clinicians; }	
}
