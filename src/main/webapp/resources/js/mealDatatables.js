/**
 * Created by jerom on 11.07.2017.
 */
var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging":false,
        "info":true,
        "columns":[
            {"data":"dateTime"},
            {"data":"description"},
            {"data":"calories"},
            {"defaultContent":"Delete", "orderable":false},
            {"defaultContent":"Edit", "oredrable":false}
        ],
        "order": [[0,"desc"]]
    });
    makeEditable()
});
function updateTable() {
    $.ajax({
        type: "POST",
        url:ajaxUrl + "filter",
        data:$("#filter").serialize(),
        success:function (data) {
            datatableApi.clear().rows.add(data).draw();
        }
    })
}

