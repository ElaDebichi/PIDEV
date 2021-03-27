$("#search").click(function() {
    var entitySelector = $("#tb").html('');
    var params = {
        url: ""
    };

    if($("#searchadv").val()!=""){
        params.url='http://127.0.0.1:8000/post/'+$("#searchadv").val()+'/search';
    }
    else{
        params.url='http://127.0.0.1:8000/post/all/searchall';

    }

    $.ajax({
        type: "GET",
        url:  params.url,
        //dataType: "jsonp",
        dataType: "text",
        success: function(msg){
            //we need to check if the value is the same
            var result = JSON.parse(msg);
            $.each(result, function(key, arr) {
                $.each(arr, function(id, value) {
                    if (key === 'disponibilite') {
                        if (id !== 'error') {
                            entitySelector.append('<tr><td>'+value['id']+'</td><td>'+
                                value['content']+'</td><td>'+value['date']+'</td><td>'+value['comments']+
                                '</td>>'+'<td>'+
                                '<a class=\"btn btn-outline-primary mr-1 mb-1\" href=\"{{ path(\'post_edit\', {\'id\':'+ value['id']+'}) }}\"> <i class=\"feather icon-edit\"></i></a> &nbsp; &nbsp;'
                            +'<a class=\"btn btn-outline-primary mr-1 mb-1\" href=\"{{ path(\'post_show\', {\'id\': ' +  value['id']+'}) }}\"><i class=\"feather icon-eye\"></i></a> '
                            +'</td></tr>'



                            );
                        } else {
                            Swal.fire({
                                type: 'error',
                                title: 'Oops...',
                                text: 'Content Does not exist',
                                footer: '<a href>Why do I have this issue?</a>',
                                timer: 1500
                            });
                        }
                    }
                });
            });

        }
    });

});





