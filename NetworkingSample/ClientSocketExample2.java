package org.kodejava.example.net;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;


public class ClientSocketExample2 {

public static void main(String[] args) {

	Scanner sc = new Scanner(System.in);

try {
String x="";
while(x!="Exit")
{

//

// Create a connection to the server socket on the server application

//

InetAddress host = InetAddress.getLocalHost();
System.out.println(InetAddress.getLocalHost());
System.out.println(host.getHostName());

Socket socket = new Socket("10.50.50.50", 7777);

//

// Send a message to the client application

//



	x=sc.nextLine();
	ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

	oos.writeObject(x);


System.out.println("Waiting For Response: ");

//

// Read and display the response message sent by server application


ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

String message = (String) ois.readObject();

System.out.println("Message Recieved: " + message);

ois.close();

oos.close();


}

} catch (UnknownHostException e) {

e.printStackTrace();

} catch (IOException e) {

e.printStackTrace();

} catch (ClassNotFoundException e) {

e.printStackTrace();

}

}
}