package com.naver.myhome6.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naver.myhome6.domain.Comment;
import com.naver.myhome6.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	CommentService commentService;

	/*
	 * 
	 * @responseBody와 jackson을 이용하여 JSON 사용하기
	 * 
	 * @ResponseBody 란?
	 * 
	 * 메서드에 @ResponseBody 애노테이션이 되어 있으면 return 되는 값은 View를 통해서 출력되는 것이 아니라 HTTP
	 * Response Body 에 직접 쓰여지게 됩니다. 예)HTTP 응답 프로토콜의 구조 HTTP/1.1 Message Header 2000K
	 * ==> Start Line Content-type:text/html = > Message Header Connect close server
	 * : Apache Tomcat ... Last-Modified : Mon,...
	 * 
	 * 
	 * Message Body <html> <head><title>Hello Jsp</title></head><body>Hello JSP!
	 * </body></html>
	 * 
	 * 응답 결과를 HTML이 아닌 json 으로 변환하여 message body 에 저장하려면 스프링에서 제공하는 변환기(Converter )를
	 * 사용해야 합니다. 이 변환기를 이용해서 자바 객체를 다양한 타입으로 변환하여 Http Response body에 설정할 수 있습니다.
	 * 스프링 설정 파일에<mvcLannotation-driven> 을 설정하면 변환기가 생성됩니다. 이 중에서 자바 객체를 json 응답 바디로
	 * 변환할 때는 MappingJackson2HttpMessageConverter 를 사용합니다.
	 * 
	 * @responseBody 를 이용해서 각 메서드의 실행 결과는 JSON 으로 변환되어 Http Response Body 에 설정됩니다.
	 */

	@ResponseBody
	@PostMapping("CommentList.bo")
	public List<Comment> commentList(int BOARD_RE_REF) throws IOException {

		List<Comment> list = commentService.getCommentList(BOARD_RE_REF);
		System.out.println("댓글 정보 : " + list.size());
		return list;
	}

	
	@PostMapping("CommentDelete.bo")
	public void CommentDelete(int num , HttpServletResponse resp) throws IOException {
		int result = commentService.commentsDelete(num);
		resp.getWriter().print(result);
	}
	
	@PostMapping("CommentUpdate.bo")
	public void CommentUpdate(Comment c,HttpServletResponse res) throws IOException {
	
		int result = commentService.commentUpdate(c);
		res.getWriter().print(result);
		
	}
	
	@RequestMapping("CommentAdd.bo")
	public void CommentAdd(String id, String content, int board_re_ref, HttpServletResponse response) throws Exception {
		System.out.println("comment 글num = " + board_re_ref);
		Comment c = new Comment();
		c.setId(id);
		c.setContent(content);
		c.setBoard_re_ref(board_re_ref);
		int result = commentService.commentsInsert(c);
		System.out.println("result = " + result);
		response.getWriter().print(result);
	}

}
