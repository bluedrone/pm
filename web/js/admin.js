/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */


$('#admin').click(function() { 
  app_viewStack('user-admin-screen', DO_SCROLL);
  getUsersList();
});


$('#user-admin-new-user').click(function() { 
  RenderUtil.render('user_form', {formMode:'add'}, function(s) { 
    $('#modals-placement').html(s);
    $('#modal-admin-user-form').modal('show'); 
    $('#modal-admin-user-form .form-control-unsaved').css({display: "block"});
    $('#modal-admin-user-form .form-control-saved').css({display: "none"});
    $('#admin-user-form-title').html("Add User");
    userForm_clearForm();
    $('#admin-user-form-save').click(function() { saveNewUser() });
  });
});


function getUsersList() {
  var jsonData = JSON.stringify({ sessionId: user.sessionId });
  $.post("admin/getUsers", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    users = parsedData.users;
    RenderUtil.render('component/user_admin_data_table', 
     {items:users, 
      title:'Users', 
      tableName:'user-admin-users-list', 
      clickable:true
      }, function(s) {
      $('#user-admin-users-list').html(s);
      $('#user-admin-users-list-title').html("Users");
      $('.clickable-table-row').dblclick( function(e){ handleDoubleClickedRow(e); });
      $('.user-admin-user-details').click( function(e){ handleUserDetails(e); });
      $('.user-admin-change-user-status').click( function(e){ handleChangeUserStatus(e); });
      $('.user-admin-delete-user').click( function(e){ handleDeleteUser(e); });
    });
  });
}

function findUserFromList(id) {
  for (i=0;i<users.length;i++) {
    if (users[i].id == id) {
      return users[i];
    }
  }
}


function handleUserDetails(e) {
  var id = e.currentTarget.name;
  var userToChange = findUserFromList(id);
  RenderUtil.render('user_form', {formMode:'edit'}, function(s) { 
    $('#modals-placement').html(s);
    $('#modal-admin-user-form').modal('show'); 
    $('#modal-admin-user-form .form-control-unsaved').css({display: "none"});
    $('#modal-admin-user-form .form-control-saved').css({display: "block"});
    $('#admin-user-form-title').html("View/Edit User");
    
    $('#user-form-first-name-saved').html(userToChange.firstName);
    $('#user-form-middle-name-saved').html(userToChange.middleName);
    $('#user-form-last-name-saved').html(userToChange.lastName);
    $('#user-form-username-saved').html(userToChange.username);
    $('#user-form-group-name-saved').html(userToChange.groupName);
    $('#user-form-practice-name-saved').html(userToChange.practiceName);
    $('#user-form-role-saved').val(userToChange.role.id);
    $('#user-form-credential-saved').val(userToChange.credential.id);
    $('#user-form-primary-phone-saved').html(userToChange.primaryPhone);
    $('#user-form-secondary-phone-saved').html(userToChange.secondaryPhone);
    $('#user-form-email-saved').html(userToChange.email);
    $('#user-form-pager-saved').html(userToChange.pager);
    $('#user-form-password-saved').html("******");
      
    $('#user-form-first-name-saved').blur(function() { updateSavedUser("firstName", $(this).html(), id); });
    $('#user-form-middle-name-saved').blur(function() { updateSavedUser("middleName", $(this).html(), id); });
    $('#user-form-last-name-saved').blur(function() { updateSavedUser("lastName", $(this).html(), id); });
    $('#user-form-username-saved').blur(function() { updateSavedUser("username", $(this).html(), id); });
    $('#user-form-group-name-saved').blur(function() { updateSavedUser("groupName", $(this).html(), id); });
    $('#user-form-practice-name-saved').blur(function() { updateSavedUser("practiceName", $(this).html(), id); });
    $('#user-form-role-saved').blur(function() { updateSavedUser("roleId", $(this).html(), id); });
    $('#user-form-primary-phone-saved').blur(function() { updateSavedUser("primaryPhone", $(this).html(), id); });
    $('#user-form-secondary-phone-saved').blur(function() { updateSavedUser("secondaryPhone", $(this).html(), id); });
    $('#user-form-credential-saved').blur(function() { updateSavedUser("credentialId", $(this).html(), id); });
    $('#user-form-pager-saved').blur(function() { updateSavedUser("pager", $(this).html(), id); });
    $('#user-form-email-saved').blur(function() { updateSavedUser("email", $(this).html(), id); });
    $('#user-form-password-saved').blur(function() { updateSavedUser("password", $(this).html(), id); });
  });
}
  

function handleDeleteUser(e) {
  var id = e.currentTarget.name;
  var userToChange = findUserFromList(id);
  var userFullName = util_buildFullName(userToChange.firstName, userToChange.middleName, userToChange.lastName);
  var args = {
    modalTitle:"Delete User", 
    modalH3:"Delete User "+userFullName+"?",
    modalH4:"Once deleted, the user will remain in the system but not be visible to the application.",
    cancelButton: 'Cancel',
    okButton: 'Confirm'
  };
  RenderUtil.render('dialog/confirm', args, function(s) { 
    $('#modals-placement').html(s);
    $('#modal-confirm').modal('show'); 
    $('#app-modal-confirmation-btn').click(function(){  
      var jsonData = JSON.stringify({ sessionId: user.sessionId, userId: id });
      $.post("admin/purgeUser", {data:jsonData}, function(data) {
        $('#modal-confirm').modal('hide'); 
        displayNotification('User '+userFullName+' deleted.');
        getUsersList();
      }); 
    });
  });
}


function handleChangeUserStatus(e) {
  var id = e.currentTarget.name;
  var userToChange = findUserFromList(id);
  var userFullName = util_buildFullName(userToChange.firstName, userToChange.middleName, userToChange.lastName);
  var activationMode = userToChange.active ? "deactivate"  : "activate";
  
  RenderUtil.render('dialog/change_user_status', {activationMode:activationMode, userFullName: userFullName}, function(s) { 
    $('#modals-placement').html(s);
    $('#modal-admin-change-user-status').modal('show'); 
    $('#app-modal-confirmation-btn').click(function(){  
      var method = activationMode == "deactivate" ? "deactivateUser"  : "activateUser";
      var jsonData = JSON.stringify({ sessionId: user.sessionId, userId: id });
      $.post("admin/"+method, {data:jsonData}, function(data) {
        $('#modal-admin-change-user-status').modal('hide'); 
        displayNotification('User '+userFullName+' '+activationMode+'d.');
        getUsersList();
      }); 
    });
  });
}
    


function saveNewUser() {
  var phoneRegexObj = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
  var emailRegexObj = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i;

  var isValid = true;
  userForm_clearErrors();

  if($("#user-form-first-name").val().length < 1) {util_showError('#user-form-first-name-validation-saved'); isValid = false; }
  if($("#user-form-last-name").val().length < 1) {util_showError('#user-form-last-name-validation-saved'); isValid = false; }
  if($("#user-form-username").val().length < 1) {util_showError('#user-form-username-validation-saved'); isValid = false; }
  if($("#user-form-role").val().length < 1) {util_showError('#user-form-role-validation-saved'); isValid = false; }
  if($("#user-form-primary-phone").val().length < 1) {util_showError('#user-form-primary-phone-validation-saved'); isValid = false; }

  if ($.trim($("#user-form-password").val()) != PASSWORD_PLACEHOLDER) {  
    if($.trim($("#user-form-password").val()).length < 1) { util_showError('#user-form-password-validation-saved'); isValid = false; }
    if ($.trim($("#user-form-password").val()).length > 0) {
      if($.trim($("#user-form-password").val()).length < 6 || $.trim($("#user-form-password-confirm").val()).length < 6) { 
        util_showError('#user-form-password-validation', 'must be at least 6 chararcters long-saved'); 
        isValid = false; 
      }
      if(util_checkPassword($.trim($("#user-form-password").val())) == false) { 
        util_showError('#user-form-password-validation', 'must contain a lowercase, uppercase, digit, and special character-saved'); 
        isValid = false; 
      }
      if($.trim($("#user-form-password").val()) != $.trim($("#user-form-password-confirm").val())) { 
        util_showError('#user-form-password-validation', 'passwords must match-saved'); 
        isValid = false; 
      }
    }
  }
  
  if($.trim($("#user-form-email").val()).length < 1) { util_showError('#user-form-email-validation-saved'); isValid = false; }
  var emailValid = util_checkRegexp($.trim($("#user-form-email").val()), emailRegexObj);
  if(emailValid == false) { util_showError('#user-form-email-validation', 'invalid email address-saved'); isValid = false; }
  if($.trim($("#user-form-email").val()) != $.trim($("#user-form-email-confirm").val())) { 
    util_showError('#user-form-email-validation', 'email addresses must match-saved'); 
    isValid = false; 
  }

  if (isValid == false) {
   return;
  }
  
  var jsonData = JSON.stringify({ 
    firstName: $('#user-form-first-name-saved').val(), 
    middleName: $('#user-form-middle-name-saved').val(), 
    lastName: $('#user-form-last-name-saved').val(), 
    username: $('#user-form-username-saved').val(), 
    groupName: $('#user-form-group-name-saved').val(), 
    practiceName: $('#user-form-practice-name-saved').val(), 
    roleId: $('#user-form-role-saved').val(), 
    credentialId: $('#user-form-credential-saved').val(), 
    primaryPhone: $('#user-form-primary-phone-saved').val(), 
    secondaryPhone: $('#user-form-secondary-phone-saved').val(), 
    email: $('#user-form-email-saved').val(),
    password: $('#user-form-password-saved').val(),
    pager: $('#user-form-pager-saved').val(),
    sessionId: user.sessionId
  });
  $.post("admin/saveNewUser", {data:jsonData}, function(data) {
    var parsedData = $.parseJSON(data);
    if (parsedData.returnCode == RETURN_CODE_DUP_USERNAME) {
      var args = {
        modalTitle:"Username Already In Use", 
        modalH3:"Username Already In Use", 
        modalH4:"Please try again with a different username.",
        cancelButton: null,
        okButton: 'OK'
      };
      RenderUtil.render('dialog/confirm', args, function(s) { 
        $('#modals-placement-saved').append(s);
        $('#modal-confirm-saved').modal('show'); 
        $('#app-modal-confirmation-btn-saved').click(function(){  $('#modal-confirm').modal('hide');});
      });
      return;
    }
    $('#modal-admin-user-form-saved').modal('hide');
    displayNotification('New user '+ parsedData.firstName + ' ' + parsedData.lastName + ' created.');
    getUsersList();
  });
}


function updateSavedUser(property, value, id) {
  var jsonData = JSON.stringify({ 
    sessionId: user.sessionId, 
    updateProperty:property,
    updatePropertyValue:value,
    userId: id
  });
  $.post("admin/updateUser", {data:jsonData}, function(data) {
    if (parsedData.returnCode == RETURN_CODE_DUP_USERNAME) {
      var args = {
        modalTitle:"Username Already In Use", 
        modalH3:"Username Already In Use", 
        modalH4:"Please try again with a different username.",
        cancelButton: null,
        okButton: 'OK'
      };
      RenderUtil.render('dialog/confirm', args, function(s) { 
        $('#modals-placement-saved').append(s);
        $('#modal-confirm-saved').modal('show'); 
        $('#app-modal-confirmation-btn-saved').click(function(){  $('#modal-confirm').modal('hide');});
      });
      return;
    }
    else {
      user[property] = value;
    }
  }); 
}

function userForm_clearForm() {
  $('#user-form-first-name-saved').val('');
  $('#user-form-middle-name-saved').val('');
  $('#user-form-last-name-saved').val('');
  $('#user-form-username-saved').val('');
  $('#user-form-group-name-saved').val('');
  $('#user-form-practice-name-saved').val('');
  $('#user-form-role-saved').val('');
  $('#user-form-primary-phone-saved').val('');
  $('#user-form-secondary-phone-saved').val('');
  $('#user-form-credential-saved').val('');
  $('#user-form-pager-saved').val('');
  $('#user-form-email-saved').val('');
  $('#user-form-email-confirm-saved').val('');
  $('#user-form-password-saved').val('');
  $('#user-form-password-confirm-saved').val('');
  userForm_clearErrors();
}

function userForm_clearErrors() {
  $('#user-form-first-name-validation-saved').css({visibility: "hidden"});
  $('#user-form-last-name-validation-saved').css({visibility: "hidden"});
  $('#user-form-username-validation-saved').css({visibility: "hidden"});
  $('#user-form-role-validation-saved').css({visibility: "hidden"});
  $('#user-form-credential-validation-saved').css({visibility: "hidden"});
  $('#user-form-primary-phone-validation-saved').css({visibility: "hidden"});
  $('#user-form-secondary-phone-validation-saved').css({visibility: "hidden"});
  $('#user-form-pager-validation-saved').css({visibility: "hidden"});
  $('#user-form-email-validation-saved').css({visibility: "hidden"});
  $('#user-form-password-validation-saved').css({visibility: "hidden"});
}