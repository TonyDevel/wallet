package com.wallet.repository;

import com.wallet.model.entity.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("select t from Transaction t " +
            "join Account acc on t.accountId = acc.id " +
            "where acc.playerId = :playerId " +
            "order by t.id")
    List<Transaction> findAllByPlayerId(@Param("playerId") Integer playerId);

}
