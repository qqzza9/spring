package com.joongang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joongang.domain.AuthVO;
import com.joongang.domain.MemberVO;
import com.joongang.mapper.MemberMapper;

import lombok.Setter;

@Service

public class MemberService {
	@Setter(onMethod_ =  @Autowired) 
	private MemberMapper mapper;
	
	@Setter(onMethod_ =  @Autowired) 
	private PasswordEncoder pwencoder;
	
	public void signup(MemberVO vo) {
		vo.setUserpw(pwencoder.encode(vo.getUserpw()));
		mapper.insertMember(vo);
	}
	public AuthVO authenticate(MemberVO vo) throws Exception {
		MemberVO selectVO=mapper.selectMemberByUserid(vo.getUserid());
		if(selectVO == null) {
			throw new Exception("nonuser");	//Exception강제로 일어나게 만드는것. 없는 사용자
		}
		if(!pwencoder.matches(vo.getUserpw(), selectVO.getUserpw())) {
			throw new Exception("nomatch");//값이 맞지 않으면
		}
		AuthVO authVO = new AuthVO();
		authVO.setUserid(selectVO.getUserid());
		authVO.setUsername(selectVO.getUsername());
		return authVO;
	}
}

