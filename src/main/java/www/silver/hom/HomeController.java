package www.silver.hom;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * aaa Handles requests for the application home page.
 */
@Controller // 컨트롤러임을 알려줌
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	// 클라인트가 요청함을 의미(RequestMapping)
	// value가 클라인트가 요청하는 서비스의 이름
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);
		// 응답하는 템플릿
		return "home";
	}

	// https://bubblecastle.tistory.com/9

	@RequestMapping(value = "timeline", method = RequestMethod.GET)
	public String timeline() {
		return "timeline";
	}

	@RequestMapping(value = "viewMessage", method = RequestMethod.POST)
	public String viewMessage(@RequestParam("time") String t, @RequestParam("name") String n, Model m) {
		// 똑같이 메서드명만 바꾸고 method 방식을 바꾸어 세팅해 놓는다.
		m.addAttribute("변수명", "값");
		m.addAttribute("time", t);
		m.addAttribute("name", n);
		m.addAttribute("age", 100);
		return "viewMsg";
	}

	@RequestMapping(value = "viewMessage", method = RequestMethod.GET)
	public String viewMessage1(@RequestParam("time") String t, @RequestParam("name") String n, Model m) {
		// view에게 값을 넘겨줄 때, 사용되는 객체 Model m, 직접 만들지 않아도 자동으로 만들어 준다.
		// 얘를 적지 않으면 아래처럼 객체를 따로 만들어서 사용할 수 있다.

		// model 역할과 view 역할을 다 할 수 있는 객체. 직접 만드는 객체
//		ModelAndView mm = new ModelAndView();

		// 디버깅 - 확인용
//		System.out.println(t+"/"+n);
		m.addAttribute("변수명", "값");
		// time이라는 변수에 내가 가진 t 값을 넘겨줄게(type은 자동으로 맞춰진다.)
		m.addAttribute("time", t);
		// name이라는 변수에 내가 가진 n 값을 넘겨줄게(type은 자동으로 맞춰진다.)
		m.addAttribute("name", n);
		// type은 신경 쓰지 않는다. 객체도 자동으로 들어갈 수 있다.
		m.addAttribute("age", 100);
		// 여기까지가 view가 변수를 받은 상태. 이제 출력을 하도록 viewMsg로 간다.
		return "viewMsg";
	}

}
