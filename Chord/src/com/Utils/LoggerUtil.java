/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author pepo
 */

public class LoggerUtil {

    private static Logger logger;
    private static FileHandler fh;
    private static LoggerUtil log;

    public LoggerUtil() {
        logger = Logger.getLogger(this.getClass().getSimpleName());
    }

    public static LoggerUtil obtenerInstancia() {
        if (log == null) {
            log = new LoggerUtil();
        }
        return log;
    }

    public static void Log(String informacion) {
        try {
            fh = new FileHandler("Tiempos.txt", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
            
            logger.info(informacion);
            
            fh.close();
            logger.removeHandler(fh);
        } catch (IOException ex) {
            Logger.getLogger(LoggerUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(LoggerUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
