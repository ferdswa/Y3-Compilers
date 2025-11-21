package uk.ac.nott.cs.comp3012.coursework.ast;

import com.ibm.icu.text.SymbolTable;
import uk.ac.nott.cs.comp3012.coursework.NottscriptParser;

import java.util.Set;

public class AstBuilder {
    private final Set<String> symbols;

    public AstBuilder(Set<String> symbols) {
        this.symbols = symbols;
    }

    public Ast visitProgram(NottscriptParser.ProgramContext ctx) {
        //LineList e = new LineList();
        System.out.println(ctx.toString());
        return null;
    }
}
