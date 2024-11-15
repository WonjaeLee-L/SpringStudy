package www.silver.hom;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import www.silver.service.IF_BoardService;
import www.silver.util.FileDataUtil;
import www.silver.vo.BoardVO;
import www.silver.vo.PageVO;

@Controller
public class BoardController {

	@Inject
	IF_BoardService boardservice;
	
	@Inject
	FileDataUtil filedatautil;

	@GetMapping(value = "board")
	// model을 쓴다 > 뷰에게 값을 넘기는구나
	// controller인데 string이므로 뷰가 string이구나
	public String board(Model model, @ModelAttribute PageVO pagevo) throws Exception {
		// controller > service > dao > mapper

		// parameter가 없으면, pagevo객체는 만들어 졌지만 page가 null 인 상태.
		if (pagevo.getPage() == null) {
			pagevo.setPage(1);
		}
		// 3가지 정보만 있으면 페이지 계산이 가능
		// 1. 현재 페이지, 2. 페이지당 게시물 수, 3. 전체 페이지 수
		// 3 >> 전체 페이지 수 
//		pagevo.setTotalCount(348);
		// 직접 넣지 말고 DB에서 가져오기
		pagevo.setTotalCount(boardservice.totalCountBoard());
		// 확인용
//		System.out.println(pagevo.getStartNo()+"시작 글 번호");
//		System.out.println(pagevo.getEndNo()+"마지막 글 번호");
//		System.out.println(pagevo.getStartPage()+"페이지그룹 시작 번호");
//		System.out.println(pagevo.getEndPage()+"페이지그룹 마지막 번호");
//		

		// 서비스 layer에 전체글 서비스를 요청하고 결과를 리턴
		List<BoardVO> list = boardservice.boardList(pagevo);
		// 리턴받은 list변수의 값을 모델 객체로 뷰에게 전송하는 코드(view에게 값을 넘기기 위해 model 객체 필요)

		model.addAttribute("list", list);
		// 변수 "list"를 넘긴다. "list"의 값은 list이다.
		// 뷰를 지정
		return "board/bbs";
		// view로 list를 넘겼기 때문에, bbs.jsp 안에서 list 변수를 가져 올 수 있다.

	}

	@GetMapping(value = "bwr")
	public String bwr() throws Exception {
		return "board/bbswr";
	}

//		System.out.println(boardvo.toString());
	// client에게 받은 data를 boardservice에 넘긴다.
	@PostMapping(value = "bwrdo")
	public String bwrdo(@ModelAttribute BoardVO boardvo, MultipartFile[] file) throws Exception {
		
		// 업로드 되는지 확인하는 중간 코드
//		System.out.println(file.length);
//		for(int i=0;i<file.length;i++) {
			// file[i].get 해서 이거저거 다 뜯어보기
			// 실제 파일 이름 >> getOriginalFilename()
//			System.out.println(file[i].getOriginalFilename());
//		}
		
		
		String[] newFileName = filedatautil.fileUpload(file);
//		System.out.println(newFileName);
		
		boardvo.setFilename(newFileName);
		// 얘를 건드리면 뒤에 다 수정해야함, vo에 변수를 추가해서 리턴받은 파일명도 함께 보낼 수 있도록 한다.(위와 같이 setFilename을 한다)
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

	// 수정(update), value 같지만 method 달라서 가능
	@PostMapping(value = "mod")
	public String modsave(@ModelAttribute BoardVO boardvo) throws Exception {
//		System.out.println(bvo.getTitle());
		boardservice.modBoard(boardvo);
		return "redirect:board";
	}

	// 수정
	@GetMapping(value = "mod")
	public String mod(@RequestParam("modno") String modno, Model model) throws Exception {

		BoardVO bvo = boardservice.modBoard(modno);
//		System.out.println(bvo.getTitle());
		// sysout은 서버 부하가 걸리는 작업이다.
		// 테스트 후, 삭제하거나 주석 처리 한다.
		// 실제로 sysout은 사용하지 않는다.
		// 테스트 위해서는 junit test라는 도구를 사용한다.
		// 또 기록을 남기기 위해서는 로그라는 기능을 사용한다.
		// 로그는 홈 컨트롤러에 가면 볼 수 있다.

		// model을 통해 view에게 넘김 이 때, 변수명이 boardvo
		model.addAttribute("boardvo", bvo);
		return "board/modform";
		// view이름에 .jsp를 붙이지 않아도 뷰리졸브가 붙여준다.
	}
	
	@GetMapping(value="view")
	public String boardView(@RequestParam("no") String no, Model model) throws Exception {
		BoardVO boardvo = boardservice.getBoard(no);
		
		// attach 가져오기
		List<String> attachList = boardservice.getAttach(no);
		// view에게 전송한 값들. 게시글과 첨부파일 리스트
		model.addAttribute("boardvo", boardvo);
		model.addAttribute("attachList", attachList);
		
		
		return "board/dview";
	}

}
