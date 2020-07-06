$(document).ready(function() {
  $('#nav-output-tabs a').on('click', function(event) {
    event.preventDefault();
    $(this).tab('show');
    const target = $(event.target).attr('href');
    $("#simulatorOutput").attr("action", `/simulator/put?type=${target}`);
  })
})