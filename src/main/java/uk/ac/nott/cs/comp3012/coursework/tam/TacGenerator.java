package uk.ac.nott.cs.comp3012.coursework.tam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.nott.cs.comp3012.coursework.AstVisitor;
import uk.ac.nott.cs.comp3012.coursework.NottscriptParser;
import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.tac.TacInstr;

import static uk.ac.nott.cs.comp3012.coursework.tam.TamRegister.*;

public class TacGenerator implements AstVisitor<TamInstruction> {
    int jumpOffset = 0;
    Map<String, Integer> jumps = new HashMap<>();

    @Override
    public TamInstruction visitProgram(Ast.Units ctx) {
        TamInstruction.InstructionList instructionList = new TamInstruction.InstructionList();
        instructionList.addAll((TamInstruction.InstructionList) visitProgramBlock((Ast.ProgramUnit) ctx.getFirst()));
        instructionList.add(new TamInstruction.Instruction(TamOpcode.HALT, CB, 0, 0));
        return instructionList;
    }

    @Override
    public TamInstruction visitProgramBlock(Ast.ProgramUnit ctx) {
        TamInstruction.InstructionList instructionList = new TamInstruction.InstructionList();
        for(int i = 1; i<ctx.size()-1;i++){
            switch(ctx.get(i)){
                case Ast.Write writeNode-> {
                    instructionList.addAll((TamInstruction.InstructionList)visitWrite(writeNode));
                }
                case Ast.IfStatement ifStatement-> {
                    instructionList.addAll((TamInstruction.InstructionList)visitIfStmt(ifStatement));
                }
                default -> throw new IllegalStateException("Statement unsupported" + ctx.get(i));
            }
        }
        return instructionList;
    }

    @Override
    public TamInstruction visitVoidFuncBlock(Ast.FuncRVoidUnit ctx) {
        return null;
    }

    @Override
    public TamInstruction visitReturnFuncBlock(Ast.FuncRValueUnit ctx) {
        return null;
    }

    @Override
    public TamInstruction visitSubrtBlock(Ast.SbrtUnit ctx) {
        return null;
    }

    @Override
    public TamInstruction visitCustomTypeDeclBlock(Ast.CustomTypeDefUnit ctx) {
        return null;
    }

    @Override
    public TamInstruction visitDeclaratorParamList(Ast.FuncDefineParams ctx) {
        return null;
    }

    @Override
    public TamInstruction visitNameUnit(Ast.NameUnit ctx) {
        return null;
    }

    @Override
    public TamInstruction visitDeclareVar(Ast.DeclareVariable ctx) {
        return null;
    }

    @Override
    public TamInstruction visitDeclPtr(Ast.DeclarePointer ctx) {
        return null;
    }

    @Override
    public TamInstruction visitDeclArray(Ast.DeclareArray ctx) {
        return null;
    }

    @Override
    public TamInstruction visitDeclPtrArray(Ast.DeclarePointerArray ctx) {
        return null;
    }

    @Override
    public TamInstruction visitInbuilt(Ast.InbuiltTypeSpec ctx) {
        return null;
    }

    @Override
    public TamInstruction visitCustom(Ast.CustomTypeSpec ctx) {
        return null;
    }

    @Override
    public TamInstruction visitBaseAssign(Ast.NormalAssign ctx) {
        return null;
    }

    @Override
    public TamInstruction visitArrayAssign(Ast.ArrayAssign ctx) {
        return null;
    }

    @Override
    public TamInstruction visitCtAssign(Ast.CustomTypeAssign ctx) {
        return null;
    }

    @Override
    public TamInstruction visitCtArrayAssign(Ast.CustomTypeArrayAssign ctx) {
        return null;
    }

    @Override
    public TamInstruction visitCall(Ast.SbrtCall ctx) {
        return null;
    }

    @Override
    public TamInstruction visitIfBlock(Ast.IfBlock ctx) {
        return null;
    }

    @Override
    public TamInstruction visitIfElse(Ast.IfElseBlock ctx) {
        return null;
    }

    @Override
    public TamInstruction visitIfStmt(Ast.IfStatement ctx) {
        TamInstruction.InstructionList instructionList = new TamInstruction.InstructionList();
        TamInstruction.InstructionList tInstrs = new TamInstruction.InstructionList();
        TamInstruction subExprs = visitExpr((Ast.Expr) ctx.getFirst());
        switch(subExprs){
            case TamInstruction.InstructionList instructions ->{
                instructionList.addAll(instructions);
            }
            case TamInstruction.Instruction instruction ->{
                instructionList.add(instruction);
            }
            default -> throw new IllegalStateException("Unexpected value: " + subExprs);
        }
        switch(ctx.getLast()){
            case Ast.Write writeNode-> {
                tInstrs.addAll((TamInstruction.InstructionList)visitWrite(writeNode));
            }
            case Ast.IfStatement ifStatement->{
                tInstrs.addAll((TamInstruction.InstructionList)visitIfStmt(ifStatement));
            }
            default -> throw new IllegalStateException("Statement unsupported" + ctx.getLast());
        }
        instructionList.add(new TamInstruction.Instruction(TamOpcode.JUMPIF, CP,0, tInstrs.size()));
        instructionList.addAll(tInstrs);
        return instructionList;
    }

    @Override
    public TamInstruction visitDoIncr1(Ast.DoIncr1 ctx) {
        return null;
    }

    @Override
    public TamInstruction visitDoIncrN1(Ast.DoIncrNot1 ctx) {
        return null;
    }

    @Override
    public TamInstruction visitDoWhile(Ast.DoWhile ctx) {
        return null;
    }

    @Override
    public TamInstruction visitRead(Ast.Read ctx) {
        return null;
    }

    @Override
    public TamInstruction visitWrite(Ast.Write ctx) {
        TamInstruction.InstructionList writeList = new TamInstruction.InstructionList();
        TamInstruction cExpr;
        for (Ast ast : ctx) {
            cExpr = visitExpr((Ast.Expr) ast);
            writeList.add((TamInstruction.Instruction) cExpr);
            cExpr = new TamInstruction.Instruction(TamOpcode.CALL, PB, 0, TamPrimitive.putint.value + 1);
            writeList.add((TamInstruction.Instruction) cExpr);
        }
        return writeList;
    }

    @Override
    public TamInstruction visitAllocPtr(Ast.AllocPtr ctx) {
        return null;
    }

    @Override
    public TamInstruction visitAllocPtrArray(Ast.AllocPtrArray ctx) {
        return null;
    }

    @Override
    public TamInstruction visitDeallocPtr(Ast.DeallocPtr ctx) {
        return null;
    }

    @Override
    public TamInstruction visitFuncCall(Ast.FuncCall ctx) {
        return null;
    }

    @Override
    public TamInstruction visitElseStmt(Ast.ElseStmt ctx) {
        return null;
    }

    @Override
    public TamInstruction visitDoParam(Ast.DoParam ctx) {
        return null;
    }

    @Override
    public TamInstruction visitReadParam(Ast.ReadParam ctx) {
        return null;
    }

    @Override
    public TamInstruction visitArrayIndex(Ast.ArrayIndex ctx) {
        return null;
    }

    @Override
    public TamInstruction visitArray(Ast.ArrayDef ctx) {
        return null;
    }

    @Override
    public TamInstruction visitParamSubList(Ast.ParamSubList ctx) {
        return null;
    }

    @Override
    public TamInstruction visitParamList(Ast.ParamList ctx) {
        return null;
    }

    @Override
    public TamInstruction visitExpr(Ast.Expr ctx) {
        return visitOrExpr((Ast.OrExpr) ctx.getFirst());
    }

    @Override
    public TamInstruction visitOrExpr(Ast.OrExpr ctx) {
        TamInstruction.InstructionList instructionList = new TamInstruction.InstructionList();
        if(ctx.size()>1){
            TamInstruction instr = visitAndExpr((Ast.AndExpr) ctx.getFirst());
            if(instr instanceof TamInstruction.Instruction){
                instructionList.add((TamInstruction.Instruction) instr);
            }
            else{
                instructionList.addAll((TamInstruction.InstructionList) instr);
            }
            for(int i=1;i<ctx.size();i++){
                TamInstruction instr1 = visitAndExpr((Ast.AndExpr) ctx.get(i));
                if(instr1 instanceof TamInstruction.Instruction){
                    instructionList.add((TamInstruction.Instruction) instr1);
                }
                else{
                    instructionList.addAll((TamInstruction.InstructionList) instr1);
                }
                instructionList.add(new TamInstruction.Instruction(TamOpcode.CALL, PB,0,TamPrimitive.or.value+1));
                //instructionList.add(new TamInstruction.Instruction(TamOpcode.RETURN,PB,1,3));
            }
            return instructionList;
        }
        else
            return visitAndExpr((Ast.AndExpr) ctx.getFirst());
    }
    @Override
    public TamInstruction visitAndExpr(Ast.AndExpr ctx) {
        TamInstruction.InstructionList instructionList = new TamInstruction.InstructionList();
        if(ctx.size()>1){
            instructionList.add((TamInstruction.Instruction) visitRelExpr((Ast.RelExpr) ctx.getFirst()));
            for(int i=1;i<ctx.size();i++){
                instructionList.add((TamInstruction.Instruction) visitRelExpr((Ast.RelExpr) ctx.get(i)));
                instructionList.add(new TamInstruction.Instruction(TamOpcode.CALL, PB,0,TamPrimitive.and.value+1));
            }
            return instructionList;
        }
        else
            return visitRelExpr((Ast.RelExpr) ctx.getFirst());
    }

    private int getRelOp(Ast.Atom.relAtom at){
        switch(at.relOp()){
            case "<", ".lt." -> {
                return TamPrimitive.lt.value + 1;
            }
            case ">", ".gt." ->{
                return TamPrimitive.gt.value + 1;
            }
            case "<=", ".le." ->{
                return TamPrimitive.le.value + 1;
            }
            case ">=", ".ge." ->{
                return TamPrimitive.ge.value + 1;
            }
            case "/=", ".neq." -> {
                return TamPrimitive.ne.value + 1;
            }
            case "==", ".eq." -> {
                return TamPrimitive.eq.value + 1;
            }
            default -> throw new IllegalStateException("Unknown logical operator");
        }
    }

    @Override
    public TamInstruction visitRelExpr(Ast.RelExpr ctx) {
        return visitConcatExpr((Ast.ConcatExpr) ctx.getFirst());
    }

    @Override
    public TamInstruction visitConcatExpr(Ast.ConcatExpr ctx) {
        return visitAddSubExpr((Ast.AddSubExpr) ctx.getFirst());
    }

    @Override
    public TamInstruction visitAddSubExpr(Ast.AddSubExpr ctx) {
        return visitMulDivExpr((Ast.MulDivExpr) ctx.getFirst());
    }

    @Override
    public TamInstruction visitMulDivExpr(Ast.MulDivExpr ctx) {
        return visitPowExpr((Ast.PowExpr) ctx.getFirst());
    }

    @Override
    public TamInstruction visitPowExpr(Ast.PowExpr ctx) {
        return visitFieldAccExpr((Ast.FieldAccessExpr) ctx.getFirst());
    }

    @Override
    public TamInstruction visitFieldAccExpr(Ast.FieldAccessExpr ctx) {
        switch(ctx.getFirst()){
            case Ast.IntSExpr intSExpr ->{
                return visitIntSExpr(intSExpr);
            }
            case Ast.Atom.boolAtom boolAtom -> {
                return visitLogicSExpr(boolAtom);
            }
            case Ast.Atom.hexNumAtom hexNumAtom -> {
                return visitHexSExpr(hexNumAtom);
            }
            case Ast.Atom.binNumAtom binNumAtom-> {
                return visitBinSExpr(binNumAtom);
            }
            case Ast.Atom.octNumAtom octNumAtom -> {
                return visitOctAtom(octNumAtom);
            }
            case Ast.ExprSExpr exprSExpr -> {
                return visitExprSExpr(exprSExpr);
            }
            default -> throw new IllegalStateException("Unsupported expression" + ctx.getFirst());
        }
    }

    @Override
    public TamInstruction visitLogicSExpr(Ast.Atom.boolAtom ctx) {
        int v = (ctx.bool())? 1:0;
        return new TamInstruction.Instruction(TamOpcode.LOADL, CB,0,v);
    }

    @Override
    public TamInstruction visitRealSExpr(Ast.Atom.realAtom ctx) {
        return null;
    }

    @Override
    public TamInstruction visitHexSExpr(Ast.Atom.hexNumAtom ctx) {
        return new TamInstruction.Instruction(TamOpcode.LOADL, CB,0, ctx.hex());
    }

    @Override
    public TamInstruction visitBinSExpr(Ast.Atom.binNumAtom ctx) {
        return new TamInstruction.Instruction(TamOpcode.LOADL, CB,0,ctx.bin());
    }

    @Override
    public TamInstruction visitCharSeqSExpr(Ast.Atom.charLiteralAtom ctx) {
        return null;
    }

    @Override
    public TamInstruction visitIntSExpr(Ast.IntSExpr ctx) {
        return visitIntnum((Ast.IntNum) ctx.getFirst());
    }

    @Override
    public TamInstruction visitExprSExpr(Ast.ExprSExpr ctx) {
        return visitExpr((Ast.Expr) ctx.getFirst());
    }

    @Override
    public TamInstruction visitArrSExpr(Ast.ArrayDef ctx) {
        return null;
    }

    @Override
    public TamInstruction visitFuncSExpr(Ast.FuncSExpr ctx) {
        return null;
    }

    @Override
    public TamInstruction visitNameSExpr(Ast.NameSExpr ctx) {
        return null;
    }

    @Override
    public TamInstruction visitRelativeOp(Ast.Atom.relAtom ctx) {
        return null;
    }

    @Override
    public TamInstruction visitTypeAtom(Ast.Atom.typeAtom ctx) {
        return null;
    }

    @Override
    public TamInstruction visitLogicalOp(Ast.Atom.logicOpAtom ctx) {
        return null;
    }

    @Override
    public TamInstruction visitMulDivOp(Ast.Atom.mulDivAtom ctx) {
//        int res;
//        if(ctx.op().equals("*")){
//            res = TamPrimitive.mult.value+1;
//        }
//        else if(ctx.op().equals("/")){
//            res = TamPrimitive.div.value+1;
//        }
        return null;
    }

    @Override
    public TamInstruction visitStar(Ast.Atom.starAtom ctx) {
        return null;
    }

    @Override
    public TamInstruction visitAddSubOp(Ast.Atom.addSubAtom ctx) {
        return null;
    }

    @Override
    public TamInstruction visitIntnum(Ast.IntNum ctx) {
        return visitNumAtom((Ast.Atom.numAtom) ctx.getLast());
    }

    @Override
    public TamInstruction visitNumAtom(Ast.Atom.numAtom ctx) {
        return new TamInstruction.Instruction(TamOpcode.LOADL, CB,0,ctx.i());
    }

    @Override
    public TamInstruction visitNameAtom(Ast.Atom.nameAtom ctx) {
        return null;
    }

    @Override
    public TamInstruction visitNodeAtom(Ast.Atom.nodeAtom ctx) {
        return null;
    }

    @Override
    public TamInstruction visitOctAtom(Ast.Atom.octNumAtom ctx) {
        return new TamInstruction.Instruction(TamOpcode.LOADL, CB,0,ctx.oct());
    }

    @Override
    public TamInstruction visit(Ast ast) {
        return null;
    }
}
