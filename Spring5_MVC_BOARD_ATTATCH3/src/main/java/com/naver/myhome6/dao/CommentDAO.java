package com.naver.myhome6.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.myhome6.domain.Comment;

@Repository
public class CommentDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public int getListCount(int num) {

		return sqlSession.selectOne("Comments.getListCount", num);

	}

	public int commentDelete(int num) {

		return sqlSession.delete("Comments.delete", num);
	}

	public int insert(Comment c) {

		return sqlSession.insert("Comments.insert", c);
	}

	public List<Comment> getCommentList(int num) {
		return sqlSession.selectList("Comments.list", num);
	}

	public int delete(int num) {
		return sqlSession.delete("Comments.delete",num);
	}

	public int update(Comment co) {

		return sqlSession.update("Comments.update", co);
	}
}
