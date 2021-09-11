package todo_list.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import todo_list.dao.TodoDao;
import todo_list.model.Item;

import java.sql.SQLException;


@Controller
@RequestMapping("/")
public class TodoController {

    Logger logger = Logger.getLogger(TodoController.class);
    private final TodoDao todoDao;

    @Autowired
    private TodoController(TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("itemstable", todoDao.findAll());
        return "index";
    }

    @PostMapping()
    public String create(@ModelAttribute("item") Item item, BindingResult bindingResult) {
        try {
             todoDao.add(item);
            if(bindingResult.hasErrors()) {
                return "index";
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return "create";
    }
}
