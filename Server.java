import java.io.*;
import java.net.*;

public class Server {
    public static void main(String args[]) throws Exception {
        String fromClient;
        String toClient;
 
        ServerSocket server = new ServerSocket(8080);
        System.out.println("Aguardando conexão na porta 8080");
 
        boolean run = true;
        while(true) {
            Socket client = server.accept();
            Thread clientReq = new Thread(new ReqThread(client), "t1");
            clientReq.start();
        }
    }
}
