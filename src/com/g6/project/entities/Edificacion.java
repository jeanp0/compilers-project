/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g6.project.entities;

import static com.g6.project.Main.ANSI_RESET;
import static com.g6.project.Main.ANSI_RED;
import com.g6.project.operations.OperacionesObra;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Jeanpier
 */
public class Edificacion extends Obra implements Serializable {

    private String tipo;
    private int n_pisos;
    private OperacionesObra opObra;


    public Edificacion() {
        this.opObra = new OperacionesObra();
    }

    public Edificacion(String codigo, String titulo, Date fechaI, Date fechaF,
            double presupuesto, int n_actividades, HashMap<String, Actividad> actividades,
            Responsable respon, String tipo, int n_pisos) {
        super(codigo, titulo, fechaI, fechaF, presupuesto, n_actividades, actividades, respon);
        this.tipo = tipo;
        this.n_pisos = n_pisos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getN_pisos() {
        return n_pisos;
    }

    public void setN_pisos(int n_pisos) {
        this.n_pisos = n_pisos;
    }

    @Override
    public String toString() {
        return super.toString() + " | Tipo de edificación: " + this.tipo + " | Número de pisos: " + this.n_pisos + ANSI_RESET;
    }

    @Override
    public void llenarDatosObra(Scanner scan) {
        scan.useDelimiter("\n");
        String tempS = "";

        super.llenarDatosObra(scan);
        do {
            System.out.print("Tipo de edificación: ");
            tempS = scan.next();

            if (opObra.validateWithLex(tempS, "LETTER") == true) {
                this.tipo = tempS;
                break;
            } else {
                System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (alfabético)." + ANSI_RESET);
            }
        } while (true);

        do {
            System.out.print("Número de pisos: ");
            tempS = scan.next();

            if (opObra.validateWithLex(tempS, "NUMBER") == true) {
                this.n_pisos = Integer.parseInt(tempS);
                break;
            } else {
                System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (numérico)." + ANSI_RESET);
            }
        } while (true);

        scan.nextLine();

        //super.getRespon().llenarDatosResponsable(scan);
    }

    @Override
    public String printFull() {
        return this.toString() + this.respon.toString();
    }
}
