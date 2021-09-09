package todo_list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import todo_list.dao.TodoDao;

@Controller
@RequestMapping("/")
public class TodoController {

    private final TodoDao todoDao;

    @Autowired
    private TodoController(TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("item", todoDao.findAll());
        return "/index";
    }
}
