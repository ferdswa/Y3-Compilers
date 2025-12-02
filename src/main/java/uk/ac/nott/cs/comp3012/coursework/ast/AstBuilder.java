package uk.ac.nott.cs.comp3012.coursework.ast;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import uk.ac.nott.cs.comp3012.coursework.NottscriptBaseVisitor;
import uk.ac.nott.cs.comp3012.coursework.NottscriptLexer;
import uk.ac.nott.cs.comp3012.coursework.NottscriptParser;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolData;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolTable;

import java.util.Objects;

public class AstBuilder extends NottscriptBaseVisitor<Ast>
{
    SymbolTable intSymbolTable;
    String cType;
    public AstBuilder(SymbolTable symbolTable){
        intSymbolTable = symbolTable;
    }

    public SymbolTable getIntSymbolTable() {
        return intSymbolTable;
    }

    /**
     * Generate an AST for the provided input code file
     * @param inputFile String representation of the code file
     * @return - The AST (Currently)
     */
    public Ast buildAst(String inputFile) {
        NottscriptLexer lx = new NottscriptLexer(CharStreams.fromString(inputFile));
        TokenStream tokens = new CommonTokenStream(lx);
        NottscriptParser px = new NottscriptParser(tokens);

        AstBuilder astBuilder = this;
        return astBuilder.visitProgram(px.program());
    }

    //VISIT AST
    @Override
    public Ast visitProgram(NottscriptParser.ProgramContext ctx) {
        intSymbolTable.setScopeName("entireProgram");
        Ast.Units units = new Ast.Units();
        for(NottscriptParser.UnitContext uctx : ctx.unit()){
            Ast elem = visit(uctx);
            units.add(elem);
        }
        return units;
    }

    //Blocks
    @Override
    public Ast visitProgramBlock(NottscriptParser.ProgramBlockContext ctx) {
        Ast.ProgramUnit block = new Ast.ProgramUnit();
        SymbolTable st = new SymbolTable(intSymbolTable);
        st.setScopeName("programBlock");
        NottscriptParser.NameUnitContext nctx = ctx.nameUnit(0);
        block.add(visit(nctx));
        for(NottscriptParser.DeclarationContext declarationContext : ctx.declaration()){
            block.add(visit(declarationContext));
        }
        cType = "ALL_DECLARED";
        for(NottscriptParser.StatementContext statementContext : ctx.statement()){
            block.add(visit(statementContext));
        }
        NottscriptParser.NameUnitContext nctx2 = ctx.nameUnit(1);
        block.add(visit(nctx2));
        return block;
    }
    @Override
    public Ast visitNameUnit(NottscriptParser.NameUnitContext ctx) {
        Ast.NameUnit  nameUnit = new Ast.NameUnit();
        NottscriptParser.NodeAtomContext nodeAtom = ctx.nodeAtom();
        nameUnit.add(visit(nodeAtom));
        NottscriptParser.NameAtomContext openNameContext = ctx.nameAtom();
        nameUnit.add(visit(openNameContext));
        return nameUnit;
    }
    @Override
    public Ast visitVoidFuncBlock(NottscriptParser.VoidFuncBlockContext ctx) {
        Ast.FuncRVoidUnit fvUnit = new Ast.FuncRVoidUnit();
        SymbolTable st = new SymbolTable(intSymbolTable);
        st.setScopeName("voidFuncBlock");
        NottscriptParser.NameUnitContext nctx = ctx.nameUnit(0);
        fvUnit.add(visit(nctx));
        if(ctx.declaratorParamList()!=null){
            NottscriptParser.DeclaratorParamListContext declParams= ctx.declaratorParamList();
            fvUnit.add(visit(declParams));
        }
        for(NottscriptParser.DeclarationContext declarationContext : ctx.declaration()){
            fvUnit.add(visit(declarationContext));
        }
        for(NottscriptParser.StatementContext statementContext : ctx.statement()){
            fvUnit.add(visit(statementContext));
        }
        NottscriptParser.NameUnitContext nctx2 = ctx.nameUnit(0);
        fvUnit.add(visit(nctx2));
        return fvUnit;
    }
    @Override
    public Ast visitReturnFuncBlock(NottscriptParser.ReturnFuncBlockContext ctx) {
        Ast.FuncRValueUnit ftUnit = new Ast.FuncRValueUnit();
        SymbolTable st = new SymbolTable(intSymbolTable);
        st.setScopeName("rFuncBlock");
        NottscriptParser.NameUnitContext nctx = ctx.nameUnit(0);
        ftUnit.add(visit(nctx));
        if(ctx.declaratorParamList()!=null){
            NottscriptParser.DeclaratorParamListContext declParams= ctx.declaratorParamList();
            ftUnit.add(visit(declParams));
        }
        NottscriptParser.NameAtomContext resultVar = ctx.nameAtom();
        ftUnit.add(visit(resultVar));
        for(NottscriptParser.DeclarationContext declarationContext : ctx.declaration()){
            ftUnit.add(visit(declarationContext));
        }
        for(NottscriptParser.StatementContext statementContext : ctx.statement()){
            ftUnit.add(visit(statementContext));
        }
        NottscriptParser.NameUnitContext nctx2 = ctx.nameUnit(0);
        ftUnit.add(visit(nctx2));
        return ftUnit;
    }
    @Override
    public Ast visitSubrtBlock(NottscriptParser.SubrtBlockContext ctx) {
        Ast.SbrtUnit sbrtUnit = new Ast.SbrtUnit();
        SymbolTable st = new SymbolTable(intSymbolTable);
        st.setScopeName("subrtBlock");
        NottscriptParser.NameUnitContext nctx = ctx.nameUnit(0);
        sbrtUnit.add(visit(nctx));
        if(ctx.declaratorParamList()!=null){
            NottscriptParser.DeclaratorParamListContext declParams= ctx.declaratorParamList();
            sbrtUnit.add(visit(declParams));
        }
        for(NottscriptParser.DeclarationContext declarationContext : ctx.declaration()){
            sbrtUnit.add(visit(declarationContext));
        }
        for(NottscriptParser.StatementContext statementContext : ctx.statement()){
            sbrtUnit.add(visit(statementContext));
        }
        NottscriptParser.NameUnitContext nctx2 = ctx.nameUnit(0);
        sbrtUnit.add(visit(nctx2));
        return sbrtUnit;
    }
    @Override
    public Ast visitCustomTypeDeclBlock(NottscriptParser.CustomTypeDeclBlockContext ctx) {
        Ast.CustomTypeDefUnit ctUnit = new Ast.CustomTypeDefUnit();
        SymbolTable st = new SymbolTable(intSymbolTable);
        st.setScopeName("customTypeDef");
        NottscriptParser.NameUnitContext nctx = ctx.nameUnit(0);
        ctUnit.add(visit(nctx));
        for(NottscriptParser.DeclarationContext declarationContext : ctx.declaration()){
            ctUnit.add(visit(declarationContext));
        }
        NottscriptParser.NameUnitContext nctx2 = ctx.nameUnit(0);
        ctUnit.add(visit(nctx2));
        return ctUnit;
    }
    @Override
    public Ast visitDeclaratorParamList(NottscriptParser.DeclaratorParamListContext ctx) {
        Ast.FuncDefineParams fp = new Ast.FuncDefineParams();
        for(NottscriptParser.NameAtomContext vName : ctx.nameAtom()){
            fp.add(visit(vName));
        }
        return fp;
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
        NottscriptParser.NodeAtomContext nodeAtom = ctx.nodeAtom();
        declarePointer.add(visit(nodeAtom));
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
        NottscriptParser.NodeAtomContext nodeAtom = ctx.nodeAtom();
        declarePointerArray.add(visit(nodeAtom));
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
        cType = ctx.getChild(0).getText();
        NottscriptParser.TypeAtomContext type = ctx.typeAtom();
        inbuiltTypeSpec.add(visit(type));
        return inbuiltTypeSpec;
    }
    @Override
    public Ast visitCustom(NottscriptParser.CustomContext ctx){
        Ast.CustomTypeSpec customTypeSpec = new Ast.CustomTypeSpec();
        cType = ctx.getChild(0).getText();
        NottscriptParser.NameAtomContext name =  ctx.nameAtom();
        customTypeSpec.add(visit(name));
        return customTypeSpec;
    }
    //Statements
    @Override
    public Ast visitBaseAssign(NottscriptParser.BaseAssignContext ctx) {
        Ast.NormalAssign normalAssign = new Ast.NormalAssign();
        NottscriptParser.NameAtomContext name = ctx.nameAtom();
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
        if(paramList != null){
            sbrtCall.add(visit(paramList));
        }
        return sbrtCall;
    }
    @Override
    public Ast visitIfBlock(NottscriptParser.IfBlockContext ctx) {
        Ast.IfBlock ifBlock = new Ast.IfBlock();
        NottscriptParser.ExprContext expr = ctx.expr();
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
        for(NottscriptParser.StatementContext statement : ctx.statement()){
            elseStmt.add(visit(statement));
        }
        return elseStmt;
    }
    @Override
    public Ast visitExitStmt(NottscriptParser.ExitStmtContext ctx) {
        return new Ast.Atom.exitAtom("exit");
    }
    @Override
    public Ast visitIfStmt(NottscriptParser.IfStmtContext ctx) {
        Ast.IfStatement ifStatement = new Ast.IfStatement();
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
        NottscriptParser.NodeAtomContext nodeAtom = ctx.nodeAtom(0);
        doIncrNot1.add(visit(nodeAtom));
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
        NottscriptParser.NodeAtomContext nodeAtom = ctx.nodeAtom(0);
        doIncr1.add(visit(nodeAtom));
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
        NottscriptParser.NodeAtomContext nodeAtom = ctx.nodeAtom(0);
        doWhile.add(visit(nodeAtom));
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

    //Expressions
    @Override
    public Ast visitExpr(NottscriptParser.ExprContext ctx) {
        Ast.Expr expr = new Ast.Expr();
        NottscriptParser.OrExprContext orExpr = ctx.orExpr();
        expr.add(visit(orExpr));
        return expr;
    }
    @Override
    public Ast visitOrExpr(NottscriptParser.OrExprContext ctx) {
        Ast.OrExpr orExpr = new Ast.OrExpr();
        for(NottscriptParser.AndExprContext andExpr: ctx.andExpr()){
            orExpr.add(visit(andExpr));
        }
        return orExpr;
    }
    @Override
    public Ast visitAndExpr(NottscriptParser.AndExprContext ctx) {
        Ast.AndExpr andExpr = new Ast.AndExpr();
        for(NottscriptParser.RelExprContext relExpr: ctx.relExpr()){
            andExpr.add(visit(relExpr));
        }
        return andExpr;
    }
    @Override
    public Ast visitRelExpr(NottscriptParser.RelExprContext ctx) {
        Ast.RelExpr relExpr = new Ast.RelExpr();
        for(NottscriptParser.ConcatExprContext concatExpr: ctx.concatExpr()){
            relExpr.add(visit(concatExpr));
        }
        for(NottscriptParser.RelativeOpContext relativeOp: ctx.relativeOp()){
            relExpr.add(visit(relativeOp));
        }
        return relExpr;
    }
    @Override
    public Ast visitConcatExpr(NottscriptParser.ConcatExprContext ctx) {
        Ast.ConcatExpr concatExpr = new Ast.ConcatExpr();
        for(NottscriptParser.AddSubExprContext addSubExpr: ctx.addSubExpr()){
            concatExpr.add(visit(addSubExpr));
        }
        return concatExpr;
    }
    @Override
    public Ast visitAddSubExpr(NottscriptParser.AddSubExprContext ctx) {
        Ast.AddSubExpr addSubExpr = new Ast.AddSubExpr();
        for(NottscriptParser.MulDivExprContext mulDivExprContext: ctx.mulDivExpr()){
            addSubExpr.add(visit(mulDivExprContext));
        }
        for(NottscriptParser.AddSubOpContext addSubOpContext: ctx.addSubOp()){
            addSubExpr.add(visit(addSubOpContext));
        }
        return addSubExpr;
    }
    @Override
    public Ast visitMulDivExpr(NottscriptParser.MulDivExprContext ctx) {
        Ast.MulDivExpr mulDivExpr = new Ast.MulDivExpr();
        for(NottscriptParser.PowExprContext powExprContext: ctx.powExpr()){
            mulDivExpr.add(visit(powExprContext));
        }
        for(NottscriptParser.MulDivOpContext mulDivOpContext: ctx.mulDivOp()){
            mulDivExpr.add(visit(mulDivOpContext));
        }
        return mulDivExpr;
    }
    @Override
    public Ast visitPowExpr(NottscriptParser.PowExprContext ctx) {
        Ast.PowExpr powExpr = new Ast.PowExpr();
        for(NottscriptParser.FieldAccExprContext fieldAccExprContext: ctx.fieldAccExpr()){
            powExpr.add(visit(fieldAccExprContext));
        }
        return powExpr;
    }
    @Override
    public Ast visitFieldAccExpr(NottscriptParser.FieldAccExprContext ctx) {
        Ast.FieldAccessExpr  fieldAccessExpr = new Ast.FieldAccessExpr();
        for(NottscriptParser.BasicContext basicContext: ctx.basic()){
            fieldAccessExpr.add(visit(basicContext));
        }
        return fieldAccessExpr;
    }
    @Override
    public Ast visitLogicSExpr(NottscriptParser.LogicSExprContext ctx) {
        String boolVal = ctx.getText();
        boolVal = boolVal.replace(".","");
        boolVal = boolVal.replace("\"","");
        boolean b =  Boolean.parseBoolean(boolVal);
        return new Ast.Atom.boolAtom(b);
    }
    @Override
    public Ast visitHexSExpr(NottscriptParser.HexSExprContext ctx) {
        String hexVal = ctx.getText();
        hexVal = hexVal.replace("z","");
        hexVal = hexVal.replace("\"","");
        return new Ast.Atom.hexNumAtom(hex2Dec(hexVal));
    }
    @Override
    public Ast visitOctSExpr(NottscriptParser.OctSExprContext ctx) {
        String octVal = ctx.getText();
        octVal = octVal.replace("o","");
        octVal = octVal.replace("\"","");
        return new Ast.Atom.octNumAtom(oct2Dec(octVal));
    }


    @Override
    public Ast visitRealSExpr(NottscriptParser.RealSExprContext ctx) {
        float realVal = Float.parseFloat(ctx.getText());
        return new Ast.Atom.realAtom(realVal);
    }
    @Override
    public Ast visitBinSExpr(NottscriptParser.BinSExprContext ctx) {
        String binVal = ctx.getText();
        binVal = binVal.replace("b","");
        return new Ast.Atom.binNumAtom(binToDec(binVal));
    }
    @Override
    public Ast visitCharSeqSExpr(NottscriptParser.CharSeqSExprContext ctx) {
        String charVal = ctx.getText();
        return new Ast.Atom.charLiteralAtom(charVal);
    }
    @Override
    public Ast visitIntSExpr(NottscriptParser.IntSExprContext ctx) {
        Ast.IntSExpr intSExpr = new Ast.IntSExpr();
        intSExpr.add(visit(ctx.intnum()));
        return intSExpr;
    }
    @Override
    public Ast visitExprSExpr(NottscriptParser.ExprSExprContext ctx) {
        Ast.ExprSExpr expr = new Ast.ExprSExpr();
        expr.add(visit(ctx.expr()));
        return expr;
    }
    @Override
    public Ast visitArrSExpr(NottscriptParser.ArrSExprContext ctx) {
        Ast.ArraySExpr arrayDef = new Ast.ArraySExpr();
        arrayDef.add(visit(ctx.array()));
        return arrayDef;
    }
    @Override
    public Ast visitFuncSExpr(NottscriptParser.FuncSExprContext ctx) {
        Ast.FuncSExpr funcSExpr = new Ast.FuncSExpr();
        NottscriptParser.NameAtomContext nameAtomContext = ctx.nameAtom();
        funcSExpr.add(visit(nameAtomContext));
        NottscriptParser.ParamListContext paramListContext = ctx.paramList();
        if(ctx.paramList()!=null){
            funcSExpr.add(visit(paramListContext));
        }
        return funcSExpr;
    }
    @Override
    public Ast visitNameSExpr(NottscriptParser.NameSExprContext ctx) {
        Ast.NameSExpr nameSExpr = new Ast.NameSExpr();
        nameSExpr.add(visit(ctx.nameAtom()));
        return nameSExpr;
    }
    //Atoms
    @Override
    public Ast visitRelativeOp(NottscriptParser.RelativeOpContext ctx) {
        String op = ctx.getText();
        //symbols.add(op);
        return new Ast.Atom.relAtom(op);
    }
    @Override
    public Ast visitTypeAtom(NottscriptParser.TypeAtomContext ctx) {
        String type = ctx.getText();
        //symbols.add(type);
        return new Ast.Atom.typeAtom(type);
    }
    @Override
    public Ast visitLogicalOp(NottscriptParser.LogicalOpContext ctx) {
        String op = ctx.getText();
        //symbols.add(op);
        return new Ast.Atom.logicOpAtom(op);
    }
    @Override
    public Ast visitMulDivOp(NottscriptParser.MulDivOpContext ctx) {
        String op = ctx.getText();
        //symbols.add(op);
        return new Ast.Atom.mulDivAtom(op);
    }
    @Override
    public Ast visitAddSubOp(NottscriptParser.AddSubOpContext ctx) {
        String op = ctx.getText();
        //symbols.add(op);
        return new Ast.Atom.addSubAtom(op);
    }
    @Override
    public Ast visitStar(NottscriptParser.StarContext ctx) {
        String op = ctx.getText();
        //symbols.add(op);
        return new Ast.Atom.starAtom(op);
    }
    @Override
    public Ast visitIntnum(NottscriptParser.IntnumContext ctx) {
        Ast.IntNum intNum = new Ast.IntNum();
        NottscriptParser.AddSubOpContext addSubOpContext = ctx.addSubOp();
        if(addSubOpContext!=null){
            intNum.add(visit(addSubOpContext));
        }
        NottscriptParser.NumAtomContext numAtomContext = ctx.numAtom();
        intNum.add(visit(numAtomContext));
        return intNum;
    }
    @Override
    public Ast visitNumAtom(NottscriptParser.NumAtomContext ctx) {
        int num = Integer.parseInt(ctx.getText());
        return new Ast.Atom.numAtom(num);
    }
    @Override
    public Ast visitNameAtom(NottscriptParser.NameAtomContext ctx) {
        String name = ctx.getText();
        if((ctx.getParent() instanceof NottscriptParser.NameUnitContext)){
            int countUses =0;
            for(SymbolTable st: intSymbolTable.getChildren()){
                if(st.getSymbols().containsKey(name)){
                    countUses++;
                }
            }
            if(countUses>1){
                throw new UnsupportedOperationException("Methods may not share names");
            }
            else{
                intSymbolTable.getChildren().getLast().define(name,new SymbolData(name,"unitID",name,intSymbolTable.getChildren().getLast().getScopeName() + "_" + ctx.parent.getChild(1).getText(), ctx.parent.getChild(0).getText()));
            }
        } else if (ctx.getParent() instanceof NottscriptParser.ReturnFuncBlockContext) {
            intSymbolTable.getChildren().getLast().define(name,new SymbolData(name,"unitID",name,intSymbolTable.getChildren().getLast().getSymbols().values().iterator().next().scope,"return"));
        } else{
            if(!cType.equals("ALL_DECLARED")){//Declaration section of code
                if(intSymbolTable.getChildren().getLast().getSymbols().containsKey(name)){
                    if(Objects.equals(intSymbolTable.getChildren().getLast().getSymbols().get(name).type, "unitID")){
                        intSymbolTable.getChildren().getLast().define(name,new SymbolData(name,cType.toLowerCase(),null,intSymbolTable.getChildren().getLast().getSymbols().values().iterator().next().scope,intSymbolTable.getChildren().getLast().lookup(name).get().notes));
                    }
                    else{
                        throw new UnsupportedOperationException("Redeclaration of existing variable");
                    }
                }
                else
                    intSymbolTable.getChildren().getLast().define(name,new SymbolData(name,cType.toLowerCase(),null,intSymbolTable.getChildren().getLast().getSymbols().values().iterator().next().scope));
            }
        }
        return new Ast.Atom.nameAtom(name);
    }
    @Override
    public Ast visitNodeAtom(NottscriptParser.NodeAtomContext ctx){
        String nodeType = ctx.getText();
        return new Ast.Atom.nodeAtom(nodeType);
    }
    private int binToDec(String binVal) {
        return Integer.parseInt(binVal,2);
    }

    private int oct2Dec(String octVal) {
        return Integer.parseInt(octVal, 8);
    }

    static int hex2Dec(String n)
    {
        return Integer.parseInt(n, 16);
    }
}
