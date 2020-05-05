package com.naver.myhome6.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.naver.myhome6.domain.MailVO;
import com.naver.myhome6.domain.Member;
import com.naver.myhome6.service.MemberService;
import com.naver.myhome6.task.SendMail;

/*
 * @Component를 이용해서 스프링 컨테이너가 해당 클래스 객체를 생성하도록 설정할 수 있지
 * 모든 클래스에 @Component를 할당하면 어떤 클래스가 어떤 역할을 수행하는지 파악하기 어렵습니다.
 * 스프링 프레임워크에서는 이런 클래스들을 분류하기 위해서
 * @Component를 상속하여 다음과 같은 세 개의 애노테이션을 제공합니다.
 * 
 * 1. @Controller - 사용자의 요청을 제어하는 Controller 클래스
 * 2. @Repository - 데이터 베이스 연동을 처리하는 DAO클래스
 * 3. @Service - 비즈니스 로직을 처리하는 Service 클래스
 */
@Controller
public class MemberController {

	@Autowired
	MemberService memberservice;
	
	@Autowired
	SendMail sendMail;

	//로그인 폼이동
		@RequestMapping(value = "/login.net", method = RequestMethod.GET)
		public ModelAndView login(ModelAndView mv, @CookieValue(value = "saveid", required = false) Cookie readCookie) {
			System.out.println(readCookie);
			if(readCookie!=null) {
				
				mv.addObject("saveid", readCookie.getValue());
				
			}
			
			mv.setViewName("member/loginForm");
			return mv;
		}

	// 로그인처리
	@RequestMapping(value = "/loginProcess.net", method = RequestMethod.POST)
	public String loginProcess(@RequestParam("id") String id, @RequestParam("password") String password,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "remember", defaultValue = "") String remember) throws Exception {

		int result = memberservice.isId(id, password);
		System.out.println("결과는 : " + result);

		if (result == 1) {
			// 로그인 성공
			session.setAttribute("id", id);
			// "saveid"라는 이름의 쿠키에 id값을 저장한 쿠키를 생성합니다
			Cookie savecookie = new Cookie("saveid", id);
			if (!remember.equals("")) {
				savecookie.setMaxAge(60 * 60);
				System.out.println("쿠키저장: 60*60초");
				System.out.println(savecookie.getValue());

			} else {
				System.out.println("쿠키저장: 0");
				savecookie.setMaxAge(0);
			}
			response.addCookie(savecookie);
			return "redirect:BoardList.bo";
		} else {
			String message = "비밀번호가 일치하지 않습니다.";
			if (result == -1)
				message = "아이디가 존재하지 않습니다.";

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + message + "');");
			out.println("location.href='login.net;'");
			out.println("</script>");
			out.close();
			return null;
		}

	}

	@RequestMapping(value = "/join.net")
	public String join() {
		return "member/joinForm";
	}

	// 회원가입 처리
	@RequestMapping(value = "/joinProcess.net")
	public void join1(Member member, HttpServletResponse response) throws IOException {

		response.setContentType("text/html;charset=utf-8");

		int result = memberservice.insert(member);
		PrintWriter out = response.getWriter();
		out.println("<script>");

		if (result == 1) {
			MailVO vo = new MailVO();vo.setTo(member.getEmail());
			vo.setContent(member.getId()+"님 회원가입을 축하드립니다");
			sendMail.sendMail(vo);
			
			out.println("alert('가입 성공');");
			out.println("location.href='login.net';");

		} else if (result == -1) {

			out.println("alert('아디 중복');");
			out.println("history.back();");

		} else {
			out.println("alert('가입 실패');");
			out.println("history.back();");

		}
		out.println("</script>");
		out.close();

	}

	// 회원가입 폼에서 아이디 검사
	@RequestMapping(value = "/idcheck.net", method = RequestMethod.GET)
	public void idcheck(@RequestParam("id") String id, HttpServletResponse response) throws Exception {

		int result = memberservice.isId(id);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);

	}

	@RequestMapping(value = "/logout.net", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login.net";
	}

	@RequestMapping(value = "/member_update.net")
	public ModelAndView memberinfo(ModelAndView modelandview, HttpSession session) {

		String id = (String) session.getAttribute("id");
		Member member = memberservice.member_info(id);

		modelandview.setViewName("member/updateForm");
		modelandview.addObject("member", member);

		return modelandview;
	}

	@RequestMapping(value = "/member_list.net")
	public ModelAndView memberlist(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "3", required = false) int limit,
			@RequestParam(value = "search_word", required = false) String search_word,
			@RequestParam(value = "search_field", required = false, defaultValue = "-1") int index,

			ModelAndView modelandview) {

		int listcount = 0;
		List<Member> list = null;

		if (search_word == null || search_word.equals("")) {
			// 총 리스트 수를 받아온다.
			listcount = memberservice.getListCount();
			list = memberservice.getMemberList(page, limit);
		} else {
			// 검색어를 입력한 경우

			listcount = memberservice.getSearchListCount(index, search_word);
			list = memberservice.getSearchList(index, search_word, page, limit);
		}

		int maxpage = (listcount + limit - 1) / limit;
		int startpage = ((page - 1) / 10) * 10 + 1;
		int endpage = startpage + 10 - 1;
		// 마지막 페이지 보여줄 수

		if (endpage > maxpage)
			endpage = maxpage;

		modelandview.setViewName("member/memberlist");
		modelandview.addObject("page", page);
		modelandview.addObject("maxpage", maxpage);
		modelandview.addObject("startpage", startpage);
		modelandview.addObject("endpage", endpage);
		modelandview.addObject("listcount", listcount);
		modelandview.addObject("totallist", list);
		modelandview.addObject("limit", limit);
		modelandview.addObject("search_field", index);
		modelandview.addObject("search_word", search_word);
		return modelandview;

	}

	// 수정처리
	@RequestMapping(value = "/updateProcess.net", method = RequestMethod.POST)
	public void updateProcess(Member member, HttpServletResponse response) throws IOException {
		int result = memberservice.update(member);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		out.println("<script>");

		if (result == 1) {
			out.println("alert('수정되었습니다.'); location.href='BoardList.bo'");
		} else {
			out.println("alert('수정 실패하였습니다.'); history.back();'");
		}
		out.println("</script>");

	}

	// 회원의 개인 정보
	@RequestMapping(value = "/member_info.net", method = RequestMethod.GET)
	public ModelAndView member_info(@RequestParam("id") String id, ModelAndView mv) {
		Member m = memberservice.member_info(id);
		mv.setViewName("member/member_info");
		mv.addObject("memberinfo", m);
		return mv;
	}

	//

	// 삭제
	@RequestMapping(value = "member_delete.net", method = RequestMethod.POST)
	public String delete(String id, HttpServletResponse response) throws Exception {
		int result = memberservice.delete(id);
		if (result != 1) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원 정보 삭제에 실패했습니다')");
			out.println("history.back()");
			out.println("</script>");
			return null;
		}
		return "redirect:member_list.net";

	}

}
