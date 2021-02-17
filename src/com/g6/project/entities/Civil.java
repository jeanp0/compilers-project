/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g6.project.entities;

import static com.g6.project.Main.ANSI_RED;
import static com.g6.project.Main.ANSI_RESET;
import com.g6.project.operations.OperacionesObra;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Jeanpier
 */
public class Civil extends Obra implements Serializable {

    private double long_obra;
    private String entidad_gober;
    private OperacionesObra opObra;

    public double getLong_obra() {
        return long_obra;
    }

    public void setLong_obra(double long_obra) {
        this.long_obra = long_obra;
    }

    public String getEntidad_gober() {
        return entidad_gober;
    }

    public void setEntidad_gober(String entidad_gober) {
        this.entidad_gober = entidad_gober;
    }

    public Civil() {
        this.opObra = new OperacionesObra();
    }

    public Civil(String codigo, String titulo, Date fechaI, Date fechaF,
            double presupuesto, int n_actividades, HashMap<String, Actividad> actividades,
            Responsable respon, double long_obra, String entidad_gober) {
        super(codigo, titulo, fechaI, fechaF, presupuesto, n_actividades, actividades, respon);
        this.long_obra = long_obra;
        this.entidad_gober = entidad_gober;
        //this.opObra = new OperacionesObra();
    }

    @Override
    public String toString() {
        return super.toString() + " | Longitud de la obra: " + this.long_obra + " | Entidad Gubernamental: " + this.entidad_gober + ANSI_RESET;
    }

    @Override
    public void llenarDatosObra(Scanner scan) {
        // cambiamos el delimitador para que reconozca los espacios
        scan.useDelimiter("\n");
        Double tempD = 0d;
        String tempS = "";

        super.llenarDatosObra(scan);
        do {
            System.out.print("Longitud de la obra (m): ");
            tempS = scan.next();

            if (opObra.validateWithLex(tempS, "NUMBER") == true) {
                this.long_obra = Double.parseDouble(tempS);
                break;
            } else {
                System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (numérico)." + ANSI_RESET);
            }
        } while (true);

        do {
            System.out.print("Entidad gobernamental: ");
            tempS = scan.next();

            if (opObra.validateWithLex(tempS, "LETTER") == true) {
                this.entidad_gober = tempS;
                break;
            } else {
                System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (alfabético)." + ANSI_RESET);
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
