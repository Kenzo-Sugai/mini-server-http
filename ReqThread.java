import java.io.*;
import java.net.*;
import java.util.*;

public class ReqThread implements Runnable{
    
    Socket client;
    String fromClient;
    String toClient;

    public ReqThread(Socket client){
        this.client = client;
    }

    public void run(){
        try{
            System.out.println("Conexão efetuada na porta 8080");
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(),true);
 
            fromClient = in.readLine();
            System.out.println("recebido do cliente: " + fromClient);
            
            if(fromClient.contains("GET /")) {
    
                // GET REQ
                String clientMsg = fromClient.replace("GET /", "");
                String htmlString = "";
                System.out.println(clientMsg);
                if(clientMsg.contains("napoleao.html HTTP/1.1")){
                    System.out.println("entrou");
                    try {
                        File html = new File("./napoleao.html");
                        Scanner reader = new Scanner(html);

                        while(reader.hasNextLine()){
                            String data = reader.nextLine();
                            htmlString += data; 
                        }
                        
                        reader.close();
                        
                    } catch(FileNotFoundException  e){
                        System.out.println(e);
                    }
                
                    out.println(htmlString);
                    out.flush();
                    client.close();

                }
            } else {
                toClient = fromClient;
                out.println(toClient);
                System.out.println("send "+toClient);
                if(fromClient.equals("Bye")) {
                    toClient = "Tchau";
                    System.out.println("send Tchau");
                    out.println(toClient);
                    client.close();
                    System.out.println("socket closed");
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

}
