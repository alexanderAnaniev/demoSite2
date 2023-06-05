package ru.youlola.spring.demoSite.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "sum")
    private BigDecimal sum;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderDetails> details;

}
