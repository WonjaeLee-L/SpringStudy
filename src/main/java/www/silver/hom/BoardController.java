package www.silver.hom;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import www.silver.service.IF_BoardService;
import www.silver.vo.BoardVO;

@Controller
public class BoardController {

	@Inject
	IF_BoardService boardservice;

	@GetMapping(value = "board")
	public String board() throws Exception {
		// controller > service > dao > mapper
		// 전체 게시글을 가져오는 작업이 필요
		return "board/bbs";
	}

	@GetMapping(value = "bwr")
	public String bwr() throws Exception {
		return "board/bbswr";
	}

//		System.out.println(boardvo.toString());
	// client에게 받은 data를 boardservice에 넘긴다.
	@PostMapping(value = "bwrdo")
	public String bwrdo(@ModelAttribute BoardVO boardvo) throws Exception {
		boardservice.addBoard(boardvo);
		return "board/bbs";
	}
}
