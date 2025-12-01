package uk.ac.nott.cs.comp3012.coursework.tac;

import java.util.List;
import uk.ac.nott.cs.comp3012.coursework.AstVisitor;
import uk.ac.nott.cs.comp3012.coursework.ast.Ast;

public class TacGenerator implements AstVisitor<List<TacInstr>> {

    @Override
    public List<TacInstr> visitProgram(Ast.Units ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitProgramBlock(Ast.ProgramUnit ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitVoidFuncBlock(Ast.FuncRVoidUnit ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitReturnFuncBlock(Ast.FuncRValueUnit ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitSubrtBlock(Ast.SbrtUnit ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitCustomTypeDeclBlock(Ast.CustomTypeDefUnit ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitDeclaratorParamList(Ast.FuncDefineParams ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitNameUnit(Ast.NameUnit ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitDeclareVar(Ast.DeclareVariable ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitDeclPtr(Ast.DeclarePointer ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitDeclArray(Ast.DeclareArray ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitDeclPtrArray(Ast.DeclarePointerArray ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitInbuilt(Ast.InbuiltTypeSpec ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitCustom(Ast.CustomTypeSpec ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitBaseAssign(Ast.NormalAssign ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitArrayAssign(Ast.ArrayAssign ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitCtAssign(Ast.CustomTypeAssign ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitCtArrayAssign(Ast.CustomTypeArrayAssign ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitCall(Ast.SbrtCall ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitIfBlock(Ast.IfBlock ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitIfElse(Ast.IfElseBlock ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitIfStmt(Ast.IfStatement ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitDoIncrN1(Ast.DoIncrNot1 ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitDoIncr1(Ast.DoIncr1 ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitDoWhile(Ast.DoWhile ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitRead(Ast.Read ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitWrite(Ast.Write ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitAllocPtr(Ast.AllocPtr ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitAllocPtrArray(Ast.AllocPtrArray ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitDeallocPtr(Ast.DeallocPtr ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitFuncCall(Ast.FuncCall ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitElseStmt(Ast.ElseStmt ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitDoParam(Ast.DoParam ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitReadParam(Ast.ReadParam ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitArrayIndex(Ast.ArrayIndex ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitArray(Ast.ArrayDef ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitParamSubList(Ast.ParamSubList ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitParamList(Ast.ParamList ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitExpr(Ast.Expr ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitLogExpr(Ast.LogExpr ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitRelExpr(Ast.RelExpr ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitConcatExpr(Ast.ConcatExpr ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitAddSubExpr(Ast.AddSubExpr ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitMulDivExpr(Ast.MulDivExpr ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitPowExpr(Ast.PowExpr ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitFieldAccExpr(Ast.FieldAccessExpr ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitLogicSExpr(Ast.Atom.boolAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitRealSExpr(Ast.Atom.realAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitHexSExpr(Ast.Atom.hexNumAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitBinSExpr(Ast.Atom.binNumAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitCharSeqSExpr(Ast.Atom.charLiteralAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitIntSExpr(Ast.IntSExpr ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitExprSExpr(Ast.ExprSExpr ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitArrSExpr(Ast.ArrayDef ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitFuncSExpr(Ast.FuncSExpr ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitNameSExpr(Ast.NameSExpr ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitRelativeOp(Ast.Atom.relAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitTypeAtom(Ast.Atom.typeAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitLogicalOp(Ast.Atom.logicOpAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitMulDivOp(Ast.Atom.mulDivAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitStar(Ast.Atom.starAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitAddSubOp(Ast.Atom.addSubAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitIntnum(Ast.IntNum ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitNumAtom(Ast.Atom.numAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitNameAtom(Ast.Atom.nameAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitNodeAtom(Ast.Atom.nodeAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visitOctAtom(Ast.Atom.octNumAtom ctx) {
        return List.of();
    }

    @Override
    public List<TacInstr> visit(Ast ast) {
        return List.of();
    }
}
