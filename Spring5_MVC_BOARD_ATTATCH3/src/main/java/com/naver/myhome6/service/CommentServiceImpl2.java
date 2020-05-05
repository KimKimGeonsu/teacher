package com.naver.myhome6.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.myhome6.aop.LogAdvice;
import com.naver.myhome6.dao.BoardDAO;
import com.naver.myhome6.dao.CommentDAO;
import com.naver.myhome6.domain.Comment;

/*
 * 
 * 기존의 CommentServiceImpl클래스에서
 * 메서드 실행 직전에 로그를 출력하도록 할 경우의 소스입니다
 * 
 * 이경우에는 CommentServiceImpl클래스와 LogAdvice 클래스가 강하게 결합되어있습니다
 * LogAdvice 클래스를 다른 클래스로 변경해야 하거나
 * 공통 기능의 메서드 beforeLog()의 시그니처(리턴타입, 이름, 매개변수)가 변경되는 경우 수정이 불가피합니다.
 * Advice 클래스 객체를 생성하고 공통 메서드를 호출하는 메서드를 호출하는 코드가 비즈니스 코드에 있다면 핵심관심(CommentServiceImpl)과 횡단 관심(LogAdvice)를 분리할수 없습니다
 * 이것을 스프링의 AOP를 이용해서 분리해보겟습니다
 * 
 */

@Service
public class CommentServiceImpl2 implements CommentService {

	@Autowired
	CommentDAO commentDAO;
//	
//	private LogAdvice log;
//	
//	public CommentServiceImpl2() {
//		log = new LogAdvice();
//	}
	
	
	@Override
	public int getListCount(int BOARD_RE_REF) {
	//	log.beforeLog();
		return commentDAO.getListCount(BOARD_RE_REF);
	}

	@Override
	public List<Comment> getCommentList(int BOARD_RE_REF) {
		//log.beforeLog();
		return commentDAO.getCommentList(BOARD_RE_REF);
	}

	@Override
	public int commentsInsert(Comment c) {
		
		return commentDAO.insert(c);
	}

	@Override
	public int commentsDelete(int num) {
	
		return commentDAO.delete(num);
	}

	@Override
	public int commentUpdate(Comment co) {
	
		return commentDAO.update(co);
	}

}
