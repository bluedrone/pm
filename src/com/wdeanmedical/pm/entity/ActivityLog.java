package com.wdeanmedical.pm.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activity_log")
public class ActivityLog extends BaseEntity implements Serializable
{
  private static final long serialVersionUID = -5032794080968872640L;
  private String username;
  private Integer patientId;
  private Date timePerformed;
  private Integer clinicianId;
  private Integer encounterId;
  private String fieldName;
  private Activity activity;

  public ActivityLog()
  {}
  @Column(name = "username")
  public String getUsername()
  {
    return username;
  }
  public void setUsername(String username)
  {
    this.username = username;
  }
  @Column(name = "patient_id")
  public Integer getPatientId()
  {
    return patientId;
  }
  public void setPatientId(Integer patientId)
  {
    this.patientId = patientId;
  }
  @Column(name = "time_performed")
  public Date getTimePerformed()
  {
    return timePerformed;
  }
  public void setTimePerformed(Date timePerformed)
  {
    this.timePerformed = timePerformed;
  }
  @Column(name = "clinician_id")
  public Integer getClinicianId()
  {
    return clinicianId;
  }
  public void setClinicianId(Integer clinicianId)
  {
    this.clinicianId = clinicianId;
  }
  @Column(name = "encounter_id")
  public Integer getEncounterId()
  {
    return encounterId;
  }
  public void setEncounterId(Integer encounterId)
  {
    this.encounterId = encounterId;
  }
  @Column(name = "field_name")
  public String getFieldName()
  {
    return fieldName;
  }
  public void setFieldName(String fieldName)
  {
    this.fieldName = fieldName;
  }
  @JoinColumn(name = "activity", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public Activity getActivity()
  {
    return activity;
  }
  public void setActivity(Activity activity)
  {
    this.activity = activity;
  }
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((activity == null) ? 0 : activity.hashCode());
    result = prime * result + ((clinicianId == null) ? 0 : clinicianId.hashCode());
    result = prime * result + ((encounterId == null) ? 0 : encounterId.hashCode());
    result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
    result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
    result = prime * result + ((timePerformed == null) ? 0 : timePerformed.hashCode());
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    return result;
  }
  @Override
  public boolean equals(Object obj)
  {
    if(this == obj) return true;
    if(!super.equals(obj)) return false;
    if(getClass() != obj.getClass()) return false;
    ActivityLog other = (ActivityLog) obj;
    if(activity == null)
    {
      if(other.activity != null) return false;
    } else if(!activity.equals(other.activity)) return false;
    if(clinicianId == null)
    {
      if(other.clinicianId != null) return false;
    } else if(!clinicianId.equals(other.clinicianId)) return false;
    if(encounterId == null)
    {
      if(other.encounterId != null) return false;
    } else if(!encounterId.equals(other.encounterId)) return false;
    if(fieldName == null)
    {
      if(other.fieldName != null) return false;
    } else if(!fieldName.equals(other.fieldName)) return false;
    if(patientId == null)
    {
      if(other.patientId != null) return false;
    } else if(!patientId.equals(other.patientId)) return false;
    if(timePerformed == null)
    {
      if(other.timePerformed != null) return false;
    } else if(!timePerformed.equals(other.timePerformed)) return false;
    if(username == null)
    {
      if(other.username != null) return false;
    } else if(!username.equals(other.username)) return false;
    return true;
  }
  @Override
  public String toString()
  {
    return "ActivityLog [username=" + username + ", patientId=" + patientId + ", timePerformed=" + timePerformed
        + ", clinicianId=" + clinicianId + ", encounterId=" + encounterId + ", fieldName=" + fieldName + ", activity="
        + activity + "]";
  }
}
