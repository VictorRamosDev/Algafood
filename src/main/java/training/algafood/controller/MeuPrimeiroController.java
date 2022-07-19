package training.algafood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import training.algafood.di.modelo.Cliente;
import training.algafood.di.service.AtivacaoClienteService;

@Controller
public class MeuPrimeiroController {

	@Autowired
	private AtivacaoClienteService ativacaoClienteService;

	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
	    Cliente joao = new Cliente("João", "joao@xyz.com", "3499998888");
	    ativacaoClienteService.ativar(joao);
		
		return "Hello!";
	}

}
