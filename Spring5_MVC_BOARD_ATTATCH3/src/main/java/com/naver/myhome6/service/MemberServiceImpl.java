package com.naver.myhome6.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.myhome6.dao.MemberDAO;
import com.naver.myhome6.domain.Member;

import sun.security.util.Password;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO dao;

	@Override
	public int isId(String id, String pass) {

		Member rmember = dao.isId(id);

		int result = -1; // 아이디가 존재하지 않는 경우 - rmember 가 null 인경우

		if (rmember != null) { // 아이디가 존재하는 경우

			if (rmember.getPassword().equals(pass)) {
				result = 1; // 아이디와 비밀번호가 일치하는 경우
			} else
				result = 0; // 아이디는 존재하지만 비밀번호가 일치하지 않는 경우
		}
		
		return result;

	}

	@Override
	public int insert(Member m) {

		return dao.insert(m);
	}

	@Override
	public int isId(String id) {

		Member rmember = dao.isId(id);
		return (rmember == null) ? -1 : 1;
		// -1 은 아이디가 존재하지 않는 경우
		// 1은 아이디가 존재하는 경우

	}
//
//	@Override
//	public Member member_info(String id) {
//		return dao.isId(id);
//	}
	
	
	
	@Override
	public Member member_info(String id) {
		return dao.member_info(id);
	}

	@Override
	public int delete(String id) {
		return dao.delete(id);

	}

	@Override
	public int update(Member m) {

		return dao.update(m);
	}

	@Override
	public List<Member> getSearchList(int index, String search_word, int page, int limit) {

		Map<String, Object> map = new HashMap<String, Object>();
		
	
		if(index!=-1) {
			String[] search_field = new String[] {"id","name","age","gender"};
			map.put("search_field", search_field[index]);
			map.put("search_word", "%"+search_word+"%");
		}
		
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		map.put("start", startrow);
		map.put("end", endrow);
		
		return dao.searchList(map);
	}

	@Override
	public int getSearchListCount(int dex, String search_word) {

		Map<String, Object> map = new HashMap<String, Object>();
		if(dex!=-1) {
			
			String[] search_field = new String[] {"id","name","age","gender"};
			map.put("search_field", search_field[dex]);
			map.put("search_word", "%" + search_word + "%");
		}
		
		return dao.searchListCount(map);
	}

	@Override
	public List<Member> getMemberList(int page, int limit) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;

		map.put("start", startrow);
		map.put("end", endrow);
		return dao.getMemberList(map);
	}

	@Override
	public int getListCount() {

		return dao.getListCount();
	}

}
