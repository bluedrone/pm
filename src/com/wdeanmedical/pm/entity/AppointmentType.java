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
@Table(name = "appointment_type")
public class AppointmentType extends BaseEntity implements Serializable {
   private static final long serialVersionUID = -3127553778387743952L;
   public static final Integer OFFICE_VISIT = 1;
   
   private String name;

  public AppointmentType() {
  }

  @Column(name = "name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

}
