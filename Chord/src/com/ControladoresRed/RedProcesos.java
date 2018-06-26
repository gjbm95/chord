package com.ControladoresRed;

import com.Comandos.EjecutarComando;
import com.Comandos.EnviarMensajeCommand;
import com.Comandos.RecibirArchivoCommand;
import com.Entidades.Estadistica;
import com.Entidades.Fantasma;
import com.Entidades.Nodo;
import com.Entidades.NodoRF;
import com.Entidades.Recurso;
import com.Utils.LoggerUtil;
import com.Utils.RespuestaUtils;
import com.Utils.SistemaUtil;
import static com.Utils.SistemaUtil.obtenerHora;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
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
public class RedProcesos extends Thread {

    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Mensaje mensaje;

    public RedProcesos(Mensaje mensaje,ObjectInputStream ois,ObjectOutputStream oos){
       this.mensaje = mensaje;
       this.ois = ois;
       this.oos = oos;
    }


    public void run(){
        try {
            System.out.println("Funcion: "+this.mensaje.getFuncion()+" Tiempo de llegada: "+ SistemaUtil.obtenerHora());
            this.realizarAccion(this.mensaje,this.ois,this.oos);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo encargado de ejecutar una accion en base a un comando recibido por socket
     * @param mensaje
     * @param ois
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public synchronized void realizarAccion(Mensaje mensaje,ObjectInputStream ois,ObjectOutputStream oos)
                                                              throws IOException, ClassNotFoundException {
        String funcion = mensaje.getFuncion();
        //Reportando
        //-------------------------------------------------------
        if (SistemaUtil.tipo.equals("fantasma"))
        SistemaUtil.generarReporte();
        //------------------------------------------------------- 
            if(funcion.equals("addnode")){
                    NodoRF nodo = (NodoRF) mensaje.getData();
                    EjecutarComando.linea("addnode "+nodo.getDireccion()+" "+nodo.getPuertopeticion());
                    LoggerUtil.obtenerInstancia().Log("Agregado nodo "+nodo.getDireccion()+" tiempo: "+SistemaUtil.obtenerHora());
                    SistemaUtil.reportarTiempo("addnode", "final", nodo); 
                    System.out.println("Se ha agregado un nodo de forma exitosa");
                    Estadistica.add_nodos();
                    EjecutarComando.linea("order");
                    oos.writeObject(new Mensaje("addnode","",nodo));
                    EjecutarComando.linea("generarFinger");
                    if (SistemaUtil.terminal)
                    EjecutarComando.linea("listring");
            }
            if(funcion.equals("deletenode")){
                    if (mensaje.getData() instanceof Nodo) {
                        Nodo nodo = (Nodo) mensaje.getData();
                        EjecutarComando.linea("deletenode " + nodo.getDireccion() + " " + nodo.getPuertopeticion());
                        EjecutarComando.linea("order");
                        oos.writeObject(new Mensaje("finalice", "", nodo));
                        EjecutarComando.linea("generarFinger");
                    }else if (mensaje.getData() instanceof  NodoRF){
                        NodoRF nodo = (NodoRF) mensaje.getData();
                        EjecutarComando.linea("deletenode " + nodo.getDireccion() + " " + nodo.getPuertopeticion());
                        EjecutarComando.linea("order");
                        oos.writeObject(new Mensaje("finalice", "", nodo));
                        EjecutarComando.linea("generarFinger");
                    }
            }
            if(funcion.equals("addtable")){
                LoggerUtil.obtenerInstancia().Log("Generando Finger "+Nodo.getInstancia().getDireccion()+" tiempo: "+obtenerHora());
                    Nodo.getInstancia().getTablaRecursos().clear();
                    ArrayList<NodoRF> anillo = (ArrayList<NodoRF>) mensaje.getData();
                    HashMap<Integer, NodoRF>  tabla = new HashMap<Integer, NodoRF>();
                int valorFinger;
                for (NodoRF nodo : anillo) {
                    int indice = 1;
                    for (int i = 1; i <= 5; i++) {
                        valorFinger = Nodo.getInstancia().getHash().intValue() + ((int) Math.pow(2, i - 1));
                        for (NodoRF aux : anillo) {
                            if (aux.getHash().intValue() >= valorFinger) {
                                tabla.put(indice, aux);
                                indice += 1;
                                break;
                            }
                        }
                    }
                    if (tabla.isEmpty()) {
                        tabla.put(1, anillo.get(0));
                    }
                }
                 LoggerUtil.obtenerInstancia().Log("Finger Generado "+Nodo.getInstancia().getDireccion()+" tiempo: "+obtenerHora());
                    Nodo.getInstancia().setTabla(tabla);
                    LoggerUtil.obtenerInstancia().Log("Finger Almacenado "+Nodo.getInstancia().getDireccion()+" tiempo: "+obtenerHora());
                    SistemaUtil.reportarTiempo("generarFinger", "final", new NodoRF(Nodo.getInstancia().getDireccion(), Nodo.getInstancia().getPuertopeticion()));
                    System.out.println("Se ha agregado la tabla de forma exitosa");
                    if (SistemaUtil.terminal)
                    EjecutarComando.linea("listfinger");
                    oos.writeObject("");
                    Nodo.getInstancia().setCompartir(true);
                    System.out.println("Compartiendo...");
                            for (Recurso recurso : Nodo.getInstancia().getRecursos()) {
                                if (recurso.getHash().longValue() > anillo.get(anillo.size() - 1).getHash().longValue()) {
                                    ConexionUtils.obtenerInstancia().enviarMensaje(new Mensaje("resource", recurso.getHash(),
                                            Nodo.getInstancia(), anillo.get(0)));
                                } else

                                    for (NodoRF nodo : anillo) {
                                        if (recurso.getHash().longValue() <= nodo.getHash().longValue()) {
                                            ConexionUtils.obtenerInstancia().enviarMensaje(new Mensaje("resource", recurso.getHash(),
                                                    Nodo.getInstancia(), nodo));
                                            break;
                                        }
                                    }

                            }
            }
            if(funcion.equals("getip")){
                    Long hash = (Long)mensaje.getData();
                    NodoRF nodo = Fantasma.obtenerInstancia().obtenerNodo(hash);
                    oos.writeObject(new Mensaje("getip",nodo,nodo));
            }

            if(funcion.equals("havefile")){
                    Long hash = (Long)mensaje.getData();
                    if(Nodo.getInstancia().buscarRecurso(hash)!=null){
                        oos.writeObject(new Mensaje("havefile",Nodo.getInstancia(),Nodo.getInstancia()));
                    }else{

                    }
            }

            if(funcion.equals("download")){
                    Long hash = (Long) mensaje.getData();
                    if (Nodo.getInstancia().buscarRecurso(hash) != null) {
                        oos.writeObject(new Mensaje("havefile",Nodo.getInstancia(),Nodo.getInstancia()));
                    }else {
                        System.out.println("redireccionando...");
                        NodoRF hashnode = Nodo.obtenerInstancia().seleccionarNodo(hash);
                        Mensaje data = new Mensaje("getip",hashnode.getHash().longValue(),Fantasma.obtenerInstancia());
                        Mensaje respuesta = (Mensaje) ConexionUtils.obtenerInstancia().enviarMensaje(data);
                        NodoRF nodo = (NodoRF) respuesta.getData();
                        if (!nodo.getDireccion().equals(mensaje.getOrigen().getDireccion())){
                        data = new Mensaje("download",hash,mensaje.getOrigen(),nodo);
                        nodo = (NodoRF)ConexionUtils.obtenerInstancia().enviarMensaje(data);
                        oos.writeObject(nodo);
                        }else{
                            oos.writeObject(null);
                        }
                    }
            }

            if(funcion.equals("resource")){
                Nodo nodo =(Nodo)mensaje.getOrigen();
                Long hash = Long.parseLong("0");
                if (mensaje.getData() instanceof BigInteger)
                hash = ((BigInteger)mensaje.getData()).longValue();
                if (mensaje.getData() instanceof Long)
                hash = (Long)mensaje.getData();
                
                Nodo.getInstancia().agregarRecurso(nodo, hash);
                System.out.println("Actualizando tabla de recursos");
                oos.writeObject("asignado");
            }

            if(funcion.equals("who")){

                Nodo nodo =(Nodo)mensaje.getOrigen();
                Long hash = (Long)mensaje.getData();
                ArrayList<Nodo> respuesta = Nodo.getInstancia().tieneRecurso(hash);
                if (respuesta!=null){
                    oos.writeObject(respuesta);
                }else if (!Nodo.getInstancia().isSolicitante()){
                    System.out.println("Redireccionando consulta...");
                        NodoRF hashnode = Nodo.obtenerInstancia().seleccionarNodo(hash);
                    if (!(nodo.getDireccion().equals(Nodo.getInstancia().getDireccion()))
                            &&!(nodo.getPuertopeticion()==Nodo.obtenerInstancia().getPuertopeticion()))
                        ConexionUtils.obtenerInstancia().enviarMensaje(new Mensaje("who", hash,
                                nodo, hashnode));
                    else{
                        oos.writeObject(null);
                    }
                }else {
                    Nodo.getInstancia().setSolicitante(false);
                    oos.writeObject(null);
                }
            }

            if(funcion.equals("getresourse")){
                Object object =  ois.readObject();
                if (object instanceof String){
                    String datos = (String)object;
                    String atributos [] = datos.split(":");

                }
            }
            if(funcion.equals("first")){
                if( Fantasma.obtenerInstancia().getAnillo()!= null && !Fantasma.obtenerInstancia().getAnillo().isEmpty())
                    oos.writeObject(Fantasma.obtenerInstancia().getAnillo().get(0));
                else
                    oos.writeObject(null);
            }

            if(funcion.equals("clean")){
                EjecutarComando.linea("cleanresources " + ((String)mensaje.getData()).split(":")[0] +
                " " + ((String)mensaje.getData()).split(":")[1]);
                oos.writeObject("");
            }

            if(funcion.equals("size")){
                Long hashArchivo = (Long)(mensaje.getData());
                oos.writeObject(Nodo.getInstancia().buscarRecurso(hashArchivo).getTamano());
            }

    }
}
