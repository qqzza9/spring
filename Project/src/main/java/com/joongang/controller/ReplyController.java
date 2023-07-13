package com.joongang.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.joongang.domain.Criteria;
import com.joongang.domain.ReplyPageDTO;
import com.joongang.domain.ReplyVO;
import com.joongang.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/replies/*")
@AllArgsConstructor
@Log4j2
public class ReplyController {
	private ReplyService service;
	
	@GetMapping(value ="/pages/{bno}",
			produces = {MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_ATOM_XML_VALUE})
	public ResponseEntity<List<ReplyVO>> getList(@PathVariable("bno") Long bno){
		List<ReplyVO> list = service.getList(bno);
		log.info("list:" + list);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping(value = "/new", consumes = "application/json",
			produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> register(@RequestBody ReplyVO vo ){
		log.info("ReplyVO: " +vo);
		int registerCount = service.register(vo);
		log.info("Reply RESISTER COUNT: " + registerCount);
		return registerCount == 1 
				? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value ="/{rno}",
			produces = {MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_ATOM_XML_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		log.info("get:" + rno);
	return new ResponseEntity<>(service.get(rno),HttpStatus.OK);
	}
	
	@PatchMapping(value ="/{rno}", consumes = "application/json",
			produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo ){
		log.info("ReplyVO: " +vo);
		int modifyCount = service.modify(vo);
		log.info("Reply RESISTER COUNT: " + modifyCount);
		return modifyCount == 1 
				? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(value ="/{rno}",
			produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno ){
		log.info("remove: " + rno);
		return service.remove(rno) == 1 
				? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	@GetMapping(value="/pages/{bno}/{page}",
			produces = {MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_ATOM_XML_VALUE })
	public ResponseEntity<ReplyPageDTO> getListWithPaging(
			@PathVariable("bno") Long bno, @PathVariable("page") int page) {
		Criteria criteria = new Criteria(page, 10);
		ReplyPageDTO dto = service.getListWithPaging(criteria, bno);
		log.info("list:" + dto.getList());
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	//댓글개수표시
	@GetMapping(value="/cnt",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getReplyCnt( //ResponseEntity안에 String 형태의 json 넣기
			@RequestParam(value="list[]") List<Long> list) { //전달받은 list 번호를 가지고 
		JsonObject jObj = new JsonObject();
		for(Long bno: list) {
			jObj.addProperty(String.valueOf(bno), service.getReplyCnt(bno));
		}
		return new ResponseEntity<>(jObj.toString(), HttpStatus.OK);
	}
}
