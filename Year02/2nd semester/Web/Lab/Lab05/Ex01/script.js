$("#first").on("dblclick", function() {
    const elem = $(this).find("option:selected").remove().clone();
    $("#second").append(elem);
});

$("#second").on("dblclick", function() {
    const elem = $(this).find("option:selected").remove().clone();
    $("#first").append(elem);
});
