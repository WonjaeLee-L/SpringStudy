package www.silver.hom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@GetMapping("logout")
	public String logout(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		session.invalidate(); // 세션을 무력화 >> 로그아웃
		return "redirect:/";
	}
	
	@PostMapping("login")
	public String login(@RequestParam("id") String id, @RequestParam("pass") String pass, HttpServletRequest request)
			throws Exception {
		// 클라이언트가 전송한 id와 pass가 DB에 있는지 확인
		// 현재는 세션과 인터셉터가 목적이라서 윗 부분은 생략하고 진행

		// 아래 코드는 데이터베이스에서 가져온 값이 있을 경우에만 실행되어야 한다. 로그인 됐다고 가정하고 이 코드를 실행한다.
		if (true) {
			// request의 세션을 가져온다. 객체 발생한 request에서 session공간을 가져온 것이다.
			HttpSession session = request.getSession();
			// 가져온 세션에 설정된 id변수의 값을 가져온다. 타입을 몰라서 object로 받았다.
			Object nowid = session.getAttribute("id");
			// 만약 가져온 값이 있다면
			if (nowid != null) {
				// 기존의 세션값의 id변수의 값을 제거한다.
				session.removeAttribute("id");
			}
			// 세션에 id변수를 저장한다.
			session.setAttribute("id", id);
		}

		return "redirect:/";
	}
}
