/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g6.project.operations.interfaces;

import com.g6.project.entities.Responsable;
import java.util.ArrayList;

/**
 *
 * @author Jeanpier
 */
public interface IOperacionesResponsable {

    public static final String RESPONSABLES_DISP = "archivos/responsables.txt";

    ArrayList<Responsable> leerTodos();

    Responsable asignarResponsable();
}
