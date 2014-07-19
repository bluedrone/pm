/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */
 
package com.wdeanmedical.pm.persistence;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.wdeanmedical.pm.core.Core;
import com.wdeanmedical.pm.entity.Appointment;
import com.wdeanmedical.pm.entity.BaseEntity;
import com.wdeanmedical.pm.entity.Clinician;
import com.wdeanmedical.pm.entity.Credential;
import com.wdeanmedical.pm.entity.Role;
import com.wdeanmedical.pm.entity.User;
import com.wdeanmedical.pm.persistence.SiteDAO;
import com.wdeanmedical.pm.util.OneWayPasswordEncoder;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AdminDAO extends SiteDAO {

  private static final Logger log = Logger.getLogger(AdminDAO.class);

  private SessionFactory sessionFactory;

  public AdminDAO() {
    super();
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  protected Session getSession() {
    return this.sessionFactory.getCurrentSession();
  }
  
  public void create(BaseEntity item) throws Exception {
    item.setLastUpdated(new Date());
    this.createEntity(item);
  }
  
  public void update(BaseEntity item) throws Exception {
    item.setLastUpdated(new Date());
    this.updateEntity(item);
  }
  
  public void delete(BaseEntity item) throws Exception {
    this.deleteEntity(item);
  }
  
  public void createClinician(Clinician clinician) throws Exception {
    Session session = this.getSession();
    clinician.setLastAccessed(new Date());
    session.save(clinician);
  }
  
  public void createUser(User user) throws Exception {
    Session session = this.getSession();
    user.setLastAccessed(new Date());
    session.save(user);
  }
  
  public Role findRoleById(int id ) throws Exception {
    return (Role) this.findById(Role.class, id);
  }
  
  public Credential findCredentialById(int id ) throws Exception {
    return (Credential) this.findById(Credential.class, id);
  }
  
  public Boolean checkUsername(String username) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(Clinician.class);
    crit.add(Restrictions.eq("username", username));
    Clinician clinician = (Clinician) crit.uniqueResult();
    return (clinician == null);
  }
  
  public Boolean checkUserUsername(String username) throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(User.class);
    crit.add(Restrictions.eq("username", username));
    User user = (User) crit.uniqueResult();
    return (user == null);
  }
  
  public Clinician findClinicianById(int id ) throws Exception {
    return (Clinician) this.findById(Clinician.class, id);
  }
    
  public User findUserById(int id ) throws Exception {
    return (User) this.findById(User.class, id);
  }
  
  public List<User> getUsers() throws Exception {
    Session session = this.getSession();
    Criteria crit = session.createCriteria(User.class);
    crit.add(Restrictions.eq("purged", false));
    List<User> list =  crit.list();
    return list;
  }

}
