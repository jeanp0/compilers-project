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
public class MedioAmbiente extends Obra implements Serializable {

    private String bioma;
    private double area;
    private OperacionesObra opObra;

    public MedioAmbiente() {
        this.opObra= new OperacionesObra();
    }

    public MedioAmbiente(String codigo, String titulo, Date fechaI, Date fechaF,
            double presupuesto, int n_actividades, HashMap<String, Actividad> actividades,
            Responsable respon, String bioma, double area) {
        super(codigo, titulo, fechaI, fechaF, presupuesto, n_actividades, actividades, respon);
        this.bioma = bioma;
        this.area = area;
    }

    public String getBioma() {
        return bioma;
    }

    public void setBioma(String bioma) {
        this.bioma = bioma;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    @Override
    public String toString() {

        return super.toString() + " | Tipo de bioma: " + this.bioma + " | Area (m^2): " + this.area + ANSI_RESET;
    }

    @Override
    public void llenarDatosObra(Scanner scan) {
        scan.useDelimiter("\n");
        String tempS = "";

        super.llenarDatosObra(scan);
        do {
            System.out.print("Bioma en que se hará la obra: ");
            tempS = scan.next();

            if (opObra.validateWithLex(tempS, "LETTER") == true) {
                this.bioma = tempS;
                break;
            } else {
                System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (alfabético)." + ANSI_RESET);
            }
        } while (true);

        do {
            System.out.print("Area de trabajo en el bioma (m^2):  ");
            tempS = scan.next();

            if (opObra.validateWithLex(tempS, "NUMBER") == true) {
                this.area = Double.parseDouble(tempS);
                break;
            } else {
                System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (Númerico)." + ANSI_RESET);
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
