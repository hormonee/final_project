
function drawModal(admin_order_album_no, admin_order_prod_no, admin_order_no) {
    //모달창 켜졌을 때 외부 스크롤 막기
    $("body").css("overflowY", "hidden");
    //이전 내용 지우기
    $(".detailTable").html('');

    var str = '';
    //admin관련 영역 뿌리기
    $.ajax({
        url: "/getAdmin/" + admin_order_no,
        type: "get",
        async: false,
        success: (result) => {
            str += `<tbody id="admin_order_tbx" class="tbx">`;
            str += `<tr>`;
            str += `<th>`;
            str += `<div class="th">카테고리 수정불가</div>`;
            str += `</th>`;
            str += `<td>${result.admin_order_category}</td>`;
            str += `</tr>`;
            str += `<tr>`;
            str += `<th>`;
            str += `<div class="th">발주가격</div>`;
            str += `</th>`;
            str += `<td>${result.admin_order_price}원</td>`;
            str += `</tr>`;
            str += `<tr>`;
            str += `<th>`;
            str += `<div class="th">발주량</div>`;
            str += `</th>`;
            str += `<td>${result.admin_order_prod_cnt}개</td>`;
            str += `</tr>`;
            str += `<tr>`;
            str += `<th>`;
            str += `<div class="th">발주회사</div>`;
            str += `</th>`;
            str += `<td>(주)${result.admin_order_company}</td>`;
            str += `</tr>`;
            str += `</tbody>`;
        },
        error: (err) => {
			 alert('발주정보 조회 실패!') 
			 return false;
			 }
    })
    if (admin_order_prod_no == 0) {//앨범이라면
        $.ajax({
            url: "/getAlbum/" + admin_order_album_no,
            type: "get",
            async: false,
            success: (result) => {
                str += `<tbody id="album_tbx" class="tbx">`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">앨범번호 수정불가</div>`;
                str += `</th>`;
                str += `<td>${result.album_no}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">타이틀 수정불가</div>`;
                str += `</th>`;
                str += `<td>${result.album_title}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">아티스트 수정불가</div>`;
                str += `</th>`;
                str += `<td>${result.album_artist}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">앨범이미지 수정불가</div>`;
                str += `</th>`;
                str += `<td><img src="${result.album_img_path}"/></td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">앨범버전 수정불가</div>`;
                str += `</th>`;
                str += `<td>${result.album_version}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">발매일 수정불가</div>`;
                str += `</th>`;
                str += `<td>${result.album_release_date}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">앨범가격</div>`;
                str += `</th>`;
                str += `<td>${result.album_price}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">할인률 for update</div>`;
                str += `</th>`;
                str += `<td>${result.album_discount_rate}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">재고 for update</div>`;
                str += `</th>`;
                str += `<td>${result.album_stock}</td>`;
                str += `</tr>`;
                str += `</tbody>`;
            },
            error: (err) => {
				 alert('앨범 조회 실패!');
				  return false;}
        })
        $(".detailTable").append(str);
    } else {//상품이라면
     $.ajax({
            url: "/getProduct/" + admin_order_prod_no,
            type: "get",
            async: false,
            success: (result) => {
                str += `<tbody id="prod_tbx" class="tbx">`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">상품번호 수정불가</div>`;
                str += `</th>`;
                str += `<td>${result.prod_no}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">상품명 수정불가</div>`;
                str += `</th>`;
                str += `<td>${result.prod_name}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">엔터테이너 수정불가</div>`;
                str += `</th>`;
                str += `<td>${result.prod_artist}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">상품이미지 수정불가</div>`;
                str += `</th>`;
                str += `<td><img src="${result.prod_img_path}"/></td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">상품타입 수정불가</div>`;
                str += `</th>`;
                str += `<td>${result.prod_type}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">상품사이즈 수정불가</div>`;
                str += `</th>`;
                str += `<td>${result.prod_sizetype}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">등록일 수정불가</div>`;
                str += `</th>`;
                str += `<td>${result.prod_regdate}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">상품가격</div>`;
                str += `</th>`;
                str += `<td>${result.prod_price}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">할인률 for update</div>`;
                str += `</th>`;
                str += `<td>${result.prod_discount_rate}</td>`;
                str += `</tr>`;
                str += `<tr>`;
                str += `<th>`;
                str += `<div class="th">재고 for update</div>`;
                str += `</th>`;
                str += `<td>${result.prod_stock}</td>`;
                str += `</tr>`;
                str += `</tbody>`;
			},
			error:(err)=>{
				alert('상품 조회 실패!');
				  return false;
			}
		});	
        
        $(".detailTable").append(str);
    }
    $(".modal").fadeIn();
};


//모달창 닫기
$("#close_btn").click(() => {
    $("body").css("overflowY", "auto");
    $(".modal").fadeOut();
});

$(".modal_close").click(() => {
    $("body").css("overflowY", "auto");
    $(".modal").fadeOut();
});

