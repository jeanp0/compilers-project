/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g6.project.jflex;

import java.io.File;

/**
 *
 * @author jeanp
 */
public class Generate {

    public static void main(String[] args) {
        String ruta = "C:/Users/jeanp/Desktop/proyecto-compiladores/COMPILERS-PROJECT-G6/src/com/g6/project/jflex/Lexer.flex";
        generarLexer(ruta);
    }

    public static void generarLexer(String ruta) {
        File archivo = new File(ruta);
        JFlex.Main.generate(archivo);
    }
}
