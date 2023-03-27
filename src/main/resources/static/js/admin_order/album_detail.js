//목록버튼 -목록으로
$(".toList_btn").click(() => {
    $(location).attr("href", "/order/orderList");
});

//자리수제한
function handleInputLength(el, max) {
    if (el.value.length > max) {
        el.value = el.value.substr(0, max);
    }
};

//file타입 바인딩
$("#fileBtn").click(() => {
    $("#imgReg").click();
});

var filecnt = 0;//파일변경 확인용 변수
//이미지 미리보기
function readURL(input) {
    //이미지 파일인지 검사하기  
    filecnt++;
    var obj = $("#imgReg");
    var pathpoint = obj.val().lastIndexOf('.');
    var filepoint = obj.val().substring(pathpoint + 1, obj.val().length);
    var filetype = filepoint.toLowerCase();

    var check_file_type = new Array();
    check_file_type = ['jpg', 'gif', 'png', 'jpeg', 'bmp', 'webp'];

    if (check_file_type.indexOf(filetype) == -1) {//이미지파일인지 검사
        alert('이미지 파일만 선택가능합니다. 다시 등록하세요.');
        return;
    } else {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $("#imgModify").attr("src", e.target.result);
                $("input[name='album_img_path']").val($("#imgReg").val().split('/').pop().split('\\').pop());//fakepath지우기
            };
            reader.readAsDataURL(input.files[0]);
        } else {
            $("#imgModify").attr("src", "");
            $("input[name='album_img_path']").val("");
        }
    }
};

//이미지 클릭시 새창에서 띄우기
$("#imgModify").click((e) => {
    if (filecnt == 0) {
        window.open(e.currentTarget.src);
    } else {//base64이미지 새 창에서 띄우기
        let data = e.currentTarget.src;
        let w = window.open('about:blank');
        let image = new Image();
        image.src = data;
        setTimeout(function () {
            w.document.write(image.outerHTML);
        }, 0);
    };
});


//submit
$("#album_modify_btn").click(() => {

    if (confirm("수정하시겠습니까?")) {

        //유효성검사
        var numPattern = /[ \{\}\[\]\/?.,;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/gi; //숫자형식확인. 특수문자 입력 방지
        if ($("input[name='album_img_path']").val() == '' || $("#imgModify").attr('src') == '') {//이미지가 없으면
            alert("앨범이미지를 등록하세요.");
            $("#fileBtn").animate({ scrollTop: $("#fileBtn").offset().top }, 400);//해당부분으로 이동
            $("#fileBtn").focus($("#fileBtn").css("animation", "fadeIn 1s ease-in-out"));
            return;
        } else if ($('input[name="album_price"]').val() == 0 || $('input[name="album_price"]').val() == '' || numPattern.test($('input[name="album_price"]').val())) {
            alert("앨범가격이 정상적으로 등록되지 않았습니다.");
            $('input[name="album_price"]').animate({ scrollTop: $('input[name="album_price"]').offset().top }, 400);//해당부분으로 이동
            $('input[name="album_price"]').focus($('input[name="album_price"]').css("animation", "fadeIn 1s ease-in-out"));
            return;
        } else if ($('input[name="album_discount_rate"]').val() == 0 || $('input[name="album_discount_rate"]').val() == '' || numPattern.test($('input[name="album_discount_rate"]').val())) {
            alert("할인율이 정상적으로 등록되지 않았습니다.");
            $('input[name="album_discount_rate"]').animate({ scrollTop: $('input[name="album_discount_rate"]').offset().top }, 400);//해당부분으로 이동
            $('input[name="album_discount_rate"]').focus($('input[name="album_discount_rate"]').css("animation", "fadeIn 1s ease-in-out"));
            return;
        }


        if (filecnt == 0) {//파일변경이 없었다면
            $("#modifyAlbumForm").attr("action", "/order/albumModify").submit();
        } else {
            //aws업로드
            var formData = new FormData();
            var file = $("#imgReg");

            formData.append("file", file[0].files[0]);
            $.ajax({
                url: "/s3/resource",
                method: "post",
                data: formData,
                contentType: false, //보내는 데이터 타입 multipart/form-data로
                processData: false, //폼데이터가 name=값&형식으로 변경되는 것 막기
                async: false,
                success: (result) => {
                    $('input[name="album_img_path"]').val(result.path);
                    $("#modifyAlbumForm").attr("action", "/order/albumModify").submit();
                },
                error: (err) => {
                    alert('이미지 업로드에 실패하였습니다.')
                    return;
                }
            });
        }

    }//end confirm
})