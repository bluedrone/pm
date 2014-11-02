/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */


var DO_SCROLL = true;
var DONT_SCROLL = false;
var DO_AUTO_LOGOUT = true;
var DEMO_MODE_ON = true;
var DEMO_MODE_OFF = false;
var DO_AUTO_SERVER_LOGOUT = true;
var INITIALIZED = false;
var USER_STATUS_AUTHORIZED = 1;
var USER_STATUS_NOT_FOUND = 0;
var USER_STATUS_INVALID_PASSWORD = -1;
var USER_STATUS_INACTIVE = -2;
var DEFAULT_TABLE_WIDTH = 772;
var DEMO_USERNAME = 'mmanager';
var DEMO_PASSWORD = 'Njs2101$';
var RETURN_CODE_DUP_EMAIL = -1;
var RETURN_CODE_INVALID_PASSWORD = -2;
var app_profileImageTempPath = "";
var clinicians;
var clinicianSelectOptions;
var clinicianPatients;
var clinicianPatientsSelectOptions;
var selectedClinician;
var userFullName;
var app_usStates;
var user = null;
var users;
var patients;
var clinicianMessages;
var app_currentPatientId;
var app_currentMessageId;
var app_currentCalendarView = 'month';
var app_idleInterval;
var app_idleTime = 0;
var app_parkWarningDisplayed;
var ONE_SECOND =  1000;
var ONE_MINUTE = 60000;

/************      @JQUERY INIT    *******************/
(function() {
	jqueryInit = function()	{
		if (INITIALIZED == false) {
		    getStaticLists();
		    INITIALIZED = true;
		    $(function () { $("[data-toggle='popover']").popover({ trigger: "hover" }); });
		    app_viewStack('signin-screen', DO_SCROLL);
		    $('.dropdown-menu').find('form').click(function (e) { e.stopPropagation(); });
		
		    !function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';
		    if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";
		    fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");
		    
		    $(document).mousemove( function(){ app_timerReset(); });
		    window.onbeforeunload = confirmBeforeUnload;
		  }
	}
	$(document).ready(function() {
		standalone = modulejs.require("app/standalone")
	    standalone.ready(jqueryInit)
	})
})()
/***********      @JQUERY INIT    *******************/

function app_runIdleTimer() {
  app_idleTime = 0;
  if (app_idleInterval) {clearInterval(app_idleInterval)};
  app_idleInterval = setInterval(app_timerIncrement, ONE_MINUTE);
}


function app_timerReset() {
  if (app_parkWarningDisplayed) { 
    $('#wdm-notification-text').html('');
    app_parkWarningDisplayed = false;
  }
  app_idleTime = 0;
}


function app_timerIncrement() {
  app_idleTime++;
  if (app_idleTime == 25) {
    displayNotification('Your session will soon be automatically parked if still idle', true);
    app_parkWarningDisplayed = true;
  }
  else if (app_idleTime == 30) {
    showParkDialog();
  }
}


function confirmBeforeUnload() {
  if (user && user != null) {
    return "Please log out first in order to save your data."; 
  }
}

$('#about').click(function(){ 
  RenderUtil.render('about', {}, function(s) {
    $('#modals-placement').html(s);
    $('#modal-about').modal('show'); 
  });
});

$('#mpark, #mspark').click(function(){ showParkDialog() });

function showParkDialog() {
  RenderUtil.render('park', {}, function(s) { 
    $('#modals-placement').html(s);
    $('#app-parked-full-name').html(userFullName);
    $('#modal-park').modal('show'); 
    park();
    $('.app-exit').click(function(){ logout(); });
    $('#app-unpark-submit').click(function(){ unpark(); });
  });
}

$('#new-message').click(function(){ 
  RenderUtil.render('new_message', {}, function(s) {
    $('#modals-placement').html(s);
    $('#modal-new-message').modal('show'); 
  });
});

$('.patient-button-group').click(function(){ 
  if (app_currentPatientId != null) {
    if (app_currentScreen != 'patient-chart-screen') {
      viewPatientChart();
    }
    return;
  }
  patientSearchDialog();
});


function patientSearchDialog() {
  RenderUtil.render('patient_search', {}, function(s) {
    $('#modals-placement').html(s);
    $('#modal-patient-search').modal('show'); 
    $('#btn-patient-search-ok').prop('disabled', true);
    $('#btn-patient-search-ok').addClass('disabled');
    $('.clickable-table-row').removeClass('table-row-highlight');
    $('#btn-patient-search-search').click(function(){ patientSearch(); });
    $('#btn-patient-search-ok').click(function(){ getPatientChart(); });
    getRecentPatients();
  
    $('#btn-patient-search-new-patient').click(function() { 
      RenderUtil.render('new_patient', {}, function(s) {
        $('#modals-placement').append(s);
        $('#modal-new-patient').modal('show');
        RenderUtil.render('component/basic_select_options', {options:app_usStates}, function(s) {
          $("#new-patient-us-state").html(s);
          app_profileImageTempPath = "";
          $('#new-patient-ssn').mask("999-99-9999");
          $('#new-patient-dob').mask("99/99/9999");
          $('#new-patient-postal-code').mask("99999");
          $('#new-patient-cancel').click(function(){ $('#modal-new-patient').modal('hide'); });
          $('#new-patient-save').click(function(){ saveNewPatient() });
          $('#modal-new-patient').on('hidden.bs.modal', function () { debug("new-patient modal hidden"); });
          $('#modal-new-patient').on('hide.bs.modal', function () { debug("new-patient hide called"); });
          $('#new-patient-photo-upload').click(function(){ 
            $('#new-patient-photo-upload-progress .progress-bar').css('width', '0');
          });
          setupPictureUpload();
        });
      });
    $('#modal-patient-search').on('hidden.bs.modal', function () { debug("patient-search modal hidden"); });
    $('#modal-patient-search').on('hide.bs.modal', function () { debug("patient-search hide called"); });
  });
 });
}

$('#app-signin-submit').click(function(){ login(DEMO_MODE_OFF); });
$('#get-started-btn').click(function(){ login(DEMO_MODE_ON); });
$('.app-exit').click(function(){ logout(); });

$('.app-dashboard-link').click(function(){ viewDashboard(); });
$('.app-patient-summary-link').click(function(){ viewPatientSummary(); });
$('#btn-patient-search-ok').click(function(){ getPatientSummary(); });
$('.app-messages-link').click(function(){ viewMessages(); });
$('.app-letters-link').click(function(){ viewLetters(); });
$('.app-schedule-link').click(function(){ viewSchedule(); });
$('#btn-patient-search-search').click(function(){ patientSearch(); });
$('.patient-button-group').click(function(){ onPatientButtonClick(); });
$('#message-view-button').click(function(){ viewClinicianMessage(); });
$('#message-close-button').click(function(){ viewMessages(); });

function viewMessages() {
  app_viewStack('messages-screen', DO_SCROLL);
  $('#messages-inbox-header').html('Inbox');
  getPatientToClinicianMessages();
}


function getPatientToClinicianMessages() {
  var jsonData = JSON.stringify({ id: user.id, sessionId: user.sessionId });
  $.post("app/getPatientToClinicianMessages", {data:jsonData}, function(data) {
  var parsedData = $.parseJSON(data);
  clinicianMessages = parsedData.patientMessages;
   var args =  {items:clinicianMessages, 
    title:'Messages', 
    tableName:'messages-inbox', 
    clickable:true, 
    columns:[
     {title:'Date', field:'date', type:'date'},
     {title:'From', field:'from', type:'simple'},
     {title:'Subject', field:'subject', type:'simple'}
    ]};
    RenderUtil.render('simple_data_table', args, function(s) {
      $('#messages-inbox').html(s);
      $('.clickable-table-row').dblclick( function(e){ 
        handleDoubleClickedRow(e); 
      });
      $('.clickable-table-row').click( function(e){ 
        $(this).addClass('table-row-highlight').siblings().removeClass('table-row-highlight');
        handleClickableRow(e); 
      });
    });
  });
}

function viewClinicianMessage() {
  $('#messages-view').css({display: "block"});
  $('#messages-inbox').css({display: "none"});
  var jsonData = JSON.stringify({ id: app_currentMessageId, sessionId: user.sessionId });
  $.post("app/getClinicianMessage", {data:jsonData}, function(data) {
	var parsedData = $.parseJSON(data);
    var content = parsedData.content;
    if (parsedData.patient) {
      var patientName = parsedData.patient.fullName;
      $('#messages-inbox-header').html("Message from: " + patientName);
    }
    else {
      $('#messages-inbox-header').html("Message from Request Appointment Form");
    }
    $('#message-content').html('<pre>'+content+'</pre>');
  });
}

function viewLetters() {
  app_viewStack('letters-screen', DO_SCROLL);
}

function viewSchedule() {
  app_viewStack('schedule-screen', DO_SCROLL);
  app_loadCalendar();
}

function onPatientButtonClick() {
  $('#btn-patient-search-ok').prop('disabled', true);
  $('#btn-patient-search-ok').addClass('disabled');
  $('.clickable-table-row').removeClass('table-row-highlight');
}


function getRecentPatients() {
  var jsonData = JSON.stringify({ sessionId: user.sessionId });
  $.post("app/getRecentPatients", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    patients = parsedData.patients;
    RenderUtil.render('simple_data_table', 
     {items:patients, 
      title:'Recent Patients', 
      tableName:'patient-search-results', 
      clickable:true, 
      columns:[
        {title:'Full Name', field:'cred.firstName', type:'double-patient'},
        {title:'Date of Birth', field:'dob', type:'date'},
        {title:'Gender', field:'demo.gender.name', type:'triple'},
        {title:'Region', field:'demo.region', type:'double'}
      ]}, function(s) {
      $('#patient-search-results').html(s);
      $('#patient-search-results-title').html("Recent Patients");
      $('.clickable-table-row').dblclick( function(e){ 
        handleDoubleClickedRow(e); 
      });
      $('.clickable-table-row').click( function(e){ 
        $(this).addClass('table-row-highlight').siblings().removeClass('table-row-highlight');
        handleClickableRow(e); 
      });
    });
  });
}


function patientSearch() {
  var jsonData = JSON.stringify({sessionId: user.sessionId });
  $.post("app/patientSearch", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    patients = parsedData.patients;
    RenderUtil.render('simple_data_table', 
     {items:patients, 
      title:'Patients', 
      tableName:'patient-search-results', 
      clickable:true, 
      columns:[
        {title:'Full Name', field:'cred.firstName', type:'double-patient'},
        {title:'Date of Birth', field:'dob', type:'date'},
        {title:'Gender', field:'demo.gender.name', type:'triple'},
        {title:'Region', field:'demo.region', type:'double'}
      ]}, function(s) {
      $('#patient-search-results').html(s);
      $('#patient-search-results-title').html("Patient Search");
      $('.clickable-table-row').click( function(e){ 
        $(this).addClass('table-row-highlight').siblings().removeClass('table-row-highlight');
        handleClickableRow(e); 
      });
    });
  });
}


function handleDoubleClickedRow(e) {
  var id = undefined;
  var tableId = undefined;
  var tableName = undefined;
  var attributes = e.currentTarget.attributes;
  for (i=0;i<attributes.length;i++) {
    if (attributes[i].name == 'name') {
      id = attributes[i].nodeValue; 
    }
    else if (attributes[i].name == 'id') {
      tableId = attributes[i].nodeValue; 
    }
    else if (attributes[i].name == 'data-table-name') {
      tableName = attributes[i].nodeValue; 
    }
  }

  if (id !== undefined) {
    if (tableName == 'patient-search-results') {
      app_currentPatientId = id; 
      $('#modal-patient-search').modal('hide'); 
      getPatientSummary();
    }
    else if (tableName == 'messages-inbox') {
      app_currentMessageId = id; 
      viewClinicianMessage();
    }
  }
}


function handleClickableRow(e) {
  var id = undefined;
  var tableId = undefined;
  var tableName = undefined;
  var attributes = e.currentTarget.attributes;
  for (i=0;i<attributes.length;i++) {
    if (attributes[i].name == 'name') {
      id = attributes[i].nodeValue; 
    }
    else if (attributes[i].name == 'id') {
      tableId = attributes[i].nodeValue; 
    }
    else if (attributes[i].name == 'data-table-name') {
      tableName = attributes[i].nodeValue; 
    }
  }

  if (id !== undefined) {
    if (tableName == 'patient-search-results') {
      app_currentPatientId = id; 
      $('#modal-patient-search').modal('hide'); 
      getPatientSummary();
    }
    else if (tableName == 'messages-inbox') {
      app_currentMessageId = id; 
    }
  }
}



function getPatientSummary() {
  var jsonData = JSON.stringify({ id: app_currentPatientId, sessionId: user.sessionId });
  $.post("app/getPatientSummary", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    app_viewStack('patient-summary-screen', DO_SCROLL);
    var fullName = util_buildFullName(parsedData.firstName, parsedData.middleName, parsedData.lastName);
    var patientHeadshot = 'app/getPatientProfileImage?sessionId=' + parsedData.sessionId + "&patientId=" + parsedData.id  + "&profileImagePath=" + parsedData.profileImagePath;
    $('.patient-summary-full-name').html(fullName);
    $('.patient-summary-dob').html(dateFormat(parsedData.dob, 'mm/dd/yyyy'));
    $('.patient-summary-gender').html(parsedData.gender.name);
    $('.patient-summary-mrn').html(parsedData.mrn);
    $('.patient-summary-primary-phone').html(parsedData.primaryPhone);
    $('.patient-summary-secondary-phone').html(parsedData.secondaryPhone);
    if (!$('.headshot').attr('src')) {
	  	$('.headshot').attr('src', patientHeadshot);
    }
    viewPatientSummary();
  });
}


function getClinicians() {
  var jsonData = JSON.stringify({ id: user.id, sessionId: user.sessionId });
  $.post("app/getClinicians", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    clinicians = parsedData.clinicians;
    RenderUtil.render('component/clinician_select_options', {options:clinicians}, function(s) {
      $("#app-appt-clinician").html(s);
    });
  });
}

function getClinicianPatients() {
  var jsonData = JSON.stringify({id: (selectedClinician != "" ? selectedClinician : 0), sessionId: user.sessionId });
  $.post("app/getPatients", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    clinicianPatients = parsedData.clinicianPatients;

    clinicianPatientsSelectOptions = "<option value=''>choose</option>";   
    for (var i=0;i<clinicianPatients.length;i++){
      clinicianPatientsSelectOptions += "<option value='"+clinicianPatients[i].patient.id+"'>"+
      util_buildFullName(clinicianPatients[i].patient.cred.firstName, clinicianPatients[i].patient.cred.middleName, clinicianPatients[i].patient.cred.lastName) +
      "</option>";
    }

    $("#app-appt-patient").html(clinicianPatientsSelectOptions);  
  });
}

  function getColumnValue(column, item) {
    var value = '';
    var columnFields = column.field.split('.'); 
    
    if (column.type == 'simple') {
      value = item[column.field];
    }
    else if (column.type == 'date') {
      value = dateFormat(item[column.field], 'mm/dd/yyyy')
    }
    else if (column.type == 'date-time') {
      value = dateFormat(item[column.field], 'mm/dd/yyyy hh:mm')
    }
    else if (column.type == 'double') {
      var field0 = columnFields[0];
      var field1 = columnFields[1];
      value = item[field0][field1];
    }  
    else if (column.type == 'double-patient') {
      var field0 = columnFields[0];
      var field1 = columnFields[1];
      value = item[field0][field1];
      value = util_buildFullName(item[field0]['firstName'], item[field0]['lastName'], item[field0]['additionalName'])

      /*
      else if (column.type == 'patients') {
        value = util_buildFullName(item['firstName'], item['lastName'], item['additionalName'])
      }
      else if (column.type == 'description') {
        value = item['appointmentType']['name'] + " with " + util_buildFullName(item[columnField]['firstName'], item[columnField]['middleName'], item[columnField]['lastName'])
      }
      else if (column.type == 'streetAddress') {
        value = util_nullCheck(item['streetAddress1']) + " " + util_nullCheck(item['streetAddress2']);
      }
      */
    }
    else if (column.type == 'triple') {
      var columnFields = column.field.split('.'); 
      var field0 = columnFields[0];
      var field1 = columnFields[1];
      var field2 = columnFields[2];
      value = item[field0][field1][field2];
    }
    return value;
  }

function viewPatientSummary() {
  app_viewStack('patient-summary-screen', DO_SCROLL);
}

function viewDashboard() {
  app_viewStack('dashboard-screen', DO_SCROLL);
}

function login(demoMode) {
  var username;
  var password;
  
  if (demoMode == false) {
    if($.trim($("#app-signin-username").attr("value")).length < 1 || $.trim($("#app-signin-password").attr("value")).length < 1) { 
      return;
    }
    username = $.trim($("#app-signin-username").attr("value"));
    password = $.trim($("#app-signin-password").attr("value"));
  }
  else {
    username = DEMO_USERNAME;
    password = DEMO_PASSWORD;
  }
  $('#app-login-running').css({display: "block"});
  var jsonData = JSON.stringify({ username: username, password: password});
  $.post("app/login", {data:jsonData},
      function(data) {
    $('#app-login-error').css({display:'none'});
    var parsedData = $.parseJSON(data);
    user = new User();
    user.id = parsedData.id;
    user.firstName = parsedData.firstName;
    user.middleName = parsedData.middleName;
    user.lastName = parsedData.lastName;
    user.streetAddress1 = parsedData.streetAddress1;
    user.streetAddress2 = parsedData.streetAddress2;
    user.city = parsedData.city;
    user.state = parsedData.state;
    user.zip = parsedData.zip;
    user.primaryPhone = parsedData.primaryPhone;
    user.secondaryPhone = parsedData.secondaryPhone;
    user.email = parsedData.email;
    user.authStatus = parsedData.authStatus;
    user.patientId = parsedData.patientId;
    user.previousLoginTime = parsedData.previousLoginTime;
    user.sessionId = parsedData.sessionId;

    $('#app-login-running').css({display: "none"});

    if (user.authStatus == USER_STATUS_AUTHORIZED) {
      userFullName = util_buildFullName(user.firstName, user.middleName, user.lastName);
      notificationText = userFullName + ' logged in.';
      app_viewStack('dashboard-screen', DO_SCROLL); 
      app_runIdleTimer(); 
    }  
    else  {
      if (user.authStatus == USER_STATUS_NOT_FOUND) {
        notificationText = 'User not found in system';
      }
      else if (user.authStatus == USER_STATUS_INVALID_PASSWORD) {
        notificationText = 'Invalid password';
      }
      else if (user.authStatus == USER_STATUS_INACTIVE) {
        notificationText = 'User is inactive';
      }
    }
    displayNotification(notificationText);
  }
  );  
}


function logout() {
  if (user == null) {
    return;
  }
  var jsonData = JSON.stringify({ sessionId: user.sessionId });
  $.post("app/logout", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    app_viewStack('signin-screen', DO_SCROLL);
    notificationText = userFullName + ' logged out.';
    if (app_idleInterval) {clearInterval(app_idleInterval)};
    displayNotification(notificationText);
    user = null;
  });
}


function park() {
  var jsonData = JSON.stringify({ sessionId: user.sessionId });
  $.post("app/park", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    if (app_idleInterval) {clearInterval(app_idleInterval)};
  });
}

function unpark() {
  if($.trim($("#app-unpark-username").attr("value")).length < 1 || $.trim($("#app-unpark-password").attr("value")).length < 1) { 
    return;
  }
  var username = $.trim($("#app-unpark-username").attr("value"));
  var password = $.trim($("#app-unpark-password").attr("value"));
  
  $('#app-unpark-running').css({display: "block"});
  var jsonData = JSON.stringify({ username: username, password: password, sessionId: user.sessionId});
  $.post("app/unpark", {data:jsonData},
    function(data) {
      $('#app-login-error').css({display:'none'});
      var parsedData = $.parseJSON(data);
      
      $('#app-login-running').css({display: "none"});
        
      if (user.authStatus == USER_STATUS_AUTHORIZED) {
        notificationText = userFullName + ' unparked.';
        $('#modal-park').modal('hide'); 
        displayNotification(notificationText);
        app_runIdleTimer(); 
      }  
      else  {
        if (user.authStatus == USER_STATUS_NOT_FOUND) {
          notificationText = 'User not found in system';
        }
        else if (user.authStatus == USER_STATUS_INVALID_PASSWORD) {
          notificationText = 'Invalid password';
        }
        else if (user.authStatus == USER_STATUS_INACTIVE) {
          notificationText = 'User is inactive';
        }
        $("#app-unpark-notification").html(notificationText);
      }
    }
  ); 
}

function showError(item, message) {
  if (message == null) {
    message = 'required field';
  }
  $(item).text(message);
  $(item).css({opacity: 0.0, visibility: "visible"}).animate({opacity: 1.0}); 
}


function displayNotification(text, sticky) {
  $('#wdm-notification-text').html(text);
  if (sticky) {
    $('#wdm-notification').fadeIn(400);
  }
  else {
    $('#wdm-notification').fadeIn(400).delay(3000).fadeOut(400); 
  }
}


function getStaticLists() {
  $.post("app/getStaticLists", {}, function(data) {
    parsedData = $.parseJSON(data);
    app_usStates = parsedData.usStates;
 });
}


function saveNewPatient() {
  var isValid = true;
  saveNewPatient_clearErrors();
  

  if($("#new-patient-first-name").val().length < 1) { 
    showError('#new-patient-first-name-validation');
    isValid = false;
  }
  if($("#new-patient-last-name").val().length < 1) { 
    showError('#new-patient-last-name-validation');
    isValid = false;
  }
  if($("#new-patient-ssn").val().length < 1) { 
    showError('#new-patient-ssn-validation');
    isValid = false;
  }
  if($("#new-patient-dob").val().length < 1) { 
    showError('#new-patient-dob-validation');
    isValid = false;
  }
  if($("#new-patient-gender").val().length < 1) { 
    showError('#new-patient-gender-validation');
    isValid = false;
  }
  if($("#new-patient-address1").val().length < 1) { 
    showError('#new-patient-address-validation');
    isValid = false;
  }
  if($("#new-patient-city").val().length < 1) { 
    showError('#new-patient-city-validation');
    isValid = false;
  }
  if($("#new-patient-us-state").val().length < 1) { 
    showError('#new-patient-us-state-validation');
    isValid = false;
  }
  if($("#new-patient-postal-code").val().length < 1) { 
    showError('#new-patient-postal-code-validation');
    isValid = false;
  }
  if($("#new-patient-primary-phone").val().length < 1) { 
    showError('#new-patient-primary-phone-validation');
    isValid = false;
  }
  
  var emailValid = util_checkRegexp($.trim($("#new-patient-email").val()), /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);
  if(emailValid == false) {
    showError('#new-patient-email-validation', 'invalid email address');
    isValid = false;
  }

  if (isValid == false) {
    return;
  }
  
  var jsonData = JSON.stringify({ 
    mrn: $('#new-patient-id').val(), 
    lastName: $('#new-patient-last-name').val(), 
    firstName: $('#new-patient-first-name').val(), 
    middleName: $('#new-patient-middle-initial').val(), 
    ssn: $('#new-patient-ssn').val(), 
    dob: $('#new-patient-dob').val(), 
    gender: $('#new-patient-gender').val(), 
    maritalStatus: $('#new-patient-marital-status').val(), 
    race: $('#new-patient-race').val(), 
    ethnicity: $('#new-patient-ethnicity').val(), 
    address1: $('#new-patient-address1').val(), 
    address2: $('#new-patient-address2').val(), 
    city: $('#new-patient-city').val(), 
    usState: $('#new-patient-us-state').val(), 
    postalCode: $('#new-patient-postal-code').val(), 
    status: $('#new-patient-status').val(), 
    occupation: $('#new-patient-occupation').val(), 
    employed: $('#new-patient-employed').val(), 
    employer: $('#new-patient-employer').val(), 
    school: $('#new-patient-school').val(), 
    schoolName: $('#new-patient-schoolName').val(), 
    primaryPhone: $('#new-patient-primary-phone').val(), 
    secondaryPhone: $('#new-patient-secondary-phone').val(), 
    email: $('#new-patient-email').val(),
    profileImageTempPath: app_profileImageTempPath,
    sessionId: user.sessionId
  });
  $.post("app/saveNewPatient", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    if (parsedData.returnCode == RETURN_CODE_DUP_EMAIL) {
      var args = {
        modalTitle:"Email Address Already In Use", 
        modalH3:"Email Address Already In Use", 
        modalH4:"Please try again with a different email address.",
        cancelButton: null,
        okButton: 'OK'
      };
      RenderUtil.render('dialog/confirm', args, function(s) { 
        $('#modals-placement').append(s);
        $('#modal-confirm').modal('show'); 
        $('#app-modal-confirmation-btn').click(function(){  $('#modal-confirm').modal('hide');});
      });
      return;
    }
    $('#modal-new-patient').modal('hide');
    $('#modal-patient-search').modal('hide'); 
    saveNewPatient_clearForm();
    displayNotification('New patient '+ parsedData.firstName + ' ' + parsedData.lastName + ' created.');
  });
}

function saveNewPatient_clearForm() {
  $('#new-patient-first-name').val('');
  $('#new-patient-middle-name').val('');
  $('#new-patient-last-name').val('');
  $('#new-patient-middle-initial').val('');
  $('#new-patient-ssn').val('');
  $('#new-patient-dob').val('');
  $('#new-patient-gender').val('');
  $('#new-patient-marital-status').val('');
  $('#new-patient-race').val('');
  $('#new-patient-ethnicity').val('');
  $('#new-patient-address1').val('');
  $('#new-patient-address2').val('');
  $('#new-patient-city').val('');
  $('#new-patient-us-state').val('');
  $('#new-patient-postal-code').val('');
  $('#new-patient-status').val('');
  $('#new-patient-occupation').val('');
  $('#new-patient-employed').val('');
  $('#new-patient-employer').val('');
  $('#new-patient-school').val('');
  $('#new-patient-school-name').val('');
  $('#new-patient-primary-phone').val('');
  $('#new-patient-secondary-phone').val('');
  $('#new-patient-email').val('');
  saveNewPatient_clearErrors();
}

function saveNewPatient_clearErrors() {
  $('#new-patient-last-name-validation').css({visibility: "hidden"});
  $('#new-patient-first-name-validation').css({visibility: "hidden"});
  $('#new-patient-ssn-validation').css({visibility: "hidden"});
  $('#new-patient-dob-validation').css({visibility: "hidden"});
  $('#new-patient-gender-validation').css({visibility: "hidden"});
  $('#new-patient-address-validation').css({visibility: "hidden"});
  $('#new-patient-city-validation').css({visibility: "hidden"});
  $('#new-patient-us-state-validation').css({visibility: "hidden"});
  $('#new-patient-postal-code-validation').css({visibility: "hidden"});
  $('#new-patient-primary-phone-validation').css({visibility: "hidden"});
  $('#new-patient-email-validation').css({visibility: "hidden"});
}


function setupPictureUpload() {
  $('#new-patient-photo-upload').click(function(){ 
    $('#new-patient-photo-upload-progress .progress-bar').css('width', '0');
  });
  uploader = new qq.FileUploader({
   element: document.getElementById('new-patient-photo-upload'),
   action: 'app/uploadProfileImage?sessionId=' + user.sessionId,
   debug: true,
   allowedExtensions: ['jpg', 'jpeg', 'png', 'gif'],
   sizeLimit: 1048576,   
   onProgress: function(id, fileName, loaded, total) {
      var progress = parseInt(loaded / total * 100, 10);
      $('#new-patient-photo-upload-progress .progress-bar').css('width', progress + '%');
   },
   onComplete: function(id, fileName, responseJSON){
     $('#new-patient-photo-upload-progress .progress-bar').css('width', '100%');
	 var response = parsedData = $.parseJSON(responseJSON);
	 app_profileImageTempPath = response.filename;
     $("#new-patient-photo").attr("src","images/"+app_profileImageTempPath);
   }
  }); 
}