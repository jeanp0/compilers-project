/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g6.project.entities;

import static com.g6.project.Main.ANSI_PURPLE;
import static com.g6.project.Main.ANSI_RESET;
import java.io.Serializable;
import java.util.Scanner;

/**
 *
 * @author Jeanpier
 */
public class Responsable implements Serializable {

    private String nombre, apellido, cedula, cargo;

    public Responsable() {

    }

    public Responsable(String nombre, String apellido, String cedula, String cargo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.cargo = cargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void llenarDatosResponsable(Scanner scan) {
        System.out.println("\nA continuación, ingrese los datos del responsable:");
        System.out.print("Nombre: ");
        this.nombre = scan.next();
        System.out.print("Apellido: ");
        this.apellido = scan.next();
        System.out.print("Cédula: ");
        this.cedula = scan.next();
        System.out.print("Cargo: ");
        this.cargo = scan.next();
    }

    @Override
    public String toString() {
        return ANSI_PURPLE + "\n-Responsable- | Cédula " + this.cedula + " | Nombre: " + this.nombre
                + " | Apellido: " + this.apellido + " | Cargo: " + this.cargo + ANSI_RESET;
    }
}
