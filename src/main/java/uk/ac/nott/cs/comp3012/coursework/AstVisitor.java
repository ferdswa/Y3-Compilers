package uk.ac.nott.cs.comp3012.coursework;

import uk.ac.nott.cs.comp3012.coursework.ast.Ast;

/**
 * Interface for any class that can walk an AST.
 *
 * @param <T> type that the visit methods should return
 */
public interface AstVisitor<T> {
        T visitProgram(Ast.Units ctx);
        T visitProgramBlock(Ast.ProgramUnit ctx);
        T visitVoidFuncBlock(Ast.FuncRVoidUnit ctx);
        T visitReturnFuncBlock(Ast.FuncRValueUnit ctx);
        T visitSubrtBlock(Ast.SbrtUnit ctx);
        T visitCustomTypeDeclBlock(Ast.CustomTypeDefUnit ctx);
        T visitDeclaratorParamList(Ast.FuncDefineParams ctx);
        T visitNameUnit(Ast.NameUnit ctx);
        T visitDeclareVar(Ast.DeclareVariable ctx);
        T visitDeclPtr(Ast.DeclarePointer ctx);
        T visitDeclArray(Ast.DeclareArray ctx);
        T visitDeclPtrArray(Ast.DeclarePointerArray ctx);
        T visitInbuilt(Ast.InbuiltTypeSpec ctx);
        T visitCustom(Ast.CustomTypeSpec ctx);
        T visitBaseAssign(Ast.NormalAssign ctx);
        T visitArrayAssign(Ast.ArrayAssign ctx);
        T visitCtAssign(Ast.CustomTypeAssign ctx);
        T visitCtArrayAssign(Ast.CustomTypeArrayAssign ctx);
        T visitCall(Ast.SbrtCall ctx);
        T visitIfBlock(Ast.IfBlock ctx);
        T visitIfElse(Ast.IfElseBlock ctx);
        T visitIfStmt(Ast.IfStatement ctx);
        T visitDoIncr1(Ast.DoIncr1 ctx);
        T visitDoIncrN1(Ast.DoIncrNot1 ctx);
        T visitDoWhile(Ast.DoWhile ctx);
        T visitRead(Ast.Read ctx);
        T visitWrite(Ast.Write ctx);
        T visitAllocPtr(Ast.AllocPtr ctx);
        T visitAllocPtrArray(Ast.AllocPtrArray ctx);
        T visitDeallocPtr(Ast.DeallocPtr ctx);
        T visitFuncCall(Ast.FuncCall ctx);
        T visitElseStmt(Ast.ElseStmt ctx);
        T visitDoParam(Ast.DoParam ctx);
        T visitReadParam(Ast.ReadParam ctx);
        T visitArrayIndex(Ast.ArrayIndex ctx);
        T visitArray(Ast.ArrayDef ctx);
        T visitParamSubList(Ast.ParamSubList ctx);
        T visitParamList(Ast.ParamList ctx);
        T visitExpr(Ast.Expr ctx);
        T visitLogExpr(Ast.LogExpr ctx);
        T visitRelExpr(Ast.RelExpr ctx);
        T visitConcatExpr(Ast.ConcatExpr ctx);
        T visitAddSubExpr(Ast.AddSubExpr ctx);
        T visitMulDivExpr(Ast.MulDivExpr ctx);
        T visitPowExpr(Ast.PowExpr ctx);
        T visitFieldAccExpr(Ast.FieldAccessExpr ctx);
        T visitLogicSExpr(Ast.Atom.boolAtom ctx);
        T visitRealSExpr(Ast.Atom.realAtom ctx);
        T visitHexSExpr(Ast.Atom.hexNumAtom ctx);
        T visitBinSExpr(Ast.Atom.binNumAtom ctx);
        T visitCharSeqSExpr(Ast.Atom.charLiteralAtom ctx);
        T visitIntSExpr(Ast.IntSExpr ctx);
        T visitExprSExpr(Ast.ExprSExpr ctx);
        T visitArrSExpr(Ast.ArrayDef ctx);
        T visitFuncSExpr(Ast.FuncSExpr ctx);
        T visitNameSExpr(Ast.NameSExpr ctx);
        T visitRelativeOp(Ast.Atom.relAtom ctx);
        T visitTypeAtom(Ast.Atom.typeAtom ctx);
        T visitLogicalOp(Ast.Atom.logicAtom ctx);
        T visitMulDivOp(Ast.Atom.mulDivAtom ctx);
        T visitStar(Ast.Atom.starAtom ctx);
        T visitAddSubOp(Ast.Atom.addSubAtom ctx);
        T visitIntnum(Ast.IntNum ctx);
        T visitNumAtom(Ast.Atom.numAtom ctx);
        T visitNameAtom(Ast.Atom.nameAtom ctx);
        T visitNodeAtom(Ast.Atom.nodeAtom ctx);
        T visitOctAtom(Ast.Atom.octNumAtom ctx);
        T visit(Ast ast);
    }
