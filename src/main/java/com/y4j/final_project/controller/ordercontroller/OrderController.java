package com.y4j.final_project.controller.ordercontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.y4j.final_project.command.OrderHistoryVO;
import com.y4j.final_project.command.ordercommand.Admin_orderVO;
import com.y4j.final_project.command.ordercommand.AlbumVO;
import com.y4j.final_project.command.ordercommand.CategoryVO;
import com.y4j.final_project.command.ordercommand.ProductVO;
import com.y4j.final_project.service.orderservice.OrderService;
import com.y4j.final_project.util.Criteria;
import com.y4j.final_project.util.PageVO;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	@Qualifier("orderService")
	private OrderService orderService;


	//목록화면
	@GetMapping("/orderList")
	public String orderList(Model model, Criteria cri) {
		
		//발주리스트 가져오기
		ArrayList<Admin_orderVO> orderList=orderService.getOrderList(cri);
		model.addAttribute("orderList",orderList);
	
		//페이징 처리
		int total=orderService.getOrderTotal(cri);
		PageVO pageVO=new PageVO(cri,total);
		model.addAttribute("pageVO",pageVO);

		return "order/orderList";
	}

	/////////////////////////////////
	//상세화면
	@PostMapping("/albumDetail")
	public String albumDetail(@RequestParam("album_no")Integer album_no, Model model) {
		AlbumVO vo=orderService.getAlbumDetail(album_no);
		model.addAttribute("vo",vo);
		return "order/albumDetail";
	}

	@PostMapping("/productDetail")
	public String productDetail(@RequestParam("prod_no")Integer prod_no, Model model) {
		ProductVO vo=orderService.getProductDetail(prod_no);
		model.addAttribute("vo",vo);
		return "order/productDetail";
	}


	//추가발주
	@PostMapping("/additionalRegist")
	public String additionalRegist(Admin_orderVO vo, RedirectAttributes ra) {
		int result=0;
		result+=orderService.additionalRegist(vo);
		if(vo.getAdmin_order_album_no()==0) {//상품재고업데이트
			result+=orderService.updateProdStock(vo);
		}else if(vo.getAdmin_order_prod_no()==0) {//앨범재고업데이트
			result+=orderService.updateAlbumStock(vo);
		}
		
		
		if(result==2) {
			String msg="성공적으로 등록되었습니다.";
			ra.addFlashAttribute("msg",msg);
		}else{
			String msg="등록에 실패하였습니다.";
			ra.addFlashAttribute("msg", msg);
		}
		
		
		return "redirect:/order/orderList";
	}



	//////////////////////////////////
	//초기발주
	@GetMapping("/orderReg")
	public String orderReg() {
		return "order/orderReg";
	}

	//초기발주 form
	@PostMapping("/registForm")
	public String registForm(Admin_orderVO avo, AlbumVO alvo, ProductVO pvo, RedirectAttributes ra) {
		
		int result=0;
		String category=avo.getAdmin_order_sizetype();
		if(category.equals("미니")||category.equals("정규")||category.equals("싱글")) {//앨범일 때
			result+=orderService.albumRegist(alvo); //앨범에 저장
			result+=orderService.adminAlbumRegist(avo); //관리자 order에 저장
		}else{//상품일 때
			result+=orderService.productRegist(pvo); //상품에 저장
			result+=orderService.adminProductmRegist(avo); //관리자 상품에 저장
		}

		String msg=result==2?"성공적으로 등록되었습니다.":"등록에 실패하였습니다.";
		ra.addFlashAttribute("msg",msg);
		return "redirect:/order/orderList";
	}

	//앨범정보수정
	@PostMapping("/albumModify")
	public String albumModify(AlbumVO vo, RedirectAttributes ra) {
		int result=orderService.albumModify(vo);
		String msg=result==1?"성공적으로 수정되었습니다.":"업데이트에 실패했습니다.";
		ra.addFlashAttribute("msg",msg);
		return "redirect:/order/orderList";
	}

	//상품정보수정
	@PostMapping("/productModify")
	public String productModify(ProductVO vo, RedirectAttributes ra) {
		int result=orderService.productModify(vo);
		String msg=result==1?"성공적으로 수정되었습니다.":"업데이트에 실패했습니다.";
		ra.addFlashAttribute("msg",msg);
		return "redirect:/order/orderList";
	}
	
	//카테고리 등록화면 띄우기
	@GetMapping("/categoryReg")
	public String categoryReg() {
		return "order/categoryReg";
	}
	
	//대분류 카테고리 등록
	@PostMapping("/bigRegForm")
	public String bigRegForm(CategoryVO vo, RedirectAttributes ra) {
		int result=orderService.bigCategoryReg(vo);
		String msg=result==1?"성공적으로 등록되었습니다.":"등록에 실패했습니다.";
		ra.addFlashAttribute("msg",msg);
		return "redirect:/order/categoryReg";
	}
	
	//중분류 카테고리 등록
	@PostMapping("/midRegForm")
	public String midRegForm(CategoryVO vo, RedirectAttributes ra) {
		int result=orderService.midSmallCategoryReg(vo);
		String msg=result==1?"성공적으로 등록되었습니다.":"등록에 실패했습니다.";
		ra.addFlashAttribute("msg",msg);
		return "redirect:/order/categoryReg";
	}
	
	///////////////////////////////////////////////////////////////
	//차트페이지
	@GetMapping("bestSellerChart")
	public String bestSellerChart(Model model, Criteria cri) {
		ArrayList<Map<String, String>> parr = orderService.getTopProduct();
		ArrayList<Map<String, String>> aarr = orderService.getTopAlbum();
		
		model.addAttribute("parr", parr);
		model.addAttribute("aarr", aarr);
		
		//발주리스트 가져오기
		ArrayList<OrderHistoryVO> list=orderService.getOrderHistoryList(cri);
		model.addAttribute("list",list);
			
		//페이징 처리
		int total=orderService.getOrderHistoryTotal(cri);
		PageVO pageVO=new PageVO(cri,total);
		model.addAttribute("pageVO",pageVO);
		
		return "order/bestSellerChart";
	}
	
}
