package com.y4j.final_project.message.service;

import java.util.ArrayList;

import com.y4j.final_project.command.MessageVO;

public interface MessageService {

	//쪽지 발송 메서드
	public int sendMsg(MessageVO vo);
	
	//받은 쪽지 조회 메서드(id 기준)
	public ArrayList<MessageVO> getReceivedMsg(String receiver);
	
	//보낸 쪽지 조회 메서드(id 기준)
	public ArrayList<MessageVO> getSentMsg(String writer);

	//미확인 쪽지 개수 반환 메서드
	public int getUncheckedMsg(Object receiver);
	
	//수신 메세지 수신일 업데이트 메서드
	public int checkMsg(int msg_no);
	
}
