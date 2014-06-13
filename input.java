package broadcast;

import java.awt.ComponentOrientation;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class input {
        public static JFrame frame;
	private JLabel headerlabel;
	public static JLabel statuslabel;
	//private ServerSocket serverSocket;
        
        static int count_total=0;
        public static int count_received=0;
        public static int count_dead=0;
        public static int count_unreachable=0;
	private int count=0;
	static private JTextArea attempted_list;
        static public JTextArea unresponsive_list;
        static connect[] arr_of_obj = new connect[1000];
        public static boolean sync_flag=true;
        static private String ip_add="";
        static private int mask,port;
        
        
    public static void main(String[] args) {
        input obj= new input();
        
        obj.read();
   obj.frame_init();
    
       convert(ip_add,mask);
    }
    private static void read()
    {
        String ports = JOptionPane.showInputDialog("Enter port");
	if(ports.equals(""))
            port=9000;
        else
            port=Integer.parseInt(ports);	
	ip_add = JOptionPane.showInputDialog("Enter network address");
	 if(ip_add.equals(""))
            ip_add="127.0.0.1";
        String masks = JOptionPane.showInputDialog("Enter network mask");
	if(masks.equals(""))
            mask=24;
        else
            mask=Integer.parseInt(masks);	
	        
    }
    private void frame_init()
	{
                frame = new JFrame("Broadcast by Nikhil Dargan");
		frame.setResizable(false);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		headerlabel = new JLabel("List of attempted ip:                                          List of remote shutdown ip:",JLabel.CENTER);
		statuslabel = new JLabel("",JLabel.CENTER);
		attempted_list = new JTextArea(8,20);
		attempted_list.setEditable(false);
                attempted_list.setLineWrap(true);
		unresponsive_list = new JTextArea(8,20);
		unresponsive_list.setEditable(false);
                unresponsive_list.setLineWrap(true);
           	frame.getContentPane().add(new JScrollPane(attempted_list),"West");
		frame.getContentPane().add(new JScrollPane(unresponsive_list),"East");
		frame.getContentPane().add(headerlabel,"North");
		frame.getContentPane().add(statuslabel,"South");
                frame.setVisible(true);
              frame.setLocationRelativeTo(null);
           
	}	  	 
    public static void broad(int a,int b,int c,int d) throws UnknownHostException
	{
            try {
                String str = Integer.toString(a) + "." + Integer.toString(b) + "." + Integer.toString(c) + "." + Integer.toString(d);
                Thread t=new connect(str,9000);
                t.start();
                attempted_list.append(str+"\n");
                count_total++;
                
               input.statuslabel.setText("Attempted: "+input.count_total+"    Users active: "+input.count_received+"   Remote Shutdown:"+input.count_dead+"  Unreachable: "+input.count_unreachable);
            } catch (SocketException ex) {
                Logger.getLogger(input.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(input.class.getName()).log(Level.SEVERE, null, ex);
            }
                
	}

    private static void convert(String ip_add,int mask)
    {
        		ip_add += ".";
			char[] ip_str=ip_add.toCharArray();
			int[] ip_int=new int[10];
			String temp;
			int i=0;
			for(int j=0;j<4;j++)
			{
				temp="";
				while(ip_str[i] != '.')
				{
					temp=temp+ip_str[i];
					i++;
					
                                }
				i++;
				ip_int[j]=Integer.parseInt(temp);
			}
			//System.out.println(ip_int[3]);
                        
                    if(mask <= 8)
                        ip_int[1]=0;
                   
                                
			for(i=0;i<256;i++)
			{
				if(mask <= 16)
                                    ip_int[2]=0;
            
				for(int j=0;j<256;j++)
				{
                                                if(mask <= 24)
                                                ip_int[3]=0;
					for(int k=0;k<256;k++)
					{
                                            try {			
                                                broad(ip_int[0],ip_int[1],ip_int[2],ip_int[3]);
                                            } catch (UnknownHostException ex) {
                                                Logger.getLogger(input.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            ip_int[3]++;
						
                                        }
                                        ip_int[2]++;
                                        if(mask > 16)
                                               break;
                                   						
				
                                }
                                ip_int[1]++;
				
                                if(mask > 8)
                                        break;
			}
    }
}
