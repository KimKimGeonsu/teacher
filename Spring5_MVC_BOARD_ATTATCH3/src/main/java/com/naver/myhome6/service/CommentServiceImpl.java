//package com.naver.myhome6.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.naver.myhome6.dao.BoardDAO;
//import com.naver.myhome6.dao.CommentDAO;
//import com.naver.myhome6.domain.Comment;
//
//@Service
//public class CommentServiceImpl implements CommentService {
//
//	@Autowired
//	CommentDAO commentDAO;
//
//	@Override
//	public int getListCount(int BOARD_RE_REF) {
//		return commentDAO.getListCount(BOARD_RE_REF);
//	}
//
//	@Override
//	public List<Comment> getCommentList(int BOARD_RE_REF) {
//		// TODO Auto-generated method stub
//		return commentDAO.getCommentList(BOARD_RE_REF);
//	}
//
//	@Override
//	public int commentsInsert(Comment c) {
//		
//		return commentDAO.insert(c);
//	}
//
//	@Override
//	public int commentsDelete(int num) {
//	
//		return commentDAO.delete(num);
//	}
//
//	@Override
//	public int commentUpdate(Comment co) {
//	
//		return commentDAO.update(co);
//	}
//
//}
