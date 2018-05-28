package com.Comandos;

import com.ControladoresRed.ConexionUtils;
import com.ControladoresRed.Mensaje;
import com.Entidades.Fantasma;
import com.Entidades.Nodo;
import com.Entidades.NodoRF;
import com.Entidades.Recurso;
import com.Utils.RespuestaUtils;

import java.io.File;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
public class CargarRecursosCommand extends BaseCommand {

    public static final String COMMAND_NAME = "loadresources";

    @Override
    public String obtenerNombreComando() {
        return COMMAND_NAME;
    }

    @Override
    public void ejecutar(String[] args, OutputStream out) {
        try {
            File carpeta = new File("recursos");
            System.out.println("Cargando recursos...");
            Nodo nodo = Nodo.obtenerInstancia();
            ArrayList<Recurso> recursos = new ArrayList<Recurso>();
            Recurso recurso;
            int id = 0;
            for (File archivo : carpeta.listFiles()) {
                recurso = new Recurso();
                recurso.setNombre(archivo.getName());
                recurso.setTamano(archivo.length());
                recurso.setId(id++);
                recurso.setDescargas(0);
                recurso.setHash(RespuestaUtils.generarHash(recurso.getNombre()));
                recurso.setRuta(archivo.getPath());
                recurso.setPropietario(nodo.getDireccion());
                recursos.add(recurso);
            }
            nodo.setRecursos(recursos);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
