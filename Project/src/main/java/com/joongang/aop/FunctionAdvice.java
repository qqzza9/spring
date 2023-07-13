package com.joongang.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.joongang.domain.MemberVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Aspect
@Component // is a class level annotation
public class FunctionAdvice {
   
   //all method in BoardService class
   @Before("execution(* com.joongang.service.BoardService.get(..))") //특정서비스의 특정함수
   //@Before("execution(* com.joongang.service.BoardService.*(..))") 특정서비스
   //@Before("execution(* com.joongang.service.*.*(..))") 서비스전체
   public void boardBefore() {
      log.info("=============boardBefore============");
   }
   
   @Setter(onMethod_=@Autowired)
   private PasswordEncoder pwencoder;
   /*
   @Before("execution(* com.joongang.service.MemberService.signup(..))")
   public void memberSignup(JoinPoint jp) { //컨트롤러에서 입력한 memberVO값을 받아올 수 있다.
      try {
         MemberVO vo = (MemberVO) jp.getArgs()[0];
         vo.setUserpw(pwencoder.encode(vo.getUserpw()));
      } catch (Throwable e) {
         e.printStackTrace();
      }
   }
  
   @Around("execution(* com.joongang.service.MemberService.signup(..))")
   public Object memberSignup(ProceedingJoinPoint pjp) { 
      Object result = null;
      try {
         MemberVO vo = (MemberVO) pjp.getArgs()[0];
         vo.setUserpw(pwencoder.encode(vo.getUserpw()));
         result = pjp.proceed(); //MemberSerive.signup함수 호출. proceed호출안하면 함수가 동작안함
      } catch (Throwable e) {
         e.printStackTrace();
      }
      return result;
   }
   
   @Around("execution(* com.joongang.service.BoardService.modify(..))")
   public Object Boardmodify(ProceedingJoinPoint pjp) { 
      Object result = null;
      try {
         BoardVO vo = (BoardVO) pjp.getArgs()[0];
         vo.setTitle("TT"+(vo.getTitle()));
         result = pjp.proceed();
      } catch (Throwable e) {
         e.printStackTrace();
      }
      return result;
   }*/
}