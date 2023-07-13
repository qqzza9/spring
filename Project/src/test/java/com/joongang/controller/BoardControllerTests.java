package com.joongang.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {com.joongang.config.RootConfig.class,
					com.joongang.config.ServletConfig.class,
					com.joongang.config.SecurityConfig.class})
@Log4j2
public class BoardControllerTests {
	@Setter(onMethod_ = { @Autowired})
	private WebApplicationContext context; //내부적을 등록되어있는것 
		
	private MockMvc mockMvc;
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	//@Test
	public void testRegister() throws Exception{
		String resultPage= mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
		  .param("title", "게시판 테스트5") //param은 name이랑 똑같아야한다
		  .param("content", "게시판 내용5")
		  .param("writer", "test5"))
		  .andReturn()
		  .getModelAndView()
		  .getViewName();
		log.info(resultPage);
	}
}
