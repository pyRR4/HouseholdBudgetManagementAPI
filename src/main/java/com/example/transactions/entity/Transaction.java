package com.example.transactions.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "transactions"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
@ToString
public class Transaction {

    @Id
    @Column(nullable = false, columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;
}
