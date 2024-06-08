package com.template.financeiroBank.service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.template.financeiroBank.model.TipoTransacao;
import com.template.financeiroBank.model.Transacao;
import com.template.financeiroBank.repository.TransacaoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FinanceiraService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    private AmazonS3 s3Client;

    @PostConstruct
    public void init(){
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localstack:4566", "us-west-2"))
                .withPathStyleAccessEnabled(true)
                .build();
    }

    public Transacao saveTransacao(Transacao transacao){
        transacao.setDataHora(LocalDateTime.now());
        Transacao savedTransacao = transacaoRepository.save(transacao);
        String bucketName = "transacoes";
        String fileName = transacao.getTipo().name().toLowerCase() + "/" + savedTransacao.getId() + ".txt";
        s3Client.putObject(bucketName, fileName, savedTransacao.toString());

        return savedTransacao;
    }

    public List<Transacao> getExtrato() {
        return transacaoRepository.findAll();
    }

    public Double getSaldo() {
        return transacaoRepository.findAll().stream().mapToDouble(t -> {
            if (t.getTipo() == TipoTransacao.DEPOSITO || t.getTipo() == TipoTransacao.RECEBIMENTO_PIX) {
                return t.getValor();
            } else {
                return -t.getValor();
            }
        }).sum();
    }
}
