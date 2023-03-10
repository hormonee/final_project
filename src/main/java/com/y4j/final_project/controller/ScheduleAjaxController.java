package com.y4j.final_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.y4j.final_project.command.EntertainerVO;
import com.y4j.final_project.command.ScheduleVO;
import com.y4j.final_project.schedule.service.ScheduleService;

@RestController
public class ScheduleAjaxController {
	
	@Autowired
	public ScheduleService scheduleService;
	
	//그룹 이름
	@PostMapping("/getEntType")
	public List<EntertainerVO> getEntType(@RequestBody EntertainerVO vo){
		return scheduleService.getEntType(vo);
	}
	
	//배우 이름
	@PostMapping("/getEntType2")
	public List<EntertainerVO> getEntType2(@RequestBody EntertainerVO vo){
		return scheduleService.getEntType2(vo);
	}
	
	//일정 수정 화면(모달)
	@PostMapping("/getModifyForm")
	public ScheduleVO modifyForm(@RequestBody ScheduleVO vo) {
		return scheduleService.getModifyForm(vo);
	};
	
	//일정 수정
	@PostMapping("/modifyForm")
	public String modifyForm(@RequestBody ScheduleVO vo,
							 RedirectAttributes ra) {
		int result = scheduleService.scheduleModify(vo);
		System.out.println(result);
		/*
		 * String msg = result==1 ? "일정이 수정되었습니다." : "일정 수정에 실패했습니다.";
		 * ra.addFlashAttribute("msg",msg);
		 */

		return "redirect:/schedule/admin_scheduleDetail";
	}
}
