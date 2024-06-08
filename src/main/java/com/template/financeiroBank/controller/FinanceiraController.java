package com.template.financeiroBank.controller;


import com.template.financeiroBank.model.Transacao;
import com.template.financeiroBank.service.FinanceiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financeira")
public class FinanceiraController {

    @Autowired
    private FinanceiraService financeiraService;

    @PostMapping("/transacao")
    public Transacao createTransacao(@RequestBody Transacao transacao){
        return financeiraService.saveTransacao(transacao);
    }

    @GetMapping("/extrato")
    public List<Transacao> getExtrato(){
        return financeiraService.getExtrato();
    }

    @GetMapping("/saldo")
    public Double getSaldo() {
        return financeiraService.getSaldo();
    }
}
