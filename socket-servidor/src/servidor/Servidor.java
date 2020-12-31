package servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import pessoa.Pessoa;
import pessoa.PessoaRepository;

public class Servidor {

	public static void main(String[] args) {
		String clientSentence;
		String captura;
		PessoaRepository pr = new PessoaRepository();

		try {
			// Cria um SocketServer (Socket característico de um servidor)
			ServerSocket socket = new ServerSocket(40000);
			while (true) {
				/*
				 * Cria um objeto Socket, mas passando informações características de um
				 * servidor, no qual somente abre uma porta e aguarda a conexão de um cliente
				 */
				Socket connectionSocket = socket.accept();
				// Cria uma buffer que irá armazenar as informações enviadas pelo cliente
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));
				// Cria uma stream de sáida para retorno das informações ao cliente
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				// Faz a leitura das informações enviadas pelo cliente as amazenam na variável
				// "clientSentence"
				clientSentence = inFromClient.readLine();

				if (clientSentence.contains("create")) {
					clientSentence = clientSentence.replace("create | ", "");
					String[] retorno2 = clientSentence.split(",");
					System.out.println(clientSentence);
					Pessoa p = new Pessoa(Integer.parseInt(retorno2[0]), retorno2[1], retorno2[2], retorno2[3]);
					boolean retorno = pr.insertPessoa(p);
					if (retorno) {
						captura = "201 created";
					} else {
						captura = "500 Internal Server Error";
					}
				} else if (clientSentence.contains("read")) {
					clientSentence = clientSentence.replace("read | ", "");

					Pessoa p = pr.findPessoaById(Integer.parseInt(clientSentence));
					if (p != null) {
						captura = "200 OK";
					} else {
						captura = "500 Internal Server Error";
					}
				} else if (clientSentence.contains("delete")) {
					clientSentence = clientSentence.replace("delete | ", "");
					boolean retorno = pr.removePessoaById(Integer.parseInt(clientSentence));
					if (retorno) {
						captura = "200 OK";
					} else {
						captura = "500 Internal Server Error";
					}
				} else if (clientSentence.contains("update")) {
					clientSentence = clientSentence.replace("update | ", "");
					String[] retorno2 = clientSentence.split(",");

					boolean retorno = pr.updateNamePessoa(Integer.parseInt(retorno2[0]), retorno2[1]);
					if (retorno) {
						captura = "200 OK";
					} else {
						captura = "500 Internal Server Error";
					}
				} else if (clientSentence.contains("sair")) {
					captura = "200 OK";
				} else {
					captura = "501 Not Implemented";
				}

				/*
				 * Faz uma modificação na String enviada pelo cliente, simulando um
				 * processamento em "back-end" antes de retorná-la ao cliente
				 */
				captura = captura.toUpperCase() + "\n";
				// Imprime a a String modificada no console do servidor
				System.out.println(captura);
				// Retorna as informações modificadas, ao cliente
				outToClient.writeBytes(captura);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}