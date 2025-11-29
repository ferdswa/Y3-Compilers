package uk.ac.nott.cs.comp3012.coursework;

import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.ast.AstBuilder;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolTable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Frontend {
    SymbolTable parent = new SymbolTable();
    public Ast runFrontend(String input){
        AstBuilder astBuilder = new AstBuilder(this.parent);
        Ast.Units ast = (Ast.Units) astBuilder.buildAst(input);
        parent = astBuilder.getIntSymbolTable();
        parent.getChildren().forEach(child->{
            System.out.println(child.getSymbols());
        });
        ast.forEach(pUnit -> {//Check that all program units have bodies
            if(pUnit instanceof ArrayList<?>){
                if(((ArrayList<?>) pUnit).size()<3){
                    throw new RuntimeException("Program units without bodies detected");
                }
            }
        });
        for(SymbolTable puST : parent.getChildren()){//Check that program units start and end with the same ID
            AtomicInteger numberOfDifferentBlockNames = new AtomicInteger();
            puST.getSymbols().values().forEach(vName -> {
                if(vName.type.equals("unitID"))
                    numberOfDifferentBlockNames.getAndIncrement();
            });
            if(numberOfDifferentBlockNames.get()!=1){
                throw new RuntimeException("Syntax Error: Program units must start and end with the same identifier");
            }
        }
        return null;
    }
}
