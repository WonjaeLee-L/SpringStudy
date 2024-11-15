package www.silver.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
	// 인터셉터 기능 사용하여 로그인 구현. 그 중 preHandle 메서드를 사용한다.
	@Override // 컨트롤러 전에 인터셉터 하겠다.
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle에 진입");
		// 로그인 판단 유무 비지니스 서비스 처리
		// 세션을 가져와서 세션이 있다면 로그인 한 것.
		// 없다면 로그인을 안했으니 메인화면으로 리턴
		HttpSession session = request.getSession();
		Object nowid = session.getAttribute("id");
		if(nowid == null) {
			response.sendRedirect(request.getContextPath()+"/");
			return false;
		}
		// return super.preHandle(request, response, handler);
		return true;
	}

	@Override // 컨트롤러 빠져나온 이후 인터셉터 하겠다.
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

}
