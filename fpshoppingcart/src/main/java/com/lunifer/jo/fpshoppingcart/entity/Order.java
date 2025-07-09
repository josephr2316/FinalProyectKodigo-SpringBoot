package com.lunifer.jo.fpshoppingcart.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable=false)
    private Long orderId;

    //@ManyToMany(fetch = FetchType.LAZY)
    //@JoinTable(
    //        name = "orders_product",
    //        joinColumns =@JoinColumn(name = "orders_id"),
    //        inverseJoinColumns = @JoinColumn(name = "product_id")
    //)
    //private Set<Product> productList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;


    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "order_status")
    private OrderStatus status;





}
