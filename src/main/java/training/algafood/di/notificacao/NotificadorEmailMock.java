package training.algafood.di.notificacao;

import training.algafood.di.modelo.Cliente;
import org.springframework.stereotype.Component;

@Component
@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
public class NotificadorEmailMock implements Notificador {

	public NotificadorEmailMock() {
		System.out.println("NotificadorEmail MOCK");
	}
	
	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("MOCK: Notificando %s através do e-mail %s: %s\n", 
				cliente.getNome(), cliente.getEmail(), mensagem);
	}

}
