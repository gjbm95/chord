package com.Comandos;

import com.ControladoresRed.ConexionUtils;
import com.ControladoresRed.Mensaje;
import com.Entidades.Fantasma;
import com.Entidades.Nodo;
import com.Entidades.NodoRF;
import com.Utils.SistemaUtil;

import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;

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
public class ExitCommand extends BaseCommand {

    public static final String COMMAND_NAME = "finish";

    @Override
    public String obtenerNombreComando() {
        return COMMAND_NAME;
    }

    @Override
    public void ejecutar(String[] args, OutputStream out) {
            if (SistemaUtil.tipo.equals("miembro")) {
                Mensaje mensaje =(Mensaje)ConexionUtils.obtenerInstancia().enviarMensaje(new Mensaje("deletenode",
                        Nodo.getInstancia(),Fantasma.obtenerInstancia()));
                if (mensaje.getFuncion().equals("finalice")){
                    System.exit(0);
                }
            }else {
                System.exit(0);
            }
    }
}
