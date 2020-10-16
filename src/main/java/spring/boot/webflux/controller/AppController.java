package spring.boot.webflux.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import spring.boot.webflux.repository.TodoAlternateRepo;
import spring.boot.webflux.repository.TodoRepository;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class AppController {
	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private TodoAlternateRepo todoAlternateRepo;

	@RequestMapping("/")
	public String index(final Model model) {

		// load and display 2 at a time - stream data - data driven mode.
		IReactiveDataDriverContextVariable reactiveDataDrivenMode = new ReactiveDataDriverContextVariable(
				todoRepository.findAll().delayElements(Duration.ofMillis(200)), 1);//.log(), 1);

		model.addAttribute("todos", reactiveDataDrivenMode);

		// classic, loaded all and display them.
		// model.addAttribute("todos", todoRepository.findAll());

		return "index";

	}

	@RequestMapping("/index2")
	public String index2Alternate(final Model model) {

		IReactiveDataDriverContextVariable reactiveDataDrivenMode = new ReactiveDataDriverContextVariable(
				todoAlternateRepo.findAll(), 1);

		model.addAttribute("todos", reactiveDataDrivenMode);
		return "index2";

	}
	
	@RequestMapping("/crud")
	public String crud(final Model model) {
		return "crud";

	}
	
	@RequestMapping("/indexlive")
	public String indexLive(final Model model) {
		return "indexlive";

	}
	
	@RequestMapping("/indexlive2")
	public String indexLive2(final Model model) {
		return "indexlive2";

	}
	
	@RequestMapping("/indexlive3")
	public String indexLive3(final Model model) {
		return "indexlive3";

	}
}
