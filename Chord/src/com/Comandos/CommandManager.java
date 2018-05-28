package com.Comandos;

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
public class CommandManager {

    private static CommandManager commandManager;

    private static final HashMap<String, Class<? extends ICommand>> COMMANDS =
            new HashMap<String, Class<? extends ICommand>>();

    /**
     * Este constructor permite el registro de cada uno de los comandos que presenta el
     * programa.
     */
    private CommandManager() {
        registCommand(ActualizarFingerCommand.COMMAND_NAME, ActualizarFingerCommand.class);
        registCommand(BuscarRecursoCommand.COMMAND_NAME, BuscarRecursoCommand.class);
        registCommand(OrganizarAnilloCommand.COMMAND_NAME, OrganizarAnilloCommand.class);
        registCommand(AgregarNodoCommand.COMMAND_NAME,AgregarNodoCommand.class);
        registCommand(EliminarNodo.COMMAND_NAME,EliminarNodo.class);
        registCommand(RecibirMensajeCommand.COMMAND_NAME, RecibirMensajeCommand.class);
        registCommand(ExitCommand.COMMAND_NAME, ExitCommand.class);
        registCommand(AsignarRedCommand.COMMAND_NAME, AsignarRedCommand.class);
        registCommand(TipoNodoCommand.COMMAND_NAME, TipoNodoCommand.class);
        registCommand(EnviarMensajeCommand.COMMAND_NAME, EnviarMensajeCommand.class);
        registCommand(HelpCommand.COMMAND_NAME,HelpCommand.class);
        registCommand(EnviarArchivoCommand.COMMAND_NAME,EnviarArchivoCommand.class);
        registCommand(RecibirArchivoCommand.COMMNAND_NAME,RecibirArchivoCommand.class);
        registCommand(CargarRecursosCommand.COMMAND_NAME,CargarRecursosCommand.class);
        registCommand(GenerarFingerCommand.COMMAND_NAME,GenerarFingerCommand.class);
        registCommand(ConsultarFingerCommand.COMMAND_NAME,ConsultarFingerCommand.class);
        registCommand(VerAnilloCommand.COMMAND_NAME,VerAnilloCommand.class);
        registCommand(VerRecursosCommand.COMMAND_NAME,VerRecursosCommand.class);
        registCommand(VerHashRecursoCommand.COMMAND_NAME,VerHashRecursoCommand.class);
        registCommand(InformarRecursosCommand.COMMAND_NAME,InformarRecursosCommand.class);
        registCommand(SegundoPlanoCommand.COMMAND_NAME,SegundoPlanoCommand.class);
        registCommand(LimpiarRecursosCommand.COMMAND_NAME,LimpiarRecursosCommand.class);
        registCommand(SeleccionarRedCommand.COMMAND_NAME,SeleccionarRedCommand.class);
    }

    /**
     * Permite la generacion de un Singleton de la clase CommandManager
     * @return
     */
    public static synchronized CommandManager getIntance() {
        if (commandManager == null) {
            commandManager = new CommandManager();
        }
        return commandManager;
    }

    /**
     * Permite obtener una clase que implemente la interfaz ICOMMAND y que sea herencia
     * de BaseCommand
     * @param commandName
     * @return
     */
    public ICommand getCommand(String commandName) {
        if (COMMANDS.containsKey(commandName.toUpperCase())) {
            try {
                return COMMANDS.get(commandName.toUpperCase()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return new ErrorCommand();
            }
        } else {
            return new NoEncontradoCommand();
        }
    }

    /**
     * Permite el registro de un comando en el mapa de hash.
     * @param commandName
     * @param command
     */
    public void registCommand(String commandName,
                              Class<? extends ICommand> command) {
        COMMANDS.put(commandName.toUpperCase(), command);
    }
}
