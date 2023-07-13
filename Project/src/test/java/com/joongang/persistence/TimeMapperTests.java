/*package com.joongang.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joongang.domain.SeatVO;
import com.joongang.domain.TimeVO;
import com.joongang.mapper.TimeMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.joongang.config.RootConfig.class,
		com.joongang.config.SecurityConfig.class})
@Log4j2
public class TimeMapperTests {
	@Setter(onMethod_ =  @Autowired) 
	private TimeMapper mapper;
	
	//@Test
	public void testInsetTime() {
		TimeVO vo = new TimeVO(2, "9-10", "H");
		mapper.insert(vo);	
	}
}
*/