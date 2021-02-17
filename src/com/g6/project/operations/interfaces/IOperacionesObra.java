/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g6.project.operations.interfaces;

import com.g6.project.entities.Obra;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Jeanpier
 */
public interface IOperacionesObra {

    static final String OBRAS = "archivos/obras.obj";

    HashMap<String, Obra> agregarObras();

    Obra buscar(String codigo);

    boolean guardar(Obra obra);

    public Obra elegirObra(Scanner scan);

    void mostrarObraFull(String codigo);
    
    boolean validateWithLex(Object valueToEvaluate, String tokenName);
}
