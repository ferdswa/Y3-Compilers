package uk.ac.nott.cs.comp3012.coursework.ast;

import com.ibm.icu.text.SymbolTable;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import uk.ac.nott.cs.comp3012.coursework.NottscriptBaseVisitor;
import uk.ac.nott.cs.comp3012.coursework.NottscriptLexer;
import uk.ac.nott.cs.comp3012.coursework.NottscriptParser;

import java.util.HashSet;
import java.util.Set;

public class AstBuilder extends NottscriptBaseVisitor<Ast>
{
    private final Set<String> symbols;

    public AstBuilder(Set<String> symbols) {
        this.symbols = symbols;
    }

    public void buildAst(String inputFile) {
        NottscriptLexer lx = new NottscriptLexer(CharStreams.fromString(inputFile));
        TokenStream tokens = new CommonTokenStream(lx);
        NottscriptParser px = new NottscriptParser(tokens);

        Set<String> symbols = new HashSet<>();
        AstBuilder astBuilder = new AstBuilder(symbols);
        Ast.BlockList blockList = (Ast.BlockList) astBuilder.visitProgram(px.program());

    }

    public Ast visitProgram(NottscriptParser.ProgramContext ctx) {
        Ast.BlockList blockList = new Ast.BlockList();
        for(NottscriptParser.BlockContext blockContext : ctx.block()){
            System.out.println("Block Found: "+blockContext.getText());
            Ast elem = visit(blockContext);
            blockList.add(elem);
        }
        return blockList;
    }
}
