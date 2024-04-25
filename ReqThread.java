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
            if (fromClient == null) {
                client.close();
                return;
            }
            System.out.println("recebido do cliente: " + fromClient);
            
            if(fromClient.contains("GET /")) {
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
                    out.write("HTTP/1.1 200 OK\r\n");
                    out.write("Content-Type: text/html\r\n");
                    out.write("\r\n");
                    System.out.println(htmlString);
                    out.write(htmlString);
                    

                }else{
                    out.write("HTTP/1.1 405 Method Not Allowed\r\n");
                    out.write("\r\n");
                    out.write("<h1>405 Method Not Allowed</h1>");
                }
                out.close();
                client.close();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

}
