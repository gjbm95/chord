package com.Entidades;

import com.ControladoresRed.ConexionUtils;
import com.ControladoresRed.Mensaje;

import java.io.Serializable;
import java.util.ArrayList;
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
public class Fantasma extends Miembro  implements Serializable {

    private ArrayList<NodoRF> anillo;

    public ArrayList<NodoRF> getAnillo() {
        return anillo;
    }

    public void setAnillo(ArrayList<NodoRF> anillo) {
        this.anillo = anillo;
    }

    public static Fantasma instancia;

    private Fantasma (){
      anillo = new ArrayList<NodoRF>();
    }

    public static Fantasma obtenerInstancia(){
        if (instancia==null)
            instancia = new Fantasma();
        return instancia;
    }

    /**
     * Metodo encargado de devolver la direccion IP y el puerto de escucha de un determinado
     * nodo dando el hash de la direccion IP del mismo.
     * @param iphash
     * @return
     */
    public NodoRF obtenerNodo(Long iphash){
        NodoRF respuesta =null;
        for(NodoRF nodo :this.anillo){
            if (iphash==nodo.getHash().longValue()){
              respuesta = nodo;
            }
        }
        System.out.println("Se va a mandar: "+ respuesta);
        return respuesta;
    }

    public HashMap<Integer, Long> generarFinger(){
        Fantasma f= Fantasma.obtenerInstancia();
        int valorFinger;
        int suma=2;
        HashMap<Integer,Long> tabla = new HashMap<Integer,Long>();
        if (!f.getAnillo().isEmpty()){
            Long primero = f.getAnillo().get(0).getHash().longValue();
            ArrayList<NodoRF> anillo = f.getAnillo();
            for(NodoRF nodo : anillo) {
                tabla = new HashMap<Integer, Long>();
                for (int i = 1; i <= 5; i++) {
                    int indice = 1;
                    valorFinger = nodo.getHash().intValue() + ((int) Math.pow(2, i - 1));
                    for (NodoRF aux : anillo) {

                        if (aux.getHash().intValue() >= valorFinger) {
                            tabla.put(indice, aux.getHash().longValue());
                            indice += 1;
                            break;
                        }
                    }
                }
                if (tabla.isEmpty()) {
                    tabla.put(1, primero);
                }

            }
        }
        return tabla;
    }

}
