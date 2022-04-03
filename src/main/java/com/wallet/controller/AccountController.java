package com.wallet.controller;

import com.wallet.model.dto.account.AccountDTO;
import com.wallet.model.dto.account.RegisterAccountRequest;
import com.wallet.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/accounts")
@Tag(name = "Controller to manage account data")
public class AccountController {

    private final IAccountService accountService;

    @Operation(summary = "Register player account",
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = AccountDTO.class)))
    )
    @PostMapping
    public AccountDTO register(@RequestBody @Valid RegisterAccountRequest request) {
        return accountService.register(request);
    }

    @Operation(summary = "Get player account",
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = AccountDTO.class)))
    )
    @GetMapping
    public AccountDTO getPlayerAccount(@RequestParam("playerId") Integer playerId) {
        return accountService.getPlayerAccount(playerId);
    }
}
