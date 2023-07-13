package com.joongang.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joongang.domain.SeatVO;
import com.joongang.mapper.SeatMapper;

import lombok.Setter;
@Service
public class SeatService {
	@Setter(onMethod_ =  @Autowired) 
	private SeatMapper mapper;

	public List<SeatVO> getResState(int sno, Timestamp resdate) {
		SeatVO vo = new SeatVO();
		vo.setSno(sno);
		vo.setResdate(resdate);
		return mapper.getResState(vo);
	}
	@Transactional(rollbackFor = SQLException.class)
	public int reservation(List<SeatVO> list) throws SQLException { // 하나라도 정상적이지 않은 예약을 체크
		int result =0;
		for(SeatVO vo :list) {
			if (mapper.hasReserved(vo) != 0) {
				throw new SQLException();
			}
			mapper.insert(vo); // 정상적으로 된다면 예약테이블에 추가 
		}
		result = list.stream().iterator().next().getSno();
		return result;
	}
	public List<SeatVO> getResInfo(String userid, int sno, Timestamp resdate) {
		SeatVO vo = new SeatVO();
		vo.setUserid(userid);
		vo.setSno(sno);
		vo.setResdate(resdate);
		return mapper.getResInfo(vo);
	}
	public List<Integer> getSeatNo() {
		List<Integer> list = mapper.getSeatNo();
		return list;
	}
}
