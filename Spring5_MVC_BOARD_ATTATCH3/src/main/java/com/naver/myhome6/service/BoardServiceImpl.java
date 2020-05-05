package com.naver.myhome6.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naver.myhome6.dao.BoardDAO;
import com.naver.myhome6.domain.Board;
import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO boarddao;

	@Override
	public List<Board> boardlist(int page, int limit) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;

		map.put("start", startrow);
		map.put("end", endrow);
	
		return boarddao.getBoardList(map);
	}

	@Override
	public int getListCount() {

		return boarddao.getListCount();
	}

	@Override
	public Board getDetail(int num) {
		//double i= 1/0; //AfterThrowing을 확인하기위해 사용
		if (setReadCountUpdate(num) != 1)
			return null;
		return boarddao.getDetail(num);
	}

	@Override
	public int boardReply(Board board) {
		boardReplyUpdate(board);
		double e = 1/0;
		board.setBOARD_RE_LEV(board.getBOARD_RE_LEV() + 1);
		board.setBOARD_RE_SEQ(board.getBOARD_RE_SEQ() + 1);
		return boarddao.boardReply(board);
	}

	@Override
	public int boardModify(Board modifyboard) {

		return boarddao.boardModify(modifyboard);
	}
	
	
	//https://m.blog.naver.com/PostView.nhn?blogId=writer0713&logNo=220579572336&proxyReferer=https%3A%2F%2Fwww.google.com%2F
	
	@Override
	public int boardDelete(int num) {
		int result = 0;
		Board board = boarddao.getDetail(num);
		if (board != null) {
			result = boarddao.boardDelete(board);
			//추가 -원문글에만 파일이 있는경우
			if(board.getBOARD_FILE()!=null) {
				System.out.println("삭제목록 추가");
				boarddao.insert_deleteFile(board.getBOARD_FILE());
			}
		}
		return result;
	}

	@Override
	public int setReadCountUpdate(int num) {
		return boarddao.setReadCountUpdate(num);

	}

	@Override
	public boolean isBoarWriter(int num, String pass) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void isnertBoard(Board board) {
		// TODO Auto-generated method stub

	}

	@Override
	public int boardReplyUpdate(Board board) {

		return boarddao.boardReplyUpdate(board);
	}

	@Override
	public void insertBoard(Board board) {

		boarddao.insertBoard(board);

	}

	@Override
	public boolean isBoardWriter(int num, String bOARD_PASS) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("num", String.valueOf(num));
		map.put("pass", bOARD_PASS);

		Board result = boarddao.isBoardWriter(map);
		if (result == null)
			return false;
		else
			return true;
	}

	@Override
	public int insert_deleteFile(String before_file) {
		return boarddao.insert_deleteFile(before_file);
	}

	@Override
	public List<String> getDeleteFileList() {
		
		return boarddao.getDeleteFileList();
	}
	
	
	

}
