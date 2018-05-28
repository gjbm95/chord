package com.Comandos;

import com.ControladoresRed.ConexionUtils;
import com.ControladoresRed.Mensaje;
import com.Entidades.*;
import org.apache.commons.io.IOUtils;
import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Universidad Catolica Andres Bello
 * Facultad de Ingenieria
 * Escuela de Ingenieria Informatica
 * Trabajo Especial de Grado
 * ----------------------------------
 * Tutor:
 * --------------
 * Wilmer Pereira
 *
 * Autores:
 * --------------
 * Garry Bruno
 * Carlos Valero
 */
public class Descarga extends Thread {

    public byte[] cuerpo;
    public int inicial;
    public int terminal;
    public Nodo nodo;
    public Long archivo;

    public String estado; // Estados: "F","D","E"

    public Descarga(int inicial, int terminal, Nodo nodo, Long archivo){
           this.inicial = inicial;
           this.terminal = terminal;
           this.nodo = nodo;
           this.archivo = archivo;
           cuerpo= new byte[terminal-inicial];
    }

    public void run(){
        try {
            DataOutputStream output;
            BufferedInputStream bis;
            BufferedOutputStream bos;
            byte[] receivedData;
            int in;
            String file;
            String[] d = null;
            DataInputStream dis = null;
            String ip =this.nodo.getDireccion();
            int puerto = this.nodo.getPuertopeticion()+1;
            Long nombre = this.archivo;
            System.out.println("Iniciando proceso de descarga de archivo");
            this.estado = "D";
            // Se abre una conexion con Servidor Socket
            Socket cliente = new Socket(ip, puerto);
            cliente.setSoTimeout(5000);
            ObjectOutputStream salidaObjeto = new ObjectOutputStream(cliente.getOutputStream());
            //Solicito el archivo:
            salidaObjeto.writeObject("4:" + nombre+":"+Nodo.getInstancia().getDireccion()
                    +":"+Nodo.getInstancia().getPuertopeticion());
            bis = new BufferedInputStream(cliente.getInputStream());
            dis = new DataInputStream(cliente.getInputStream());
            //Recibimos el nombre del fichero
            file = dis.readUTF();
            d = file.split(":");
            d[0] = d[0].substring(d[0].indexOf('\\') + 1, d[0].length());
            //La data recibida, vendran en paquetes de 1024 bytes.
            receivedData = new byte[terminal-inicial];
            boolean recarga;

            recarga=false;
            salidaObjeto.writeObject(0);

            salidaObjeto.writeObject(inicial);
            salidaObjeto.writeObject(terminal);
            int l = 0;
            boolean partido = true;
            int recorrido =0;
            IOUtils.read(bis,receivedData);
            cuerpo=receivedData;
            this.estado = "F";
            dis.close();
            System.out.println("Finalizando proceso de descarga de archivo");
        } catch (Exception e) {
            Logger.getLogger(RecibirArchivoCommand.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Descarga fallida!");
            this.estado = "E";
            try {
                Mensaje mensaje =(Mensaje) ConexionUtils.obtenerInstancia().enviarMensaje(new Mensaje("deletenode",
                        new NodoRF(nodo.getDireccion(),nodo.getPuertopeticion()), Fantasma.obtenerInstancia()));
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            }
        }

    }

}
