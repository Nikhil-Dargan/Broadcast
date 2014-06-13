import java.net.*;
import java.io.*;
public class server
{
   private ServerSocket serverSocket;
   
   public client(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(100000);
   }

   public void run()
   {
         try
         {
            System.out.println("Waiting for client on port " +
            serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
	        String message="";
	        DataInputStream in = new DataInputStream(server.getInputStream());
            DataOutputStream out =  new DataOutputStream(server.getOutputStream());
            System.out.println("connected\n\n");
	  message=in.readUTF();
		System.out.println(message);
                InputStreamReader varName = new InputStreamReader(System.in);
		BufferedReader in1=new BufferedReader(varName);	
                long start=System.currentTimeMillis();
                int flag=0;
                while((System.currentTimeMillis()-start)<5000)
                {
                    if(System.in.available() > 0)
                    {
                      message=in1.readLine();
                      flag=1;
                      break;
                    }
                }out.writeUTF(message);
               if(flag==0)
               {
                		System.out.println("Force shutdown by server\n");
		 Process process = Runtime.getRuntime().exec("notepad.exe");
            	//	Process process = Runtime.getRuntime().exec("shutdown -r -t 60");
                }
	
         }catch(Exception e){}
         
      
   }
   public static void main(String [] args) throws IOException
   {
      int port = 9000;
     
         client obj = new client(port);
         obj.run();
      
   }
}

