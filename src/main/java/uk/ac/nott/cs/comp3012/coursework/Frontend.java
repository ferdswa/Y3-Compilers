package uk.ac.nott.cs.comp3012.coursework;

import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.ast.AstBuilder;
import uk.ac.nott.cs.comp3012.coursework.util.HashMapTable;

import java.util.ArrayList;
import java.util.Arrays;

public class Frontend {
    public Ast runFrontend(String input){
        HashMapTable<Object,Object,Object> symbolTable = new HashMapTable<>();
        AstBuilder astBuilder = new AstBuilder();
        ArrayList<String> ast = astBuilder.buildAst(input);
        ArrayList<String[]> pathAsNodes = new ArrayList<>();
        for (String s : ast) {
            pathAsNodes.add(s.split(":"));
        }
        for (String[] pathAsNode : pathAsNodes) {
            System.out.println(Arrays.toString(pathAsNode));
        }

        return null;
    }
}
