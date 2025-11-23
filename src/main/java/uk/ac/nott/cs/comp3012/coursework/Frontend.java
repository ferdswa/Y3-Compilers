package uk.ac.nott.cs.comp3012.coursework;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.ast.AstBuilder;
import uk.ac.nott.cs.comp3012.coursework.ast.ParseTreePrinter;
import uk.ac.nott.cs.comp3012.coursework.util.HashMapTable;

import java.util.HashSet;
import java.util.Set;

public class Frontend {
    public Ast runFrontend(String input){
        AstBuilder astBuilder = new AstBuilder(new HashMapTable<>());
        astBuilder.buildAst(input);

//        ParseTreeWalker walker = new ParseTreeWalker();
//        ParseTreePrinter printer = new ParseTreePrinter();
//        walker.walk(printer, px.program());
        return null;
    }
}
