import static java.lang.System.*;
import javax.swing.*;
import java.util.*;
import java.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.lang.*;
import java.net.*;

public class BejixedServer extends JFrame
{
	private ServerSocket server;
	private int port = 7777;
	private Container win, subWin;
	
	public BejixedServer() 
	{
		super("Server");
		win = getContentPane();
		win.setLayout(null);
		
		
		
		setSize(750,750);
		setLocation(500,150);
		try{setIconImage(ImageIO.read(new File("logo2.jpg")));}
		catch(Exception ex){}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		
		//SERVER STUFF
		try 
		{	
			server = new ServerSocket(port);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	
	}



	public static void main(String[] args) 
	{
		BejixedServer example = new BejixedServer();
		example.handleConnection();
	}
	
	public void handleConnection()
	{
		// The server do a loop here to accept all connection initiated by the client application.	
		while (true) 
		{
			try 
			{
				System.out.println("Waiting for client on port " +
			    server.getLocalPort() + "...");
			
			 	InetAddress ip = InetAddress.getLocalHost();
			 	System.out.println(ip);
			
			
				Socket socket = server.accept();
				System.out.println("Just connected to "+ socket.getRemoteSocketAddress());
			
			
				new ConnectionHandler(socket);
			
			}
			catch (IOException e)
			{	
				e.printStackTrace();
			}
		}	
	}
}



class ConnectionHandler implements Runnable
{
	private Socket socket;
	Scanner sc = new Scanner(System.in);
	int x[][]=new int[8][8];
	
	public ConnectionHandler(Socket socket)
	{	
		this.socket = socket;	
		Thread t = new Thread(this);
		t.start();
	}


	public void run()
	{
		try
		{	
			// Send a response information to the client application
			for(int i=0;i<8;i++)
			{
				out.println();
				for(int j=0;j<8;j++)
				{
					x[i][j]= (int) (Math.random()*7);
					out.print(x[i][j]+" ");
				}
			}
			
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(x);	
			
			// Read a message sent by client application
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			
			while(true)
			{
				String message = (String) ois.readObject();
				String s[]=message.split(" ");
				System.out.println("Message Received From "+s[0]+": " + s[1]);
			}
			
			/*ois.close();
			
			oos.close();
			
			socket.close();*/
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}

