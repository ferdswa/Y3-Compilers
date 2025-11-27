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
     * PLAN: Recursively add the AST Nodes to the SymbolTable.
     *
     * @param curPath The base path (this will be BlockList)
     * @param cNode Current AST node to break down and walk subtree of
     */
    @SuppressWarnings("unchecked") //Doesn't matter, all ASTs are either an ArrayList<Ast> or Ast.Atom which we've checked for already.
    public void PrintPathToNode(Ast cNode, SymbolTable prevST){
        SymbolTable symbolTable = new SymbolTable(prevST);
        switch(cNode){
            case Ast.FuncDefineParams funcDefineParams -> {

            }
            case Ast.DeclareVariable declareVariable -> {

            }
            case Ast.DeclarePointer declarePointer -> {

            }
            case Ast.DeclareArray declareArray -> {

            }
            case Ast.DeclarePointerArray declPtr -> {

            }
            case Ast.InbuiltTypeSpec inbuiltTypeSpec -> {

            }
            case Ast.CustomTypeSpec customTypeSpec -> {

            }
            case Ast.NormalAssign normalAssign -> {

            }
            case Ast.ArrayAssign arrayAssign -> {

            }
            case Ast.CustomTypeAssign customTypeAssign -> {

            }
            case Ast.CustomTypeArrayAssign customTypeArrayAssign->{

            }
            case Ast.SbrtCall sbrtCall -> {

            }
            case Ast.IfBlock ifBlock -> {

            }
            case Ast.IfElseBlock ifElseBlock -> {

            }
            case Ast.IfStatement ifStatement -> {

            }
            case Ast.DoIncr1 doIncr1 -> {

            }
            case Ast.DoIncrNot1 doIncrNot1->{

            }
            case Ast.DoWhile doWhile -> {

            }
            case Ast.Read read->{

            }
            case Ast.Write write->{

            }
            case Ast.AllocPtr allocPtr -> {

            }
            case Ast.DeallocPtr deallocPtr -> {

            }
            case Ast.AllocPtrArray allocPtrArray -> {

            }
            case Ast.FuncCall funcCall -> {

            }
            case Ast.ElseStmt elseStmt -> {

            }
            case Ast.DoParam doParam -> {

            }
            case Ast.ReadParam readParam -> {

            }
            case Ast.ArrayIndex arrayIndex -> {

            }
            case Ast.ArrayDef arrayDef -> {

            }
            case Ast.ParamSubList paramSubList->{

            }
            case Ast.ParamList paramList -> {

            }
            case Ast.Expr expr-> {

            }
            case Ast.LogExpr logExpr -> {

            }
            case Ast.RelExpr relExpr -> {

            }
            case Ast.ConcatExpr concatExpr ->
            {

            }
            case Ast.AddSubExpr addSubExpr -> {

            }
            case Ast.MulDivExpr mulDivExpr -> {

            }
            case Ast.PowExpr powExpr -> {

            }
            case Ast.FieldAccessExpr fieldAccessExpr ->
            {

            }
            case Ast.IntNum intNum -> {

            }
            case Ast.ExprSExpr exprSExpr -> {

            }
            case Ast.FuncSExpr funcSExpr -> {

            }
            case Ast.NameSExpr nameSExpr -> {
                
            }
            case Ast.Atom.nameAtom nameAtom -> {

            }
            case Ast.Atom.numAtom numAtom -> {

            }
            case Ast.Atom.binNumAtom binNumAtom -> {

            }
            case Ast.Atom.hexNumAtom hexNumAtom -> {

            }
            case Ast.Atom.octNumAtom octNumAtom -> {

            }
            case Ast.Atom.realAtom realAtom -> {

            }
            case Ast.Atom.charLiteralAtom charLiteralAtom -> {

            }
            case Ast.Atom.logicAtom logicAtom -> {

            }
            case Ast.Atom.boolAtom boolAtom -> {

            }
            case Ast.Atom.addSubAtom addSubAtom -> {

            }
            case Ast.Atom.mulDivAtom mulDivAtom -> {

            }
            case Ast.Atom.relAtom relAtom -> {

            }
            case Ast.Atom.starAtom starAtom -> {

            }
            case Ast.Atom.typeAtom typeAtom -> {

            }
            case Ast.Atom.nodeAtom nodeAtom -> {

            }
            default -> throw new IllegalStateException("Unexpected value: " + cNode);
        }
    }
}
