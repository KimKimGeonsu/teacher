package com.naver.myhome6.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.naver.myhome6.domain.Board;
import com.naver.myhome6.domain.Comment;
import com.naver.myhome6.service.BoardService;
import com.naver.myhome6.service.CommentService;

@Controller
public class BoardController {

	@Autowired
	BoardService boardservice;

	@Autowired
	CommentService commentService;
	
	//추가
	//savefolder.properties
	//속성=값
	//의 형식으로 작성하시면됩니다.
	//String saveFolder = "D:\\spring\\Spring5_MVC_BOARD_ATTATCH2\\src\\main\\webapp\\resources\\upload"
	//값을 가져오기 위해서는 속성(property)를 이용합니다.
	@Value("${savefoldername}")
	private String saveFolder;
	

	/*
	 * 스프링 컨테이너는 매개변수 BbsBean 객체를 생성하고 BbsBean 객체의 setter 메서드를 호출하여 값을 매핑시킴
	 */
	@PostMapping("/BoardAddAction.bo")
	public String bbs_write_ok(Board board, HttpServletRequest request) throws Exception {

		MultipartFile uploadfile = board.getUploadfile();

		if (!uploadfile.isEmpty()) {
			String fileName = uploadfile.getOriginalFilename(); // 원래 파일 명
			board.setBOARD_ORIGINAL(fileName); // 원래 파일명 저장

			// 새로운 폴더 이름 : 오늘 년, 월, 일
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int date = c.get(Calendar.DATE);

			//String saveFolder = request.getSession().getServletContext().getRealPath("resources") + "/upload/";
			//String saveFolder = "D:\\spring\\Spring5_MVC_BOARD_ATTATCH2\\src\\main\\webapp\\resources\\upload/";
			String homedir = saveFolder + year + "-" + month + "-" + date;

			
				//2.특정폴더
			//String saveFolder = "D:\\spring\\Spring5_MVC_BOARD_ATTATCH2\\src\\main\\webapp\\resources\\upload"
			// 이 경로에 파일을 저장할거임
			
			System.out.println(homedir);

			File path1 = new File(homedir);
			if (!(path1.exists())) { // 이 파일의 경로가 존재하는지 확인
				path1.mkdir(); // 없을 경우 경로 만들기
			}

			// 난수를 구합니다. 사용자가 올린 파일의 이름이 중복되면 안되니까
			Random r = new Random();
			int random = r.nextInt(100000000);

			/* 확장자 구하기 시작 //// 원래 파일의 형식이 .png / .jpg / 같은 형식일거니까 */
			int index = fileName.lastIndexOf(".");
			/*
			 * 문자열에서 특정 문자열의 위치 값을 반환한다. indexOf가 처음 발견되는 문자열에 대한 index를 반환하는 반면,
			 * lastIndexOf는 마지막으로 발견되는 문자열의 index를 반환합니다. (파일명에 점이 여러개 있을 경우 맨 마지막에 발견되는
			 * 문자열의 위치를 리턴합니다.)
			 */

			/*
			 * 문자열에서 특정 문자열의 위치 값을 반환한다. indexOf가 처음 발견되는 문자열에 대한 index를 반환하는 반면, lastIndex는
			 * 마지막으로 발견되는 문자열ㅇ릐 index를 반환함
			 */

			System.out.println("index = " + index);

			String fileExtension = fileName.substring(index + 1); // 확장자만 따로 뻄
			System.out.println("fileExtension = " + fileExtension);
			/* 확장자 구하기 끝 */

			// 새로운 파일명
			String refileName = "bbs" + year + month + date + random + "." + fileExtension;

			System.out.println("refileName = " + refileName);

			// 오라클 디비에 저장될 파일명
			String fileDBName = "/" + year + "-" + month + "-" + date + "/" + refileName;
			System.out.println("fileDbName = " + fileDBName);

			// transferTo(File path) : 업로한 파일의 매개변수의 경로에 저장합니다.
			uploadfile.transferTo(new File(saveFolder + fileDBName));

			// 바뀐 파일명으로 저장
			board.setBOARD_FILE(fileDBName);
		}

		boardservice.insertBoard(board); // 저장 메서드 호출

		return "redirect:BoardList.bo";
	}

	@PostMapping("BoardModifyAction.bo")
	public ModelAndView BoardModifyAction(Board board,String before_file ,ModelAndView mv, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(board);
		boolean usercheck = boardservice.isBoardWriter(board.getBOARD_NUM(), board.getBOARD_PASS());

		// 비밀번호가 다른 경우
		if (usercheck == false) {
			response.setContentType("textl/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호가 다름');");
			out.print("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}

		MultipartFile uploadfile = board.getUploadfile();
		String saveFolder = request.getSession().getServletContext().getRealPath("resources") + "/upload/";

		if (uploadfile != null && !uploadfile.isEmpty()) { // 파일을 변경한 경우
			System.out.println("파일을 변경한경우");
			String fileName = uploadfile.getOriginalFilename(); // 원래 파일
			board.setBOARD_ORIGINAL(fileName);

			String fileDBName = fileDBName(fileName, saveFolder);

			// transferTo(File path) : 업로드한 파일을 매개변수의 경로에 저장합ㄴ디ㅏ.
			uploadfile.transferTo(new File(saveFolder + fileDBName));
			// 바뀐 파일명으로 저장
			board.setBOARD_FILE(fileDBName);
		}

		// DAO에서 수정 메서드 호출하여 수정합니다.
		int result = boardservice.boardModify(board);

		// 수정에 실패한 경우
		if (result == 0) {
			System.out.println("게시판 수정 실패");
			mv.setViewName("error/error");
			mv.addObject("url", request.getRequestURL());
			mv.addObject("message", "게시판 수정 실패");

		} else { // 수정 성공
			System.out.println("게시판 수정 완료");
			String url = "redirect:BoardDetailAction.bo?num=" + board.getBOARD_NUM();
			//추가
			//수정 전에 파일이 있고 새로운 파일을 선택한 경우는 삭제할 목록을 테이블에 추가
			if(!before_file.equals("") && !before_file.equals(board.getBOARD_FILE())) {
				boardservice.insert_deleteFile(before_file);
			}
			// 수정한 글 내용을 보여주기 위해 글 내용 보기 페이지로 이동하기 위해
			// 경로를 설정
			mv.setViewName(url);

		}

		return mv;
	}

	private String fileDBName(String fileName, String saveFolder) {
		// 새로운 폴더 이름 : 오늘 년+월+일
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);

		String homedir = saveFolder + year + "-" + month + "-" + date;
		System.out.println(homedir);
		File path1 = new File(homedir);
		if (!(path1.exists()))
			path1.mkdir();// 새로운 폴더 생성

		// 난수를 구합니다.
		Random r = new Random();
		int random = r.nextInt(100000000);

		/* 확장자 구하기 시작 */
		int index = fileName.lastIndexOf(".");
		/*
		 * 문자열에서 특정 문자열의 위치 값을 반환한다. indexOf가 처음 발견되는 문자열에 대한 index를 반환하는 반면, lastIndex는
		 * 마지막으로 발견되는 문자열ㅇ릐 index를 반환함
		 */

		System.out.println("index = " + index);

		String fileExtension = fileName.substring(index + 1); // 확장자만 따로 뻄
		System.out.println("fileExtension = " + fileExtension);
		/* 확장자 구하기 끝 */

		// 새로운 파일명
		String refileName = "bbs" + year + month + date + random + "." + fileExtension;

		System.out.println("refileName = " + refileName);

		// 오라클 디비에 저장될 파일명
		String fileDBName = "/" + year + "-" + month + "-" + date + "/" + refileName;
		System.out.println("fileDbName = " + fileDBName);

		return fileDBName;
	}

	@GetMapping("BoardModifyView.bo")
	public ModelAndView BoardModifyView(int num, ModelAndView m, HttpServletRequest request) {

		// 글의 내용을 DAO에서 읽은 후 얻은 결과를 boarddata 객체에 저장합니다.
		System.out.println("num :" + num);
		Board boarddata = boardservice.getDetail(num);

		if (boarddata == null) {
			System.out.println("상세보기 실패");
			m.setViewName("error/error");
			m.addObject("url", request.getRequestURL());
			m.addObject("message", "상세보기 실패입니다.");
		} else {
			System.out.println("상세보기 성공");

			m.setViewName("board/qna_board_modify");
			m.addObject("boarddata", boarddata);

		}

		return m;

	}

	@GetMapping("BoardReplyView.bo")
	public ModelAndView BoardReplyView(int num, ModelAndView m, HttpServletRequest request) {
		System.out.println("게시판 글에 답변을 달거임 " + num);
		Board boarddata = boardservice.getDetail(num);

		if (boarddata == null) {
			System.out.println("답장 페이지 이동 실패");
			m.setViewName("error/error");
			m.addObject("url", request.getRequestURL());
			m.addObject("message", "답변 실패입니다.");
		} else {
			System.out.println("상세보기 성공");

			m.setViewName("board/qna_board_reply");
			m.addObject("boarddata", boarddata);

		}
		return m;

	}

	@PostMapping("BoardReplyAction.bo")
	public ModelAndView BoardReplyAction(Board board, ModelAndView mav, HttpServletRequest request) throws Exception {

		System.out.println("board 확인" + board.getBOARD_SUBJECT());
		int result = 0;
		result = boardservice.boardReply(board);

		if (result == 0) {

			System.out.println("답변 실패");
			mav.addObject("url", request.getRequestURL());
			mav.addObject("message", "답변 작성 실패입니다.");
			mav.setViewName("error/error");

		} else {

			mav.setViewName("redirect:BoardList.bo");
		}

		return mav;

	}

	@GetMapping(value = "/BoardWrite.bo")
	// == @RequestMapping (value = "/BoardWrite.bo",method=RequestMethod.GET) 이랑 같음
	public String boardwrite() throws Exception {

		return "board/qna_board_write";

	}

	@RequestMapping(value = "/BoardDetailAction.bo")
	public ModelAndView BoardDetail(@RequestParam(value = "num") int num, ModelAndView m, HttpServletRequest request) {

		// 글의 내용을 DAO에서 읽은 후 얻은 결과를 boarddata 객체에 저장합니다.
		System.out.println("num :" + num);
		Board boarddata = boardservice.getDetail(num);

		if (boarddata == null) {
			System.out.println("상세보기 실패");
			m.setViewName("error/error");
			m.addObject("url", request.getRequestURL());
			m.addObject("message", "상세보기 실패입니다.");
		} else {
			System.out.println("상세보기 성공");
			int count = commentService.getListCount(num);
			m.setViewName("board/qna_board_view");
			m.addObject("count", count);
			m.addObject("boarddata", boarddata);

		}

		return m;
	}

	@PostMapping(value = "/BoardDeleteAction.bo")
	public String boarddelete(int num, String BOARD_PASS, HttpServletResponse response) throws Exception {

		// 글 삭제 명령을 요청한 사용자가 글을 작성한 사용자인지 판단하기 위해
		// 입력한 비밀번호와 저장된 비밀번호를 비교하여 일치하면 삭제합니다.
		boolean usercheck = boardservice.isBoardWriter(num, BOARD_PASS);

		// 비밀번호 일치하지 않는 경우
		if (usercheck == false) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호가 다릅니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();

			return null;
		}

		int result = boardservice.boardDelete(num);

		if (result == 0) {
			System.out.println("삭제 실패");
		}

		return "redirect:BoardList.bo";
	}

	@ResponseBody
	@PostMapping("BoardListAjax.bo")
	public Object BoardListAjax(int limit, int page) {

		System.out.println("limit : " + limit + ", page : "+ page);
		// 총 리스트 수를 받아옵니다.
		int listcount = boardservice.getListCount(); // 총 리스트 수

		/*
		 * 총 페이지 수 = (DB에 저장된 총 리스트의 수 + 한 페이지에서 보여주는 리스트의 수 -1)/ 한 페이지에서 보이는 리스트의 수
		 * 
		 * 예를 들어 한 페이지에서 보여주는 리스트의 수가 10개인 경우 예1) DB에 저장된 총 리스트의 수가 0이면 총 페이지의 수는 0페이지
		 * 예2) DB에 저장된 총 리스트의 수가 (1~10)이면 총 페이지수는 1페이지 예3) DB에 저장된 총 리스트의 수가 (11~20) 이면
		 * 총 페이지수는 2페이지 예4) DB에 저장된 총 리스트의 수가 (21~30) 이면 총 페이지수는 3페이지
		 * 
		 * 
		 */

		int maxpage = (listcount + limit - 1) / limit;
		System.out.println("총 페이지수 : " + maxpage);

		/*
		 * startpage : 현재 페이지 그룹에서 맨 처음에 표시될 페이지 수를 의미합니다. ([1], [11],[21] 등...) 보여줄
		 * 페이지가 30개일 경우 [1][2][3]...[30]까지 다 표시하기에는 너무 많기 때문에 보통 한 페이지에는 10페이지 정도 까지
		 * 이동할수 있게 표시합니다. 예)페이지 그룹이 아래와 같은 경우 [1][2][3][4][5][6][7][8][9][10] 페이지 그룹의 시작
		 * 페이지는 startpage에 마지막 페이지는 endpage에 구합니다.
		 * 
		 * 예로 1~10 페이지의 내용을 나타낼때는 페이지 그룹은 [1][2][3]..[10]로 표시되고 11~20 페이지의 내용을 나타낼때는 페이지
		 * 그룹은 [11][12][13]..[20]까지 표시됩니다.
		 */

		int startpage = ((page - 1) / 10) * 10 + 1;
		System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

		// endpage: 현재 페이지 그룹에서 보여줄 마지막 페이지수([10],[20],[30]) 등
		int endpage = startpage + 10 - 1;
		System.out.println("현재 페이지에 보여줄 마지막 페이지 수 =" + endpage);

		/*
		 * 마지막 그룹의 마지막페이지 값은 최대 페이지 값입니다. 예로 마지막 페이지 그룹이 [21]~[30]인 경우 시작
		 * 페이지(startpage=21)와 마지막페이지(endpage=30) 이지만 최대 페이지(maxpage)가 25라면 [21]~[25]까짐나
		 * 표시되도록 합니다.
		 */

		if (endpage > maxpage)
			endpage = maxpage;
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Board> list = boardservice.boardlist(page, limit);
		result.put("boardlist",list);
		result.put("page",page);
		result.put("maxpage",maxpage);
		result.put("startpage",startpage);
		result.put("endpage",endpage);
		result.put("listcount",listcount);
		result.put("limit",limit);
		
		return result;

	}

	@RequestMapping(value = "/BoardList.bo")
	public ModelAndView boardlist(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			ModelAndView model) {

		int limit = 10; // 한 화면에 보여줄 갯수

		// 총 리스트 수를 받아옵니다.
		int listcount = boardservice.getListCount(); // 총 리스트 수

		/*
		 * 총 페이지 수 = (DB에 저장된 총 리스트의 수 + 한 페이지에서 보여주는 리스트의 수 -1)/ 한 페이지에서 보이는 리스트의 수
		 * 
		 * 예를 들어 한 페이지에서 보여주는 리스트의 수가 10개인 경우 예1) DB에 저장된 총 리스트의 수가 0이면 총 페이지의 수는 0페이지
		 * 예2) DB에 저장된 총 리스트의 수가 (1~10)이면 총 페이지수는 1페이지 예3) DB에 저장된 총 리스트의 수가 (11~20) 이면
		 * 총 페이지수는 2페이지 예4) DB에 저장된 총 리스트의 수가 (21~30) 이면 총 페이지수는 3페이지
		 * 
		 * 
		 */

		int maxpage = (listcount + limit - 1) / limit;
		System.out.println("총 페이지수 : " + maxpage);

		/*
		 * startpage : 현재 페이지 그룹에서 맨 처음에 표시될 페이지 수를 의미합니다. ([1], [11],[21] 등...) 보여줄
		 * 페이지가 30개일 경우 [1][2][3]...[30]까지 다 표시하기에는 너무 많기 때문에 보통 한 페이지에는 10페이지 정도 까지
		 * 이동할수 있게 표시합니다. 예)페이지 그룹이 아래와 같은 경우 [1][2][3][4][5][6][7][8][9][10] 페이지 그룹의 시작
		 * 페이지는 startpage에 마지막 페이지는 endpage에 구합니다.
		 * 
		 * 예로 1~10 페이지의 내용을 나타낼때는 페이지 그룹은 [1][2][3]..[10]로 표시되고 11~20 페이지의 내용을 나타낼때는 페이지
		 * 그룹은 [11][12][13]..[20]까지 표시됩니다.
		 */

		int startpage = ((page - 1) / 10) * 10 + 1;
		System.out.println("현재 페이지에 보여줄 시작 페이지 수 = " + startpage);

		// endpage: 현재 페이지 그룹에서 보여줄 마지막 페이지수([10],[20],[30]) 등
		int endpage = startpage + 10 - 1;
		System.out.println("현재 페이지에 보여줄 마지막 페이지 수 =" + endpage);

		/*
		 * 마지막 그룹의 마지막페이지 값은 최대 페이지 값입니다. 예로 마지막 페이지 그룹이 [21]~[30]인 경우 시작
		 * 페이지(startpage=21)와 마지막페이지(endpage=30) 이지만 최대 페이지(maxpage)가 25라면 [21]~[25]까짐나
		 * 표시되도록 합니다.
		 */

		if (endpage > maxpage)
			endpage = maxpage;

		List<Board> list = boardservice.boardlist(page, limit);
		System.out.println("page =" + page);
		System.out.println("limit =" + limit);
		model.setViewName("board/qna_board_list");
		model.addObject("page", page);
		model.addObject("maxpage", maxpage);
		model.addObject("startpage", startpage);
		model.addObject("endpage", endpage);
		model.addObject("limit", limit);
		model.addObject("boardlist", list);
		model.addObject("listcount", listcount);
		return model;
	}
	
	@GetMapping("BoardFileDown.bo")
	public void BoardFileDown(String filename, HttpServletRequest request, String original, HttpServletResponse response) throws Exception{
		String savePath ="resources/upload";
		
		
		//서블릿 실행환경 정보를 담고 있는 객체를 리턴합니다.
		ServletContext context = request.getSession().getServletContext();
		//String sDownloadPath = context.getRealPath(savePath);
		
		//String sFilePath = sDownloadPath +"\\"+fileName;
		// "\" 추가하기위해 "\\"사용합니다.
		//2.
		String sFilePath =saveFolder +"/"+filename;
		System.out.println(sFilePath);
		
		byte b[] = new byte[4096];
		//sFilePath에 있는 파일의 MimeType을 구해옵니다
		String sMimeType = context.getMimeType(sFilePath);
		System.out.println("sMimeType>>>"+sMimeType);
		
		if(sMimeType==null)
			sMimeType ="application/octet-stream";
		
		response.setContentType(sMimeType);
		
		//-이부분이 한글 파일명이 깨지는 것을 방지해줍니다
		String sEncoding = new String(original.getBytes("utf-8"),"ISO-8859-1");
		System.out.println(sEncoding);
		
		/*
		 * 
		 * Content-Disposition: attachemnt : 브라우저는 해당 Content를 처리하지않고,
		 * 
		 */
		
		response.setHeader("Content-Disposition", "attachment;filename ="+sEncoding);
		
		
		
		//프로젝트 속성 - Project - facets에서 자바버전 1.8로수정
		try(
				//웹브라우저로 출력 스트림을 생성합니다
				BufferedOutputStream out2 = new BufferedOutputStream(response.getOutputStream());
				//sFilePath로 지정한 파일에 대한 입력 스트림을 생성합니다
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(sFilePath));
				)
		{
			int numRead;
			//read(b,0,b.length) : 바이트 배열 B의 0 부터 렝스
			//크기만큼 
			while((numRead=in.read(b,0,b.length))!=-1) {//읽을 데이터가 존재하면  				//바이트 배열 b의 0번부터 numRead크기만 큼 브라우저로 출력
				//바이트 배열 b의 0번부터 num Read크기만큼 브라우저로 출력
				out2.write(b,0,numRead);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
				
		
	}

}