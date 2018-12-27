package psiquesis;

/* TCPClient.java from Kurose-Ross 
Este programa cliente cria 3 cadeias( inFromUser, outToServer, inFromServer ) e
um socket com o nome clientSocket.
Os caracteres digitados pelo usuário vão para inFromUser...
Os caracteres que são enviados ao servidor são armazenados em outToServer
Os caracteres que chegam através da rede vão para inFromServer...
 */

import java.io.*; 
	//É um pacote java que possui classes para Entrada e Saída (BufferedReader e DataOutPutStream)
import java.net.*;
	//Provê classes de suporte a rede (Socket e ServerSocket)

class TCPClient 
	//Inicia-se a definição da classe TCPClient
{ 
	//Limita aquilo que esta dentro de TCPClient
	// A classe TCPClient não possui variáveis, mas somente o método main
  public static void main(String argv[]) throws Exception
		//O método é como se fosse uma função. No caso o método main é igual a função
		//main da linguagem C. Todo processamento começa dele.
    { //Inicia o bloco da função mainA/NOVO/27/30/Não há
    String sentence;
		//Sentence: Cadeia de caracteres digitados pelo usuário e enviado ao servidor
    String modifiedSentence;
		//modifiedSentence: Cadeia de caracteres recebida do servidor
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		//Cria o objeto inFromUser sendo do tipo BufferedReader inicializando como System.in
		//Isso permite que o cliente leia a informação do seu teclado
    Socket clientSocket = new Socket("localhost", 12345);
		//Cria o objeto clientSocket do tipo Socket, determinando o endereço e porta do servidor, ou seja,
		//neste caso cria uma conexão TCP com o servidor especificado.
    DataOutputStream outToServer = 
			new DataOutputStream(clientSocket.getOutputStream());
		//Cria o objeto outToServer associando ele aquilo que é a saída do Socket.
    BufferedReader inFromServer = 
			new BufferedReader(new InputStreamReader(
                                               clientSocket.getInputStream()));
		//Cria o objeto inFromServer associando ele aquilo que é recebido via socket
    sentence = inFromUser.readLine();
		//Coloca aquilo que o usuário digita no objeto sentence
    outToServer.writeBytes(sentence + '\n');
		//Escreve em outToServer aquilo que esta na variável sentence. Isso fluirá pela conexão TCP.
    modifiedSentence = inFromServer.readLine();
		//Quando chega o retorno do servidor, coloca ele em modifiedSentence
    System.out.println("FROM SERVER: " + modifiedSentence);
		//Imprime na tela aquilo que o servidor enviou e foi armazenado em modifiedSentence
    clientSocket.close();
		//Encerra-se o socket e consequentemente a conexão TCP com o servidor.
    } //Encerra o bloco da função main
}
