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
import com.g6.project.operations.OperacionesObra;
import com.g6.project.operations.OperacionesResponsable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Jeanpier
 */
public abstract class Obra implements Serializable {

    private static final long serialVersionUID = 6628353682162772374L;
    protected String codigo;
    protected String titulo;
    protected Date fechaI;
    protected Date FechaF;
    protected double presupuesto;
    protected int n_actividades;
    protected HashMap<String, Actividad> actividades;
    protected Responsable respon;
    protected OperacionesObra opObra;

    public Obra() {
        this.respon = new Responsable();
        this.actividades = new HashMap<>();
        this.opObra = new OperacionesObra();
    }

    public Obra(String codigo, String titulo, Date fechaI, Date fechaF,
            double presupuesto, int n_actividades, HashMap<String, Actividad> actividades,
            Responsable respon) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.fechaI = fechaI;
        this.FechaF = fechaF;
        this.presupuesto = presupuesto;
        this.n_actividades = n_actividades;
        this.actividades = actividades;
        this.respon = respon;
    }

    public Responsable getRespon() {
        return respon;
    }

    public void setRespon(Responsable respon) {
        this.respon = respon;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaI() {
        return fechaI;
    }

    public void setFechaI(Date fechaI) {
        this.fechaI = fechaI;
    }

    public Date getFechaF() {
        return FechaF;
    }

    public void setFechaF(Date FechaF) {
        this.FechaF = FechaF;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        if (presupuesto > 0) {
            this.presupuesto = presupuesto;
        } else {
            System.out.println("Presupuesto inválido.");
        }
    }

    public int getN_actividades() {
        return n_actividades;
    }

    public void setN_actividades(int n_actividades) {
        if (n_actividades > 0) {
            this.n_actividades = n_actividades;
        } else {
            System.out.println("Numero de actividades inválido.");
        }

    }

    public HashMap<String, Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(HashMap<String, Actividad> actividades) {
        this.actividades = actividades;
    }

    public void llenarDatosObra(Scanner scan) {
//        scan.useDelimiter("\n");
        String tempS = "";
        System.out.println("\nA continuación, por favor ingrese los datos de la obra a registrar:");

//        Código
        do {
            System.out.print("Código: ");
            tempS = scan.next();
            // si es válido rompe el ciclo y continúa el código
            if (opObra.validateWithLex(tempS, "ALPHANUM") == true) {
                this.codigo = tempS;
                break;
            } else { // si no es válido se pide el valor de nuevo 
                System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (alfanumérico)." + ANSI_RESET);
            }
        } while (true);

//        Título
        do {
            System.out.print("Título: ");
            tempS = scan.next();
            if (opObra.validateWithLex(tempS, "LETTER") == true) {
                this.titulo = tempS;
                break;
            } else {
                System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (alfabético)." + ANSI_RESET);
            }
        } while (true);

//        Presupuesto
        do {
            System.out.print("Presupuesto ($): ");
            tempS = scan.next();
            if (opObra.validateWithLex(tempS, "NUMBER") == true) {
                this.presupuesto = Double.parseDouble(tempS);
                break;
            } else {
                System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (numérico)." + ANSI_RESET);
            }
        } while (true);

//        Número de actividades previstas
        do {
            System.out.print("Número de actividades previstas: ");
            tempS = scan.next();
            if (opObra.validateWithLex(tempS, "NUMBER") == true) {
                this.n_actividades = Integer.parseInt(tempS);
                break;
            } else {
                System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (numérico)." + ANSI_RESET);
            }
        } while (true);
    
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

        System.out.print("Ingrese la cédula del Responsable para ésta obra: ");
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
            System.out.println(ANSI_RED +"No se encontró al responsable con dicha cédula." + ANSI_RESET);
        }

    }

    @Override
    public String toString() {
        return ANSI_PURPLE + "\n-Obra- | Código: " + this.getCodigo() + " | Título: " + this.getTitulo()
                + " | Presupuesto: " + this.getPresupuesto() + " | Número de actividades previstas: " + this.getN_actividades();
    }

    public String printFull() {
        return "";
    }
}
