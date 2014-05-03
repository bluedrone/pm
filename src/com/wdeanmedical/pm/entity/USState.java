package com.wdeanmedical.pm.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "us_state")
public class USState extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 4749957909713623155L;
  private String name;
    

   public USState() {
   }
    
  @Column(name = "name")
  @Basic(optional = false)  
   public String getName() {
     return name;
   }
   public void setName(String name) {
     this.name = name;
   }
    

  @Override
  public String toString() {
    return "com.wdeanmedical.portal.entity.USState[id=" + getId() + "]";
  }

    
}
