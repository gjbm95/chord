package com.Comandos;

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
public abstract class BaseCommand implements ICommand  {

    @Override
    public abstract String obtenerNombreComando();

    @Override
    public abstract void ejecutar(String[] args, OutputStream out);

    public void write(OutputStream out, String message) {
        try {
            out.write(message.getBytes());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

