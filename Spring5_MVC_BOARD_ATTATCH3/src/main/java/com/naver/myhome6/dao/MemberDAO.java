package com.naver.myhome6.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.myhome6.domain.Member;

@Repository
public class MemberDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public int insert(Member m) {
		return sqlSession.insert("Members.insert", m);
	}

	public Member isId(String id) {
		return sqlSession.selectOne("Members.idcheck", id);
	}

	public int getListCount() {
		return sqlSession.selectOne("Members.getlistcount");
	}

	public List<Member> getMemberList(Map<String, Integer> map) {
		return sqlSession.selectList("Members.getmemberlist", map);
	}

	public int update(Member m) {
		return sqlSession.update("Members.update", m);
	}

	public List<Member> searchList(Map<String, Object> map) {

		return sqlSession.selectList("Members.getSearchList", map);
	}

	public int searchListCount(Map<String, Object> map) {

		return sqlSession.selectOne("Members.searchcount", map);
	}
	
	public Member member_info(String id) {
		return sqlSession.selectOne("Members.idcheck",id);
	}
	
	
	public int delete(String id) {
		return sqlSession.delete("Members.delete",id);
	}
	

}
