package www.silver.service;

import www.silver.vo.MemberVO;

// 인터페이스 표현 네이밍 규칙 IF_로 정의
public interface IF_MemberService {
	// 서비스 작업을 메서드로 정의합니다. 기능만, 인터페이스로
	// 컨트롤러가 join 작업을 요청한다. 이 때, 매개변수로 객체의 주소를 전달한다.
	public void join(MemberVO membervo) throws Exception;
}
