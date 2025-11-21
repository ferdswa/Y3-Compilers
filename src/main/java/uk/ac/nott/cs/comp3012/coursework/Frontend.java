package uk.ac.nott.cs.comp3012.coursework;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.ast.AstBuilder;

import java.util.HashSet;
import java.util.Set;

public class Frontend {
    public Ast runFrontend(String input){
        NottscriptLexer lx = new NottscriptLexer(CharStreams.fromString(input));
        TokenStream tokens = new CommonTokenStream(lx);
        NottscriptParser px = new NottscriptParser(tokens);

        Set<String> symbols = new HashSet<>();
        AstBuilder astBuilder = new AstBuilder(symbols);
        astBuilder.visitProgram(px.program());
        return null;
    }
}
