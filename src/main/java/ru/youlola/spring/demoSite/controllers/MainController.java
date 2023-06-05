package ru.youlola.spring.demoSite.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.youlola.spring.demoSite.services.ProductService;

@Controller
@RequestMapping({"", "/"})
public class MainController {
    ProductService productService;
    ModelMapper modelMapper;

    public String index() {
        return "index";
    }

    @GetMapping("/debetCards")
    public String debetCards() {
        return "debetCards";
    }

    @GetMapping("/creditCards")
    public String creditCards() {
        return "creditCards";
    }

    @GetMapping("/brokerBills")
    public String brokerBills() {
        return "brokerBills";
    }

    @Autowired
    public MainController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }
}




