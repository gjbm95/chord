package com.Comandos;

import com.Entidades.Fantasma;
import com.Entidades.Nodo;
import com.Utils.SistemaUtil;

import java.io.OutputStream;

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
public class AsignarRedCommand extends BaseCommand {

    public static final String COMMAND_NAME = "network";

    @Override
    public String obtenerNombreComando() {
        return COMMAND_NAME;
    }

    @Override
    public void ejecutar(String[] args, OutputStream out) {
        if (args.length>1){
                if (args.length==3){
                    if (args[2].equals("miembro")) {
                    Nodo miembro = Nodo.obtenerInstancia();
                    miembro.setDireccion(args[0]);
                    miembro.setPuertopeticion(Integer.parseInt(args[1]));
                    miembro.setPuertoArchivo(Integer.parseInt(args[1]) + 1);
                    System.out.println("Se ha asignado la red correctamente");
                    } else if (args[2].equals("central")) {
                        Fantasma fantasma = Fantasma.obtenerInstancia();
                        fantasma.setDireccion(args[0]);
                        fantasma.setPuertopeticion(Integer.parseInt(args[1]));
                        System.out.println("Se ha asignado la red correctamente");
                    } else {
                        write(out, "Debe especificar el tipo de nodo");
                    }
                }else{
                    System.out.println("Faltan argumentos en la sentencia!");
                }
        }else if(args.length == 1)
            {
                if (args[0].equals("-s"))
                {
                    if (SistemaUtil.tipo.equals("miembro")) {
                        try {
                            System.out.println("-------------------------------------------------------");
                            System.out.println("Sus datos de conexion son: ");
                            System.out.println("-------------------------------------------------------");
                            Nodo miembro = Nodo.obtenerInstancia();
                            System.out.println("IP: " + miembro.getDireccion());
                            System.out.println("Hash IP: " + miembro.getHash());
                            System.out.println("Puerto de Escucha: " + miembro.getPuertopeticion());
                            System.out.println("Puerto de Archivos: " + miembro.getPuertoArchivo());
                            System.out.println("-------------------------------------------------------");
                        }catch (NullPointerException e){
                            System.out.println("Aun no se han colocado los datos de red");
                        }
                    }

                    if (SistemaUtil.tipo.equals("fantasma")) {
                        try {
                            System.out.println("-------------------------------------------------------");
                            System.out.println("Sus datos de conexion son: ");
                            System.out.println("-------------------------------------------------------");
                            Fantasma fantasma = Fantasma.obtenerInstancia();
                            System.out.println("IP: " + fantasma.getDireccion());
                            System.out.println("Hash IP: " + fantasma.getHash());
                            System.out.println("Puerto de Escucha: " + fantasma.getPuertopeticion());
                            System.out.println("-------------------------------------------------------");
                        }catch (NullPointerException e){
                            System.out.println("Aun no se han colocado los datos de red");
                        }
                    }

                    if (SistemaUtil.tipo.equals("")){
                        System.out.println("Usted no ha especificado su tipo de nodo");
                    }
                }
            }
    }
}
