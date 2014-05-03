package com.wdeanmedical.pm.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


public class AppointmentDTO extends AuthorizedDTO {
	private int id;
	private String startTime;
	private String endTime;
	private Integer dayOfWeek;
	private boolean override;
	private int patient;
	private int clinician;
	private int department;
	private int appointmentType;
	private String title;
	private String desc;

	public AppointmentDTO() {
	}

	
	public int getId() { return id; }
	public void setId(int id) {	this.id = id; }

	public String getStartTime() { return startTime; }
	public void setStartTime(String startTime) { this.startTime = startTime;}

	public String getEndTime() { return endTime; }
	public void setEndTime(String endTime) { this.endTime = endTime; }

	public Integer getDayOfWeek() {	return dayOfWeek; }
	public void setDayOfWeek(Integer dayOfWeek) { this.dayOfWeek = dayOfWeek; }

	public boolean isOverride() { return override; }
	public void setOverride(boolean override) { this.override = override; }

	public int getPatient() { return patient; }
	public void setPatient(int patient) { this.patient = patient; }

	public int getClinician() {	return clinician; }
	public void setClinician(int clinician) { this.clinician = clinician; }

	public int getDepartment() { return department; }
	public void setDepartment(int department) { this.department = department; }

	public int getAppointmentType() { return appointmentType;	}
	public void setAppointmentType(int appointmentType) { this.appointmentType = appointmentType; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getDesc() { return desc; } 
	public void setDesc(String desc) { this.desc = desc; }
	
}
