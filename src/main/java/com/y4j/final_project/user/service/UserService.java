package com.y4j.final_project.user.service;

import java.util.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import com.y4j.final_project.command.UserVO;
import com.y4j.final_project.util.Criteria;


public interface UserService {

	//유저 등록 메서드
	public int registUser(UserVO vo);
	
	//유저 정보 조회 메서드
	public ArrayList<UserVO> getUserList(Criteria cri);
	
	//유저 정보 수정 메서드
	public int updateUser(UserVO vo);

	//전체 유저 수 반환 메서드
	public int getUserTotal(Criteria cri);

	//비밀번호 암호화
	public PasswordEncoder passwordEncoder();

	//아이디 중복 검사
	public int idCheck(String user_id);

	

	 

	

	
	//유저 특정 1명 데이터 반환 메서드
	public UserVO getUserInfo(int user_no);
	
	//유저 특정 1명 데이터 반환 메서드
	public UserVO getUserInfo2(Object user_id);
	
	//유저 정보 수정 메서드
	public int updateUserInfo(UserVO vo);
	
}
