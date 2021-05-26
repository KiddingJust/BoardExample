package org.gaiga.controller;

import java.util.List;

import org.gaiga.dto.BoardDto;
import org.gaiga.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {
	
	private BoardService boardService;
	
	//이거 대신에 class의 어노테이션에 @AllArgsConstructor를 해주어도 됨. 
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	//@RequestMapping(value="/", method=RequestMethod.GET)
	//Model을 통해 View에 데이터를 전달할 것. 
//	@GetMapping("/")
//	public String list(Model model) {
//		List<BoardDto> boardDtoList = boardService.getBoardList();
//		model.addAttribute("boardList", boardDtoList);
//		
//		return "board/list.html";
//	}
	
	//페이징 처리 이후의 홈화면 
	//page 이름으로 넘어오면 파라미터를 받아주고, 없으면 기본값으로 1을 설정 
	@GetMapping("/")
	public String list(Model model, @RequestParam(value="page", defaultValue="1") Integer pageNum) {
		List<BoardDto> boardDtoList = boardService.getBoardList(pageNum);
		Integer[] pageList = boardService.getPageList(pageNum);
		
		model.addAttribute("boardList", boardDtoList);
		model.addAttribute("pageList", pageList);

		return "board/list.html";
	}
	
	@GetMapping("/post")
	public String write() {
		return "board/write.html";
	}
	
	@PostMapping("/post")
	public String write(BoardDto boardDto) {
		boardService.savePost(boardDto);
		return "redirect:/";
	}
	
	//PathVariable을 통해 요청에 오는 id값을 getPost의 인자로 전달 
	@GetMapping("/post/{no}")
	public String detail(@PathVariable("no") Long id, Model model) {
		BoardDto boardDto = boardService.getPost(id);
		
		model.addAttribute("boardDto", boardDto);
		return "board/detail.html";
	}
	
	@GetMapping("/post/edit/{no}")
	public String edit(@PathVariable("no") Long id, Model model) {
		BoardDto boardDto = boardService.getPost(id);
		model.addAttribute("boardDto", boardDto);
		return "board/update.html";
	}
	
	@PutMapping("/post/edit/{no}")
	public String update(BoardDto boardDto) {
		boardService.savePost(boardDto);
		return "redirect:/";
	}
	
	@DeleteMapping("/post/{no}")
	public String delete(@PathVariable("no") Long id) {
		boardService.deletePost(id);
		return "redirect:/";
	}
	
	@GetMapping("/board/search")
	public String search(@RequestParam(value="keyword") String keyword, Model model) {
		List<BoardDto> boardDtoList = boardService.searchPosts(keyword);
		model.addAttribute("boardList", boardDtoList);
		return "board/list.html";
	}
}
