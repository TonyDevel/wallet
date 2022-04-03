package com.wallet.controller;

import com.wallet.model.dto.transaction.TransactionDTO;
import com.wallet.model.dto.transaction.TransactionRequest;
import com.wallet.service.ITransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
@Tag(name = "Controller to manage transactions")
public class TransactionController {

    private final ITransactionService transactionService;

    @Operation(summary = "Create transaction",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = TransactionDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request")}
    )
    @PostMapping
    public TransactionDTO createTransaction(@RequestBody @Valid TransactionRequest request) {
        return transactionService.create(request);
    }

    @Operation(summary = "Get transaction history",
            responses =
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionDTO.class))))
    )
    @GetMapping
    public List<TransactionDTO> getTransactionHistory(@RequestParam("playerId") Integer playerId) {
        return transactionService.getTransactionHistory(playerId);
    }
}
