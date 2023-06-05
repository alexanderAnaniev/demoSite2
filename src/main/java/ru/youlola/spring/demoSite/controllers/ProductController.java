package ru.youlola.spring.demoSite.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.youlola.spring.demoSite.dto.ProductDTO;
import ru.youlola.spring.demoSite.models.Product;
import ru.youlola.spring.demoSite.services.ProductService;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String productsList(Model model){
        model.addAttribute("products", productService.findAll().stream().map(this::convertToProductDTO).collect(Collectors.toList()));
        System.out.println(productService.findAll().stream().map(this::convertToProductDTO).collect(Collectors.toList()));
        return "products";
    }

    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal){
        if (principal == null){
            return "redirect:/products";
        }
        productService.addToUserBucket(id,principal.getName());
        return "redirect:/products";
    }

    public ProductDTO convertToProductDTO(Product product){
        return this.modelMapper.map(product, ProductDTO.class);
    }
    public Product convertToProduct(ProductDTO productDTO){
        return  modelMapper.map(productDTO, Product.class);
    }
}
