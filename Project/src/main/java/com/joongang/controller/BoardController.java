package com.joongang.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.joongang.domain.BoardAttachVO;
import com.joongang.domain.BoardVO;
import com.joongang.domain.Criteria;
import com.joongang.domain.PageDTO;
import com.joongang.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/board/*")
public class BoardController {
	@Setter(onMethod_=@Autowired) // 자동으로 연결해서 사용할수있도록
	private BoardService boardService; //BoardService 사용하려고 @Autowired
	
	@GetMapping("/register")
	public String registerForm() {
		return "/board/registerForm";
	}
	@PostMapping("/register")
	public String register(BoardVO vo,RedirectAttributes rttr) {
		boardService.register(vo);
		rttr.addFlashAttribute("result", vo.getBno());
		return "redirect:/board/list";
	}
	@GetMapping("/list")
	public String list(Criteria criteria, Model model) {
		List<BoardVO> list = boardService.getList(criteria); //페이지 관련 코드 추가함
		model.addAttribute("list", list);
		int total = boardService.getTotal(criteria); // 전체페이지 불러오기
		model.addAttribute("pageDTO", new PageDTO(criteria, total));
		log.info(list + "total: " + total);
		return "/board/list";
	}
	
	@GetMapping("/get")
	public String get(@RequestParam("bno")Long bno, Criteria criteria, Model model) {
		model.addAttribute("board", boardService.get(bno));
		return "/board/get";
	}
	
	@GetMapping("/modify")
	public String modify(@RequestParam("bno")Long bno, Criteria criteria, Model model) {
		model.addAttribute("board", boardService.get(bno));
		return "/board/modify";
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO board, Criteria criteria, RedirectAttributes rttr) {
		if(	boardService.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list" + criteria.getListLink();
	}
	/*
	@PostMapping("/remove")
	public String remove(@RequestParam("bno")Long bno,Criteria criteria, RedirectAttributes rttr) {
		if(	boardService.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list" + criteria.getListLink();
	}*/
	@GetMapping(value = "/getAttachList/{bno}", 
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(
			@PathVariable("bno") Long bno){
		log.info("getAttachList " + bno);
		return new ResponseEntity<>(boardService.getAttachList(bno), HttpStatus.OK);
	}
	//첨부파일 표시
   @GetMapping(value="/getAttachListOnList", 
		   produces=MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> getAttachListOnList(
		   @RequestParam(value="list[]") List<Long> list) {
      log.info("getAttachListOnList " + list.stream().collect(Collectors.toList()));
      Map<Long, List<BoardAttachVO>> map = new HashMap<Long, List<BoardAttachVO>>();
      for(Long bno : list) {
         map.put(bno, boardService.getAttachList(bno));
      }
      String gson = new Gson().toJson(map, HashMap.class);
      return new ResponseEntity<>(gson, HttpStatus.OK);
   }
   //삭제
   @PostMapping("/remove")
	public String remove(@RequestParam("bno")Long bno,Criteria criteria,
			RedirectAttributes rttr) {
	   List<BoardAttachVO> attachList = boardService.getAttachList(bno);
	   if(boardService.remove(bno)) {
		   deleteFiles(attachList);
		   rttr.addFlashAttribute("result", "success");
		}
	   /*
	   rttr.addAttribute("pageNum", criteria.getPageNum());
	   rttr.addAttribute("amount",  criteria.getAmount());
	   return "redirect:/board?list";
	   */
	   return "redirect:/board/list" + criteria.getListLink();
   }
   private void deleteFiles(List<BoardAttachVO> attachList) {
	   if(attachList == null || attachList.size() == 0) {
		   return;
	   }
	   for(BoardAttachVO attachVO : attachList) {
		   try {
			   Path file = Paths.get("C:\\upload\\"+attachVO.getUploadpath()+
					   "\\" + attachVO.getUuid()+"_"+attachVO.getFilename());
				Files.deleteIfExists(file);
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get("c:\\upload\\"+attachVO.getUploadpath() +
							"\\s_" + attachVO.getUuid()+ "_"+ attachVO.getFilename());
					Files.delete(thumbNail);
				}
		   } catch(Exception e) {
			   log.error("delete file error" + e.getMessage());
		   }   
	   }   
   }
}
