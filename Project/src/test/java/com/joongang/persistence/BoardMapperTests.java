package com.joongang.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joongang.domain.BoardVO;
import com.joongang.mapper.BoardMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.joongang.config.RootConfig.class,
		com.joongang.config.SecurityConfig.class})
@Log4j2
public class BoardMapperTests {
	@Setter(onMethod_ =  @Autowired) 
	private BoardMapper mapper;
	
	//@Test
	public void testInsertBoard() {
		//BoardVO vo = new BoardVO();
		//vo.setTitle("게시판 테스트");
		//vo.setContent("게시판 내용");
		//vo.setWriter("test");
	
		BoardVO vo = new BoardVO("게시판 테스트2", "게시판 내용2", "test2");
		mapper.insert(vo);	
	}
}
