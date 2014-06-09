package redes1;

import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

public class main {
	
	public static String nick_usuario;
	public static String nick_recibe;
	public static String msj;
	
	public static void main(String args[]) throws
	UnknownHostException, IOException{
		
		byte[] buffer = new byte[1024];
		int bytes;
		int Puerto;
		
		Puerto = 8001 + (int)(Math.random()*60000); 
		
		ServerSocket servidor = new ServerSocket(Puerto);
		String url = "http://localhost:"+ Puerto +"/login.html";

        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
		while(true){
			//System.out.println("Esperando cliente");
			Socket cliente = servidor.accept();
			//System.out.println("Cliente conectado");
			System.setProperty("line.separador","\r\n");
			Scanner lee = new Scanner(cliente.getInputStream());
			PrintWriter escribe = new PrintWriter(cliente.getOutputStream(),true);
			
			String metodo = lee.next();
			//System.out.println(metodo);
			
			String fichero = "." + lee.next(); 
		
			FileInputStream fis = null; 
			boolean existe = true; 
			
			int i = 0;
			
			try { 
				fis = new FileInputStream(fichero); 
			} 
			catch (FileNotFoundException e) { 
				existe = false; 
			} 
				 
			if (existe && fichero.length()>2) 
				while((bytes = fis.read(buffer)) != -1 )
					cliente.getOutputStream().write(buffer, 0, bytes); 
			else {
				escribe.println("HTTP/1.0 404 Not Found"); 
				escribe.println(); 
			}
			//String a = null;
			cliente.close();
			//lee.close();
			//escribe.close();
			//fis.close();
			String aRemplazar = null;
			String web = null;
			if(metodo.equals("POST")){
				
				//con crome es el 36 y en safari con 39 (esto en mac)
				while(i<34){
					 aRemplazar = lee.next();
					 
					 //System.out.println(i);
					 //System.out.println(a);
					 //if (i==28){
					//	 web = aRemplazar;
					//	 System.out.println(web);
					 //}
					 System.out.println(aRemplazar);
					 i++;
					
				}
			}		
			
			
			FileWriter fichero1 = null;
			FileWriter fichero2 = null;
	        PrintWriter pw = null;
	        try
	        {
	            
	            

		        String remplazado1 = null;
		        String remplazado2 = null;
		        String remplazado3 = null;
		        String remplazado4 = null;
		        String remplazado5 = null;
		        String remplazado6 = null;
		        String remplazado7 = null;
		        String remplazado8 = null;
		        String remplazado9 = null;
		        //String remplazadoFinal = null;
	            
	            //String aRemplazar = a;
	 			remplazado1=aRemplazar.replace("username", "");
	 			//System.out.println(remplazado1);
	 			 remplazado2=remplazado1.replace("=", "");
	 			//System.out.println(remplazado2);
	 			 remplazado3=remplazado2.replace("&", ",");
	 			//System.out.println(remplazado3);
	 			 remplazado4=remplazado3.replace("send_button","");
	 			 remplazado5=remplazado4.replace("ip", "");
	 			//System.out.println(remplazado5);
	 			 remplazado6=remplazado5.replace("envia", "");
	 			//System.out.println(remplazado6);
	 			 remplazado7=remplazado6.replace("mensaje", "");
	 			//System.out.println(remplazado7);
	 			 remplazado8=remplazado7.replace("+", " ");
	 			//System.out.println(remplazado8);
	 			 remplazado9=remplazado8.replace("login", "");
	 			//System.out.println(remplazado9);
	 			
	 			String [] remplazadoFinal= remplazado9.split(",");
	 			System.out.println(remplazadoFinal[0]);
	 			//System.out.println(remplazadoFinal[1]);
	 			
	 			
	 			
	 			
	 			
	 			
	 			if (nick_usuario == null)
	 			{
	 				nick_usuario = remplazadoFinal[0];
	 				System.out.print(nick_usuario);
	 				fichero1 = new FileWriter(nick_usuario+".txt",true);
	 				
	 				
		            
	 				
	 			}
	 			else
	 			{
	 				
	 				msj = nick_usuario + ": " + remplazadoFinal[1];
	 				System.out.println(msj);
	 				nick_recibe = remplazadoFinal[0];
	 				System.out.print(nick_recibe);

	 				fichero2 = new FileWriter("chat"+nick_usuario+nick_recibe+".txt",true);
	 				pw = new PrintWriter(fichero2);
	 				pw.write(msj);
	 				
	 				pw.write("\n");
	 				pw.close();
	 				
	 				try {
		 				File inFile = new File("chat" + nick_usuario + nick_recibe + ".txt");
		 				File outFile = new File("chat.txt");

		 				FileInputStream in = new FileInputStream(inFile);
		 				FileOutputStream out = new FileOutputStream(outFile);

		 				int c;
		 				while( (c = in.read() ) != -1)
		 					out.write(c);

		 				in.close();
		 				out.close();
		 			} catch(IOException e) {
		 				System.err.println("Hubo un error de entrada/salida!!!");
		 			}
	 				
	 			}





	 			
	           //pw.append("Contacto:                       \n");
	            
	            
	            

		 
		         // Lectura del fichero

		         	 
	        

	            
	            
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	           try {
	           // Nuevamente aprovechamos el finally para 
	           // asegurarnos que se cierra el fichero.
	           if (null != fichero1)
	              fichero1.close();
	           } catch (Exception e2) {
	              e2.printStackTrace();
	           }
	        }

			
			
			
			  
			
			
			
		}
	}

}