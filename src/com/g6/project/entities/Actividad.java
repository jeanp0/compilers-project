/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g6.project.entities;

import static com.g6.project.Main.ANSI_GREEN;
import static com.g6.project.Main.ANSI_PURPLE;
import static com.g6.project.Main.ANSI_RED;
import static com.g6.project.Main.ANSI_RESET;
import com.g6.project.operations.OperacionesResponsable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import com.g6.project.operations.OperacionesObra;

/**
 *
 * @author Jeanpier
 */
public class Actividad implements Serializable {

    private String nom_act;
    private Responsable respon;
    private double monto_util;
    private String cod_obra;
    private int n_trab;
    protected OperacionesObra opObra;


    public Actividad() {
        this.respon = new Responsable();
        this.opObra = new OperacionesObra();
        
    }

    public Actividad(String nom_act, Responsable respon, double monto_util,
            String cod_obra, int n_trab) {
        this.nom_act = nom_act;
        this.respon = respon;
        this.cod_obra = cod_obra;
        this.monto_util = monto_util;
        this.n_trab = n_trab;
    }

    public String getNom_act() {
        return nom_act;
    }

    public void setNom_act(String nom_act) {
        this.nom_act = nom_act;
    }

    public Responsable getRespon() {
        return respon;
    }

    public void setRespon(Responsable respon) {
        this.respon = respon;
    }

    public double getMonto_util() {
        return monto_util;
    }

    public void setMonto_util(double monto_util) {
        this.monto_util = monto_util;
    }

    public String getCod_obra() {
        return cod_obra;
    }

    public void setCod_obra(String cod_obra) {
        this.cod_obra = cod_obra;
    }

    public int getN_trab() {
        return n_trab;
    }

    public void setN_trab(int n_trab) {
        this.n_trab = n_trab;
    }

    public void leerDatosActividad(Scanner scan, Obra obra) {
//        int j = 0;
//        String codigo;
        scan.useDelimiter("\n");
        String tempS = "";
        System.out.println("\nPor favor, acontinuación ingrese los datos referentes a la actividad:");;
//        do{
//            if(j > 0){
//                System.out.print("Código de obra no encontrado. Escribe otro que si sea válido por favor: ");
//            }
//            codigo = scan.next();
//            for(Map.Entry<String, Obra> entrada: obraOP.agregarObras().entrySet()){
//                if(entrada.getKey().equals(codigo)){
//                    this.cod_obra = codigo;
//                    break;
//                }
//            }
//            j++;
//        }while(this.cod_obra == null);
               
        do {
            System.out.print("Nombre de actividad: ");
            tempS = scan.next();

            if (opObra.validateWithLex(tempS, "LETTER") == true) {
                this.nom_act = tempS;
                break;
            } else {
                System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (alfabético)." + ANSI_RESET);
            }
        } while (true);

        do {
            System.out.print("Monto a utilizar para la actividad ($): ");
            tempS = scan.next();

            if (opObra.validateWithLex(tempS, "NUMBER") == true) {
                this.monto_util = Double.parseDouble(tempS);
                break;
            } else {
                System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (numérico)." + ANSI_RESET);
            }
        } while (true);
        
        this.cod_obra = obra.getCodigo();
        System.out.print("Código de obra de la actividad: " + this.cod_obra + "\n");
        
        do {
            System.out.print("Número de trabajadores involucrados: ");
            tempS = scan.next();

            if (opObra.validateWithLex(tempS, "NUMBER") == true) {
                this.n_trab = Integer.parseInt(tempS);
                break;
            } else {
                System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (numérico)." + ANSI_RESET);
            }
        } while (true);

        scan.nextLine();
    }

    public void asignarResponsable(Scanner scan) {
        OperacionesResponsable responsableOP = new OperacionesResponsable();
        ArrayList<Responsable> listaResponsables = responsableOP.leerTodos();
        int i = 0, j = 0;
        String cedula;

        System.out.println("\nLista de Responsables disponibles: ");
        for (Responsable responsable : listaResponsables) {
            System.out.println("Responsable número " + (i + 1) + ":");
            System.out.println("\tCédula: " + responsable.getCedula());
            System.out.println("\tNombre: " + responsable.getNombre());
            System.out.println("\tApellido: " + responsable.getApellido());
            System.out.println("\tCargo: " + responsable.getCargo() + "\n");
            i++;
        }

        System.out.print("\nIngrese la cédula del Responsable para ésta Actividad: ");
        this.respon = null;
        do {
            if (j > 0) {
                System.out.print(ANSI_RED + "Cédula no existente en Archivos, intente con otra por favor: " + ANSI_RESET);
            }
            cedula = scan.next();
            for (Responsable responsable : listaResponsables) {
                if (responsable.getCedula().equals(cedula)) {
                    this.respon = responsable;
                    break;
                }
            }
            j++;
        } while (this.respon == null);

        if (this.respon != null) {
            System.out.println(ANSI_GREEN + "\n¡Responsable seleccionado con éxito!" + ANSI_RESET);
            System.out.println(this.respon.toString());
        } else {
            System.out.println(ANSI_RED + "No se encontró al responsable con dicha cédula." + ANSI_RESET);
        }
    }

    @Override
    public String toString() {
        return ANSI_PURPLE + "\n-Actividad- | " + this.nom_act + " | Código de obra: " + this.cod_obra
                + " | Monto utilizado: " + this.monto_util + " | Número de trabajadores: " + this.n_trab + ANSI_RESET;
    }

    public String printFull() {
        return this.toString() + this.respon.toString();
    }
}
