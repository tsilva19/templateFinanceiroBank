package com.template.financeiroBank.controller;


import com.template.financeiroBank.model.Transacao;
import com.template.financeiroBank.service.FinanceiraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financeira")
@Tag(name = "financeira", description = "API para gerenciamento de transações financeiras")
public class FinanceiraController {

    @Autowired
    private FinanceiraService financeiraService;

    @Operation(summary = "Criação de transação")
    @PostMapping("/transacao")
    public Transacao createTransacao(@RequestBody Transacao transacao){
        return financeiraService.saveTransacao(transacao);
    }

    @Operation(summary = "Visualizar extrato")
    @GetMapping("/extrato")
    public List<Transacao> getExtrato(){
        return financeiraService.getExtrato();
    }

    @Operation(summary = "Visualizar Saldo")
    @GetMapping("/saldo")
    public Double getSaldo() {
        return financeiraService.getSaldo();
    }
}
