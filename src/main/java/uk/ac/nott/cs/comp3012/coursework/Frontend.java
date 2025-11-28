package uk.ac.nott.cs.comp3012.coursework;

import org.apache.commons.collections4.ListUtils;
import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.ast.AstBuilder;
import uk.ac.nott.cs.comp3012.coursework.util.Node;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolData;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolTable;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Frontend {
    ArrayList<String> listPaths = new ArrayList<>();
    public Ast runFrontend(String input){
        AstBuilder astBuilder = new AstBuilder();
        Ast.Units ast = (Ast.Units) astBuilder.buildAst(input);
        SymbolTable symbolTable = new SymbolTable();
        SymbolTable newST = new SymbolTable(symbolTable);
        symbolTable.define("Units",new SymbolData("Units","nodeAtom"));
        for (Ast unit : ast) {//For unit
            switch(unit){
                case Ast.FuncRValueUnit funcRValueUnit -> {
                    newST.define("unit"+ast.indexOf(funcRValueUnit),new SymbolData(funcRValueUnit.getClass().getSimpleName(),"nodeAtom"));
                }
                case Ast.FuncRVoidUnit funcRVoidUnit -> {
                    newST.define("unit"+ast.indexOf(funcRVoidUnit),new SymbolData(funcRVoidUnit.getClass().getSimpleName(),"nodeAtom"));
                }
                case Ast.ProgramUnit programUnit -> {
                    newST.define("unit"+ast.indexOf(programUnit),new SymbolData(programUnit.getClass().getSimpleName(),"nodeAtom"));
                }
                case Ast.CustomTypeDefUnit customTypeDefUnit -> {
                    newST.define("unit"+ast.indexOf(customTypeDefUnit),new SymbolData(customTypeDefUnit.getClass().getSimpleName(),"nodeAtom"));
                }
                default -> throw new IllegalStateException("Unexpected value: " + unit);
            }
        }
        return null;
    }
    /**
     * Recursively walk the generated AST and find the path to all terminals, filling in non-terminals on the way. Add terminal values to the path when discovered
     * PLAN: Replace the .getFirsts with .forEach
     *
     * @param prevST The preceding (parent) SymbolTable
     * @param cNode Current AST node to make a SymbolTable
     */
    public void PrintPathToNode(Ast cNode, SymbolTable prevST){
        SymbolTable symbolTable = new SymbolTable(prevST);
        switch(cNode){
            case Ast.FuncDefineParams funcDefineParams -> {
                symbolTable.define("functionParamList",new SymbolData(funcDefineParams.getClass().getSimpleName(),"parameterList"));
                PrintPathToNode(funcDefineParams.getFirst(),symbolTable);
            }
            case Ast.DeclareVariable declareVariable -> {
                symbolTable.define("declareVariable",new SymbolData(declareVariable.getClass().getSimpleName(),"declaration"));
                PrintPathToNode(declareVariable.getFirst(),symbolTable);
            }
            case Ast.DeclarePointer declarePointer -> {
                symbolTable.define("declarePointer",new SymbolData(declarePointer.getClass().getSimpleName(),"declaration"));
                PrintPathToNode(declarePointer.getFirst(),symbolTable);
            }
            case Ast.DeclareArray declareArray -> {
                symbolTable.define("declareArray",new SymbolData(declareArray.getClass().getSimpleName(),"declaration"));
                PrintPathToNode(declareArray.getFirst(),symbolTable);
            }
            case Ast.DeclarePointerArray declPtr -> {
                symbolTable.define("declarePointerArray",new SymbolData(declPtr.getClass().getSimpleName(),"declaration"));
                PrintPathToNode(declPtr.getFirst(),symbolTable);
            }
            case Ast.InbuiltTypeSpec inbuiltTypeSpec -> {
                symbolTable.define("inbuiltType", new SymbolData(inbuiltTypeSpec.getClass().getSimpleName(),"typeSpec"));
                PrintPathToNode(inbuiltTypeSpec.getFirst(),symbolTable);
            }
            case Ast.CustomTypeSpec customTypeSpec -> {
                symbolTable.define("customType", new SymbolData(customTypeSpec.getClass().getSimpleName(),"typeSpec"));
                PrintPathToNode(customTypeSpec.getFirst(),symbolTable);}
            case Ast.NormalAssign normalAssign -> {
                symbolTable.define("assignVariable", new SymbolData(normalAssign.getClass().getSimpleName(),"assign"));
                PrintPathToNode(normalAssign.getFirst(),symbolTable); }
            case Ast.ArrayAssign arrayAssign -> {
                symbolTable.define("assignArray", new SymbolData(arrayAssign.getClass().getSimpleName(),"assign"));
                PrintPathToNode(arrayAssign.getFirst(),symbolTable);}
            case Ast.CustomTypeAssign customTypeAssign -> {
                symbolTable.define("assignCustomType", new SymbolData(customTypeAssign.getClass().getSimpleName(),"assign"));
                PrintPathToNode(customTypeAssign.getFirst(),symbolTable);}
            case Ast.CustomTypeArrayAssign customTypeArrayAssign->{
                symbolTable.define("assignCustomTypeArray", new SymbolData(customTypeArrayAssign.getClass().getSimpleName(),"assign"));
                PrintPathToNode(customTypeArrayAssign.getFirst(),symbolTable);}
            case Ast.SbrtCall sbrtCall -> {
                symbolTable.define("callSubroutine", new SymbolData(sbrtCall.getClass().getSimpleName(),"call"));
                PrintPathToNode(sbrtCall.getFirst(),symbolTable);}
            case Ast.IfBlock ifBlock -> {
                symbolTable.define("conditionalJump", new SymbolData(ifBlock.getClass().getSimpleName(),"ifNoElse"));
                PrintPathToNode(ifBlock.getFirst(),symbolTable);}
            case Ast.IfElseBlock ifElseBlock -> {
                symbolTable.define("conditionalJump", new SymbolData(ifElseBlock.getClass().getSimpleName(),"ifWithElse"));
                PrintPathToNode(ifElseBlock.getFirst(),symbolTable);}
            case Ast.IfStatement ifStatement -> {
                symbolTable.define("conditionalJump", new SymbolData(ifStatement.getClass().getSimpleName(),"ifSingleExpr"));
                PrintPathToNode(ifStatement.getFirst(),symbolTable);}
            case Ast.DoIncr1 doIncr1 -> {
                symbolTable.define("doIncrementOne",new SymbolData(doIncr1.getClass().getSimpleName(),"do"));
                PrintPathToNode(doIncr1.getFirst(),symbolTable);}
            case Ast.DoIncrNot1 doIncrNot1->{
                symbolTable.define("doIncrementNotOne",new SymbolData(doIncrNot1.getClass().getSimpleName(),"do"));
                PrintPathToNode(doIncrNot1.getFirst(),symbolTable);}
            case Ast.DoWhile doWhile -> {
                symbolTable.define("doWhile",new SymbolData(doWhile.getClass().getSimpleName(),"do"));
                PrintPathToNode(doWhile.getFirst(),symbolTable);}
            case Ast.Read read->{
                symbolTable.define("read",new SymbolData(read.getClass().getSimpleName(),"IO"));
                PrintPathToNode(read.getFirst(),symbolTable);}
            case Ast.Write write->{
                symbolTable.define("write",new SymbolData(write.getClass().getSimpleName(),"IO"));
                PrintPathToNode(write.getFirst(),symbolTable);}
            case Ast.AllocPtr allocPtr -> {
                symbolTable.define("allocatePointer",new SymbolData(allocPtr.getClass().getSimpleName(),"allocation"));
                PrintPathToNode(allocPtr.getFirst(),symbolTable);}
            case Ast.DeallocPtr deallocPtr -> {
                symbolTable.define("deAllocatePointer",new SymbolData(deallocPtr.getClass().getSimpleName(),"deallocation"));
                PrintPathToNode(deallocPtr.getFirst(),symbolTable);}
            case Ast.AllocPtrArray allocPtrArray -> {
                symbolTable.define("allocatePointerArray",new SymbolData(allocPtrArray.getClass().getSimpleName(),"allocation"));
                PrintPathToNode(allocPtrArray.getFirst(),symbolTable);}
            case Ast.FuncCall funcCall -> {
                symbolTable.define("callFunction",new SymbolData(funcCall.getClass().getSimpleName(),"call"));
                PrintPathToNode(funcCall.getFirst(),symbolTable);}
            case Ast.ElseStmt elseStmt -> {
                symbolTable.define("elseBlock",new SymbolData(elseStmt.getClass().getSimpleName(),"else"));
                PrintPathToNode(elseStmt.getFirst(),symbolTable);}
            case Ast.DoParam doParam -> {
                symbolTable.define("doParamList",new SymbolData(doParam.getClass().getSimpleName(),"parameterList"));
                PrintPathToNode(doParam.getFirst(),symbolTable);}
            case Ast.ReadParam readParam -> {
                symbolTable.define("readParamList",new SymbolData(readParam.getClass().getSimpleName(),"parameterList"));
                PrintPathToNode(readParam.getFirst(),symbolTable);}
            case Ast.ArrayIndex arrayIndex -> {
                symbolTable.define("arrayIndex",new SymbolData(arrayIndex.getClass().getSimpleName(),"arrayIndexer"));
                PrintPathToNode(arrayIndex.getFirst(),symbolTable);}
            case Ast.ArrayDef arrayDef -> {
                symbolTable.define("array",new SymbolData(arrayDef.getClass().getSimpleName(),"array"));
                PrintPathToNode(arrayDef.getFirst(),symbolTable);}
            case Ast.ParamSubList paramSubList->{
                symbolTable.define("parameterSubList",new SymbolData(paramSubList.getClass().getSimpleName(),"parameterSubList"));
                PrintPathToNode(paramSubList.getFirst(),symbolTable);}
            case Ast.ParamList paramList -> {
                symbolTable.define("parameterList",new SymbolData(paramList.getClass().getSimpleName(),"parameterList"));
                PrintPathToNode(paramList.getFirst(),symbolTable);}
            case Ast.Expr expr-> {
                symbolTable.define("topLevelExpression",new SymbolData(expr.getClass().getSimpleName(),"expression"));
                PrintPathToNode(expr.getFirst(),symbolTable);}
            case Ast.LogExpr logExpr -> {
                symbolTable.define("logicalExpression",new SymbolData(logExpr.getClass().getSimpleName(),"expression"));
                PrintPathToNode(logExpr.getFirst(),symbolTable);}
            case Ast.RelExpr relExpr -> {
                symbolTable.define("relativeExpression",new SymbolData(relExpr.getClass().getSimpleName(),"expression"));
                PrintPathToNode(relExpr.getFirst(),symbolTable);}
            case Ast.ConcatExpr concatExpr ->
            {
                symbolTable.define("concatenationExpression",new SymbolData(concatExpr.getClass().getSimpleName(),"expression"));
                PrintPathToNode(concatExpr.getFirst(),symbolTable);}
            case Ast.AddSubExpr addSubExpr -> {
                symbolTable.define("additionSubtractionExpression",new SymbolData(addSubExpr.getClass().getSimpleName(),"expression"));
                PrintPathToNode(addSubExpr.getFirst(),symbolTable);}
            case Ast.MulDivExpr mulDivExpr -> {
                symbolTable.define("multiplyDivideExpression",new SymbolData(mulDivExpr.getClass().getSimpleName(),"expression"));
                PrintPathToNode(mulDivExpr.getFirst(),symbolTable);}
            case Ast.PowExpr powExpr -> {
                symbolTable.define("exponentExpression",new SymbolData(powExpr.getClass().getSimpleName(),"expression"));
                PrintPathToNode(powExpr.getFirst(),symbolTable);}
            case Ast.FieldAccessExpr fieldAccessExpr ->
            {
                symbolTable.define("fieldAccessExpression",new SymbolData(fieldAccessExpr.getClass().getSimpleName(),"expression"));
                PrintPathToNode(fieldAccessExpr.getFirst(),symbolTable);}
            case Ast.IntNum intNum -> {
                symbolTable.define("intNum",new SymbolData(intNum.getClass().getSimpleName(),"expression"));
                PrintPathToNode(intNum.getFirst(),symbolTable);}
            case Ast.ExprSExpr exprSExpr -> {
                symbolTable.define("exprSExpression",new SymbolData(exprSExpr.getClass().getSimpleName(),"expression"));
                PrintPathToNode(exprSExpr.getFirst(),symbolTable);}
            case Ast.FuncSExpr funcSExpr -> {
                symbolTable.define("funcSExpression",new SymbolData(funcSExpr.getClass().getSimpleName(),"expression"));
                PrintPathToNode(funcSExpr.getFirst(),symbolTable);}
            case Ast.NameSExpr nameSExpr -> {
                symbolTable.define("nameSExpression",new SymbolData(nameSExpr.getClass().getSimpleName(),"expression"));
                PrintPathToNode(nameSExpr.getFirst(),symbolTable);}
            case Ast.Atom.nameAtom nameAtom -> {
                symbolTable.define("nameAtom",new SymbolData(nameAtom.getClass().getSimpleName(),"atomic",nameAtom.name()));
            }
            case Ast.Atom.numAtom numAtom -> {
                symbolTable.define("numAtom",new SymbolData(numAtom.getClass().getSimpleName(),"atomic",String.valueOf(numAtom.i())));
            }
            case Ast.Atom.binNumAtom binNumAtom -> {
                symbolTable.define("binaryAtom",new SymbolData(binNumAtom.getClass().getSimpleName(),"atomic",binNumAtom.bin()));
            }
            case Ast.Atom.hexNumAtom hexNumAtom -> {
                symbolTable.define("hexadecimalAtom",new SymbolData(hexNumAtom.getClass().getSimpleName(),"atomic",hexNumAtom.hex()));
            }
            case Ast.Atom.octNumAtom octNumAtom -> {
                symbolTable.define("octaryAtom",new SymbolData(octNumAtom.getClass().getSimpleName(),"atomic",octNumAtom.oct()));
            }
            case Ast.Atom.realAtom realAtom -> {
                symbolTable.define("realAtom", new SymbolData(realAtom.getClass().getSimpleName(), "atomic", String.valueOf(realAtom.f())));
            }
            case Ast.Atom.charLiteralAtom charLiteralAtom -> {
                symbolTable.define("stringLiteral", new SymbolData(charLiteralAtom.getClass().getSimpleName(), "atomic", charLiteralAtom.charLiteral()));
            }
            case Ast.Atom.logicAtom logicAtom -> {
                symbolTable.define("logicalOperator", new SymbolData(logicAtom.getClass().getSimpleName(),"operator", logicAtom.logicVal()));
            }
            case Ast.Atom.boolAtom boolAtom -> {
                symbolTable.define("logicalOperator", new SymbolData(boolAtom.getClass().getSimpleName(),"operator", boolAtom.bool()));
            }
            case Ast.Atom.addSubAtom addSubAtom -> {
                symbolTable.define("additionSubtractionOperator", new SymbolData(addSubAtom.getClass().getSimpleName(),"operator", addSubAtom.op()));
            }
            case Ast.Atom.mulDivAtom mulDivAtom -> {
                symbolTable.define("multiplyDivideOperator", new SymbolData(mulDivAtom.getClass().getSimpleName(),"operator", mulDivAtom.op()));
            }
            case Ast.Atom.relAtom relAtom -> {
                symbolTable.define("relativeOperator", new SymbolData(relAtom.getClass().getSimpleName(),"operator", relAtom.relOp()));
            }
            case Ast.Atom.starAtom starAtom -> {
                symbolTable.define("pointerStar", new SymbolData(starAtom.getClass().getSimpleName(),"counter", starAtom.ptrStar()));
            }
            case Ast.Atom.typeAtom typeAtom -> {
                symbolTable.define("type", new SymbolData(typeAtom.getClass().getSimpleName(),"atomic", typeAtom.type()));
            }
            case Ast.Atom.nodeAtom nodeAtom -> {
                symbolTable.define(nodeAtom.nodeType(), new SymbolData(nodeAtom.getClass().getSimpleName(),"node", nodeAtom.nodeType()));
            }
            default -> throw new IllegalStateException("Unexpected value: " + cNode);
        }
    }
}
