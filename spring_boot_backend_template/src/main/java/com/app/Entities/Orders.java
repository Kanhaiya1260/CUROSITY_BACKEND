package com.app.Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
@ToString
public class Orders {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="order_id")
   private Long orderId;
   @Column(name = "order_date",nullable = false)
   private LocalDate orderDate = LocalDate.now();
   @Column(name = "delhivery_date",nullable = false)
   private LocalDateTime dehiveryDate = LocalDateTime.now().plusDays(7);
   @NotNull
   private Integer quantity;
   boolean Status = false;
   @ManyToOne
   @JoinColumn(name="aid")
   private Address delhiveryAddress;
   @OneToOne
   @JoinColumn(name="pid",nullable = false)
   private Product product;
   @OneToOne
   @JoinColumn(name="uid",nullable = false)
   private User user; 
}
