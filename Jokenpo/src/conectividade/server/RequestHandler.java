package conectividade.server;

import static conectividade.Flag.ADVERSARIOJOGOU;
import static conectividade.Flag.JOGADAS;
import static conectividade.Flag.PLACAR;
import static conectividade.Flag.PLACARFINAL;
import static conectividade.Flag.VENCEDOR;
import static conectividade.Flag.VENCEDORFINAL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Classe respons�vel por receber e enviar mensagens entre o Servidor e os Clientes
 */
public class RequestHandler extends Thread {
	private Socket socket;
	private Server server;
	private BufferedReader in;
	private PrintWriter out;

	RequestHandler(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
		this.setName("Server RequestHandler" + socket.getInetAddress());
		
		/**
		 * in - Input de mensagens do cliente
		 * out - Output de mensasens para o cliente
		 */
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		try {
			/**
			 * Loop que fica constantemente verificando novas mensagens do cliente
			 */
			boolean stop = false;
			do {
				String[] line = in.readLine().split(":");
				String flag = line[0];
				String value = line[1];
				
				if("NICKNAME".equals(flag)) {
					adicionarJogador(value);
				}
				
				if("STOP".equals(flag)) {
					stop = true;
					server.stopServer();
				}
				
				if("JOGADA".equals(flag)) {
					String name = line[2];
					boolean fazerJogada = server.getJogo().armazenaJogada(name, value);
					server.sendToClients(ADVERSARIOJOGOU + name);
					
					if(fazerJogada) { 
						String nomeVencedor = server.getJogo().fazJogada();
						server.sendToClients(JOGADAS + server.getJogadas());
						server.sendToClients(VENCEDOR + nomeVencedor);
						server.resetarJogadas();
						
						/**
						 * Tempo dos jogadores verem o resultado
						 */
						RequestHandler.sleep(5000);
						
						/**
						 * Verifica se o Jogo chegou ao fim
						 */
						server.sendToClients(PLACAR + server.getPlacar());
						String vencedorFinal = server.getJogo().verificaFim();
						server.sendToClients(VENCEDORFINAL + vencedorFinal);
						if(vencedorFinal != null)
							server.sendToClients(PLACARFINAL + server.getPlacar());
					}
				}
				
			} while (!stop);
			
			in.close();
			out.close();
			socket.close();
			this.interrupt();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void adicionarJogador(String nomeJogador) {
		server.getNomesJogadores().add(nomeJogador);
	}
	
	/**
	 * @param message - Mensagem a ser enviada para o servidor
	 */
	public void sendToClient(String message) {
		out.println(message);
		out.flush();
	}
}