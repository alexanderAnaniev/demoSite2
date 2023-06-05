package ru.youlola.spring.demoSite.dto;

import lombok.*;
import ru.youlola.spring.demoSite.models.Product;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BucketDetailDTO {
        private String title;
        private Long productId;
        private BigDecimal price;
        private BigDecimal amount;
        private Double sum;

        private Long bucketId;
        private String link;



    public BucketDetailDTO(Product product,Long bucketId) {
        this.title = product.getTitle();
        this.productId = product.getId();
        this.price = product.getPrice();
        this.amount = new BigDecimal(1.0);
        this.sum = Double.valueOf(product.getPrice().toString());
        this.bucketId = bucketId;
        this.link = product.getLink();
    }


}
