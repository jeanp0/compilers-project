/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g6.project.operations;

import static com.g6.project.Main.ANSI_GREEN;
import static com.g6.project.Main.ANSI_RED;
import static com.g6.project.Main.ANSI_RESET;
import com.g6.project.entities.Actividad;
import com.g6.project.operations.interfaces.IOperacionesActividad;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Jeanpier
 */
public class OperacionesActividad implements IOperacionesActividad, Serializable {

    private HashMap<String, Actividad> listaActividades;

    public HashMap<String, Actividad> getListaActividades() {
        return listaActividades;
    }

    public void setListaActividades(HashMap<String, Actividad> listaActividades) {
        this.listaActividades = listaActividades;
    }

    public OperacionesActividad() {
        listaActividades = new HashMap<>();
    }

    @Override
    public HashMap<String, Actividad> agregarActividades() {
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        HashMap<String, Actividad> lista = null;

        try {
            file = new File(IOperacionesActividad.ACTIVIDADES);
            if (file.length() == 0) {
                return null;
            }
            fis = new FileInputStream(file);
            lista = new HashMap<>();
            boolean check = true;
            while (check) {
                try {
                    ois = new ObjectInputStream(fis);
                    Object aux = ois.readObject();
                    if (aux instanceof Actividad) {
                        Actividad actividad = (Actividad) aux;
                        lista.put(actividad.getNom_act(), actividad);
                    }
                } catch (EOFException ex) {
                    check = false;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo de Actividades no fue encontrado: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("La clase Actividad no fue encontrada: " + ex.getMessage());
        } catch (IOException io) {
            System.out.println("Error IO: " + io.getMessage());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException io) {
                System.out.println("Error al cerrar el stream en agregar Actividades" + io.getMessage());
            }
        }
        return lista;
    }

    @Override
    public Actividad buscar(String nombre) {
        Actividad actEncontrada = null;
        this.listaActividades = this.agregarActividades();
        if (this.listaActividades == null) {
            return null;
        }
        Actividad actividad = this.listaActividades.get(nombre);
        if (actividad != null) {
            actEncontrada = actividad;
        }

        return actEncontrada;
    }

    //GUARDAR EN ARCHIVO BINARIO LAS OBRAS
    @Override
    public boolean guardar(Actividad act) {

        Actividad act_save = this.buscar(act.getNom_act());
        if (act_save != null) {
            System.out.println(ANSI_RED + "Ya existe la actividad" + ANSI_RESET);
            return false;
        }
        File file = null;
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        boolean res = true;
        try {
            file = new File(IOperacionesActividad.ACTIVIDADES);
            if (!file.exists()) {
                file.createNewFile();
            }
            fout = new FileOutputStream(file, true);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(act);

            System.out.println("\n" + ANSI_GREEN + "La Actividad se ha guardado correctamente.\n" + ANSI_RESET);
        } catch (IOException ioex) {
            System.out.println("Error en guardar Actividad: " + ioex.getMessage());
            res = false;
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException io) {
                System.out.println("Error al cerrar stream en guardar Actividad: " + io.getMessage());
                res = false;
            }
        }
        return res;
    }

    @Override
    public void mostrarActividadFull(String nombre) {
        Actividad actividad = this.buscar(nombre);
        OperacionesObra obraOP = new OperacionesObra();

        if (actividad == null) {
            System.out.println(ANSI_RED + "\nNo existen actividades con ese nombre" + ANSI_RESET);
        } else {
            System.out.println(actividad.printFull());
            System.out.println("\nPertenece a la obra: " + obraOP.buscar(actividad.getCod_obra()).toString() + ANSI_RESET);
        }
    }

}
