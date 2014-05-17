package com.wdeanmedical.pm.service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wdeanmedical.pm.core.Core;
import com.wdeanmedical.pm.entity.Activity;
import com.wdeanmedical.pm.entity.ActivityLog;
import com.wdeanmedical.pm.entity.BaseEntity;
import com.wdeanmedical.pm.persistence.ActivityLogDAO;

public class ActivityLogService {

	private static Log log = LogFactory.getLog(ActivityLogService.class);
	private ServletContext context;
	private WebApplicationContext wac;
	private ActivityLogDAO activityLogDAO;

	public ActivityLogService() {
		context = Core.servletContext;
		wac = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		activityLogDAO = (ActivityLogDAO) wac.getBean("activityLogDAO");
	}

	public void logLogin(String username) throws Exception {
		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedDate(new Date());
		activityLog.setTimePerformed(new Date());
		activityLog.setUsername(username);
		activityLogDAO.create(activityLog, Activity.LOGIN);
		log.info("======= Audit logged login for Username: " + username);
	}

	public void logLogout(String username)	throws Exception {
		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedDate(new Date());
		activityLog.setTimePerformed(new Date());
		activityLog.setUsername(username);
		activityLogDAO.create(activityLog, Activity.LOGOUT);
		log.info("======= Audit logged logout for Username: " + username);

	}
	
	public void logViewPatient(String username, Integer patientId, String path) throws Exception {
      ActivityLog activityLog = new ActivityLog();
      activityLog.setCreatedDate(new Date());
      activityLog.setTimePerformed(new Date());
      activityLog.setUsername(username);
      activityLog.setPatientId(patientId);
      activityLog.setFieldName(path);
      activityLogDAO.create(activityLog, Activity.VIEW_PATIENT);
      log.info("======= Audit logged view patient for Username: " + username);

    }	

}
