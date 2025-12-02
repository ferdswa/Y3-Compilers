package uk.ac.nott.cs.comp3012.coursework.types;

import uk.ac.nott.cs.comp3012.coursework.AstVisitor;
import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolTable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TypeChecker implements AstVisitor<Type> {
    SymbolTable symbolTable = new SymbolTable();
    public TypeChecker(SymbolTable symbolData) {
        this.symbolTable = symbolData;
    }
    @Override
    public Type visitProgram(Ast.Units ctx) {
        for(Ast unit: ctx){
            switch (unit){
                case Ast.ProgramUnit programUnit ->{
                    if(symbolTable.getParent() != null){
                        symbolTable=symbolTable.getParent();
                    }
                    symbolTable=symbolTable.getChildren().get(ctx.indexOf(unit));
                    visitProgramBlock(programUnit);
                }
                case Ast.FuncRValueUnit returnFuncBlockContext -> {
                    if(symbolTable.getParent() != null){
                        symbolTable=symbolTable.getParent();
                    }
                    symbolTable=symbolTable.getChildren().get(ctx.indexOf(unit));
                    visitReturnFuncBlock(returnFuncBlockContext);
                }
                case Ast.FuncRVoidUnit voidFuncBlockContext -> {
                    if(symbolTable.getParent() != null){
                        symbolTable=symbolTable.getParent();
                    }
                    symbolTable=symbolTable.getChildren().get(ctx.indexOf(unit));
                    visitVoidFuncBlock(voidFuncBlockContext);
                }
                case Ast.CustomTypeDefUnit customTypeDeclBlockContext -> {
                    if(symbolTable.getParent() != null){
                        symbolTable=symbolTable.getParent();
                    }
                    symbolTable=symbolTable.getChildren().get(ctx.indexOf(unit));
                    visitCustomTypeDeclBlock(customTypeDeclBlockContext);
                }
                default -> throw new IllegalStateException("Unrecognised Program Subunit");
            }
        }
        return Type.BaseType.OK;
    }

    @Override
    public Type visitProgramBlock(Ast.ProgramUnit ctx) {
        for(Ast unit: ctx){
            switch (unit) {
                case Ast.NameUnit nameUnit -> {
                    visitNameUnit(nameUnit);
                }
                case Ast.DeclareVariable declareVariable -> {
                    visitDeclareVar(declareVariable);
                }
                case Ast.NormalAssign normalAssign -> {
                    visitBaseAssign(normalAssign);
                }
                case Ast.ArrayAssign arrayAssign -> {
                    visitArrayAssign(arrayAssign);
                }
                case Ast.CustomTypeAssign customTypeAssign -> {
                    visitCtAssign(customTypeAssign);
                }
                case Ast.CustomTypeArrayAssign customTypeArrayAssign -> {
                    visitCtArrayAssign(customTypeArrayAssign);
                }
                case Ast.Read read -> {
                    visitRead(read);
                }
                case Ast.Write write -> {
                    visitWrite(write);
                }
                case Ast.IfElseBlock ifElseBlock -> {
                    visitIfElse(ifElseBlock);
                }
                case Ast.IfBlock ifBlock -> {
                    visitIfBlock(ifBlock);
                }
                case Ast.IfStatement ifStmt -> {
                    visitIfStmt(ifStmt);
                }
                default -> throw new UnsupportedOperationException("Statement type not supported :(");
            }
        }
        return Type.BaseType.OK;
    }

    @Override
    public Type visitVoidFuncBlock(Ast.FuncRVoidUnit ctx) {
        return null;
    }

    @Override
    public Type visitReturnFuncBlock(Ast.FuncRValueUnit ctx) {
        return null;
    }

    @Override
    public Type visitSubrtBlock(Ast.SbrtUnit ctx) {
        return null;
    }

    @Override
    public Type visitCustomTypeDeclBlock(Ast.CustomTypeDefUnit ctx) {
        return null;
    }

    @Override
    public Type visitDeclaratorParamList(Ast.FuncDefineParams ctx) {
        return null;
    }

    @Override
    public Type visitNameUnit(Ast.NameUnit ctx) {
        return null;
    }

    @Override
    public Type visitDeclareVar(Ast.DeclareVariable ctx) {
        return null;
    }

    @Override
    public Type visitDeclPtr(Ast.DeclarePointer ctx) {
        return null;
    }

    @Override
    public Type visitDeclArray(Ast.DeclareArray ctx) {
        return null;
    }

    @Override
    public Type visitDeclPtrArray(Ast.DeclarePointerArray ctx) {
        return null;
    }

    @Override
    public Type visitInbuilt(Ast.InbuiltTypeSpec ctx) {
        return null;
    }

    @Override
    public Type visitCustom(Ast.CustomTypeSpec ctx) {
        return null;
    }

    @Override
    public Type visitBaseAssign(Ast.NormalAssign ctx) {
        HashSet<Type> set = new HashSet<>();
        set.add(visitNameAtom((Ast.Atom.nameAtom) ctx.getFirst()));
        set.add(visitExpr((Ast.Expr) ctx.getLast()));
        if(set.size()==1){
            return Type.BaseType.OK;
        }
        throw new UnsupportedOperationException("Type of expression does not match type of variable");
    }


    @Override
    public Type visitArrayAssign(Ast.ArrayAssign ctx) {
        return null;
    }

    @Override
    public Type visitCtAssign(Ast.CustomTypeAssign ctx) {
        return null;
    }

    @Override
    public Type visitCtArrayAssign(Ast.CustomTypeArrayAssign ctx) {
        return null;
    }

    @Override
    public Type visitCall(Ast.SbrtCall ctx) {
        return null;
    }

    @Override
    public Type visitIfBlock(Ast.IfBlock ctx) {
        Type t = visitExpr((Ast.Expr) ctx.getFirst());
        if(t==Type.BaseType.Logical){
            for(int i = 1; i < ctx.size(); i++) {
                switch (ctx.get(i)) {//Statements
                    case Ast.NormalAssign normalAssign -> {
                        visitBaseAssign(normalAssign);
                    }
                    case Ast.ArrayAssign arrayAssign -> {
                        visitArrayAssign(arrayAssign);
                    }
                    case Ast.CustomTypeAssign customTypeAssign -> {
                        visitCtAssign(customTypeAssign);
                    }
                    case Ast.CustomTypeArrayAssign customTypeArrayAssign -> {
                        visitCtArrayAssign(customTypeArrayAssign);
                    }
                    case Ast.Read read -> {
                        visitRead(read);
                    }
                    case Ast.Write write -> {
                        visitWrite(write);
                    }
                    case Ast.IfElseBlock ifElseBlock -> {
                        visitIfElse(ifElseBlock);
                    }
                    case Ast.IfBlock ifBlock -> {
                        visitIfBlock(ifBlock);
                    }
                    case Ast.IfStatement ifStmt -> {
                        visitIfStmt(ifStmt);
                    }
                    case Ast.ElseStmt elseStmt -> {
                        visitElseStmt(elseStmt);
                    }
                    case Ast.Atom.exitAtom exitAtom -> {
                        visitExitStmt(exitAtom);
                    }
                    default -> throw new UnsupportedOperationException("Statement type not supported :(");
                }
            }
        }
        else{
            throw new UnsupportedOperationException("If statement expression is not a logical expression");
        }
        return Type.BaseType.OK;
    }

    @Override
    public Type visitIfElse(Ast.IfElseBlock ctx) {
        Type t = visitExpr((Ast.Expr) ctx.getFirst());
        if(t==Type.BaseType.Logical){
            for(int i = 1; i < ctx.size(); i++) {
                switch (ctx.get(i)) {//Statements
                    case Ast.NormalAssign normalAssign -> {
                        visitBaseAssign(normalAssign);
                    }
                    case Ast.ArrayAssign arrayAssign -> {
                        visitArrayAssign(arrayAssign);
                    }
                    case Ast.CustomTypeAssign customTypeAssign -> {
                        visitCtAssign(customTypeAssign);
                    }
                    case Ast.CustomTypeArrayAssign customTypeArrayAssign -> {
                        visitCtArrayAssign(customTypeArrayAssign);
                    }
                    case Ast.Read read -> {
                        visitRead(read);
                    }
                    case Ast.Write write -> {
                        visitWrite(write);
                    }
                    case Ast.IfElseBlock ifElseBlock -> {
                        visitIfElse(ifElseBlock);
                    }
                    case Ast.IfBlock ifBlock -> {
                        visitIfBlock(ifBlock);
                    }
                    case Ast.IfStatement ifStmt -> {
                        visitIfStmt(ifStmt);
                    }
                    case Ast.Atom.exitAtom exitAtom -> {
                        visitExitStmt(exitAtom);
                    }
                    case Ast.ElseStmt elseStmt -> {
                        visitElseStmt(elseStmt);
                    }
                    default -> throw new UnsupportedOperationException("Statement type not supported :(");
                }
            }
        }
        else{
            throw new UnsupportedOperationException("If statement expression is not a logical expression");
        }
        return Type.BaseType.OK;
    }

    @Override
    public Type visitElseStmt(Ast.ElseStmt ctx) {
        for(int i = 1; i < ctx.size(); i++) {
            switch (ctx.get(i)) {//Statements
                case Ast.NormalAssign normalAssign -> {
                    visitBaseAssign(normalAssign);
                }
                case Ast.ArrayAssign arrayAssign -> {
                    visitArrayAssign(arrayAssign);
                }
                case Ast.CustomTypeAssign customTypeAssign -> {
                    visitCtAssign(customTypeAssign);
                }
                case Ast.CustomTypeArrayAssign customTypeArrayAssign -> {
                    visitCtArrayAssign(customTypeArrayAssign);
                }
                case Ast.Read read -> {
                    visitRead(read);
                }
                case Ast.Write write -> {
                    visitWrite(write);
                }
                case Ast.IfElseBlock ifElseBlock -> {
                    visitIfElse(ifElseBlock);
                }
                case Ast.IfBlock ifBlock -> {
                    visitIfBlock(ifBlock);
                }
                case Ast.IfStatement ifStmt -> {
                    visitIfStmt(ifStmt);
                }
                case Ast.Atom.exitAtom exitAtom -> {
                    visitExitStmt(exitAtom);
                }
                case Ast.ElseStmt elseStmt -> {
                    visitElseStmt(elseStmt);
                }
                default -> throw new UnsupportedOperationException("Statement type not supported :(");
            }
        }
        return Type.BaseType.OK;
    }

    @Override
    public Type visitIfStmt(Ast.IfStatement ctx) {//Single line if
        Type t = visitExpr((Ast.Expr)ctx.getFirst());
        if(t==Type.BaseType.Logical){
            switch (ctx.getLast()){//Statement of single line if
                case Ast.NormalAssign normalAssign -> {
                    return visitBaseAssign(normalAssign);
                }
                case Ast.ArrayAssign arrayAssign -> {
                    return visitArrayAssign(arrayAssign);
                }
                case Ast.CustomTypeAssign customTypeAssign -> {
                    return visitCtAssign(customTypeAssign);
                }
                case Ast.CustomTypeArrayAssign customTypeArrayAssign -> {
                    return visitCtArrayAssign(customTypeArrayAssign);
                }
                case Ast.Read read -> {
                    return visitRead(read);
                }
                case Ast.Write write -> {
                    return visitWrite(write);
                }
                case Ast.IfElseBlock ifElseBlock -> {
                    return visitIfElse(ifElseBlock);
                }
                case Ast.IfBlock ifBlock -> {
                    return visitIfBlock(ifBlock);
                }
                case Ast.IfStatement ifStmt -> {
                    return visitIfStmt(ifStmt);
                }
                case Ast.Atom.exitAtom exitAtom -> {
                    return visitExitStmt(exitAtom);
                }
                default -> throw new UnsupportedOperationException("Statement type not supported :(");
            }
        }
        else{
            throw new UnsupportedOperationException("If statement expression is not a logical expression");
        }
    }

    @Override
    public Type visitDoIncrN1(Ast.DoIncrNot1 ctx) {
        return null;
    }

    @Override
    public Type visitDoIncr1(Ast.DoIncr1 ctx) {
        return null;
    }

    @Override
    public Type visitDoWhile(Ast.DoWhile ctx) {
        return null;
    }

    @Override
    public Type visitRead(Ast.Read ctx) {
        for(Ast ctx1: ctx){//Run through the list of read parameters, if nothing is thrown then OK
            visitReadParam((Ast.ReadParam) ctx1);
        }
        return Type.BaseType.OK;
    }

    @Override
    public Type visitWrite(Ast.Write ctx) {
        for(Ast ctx1: ctx){//Run through the list of read parameters, if nothing is thrown then OK
            visitExpr((Ast.Expr) ctx1);
        }
        return Type.BaseType.OK;
    }

    @Override
    public Type visitAllocPtr(Ast.AllocPtr ctx) {
        return null;
    }

    @Override
    public Type visitAllocPtrArray(Ast.AllocPtrArray ctx) {
        return null;
    }

    @Override
    public Type visitDeallocPtr(Ast.DeallocPtr ctx) {
        return null;
    }

    @Override
    public Type visitFuncCall(Ast.FuncCall ctx) {
        return null;
    }

    @Override
    public Type visitDoParam(Ast.DoParam ctx) {
        return null;
    }

    @Override
    public Type visitReadParam(Ast.ReadParam ctx) {
        for(Ast ctx1: ctx){//Run through the two types of read parameters, if nothing is thrown then OK
            if(ctx1 instanceof Ast.Atom.nameAtom){
                visitNameAtom((Ast.Atom.nameAtom) ctx1);
            }
            else{
                visitArray((Ast.ArrayDef) ctx1);
            }
        }
        return Type.BaseType.OK;
    }

    @Override
    public Type visitArrayIndex(Ast.ArrayIndex ctx) {//NOT CORRECT
        for(Ast index: ctx){
            if(index instanceof Ast.Atom.nameAtom){
                return visitNameAtom((Ast.Atom.nameAtom)index);
            }
            else if(index instanceof Ast.Atom.numAtom) {
                return visitNumAtom((Ast.Atom.numAtom) index);
            }
            else{
                throw new UnsupportedOperationException("Array indexed with non-number and non-variable value");
            }
        }
        throw new UnsupportedOperationException("Array indexed with non-number and non-variable value");
    }

    @Override
    public Type visitArray(Ast.ArrayDef ctx) {
        Type baseType = visitNameAtom((Ast.Atom.nameAtom)ctx.getFirst());
        List<Integer> dims = new ArrayList<>();
        for(int i = 1; i < ctx.size(); i++){
            Type v = visitArrayIndex((Ast.ArrayIndex) ctx.get(i));
            if(v == Type.BaseType.Number){
                dims.add(i);
            }
            else{
                throw new UnsupportedOperationException("Array indexed with non-number and non-variable value");
            }
        }
        return baseType;
    }

    @Override
    public Type visitParamSubList(Ast.ParamSubList ctx) {
        return null;
    }

    @Override
    public Type visitParamList(Ast.ParamList ctx) {
        return null;
    }

    @Override
    public Type visitExpr(Ast.Expr ctx) {
        return visitOrExpr((Ast.OrExpr)ctx.getFirst());
    }

    @Override
    public Type visitOrExpr(Ast.OrExpr ctx) {
        HashSet<Type> types = new HashSet<>();
        if(ctx.size()>1){//RelExpr being calculated. Check types of all subexprs. If not == then throw error.
            for(Ast expr: ctx){
                if(expr instanceof Ast.AndExpr){//Find types of RelExprs's sub exprs. Ignore the ops for now as they aren't relevant for typechecking
                    types.add(visitAndExpr((Ast.AndExpr)expr));
                }
            }
            if(types.size()==1){//Ensure we're comparing exprs of equal type, then return that this evaluates to a logical value
                return Type.BaseType.Logical;
            }
            else{
                throw new UnsupportedOperationException("Attempting to perform relative comparison calculation on multiple types");
            }
        }
        else{//Not a logical expression. Continue.
            return visitAndExpr((Ast.AndExpr)ctx.getFirst());
        }
    }

    @Override
    public Type visitAndExpr(Ast.AndExpr ctx) {
        HashSet<Type> types = new HashSet<>();
        if(ctx.size()>1){//RelExpr being calculated. Check types of all subexprs. If not == then throw error.
            for(Ast expr: ctx){
                if(expr instanceof Ast.RelExpr){//Find types of RelExprs's sub exprs. Ignore the ops for now as they aren't relevant for typechecking
                    types.add(visitRelExpr((Ast.RelExpr)expr));
                }
            }
            if(types.size()==1){//Ensure we're comparing exprs of equal type, then return that this evaluates to a logical value
                return Type.BaseType.Logical;
            }
            else{
                System.out.println(types);
                throw new UnsupportedOperationException("Attempting to perform relative comparison calculation on multiple types");
            }
        }
        else{//Not a logical expression. Continue.
            return visitRelExpr((Ast.RelExpr)ctx.getFirst());
        }
    }

    @Override
    public Type visitRelExpr(Ast.RelExpr ctx) {
        HashSet<Type> types = new HashSet<>();
        if(ctx.size()>1){//RelExpr being calculated. Check types of all subexprs. If not == then throw error.
            for(Ast expr: ctx){
                if(expr instanceof Ast.ConcatExpr){//Find types of RelExprs's sub exprs. Ignore the ops for now as they aren't relevant for typechecking
                    types.add(visitConcatExpr((Ast.ConcatExpr)expr));
                }
            }
            if(types.size()==1){//Ensure we're comparing exprs of equal type, then return that this evaluates to a logical value
                return Type.BaseType.Logical;
            }
            else{
                throw new UnsupportedOperationException("Attempting to perform relative comparison calculation on multiple types");
            }
        }
        else{//Not a relative comparison expression. Continue.
            return visitConcatExpr((Ast.ConcatExpr)ctx.getFirst());
        }
    }

    @Override
    public Type visitConcatExpr(Ast.ConcatExpr ctx) {
        HashSet<Type> types = new HashSet<>();
        if(ctx.size()>1){//ConcatExpr being calculated. Check types of all subexprs. If not == then throw error.
            for(Ast expr: ctx){
                if(expr instanceof Ast.AddSubExpr){//Find types of ConcatExpr's sub exprs.
                    types.add(visitAddSubExpr((Ast.AddSubExpr)expr));
                }
            }
            if(types.size()==1 && types.contains(Type.BaseType.Character)){//ConcatExprs can only handle strings
                return Type.BaseType.Character;
            }
            else{
                throw new UnsupportedOperationException("Attempting to perform concatenation calculation on multiple or non-string types");
            }
        }
        else{//Not a muldiv expression. Continue.
            return visitAddSubExpr((Ast.AddSubExpr)ctx.getFirst());
        }
    }

    @Override
    public Type visitAddSubExpr(Ast.AddSubExpr ctx) {
        HashSet<Type> types = new HashSet<>();
        if(ctx.getFirst() instanceof Ast.Atom.addSubAtom){//Is the operation starting with a sign?
            if(ctx.size()>2){//There is more than one expr being added/subbed
                return addSubLongVerify(ctx, types);
            }
            else{//Not an addsub expression. Continue. Ensure that the type of the subexpr is a number, as it is being hit with a sign
                Type t = visitMulDivExpr((Ast.MulDivExpr)ctx.getLast());
                if(t == Type.BaseType.Number)
                    return t;
                else
                    throw new UnsupportedOperationException("Attempting to subtract or add a non-numeric type");
            }
        }
        else {
            if(ctx.size()>1){//There is more than one expr being added/subbed
                return addSubLongVerify(ctx, types);
            }
            else{//Not an addsub expression. Continue.
                return visitMulDivExpr((Ast.MulDivExpr)ctx.getFirst());
            }
        }
    }

    private Type addSubLongVerify(Ast.AddSubExpr ctx, HashSet<Type> types) {
        for(Ast expr: ctx){
            if(expr instanceof Ast.MulDivExpr){//Find types of AddSub's sub exprs. Ignore the ops for now as they aren't relevant for typechecking
                types.add(visitMulDivExpr((Ast.MulDivExpr)expr));
            }
        }
        if(types.size()==1 && types.contains(Type.BaseType.Number)){//PowExprs can only handle numbers
            return Type.BaseType.Number;
        }
        else{
            throw new UnsupportedOperationException("Attempting to perform addition/subtraction calculation on multiple or non-numeric types");
        }
    }

    @Override
    public Type visitMulDivExpr(Ast.MulDivExpr ctx) {
        HashSet<Type> types = new HashSet<>();
        if(ctx.size()>1){//Power expr being calculated. Check types of all subexprs. If not == then throw error.
            for(Ast expr: ctx){
                if(expr instanceof Ast.PowExpr){//Find types of MulDiv's sub exprs. Ignore the ops for now as they aren't relevant for typechecking
                    types.add(visitPowExpr((Ast.PowExpr)expr));
                }
            }
            if(types.size()==1 && types.contains(Type.BaseType.Number)){//PowExprs can only handle numbers
                return Type.BaseType.Number;
            }
            else{
                throw new UnsupportedOperationException("Attempting to perform multiplication/division calculation on multiple or non-numeric types");
            }
        }
        else{//Not a muldiv expression. Continue.
            return visitPowExpr((Ast.PowExpr)ctx.getFirst());
        }
    }

    @Override
    public Type visitPowExpr(Ast.PowExpr ctx) {
        HashSet<Type> types = new HashSet<>();
        if(ctx.size()>1){//Power expr being calculated. Check types of all subexprs. If not == then throw error.
            for(Ast expr: ctx){
                if(expr instanceof Ast.FieldAccessExpr){
                    types.add(visitFieldAccExpr((Ast.FieldAccessExpr)expr));
                }
                else{//PowExprs should only consist of FieldAccExprs so something has gone seriously wrong here.
                    throw new UnsupportedOperationException("How?");
                }
            }
            if(types.size()==1 && types.contains(Type.BaseType.Number)){//PowExprs can only handle numbers
                return Type.BaseType.Number;
            }
            else{
                throw new UnsupportedOperationException("Attempting to perform exponent calculation on multiple or non-numeric types");
            }
        }
        else{//Not a power expression. Continue.
            return visitFieldAccExpr((Ast.FieldAccessExpr)ctx.getFirst());
        }
    }

    @Override
    public Type visitFieldAccExpr(Ast.FieldAccessExpr ctx) {
        if(ctx.size()>1){//Attempting field access. Return type of field. Throw error if attempting to access a non-custom type
            if(ctx.getFirst() instanceof Ast.NameSExpr){
                return visitNameSExpr((Ast.NameSExpr)ctx.getLast());
            }
            else{
                throw new UnsupportedOperationException(String.format("Cannot perform field access on a basic expression of type: %s", ctx.getFirst().getClass().getSimpleName()));
            }
        }
        else{
            switch (ctx.getFirst()){
                case Ast.NameSExpr nameSExpr -> {
                    return visitNameSExpr(nameSExpr);
                }
                case Ast.ExprSExpr exprSExpr -> {
                    return visitExprSExpr(exprSExpr);
                }
                case Ast.ArraySExpr arrayDefSExpr -> {
                    return visitArrSExpr((Ast.ArrayDef) arrayDefSExpr.getFirst());
                }
                case Ast.IntSExpr intSExpr -> {
                    return visitIntSExpr(intSExpr);
                }
                case Ast.Atom.binNumAtom binNumAtom ->
                {
                    return visitBinSExpr(binNumAtom);
                }
                case Ast.Atom.octNumAtom octNumAtom-> {
                    return visitOctAtom(octNumAtom);
                }
                case Ast.Atom.hexNumAtom hexNumAtom-> {
                    return visitHexSExpr(hexNumAtom);
                }
                case Ast.Atom.realAtom realAtom -> {
                    return visitRealSExpr(realAtom);
                }
                case Ast.FuncSExpr funcSExpr -> {
                    return visitFuncSExpr(funcSExpr);
                }
                case Ast.Atom.charLiteralAtom charLiteralAtom -> {
                    return visitCharSeqSExpr(charLiteralAtom);
                }
                case Ast.Atom.boolAtom logicAtom -> {
                    return visitLogicSExpr(logicAtom);
                }
                default -> throw new IllegalStateException("Unexpected value: " + ctx.getFirst());
            }
        }
    }

    @Override
    public Type visitLogicSExpr(Ast.Atom.boolAtom ctx) {
        return Type.BaseType.Logical;
    }

    @Override
    public Type visitRealSExpr(Ast.Atom.realAtom ctx) {
        return Type.BaseType.Number;
    }

    @Override
    public Type visitHexSExpr(Ast.Atom.hexNumAtom ctx) {
        return Type.BaseType.Number;
    }

    @Override
    public Type visitBinSExpr(Ast.Atom.binNumAtom ctx) {
        return Type.BaseType.Number;
    }

    @Override
    public Type visitCharSeqSExpr(Ast.Atom.charLiteralAtom ctx) {
        return Type.BaseType.Character;
    }

    @Override
    public Type visitIntSExpr(Ast.IntSExpr ctx) {
        return Type.BaseType.Number;
    }

    @Override
    public Type visitExprSExpr(Ast.ExprSExpr ctx) {
        if(ctx.getFirst() instanceof Ast.Expr){
            return visitExpr((Ast.Expr) ctx.getFirst());
        }
        throw new UnsupportedOperationException("Not an expression");
    }

    @Override
    public Type visitArrSExpr(Ast.ArrayDef ctx) {
        return visitArray(ctx);
    }

    @Override
    public Type visitFuncSExpr(Ast.FuncSExpr ctx) {//This catches both arrays and function calls. Check current level symbol table, if present and an array -> array, if not, check level above for a method name.
        //Find the table using the nameAtom
        //find the return value of the table
        return null;
    }

    @Override
    public Type visitNameSExpr(Ast.NameSExpr ctx) {
        if(ctx.getFirst() instanceof Ast.Atom.nameAtom) {
            return visitNameAtom((Ast.Atom.nameAtom) ctx.getFirst());
        }
        throw new UnsupportedOperationException("Not a name");
    }

    @Override
    public Type visitRelativeOp(Ast.Atom.relAtom ctx) {
        return null;
    }

    @Override
    public Type visitTypeAtom(Ast.Atom.typeAtom ctx) {

        return null;
    }

    @Override
    public Type visitLogicalOp(Ast.Atom.logicOpAtom ctx) {
        return null;
    }

    @Override
    public Type visitMulDivOp(Ast.Atom.mulDivAtom ctx) {
        return null;
    }

    @Override
    public Type visitStar(Ast.Atom.starAtom ctx) {
        return null;
    }

    @Override
    public Type visitAddSubOp(Ast.Atom.addSubAtom ctx) {
        return null;
    }

    @Override
    public Type visitIntnum(Ast.IntNum ctx) {
        return Type.BaseType.Number;
    }

    @Override
    public Type visitNumAtom(Ast.Atom.numAtom ctx) {
        return Type.BaseType.Number;
    }

    @Override
    public Type visitNameAtom(Ast.Atom.nameAtom ctx) {
        AtomicReference<String> iType = new AtomicReference<>("");
        symbolTable.getSymbols().forEach((k,v)->{
           if(k.equals(ctx.name())){
               iType.set(v.type);
           }
        });
        switch (iType.get()) {
            case "integer", "real" -> {
                return Type.BaseType.Number;
            }
            case "character" -> {
                return Type.BaseType.Character;
            }
            case "logical" -> {
                return Type.BaseType.Logical;
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Type visitNodeAtom(Ast.Atom.nodeAtom ctx) {
        return null;
    }

    @Override
    public Type visitOctAtom(Ast.Atom.octNumAtom ctx) {
        return Type.BaseType.Number;
    }

    @Override
    public Type visitExitStmt(Ast.Atom.exitAtom ctx) {
        return null;
    }

    @Override
    public Type visit(Ast ast) {
        return null;
    }
}
