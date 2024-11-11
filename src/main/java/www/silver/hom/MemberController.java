package www.silver.hom;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import www.silver.service.IF_MemberService;
import www.silver.vo.MemberVO;

@Controller
public class MemberController {

	// 서비스로직을 실행할 수 있는 변수를 선언한다.
	// 선언 시, 객체의 주소가 필요하다. Spring에서는 객체의 주소를 주입받기에 주입한다.(@Inject)
	@Inject // 주입 받기 위해서는 아래의 객체가 컨트롤러에 있어야 한다.
	// inject가 있기 때문에 (컨테이너에 등록되어있는)객체의 주소를 가지고 온다.
	IF_MemberService memberservice;
	// 이 인터페이스에 해당되는 객체는 무조건 한 개.(1:1 구현이므로.. 구현이 중복으로 된다면, 아래 클래스가 여러개라 객체도 여러 개 였을
	// 것이다.)
	// 그래서 위의 코드가 나올 수 있다. IF_MemberService의 객체 memberservice를 만든다.
	// table이 열개라면, 인터페이스가 열개라고 생각

	@PostMapping(value = "join") // home에서 form에 join value여서, 이것도 join
	public String join(@ModelAttribute MemberVO membervo) throws Exception {
		// VO로 파라미터 받는다. silver.vo package 만든다.

		// 클라이언트가 보낸게 컨트롤러로 온다. 객체(membervo)로 받는다.
		// 클라이언트가 요청한게 비즈니스로직이 필요해서, 서비스단에게 요청. 요청하려면 서비스 객체가 필요해서
		// 맨위에서 참조변수를 만들었다. 저 만든 memberservice로 서비스단에 준다. 서비스는 인터페이스로 만들어놓고 구현받아서 세부적으로
		// 코딩한다.

		// MemberVO로 받겠다.
		System.out.println(membervo.toString());
		// 회원가입 전송하면 console에 출력하게 됨.

		// 클라이언트(home.jsp)로부터 받은 걸 비지니스로직을 서비스단에게 요청..(www.silver.service에서 서비스)
		memberservice.join(membervo);

		return "home";

		// 디버깅
		// 1. 클라이언트 -> 컨트롤러 전송
		// 2. 컨트롤러 -> 서비스
		// 3. 서비스 -> Dao
		// 4. Dao - sqlSession 매핑(mapper 포함)

	}
}
