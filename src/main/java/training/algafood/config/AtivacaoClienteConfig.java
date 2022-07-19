//package com.algaworks.algafood.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.algaworks.algafood.di.service.AtivacaoClienteService;
//
//@Configuration
//public class AtivacaoClienteConfig {
//
//	@Bean(initMethod = "init", destroyMethod = "destroy")
//	public AtivacaoClienteService onInit() {
//		System.out.println("INIT2");
//		return new AtivacaoClienteService("");
//	}
//
//}
