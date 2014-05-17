package com.wdeanmedical.pm.persistence;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.wdeanmedical.pm.entity.Activity;
import com.wdeanmedical.pm.entity.ActivityLog;

@Transactional
public class ActivityLogDAO  extends SiteDAO{
	
      private static final Logger log = Logger.getLogger(ActivityLogDAO.class);
		
      private SessionFactory sessionFactory;

      public ActivityLogDAO() {
        super();
      }

      public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
      }

      @Override
      protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
      }
      
      public void create(ActivityLog activityLog, Integer activityId) throws Exception {
       	  Session session = this.getSession();
    	  Activity activity = (Activity)this.findById(Activity.class, activityId);
    	  activityLog.setActivity(activity);
     	  session.save(activityLog);     	
	  }

}
