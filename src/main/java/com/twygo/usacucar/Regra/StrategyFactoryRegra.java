package com.twygo.usacucar.Regra;

import com.twygo.usacucar.Estrategias.CriarUsuarioStrategy;
import com.twygo.usacucar.Estrategias.InscreverUsuarioCursoStrategy;
import com.twygo.usacucar.Estrategias.MostrarStatusCursoStrategy;
import com.twygo.usacucar.Interfaces.Strategy;
import com.twygo.usacucar.Interfaces.StrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StrategyFactoryRegra implements StrategyFactory {

    @Autowired
    private CriarUsuarioStrategy criarUsuarioStrategy;

    @Autowired
    private InscreverUsuarioCursoStrategy inscreverUsuarioCursoStrategy;

    @Autowired
    private MostrarStatusCursoStrategy mostrarStatusCursoStrategy;

    @Override
    public Strategy createStrategy(String type) {
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

