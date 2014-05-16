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
  private Date lastAccessTime;
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
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
    result = prime * result + ((lastAccessTime == null) ? 0 : lastAccessTime.hashCode());
    result = prime * result + (parked ? 1231 : 1237);
    result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
    result = prime * result + ((user == null) ? 0 : user.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    UserSession other = (UserSession) obj;
    if (ipAddress == null) {
      if (other.ipAddress != null)
        return false;
    } else if (!ipAddress.equals(other.ipAddress))
      return false;
    if (lastAccessTime == null) {
      if (other.lastAccessTime != null)
        return false;
    } else if (!lastAccessTime.equals(other.lastAccessTime))
      return false;
    if (parked != other.parked)
      return false;
    if (sessionId == null) {
      if (other.sessionId != null)
        return false;
    } else if (!sessionId.equals(other.sessionId))
      return false;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "UserSession [user=" + user + ", ipAddress=" + ipAddress + ", sessionId=" + sessionId + ", lastAccessTime="
        + lastAccessTime + ", parked=" + parked + "]";
  }

}
