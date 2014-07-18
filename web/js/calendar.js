/*
 * WDean Medical is distributed under the
 * GNU Lesser General Public License (GNU LGPL).
 * For details see: http://www.wdeanmedical.com
 * copyright 2013-2014 WDean Medical
 */

function app_loadCalendar() {
  request = $.ajax({
    type: "GET",
    contentType: "application/json",
    data:"{}",
    url: "app/getAppointments?sessionId="+user.sessionId,
    dataType: "json",
    success: function(data) {
      $('#app-calendar').fullCalendar('destroy');
      var calendar =  $('#app-calendar').fullCalendar({
        defaultView: app_currentCalendarView,
        header: {
          left: 'prev,next today',
          center: 'title',
          right: 'month,agendaWeek,agendaDay'
        },
        windowResize: true,
        selectable: true,
        selectHelper: true,
        select: function(start, end) {
          newApptForm(start, end)
        },
        eventDragStop: function(event, jsEvent, ui, view) {
          moveAppt(event, jsEvent, ui, view);
        },
        eventResizeStop: function(event, jsEvent, ui, view) {
          moveAppt(event, jsEvent, ui, view);
        },
        lazyFetching: true,
        editable: true,
        eventRender: function(event, element) {
          startDate = event.start.format('h:mm');
          endDate = event.end.format('h:mm');
          eventTitle = event.title; 
          element.find('.fc-event-time').html(startDate + ' - ' + endDate);
          //element.find('.fc-event-time').html(startDate);
          element.find('.fc-event-title').html(eventTitle);
        },
        eventMouseover: function(calEvent, jsEvent) {
          var tooltip = 
          '<div class="tooltipevetn" style="min-width:110px;max-width:400px;min-height:20px;background:#C0C0C0;color: black;'+
          'font-size: small; font-weight: bold;position:absolute;z-index:10001;">' + calEvent.desc + '</div>';
          $("body").append(tooltip);
          $(this).mouseover(function(e) {
            $(this).css('z-index', 10000);
            $('.tooltipevetn').fadeIn('500');
            $('.tooltipevetn').fadeTo('10', 1.9);
          }).mousemove(function(e) {
            $('.tooltipevetn').css('top', e.pageY + 10);
            $('.tooltipevetn').css('left', e.pageX + 20);
          });
        },
        eventMouseout: function(calEvent, jsEvent) {
          $(this).css('z-index', 8);
          $('.tooltipevetn').remove();
        },
        events: $.map(data, function(item, i) {
          var event = new Object();
          if (item != null){
            event.id = item.id;
            event.className = item.className;
            event.start = item.start;
            event.end = item.end;
            event.desc = item.desc;
            event.title = item.title;
            event.allDay = item.allDay;
            return event;
          }
        }),
        allDayDefault: false
      });
      $('.fc-button-agendaDay').click(function() { app_currentCalendarView = 'agendaDay'});
      $('.fc-button-agendaWeek').click(function() { app_currentCalendarView = 'agendaWeek'});
      $('.fc-button-month').click(function() { app_currentCalendarView = 'month'});
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      debug('There was an error while fetching data for calendar.');
    }
  });
}





function moveAppt(event, jsEvent, ui, view) {
  alert('appt moved');
}



function resizeAppt(event, jsEvent, ui, view) {
  alert('appt resized');
}



function newApptForm(start, end) {
  var offset = new Date().getTimezoneOffset();
  start.add('m', offset);
  end.add('m', offset);
  RenderUtil.render('dialog/event', {}, function(s) {
    $('#modals-placement').html(s);
    $('#modal-event').modal('show'); 
    $('.form_time').timepicker({
      template: false,
      showInputs: false,
      minuteStep: 5
    });
    $('#app-appt-start').val(dateFormat(start, 'h:MM TT'));
    $('#app-appt-end').val(dateFormat(end, 'h:MM TT'));
    getClinicians();
    $('#app-appt-clinician').on('change',function(){
      selectedClinician = $('#app-appt-clinician').val();
      getClinicianPatients();
    });
    $('#app-appt-submit').one("click", function (e) { handleNewAppt(e, start, end, offset); });
  });
}



function editApptForm(start, end) {
  var offset = new Date().getTimezoneOffset();
  start.add('m', offset);
  end.add('m', offset);
  RenderUtil.render('dialog/event', {}, function(s) {
    $('#modals-placement').html(s);
    $('#modal-event').modal('show'); 
    $('.form_time').timepicker({
      template: false,
      showInputs: false,
      minuteStep: 5
    });
    $('#app-appt-start').val(dateFormat(start, 'h:MM TT'));
    $('#app-appt-end').val(dateFormat(end, 'h:MM TT'));
    getClinicians();
    $('#app-appt-clinician').on('change',function(){
      selectedClinician = $('#app-appt-clinician').val();
      getClinicianPatients();
    });
    $('#app-appt-submit').one("click", function (e) { handleNewAppt(e, start, end, offset); });
  });
}



function handleNewAppt(e, start, end) {
  var isValid = true;
  handleNewAppt_clearErrors();
  
  if($("#app-appt-start").val().length < 1) { 
    showError('#app-appt-start-validation');
    isValid = false;
  }
  if($("#app-appt-end").val().length < 1) { 
    showError('#app-appt-end-validation');
    isValid = false;
  }
  if($("#app-appt-clinician").val().length < 1) { 
    showError('#app-appt-clinician-validation');
    isValid = false;
  }
  if($("#app-appt-patient").val().length < 1) { 
    showError('#app-appt-patient-validation');
    isValid = false;
  }
  if($("#app-appt-desc").val().length < 1) { 
    showError('#app-appt-desc-validation');
    isValid = false;
  }
  
  if (isValid == false) {
    return;
  }
  
  var startTimeString = dateFormat(start, 'mm/dd/yyyy') + " " + $('#app-appt-start').val();
  var startTime = moment (startTimeString, "mm/dd/yyyy HH:mm A");
  var endTimeString = dateFormat(end, 'mm/dd/yyyy') + " " + $('#app-appt-end').val();
  var endTime = moment (endTimeString, "mm/dd/yyyy HH:mm A");
  
  var jsonData = JSON.stringify({ 
    sessionId: user.sessionId,
    startTime: startTimeString,
    endTime: endTimeString,
    clinician: $('#app-appt-clinician').val(), 
    patient: $('#app-appt-patient').val(),
    desc: $('#app-appt-desc').val() 
  });
  
  $.post("app/newAppt", {data:jsonData}, function(data) {
    handleNewAppt_clearForm();
    var parsedData = $.parseJSON(data);
    displayNotification('New appointment created.');
    var parsedData = $.parseJSON(data);
    $('#modal-event').modal('hide');
    //$('#app-calendar').fullCalendar('removeEvents');
    //$('#app-calendar').fullCalendar('removeEventSource', data);
    //$('#app-calendar').fullCalendar('addEventSource', data);
    app_loadCalendar();
  });
 
}

function handleNewAppt_clearForm() {
  $('#app-appt-start').val('');
  $('#app-appt-end').val('');
  $('#app-appt-clinician').val('');
  $('#app-appt-patient').val('');
  $('#app-appt-desc').val('');
  handleNewAppt_clearErrors();
}

function handleNewAppt_clearErrors() {
  $('#app-appt-start-validation').css({visibility: "hidden"});
  $('#app-appt-end-validation').css({visibility: "hidden"});
  $('#app-appt-clinician-validation').css({visibility: "hidden"});
  $('#app-appt-patient-validation').css({visibility: "hidden"});
  $('#app-appt-desc-validation').css({visibility: "hidden"});
}