package uk.ac.nott.cs.comp3012.coursework.ast;

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
            System.out.println("Decl Found: "+declarationContext.getText());
            block.add(visit(declarationContext));
        }
        for(NottscriptParser.StatementContext statementContext : ctx.statement()){
            System.out.println("Stmt Found: "+statementContext.getText());
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
        Ast.DeclarePointer declarePointer = new Ast.DeclarePointer();
        NottscriptParser.TypeSpecContext typeSpecContext =  ctx.typeSpec();
        declarePointer.add(visit(typeSpecContext));
        for(NottscriptParser.NameAtomContext nameContext : ctx.nameAtom()){
            declarePointer.add(visit(nameContext));
        }
        return declarePointer;
    }
    @Override
    public Ast visitDeclArray(NottscriptParser.DeclArrayContext ctx) {
        Ast.DeclareArray declareArray = new Ast.DeclareArray();
        NottscriptParser.TypeSpecContext typeSpecContext =  ctx.typeSpec();
        declareArray.add(visit(typeSpecContext));
        for(NottscriptParser.NumAtomContext numAtomContext : ctx.numAtom()){
            declareArray.add(visit(numAtomContext));
        }
        for(NottscriptParser.NameAtomContext nameContext : ctx.nameAtom()){
            declareArray.add(visit(nameContext));
        }
        return declareArray;
    }
    @Override
    public Ast visitDeclPtrArray(NottscriptParser.DeclPtrArrayContext ctx) {
        Ast.DeclarePointerArray declarePointerArray = new Ast.DeclarePointerArray();
        NottscriptParser.TypeSpecContext typeSpecContext =  ctx.typeSpec();
        declarePointerArray.add(visit(typeSpecContext));
        for(NottscriptParser.StarContext pointerArrLen : ctx.star()){
            declarePointerArray.add(visit(pointerArrLen));
        }
        for(NottscriptParser.NameAtomContext nameAtomContext : ctx.nameAtom()){
            declarePointerArray.add(visit(nameAtomContext));
        }
        return declarePointerArray;
    }
    //TypeSpecs
    @Override
    public Ast visitInbuilt(NottscriptParser.InbuiltContext ctx) {
        Ast.InbuiltTypeSpec inbuiltTypeSpec = new Ast.InbuiltTypeSpec();
        NottscriptParser.TypeAtomContext type = ctx.typeAtom();
        inbuiltTypeSpec.add(visit(type));
        return inbuiltTypeSpec;
    }
    @Override
    public Ast visitCustom(NottscriptParser.CustomContext ctx){
        Ast.CustomTypeSpec customTypeSpec = new Ast.CustomTypeSpec();
        NottscriptParser.NameAtomContext name =  ctx.nameAtom();
        customTypeSpec.add(visit(name));
        return customTypeSpec;
    }
    //Statements
    @Override
    public Ast visitBaseAssign(NottscriptParser.BaseAssignContext ctx) {
        Ast.NormalAssign normalAssign = new Ast.NormalAssign();
        NottscriptParser.NameAtomContext name =  ctx.nameAtom();
        normalAssign.add(visit(name));
        NottscriptParser.ExprContext expr = ctx.expr();
        normalAssign.add(visit(expr));
        return normalAssign;
    }
    @Override
    public Ast visitArrayAssign(NottscriptParser.ArrayAssignContext ctx) {
        Ast.ArrayAssign arrayAssign = new Ast.ArrayAssign();
        NottscriptParser.ArrayContext array = ctx.array();
        arrayAssign.add(visit(array));
        NottscriptParser.ExprContext expr = ctx.expr();
        arrayAssign.add(visit(expr));
        return arrayAssign;
    }
    @Override
    public Ast visitCtArrayAssign(NottscriptParser.CtArrayAssignContext ctx){
        Ast.CustomTypeArrayAssign customTypeArrayAssign = new Ast.CustomTypeArrayAssign();
        NottscriptParser.NameAtomContext name =  ctx.nameAtom();
        customTypeArrayAssign.add(visit(name));
        NottscriptParser.ArrayContext array = ctx.array();
        customTypeArrayAssign.add(visit(array));
        NottscriptParser.ExprContext expr = ctx.expr();
        customTypeArrayAssign.add(visit(expr));
        return customTypeArrayAssign;
    }
    @Override
    public Ast visitCtAssign(NottscriptParser.CtAssignContext ctx){
        Ast.CustomTypeAssign customTypeAssign = new Ast.CustomTypeAssign();
        for(NottscriptParser.NameAtomContext name : ctx.nameAtom()){
            customTypeAssign.add(visit(name));
        }
        NottscriptParser.ExprContext expr = ctx.expr();
        customTypeAssign.add(visit(expr));
        return customTypeAssign;
    }
    @Override
    public Ast visitCall(NottscriptParser.CallContext ctx) {
        Ast.SbrtCall sbrtCall = new Ast.SbrtCall();
        NottscriptParser.NameAtomContext name =  ctx.nameAtom();
        sbrtCall.add(visit(name));
        NottscriptParser.ParamListContext paramList = ctx.paramList();
        sbrtCall.add(visit(paramList));
        return sbrtCall;
    }
    @Override
    public Ast visitIfBlock(NottscriptParser.IfBlockContext ctx) {
        Ast.IfBlock ifBlock = new Ast.IfBlock();
        NottscriptParser.ExprContext expr = ctx.expr();
        System.out.println("Found multi-line if: "+ctx.getText());
        ifBlock.add(visit(expr));
        for(NottscriptParser.StatementContext statement : ctx.statement()){
            ifBlock.add(visit(statement));
        }
        return ifBlock;
    }
    @Override
    public Ast visitIfElse(NottscriptParser.IfElseContext ctx) {
        Ast.IfElseBlock ifElseBlock = new Ast.IfElseBlock();
        NottscriptParser.ExprContext expr = ctx.expr();
        ifElseBlock.add(visit(expr));
        System.out.println("Found multi-line if: "+ctx.getText());
        for(NottscriptParser.StatementContext statement : ctx.statement()){
            ifElseBlock.add(visit(statement));
        }
        NottscriptParser.ElseStmtContext elseStmt = ctx.elseStmt();
        ifElseBlock.add(visit(elseStmt));
        return ifElseBlock;
    }
    @Override
    public Ast visitElseStmt(NottscriptParser.ElseStmtContext ctx) {
        Ast.ElseStmt elseStmt = new Ast.ElseStmt();
        System.out.println("Found else: "+ctx.getText());
        for(NottscriptParser.StatementContext statement : ctx.statement()){
            elseStmt.add(visit(statement));
        }
        return elseStmt;
    }
    @Override
    public Ast visitIfStmt(NottscriptParser.IfStmtContext ctx) {
        Ast.IfStatement ifStatement = new Ast.IfStatement();
        System.out.println("Found single-line if: "+ctx.getText());
        NottscriptParser.ExprContext expr = ctx.expr();
        ifStatement.add(visit(expr));
        NottscriptParser.StatementContext statement = ctx.statement();
        ifStatement.add(visit(statement));
        return ifStatement;
    }
    @Override
    public Ast visitDoParam(NottscriptParser.DoParamContext ctx) {
        Ast.DoParam doParam = new Ast.DoParam();
        NottscriptParser.NameAtomContext name =  ctx.nameAtom();
        if(name!=null){
            doParam.add(visit(name));
        }
        else{
            NottscriptParser.IntnumContext num = ctx.intnum();
            doParam.add(visit(num));
        }
        return doParam;
    }
    @Override
    public Ast visitDoIncrN1(NottscriptParser.DoIncrN1Context ctx) {
        Ast.DoIncrNot1 doIncrNot1 = new Ast.DoIncrNot1();
        for(NottscriptParser.DoParamContext doParamContext : ctx.doParam()){
            doIncrNot1.add(visit(doParamContext));
        }
        for(NottscriptParser.StatementContext statement : ctx.statement()){
            doIncrNot1.add(visit(statement));
        }
        return doIncrNot1;
    }
    @Override
    public Ast visitDoIncr1(NottscriptParser.DoIncr1Context ctx) {
        Ast.DoIncr1 doIncr1 = new Ast.DoIncr1();
        for(NottscriptParser.DoParamContext doParamContext : ctx.doParam()){
            doIncr1.add(visit(doParamContext));
        }
        for(NottscriptParser.StatementContext statement : ctx.statement()){
            doIncr1.add(visit(statement));
        }
        return doIncr1;
    }
    @Override
    public Ast visitDoWhile(NottscriptParser.DoWhileContext ctx) {
        Ast.DoWhile doWhile = new Ast.DoWhile();
        NottscriptParser.ExprContext expr = ctx.expr();
        doWhile.add(visit(expr));
        for(NottscriptParser.StatementContext statement : ctx.statement()){
            doWhile.add(visit(statement));
        }
        return doWhile;
    }
    @Override
    public Ast visitReadParam(NottscriptParser.ReadParamContext ctx) {
        Ast.ReadParam readParam = new Ast.ReadParam();
        NottscriptParser.NameAtomContext name =  ctx.nameAtom();
        if(name!=null){
            readParam.add(visit(name));
        }
        else{
            NottscriptParser.ArrayContext array = ctx.array();
            readParam.add(visit(array));
        }
        return readParam;
    }
    @Override
    public Ast visitRead(NottscriptParser.ReadContext ctx) {
        Ast.Read read = new Ast.Read();
        for(NottscriptParser.ReadParamContext readParamContext : ctx.readParam()){
            read.add(visit(readParamContext));
        }
        return read;
    }
    @Override
    public Ast visitWrite(NottscriptParser.WriteContext ctx) {
        Ast.Write write = new Ast.Write();
        for(NottscriptParser.ExprContext expr : ctx.expr()){
            write.add(visit(expr));
        }
        return write;
    }
    @Override
    public Ast visitAllocPtr(NottscriptParser.AllocPtrContext ctx) {
        Ast.AllocPtr allocPtr = new Ast.AllocPtr();
        NottscriptParser.NameAtomContext name = ctx.nameAtom();
        allocPtr.add(visit(name));
        return allocPtr;
    }
    @Override
    public Ast visitAllocPtrArray(NottscriptParser.AllocPtrArrayContext ctx) {
        Ast.AllocPtrArray allocPtrArray = new Ast.AllocPtrArray();
        NottscriptParser.NameAtomContext name = ctx.nameAtom();
        allocPtrArray.add(visit(name));
        NottscriptParser.ArrayIndexContext arrayIndex = ctx.arrayIndex();
        allocPtrArray.add(visit(arrayIndex));
        return allocPtrArray;
    }
    @Override
    public Ast visitDeallocPtr(NottscriptParser.DeallocPtrContext ctx) {
        Ast.DeallocPtr deallocPtr = new Ast.DeallocPtr();
        NottscriptParser.NameAtomContext name = ctx.nameAtom();
        deallocPtr.add(visit(name));
        return deallocPtr;
    }
    @Override
    public Ast visitFuncCall(NottscriptParser.FuncCallContext ctx) {
        Ast.FuncCall funcCall = new Ast.FuncCall();
        NottscriptParser.NameAtomContext name = ctx.nameAtom();
        funcCall.add(visit(name));
        NottscriptParser.ParamListContext paramList = ctx.paramList();
        funcCall.add(visit(paramList));
        return funcCall;
    }
    @Override
    public Ast visitArray(NottscriptParser.ArrayContext ctx) {
        Ast.ArrayDef arrayDef = new Ast.ArrayDef();
        NottscriptParser.NameAtomContext name = ctx.nameAtom();
        arrayDef.add(visit(name));
        for(NottscriptParser.ArrayIndexContext arrayIndexContext : ctx.arrayIndex()){
            arrayDef.add(visit(arrayIndexContext));
        }
        return  arrayDef;
    }
    @Override
    public Ast visitArrayIndex(NottscriptParser.ArrayIndexContext ctx) {
        Ast.ArrayIndex arrayIndex = new Ast.ArrayIndex();
        NottscriptParser.NameAtomContext name = ctx.nameAtom();
        if(name!=null){
            arrayIndex.add(visit(name));
        }
        else{
            NottscriptParser.NumAtomContext num = ctx.numAtom();
            arrayIndex.add(visit(num));
        }
        return arrayIndex;
    }
    @Override
    public Ast visitParamSubList(NottscriptParser.ParamSubListContext ctx) {
        Ast.ParamSubList paramSubList = new Ast.ParamSubList();
        NottscriptParser.NameAtomContext name = ctx.nameAtom();
        if(name!=null){
            paramSubList.add(visit(name));
        }
        else{
            NottscriptParser.ExprContext expr = ctx.expr();
            paramSubList.add(visit(expr));
        }
        return paramSubList;
    }
    @Override
    public Ast visitParamList(NottscriptParser.ParamListContext ctx) {
        Ast.ParamList paramList = new Ast.ParamList();
        for(NottscriptParser.ParamSubListContext psl: ctx.paramSubList()){
            paramList.add(visit(psl));
        }
        return paramList;
    }

}
