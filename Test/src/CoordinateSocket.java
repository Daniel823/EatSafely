import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class CoordinateSocket {
	int port_number;
	InetAddress host;
	Socket client_socket;
	DataOutputStream output2;
	PrintWriter output1;
	BufferedReader input1;
	DataInputStream input2;
	String message;

	public CoordinateSocket( int port ) throws IOException {
		port_number = port;
		String hostIP = "207.181.245.105";
		//host = InetAddress.getByName(hostIP);
		host = InetAddress.getLoopbackAddress();
		//client_socket = new Socket( host, port );
		client_socket = new Socket( hostIP, port );
		//output1= new PrintWriter( client_socket.getOutputStream(), true );
		output2 = new DataOutputStream(client_socket.getOutputStream());
		input1= new BufferedReader( new InputStreamReader(client_socket.getInputStream()) );
		input2 = new DataInputStream(client_socket.getInputStream());
	}

	public void transmit() throws IOException
	{
			output2.writeUTF("-l");
			output2.writeUTF("41.950077, -87.650944;41.947426, -87.647017");
			
			input2.readUTF();
	}

	public void rTest() throws IOException
	{
			output2.writeUTF("-r");
			output2.writeUTF("41.92268911,-87.64469334");
			
			input2.readUTF();
	}
	
	public void echo(String mess) throws IOException
	{
		output1.println(mess);
		message= input1.readLine();
		System.out.println( "Received: " + message );		
		client_socket.close();
	}
}
