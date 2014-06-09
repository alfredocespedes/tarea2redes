package redes1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;

public class ChatCliente {
private static int port = 8004; /* port to connect to */
private static String host = "localhost"; /* host to connect to */

private static BufferedReader stdIn;

private static String nick;

/**
 * Read in a nickname from stdin and attempt to authenticate with the 
 * server by sending a NICK command to @out. If the response from @in
 * is not equal to "OK" go bacl and read a nickname again
 */
private static String getNick(BufferedReader in, 
                              PrintWriter out) throws IOException {
    System.out.print("Ingresar el usuario: ");
    String msg = stdIn.readLine();
    
	/*
    String nombreArchivo= msg + ".txt";
    FileWriter fw = null;
    try { 
    fw = new FileWriter(nombreArchivo); 
    BufferedWriter bw = new BufferedWriter(fw); 
    PrintWriter salArch = new PrintWriter(bw); 
    
    //salArch.print("Primer linea"); 
    //salArch.println(); 
    salArch.close(); 
    } 
    catch (IOException ex) { 
    } 
	*/
    
    out.println("NICK " + msg);
    String serverResponse = in.readLine();
    if ("SERVER: OK".equals(serverResponse)) return msg;
    System.out.println(serverResponse);
    return getNick(in, out);
}

public static void main (String[] args) throws IOException {

    Socket server = null;
    //System.out.print(port);

    try {
        server = new Socket(host, port);
    } catch (UnknownHostException e) {
        System.err.println(e);
        System.exit(1);
    }

    stdIn = new BufferedReader(new InputStreamReader(System.in));

    /* obtain an output stream to the server... */
    PrintWriter out = new PrintWriter(server.getOutputStream(), true);
    /* ... and an input stream */
    BufferedReader in = new BufferedReader(new InputStreamReader(
                server.getInputStream()));
    
    nick = getNick(in, out);

    //System.out.print(nick);
    /* create a thread to asyncronously read messages from the server */
    ServerConn sc = new ServerConn(server);
    
    Thread t = new Thread(sc);
    t.start();

    String msg;
    
    /* loop reading messages from stdin and sending them to the server */
    while ((msg = stdIn.readLine()) != null) {
    	
    	String delimitador = " ";
    	String [] separada = msg.split(delimitador);
    	//System.out.print(separada[0]);
    	//System.out.print("\n");
    	//System.out.print(separada[1]);
    	//System.out.print("\n");
    	String mensaje = "";
    	for (int cont=2; cont<separada.length; cont++){
    		mensaje = mensaje + separada[cont] + " ";
    	}
    	//System.out.print(mensaje);
    	//System.out.print("\n");
    	
    	FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(separada[1] + ".txt",true);
            pw = new PrintWriter(fichero);
            
            pw.append(nick + ": " + mensaje + "|");
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    	
    	/*
    	String direccion = "C:/Users/Gustavo/workspace/Tarea_2/" + separada[1] + ".txt";
    	File fichero = new File(direccion);
    	if(fichero.exists()){
    		System.out.print("existe el fichero");
    	}
    	*/
    	
    	System.out.print("\n");
    	
        out.println(msg);
    }
  }
}

class ServerConn implements Runnable {
private BufferedReader in = null;

public ServerConn(Socket server) throws IOException {
    /* obtain an input stream from the server */
    in = new BufferedReader(new InputStreamReader(
                server.getInputStream()));
}

public void run() {
    String msg;
    try {
        /* loop reading messages from the server and show them 
         * on stdout */
        while ((msg = in.readLine()) != null) {
            System.out.println(msg);
        }
    } catch (IOException e) {
        System.err.println(e);
    }
  }
}