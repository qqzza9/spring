package com.joongang.mapper;

import java.util.List;

import com.joongang.domain.SeatVO;

public interface SeatMapper {
	public List<Integer> getSeatNo();
	public List<SeatVO> getResState(SeatVO vo);
	public int insert(SeatVO vo);
	public int hasReserved(SeatVO vo);
	public List<SeatVO> getResInfo(SeatVO vo);

}
