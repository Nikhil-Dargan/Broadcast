package broadcast;

import java.net.*;
import java.io.*;

public class connect extends Thread {
    Socket server;
    String serverName ;
    int port;
    static int flag_unreach=0;
    public connect(String serverName, int port) throws UnknownHostException, SocketException, IOException
    { 
        this.serverName = serverName;
        this.port = port; 
    }
    
    public synchronized void textarea_append()
    {
    input.unresponsive_list.append(serverName+"\n");
        
    }
    @Override        
    public void run()
    {
        String message="";
        try{
            
            server = new Socket(serverName, port);
            server.setSoTimeout(5000);
         OutputStream outToServer = server.getOutputStream();
         DataOutputStream out =new DataOutputStream(outToServer);
         out.writeUTF("Please shutdown");
				
         InputStream inFromServer = server.getInputStream();
				
         DataInputStream in = new DataInputStream(inFromServer);
         String msg=in.readUTF();
                input.count_received++;
                System.out.println(serverName+"\n");
                server.close();
              
        }
        catch(ConnectException e)
        {
            while(flag_unreach==1)
            {}
            flag_unreach=1;
            input.count_unreachable++;
            flag_unreach=0;
              input.statuslabel.setText("Attempted: "+input.count_total+"    Users active: "+input.count_received+"   Remote Shutdown:"+input.count_dead+"  Unreachable: "+input.count_unreachable);
        }
        
    catch(SocketTimeoutException e)
    {
        input.count_dead++;
        textarea_append();
        //System.out.println("");
     input.statuslabel.setText("Attempted: "+input.count_total+"    Users active: "+input.count_received+"   Remote Shutdown:"+input.count_dead+"  Unreachable: "+input.count_unreachable); 
    }
        catch(IOException e)
        {}
}
}
