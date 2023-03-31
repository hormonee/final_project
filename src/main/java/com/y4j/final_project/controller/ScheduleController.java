package com.y4j.final_project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.y4j.final_project.command.EntertainerScheduleVO;
import com.y4j.final_project.command.ScheduleVO;
import com.y4j.final_project.schedule.service.ScheduleService;
import com.y4j.final_project.util.Criteria;
import com.y4j.final_project.util.PageVO;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	
	@Autowired
	@Qualifier("scheduleService")
	private ScheduleService scheduleService;
	
	@GetMapping("/admin_scheduleReg")
	public String reg() {	
		return "schedule/admin_scheduleReg";
	}
	
	//일정 리스트
	@GetMapping("/admin_scheduleList")
	public String list(Model model,
					   Criteria cri) {
		ArrayList<ScheduleVO> list = scheduleService.getList(cri);
		
		if(list.isEmpty()) {
			String noSearch = "검색된 일정이 없습니다.";
			model.addAttribute("noSearch", noSearch);
		}
		model.addAttribute("list",list);
		
		//하루 일정
		LocalDate now = LocalDate.now();
		int todayCnt = scheduleService.getTodayScheduleCnt(now);
		model.addAttribute("todayCnt", todayCnt);
		
		//pagination 처리
		int total = scheduleService.getScheduleTotal(cri);
		PageVO pageVO = new PageVO(cri, total);
		model.addAttribute("pageVO", pageVO);

		return "schedule/admin_scheduleList";
	}
	
	
	//일정 상세
	@GetMapping("/admin_scheduleDetail")
	public String getDetail(@RequestParam("schedule_no") int schedule_no,
							Model model) {
		ScheduleVO vo = scheduleService.getDetail(schedule_no);
		model.addAttribute("vo", vo);
		
		return "schedule/admin_scheduleDetail";
	}
	
	//등록 요청
	@PostMapping("/registForm")
	public String registForm(@Valid ScheduleVO vo, Errors errors, Model model,
							 EntertainerScheduleVO vo2,
							 RedirectAttributes ra) {
		System.out.println("error 없음");
		
		if(errors.hasErrors()) { //에러 존재시 true
			
			System.out.println("error존재");
			List<FieldError> list = errors.getFieldErrors(); //에러가 발생된 목록
			for(FieldError err : list) {
				System.out.println(err.getField()); //에러 필드명
				System.out.println(err.getDefaultMessage()); //에러 메세지
				
				if(err.isBindingFailure()) { //유효성 검사의 실패가 아니라, 자바 내부의 에러라면 true 반환
					model.addAttribute("valid_" + err.getField(), "형식이 올바르지 않습니다");
				} else { //유효성 검사에 실패한 목록
					model.addAttribute("valid_" + err.getField(), err.getDefaultMessage());
				}
			} //end
			
			//사용자가 적은 값은 VO에 담김
			model.addAttribute("vo", vo); //사용자가 작성한 값을 화면으로
			
			return "schedule/admin_scheduleReg"; //원래 화면으로
		}//유효성 검사 end
		
		//아티스트 선택 option 처리
		int result = scheduleService.regist(vo);
		int result2 = scheduleService.registMap(vo2);
		
		String msg = result == 1 ? (result2 == 1 ? "일정이 등록되었습니다." : "일정 등록에 실패했습니다.") : "일정 등록에 실패하였습니다.";
		ra.addFlashAttribute("msg", msg);
		return "redirect:/schedule/admin_scheduleList";
	}
	
	//일정 수정
	@PostMapping("/modifyForm")
	public String modifyForm(ScheduleVO vo,
							 RedirectAttributes ra) {	
		scheduleService.scheduleModify(vo);
		return "redirect:/schedule/admin_scheduleDetail?schedule_no=" + vo.getSchedule_no();
	}
	
	// 일정 삭제
	@PostMapping("/deleteForm")
	public String deleteForm(@RequestParam("schedule_no") int schedule_no,
														  RedirectAttributes ra) {
		int result = scheduleService.scheduleDelete(schedule_no);
//		System.out.println("삭제될때 나오는 값은?" + result);
		String msg = ( result == 2 ? "일정이 삭제되었습니다." : "삭제에 실패했습니다." );
		ra.addFlashAttribute("msg", msg);
		
	return "redirect:/schedule/admin_scheduleList";	
	}

}
