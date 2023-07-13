package com.joongang.domain;

import lombok.Data;

@Data
public class PageDTO {
	private static final int pp1=10;
	private int startPage; //시작페이지
	private int endPage; //마지막페이지
	private boolean prev, next; //이전페이지, 다음 페이지 존재유무
	
	private int total; //전체 게시물 수 
	private Criteria criteria; //현재 페이지, 페이지당 게시물 표시수 정보
	
	public PageDTO(Criteria criteria, int total) {
		this.criteria = criteria;
		this.total = total;
		
		this.endPage = (int) (Math.ceil(criteria.getPageNum() / (float)(pp1*1.0))) *pp1;
		this.startPage = this.endPage - (pp1-1);
		int realEnd = (int) (Math.ceil((total * 1.0) / criteria.getAmount()));
		if(realEnd < this.endPage){
			this.endPage = realEnd;
		}
		this.prev = this.startPage>1;
		this.next = this.endPage<realEnd;
	}
}
