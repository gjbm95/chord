package com.Comandos;

import com.ControladoresRed.ConexionUtils;
import com.ControladoresRed.Mensaje;
import com.ControladoresRed.RedProcesos;
import com.Entidades.Fantasma;
import com.Entidades.Nodo;
import com.Entidades.NodoRF;
import com.Utils.SistemaUtil;
import org.omg.PortableInterceptor.ObjectReferenceTemplate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

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
public class RecibirMensajeCommand extends AsyncCommand{

    public static final String COMMAND_NAME = "listen";


    @Override
    public String obtenerNombreComando() {
        return COMMAND_NAME;
    }

    @Override
    public void executeOnBackground(String[] args, OutputStream out) {
            try {
                ServerSocket recepcion = null;
                if (SistemaUtil.tipo.equals("miembro")) {
                    Nodo nodo = Nodo.obtenerInstancia();
                    recepcion = new ServerSocket(nodo.getPuertopeticion());
                }else if (SistemaUtil.tipo.equals("fantasma")){
                    Fantasma nodo = Fantasma.obtenerInstancia();
                    recepcion = new ServerSocket(nodo.getPuertopeticion());
                }
                while (true) {
                    //System.out.println("ConexionUtils habilitada y en espera...");
                    Socket recibo = recepcion.accept();
                    ObjectInputStream ois = new ObjectInputStream(recibo.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(recibo.getOutputStream());
                    //Mensaje que llega:
                    Object mensaje = ois.readObject();
                    //Falta ejecutar acciones dependiendo del mensaje
                    Mensaje data = (Mensaje) mensaje;
                    //Se ejecuta un hilo con el proceso
                    new RedProcesos(data,ois,oos).start();

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Object respuesta = null;
    }



}
