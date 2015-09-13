package org.kodejava.example.net;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.lang.Runnable;
import java.lang.Thread;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.net.InetAddress;

public class ServerSocketExample2
{

private ServerSocket server;
private int port = 7777;

public ServerSocketExample2() {

try {

server = new ServerSocket(port);

} catch (IOException e) {

e.printStackTrace();

}

}



public static void main(String[] args) {

ServerSocketExample2 example = new ServerSocketExample2();

example.handleConnection();

}


public void handleConnection() {



//

// The server do a loop here to accept all connection initiated by the
// client application.

//

while (true) {

try {
	System.out.println("Waiting for client on port " +
            server.getLocalPort() + "...");

 	 InetAddress ip = InetAddress.getLocalHost();
   System.out.println(ip);


Socket socket = server.accept();
System.out.println("Just connected to "
                  + socket.getRemoteSocketAddress());


new ConnectionHandler(socket);


} catch (IOException e) {

e.printStackTrace();

}

}

}
}



class ConnectionHandler implements Runnable {

private Socket socket;

Scanner sc = new Scanner(System.in);
String x="";




public ConnectionHandler(Socket socket) {

this.socket = socket;





Thread t = new Thread(this);

t.start();

}


public void run() {


try
{

//

// Read a message sent by client application

//

ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

String message = (String) ois.readObject();

System.out.println("Message Received: " + message);
x = sc.nextLine();


//

// Send a response information to the client application

//

ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

oos.writeObject(x);



ois.close();

oos.close();

socket.close();



System.out.println("Waiting for Response: ");

} catch (IOException e) {

e.printStackTrace();

} catch (ClassNotFoundException e) {

e.printStackTrace();

}

}
}

