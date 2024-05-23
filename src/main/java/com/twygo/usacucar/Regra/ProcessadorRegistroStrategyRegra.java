package com.twygo.usacucar.Regra;

import com.twygo.usacucar.Entidades.FilaProcessamento;

import com.twygo.usacucar.Interfaces.Strategy;
import com.twygo.usacucar.Interfaces.StrategyFactory;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class ProcessadorRegistroStrategyRegra {

    @Autowired
    private StrategyFactory strategyFactory;

    @SneakyThrows
    public void processarRegistro(FilaProcessamento registro) {
        Strategy strategy = strategyFactory.createStrategy(registro.getTp_acao());
        strategy.executarAcao(registro);
    }
}
