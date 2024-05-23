package com.twygo.usacucar;

import com.twygo.usacucar.Entidades.FilaProcessamento;
import com.twygo.usacucar.Regra.FilaProcessamentoRegra;
import com.twygo.usacucar.Regra.ProcessadorRegistroStrategyRegra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "com.twygo.usacucar")
@EnableJpaRepositories(basePackages = "com.twygo.usacucar")
public class UsacucarApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private FilaProcessamentoRegra filaProcessamentoRegra;

	@Autowired
	private ProcessadorRegistroStrategyRegra processador;

	public static void main(String[] args) {
		SpringApplication.run(UsacucarApplication.class, args);
	}

	@Override
	public void run(String... args) {
		List<FilaProcessamento> registros = filaProcessamentoRegra.buscarRegistrosOrdenadosTpAcao();
		for (FilaProcessamento registro : registros) {
			processador.processarRegistro(registro);
		}
	}
}