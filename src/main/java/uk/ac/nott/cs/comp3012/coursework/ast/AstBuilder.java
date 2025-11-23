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
        System.out.println(blockList);

    }

    @Override
    public Ast visitProgram(NottscriptParser.ProgramContext ctx) {
        Ast.BlockList blockList = new Ast.BlockList();
        for(NottscriptParser.BlockContext blockContext : ctx.block()){
            System.out.println("Block Found: "+blockContext.getText());
            Ast elem = visit(blockContext);
            blockList.add(elem);
        }
        return blockList;
    }

    //Blocks
    @Override
    public Ast visitProgramBlock(NottscriptParser.ProgramBlockContext ctx) {
        Ast.ProgramBlock block = new Ast.ProgramBlock();
        NottscriptParser.NameAtomContext openNameContext = ctx.nameAtom(0);
        block.add(visit(openNameContext));
        for(NottscriptParser.DeclarationContext declarationContext : ctx.declaration()){
            block.add(visit(declarationContext));
        }
        for(NottscriptParser.StatementContext statementContext : ctx.statement()){
            block.add(visit(statementContext));
        }
        NottscriptParser.NameAtomContext closeNameContext = ctx.nameAtom(1);
        block.add(visit(closeNameContext));
        return block;
    }

    //Declarations
    @Override
    public Ast visitDeclareVar(NottscriptParser.DeclareVarContext ctx) {
        Ast.DeclareVariable var = new Ast.DeclareVariable();
        NottscriptParser.TypeSpecContext typeSpecContext =  ctx.typeSpec();
        var.add(visit(typeSpecContext));
        for(NottscriptParser.NameAtomContext nameContext : ctx.nameAtom()){
            var.add(visit(nameContext));
        }
        return var;
    }
    @Override
    public Ast visitDeclPtr(NottscriptParser.DeclPtrContext ctx) {
        Ast.DeclarePointer var = new Ast.DeclarePointer();
        NottscriptParser.TypeSpecContext typeSpecContext =  ctx.typeSpec();
        var.add(visit(typeSpecContext));
        for(NottscriptParser.NameAtomContext nameContext : ctx.nameAtom()){
            var.add(visit(nameContext));
        }
        return var;
    }
    @Override
    public Ast visitDeclArray(NottscriptParser.DeclArrayContext ctx) {
        Ast.DeclareArray var = new Ast.DeclareArray();
        NottscriptParser.TypeSpecContext typeSpecContext =  ctx.typeSpec();
        var.add(visit(typeSpecContext));
        for(NottscriptParser.NumAtomContext numAtomContext : ctx.numAtom()){
            var.add(visit(numAtomContext));
        }
        for(NottscriptParser.NameAtomContext nameContext : ctx.nameAtom()){
            var.add(visit(nameContext));
        }
        return var;
    }
    @Override
    public Ast visitDeclPtrArray(NottscriptParser.DeclPtrArrayContext ctx) {
        Ast.DeclarePointerArray var = new Ast.DeclarePointerArray();
        NottscriptParser.TypeSpecContext typeSpecContext =  ctx.typeSpec();
        var.add(visit(typeSpecContext));
        for(NottscriptParser.StarContext pointerArrLen : ctx.star()){
            var.add(visit(pointerArrLen));
        }
        for(NottscriptParser.NameAtomContext nameAtomContext : ctx.nameAtom()){
            var.add(visit(nameAtomContext));
        }
        return var;
    }
    //TypeSpecs
    @Override
    public Ast visitInbuilt(NottscriptParser.InbuiltContext ctx) {
        Ast.InbuiltTypeSpec var = new Ast.InbuiltTypeSpec();
        NottscriptParser.TypeAtomContext type = ctx.typeAtom();
        var.add(visit(type));
        return var;
    }
    @Override
    public Ast visitCustom(NottscriptParser.CustomContext ctx){
        Ast.CustomTypeSpec var = new Ast.CustomTypeSpec();
        NottscriptParser.NameAtomContext name =  ctx.nameAtom();
        var.add(visit(name));
        return var;
    }
}
