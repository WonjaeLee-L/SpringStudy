package www.silver.hom;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import www.silver.service.IF_BoardService;
import www.silver.vo.BoardVO;

@Controller
public class BoardController {

	@Inject
	IF_BoardService boardservice;

	@GetMapping(value = "board")
	// model을 쓴다 > 뷰에게 값을 넘기는구나
	// controller인데 string이므로 뷰가 string이구나
	public String board(Model model) throws Exception {
		// controller > service > dao > mapper
		// 서비스 layer에 전체글 서비스를 요청하고 결과를 리턴
		List<BoardVO> list = boardservice.boardList();
		// 리턴받은 list변수의 값을 모델 객체로 뷰에게 전송하는 코드(view에게 값을 넘기기 위해 model 객체 필요)

		model.addAttribute("list", list);
		// 변수 "list"를 넘긴다. "list"의 값은 list이다.
		// 뷰를 지정
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
//		return "board/bbs";
		// view로 가면 방금 입력한 값이 나오지 않는다.
		// model 객체로 받지 않아서 안된다.
		return "redirect:board";
		// view가 아니라 board로 돌아가는 것을 입력.
		// 방금 등록한 글을 바로 볼 수 있다.
	}

	// 삭제
	@GetMapping(value = "del")
	public String del(@RequestParam("delno") String delno) throws Exception {
		boardservice.deleteBoard(delno);
		return "redirect:board";
	}

}
