jQuery(document).ready(function(){
  // IE 8-10
  // If in compatibility mode
  if (document.documentMode == 7){
    // Show error div
    $('.ie-cm-alert').show();
  }
    
  $('.close-alert').click(function() {
    $('.ie-cm-alert').hide();
  });
});