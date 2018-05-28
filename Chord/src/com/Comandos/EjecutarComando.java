package com.Comandos;

import java.util.Arrays;

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
public class EjecutarComando {


    /**
     * Metodo encargado de la ejecución de un comando colocado por texto en pantalla
     * @param line representa la linea de comando como tal
     */
    public static void linea(String line){
        CommandManager manager = CommandManager.getIntance();
        if ("EXIT".equals(line.toUpperCase())) {
            System.exit(0);
        }
        if (line.trim().length() == 0) {
            //  continue;
        }else {
            String[] commands = CommandUtil.tokenizerArgs(line);
            String commandName = commands[0];
            String[] commandArgs = null;
            if (commands.length > 1) {
                commandArgs = Arrays.copyOfRange(commands, 1, commands.length);
            }
            ICommand command = manager.getCommand(commandName);
            command.ejecutar(commandArgs, System.out);
            System.out.println("");
        }

    }
}
