package redes1;

import java.io.*;
import java.net.*;

public class Client {
    private DatagramSocket socket = null;
    private FileEvent event = null;
    private String sourceFilePath = "C:/Users/alfi/Desktop/tarea2redes/main/archivo.txt";
    private String destinationPath = "C:/Users/alfi/Desktop/tarea2redes/main/udp/";
    private String hostName = "localHost";


    public Client() {

    }

    public void createConnection() {
        try {

            socket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(hostName);
            byte[] incomingData = new byte[1024];
            System.out.println("Inicio del cliente");
            event = getFileEvent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(event);
            byte[] data = outputStream.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
            socket.send(sendPacket);
            System.out.println("El archivo fue enviado desde el cliente");
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            socket.receive(incomingPacket);
            //System.out.println(incomingData);
            String response = new String(incomingPacket.getData());
            //System.out.println(response.length());
            String respuesta = "";
            for(int x=0;x<16;x++){
            	respuesta = respuesta + response.charAt(x);
            }
            //System.out.println(respuesta);
            System.out.println("Respuesta del servidor: " + respuesta);
            Thread.sleep(2000);
            System.out.println("Fin del cliente");
            System.exit(0);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public FileEvent getFileEvent() {
        FileEvent fileEvent = new FileEvent();
        String fileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.length());
        String path = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("/") + 1);
        fileEvent.setDestinationDirectory(destinationPath);
        fileEvent.setFilename(fileName);
        fileEvent.setSourceDirectory(sourceFilePath);
        File file = new File(sourceFilePath);
        if (file.isFile()) {
            try {
                DataInputStream diStream = new DataInputStream(new FileInputStream(file));
                long len = (int) file.length();
                byte[] fileBytes = new byte[(int) len];
                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read,
                        fileBytes.length - read)) >= 0) {
                    read = read + numRead;
                }
                fileEvent.setFileSize(len);
                fileEvent.setFileData(fileBytes);
                fileEvent.setStatus("Exito");
            } catch (Exception e) {
                e.printStackTrace();
                fileEvent.setStatus("Error");
            }
        } else {
            System.out.println("ruta especificada no está apuntando a un archivo");
            fileEvent.setStatus("Error");
        }
        return fileEvent;
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.createConnection();
    }
}