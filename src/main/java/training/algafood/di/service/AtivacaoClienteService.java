package training.algafood.di.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import training.algafood.di.modelo.Cliente;
import training.algafood.di.notificacao.NivelUrgencia;
import training.algafood.di.notificacao.Notificador;
import training.algafood.di.notificacao.TipoDoNotificador;

import training.algafood.event.ClienteAtivadoEvent;

@Component
public class AtivacaoClienteService {
	
	@TipoDoNotificador(NivelUrgencia.URGENTE)
	@Autowired
	private Notificador notificador;
	
	@PostConstruct
	public void init() {
		System.out.println("INIT1 " + notificador.getClass());
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("DESTROY1 " + notificador.getClass());
	}
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	public void ativar(Cliente cliente) {
		cliente.ativar();
		
		eventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));

		if (notificador != null) {
			notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
		} else {
			System.out.println("Não existe notificador, mas cliente foi ativado.");
		}
	}
}
