package training.algafood.di.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import training.algafood.event.ClienteAtivadoEvent;

@Component
public class NotificacaoListener {

	@EventListener
	public void notifica(ClienteAtivadoEvent event) {
		System.out.println("Cliente " + event.getCliente().getNome() + " agora está ativo.");
	}

}
