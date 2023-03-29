package com.y4j.final_project.command;

import java.time.LocalDateTime;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleVO {
	
	/* 서버에서 유효성 검사
	 * @NotNull - null값만 허용하지 않음, 공백으로 주면 에러 안남 (wrapper의Integer, Long, String 등)
	 * @NotBlank - null값과 공백 허용하지 않음 (String에만 적용)
	 * @NotEmpty - null값을 허용하지 않음 (Array, list 적용)
	 * @Pattern - 정규표현식에 맞는 문자열을 정의할 수 있음 (String에만 적용)
	 * 
	 * @Email - 이메일 형식 검증 (공백은 통과)
	 * @Min - 최소값
	 * @Max - 최대값
	 */

	private int schedule_no;
	
//	@FutureOrPresent(message = "오늘 이후의 날짜만 선택할 수 있습니다")
	@NotBlank(message = "날짜는 공백일 수 없습니다")
	private String schedule_start_time;
	
//	@FutureOrPresent(message = "오늘 이후의 날짜만 선택할 수 있습니다")
	@NotBlank(message = "날짜는 공백일 수 없습니다")
	private String schedule_end_time;
	
	private String schedule_writer;
	@NotBlank(message = "스케줄 종류를 선택하세요")
	private String schedule_type;
	@NotBlank(message = "스케줄명을 입력하세요")
	private String schedule_ent_name;
	
	@NotBlank(message = "주소를 입력하세요")
	private String schedule_location;
	private String schedule_content;
	
	//아티스트 타입, 이름
//	@NotBlank(message = "아티스트 종류를 선택하세요")
	private String ent_type;
	private String ent_name; 
	
}
