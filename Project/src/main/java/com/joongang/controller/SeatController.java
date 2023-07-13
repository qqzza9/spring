package com.joongang.controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joongang.domain.AuthVO;
import com.joongang.domain.SeatVO;
import com.joongang.service.SeatService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@AllArgsConstructor
@RequestMapping("/seat/*")
public class SeatController {
	private SeatService service;
	
	@GetMapping(value= "/getResState",
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<SeatVO>> getResState(@RequestParam(value="sno") int sno,
			@RequestParam(value="resdate") Long resdate, HttpSession session) {
		Instant instant = Instant.ofEpochMilli(resdate);
		Timestamp ts = Timestamp.from(instant);
		List<SeatVO> list = service.getResState(sno, ts);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping(value= "/reservation", consumes ="application/json",
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Integer> reservation(@RequestBody List<SeatVO> list) {
		log.info("SeatVO: " + list);
		int seatNo = 0;
		try {
			seatNo = service.reservation(list);
		} catch (SQLException ex) {
		}
		return new ResponseEntity<>(seatNo, HttpStatus.OK);
	}
	@ResponseBody
	@GetMapping(value = "/getResInfo", 
			produces = {MediaType.APPLICATION_JSON_VALUE}) 
	public ResponseEntity<List<SeatVO>> getResInfo(@RequestParam(value="sno") int sno,
			@RequestParam(value="resdate") Long resdate, HttpSession session ) {
		Instant instant = Instant.ofEpochMilli(resdate);
		Timestamp ts = Timestamp.from(instant);
		AuthVO auth = (AuthVO)session.getAttribute("auth");
		List<SeatVO> list = service.getResInfo(auth.getUserid(), sno, ts);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	@ResponseBody
	@GetMapping(value="/getSeatNo", produces = {MediaType.APPLICATION_JSON_VALUE}) 
	public ResponseEntity<List<Integer>> getResState(HttpSession session) {
		List<Integer> list = service.getSeatNo();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	@GetMapping(value = "/main")
	public String home(Model model) {
		return "/seat/main";
	}
}
