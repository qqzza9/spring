package com.joongang.persistence;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joongang.domain.BoardVO;
import com.joongang.domain.ReplyVO;
import com.joongang.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.joongang.config.RootConfig.class,
		com.joongang.config.SecurityConfig.class})
@Log4j2
public class ReplyMapperTests {
	@Setter(onMethod_ =  @Autowired) 
	private ReplyMapper mapper;
	
	//@Test
	public void testInsertReply() {
		long bno = 10l;
		for(int i=0; i<10; i ++) {
			ReplyVO vo = new ReplyVO();
			vo.setBno(bno);
			vo.setReply("test");
			vo.setReplyer("test");
			mapper.insert(vo);		
		}
	}
	
	//@Test
	public void testReadReply() {
		long targetRno=5;
		ReplyVO vo = mapper.read(targetRno);
		log.info(vo);	
	}
	
	
	//@Test
	public void testDeleteReply() {
		long targetRno=1l;
		int count = mapper.delete(targetRno);
		log.info("DELETE COUNT: " + count);	
	}

	//@Test
	public void testUpdateReply() {
		long targetRno=2l;
		ReplyVO vo = mapper.read(targetRno);
		vo.setReply("Update Reply");
		int count = mapper.update(vo);
		log.info("update COUNT: " + count);	
	}
	
	//@Test
	public void testGetList() {
		long targetBno=1l;
		List<ReplyVO> replies = mapper.getList(targetBno);
		for(ReplyVO vo : replies) {
			log.info(vo);
		}
		//replies.forEach(reply -> log.info(reply));
	}
	
	
}
