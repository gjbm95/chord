package com.principal;

import com.Comandos.Descarga;
import com.Comandos.EjecutarComando;
import com.Entidades.Nodo;
import com.Utils.RespuestaUtils;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
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

public class Main {

    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------------");
        System.out.println("UCAB - Trabajo Especial de Grado");
        System.out.println("Autores: Garry Bruno / Carlos Valero");
        System.out.println("Tutor: Wilmer Pereira");
        System.out.println("-----------------------------------------------------------");
        System.out.println("Algoritmo de Direccionamiento por Hash para redes P2P");
        System.out.println("-----------------------------------------------------------");
        System.out.println("En caso de no conocer los comandos, escriba el comando help");
        System.out.println("-----------------------------------------------------------");
        System.out.println("Ingrese un comando:");
        Scanner in = new Scanner(System.in);
        while (true) {
            String line = in.nextLine();
            EjecutarComando.linea(line);
        }

    }



}
