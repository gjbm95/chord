package com.Comandos;

import com.ControladoresRed.ConexionUtils;
import com.ControladoresRed.Mensaje;
import com.Entidades.Fantasma;
import com.Entidades.Nodo;
import com.Entidades.NodoRF;
import com.Utils.SistemaUtil;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Random;
import java.util.Scanner;

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
public class SeleccionarRedCommand extends BaseCommand{
    public static final String COMMAND_NAME = "selectnetwork";

    @Override
    public String obtenerNombreComando() {
        return null;
    }

    @Override
    public void ejecutar(String[] args, OutputStream out) {
        EjecutarComando.linea("loadresources");
        System.out.println("");
        System.out.println("-------------------------------------------------------");
        System.out.println("Seleccione una direccion:");
        System.out.println("-------------------------------------------------------");
        int conteo =0;
        String direcciones []= new String[10];
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements())
                {
                    InetAddress i = (InetAddress) ee.nextElement();
                    String direccion = i.getHostAddress();
                    String octetos[] = direccion.split("\\.");
                    if(octetos.length==4) {
                        System.out.println((conteo+1)+"- " + i.getHostAddress());
                        direcciones[conteo]=i.getHostAddress();
                        conteo++;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        boolean listo=false;
        while(!listo) {
            Scanner in = new Scanner(System.in);
            String line = in.nextLine();
            try {
                if ((Integer.parseInt(line)>=0)||(Integer.parseInt(line)<=10)) {
                   if (args[0].equals("miembro")) {
                       EjecutarComando.linea("network " + direcciones[Integer.parseInt(line) - 1]
                               + " " + this.seleccionarPuerto() + " miembro");
                       SistemaUtil.tipo = "miembro";
                       EjecutarComando.linea("listen");
                       EjecutarComando.linea("listenfile");
                       System.out.println("Coloque la IP del servidor central");
                       System.out.println("---------------------------------------------------");
                       in = new Scanner(System.in);
                       line = in.nextLine();
                       EjecutarComando.linea("network " + line + " 2000 central");
                       listo = true;
                   }else if (args[0].equals("fantasma")){
                       EjecutarComando.linea("network " + direcciones[Integer.parseInt(line) - 1]
                               + " 2000" + " central");
                       SistemaUtil.tipo = "fantasma";
                       EjecutarComando.linea("listen");
                       listo = true;
                   }
                }else{
                    System.out.println("Ha ingresado un valor no valido");
                    listo=false;
                }
            }catch(Exception e){
                System.out.println("Ha ingresado un valor no valido");
            }
        }
    }

    private String seleccionarPuerto(){
        String respuesta ="";
        Integer valor =0;
        while ((valor<2001)||(valor>5000)){
            Random r = new Random();
            valor = r.nextInt(5000);
        }
        respuesta = valor.toString();
        return respuesta;
    }
}
