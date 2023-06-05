package ru.youlola.spring.demoSite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.youlola.spring.demoSite.dto.BucketDTO;
import ru.youlola.spring.demoSite.models.Bucket;
import ru.youlola.spring.demoSite.models.Person;
import ru.youlola.spring.demoSite.repositories.PeopleRepository;
import ru.youlola.spring.demoSite.repositories.ProductRepository;
import ru.youlola.spring.demoSite.services.BucketService;
import ru.youlola.spring.demoSite.services.ProductService;

import java.security.Principal;

@Controller
@RequestMapping("/bucket")
public class BucketController {

    private final BucketService bucketService;
    private final PeopleRepository peopleRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public BucketController(BucketService bucketService,
                            PeopleRepository peopleRepository, ProductService productService,
                            ProductRepository productRepository) {
        this.bucketService = bucketService;
        this.peopleRepository = peopleRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @DeleteMapping("/{bucketId}/product/{productId}")
    public String deleteProductFromBucket (@PathVariable ("bucketId") Long buckedId,
                                           @PathVariable ("productId") Long productId){
        bucketService.deleteProduct(buckedId,productId);
        return "redirect:/bucket";
    }

    @GetMapping()
    public String aboutBasket(Model model, Principal principal){
        if (principal == null){
            model.addAttribute("bucket",new Bucket());
        }
        else {
            BucketDTO bucketDTO = bucketService.getBucketByPerson(principal.getName());
            model.addAttribute("bucket",bucketDTO);
        }
    return "bucket";
    }
}
