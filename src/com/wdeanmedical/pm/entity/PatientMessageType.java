package com.wdeanmedical.pm.entity;

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

@Entity
@Table(name = "patient_message_type")
public class PatientMessageType extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 2671345577570043946L;
  private String name;
  public static final int GENERAL = 1;
  public static final int MEDICAL_ADVICE = 2;
  public static final int RX_RENEWAL = 3;
  public static final int APPT_REQUEST = 4;
  public static final int INITIAL_APPT_REQUEST = 5;

  public PatientMessageType() {
  }

  @Column(name = "name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

}
