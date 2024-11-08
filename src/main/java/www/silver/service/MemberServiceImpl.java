package www.silver.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import www.silver.dao.IF_MemberDao;
import www.silver.vo.MemberVO;

// Impl > implement 받은 표시

@Service // 해당 클래스를 객체로 만들어라. @Service는 서비스의 역할하는 객체. 이 클래스가 객체로 만들어져야 아래에서 Inject가 가능하다.
public class MemberServiceImpl implements IF_MemberService {
	@Inject
	IF_MemberDao memberdao; // 이객체의 타입은 MemberDaoImpl

	@Override
	public void join(MemberVO membervo) throws Exception {
		// 이게 서비스단이고, 이 메서드를 컨트롤러가 호출한다. 얘는 dao를 호출해야하므로 위의 memberdao 객체를 만든 것.
		System.out.println("join 서비스");
		// dao에게 작업 지시해야 한다. 코드를 여기에 쓴다.
		// db작업하는애가 dao니까 dao에 db작업 코드를 쓴다. 얘가 dao에게 넘겨야하므로
		// dao객체를 만든 것이다.
		// spring에서는 1:1 구현. interface가 한 개 당, 한 클래스만 구현 받을 수 있다.
		// (그래서 이 클래스의 이름은 중요하지 않다. 어차피 한 개만 구현받으므로 어떤걸 구현받는지가 중요하다.)

		// 여기서는 아이디 검사 등 중복체크 할 수 있다. <생략>

		// 실제 데이터를 저장하도록 지시한다.
		// DB작업 필요하므로 dao타입 객체 생성.
		memberdao.insertOne(membervo);

	}

}
