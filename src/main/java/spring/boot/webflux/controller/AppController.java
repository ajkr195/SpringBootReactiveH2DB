package spring.boot.webflux.controller;

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

        // loads 1 and display 1, stream data, data driven mode.
        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
                new ReactiveDataDriverContextVariable(todoRepository.findAll(), 1);

        model.addAttribute("todos", reactiveDataDrivenMode);

        // classic, wait repository loaded all and display it.
        //model.addAttribute("todos", todoRepository.findAll());

        return "index";

    }
    
    @RequestMapping("/index2")
    public String index2Alternate(final Model model) {

        // loads 1 and display 1, stream data, data driven mode.
        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
                new ReactiveDataDriverContextVariable(todoAlternateRepo.findAll(), 1);

        model.addAttribute("todos", reactiveDataDrivenMode);

        // classic, wait repository loaded all and display it.
        //model.addAttribute("todos", todoRepository.findAll());

        return "index2";

    }
}
