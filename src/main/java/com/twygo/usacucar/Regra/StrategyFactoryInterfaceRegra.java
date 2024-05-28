package com.twygo.usacucar.Regra;

import com.twygo.usacucar.Estrategias.CriarUsuarioStrategyInterface;
import com.twygo.usacucar.Estrategias.InscreverUsuarioCursoStrategyInterface;
import com.twygo.usacucar.Estrategias.MostrarStatusCursoStrategyInterface;
import com.twygo.usacucar.Interfaces.StrategyInterface;
import com.twygo.usacucar.Interfaces.StrategyFactoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StrategyFactoryInterfaceRegra implements StrategyFactoryInterface {

    @Autowired
    private CriarUsuarioStrategyInterface criarUsuarioStrategy;

    @Autowired
    private InscreverUsuarioCursoStrategyInterface inscreverUsuarioCursoStrategy;

    @Autowired
    private MostrarStatusCursoStrategyInterface mostrarStatusCursoStrategy;

    @Override
    public StrategyInterface createStrategy(String type) {
        switch (type) {
            case "1":
                return criarUsuarioStrategy;
            case "6":
                return inscreverUsuarioCursoStrategy;
            case "7":
                return mostrarStatusCursoStrategy;
            default:
                throw new IllegalArgumentException("Tipo de ação inválido: " + type);
        }
    }
}

