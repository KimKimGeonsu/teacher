package com.naver.myhome6.service;

import java.util.List;
import java.util.Map;

import com.naver.myhome6.domain.Board;

public interface BoardService {
	// 글 목록 보기
	public List<Board> boardlist(int page, int limit);

	// 글의 갯수 구하기
	public int getListCount();

	// 글 내용 보기
	public Board getDetail(int num);

	// 글 답변
	public int boardReply(Board board);

	// 글 수정
	public int boardModify(Board modifyboard);

	// 글 삭제
	public int boardDelete(int num);

	// 조회수 업데이트
	public int setReadCountUpdate(int num);

	// 글쓴이인지 확인
	public boolean isBoarWriter(int num, String pass);

	// 글 등록하기
	public void isnertBoard(Board board);

	// 시퀀스 수정
	public int boardReplyUpdate(Board board);

	public void insertBoard(Board board);

	public boolean isBoardWriter(int num, String bOARD_PASS);
	
	public int insert_deleteFile(String before_file);
	
	public List<String> getDeleteFileList();

}
