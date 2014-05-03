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
@Table(name = "user_session")
public class UserSession extends BaseEntity implements Serializable {
  private static final long serialVersionUID = -4156072292651636347L;
  private User user;
  private String ipAddress;
  private String sessionId;
  private Date  lastAccessTime;
  private boolean parked;

  public UserSession() {
  }

  @Column(name = "ip_address")
  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  @JoinColumn(name = "user", referencedColumnName = "id")
  @ManyToOne(optional = false)
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Column(name = "session_id")
  public String getSessionId() {
    return sessionId;
  }
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
  
  @Column(name = "last_access_time")
  @Temporal(TemporalType.TIMESTAMP)
  @Basic(optional = false)
  public Date getLastAccessTime() {
    return lastAccessTime;
  }
  public void setLastAccessTime(Date lastAccessTime) {
    this.lastAccessTime = lastAccessTime;
  }
  
  
  @Column(name = "parked")
  public boolean isParked() {
    return parked;
  }
  public void setParked(boolean parked) {
    this.parked = parked;
  }

@Override
  public String toString() {
    return "UserSession[" + getSessionId() + ", " + getUser().getUsername() + ", " + getIpAddress() + ", " + getLastAccessTime() + ", " + isParked() + "]";
  }
    
  @Override
  public boolean equals(Object object) {
    if (!(object instanceof UserSession)) {
      return false;
    }
    UserSession other = (UserSession) object;
    if ((getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
      return false;
    }
    return true;
  }

}
