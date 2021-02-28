/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g6.project.operations;

import static com.g6.project.Main.ANSI_GREEN;
import static com.g6.project.Main.ANSI_PURPLE;
import static com.g6.project.Main.ANSI_RED;
import static com.g6.project.Main.ANSI_RESET;
import com.g6.project.entities.Actividad;
import com.g6.project.entities.Civil;
import com.g6.project.entities.Edificacion;
import com.g6.project.entities.MedioAmbiente;
import com.g6.project.entities.Obra;
import com.g6.project.jflex.Tokens;
import static com.g6.project.jflex.Tokens.ALPHANUM;
import static com.g6.project.jflex.Tokens.LETTER;
import static com.g6.project.jflex.Tokens.NUMBER;
import com.g6.project.operations.interfaces.IOperacionesObra;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeanpier
 */
public class OperacionesObra implements IOperacionesObra, Serializable {

    private HashMap<String, Obra> listaObras;

    public OperacionesObra() {
        listaObras = new HashMap<>();
    }

    public HashMap<String, Obra> getListaObras() {
        return listaObras;
    }

    public void setListaObras(HashMap<String, Obra> listaObras) {
        this.listaObras = listaObras;
    }

    //AGREGAR OBRAS DEL ARCHIVO A UNA COLECCIÓN (MAPA)
    @Override
    public HashMap<String, Obra> agregarObras() {
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        HashMap<String, Obra> lista = null;

        try {
            file = new File(IOperacionesObra.OBRAS);
            if (file.length() == 0) {
                return null;//VACÍO
            }
            fis = new FileInputStream(file);
            lista = new HashMap<>();
            boolean check = true;
            while (check) {//MIENTRAS HAYA OBJETOS...
                try {
                    ois = new ObjectInputStream(fis);
                    Object aux = ois.readObject();//LEE EL OBJETO EN UN AUX
                    if (aux instanceof Obra) {
                        Obra obra = (Obra) aux;
                        lista.put(obra.getCodigo(), obra);
                    }
                } catch (EOFException ex) {
                    check = false;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo de obras no fue encontrado: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("La clase Obra no fue encontrada: " + ex.getMessage());
        } catch (IOException io) {
            System.out.println("Error IO: " + io.getMessage());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException io) {
                System.out.println("Error al cerrar stream en leer Obras: " + io.getMessage());
            }
        }
        return lista;
    }

    //BUSCAR OBRA EN COLECCIÓN
    @Override
    public Obra buscar(String codigo) {
        Obra obraEncontrada = null;
        this.listaObras = this.agregarObras();
        if (this.listaObras == null) {
            return null;
        }
        Obra obra = this.listaObras.get(codigo);
        if (obra != null) {
            obraEncontrada = obra;
        }

        return obraEncontrada;
    }

    //GUARDAR EN ARCHIVO BINARIO LAS OBRAS
    @Override
    public boolean guardar(Obra obra) {

        Obra obra_save = this.buscar(obra.getCodigo());
        if (obra_save != null) {
            System.out.println(ANSI_RED + "Ya existe la obra" + ANSI_RESET);
            return false;
        }
        File file = null;
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        boolean res = true;
        try {
            file = new File(IOperacionesObra.OBRAS);
            if (!file.exists()) {
                file.createNewFile();
            }
            fout = new FileOutputStream(file, true);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(obra);
            System.out.println("\n" + ANSI_GREEN + "La obra se ha guardado correctamente.\n" + ANSI_RESET);
        } catch (IOException ioex) {
            System.out.println("Error en guardar Obra: " + ioex.getMessage());
            res = false;
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException io) {
                System.out.println("Error al cerrar stream en guardar obra: " + io.getMessage());
                res = false;
            }
        }
        return res;
    }

    @Override
    public Obra elegirObra(Scanner scan) {
        Obra obra;
        Obra obraSeleccionada = null;
        String codigo;
        int i = 0, j = 0;
        for (Map.Entry<String, Obra> entrada : this.agregarObras().entrySet()) {
            obra = entrada.getValue();
            System.out.println("\nObra número " + (i + 1) + ":");
            System.out.println(obra.toString());
            i++;
        }
        System.out.print("\nIngrese código de la obra que desea seleccionar: ");

        do {
            if (j > 0) {
                System.out.print(ANSI_RED + "Código de obra no encontrado. Escribe otro que si sea válido por favor: " + ANSI_RESET);
            }
            codigo = scan.next();
            for (Map.Entry<String, Obra> entrada : this.agregarObras().entrySet()) {
                if (entrada.getKey().equals(codigo)) {
                    obraSeleccionada = entrada.getValue();
                    break;
                }
            }
            j++;
        } while (obraSeleccionada == null);

        return obraSeleccionada;
    }

    @Override
    public void mostrarObraFull(String codigo) {
        OperacionesActividad actOP = new OperacionesActividad();
        Obra obra = null;
        int i = 0;

        for (Map.Entry<String, Obra> entrada : this.agregarObras().entrySet()) {
            if (entrada.getKey().equals(codigo)) {
                obra = entrada.getValue();
                break;
            } else {
                System.out.println(ANSI_RED + "No hay obras que conincida con ese código." + ANSI_RESET);
            }
        }

        if (obra != null) {
            System.out.println(obra.printFull());
        }

        for (Map.Entry<String, Actividad> entrada : actOP.agregarActividades().entrySet()) {
            if (entrada.getValue().getCod_obra().equals(codigo)) {
                System.out.println("\nActividad " + (i + 1) + ":");
                System.out.print(entrada.getValue());
                i++;
            }
        }

    }

    public void informeObra(Obra obra) {
        OperacionesActividad actOP = new OperacionesActividad();
        double porc = 0, i = 0;

        for (Map.Entry<String, Actividad> entrada : actOP.agregarActividades().entrySet()) {
            if (entrada.getValue().getCod_obra().equals(obra.getCodigo())) {
                i++;
            }
        }
        porc = (i * 100 / obra.getN_actividades());
        System.out.println(ANSI_PURPLE + "La obra seleccionada tiene un progreso del " + porc + "%." + ANSI_RESET);
    }

    @Override
    public boolean validateWithLex(Object valueToEvaluate, String tokenName) {
        boolean isValid = false;
        // escribe un texto con la entrada
        File file = new File("inputFile.txt");
        PrintWriter writer;
        try {
            writer = new PrintWriter(file);
            writer.print(valueToEvaluate);
            writer.close();
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger("Test").log(Level.SEVERE, "BOOM!", ex);
        }
        // se lee el texto de la entrada con el analizador léxico
        try {
            Reader reader;
            reader = new BufferedReader(new FileReader("inputFile.txt"));
            Lexer lexer = new Lexer(reader);
            while (true) {
                Tokens tokens = lexer.yylex();
//                System.out.println(tokens);
                if ("NUMBER".equals(tokenName)) {
                    if (tokens == NUMBER) {
                        isValid = true;
                    } else {
                        isValid = false;
                        break;
                    }
                    break;
                }
                if ("LETTER".equals(tokenName)) {
                    if (tokens == LETTER) {
                        isValid = true;
                    } else {
                        isValid = false;
                        break;
                    }
                    break;
                }
                if ("ALPHANUM".equals(tokenName)) {
                    if (tokens == ALPHANUM) {
                        isValid = true;
                    } else {
                        isValid = false;
                        break;
                    }
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Obra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Obra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isValid;
    }
}
