package com.naver.myhome6.task;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.naver.myhome6.service.BoardService;
@Service
public class FileCheckTask {
	
	@Value("${savefoldername}")
	private String saveFolder;
	
	@Autowired
	private BoardService boardService;

	//cron 사용법
	//seconds(초) minutes(분) hours(시) day(일)
	//months(달) day of week(요일0~6) year(optional)
	//				초분시 일 달 요일
	@Scheduled(cron = "0 20 11 * * *")
	public void checkFiles() throws Exception{
		System.out.println("checkFiles");
		List<String> deleteFileList = boardService.getDeleteFileList();
		
		for(String filename:deleteFileList) {
			File file = new File(saveFolder+filename);
			if(file.exists()) {
				if(file.delete()) {
					System.out.println(file.getPath()+"삭제되었습니다");
				}
			}
		}
		
	}

	
	//@Scheduled(fixedDelay = 1000)//이전에 실행된 Task종료
	//밀리세컨드 단위입니다.
	public void test() throws Exception{
		System.out.println("test");
	}
	
	
}
