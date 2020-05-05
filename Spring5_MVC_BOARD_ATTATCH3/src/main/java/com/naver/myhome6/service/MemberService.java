package com.naver.myhome6.service;

import java.util.List;

import com.naver.myhome6.domain.Member;

public interface MemberService {

	public int isId(String id, String pass);

	public int insert(Member m);

	public int isId(String id);

	public Member member_info(String id);

	public int delete(String id);

	public int update(Member m);

	public List<Member> getSearchList(int index, String search_word, int page, int limit);

	public int getSearchListCount(int dex, String search_word);
	
	public List<Member> getMemberList(int page, int limit);

	public int getListCount();


}
