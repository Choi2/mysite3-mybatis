package com.cafe24.mysite.common;

import org.springframework.beans.factory.annotation.Autowired;

import com.cafe24.mysite.service.BoardService;
import com.cafe24.web.constant.ConstantVariables;

public class Pager {
	private int page;
	private int startGroupPage;
	private int startPage;
	private int endPage;
	private String word;
	
	private int currentDataSizePerPage;
	private int currentGroupPage;
	
	private int boardSize;
	
	@Autowired 
	private BoardService boardService;
		
	public void calculatePage(int page, String word) {
		
		this.page = page;
		this.word = word;
		
		this.currentDataSizePerPage = page * ConstantVariables.PAGE_SIZE;
		this.currentGroupPage = page / ConstantVariables.PAGE_SIZE;
		
		if (this.word != null && !this.word.equals("")) {
			this.word = "%" + this.word + "%";
		} else {
			this.word = null;
		}
			
		this.boardSize = boardService.getCurrentGroupPage(currentGroupPage * ConstantVariables.GROUP_SIZE,  word);
		
		this.startGroupPage = currentGroupPage;
		this.startPage = (currentGroupPage * ConstantVariables.PAGE_SIZE) + 1;
		this.endPage = this.startPage + (boardSize / ConstantVariables.PAGE_SIZE);
		this.endPage = (boardSize % ConstantVariables.PAGE_SIZE == 0) ? this.endPage - 1:  this.endPage; 
		if(boardSize > ConstantVariables.GROUP_SIZE) {
			this.endPage -= 1;
		}
		if(endPage < startPage) {
			endPage = startPage;
		}
	}



	public String getWord() {
		return word;
	}


	public void setWord(String word) {
		this.word = word;
	}

	public int getCurrentDataSizePerPage() {
		return currentDataSizePerPage;
	}

	public void setCurrentDataSizePerPage(int currentDataSizePerPage) {
		this.currentDataSizePerPage = currentDataSizePerPage;
	}


	public int getPage() {
		return page;
	}



	public void setPage(int page) {
		this.page = page;
	}



	public int getStartGroupPage() {
		return startGroupPage;
	}



	public void setStartGroupPage(int startGroupPage) {
		this.startGroupPage = startGroupPage;
	}



	public int getStartPage() {
		return startPage;
	}



	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}



	public int getEndPage() {
		return endPage;
	}



	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}



	public int getCurrentGroupPage() {
		return currentGroupPage;
	}



	public void setCurrentGroupPage(int currentGroupPage) {
		this.currentGroupPage = currentGroupPage;
	}



	public int getBoardSize() {
		return boardSize;
	}



	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}



	public BoardService getBoardService() {
		return boardService;
	}



	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}



	@Override
	public String toString() {
		return "Pager [page=" + page + ", startGroupPage=" + startGroupPage + ", startPage=" + startPage + ", endPage="
				+ endPage + ", word=" + word + "]";
	}

}
