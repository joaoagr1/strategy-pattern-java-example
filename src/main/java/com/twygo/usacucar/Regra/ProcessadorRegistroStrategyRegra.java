package com.twygo.usacucar.Regra;

import com.twygo.usacucar.Entidades.FilaProcessamento;

import com.twygo.usacucar.Interfaces.StrategyInterface;
import com.twygo.usacucar.Interfaces.StrategyFactoryInterface;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class ProcessadorRegistroStrategyRegra {

    @Autowired
    private StrategyFactoryInterface strategyFactoryInterface;

    @SneakyThrows
    public void processarRegistro(FilaProcessamento registro) {
        StrategyInterface strategyInterface = strategyFactoryInterface.createStrategy(registro.getTp_acao());
        strategyInterface.executarAcao(registro);
    }
}
