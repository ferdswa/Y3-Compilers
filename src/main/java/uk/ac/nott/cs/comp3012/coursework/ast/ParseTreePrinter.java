package uk.ac.nott.cs.comp3012.coursework.ast;


import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import uk.ac.nott.cs.comp3012.coursework.NottscriptBaseListener;
import uk.ac.nott.cs.comp3012.coursework.NottscriptParser;

public class ParseTreePrinter extends NottscriptBaseListener {
    int indent = 0;
    private void printIndent() {
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
    }
    @Override
    public void enterProgram(NottscriptParser.ProgramContext ctx){
        printIndent();
        System.out.println("Begin Nottscript file");
        indent += 2;
    }
    @Override
    public void exitProgram(NottscriptParser.ProgramContext ctx){
        printIndent();
        System.out.println("End Nottscript file");
        indent -= 2;
    }
    @Override
    public void enterProgramBlock(NottscriptParser.ProgramBlockContext ctx) {
        printIndent();
        System.out.println("Begin program block");
        indent += 2;
    }

    @Override
    public void exitProgramBlock(NottscriptParser.ProgramBlockContext ctx) {
        printIndent();
        System.out.println("End program block");
        indent -= 2;
    }
    @Override public void enterVoidFuncBlock(NottscriptParser.VoidFuncBlockContext ctx) {
        printIndent();
        System.out.println("Begin void function block");
        indent += 2;
    }
    
    @Override public void exitVoidFuncBlock(NottscriptParser.VoidFuncBlockContext ctx) {
        printIndent();
        System.out.println("End void function block");
        indent -= 2;
    }
    
    @Override public void enterReturnFuncBlock(NottscriptParser.ReturnFuncBlockContext ctx) {
        printIndent();
        System.out.println("Begin return function block");
        indent += 2;
    }
    
    @Override public void exitReturnFuncBlock(NottscriptParser.ReturnFuncBlockContext ctx) {
        printIndent();
        System.out.println("End return function block");
        indent -= 2;
    }
    
    @Override public void enterSubrtBlock(NottscriptParser.SubrtBlockContext ctx) {
        printIndent();
        System.out.println("Begin subrt block");
        indent += 2;
    }
    
    @Override public void exitSubrtBlock(NottscriptParser.SubrtBlockContext ctx) {
        printIndent();
        System.out.println("End subrt block");
        indent -= 2;
    }
    @Override public void enterCustomTypeDeclBlock(NottscriptParser.CustomTypeDeclBlockContext ctx) {
        printIndent();
        System.out.println("Begin custom type declaration block");
        indent += 2;
    }
    @Override public void exitCustomTypeDeclBlock(NottscriptParser.CustomTypeDeclBlockContext ctx) {
        printIndent();
        System.out.println("End custom type declaration block");
        indent -= 2;
    }
    @Override
    public void enterDeclaratorParamList(NottscriptParser.DeclaratorParamListContext ctx) {
        printIndent();
        System.out.println("Begin delarator parameter list");
        indent += 2;
    }

    @Override
    public void exitDeclaratorParamList(NottscriptParser.DeclaratorParamListContext ctx) {
        printIndent();
        System.out.println("End delarator parameter list");
        indent -= 2;
    }
    @Override public void enterDeclareVar(NottscriptParser.DeclareVarContext ctx) {
        printIndent();
        System.out.println("Begin declare var");
        indent += 2;
    }
    
    @Override public void exitDeclareVar(NottscriptParser.DeclareVarContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterDeclPtr(NottscriptParser.DeclPtrContext ctx) {
        printIndent();
        System.out.println("Begin declare pointer");
        indent += 2;
    }
    
    @Override public void exitDeclPtr(NottscriptParser.DeclPtrContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterDeclArray(NottscriptParser.DeclArrayContext ctx) {
        printIndent();
        System.out.println("Begin declare array");
        indent += 2;
    }
    
    @Override public void exitDeclArray(NottscriptParser.DeclArrayContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterDeclPtrArray(NottscriptParser.DeclPtrArrayContext ctx) {
        printIndent();
        System.out.println("Begin declare array pointer");
        indent += 2;
    }
    
    @Override public void exitDeclPtrArray(NottscriptParser.DeclPtrArrayContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterBaseAssign(NottscriptParser.BaseAssignContext ctx) {
        printIndent();
        System.out.println("Begin base assign");
        indent += 2;
    }
    
    @Override public void exitBaseAssign(NottscriptParser.BaseAssignContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterArrayAssign(NottscriptParser.ArrayAssignContext ctx) {
        printIndent();
        System.out.println("Begin array assign");
        indent += 2;
    }
    
    @Override public void exitArrayAssign(NottscriptParser.ArrayAssignContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterCtAssign(NottscriptParser.CtAssignContext ctx) {
        printIndent();
        System.out.println("Begin custom type assign");
        indent += 2;
    }
    
    @Override public void exitCtAssign(NottscriptParser.CtAssignContext ctx) {
        printIndent();
        System.out.println("End custom type assign");
        indent -= 2;
    }
    
    @Override public void enterCtArrayAssign(NottscriptParser.CtArrayAssignContext ctx) {
        printIndent();
        System.out.println("Begin custom type array assign");
        indent += 2;
    }
    
    @Override public void exitCtArrayAssign(NottscriptParser.CtArrayAssignContext ctx) {
        printIndent();
        System.out.println("End custom type array assign");
        indent -= 2;
    }
    
//    @Override public void enterCustomTypeDecl(NottscriptParser.CustomTypeDeclContext ctx) {
//        printIndent();
//        System.out.println("Begin custom type decl");
//        indent += 2;
//    }
//
//    @Override public void exitCustomTypeDecl(NottscriptParser.CustomTypeDeclContext ctx) {
//        printIndent();
//        System.out.println("End custom type decl");
//        indent -= 2;
//    }
    
    @Override public void enterCall(NottscriptParser.CallContext ctx) {
        printIndent();
        System.out.println("Begin call");
        indent += 2;
    }
    
    @Override public void exitCall(NottscriptParser.CallContext ctx) {
        printIndent();
        System.out.println("End call");
        indent -= 2;
    }
    
    @Override public void enterIfBlock(NottscriptParser.IfBlockContext ctx) {
        printIndent();
        System.out.println("Begin if block");
        indent += 2;
    }
    
    @Override public void exitIfBlock(NottscriptParser.IfBlockContext ctx) {
        printIndent();
        System.out.println("End if block");
        indent -= 2;
    }
    
    @Override public void enterIfElse(NottscriptParser.IfElseContext ctx) {
        printIndent();
        System.out.println("Begin if else");
        indent += 2;
    }
    
    @Override public void exitIfElse(NottscriptParser.IfElseContext ctx) {
        printIndent();
        System.out.println("End if else");
        indent -= 2;
    }

    @Override public void enterElseStmt(NottscriptParser.ElseStmtContext ctx) {
        printIndent();
        System.out.println("Begin else");
        indent += 2;
    }
    @Override public void exitElseStmt(NottscriptParser.ElseStmtContext ctx) {
        printIndent();
        System.out.println("End else");
        indent -= 2;
    }
    
    @Override public void enterIfStmt(NottscriptParser.IfStmtContext ctx) {
        printIndent();
        System.out.println("Begin if stmt");
        indent += 2;
    }
    
    @Override public void exitIfStmt(NottscriptParser.IfStmtContext ctx) {
        printIndent();
        System.out.println("End if stmt");
        indent -= 2;
    }
    
    @Override public void enterDoIncrN1(NottscriptParser.DoIncrN1Context ctx) {
        printIndent();
        System.out.println("Begin do with custom increment");
        indent += 2;
    }
    
    @Override public void exitDoIncrN1(NottscriptParser.DoIncrN1Context ctx) {
        printIndent();
        System.out.println("End do with custom increment");
        indent -= 2;
    }
    
    @Override public void enterDoIncr1(NottscriptParser.DoIncr1Context ctx) {
        printIndent();
        System.out.println("Begin do with default increment");
        indent += 2;
    }
    
    @Override public void exitDoIncr1(NottscriptParser.DoIncr1Context ctx) {
        printIndent();
        System.out.println("End do with default increment");
        indent -= 2;
    }
    
    @Override public void enterDoWhile(NottscriptParser.DoWhileContext ctx) {
        printIndent();
        System.out.println("Begin do while");
        indent += 2;
    }
    
    @Override public void exitDoWhile(NottscriptParser.DoWhileContext ctx) {
        printIndent();
        System.out.println("End do while");
        indent -= 2;
    }

    @Override public void enterDoParam(NottscriptParser.DoParamContext ctx) {
        printIndent();
        System.out.println("Begin do param");
        indent += 2;
    }

    @Override public void exitDoParam(NottscriptParser.DoParamContext ctx) {
        printIndent();
        System.out.println("End do param");
        indent -= 2;
    }
    
    @Override public void enterRead(NottscriptParser.ReadContext ctx) {
        printIndent();
        System.out.println("Begin read");
        indent += 2;
    }
    
    @Override public void exitRead(NottscriptParser.ReadContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterWrite(NottscriptParser.WriteContext ctx) {
        printIndent();
        System.out.println("Begin write");
        indent += 2;
    }
    
    @Override public void exitWrite(NottscriptParser.WriteContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterAllocPtr(NottscriptParser.AllocPtrContext ctx) {
        printIndent();
        System.out.println("Begin alloc pointer");
        indent += 2;
    }
    
    @Override public void exitAllocPtr(NottscriptParser.AllocPtrContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterAllocPtrArray(NottscriptParser.AllocPtrArrayContext ctx) {
        printIndent();
        System.out.println("Begin alloc array pointer");
        indent += 2;
    }
    
    @Override public void exitAllocPtrArray(NottscriptParser.AllocPtrArrayContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterDeallocPtr(NottscriptParser.DeallocPtrContext ctx) {
        printIndent();
        System.out.println("Begin dealloc pointer");
        indent += 2;
    }
    
    @Override public void exitDeallocPtr(NottscriptParser.DeallocPtrContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterFuncCall(NottscriptParser.FuncCallContext ctx) {
        printIndent();
        System.out.println("Begin func call");
        indent += 2;
    }
    
    @Override public void exitFuncCall(NottscriptParser.FuncCallContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterArray(NottscriptParser.ArrayContext ctx) {
        printIndent();
        System.out.println("Begin array");
        indent += 2;
    }
    
    @Override public void exitArray(NottscriptParser.ArrayContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterParamList(NottscriptParser.ParamListContext ctx) {
        printIndent();
        System.out.println("Begin parameter list");
        indent += 2;
    }
    
    @Override public void exitParamList(NottscriptParser.ParamListContext ctx) {
        indent -= 2;
    }
    @Override public void enterFieldAccExpr(NottscriptParser.FieldAccExprContext ctx) {
        printIndent();
        System.out.println("Begin field access expression");
        indent += 2;
    }
    @Override public void exitFieldAccExpr(NottscriptParser.FieldAccExprContext ctx) { indent -=2;}
    @Override public void enterPowExpr(NottscriptParser.PowExprContext ctx) {
        printIndent();
        System.out.println("Begin power expression");
        indent += 2;
    }
    
    @Override public void exitPowExpr(NottscriptParser.PowExprContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterMulDivExpr(NottscriptParser.MulDivExprContext ctx) {
        printIndent();
        System.out.println("Begin mul div expression");
        indent += 2;
    }
    
    @Override public void exitMulDivExpr(NottscriptParser.MulDivExprContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterAddSubExpr(NottscriptParser.AddSubExprContext ctx) {
        printIndent();
        System.out.println("Begin add sub expression");
        indent += 2;
    }
    
    @Override public void exitAddSubExpr(NottscriptParser.AddSubExprContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterConcatExpr(NottscriptParser.ConcatExprContext ctx) {
        printIndent();
        System.out.println("Begin concat expression");
        indent += 2;
    }
    
    @Override public void exitConcatExpr(NottscriptParser.ConcatExprContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterRelExpr(NottscriptParser.RelExprContext ctx) {
        printIndent();
        System.out.println("Begin rel expression");
        indent += 2;
    }
    
    @Override public void exitRelExpr(NottscriptParser.RelExprContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterLogExpr(NottscriptParser.LogExprContext ctx) {
        printIndent();
        System.out.println("Begin log expression");
        indent += 2;
    }
    
    @Override public void exitLogExpr(NottscriptParser.LogExprContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterLogicSExpr(NottscriptParser.LogicSExprContext ctx) {
        printIndent();
        System.out.println("Logic simple expression: "+ctx.getText());
        indent += 2;
    }

    @Override
    public void enterLogicalOp(NottscriptParser.LogicalOpContext ctx) {
        printIndent();
        System.out.println("Logical op: "+ctx.getText());
        indent += 2;
    }

    @Override
    public void enterAddSubOp(NottscriptParser.AddSubOpContext ctx) {
        printIndent();
        System.out.println("AddSub op: "+ctx.getText());
        indent += 2;
    }

    @Override
    public void enterMulDivOp(NottscriptParser.MulDivOpContext ctx) {
        printIndent();
        System.out.println("MulDiv op: "+ctx.getText());
        indent += 2;
    }


    @Override public void exitLogicSExpr(NottscriptParser.LogicSExprContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterRealSExpr(NottscriptParser.RealSExprContext ctx) {
        printIndent();
        System.out.println("real simple expression: "+ctx.getText());
        indent += 2;
    }

    @Override public void exitRealSExpr(NottscriptParser.RealSExprContext ctx) {
        indent -= 2;
    }

    @Override public void enterHexSExpr(NottscriptParser.HexSExprContext ctx) {
        printIndent();
        System.out.println("Hex simple expression: "+ctx.getText());
        indent += 2;

    }

    @Override public void exitHexSExpr(NottscriptParser.HexSExprContext ctx) {
        indent -= 2;

    }
    
    @Override public void enterBinSExpr(NottscriptParser.BinSExprContext ctx) {
        printIndent();
        System.out.println("Binary simple expression: " + ctx.getText());
        indent += 2;

    }

    @Override public void exitBinSExpr(NottscriptParser.BinSExprContext ctx) {
        indent -= 2;

    }
    
    @Override public void enterCharSeqSExpr(NottscriptParser.CharSeqSExprContext ctx) {
        printIndent();
        System.out.println("Char sequence: "+ ctx.getText());
        indent += 2;
    }

    @Override public void exitCharSeqSExpr(NottscriptParser.CharSeqSExprContext ctx) {
        indent -= 2;
    }

    @Override public void enterIntSExpr(NottscriptParser.IntSExprContext ctx) {
        printIndent();
        System.out.println("Begin int simple expression");
        indent += 2;
    }
    
    @Override public void exitIntSExpr(NottscriptParser.IntSExprContext ctx) {
        indent -=2;
    }
    
    @Override public void enterExprSExpr(NottscriptParser.ExprSExprContext ctx) {
        printIndent();
        System.out.println("Begin expr simple expression");
        indent += 2;
    }
    
    @Override public void exitExprSExpr(NottscriptParser.ExprSExprContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterArrSExpr(NottscriptParser.ArrSExprContext ctx) {
        printIndent();
        System.out.println("Begin arr simple expression");
        indent += 2;
    }
    
    @Override public void exitArrSExpr(NottscriptParser.ArrSExprContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterFuncSExpr(NottscriptParser.FuncSExprContext ctx) {
        printIndent();
        System.out.println("Begin func simple expression");
        indent += 2;
    }
    
    @Override public void exitFuncSExpr(NottscriptParser.FuncSExprContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterNameSExpr(NottscriptParser.NameSExprContext ctx) {
        printIndent();
        System.out.println("Begin name simple expression");
        indent += 2;
    }

    @Override public void exitNameSExpr(NottscriptParser.NameSExprContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterExpr(NottscriptParser.ExprContext ctx) {
        printIndent();
        System.out.println("Begin expr");
        indent += 2;
    }
    
    @Override public void exitExpr(NottscriptParser.ExprContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterRelativeOp(NottscriptParser.RelativeOpContext ctx) {
        printIndent();
        System.out.println("Relative operator: "+ctx.getText());
        indent += 2;
    }
    
    @Override public void enterIntnum(NottscriptParser.IntnumContext ctx) {
        printIndent();
        System.out.printf("Integer: %s%n", ctx.getText());
        indent += 2;
    }

    @Override public void exitIntnum(NottscriptParser.IntnumContext ctx) {
        indent -= 2;
    }
    
    @Override public void enterNumAtom(NottscriptParser.NumAtomContext ctx) {
        printIndent();
        System.out.printf("Number: %s%n", ctx.USIGNINT().getText());
    }

    @Override public void enterTypeAtom(NottscriptParser.TypeAtomContext ctx) {
        printIndent();
        System.out.printf("Type: %s%n", ctx.getText());
    }

    
    @Override public void enterNameAtom(NottscriptParser.NameAtomContext ctx) {
        printIndent();
        System.out.printf("Name: %s%n", ctx.NAME().getText());
    }

    @Override public void enterInbuilt(NottscriptParser.InbuiltContext ctx) {
        printIndent();
        System.out.println("Begin Inbuilt Type Spec");
        indent += 2;
    }

    @Override public void exitInbuilt(NottscriptParser.InbuiltContext ctx) {
        indent -= 2;
    }

    @Override public void enterCustom(NottscriptParser.CustomContext ctx) {
        printIndent();
        System.out.println("Enter Custom Type Spec");
        indent += 2;
    }



    @Override public void enterEveryRule(ParserRuleContext ctx) { }

    @Override public void exitEveryRule(ParserRuleContext ctx) { }

    @Override public void visitTerminal(TerminalNode node) { }

    @Override public void visitErrorNode(ErrorNode node) { }

}
