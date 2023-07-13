package com.joongang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joongang.domain.BoardAttachVO;
import com.joongang.domain.BoardVO;
import com.joongang.domain.Criteria;
import com.joongang.mapper.BoardAttachMapper;
import com.joongang.mapper.BoardMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BoardService {
	@Setter(onMethod_ =  @Autowired) 
	private BoardMapper mapper;
	
	@Setter(onMethod_ =  @Autowired) 
	private BoardAttachMapper attachMapper;
	
	@Transactional
	public void register(BoardVO vo) {
		mapper.insert(vo);
	
		List<BoardAttachVO> list = vo.getAttachList();
		if(list == null || list.isEmpty()) {
			return;
		}
		for(BoardAttachVO attach : list) {
			attach.setBno(vo.getBno());
			attachMapper.insert(attach);
		}
	}
	//수정은 기존에 있던 첨부파일 데이터 베이스에서 모두 삭제하고 새로 전달 받은 첨부파일을 데이터 베이스에 기록하는 것으로 처리(기존 파일 새파일 구분 불가)
	@Transactional
	public boolean modify(BoardVO board) {
		log.info("modify....." + board);
		attachMapper.deleteAll(board.getBno());
		boolean modifyResult = mapper.update(board) == 1;
		List<BoardAttachVO> list =board.getAttachList();
		if(modifyResult && list != null) {
			for(BoardAttachVO vo : list) {
				vo.setBno(board.getBno());
				attachMapper.insert(vo);
			}
		}
		return modifyResult;
	}
	
	public List<BoardAttachVO> getAttachList(Long bno){
		log.info("get Attach list by bno" + bno);
		return attachMapper.findByBno(bno);
	} 
	
	public List<BoardVO> getList(){
		return mapper.getList();
	}
	
	
	public BoardVO get(Long bno){
		return mapper.read(bno);
	}
	
	
	public boolean remove(Long bno) {
		return mapper.delete(bno) == 1;
	}

	
	
	public List<BoardVO> getList(Criteria criteria) {
		return mapper.getListWithPaging(criteria);
	}
	public int getTotal(Criteria criteria) {
		return mapper.getTotalCount(criteria);
	}
	
	
}
