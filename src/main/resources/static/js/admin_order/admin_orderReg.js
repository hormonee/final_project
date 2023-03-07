/*취소버튼*/
$("#redirectList").click(() => {
    if (confirm("변경사항이 저장되지 않습니다. 목록으로 돌아가시겠습니까?")) {
        location.href = "orderList";
    }
})


var entertainer=""; //카테고리에서 꺼낼 연예인

/*카테고리*/
// 화면 로딩시 대분류 카테고리 생성
$(document).ready(() => {
    $.ajax({
        url: "../getCategory",
        type: "get",
        success: function (result) {
            var str = '';
            str += '<ul class="categoryList" style="position:relative;" onclick="getAllCategory(event);">';
            result.forEach((item) => { str += '<li><a href="#" data-set=' + JSON.stringify(item) + '>' + item.category_detail_nm + '</a></li>'; });
            str += '</ul>';

            $(".categoryListWrap").append(str);
        },
        error: function (err) {
            alert("카테고리 조회 실패! 담당자에게 문의하세요.")
        }
    })
});


//카테고리 생성
function getAllCategory(e) {
    e.preventDefault(); //고유 이벤트 중지
    if (e.target.tagName != 'A') return; //태그 검증하기
    var dcate = $(e.target).data("set"); //jquery 데이터셋

    //카테고리 세분화
    if (dcate.category_lv == 1 || dcate.category_lv == 2) { //대분류, 중분류일때만
        $(e.currentTarget).category_remove(); //이전 카테고리 삭제
        $.ajax({
            url: "../getCategoryChild/" + dcate.category_group_id + "/" + dcate.category_lv + "/" + dcate.category_detail_lv,
            type: "get",
            success: function (result) { category_create(result) },
            error: function (err) { alert("카테고리 조회 실패! 담당자에게 문의하세요.") }
        })
    }
    $(e.target).category_set();
};


//카테고리세팅
$.fn.category_set = function () {
    var category_id = this.data("set").category_id;
    var category_group_id = this.data("set").category_group_id;
    $("input[name='admin_order_prod_category']").val(category_group_id + category_id);

    if(this.data("set").category_lv==2){
        entertainer=this.data("set").category_detail_nm;
    }
};

//이전 카테고리 삭제
$.fn.category_remove = function () {
    while (this.next().length != 0) {
        $(this).next().remove();
    }
};


//다음카테고리 생성
function category_create(result) {
    var category = "";
    category += '<ul class="categoryList" style="position:relative;" onclick="getAllCategory(event);">';
    result.forEach((item) => { category += '<li><a href="#" data-set=' + JSON.stringify(item) + '>' + item.category_detail_nm + '</a></li>' });
    category += '</ul>';
    $(".categoryListWrap").append(category);
};


//상세등록창 띄우기
$(".detail_btn").click((e) => {
    var categorySelected = $("input[name='admin_order_prod_category']").val();
    $("#adCategory").val(categorySelected);//admin용 카테고리 저장
    if (categorySelected == '' || categorySelected == 'A1' || categorySelected == 'A2' || categorySelected == 'A3' || categorySelected == 'A4' || categorySelected == 'B17' || categorySelected == 'B18' || categorySelected == 'B19') {
        alert('상세카테고리를 선택하세요');
    } else {
        $(e.currentTarget).detail_remove();
        if (categorySelected == 'A5' || categorySelected == 'A9' || categorySelected == 'A13') { //앨범일 때
            var astr = '';
            astr += '<tbody class="tbx">';
            astr += `<tr>`;
            astr += `<th>카테고리</th>`;
            astr += `<td><input type="text" required="required" readonly name="album_category" value="`+categorySelected+`"/></td>`;
            astr += `</tr>`;
            astr += '<tr>';
            astr += '<th>가수</th>';
            astr += '<td><input type="text" required="required" readonly name="album_artist" value="'+entertainer+'"/></td>';
            astr += '</tr>';
            astr += `<tr>`;
            astr += `<th>앨범명</th>`;
            astr += `<td><input type="text" required="required" name="album_title"/></td>`;
            astr += `</tr>`;
            astr += `<tr>`;
            astr += `<th>발매일</th>`;
            astr += `<td><input type="text" id="datepick2" pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" placeholder="날짜는 yyyy-MM-dd로 입력하세요" required="required" name-"album_release_date"/></td>`;
            astr += `</tr>`;
            astr += `<tr>`;
            astr += `<th>가격</th>`;
            astr += `<td><input type="text" id="alb_price" required="required" name="album_price"/></td>`;
            astr += `</tr>`;
            astr += `<tr>`;
            astr += `<th>할인률</th>`;
            astr += `<td><input type="text" required="required" name="album_discount_rate"/></td>`;
            astr += `</tr>`;
            astr += `<tr>`;
            astr += `<th>재고</th>`;
            astr += `<td><input type="text" required="required" name="album_stock"/></td>`;
            astr += `</tr>`;
            astr += `<tr>`;
            astr += `<th>이미지업데이트</th>`;
            astr += `<td><input type="text" required="required" name="album_img_path"/></td>`;
            astr += `</tr>`;
            astr += `<tr>`;
            astr +=`<th>회사</th>`;
            astr +=`<td>`;
            astr +=`<input type="text" name="admin_order_company" required="required"/>`;
            astr +=`</td>`;
            astr +=`</tr>`;
            astr += `</tbody>`;
            $('.regTable').append(astr);
        } else {//앨범이 아닌 상품일 때
            var pstr = '';
            pstr += '<tbody class="tbx">';
            pstr += `<tr>`;
            pstr += `<th>카테고리</th>`;
            pstr += `<td><input type="text" required="required" readonly name="prod_category" value="`+categorySelected+`"/></td>`;
            pstr += `</tr>`;
            pstr += `<tr>`;
            pstr += `<th>연예인</th>`;
            pstr += `<td><input type="text" required="required" readonly name="prod_artist" value="`+entertainer+`"/></td>`;
            pstr += `</tr>`;
            pstr += '<tr>';
            pstr += '<th>상품타입</th>';
            pstr += '<td><input type="text" required="required" name="prod_type"/></td>';
            pstr += '</tr>';
            pstr += `<tr>`;
            pstr += `<th>상품명</th>`;
            pstr += `<td><input type="text" required="required" name="prod_name"/></td>`;
            pstr += `</tr>`;
            pstr += `<tr>`;
            pstr += `<th>사이즈</th>`;
            pstr += `<td><input type="text" required="required" name="prod_sizetype"/></td>`;
            pstr += `</tr>`;
            pstr += `<tr>`;
            pstr += `<th>등록일</th>`;
            pstr += `<td><input type="text" id="datepick3" pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" placeholder="날짜는 yyyy-MM-dd로 입력하세요" required="required" name="prod_regdate"/></td>`;
            pstr += `</tr>`;
            pstr += `<tr>`;
            pstr += `<th>가격</th>`;
            pstr += `<td><input type="text" id="pd_price" required="required" name="prod_price"/></td>`;
            pstr += `</tr>`;
            pstr += `<tr>`;
            pstr += `<th>할인률</th>`;
            pstr += `<td><input type="text" required="required" name="prod_discount_rate"/></td>`;
            pstr += `</tr>`;
            pstr += `<tr>`;
            pstr += `<th>재고</th>`;
            pstr += `<td><input type="text" required="required" name="prod_stock"/></td>`;
            pstr += `</tr>`;
            pstr += `<tr>`;
            pstr += `<th>이미지경로</th>`;
            pstr += `<td><input type="text" required="required" name="prod_img_path"/></td>`;
            pstr += `</tr>`;
            pstr += `<tr>`;
            pstr += `<th>상세설명이미지</th>`;
            pstr += `<td><input type="text" required="required" name="prod_info_img_path"/></td>`;
            pstr += `</tr>`;
            pstr += `<tr>`;
            pstr +=`<th>회사</th>`;
            pstr +=`<td>`;
            pstr +=`<input type="text" name="admin_order_company" required="required"/>`;
            pstr +=`</td>`;
            pstr +=`</tr>`;
            pstr += '</tbody>';
            $('.regTable').append(pstr);
        }
    }
});


//이전 상세등록창 지우기
$.fn.detail_remove = function () {
    $(".tbx").remove();
};


/*달력*/
$(function () {
    $("#datepick1").datepicker({ showButtonPanel: true });
    $("#datepick1").datepicker("option", "dateFormat", "yy-mm-dd");
});

$(".regTable").on("focus","#datepick2",
    function(){
        $("#datepick2").datepicker({ showButtonPanel: true });
        $("#datepick2").datepicker("option", "dateFormat", "yy-mm-dd");
    }
);

$(".regTable").on("focus","#datepick3",
    function(){
        $("#datepick3").datepicker({ showButtonPanel: true });
        $("#datepick3").datepicker("option", "dateFormat", "yy-mm-dd");
    }
);


/*발주버튼*/
$("#submitOrder").click((e) => {
    // console.log(e.currentTarget); submit input태그
    e.preventDefault(); //고유이벤트제거

    //유효성검사
    var categorySelected = $("input[name='admin_order_prod_category']").val();  //카테고리 선택값
    if($("input[name='admin_order_regdate']").val()==''){
        alert('발주일자를 입력하세요!');
        $("input[name='admin_order_regdate']").focus();
    }else if(categorySelected ==''){
        alert('카테고리를 선택하세요!');
        $(".categoryList").focus($(".categoryList").css("animation","fadeIn 1s ease-in-out"));
    }else if(categorySelected == 'A1' || categorySelected == 'A2' || categorySelected == 'A3' || categorySelected == 'A4' || categorySelected == 'B17' || categorySelected == 'B18' || categorySelected == 'B19'){
        alert('상세카테고리를 선택하세요!');
        $(".categoryList").focus($(".categoryList").css("animation","fadeIn 1s ease-in-out"));
    }else if($(".tbx").length==0){
        alert("상세발주조회를 선택하세요!");
    }
 
    
    if(categorySelected == 'A5' || categorySelected == 'A9' || categorySelected == 'A13'){ //앨범일 때
        if($("input[name='album_title']").val()==''){
            alert("앨범명을 입력하세요!");
            $("input[name='album_title']").focus();
        }else if($("input[name='album_release_date']").val()==''){
            alert("발매일을 입력하세요!");
            $("input[name='album_release_date']").focus();
        }else if($("input[name='album_price']").val()==''){
            alert("가격을 입력하세요!");
            $("input[name='album_price']").focus();
        }else if($("input[name='album_discount_rate']").val()==''){
            alert("할인률을 입력하세요!");
            $("input[name='album_discount_rate']").focus();
        }else if($("input[name='album_stock']").val()==''){
            alert("재고를 입력하세요!");
            $("input[name='album_stock']").focus();
        }else if($("input[name='album_img_path']").val()==''){
            alert("이미지를 업데이트하세요!");
            $("input[name='album_img_path']").focus();
        }else if($("input[name='admin_order_company']").val()==''){
            alert("회사를 입력하세요!");
            $("input[name='admin_order_company']").focus();
        }else{
            $("#adCnt").val($("input[name='album_stock']").val()); // admin_order_prod_cnt 넣기
            $("#adPrice").val($("input[name='album_price']").val()*$("input[name='album_stock']").val()); //가격 넣기
        }
    }else{//상품일 때
        if($("input[name='prod_type']").val()==''){
            alert("상품타입을 입력하세요!");
            $("input[name='prod_type']").focus();
        }else if($("input[name='prod_name']").val()==''){
            alert("상품명을 입력하세요!");
            $("input[name='prod_name']").focus();
        }else if($("input[name='prod_sizetype']").val()==''){
            alert("사이즈타입을 입력하세요!");
            $("input[name='prod_sizetype']").focus();
        }else if($("input[name='prod_regdate']").val()==''){
            alert("등록일을 입력하세요!");
            $("input[name='prod_regdate']").focus();
        }else if($("input[name='prod_price']").val()==''){
            alert("가격을 입력하세요!");
            $("input[name='prod_price']").focus();
        }else if($("input[name='prod_discount_rate']").val()==''){
            alert("할인률을 입력하세요!");
            $("input[name=''prod_discount_rate]").focus();
        }else if($("input[name='prod_stock']").val()==''){
            alert("재고를 입력하세요!");
            $("input[name='prod_stock']").focus();
        }else if($("input[name='prod_img_path']").val()==''){
            alert("이미지경로를 입력하세요!");
            $("input[name='prod_img_path']").focus();
        }else if($("input[name='prod_info_img_path']").val()==''){
            alert("상세설명이미지를 입력하세요");
            $("input[name='prod_info_img_path']").focus();
        }else if($("input[name='admin_order_company']").val()==''){
            alert("회사를 입력하세요!");
            $("input[name='admin_order_company']").focus();
        }else{
            $("#adCnt").val($("input[name='album_stock']").val()); // admin_order_prod_cnt 넣기
            $("#.adSizetype").val($("input[name='prod_sizetype']").val());//사이즈넣기
            $("#adPrice").val($("input[name='prod_price']").val()*$("input[name='prod_stock']").val())//가격 넣기
        }   
    }
    



});