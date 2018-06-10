package com.Utils;

import com.Comandos.EjecutarComando;
import com.ControladoresRed.ConexionUtils;
import com.ControladoresRed.Mensaje;
import com.Entidades.Estadistica;
import com.Entidades.Fantasma;
import com.Entidades.Nodo;
import com.Entidades.NodoRF;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Junior on 01/04/2018.
 */
public class SistemaUtil {

    public static String tipo ="";
    public static boolean terminal=false;

    public static String obtenerHora(){
        Calendar calendario = Calendar.getInstance();
        int hora, minutos, segundos,milisegundos;
        hora =calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);
        milisegundos = calendario.get(Calendar.MILLISECOND);
        return hora + ":" + minutos + ":" + segundos + ":" + milisegundos;
    }
    
    
    public synchronized static void pilotoAutomatico(String args[]){
             terminal = true;  
             if (args[0].equals("fantasma")){
                String direcciones[] = adaptadoresDisponibles();
                EjecutarComando.linea("network " + direcciones[Integer.parseInt(args[1]) - 1]
                               + " 2000" + " central");
                       SistemaUtil.tipo = "fantasma";
                       EjecutarComando.linea("listen");
              }
              
              if (args[0].equals("miembro")){
                   EjecutarComando.linea("loadresources");
                   String direcciones[] = adaptadoresDisponibles();
                   EjecutarComando.linea("network " + direcciones[Integer.parseInt(args[1]) - 1]
                               + " " + seleccionarPuerto() + " miembro");
                   SistemaUtil.tipo = "miembro";
                   EjecutarComando.linea("listen");
                   EjecutarComando.linea("listenfile");
                   EjecutarComando.linea("network " + args[2] + " 2000 central");
                   try {
                        NodoRF mynodorf = new NodoRF(Nodo.obtenerInstancia().getDireccion(),Nodo.getInstancia().getPuertopeticion());
                        ConexionUtils.obtenerInstancia().enviarMensaje(new Mensaje("addnode",mynodorf,Fantasma.obtenerInstancia()));
                        Thread.sleep(10000);
                        EjecutarComando.linea("share");
                        Thread.sleep(10000);
                        String [] archivos = {"archivo1.jpg","archivo2.mp3","archivo3.txt"};
                        Random r = new Random();
                        Integer valor = r.nextInt(3);
                        EjecutarComando.linea("search "+archivos[valor]);
                        Thread.sleep(60000);
                        System.out.println("Piloto automatico finalizado");
                        System.out.println("Algunos datos recogidos");
                        System.out.println("-----------------------------------------");
                        EjecutarComando.linea("listfinger");
                   } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InterruptedException ex) {
                      Logger.getLogger(SistemaUtil.class.getName()).log(Level.SEVERE, null, ex);
                  }

              }
              
    }
    
      
     private static String [] adaptadoresDisponibles(){
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
       return direcciones; 
     }
    
     private static String seleccionarPuerto(){
        String respuesta ="";
        Integer valor =0;
        while ((valor<2001)||(valor>5000)){
            Random r = new Random();
            valor = r.nextInt(5000);
        }
        respuesta = valor.toString();
        return respuesta;
    }
     
        
    public static void generarReporte(){
       ArchivoThread generar = new ArchivoThread();
       new Thread(generar).start();     
    }
    
    private static class ArchivoThread implements Runnable{
        public void run() {
            try {
                String ruta = "resultados.txt";
                File archivo = new File(ruta);
                BufferedWriter bw;
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write("------------------------------------------------------\n");
                bw.newLine();
                bw.write("Resultados de Prueba de Estres \n");
                bw.newLine();
                bw.write("------------------------------------------------------\n");
                bw.newLine();
                bw.write("Nº Nodos estables: "+Estadistica.getNodos_estables()+" \n");
                bw.newLine();
                bw.write("Nº Nodos caidos: "+Estadistica.getNodos_caidos()+" \n");
                bw.newLine();
                bw.write("Nº tablas generadas: "+Estadistica.getTablas_generadas()+"\n");
                bw.newLine();
                bw.write("------------------------------------------------------\n");
                bw.newLine();
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(SistemaUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
}
