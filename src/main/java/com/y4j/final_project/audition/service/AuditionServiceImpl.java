package com.y4j.final_project.audition.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.y4j.final_project.aws.service.AwsS3Service;
import com.y4j.final_project.command.AuditionFileVO;
import com.y4j.final_project.command.AuditionVO;
import com.y4j.final_project.util.Criteria;

import lombok.RequiredArgsConstructor;

@Service @Primary
@RequiredArgsConstructor
public class AuditionServiceImpl implements AuditionService{

	@Autowired
	private AuditionMapper auditionMapper;
	
	@Autowired
	private AwsS3Service awsService;
	
	
	//오디션 지원폼 등록 메서드
	public int registAud(AuditionVO vo, List<MultipartFile> list) {
		
		int result = auditionMapper.registAud(vo);
		
		for(MultipartFile file : list) {
			
			AuditionFileVO afVO = AuditionFileVO.builder()
					  .audition_cv_file_path(null)
					  .audition_cv_file_extension(null)
					  .build();
			
			auditionMapper.registAudFile(afVO);
		}
		
		return result;
	}

	//오디션 지원폼 전체 조회 메서드	
	public ArrayList<AuditionVO> getAudList(Criteria cri) {
		return auditionMapper.getAudList(cri);
	}
	
	//전체 오디션 지원 수 반환 메서드
	public int getAudTotal(Criteria cri) {
		return auditionMapper.getAudTotal(cri);
	}

	//이미지 데이터 조회
	public List<AuditionFileVO> getProductImg(AuditionVO vo) {
		return auditionMapper.getProductImg(vo);
	}
	
}
