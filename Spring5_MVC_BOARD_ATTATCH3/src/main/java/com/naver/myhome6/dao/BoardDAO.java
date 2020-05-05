package com.naver.myhome6.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.myhome6.domain.Board;
import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;

@Repository
public class BoardDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public int getListCount() {
		return sqlSession.selectOne("Boards.count");
	}

	public List<Board> getBoardList(HashMap<String, Integer> map) {
		return sqlSession.selectList("Boards.list", map);
	}

	public int setReadCountUpdate(int num) {

		return sqlSession.update("Boards.readcountupdate", num);

	}

	public void insertBoard(Board board) {

		System.out.println(board.getBOARD_FILE());

		System.out.println(board.getBOARD_ORIGINAL());
		int result = sqlSession.insert("Boards.insert", board);

		if (result == 1) {
			System.out.println("글 등록 완료");
		} else {
			System.out.println("글 등록 실패");
		}
	}

	public Board getDetail(int num) {
		return sqlSession.selectOne("detail", num);
	}

	public int boardReply(Board board) {
		return sqlSession.insert("Boards.reply_insert", board);
	}

	public int boardReplyUpdate(Board board) {

		return sqlSession.update("Boards.updateRe", board);
	}

	public int boardModify(Board modifyboard) {
		return sqlSession.update("Boards.Modify", modifyboard);
	}

	public Board isBoardWriter(HashMap<String, String> map) {
		return sqlSession.selectOne("Boards.BoardWriter", map);
	}

	public int boardDelete(Board b) {
		
		return sqlSession.delete("Boards.delete", b);
	}
	
	public int insert_deleteFile(String before_file) {
		return sqlSession.insert("Boards.insert_deleteFile",before_file);
	}
	public int insert_deleteFiles(Board board) {
		return sqlSession.insert("Boards.insert_deleteFiles",board);
	}
	
	public List<String> getDeleteFileList(){
		return sqlSession.selectList("Boards.deleteFileList");
	}
	
	
	

}
