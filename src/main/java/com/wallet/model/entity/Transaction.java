package com.wallet.model.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
@Entity
public class Transaction {

    @Id
    private Integer id;

    @Column(nullable = false, name = "account_id")
    private Integer accountId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type")
    private TransactionType type;

    @Column(nullable = false, name = "amount")
    private BigDecimal amount;

}
