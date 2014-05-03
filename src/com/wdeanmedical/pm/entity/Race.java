package com.wdeanmedical.pm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wdeanmedical.pm.entity.BaseEntity;

@Entity
@Table(name = "race")
public class Race extends BaseEntity implements Serializable {
  
  private static final long serialVersionUID = 1434408073591785698L;
  private String name;

  public Race() {
  }

  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
