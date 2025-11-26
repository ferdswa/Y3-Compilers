package uk.ac.nott.cs.comp3012.coursework;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.ast.AstBuilder;
import uk.ac.nott.cs.comp3012.coursework.ast.ParseTreePrinter;
import uk.ac.nott.cs.comp3012.coursework.util.HashMapTable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Frontend {
    public Ast runFrontend(String input){
        HashMapTable<Object,Object,Object> symbolTable = new HashMapTable<>();
        AstBuilder astBuilder = new AstBuilder();
        ArrayList<String> ast = astBuilder.buildAst(input);

        return null;
    }
}
