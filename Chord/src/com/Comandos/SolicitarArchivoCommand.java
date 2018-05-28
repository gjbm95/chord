package com.Comandos;

import com.Entidades.Recurso;

import java.io.*;
import java.net.Socket;
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
public class SolicitarArchivoCommand extends BaseCommand {

    public static final String COMMAND_NAME="";

    @Override
    public String obtenerNombreComando() {
        return null;
    }

    @Override
    public void ejecutar(String[] args, OutputStream out) {

    }
}
