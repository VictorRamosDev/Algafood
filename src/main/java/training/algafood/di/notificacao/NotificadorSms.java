package training.algafood.di.notificacao;

import org.springframework.stereotype.Component;

import training.algafood.di.modelo.Cliente;

@Component
@TipoDoNotificador(NivelUrgencia.NORMAL)
public class NotificadorSms implements Notificador {

	public NotificadorSms() {
		System.out.println("NotificadorSms REAL");
	}
	
	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s por SMS através do telefone %s: %s\n", 
				cliente.getNome(), cliente.getTelefone(), mensagem);
	}

}
