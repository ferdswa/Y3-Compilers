package uk.ac.nott.cs.comp3012.coursework.tam;

import java.util.*;

import uk.ac.nott.cs.comp3012.coursework.AstVisitor;
import uk.ac.nott.cs.comp3012.coursework.ast.Ast;

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
        for(int i = 1; i<ctx.size()-1;i++){//Declarations currently unsupported
            switch(ctx.get(i)){
                case Ast.Write writeNode-> {
                    instructionList.addAll((TamInstruction.InstructionList)visitWrite(writeNode));
                }
                case Ast.IfStatement ifStatement-> {
                    instructionList.addAll((TamInstruction.InstructionList)visitIfStmt(ifStatement));
                }
                case Ast.IfBlock ifBlock -> {
                    instructionList.addAll((TamInstruction.InstructionList)visitIfBlock(ifBlock));
                }
                case Ast.IfElseBlock ifElseBlock -> {
                    instructionList.addAll((TamInstruction.InstructionList) visitIfElse(ifElseBlock));
                }
                case Ast.Atom.exitAtom exitAtom -> {
                    instructionList.add((TamInstruction.Instruction)visitExitStmt(exitAtom));
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
        for(int i=1; i<ctx.size();i++) {
            switch (ctx.get(i)) {
                case Ast.Write writeNode -> {
                    tInstrs.addAll((TamInstruction.InstructionList) visitWrite(writeNode));
                }
                case Ast.IfStatement ifStatement -> {
                    tInstrs.addAll((TamInstruction.InstructionList) visitIfStmt(ifStatement));
                }
                case Ast.IfBlock ifBlock -> {
                    tInstrs.addAll((TamInstruction.InstructionList)visitIfBlock(ifBlock));
                }
                case Ast.IfElseBlock ifElseBlock -> {
                    tInstrs.addAll((TamInstruction.InstructionList) visitIfElse(ifElseBlock));
                }
                case Ast.Atom.exitAtom exitAtom -> {
                    tInstrs.add((TamInstruction.Instruction)visitExitStmt(exitAtom));
                }
                default -> throw new IllegalStateException("Statement unsupported" + ctx.get(i));
            }
        }
        instructionList.add(new TamInstruction.Instruction(TamOpcode.JUMPIF, CP,0, tInstrs.size()));
        instructionList.addAll(tInstrs);
        return instructionList;
    }

    @Override
    public TamInstruction visitIfElse(Ast.IfElseBlock ctx) {
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
        for(int i=1; i<ctx.size()-1;i++) {//Final subAST is always the ElseStmt group
            switch (ctx.get(i)) {
                case Ast.Write writeNode -> {
                    tInstrs.addAll((TamInstruction.InstructionList) visitWrite(writeNode));
                }
                case Ast.IfStatement ifStatement -> {
                    tInstrs.addAll((TamInstruction.InstructionList) visitIfStmt(ifStatement));
                }
                case Ast.IfBlock ifBlock -> {
                    tInstrs.addAll((TamInstruction.InstructionList)visitIfBlock(ifBlock));
                }
                case Ast.IfElseBlock ifElseBlock -> {
                    tInstrs.addAll((TamInstruction.InstructionList) visitIfElse(ifElseBlock));
                }
                case Ast.Atom.exitAtom exitAtom -> {
                    tInstrs.add((TamInstruction.Instruction)visitExitStmt(exitAtom));
                }
                default -> throw new IllegalStateException("Statement unsupported" + ctx.get(i).getClass().getSimpleName());
            }
        }
        TamInstruction.InstructionList fInstrs = new TamInstruction.InstructionList();
        TamInstruction elseStmt = visitElseStmt((Ast.ElseStmt) ctx.getLast());
        switch(elseStmt){
            case TamInstruction.InstructionList lElseStmt ->{
                System.out.println("Else recog");
                fInstrs.addAll(lElseStmt);
            }
            case TamInstruction.Instruction sElseStmt ->{
                System.out.println("Else recog");
                fInstrs.add(sElseStmt);
            }
            default -> throw new IllegalStateException("Unexpected value: " + elseStmt);
        }
        instructionList.add(new TamInstruction.Instruction(TamOpcode.JUMPIF, CP,0, tInstrs.size()));
        instructionList.addAll(tInstrs);
        instructionList.addAll(fInstrs);
        return instructionList;
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
            case Ast.IfBlock ifBlock -> {
                tInstrs.addAll((TamInstruction.InstructionList)visitIfBlock(ifBlock));
            }
            case Ast.IfElseBlock ifElseBlock -> {
                tInstrs.addAll((TamInstruction.InstructionList) visitIfElse(ifElseBlock));
            }
            case Ast.Atom.exitAtom exitAtom -> {
                tInstrs.add((TamInstruction.Instruction)visitExitStmt(exitAtom));
            }
            default -> throw new IllegalStateException("Statement unsupported" + ctx.getLast());
        }
        instructionList.add(new TamInstruction.Instruction(TamOpcode.JUMPIF, CP,0, tInstrs.size()));
        instructionList.addAll(tInstrs);
        return instructionList;
    }

    @Override
    public TamInstruction visitElseStmt(Ast.ElseStmt ctx) {
        TamInstruction.InstructionList elseInstrs = new TamInstruction.InstructionList();
        for(int i=0;i<ctx.size();i++){
            switch(ctx.get(i)){
                case Ast.Write writeNode-> {
                    elseInstrs.addAll((TamInstruction.InstructionList)visitWrite(writeNode));
                }
                case Ast.IfStatement ifStatement->{
                    elseInstrs.addAll((TamInstruction.InstructionList)visitIfStmt(ifStatement));
                }
                case Ast.IfBlock ifBlock -> {
                    elseInstrs.addAll((TamInstruction.InstructionList)visitIfBlock(ifBlock));
                }
                case Ast.IfElseBlock ifElseBlock -> {
                    elseInstrs.addAll((TamInstruction.InstructionList) visitIfElse(ifElseBlock));
                }
                case Ast.Atom.exitAtom exitAtom -> {
                    elseInstrs.add((TamInstruction.Instruction)visitExitStmt(exitAtom));
                }
                default -> throw new IllegalStateException("Statement unsupported" + ctx.getLast());
            }
        }
        return elseInstrs;
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
            if(cExpr instanceof TamInstruction.InstructionList){
                writeList.addAll((TamInstruction.InstructionList)cExpr);
            }
            else{
                writeList.add((TamInstruction.Instruction) cExpr);
            }
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
            //Get ops further down list
            TamInstruction instr = visitRelExpr((Ast.RelExpr) ctx.getFirst());
            if(instr instanceof TamInstruction.Instruction){
                instructionList.add((TamInstruction.Instruction) instr);
            }
            else{
                instructionList.addAll((TamInstruction.InstructionList) instr);
            }
            for(int i=1;i<ctx.size();i++){
                TamInstruction instr1 = visitRelExpr((Ast.RelExpr) ctx.get(i));
                if(instr1 instanceof TamInstruction.Instruction){
                    instructionList.add((TamInstruction.Instruction) instr1);
                }
                else{
                    instructionList.addAll((TamInstruction.InstructionList) instr1);
                }
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
        TamInstruction.InstructionList instructionList = new TamInstruction.InstructionList();
        if(ctx.size()>1){
            //Get ops further down list
            List<Ast.ConcatExpr> ccExprs = new ArrayList<>();
            List<Ast.Atom.relAtom> ops = new ArrayList<>();
            for (Ast ast : ctx) {
                if (ast instanceof Ast.ConcatExpr) {
                    ccExprs.add((Ast.ConcatExpr) ast);
                } else if (ast instanceof Ast.Atom.relAtom) {
                    ops.add((Ast.Atom.relAtom) ast);
                } else {
                    throw new IllegalStateException("Unknown logical operator");
                }
            }
            TamInstruction instr = visitConcatExpr(ccExprs.getFirst());
            if(instr instanceof TamInstruction.Instruction){
                instructionList.add((TamInstruction.Instruction) instr);
            }
            else{
                instructionList.addAll((TamInstruction.InstructionList) instr);
            }

            for(int i=1;i<ccExprs.size();i++){
                TamInstruction instr1 = visitConcatExpr(ccExprs.get(i));
                if(instr1 instanceof TamInstruction.Instruction){
                    instructionList.add((TamInstruction.Instruction) instr1);
                }
                else{
                    instructionList.addAll((TamInstruction.InstructionList) instr1);
                }
                Ast.Atom.relAtom rl = ops.get(i-1);
                int relOpOffset = getRelOp(rl);
                if(relOpOffset == TamPrimitive.ne.value+1 ||  relOpOffset == TamPrimitive.eq.value+1){
                    instructionList.add(new TamInstruction.Instruction(TamOpcode.LOADL, CB,0,1));
                }
                instructionList.add(new TamInstruction.Instruction(TamOpcode.CALL, PB,0,relOpOffset));
            }
            return instructionList;
        }
        else
            return visitConcatExpr((Ast.ConcatExpr) ctx.getFirst());
    }

    @Override
    public TamInstruction visitConcatExpr(Ast.ConcatExpr ctx) {
        return visitAddSubExpr((Ast.AddSubExpr) ctx.getFirst());
    }

    @Override
    public TamInstruction visitAddSubExpr(Ast.AddSubExpr ctx) {
        TamInstruction.InstructionList instructionList = new TamInstruction.InstructionList();
        boolean negate = ctx.getFirst() instanceof Ast.Atom.addSubAtom && ((Ast.Atom.addSubAtom) ctx.getFirst()).op().equals("-");
        if(ctx.size()>1){//More than one
            //Get ops further down list
            List<Ast.MulDivExpr> mdExprs = new ArrayList<>();
            List<Ast.Atom.addSubAtom> ops = new ArrayList<>();
            for (Ast ast : ctx) {
                if (ast instanceof Ast.MulDivExpr) {
                    mdExprs.add((Ast.MulDivExpr) ast);
                } else if (ast instanceof Ast.Atom.addSubAtom) {
                    ops.add((Ast.Atom.addSubAtom) ast);
                } else {
                    throw new IllegalStateException("Unknown logical operator");
                }
            }
            TamInstruction instr = visitMulDivExpr(mdExprs.getFirst());
            if(instr instanceof TamInstruction.Instruction){
                instructionList.add((TamInstruction.Instruction) instr);
            }
            else{
                instructionList.addAll((TamInstruction.InstructionList) instr);
            }
            for(int i=1;i<mdExprs.size();i++){
                TamInstruction instr1 = visitMulDivExpr(mdExprs.get(i));
                if(instr1 instanceof TamInstruction.Instruction){
                    instructionList.add((TamInstruction.Instruction) instr1);
                }
                else{
                    instructionList.addAll((TamInstruction.InstructionList) instr1);
                }
                Ast.Atom.addSubAtom as;
                if(negate){
                    as = ops.get(i);//First op already consumed by negation/the random plus sign
                }
                else{
                    as = ops.get(i-1);
                }
                int asOffset = getAsOffset(as);
                instructionList.add(new TamInstruction.Instruction(TamOpcode.CALL, PB,0,asOffset));
            }
            return instructionList;
        }
        else {
            return visitMulDivExpr((Ast.MulDivExpr) ctx.getLast());
        }
    }

    private int getAsOffset(Ast.Atom.addSubAtom as) {
        switch(as.op()){
            case "+"-> {
                //expValue += expValue2;
                return TamPrimitive.add.value + 1;
            }
            case "-"->{
                System.out.println(expValue+" "+ expValue2);
                expValue -= (2*expValue2);
                return TamPrimitive.sub.value + 1;
            }
            default -> throw new IllegalStateException("Unknown logical operator");
        }
    }

    @Override
    public TamInstruction visitMulDivExpr(Ast.MulDivExpr ctx) {
        TamInstruction.InstructionList instructionList = new TamInstruction.InstructionList();
        if(ctx.size()>1){
            //Get ops further down list
            List<Ast.PowExpr> powExprs = new ArrayList<>();
            List<Ast.Atom.mulDivAtom> ops = new ArrayList<>();
            for (Ast ast : ctx) {
                if (ast instanceof Ast.PowExpr) {
                    powExprs.add((Ast.PowExpr) ast);
                } else if (ast instanceof Ast.Atom.mulDivAtom) {
                    ops.add((Ast.Atom.mulDivAtom) ast);
                } else {
                    throw new IllegalStateException("Unknown logical operator");
                }
            }
            TamInstruction instr = visitPowExpr(powExprs.getFirst());
            if(instr instanceof TamInstruction.Instruction){
                instructionList.add((TamInstruction.Instruction) instr);
            }
            else{
                instructionList.addAll((TamInstruction.InstructionList) instr);
            }

            for(int i=1;i<powExprs.size();i++){
                TamInstruction instr1 = visitPowExpr(powExprs.get(i));
                if(instr1 instanceof TamInstruction.Instruction){
                    instructionList.add((TamInstruction.Instruction) instr1);
                }
                else{
                    instructionList.addAll((TamInstruction.InstructionList) instr1);
                }
                Ast.Atom.mulDivAtom md = ops.get(i-1);
                int mulDivOpOffset = getMdOffset(md);
                instructionList.add(new TamInstruction.Instruction(TamOpcode.CALL, PB,0,mulDivOpOffset));
            }
            return instructionList;
        }
        else
            return visitPowExpr((Ast.PowExpr) ctx.getFirst());
    }

    private int getMdOffset(Ast.Atom.mulDivAtom as) {
        switch(as.op()){
            case "*"-> {
                expValue *= expValue2;
                return TamPrimitive.mult.value + 1;
            }
            case "/"->{
                expValue /= expValue2;
                return TamPrimitive.div.value + 1;
            }
            default -> throw new IllegalStateException("Unknown logical operator");
        }
    }

    int expValue = 0;
    int expValue2 = 0;
    @Override
    public TamInstruction visitPowExpr(Ast.PowExpr ctx) {
        TamInstruction.InstructionList instructionList = new TamInstruction.InstructionList();
        if (ctx.size() > 1) {
            //Get base
            TamInstruction instr = visitFieldAccExpr((Ast.FieldAccessExpr) ctx.getFirst());
            if (instr instanceof TamInstruction.Instruction) {
                instructionList.add((TamInstruction.Instruction) instr);
            } else {
                instructionList.addAll((TamInstruction.InstructionList) instr);
            }
            for (int i = 1; i < ctx.size(); i++) {
                expValue = 0;
                //Get the value of the next expression in the list
                visitFieldAccExpr((Ast.FieldAccessExpr) ctx.get(i));
                System.out.println(expValue);
                if (expValue > 0) {
                    for (int j = 1; j < expValue; j++) {
                        //Push another first value
                        if (instr instanceof TamInstruction.Instruction) {
                            instructionList.add((TamInstruction.Instruction) instr);
                        } else {
                            instructionList.addAll((TamInstruction.InstructionList) instr);
                        }
                        //Multiply first value by itself
                        instructionList.add(new TamInstruction.Instruction(TamOpcode.CALL, PB, 0, TamPrimitive.mult.value + 1));
                    }
                } else if (expValue < 0) {//Negative exponentials. They don't work.
                    for (int j = -1; j > expValue; j--) {
                        //Push another first value
                        if (instr instanceof TamInstruction.Instruction) {
                            instructionList.add((TamInstruction.Instruction) instr);
                        } else {
                            instructionList.addAll((TamInstruction.InstructionList) instr);
                        }
                        //Multiply first value by itself
                        instructionList.add(new TamInstruction.Instruction(TamOpcode.CALL, PB, 0, TamPrimitive.div.value + 1));
                    }
                }
                else{
                    instructionList.add(new TamInstruction.Instruction(TamOpcode.LOADL, CB,0,1));
                    instructionList.add(new TamInstruction.Instruction(TamOpcode.CALL, PB,0,TamPrimitive.mult.value+1));
                }
            }
            return instructionList;

//            for(int i=ctx.size()-1;i>=1;i--){
//                if(iexponent == Integer.MAX_VALUE ){
//                    expValue = 0;
//                    visitFieldAccExpr((Ast.FieldAccessExpr) ctx.get(i));
//                    iexponent = expValue;
//                    expValue = 0;
//                    visitFieldAccExpr((Ast.FieldAccessExpr) ctx.get(i-1));
//                    ibase = expValue;
//                }
//                else{
//                    expValue = 0;
//                    visitFieldAccExpr((Ast.FieldAccessExpr) ctx.get(i-1));
//                    ibase = ans;
//                }
//
//                for(int j=0;j<iexponent;j++){
//                    ans *= ibase;
//                }
//                iexponent = ans;
//                System.out.println(ans);
//            }//One more iteration
//            return new TamInstruction.Instruction(TamOpcode.LOADL, CB,0,iexponent);
        }
        else
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
        expValue2 = ctx.hex();
        return new TamInstruction.Instruction(TamOpcode.LOADL, CB,0, ctx.hex());
    }

    @Override
    public TamInstruction visitBinSExpr(Ast.Atom.binNumAtom ctx) {
        expValue2 = ctx.bin();
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
    public TamInstruction visitIntnum(Ast.IntNum ctx) {//TODO: Intnums can also be inverted!
        if(ctx.size()>1){//Negation
            TamInstruction.InstructionList instructions = new TamInstruction.InstructionList();
            instructions.add((TamInstruction.Instruction) visitNumAtom((Ast.Atom.numAtom) ctx.getLast()));
            if(((Ast.Atom.addSubAtom)ctx.getFirst()).op().equals("-")){
                instructions.add(new TamInstruction.Instruction(TamOpcode.CALL, PB,0,TamPrimitive.neg.value+1));
                expValue -= ((Ast.Atom.numAtom) ctx.getLast()).i();
                expValue2 = -expValue2;
            }
            else{
                expValue += ((Ast.Atom.numAtom) ctx.getLast()).i();
            }
            return instructions;
        }
        else{
            expValue += ((Ast.Atom.numAtom) ctx.getLast()).i();
            return visitNumAtom((Ast.Atom.numAtom) ctx.getLast());
        }
    }

    boolean first = false;
    @Override
    public TamInstruction visitNumAtom(Ast.Atom.numAtom ctx) {
        expValue2 = ctx.i();
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
        expValue2 = ctx.oct();
        return new TamInstruction.Instruction(TamOpcode.LOADL, CB,0,ctx.oct());
    }

    @Override
    public TamInstruction visitExitStmt(Ast.Atom.exitAtom ctx) {
        return new TamInstruction.Instruction(TamOpcode.HALT, CB, 0,0);
    }

    @Override
    public TamInstruction visit(Ast ast) {
        return null;
    }
}
