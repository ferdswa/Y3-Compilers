package uk.ac.nott.cs.comp3012.coursework.types;


import uk.ac.nott.cs.comp3012.coursework.AstVisitor;
import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolTable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TypeChecker implements AstVisitor<Type> {
    SymbolTable symbolTable = new SymbolTable();
    @Override
    public Type visitProgram(Ast.Units ctx) {
        return null;
    }

    @Override
    public Type visitProgramBlock(Ast.ProgramUnit ctx) {
        return null;
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
        return null;
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
        return null;
    }

    @Override
    public Type visitIfElse(Ast.IfElseBlock ctx) {
        return null;
    }

    @Override
    public Type visitIfStmt(Ast.IfStatement ctx) {
        return null;
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
        return null;
    }

    @Override
    public Type visitWrite(Ast.Write ctx) {
        return null;
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
    public Type visitElseStmt(Ast.ElseStmt ctx) {
        return null;
    }

    @Override
    public Type visitDoParam(Ast.DoParam ctx) {
        return null;
    }

    @Override
    public Type visitReadParam(Ast.ReadParam ctx) {
        return null;
    }

    @Override
    public Type visitArrayIndex(Ast.ArrayIndex ctx) {
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
        return new Type.ArrayType(baseType, dims);
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
        return null;
    }

    @Override
    public Type visitLogExpr(Ast.LogExpr ctx) {
        return null;
    }

    @Override
    public Type visitRelExpr(Ast.RelExpr ctx) {
        return null;
    }

    @Override
    public Type visitConcatExpr(Ast.ConcatExpr ctx) {
        return null;
    }

    @Override
    public Type visitAddSubExpr(Ast.AddSubExpr ctx) {
        return null;
    }

    @Override
    public Type visitMulDivExpr(Ast.MulDivExpr ctx) {
        return null;
    }

    @Override
    public Type visitPowExpr(Ast.PowExpr ctx) {
        return null;
    }

    @Override
    public Type visitFieldAccExpr(Ast.FieldAccessExpr ctx) {
        return null;
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
    public Type visitFuncSExpr(Ast.FuncSExpr ctx) {
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
    public Type visitLogicalOp(Ast.Atom.logicAtom ctx) {
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
    public Type visit(Ast ast) {
        return null;
    }
}
