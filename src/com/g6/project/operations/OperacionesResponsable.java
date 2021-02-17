/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g6.project.operations;

import com.g6.project.entities.Responsable;
import com.g6.project.operations.interfaces.IOperacionesResponsable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Jeanpier
 */
public class OperacionesResponsable implements IOperacionesResponsable, Serializable {

    private ArrayList<Responsable> listaResponsables;

    public OperacionesResponsable() {
        listaResponsables = new ArrayList();
    }

    public ArrayList<Responsable> getListaResponsables() {
        return listaResponsables;
    }

    public void setListaResponsables(ArrayList<Responsable> listaResponsables) {
        this.listaResponsables = listaResponsables;
    }

    @Override
    public ArrayList<Responsable> leerTodos() {
        ArrayList<Responsable> lista = null;
        File file = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            file = new File(IOperacionesResponsable.RESPONSABLES_DISP);
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            lista = new ArrayList();
            String linea = "";
            while (br.ready()) {
                linea = br.readLine();
                String[] datos = linea.split(";");
                Responsable responsable = new Responsable();
                responsable.setCedula(datos[0]);
                responsable.setNombre(datos[1]);
                responsable.setApellido(datos[2]);
                responsable.setCargo(datos[3]);
                lista.add(responsable);
            }
        } catch (FileNotFoundException fe) {
            System.out.println("Error en leer responsable. " + fe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Error en leer responsable. " + ioe.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException io) {
                System.out.println("Error al cerrar en leer datos de responsables. " + io.getMessage());
            }
        }
        return lista;
    }

    @Override
    public Responsable asignarResponsable() {
        Responsable responsable;
        int i = 0;
        if (listaResponsables.size() > 0) {
            responsable = listaResponsables.get(i++);
            return responsable;
        } else {
            System.out.println("No hay responsables. Revisar Archivo.");
            return null;
        }
    }
}
