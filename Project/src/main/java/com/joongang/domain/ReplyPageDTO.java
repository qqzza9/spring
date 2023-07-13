package com.joongang.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyPageDTO {
	private int replyCnt;
	private List<ReplyVO> list;
}
