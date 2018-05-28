package com.Comandos;

import com.Entidades.Fantasma;
import com.Entidades.Nodo;
import com.Entidades.NodoRF;

import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

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
public class OrganizarAnilloCommand extends BaseCommand{

    public static final String COMMAND_NAME = "order";

    @Override
    public String obtenerNombreComando() {
        return COMMAND_NAME;
    }

    @Override
    public void ejecutar(String[] args, OutputStream out) {
        ArrayList<Integer> original = new ArrayList<Integer>();
        ArrayList<NodoRF> resultado = new ArrayList<NodoRF>();
        Fantasma fantasma = Fantasma.obtenerInstancia();
        for (NodoRF elemento : fantasma.getAnillo())
        {
            original.add(elemento.getHash().intValue());
        }
        Collections.sort(original);
        for(int elemento : original){
            for(NodoRF organizando : fantasma.getAnillo()){
                if ((elemento == organizando.getHash().intValue())&&(!existe(organizando.getDireccion()
                        ,organizando.getPuertopeticion(),resultado)))
                    resultado.add(organizando);
            }
        }
        fantasma.setAnillo(resultado);
    }

    public boolean existe(String ip,int puerto,ArrayList<NodoRF> list){
        for(NodoRF nodo: list)
        {
            if ((nodo.getDireccion().equals(ip))&&(nodo.getPuertopeticion()==puerto))
            {
                return true;
            }
        }
        return false;
    }
}
