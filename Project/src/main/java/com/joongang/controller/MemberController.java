package com.joongang.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joongang.domain.AuthVO;
import com.joongang.domain.MemberVO;
import com.joongang.service.MemberService;

import lombok.Setter;

@Controller //빈으로 등록하는 표시
@RequestMapping("/member/*")
public class MemberController {
	@Setter(onMethod_=@Autowired) //동적생성
	private MemberService memberService; //MemberService의 것을 사용하기 위해 인스턴스 선언
	@GetMapping("/signup")
	public String signupForm() {
		return "/member/signupForm";
	}
	/* 회원가입
	@PostMapping("/signup")
	public String signupSubmit (MemberVO vo) {
		memberService.signup(vo);
		return "redirect:/";
	}*/
	
	@GetMapping("/login")
	public String loginForm() {
		return "/member/loginForm";
	}
	
	@PostMapping("/login")
	public String loginSubmit(MemberVO vo, HttpSession session, RedirectAttributes rttr) {
		try {
			AuthVO authVO=memberService.authenticate(vo); //memberService의 authenticate 호출하고 AuthVO 리턴 받는다
			session.setAttribute("auth", authVO); //리턴한것을 session에 auth추가
			String userURI = (String) session.getAttribute("userURI");//로그인이 안되었을때
			if(userURI != null) {
				session.removeAttribute("userURI"); 
				return "redirect:"+userURI;
			}
			return "redirect:/";
		}catch(Exception e) { //예외처리)없는 아이디거나,비밀번호가 틀렸을 경우
			rttr.addFlashAttribute("error",e.getMessage()); //jsp 메세지 경고창
			rttr.addFlashAttribute("memberVO", vo);
			return "redirect:/member/login";
		}
	}
	@GetMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes rttr) {
		session.removeAttribute("auth");
		rttr.addFlashAttribute("msg","logout");
		return "redirect:/";
	}
	@PostMapping("/signup")
	public String signupLogin(MemberVO vo, HttpSession session) {
		String userpw = vo.getUserpw(); //비밀번호 암호화되기전에 비밀번호 챙겨놓음
		memberService.signup(vo);
		try {
			vo.setUserpw(userpw);
			AuthVO authVO=memberService.authenticate(vo); 
			session.setAttribute("auth", authVO);	
		}catch(Exception e) {
		}
		return "redirect:/";	
	}
}
