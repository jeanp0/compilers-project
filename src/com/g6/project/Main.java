/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g6.project;

import com.g6.project.entities.Actividad;
import com.g6.project.entities.Obra;
import com.g6.project.entities.MedioAmbiente;
import com.g6.project.entities.Civil;
import com.g6.project.entities.Edificacion;
import com.g6.project.operations.OperacionesActividad;
import com.g6.project.operations.OperacionesObra;
import java.io.Serializable;
import java.util.Scanner;

/**
 *
 * @author Jeanpier
 */
public class Main implements Serializable {

    /**
     * @param args the command line arguments
     */
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    transient Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        Main J = new Main();
        J.menuPrincipal();
    }

    public void menuPrincipal() {
        OperacionesObra obraOP = new OperacionesObra();
        OperacionesActividad actOP = new OperacionesActividad();
        Actividad actividad = new Actividad();
//      Obra sin inicializar porque es una clase abstracta, será instanciada una vez se elija su tipo
        Obra obra;
        String ingAct, codigo, nombre, tempS;
        int op, j;

        try {
            do {
//          Menú principal del sistema, se pide una opción numérica para continuar con su funcionalidad respectiva

                do {
                    System.out.print("\n\n1 => Registrar una nueva Obra.\n2 => Ingresar actividades.\n3 => Buscar obra por código."
                            + "\n4 => Buscar actividad por nombre.\n5 => Ver progreso de alguna Obra\n6 => Salir.\n\nIngrese el número de alguna opción: ");
                    tempS = scan.next();
                    if (obraOP.validateWithLex(tempS, "NUMBER") == true) {
                        op = Integer.parseInt(tempS);
                        break;
                    } else {
                        System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (numérico)." + ANSI_RESET);
                    }
                } while (true);

//          De acuerdo a la opción ingresada, se ejecuta un case
                switch (op) {

//              Registrar una nueva obra
                    case 1:
                        do {
                            System.out.println("\n1 => Civil \n2 => Edificacion\n3 => Medio Ambiente");
                            System.out.print("\nSelecciona un tipo de Obra: ");

                            tempS = scan.next();
                            if (obraOP.validateWithLex(tempS, "NUMBER") == true) {
                                j = Integer.parseInt(tempS);
                                break;
                            } else {
                                System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (numérico)." + ANSI_RESET);
                            }
                        } while (true);
//                  Se selecciona el tipo de obra
                        switch (j) {
                            case 1:
                                obra = new Civil();//INICIALIZA LA OBRA COMO CIVIL
                                obra.llenarDatosObra(scan);//LEE LOS DATOS DE LA OBRA POR TECLADO
                                obra.asignarResponsable(scan);
                                System.out.println(obra.toString());
                                obraOP.guardar(obra);//GUARDA LA OBRA EN ARCHIVO BIN
                                obraOP.agregarObras();//AGREGA OBRAS AL MAPA
                                break;
                            case 2:
                                obra = new Edificacion();
                                obra.llenarDatosObra(scan);
                                obra.asignarResponsable(scan);
                                System.out.println(obra.toString());
                                obraOP.guardar(obra);
                                obraOP.agregarObras();
                                break;
                            case 3:
                                obra = new MedioAmbiente();
                                obra.llenarDatosObra(scan);
                                obra.asignarResponsable(scan);
                                System.out.println(obra.toString());
                                obraOP.guardar(obra);
                                obraOP.agregarObras();
                                break;
                            default:
                                break;
                        }
                        break;

//              Ingresar actividades de una obra por su código
                    case 2:
                        if (obraOP.agregarObras() != null) {
                            obra = obraOP.elegirObra(scan);
                            do {
                                actividad.leerDatosActividad(scan, obra);
                                actividad.asignarResponsable(scan);
                                System.out.println(actividad.toString());
                                actOP.guardar(actividad); // lo guarda en el archivo 
                                actOP.agregarActividades(); // lo guarda en la lista
                                System.out.print("¿Desea ingresar otra actividad? (SI/NO): ");
                                ingAct = scan.next();
                                System.out.println();
                            } while (ingAct.toUpperCase().equals("SI"));
                        } else {
                            System.out.println(ANSI_RED + "No hay obras registradas." + ANSI_RESET);
                        }
                        break;

//              Buscar obra por código para mostrar todos sus datos
                    case 3:
                        tempS = "";
                        if (obraOP.agregarObras() != null) {
                            do {
                                System.out.print("\nIngrese el código de la Obra a buscar: ");
                                tempS = scan.next();
                                if (obraOP.validateWithLex(tempS, "ALPHANUM") == true) {
                                    codigo = tempS;
                                    break;
                                } else {
                                    System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (alfanumérico)." + ANSI_RESET);
                                }
                            } while (true);
                            obraOP.mostrarObraFull(codigo);
                        } else {
                            System.out.println(ANSI_RED + "No hay obras registradas." + ANSI_RESET);
                        }
                        break;

//              Buscar actividad por su nombre para mostrar todos sus datos
                    case 4:
                        if (actOP.agregarActividades() != null) {
                            do {
                                System.out.print("\nIngrese el nombre de la Actividad a buscar: ");
                                tempS = scan.next();
                                if (obraOP.validateWithLex(tempS, "LETTER") == true) {
                                    nombre = tempS;
                                    break;
                                } else {
                                    System.out.println(ANSI_RED + "Por favor, ingrese un valor válido (alfabético)." + ANSI_RESET);
                                }
                            } while (true);
                            actOP.mostrarActividadFull(nombre);
                        } else {
                            System.out.println(ANSI_RED + "No hay actividades registradas." + ANSI_RESET);
                        }
                        break;

//              Ver informe de una obra por su código
                    case 5:
                        if (obraOP.agregarObras() != null) {
                            // elige la obra por su código
                            obra = obraOP.elegirObra(scan);
//                          // una vez obtenida la obra, se imprime su informe
                            obraOP.informeObra(obra);
                        } else {
                            System.out.println(ANSI_RED + "No hay obras registradas." + ANSI_RESET);
                        }
                    default:
                        break;
                }

//      Se sale del bucle con la opción 6 (salir)
            } while (op != 6);
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED + "¡Error! Ten cuidado con los valores que ingresas: " + e.getMessage() + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "La aplicación se reiniciará..." + ANSI_RESET);
            this.menuPrincipal();
        }
    }

}
