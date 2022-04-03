package com.wallet.repository;

import com.wallet.model.entity.Account;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByPlayerId(Integer playerId);

    @Query("SELECT (a.amount - :amountToWithdraw) >= 0 " +
            "FROM Account a " +
            "WHERE a.id = :accountId")
    boolean sufficientFundsToWithdraw(@Param("accountId") Integer accountId,
                                      @Param("amountToWithdraw") BigDecimal amountToWithdraw);

    @Query("SELECT a.amount < 0 " +
            "FROM Account a " +
            "WHERE a.id = :accountId")
    boolean isNegativeBalance(@Param("accountId") Integer accountId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Account a " +
            "SET a.amount = a.amount + :amount " +
            "WHERE a.id = :accountId")
    void addFunds(@Param("accountId") Integer accountId, @Param("amount") BigDecimal amount);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Account a " +
            "SET a.amount = a.amount - :amount " +
            "WHERE a.id = :accountId")
    void withdrawFunds(@Param("accountId") Integer accountId, @Param("amount") BigDecimal amount);
}
