/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g6.project.operations.interfaces;

import com.g6.project.entities.Actividad;
import com.g6.project.entities.Obra;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Jeanpier
 */
public interface IOperacionesActividad {

    static final String ACTIVIDADES = "archivos/actividades.obj";

    HashMap<String, Actividad> agregarActividades();

    Actividad buscar(String nombre);

    boolean guardar(Actividad act);

    void mostrarActividadFull(String nombre);
    
    
}
