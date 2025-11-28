package uk.ac.nott.cs.comp3012.coursework;

import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.ast.AstBuilder;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolData;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolTable;

import java.util.*;

public class Frontend {
    public Ast runFrontend(String input){
        AstBuilder astBuilder = new AstBuilder();
        Ast.Units ast = (Ast.Units) astBuilder.buildAst(input);
        SymbolTable topST = new SymbolTable();
        topST.define("units",new SymbolData("units","entireProgram"));
        topST.setScopeName("entireProgram");//virstual and unused
        for (Ast unit : ast) {//For unit
            SymbolTable symbolTable = new SymbolTable(topST);
            switch(unit){
                case Ast.FuncRValueUnit funcRValueUnit -> {
                    symbolTable.define("function",new SymbolData(funcRValueUnit.getClass().getSimpleName(),"unit"));
                    symbolTable.setScopeName(symbolTable.getParent().getSymbols().keySet().iterator().next());//Get the first symbol of parent table
                    funcRValueUnit.forEach(subAst -> {
                        GenerateSubTables(subAst,symbolTable);
                    });
                }
                case Ast.FuncRVoidUnit funcRVoidUnit -> {
                    symbolTable.define("function",new SymbolData(funcRVoidUnit.getClass().getSimpleName(),"unit"));
                    symbolTable.setScopeName(symbolTable.getParent().getSymbols().keySet().iterator().next());
                    funcRVoidUnit.forEach(subAst -> {
                        GenerateSubTables(subAst,symbolTable);
                    });
                }
                case Ast.ProgramUnit programUnit -> {
                    symbolTable.define("program",new SymbolData(programUnit.getClass().getSimpleName(),"unit"));
                    symbolTable.setScopeName(symbolTable.getParent().getSymbols().keySet().iterator().next());
                    programUnit.forEach(subAst -> {
                        GenerateSubTables(subAst,symbolTable);
                    });
                }
                case Ast.CustomTypeDefUnit customTypeDefUnit -> {
                    symbolTable.define("customtype",new SymbolData(customTypeDefUnit.getClass().getSimpleName(),"unit"));
                    symbolTable.setScopeName(symbolTable.getParent().getSymbols().keySet().iterator().next());
                    customTypeDefUnit.forEach(subAst -> {
                        GenerateSubTables(subAst,symbolTable);
                    });
                }
                default -> throw new IllegalStateException("Unexpected value: " + unit);
            }
        }
        //Node lookup will not be O(1) because I have spent enough time on this part. There is now a navigable tree, fully traversable up and down; parent node is symbolTable.
        for(SymbolTable st: topST.getChildren()){
            System.out.println(st.getChildren().size());
            if(st.getChildren().size()>3){//Check that the name at the start and end of block are the same and that there are statements or declarations inside the block
                if(st.getChildren().get(1).getSymbols().get("nameAtom").value.equals(st.getChildren().getLast().getSymbols().get("nameAtom").value)){
                    FlattenAst(st);
                }
                else{
                    throw new IllegalStateException(String.format("Mismatched opening and closing names in %s. Opening: %s vs Closing: %s", st.getSymbols().keySet().iterator().next(),st.getChildren().get(1).getSymbols().get("nameAtom").value ,st.getChildren().getLast().getSymbols().get("nameAtom").value));
                }
            }
            else{//Random empty blocks will
                throw new IllegalStateException(String.format("Malformed program subunit %s. Empty %s are not allowed.", st.getChildren().getLast().getSymbols().get("nameAtom").value, st.getSymbols().keySet().iterator().next()));
            }
        }
        return null;
    }


    /**
     * Flatten the AST down by removing nodes with 1 child node
     * @param symbolTable The symbol table to check
     */
    public void FlattenAst(SymbolTable symbolTable){
        SymbolTable copy;
        ArrayList<SymbolTable> symbolTables = (ArrayList<SymbolTable>) symbolTable.getChildren();
        if(symbolTable.getChildren().size() == 1){//If there is only one node below this one you can remove it.
            copy = symbolTable.getChildren().getFirst();
            copy.setParent(symbolTable.getParent());
            symbolTable.getParent().addChild(copy);
            symbolTable.getParent().removeChild(symbolTable.getChildren().getFirst());
            FlattenAst(copy);
        }
        else if(!symbolTable.getChildren().isEmpty()){
            System.out.println("Useful Node: " + symbolTable.getSymbols().keySet().iterator().next());
            while(!symbolTables.isEmpty()){
                copy = symbolTables.getFirst();
                symbolTables.remove(symbolTables.getFirst());
                FlattenAst(copy);
            }
        }
        else{
            System.out.println("Atomic: " + symbolTable.getSymbols().values().iterator().next().value);
        }
    }
    /**
     * Recursively walk the generated AST and find the path to all terminals, filling in non-terminals on the way. Add terminal values to the path when discovered
     * PLAN: Replace the .getFirsts with .forEach
     *
     * @param prevST The preceding (parent) SymbolTable
     * @param cNode Current AST node to make a SymbolTable
     */
    public void GenerateSubTables(Ast cNode, SymbolTable prevST){
        SymbolTable symbolTable = new SymbolTable(prevST);
        switch(cNode){
            case Ast.FuncDefineParams funcDefineParams -> {
                symbolTable.define("functionParamList",new SymbolData(funcDefineParams.getClass().getSimpleName(),"parameterList"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                funcDefineParams.forEach(funcDefineParam -> {
                    GenerateSubTables(funcDefineParam,symbolTable);
                });
            }
            case Ast.DeclareVariable declareVariable -> {
                symbolTable.define("declareVariable",new SymbolData(declareVariable.getClass().getSimpleName(),"declaration"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                declareVariable.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });
            }
            case Ast.DeclarePointer declarePointer -> {
                symbolTable.define("declarePointer",new SymbolData(declarePointer.getClass().getSimpleName(),"declaration"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                declarePointer.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });
            }
            case Ast.DeclareArray declareArray -> {
                symbolTable.define("declareArray",new SymbolData(declareArray.getClass().getSimpleName(),"declaration"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                declareArray.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });
            }
            case Ast.DeclarePointerArray declPtr -> {
                symbolTable.define("declarePointerArray",new SymbolData(declPtr.getClass().getSimpleName(),"declaration"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                declPtr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });
            }
            case Ast.InbuiltTypeSpec inbuiltTypeSpec -> {
                symbolTable.define("inbuiltType", new SymbolData(inbuiltTypeSpec.getClass().getSimpleName(),"typeSpec"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                inbuiltTypeSpec.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });
            }
            case Ast.CustomTypeSpec customTypeSpec -> {
                symbolTable.define("customType", new SymbolData(customTypeSpec.getClass().getSimpleName(),"typeSpec"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                customTypeSpec.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });
            }
            case Ast.NormalAssign normalAssign -> {
                symbolTable.define("assignVariable", new SymbolData(normalAssign.getClass().getSimpleName(),"assign"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                normalAssign.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });
            }
            case Ast.ArrayAssign arrayAssign -> {
                symbolTable.define("assignArray", new SymbolData(arrayAssign.getClass().getSimpleName(),"assign"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                arrayAssign.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });
            }
            case Ast.CustomTypeAssign customTypeAssign -> {
                symbolTable.define("assignCustomType", new SymbolData(customTypeAssign.getClass().getSimpleName(),"assign"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                customTypeAssign.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.CustomTypeArrayAssign customTypeArrayAssign->{
                symbolTable.define("assignCustomTypeArray", new SymbolData(customTypeArrayAssign.getClass().getSimpleName(),"assign"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                customTypeArrayAssign.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.SbrtCall sbrtCall -> {
                symbolTable.define("callSubroutine", new SymbolData(sbrtCall.getClass().getSimpleName(),"call"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                sbrtCall.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });;}
            case Ast.IfBlock ifBlock -> {
                symbolTable.define("conditionalJump", new SymbolData(ifBlock.getClass().getSimpleName(),"ifNoElse"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                ifBlock.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.IfElseBlock ifElseBlock -> {
                symbolTable.define("conditionalJump", new SymbolData(ifElseBlock.getClass().getSimpleName(),"ifWithElse"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                ifElseBlock.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.IfStatement ifStatement -> {
                symbolTable.define("conditionalJump", new SymbolData(ifStatement.getClass().getSimpleName(),"ifSingleExpr"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                ifStatement.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.DoIncr1 doIncr1 -> {
                symbolTable.define("doIncrementOne",new SymbolData(doIncr1.getClass().getSimpleName(),"do"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                doIncr1.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.DoIncrNot1 doIncrNot1->{
                symbolTable.define("doIncrementNotOne",new SymbolData(doIncrNot1.getClass().getSimpleName(),"do"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                doIncrNot1.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.DoWhile doWhile -> {
                symbolTable.define("doWhile",new SymbolData(doWhile.getClass().getSimpleName(),"do"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                doWhile.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.Read read->{
                symbolTable.define("read",new SymbolData(read.getClass().getSimpleName(),"IO"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                read.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.Write write->{
                symbolTable.define("write",new SymbolData(write.getClass().getSimpleName(),"IO"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                write.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.AllocPtr allocPtr -> {
                symbolTable.define("allocatePointer",new SymbolData(allocPtr.getClass().getSimpleName(),"allocation"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                allocPtr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.DeallocPtr deallocPtr -> {
                symbolTable.define("deAllocatePointer",new SymbolData(deallocPtr.getClass().getSimpleName(),"deallocation"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                deallocPtr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.AllocPtrArray allocPtrArray -> {
                symbolTable.define("allocatePointerArray",new SymbolData(allocPtrArray.getClass().getSimpleName(),"allocation"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                allocPtrArray.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.FuncCall funcCall -> {
                symbolTable.define("callFunction",new SymbolData(funcCall.getClass().getSimpleName(),"call"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                funcCall.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.ElseStmt elseStmt -> {
                symbolTable.define("elseBlock",new SymbolData(elseStmt.getClass().getSimpleName(),"else"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                elseStmt.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.DoParam doParam -> {
                symbolTable.define("doParamList",new SymbolData(doParam.getClass().getSimpleName(),"parameterList"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                doParam.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.ReadParam readParam -> {
                symbolTable.define("readParamList",new SymbolData(readParam.getClass().getSimpleName(),"parameterList"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                readParam.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.ArrayIndex arrayIndex -> {
                symbolTable.define("arrayIndex",new SymbolData(arrayIndex.getClass().getSimpleName(),"arrayIndexer"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                arrayIndex.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.ArrayDef arrayDef -> {
                symbolTable.define("array",new SymbolData(arrayDef.getClass().getSimpleName(),"array"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                arrayDef.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.ParamSubList paramSubList->{
                symbolTable.define("parameterSubList",new SymbolData(paramSubList.getClass().getSimpleName(),"parameterSubList"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                paramSubList.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.ParamList paramList -> {
                symbolTable.define("parameterList",new SymbolData(paramList.getClass().getSimpleName(),"parameterList"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                paramList.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.Expr expr-> {
                symbolTable.define("topLevelExpression",new SymbolData(expr.getClass().getSimpleName(),"expression"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                expr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.LogExpr logExpr -> {
                symbolTable.define("logicalExpression",new SymbolData(logExpr.getClass().getSimpleName(),"expression"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                logExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.RelExpr relExpr -> {
                symbolTable.define("relativeExpression",new SymbolData(relExpr.getClass().getSimpleName(),"expression"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                relExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.ConcatExpr concatExpr ->
            {
                symbolTable.define("concatenationExpression",new SymbolData(concatExpr.getClass().getSimpleName(),"expression"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                concatExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.AddSubExpr addSubExpr -> {
                symbolTable.define("additionSubtractionExpression",new SymbolData(addSubExpr.getClass().getSimpleName(),"expression"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                addSubExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.MulDivExpr mulDivExpr -> {
                symbolTable.define("multiplyDivideExpression",new SymbolData(mulDivExpr.getClass().getSimpleName(),"expression"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                mulDivExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.PowExpr powExpr -> {
                symbolTable.define("exponentExpression",new SymbolData(powExpr.getClass().getSimpleName(),"expression"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                powExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.FieldAccessExpr fieldAccessExpr ->
            {
                symbolTable.define("fieldAccessExpression",new SymbolData(fieldAccessExpr.getClass().getSimpleName(),"expression"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                fieldAccessExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.IntNum intNum -> {
                symbolTable.define("intNum",new SymbolData(intNum.getClass().getSimpleName(),"expression"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                intNum.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.IntSExpr intSExpr -> {
                symbolTable.define("intNum",new SymbolData(intSExpr.getClass().getSimpleName(),"expression"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                intSExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.ExprSExpr exprSExpr -> {
                symbolTable.define("exprSExpression",new SymbolData(exprSExpr.getClass().getSimpleName(),"expression"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                exprSExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.FuncSExpr funcSExpr -> {
                symbolTable.define("funcSExpression",new SymbolData(funcSExpr.getClass().getSimpleName(),"expression"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                funcSExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.NameSExpr nameSExpr -> {
                symbolTable.define("nameSExpression",new SymbolData(nameSExpr.getClass().getSimpleName(),"expression"));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
                nameSExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,symbolTable);
                });}
            case Ast.Atom.nameAtom nameAtom -> {
                symbolTable.define("nameAtom",new SymbolData(nameAtom.getClass().getSimpleName(),"atomic",nameAtom.name()));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            case Ast.Atom.numAtom numAtom -> {
                symbolTable.define("numAtom",new SymbolData(numAtom.getClass().getSimpleName(),"atomic",String.valueOf(numAtom.i())));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            case Ast.Atom.binNumAtom binNumAtom -> {
                symbolTable.define("binaryAtom",new SymbolData(binNumAtom.getClass().getSimpleName(),"atomic",binNumAtom.bin()));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            case Ast.Atom.hexNumAtom hexNumAtom -> {
                symbolTable.define("hexadecimalAtom",new SymbolData(hexNumAtom.getClass().getSimpleName(),"atomic",hexNumAtom.hex()));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            case Ast.Atom.octNumAtom octNumAtom -> {
                symbolTable.define("octaryAtom",new SymbolData(octNumAtom.getClass().getSimpleName(),"atomic",octNumAtom.oct()));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            case Ast.Atom.realAtom realAtom -> {
                symbolTable.define("realAtom", new SymbolData(realAtom.getClass().getSimpleName(), "atomic", String.valueOf(realAtom.f())));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            case Ast.Atom.charLiteralAtom charLiteralAtom -> {
                symbolTable.define("stringLiteral", new SymbolData(charLiteralAtom.getClass().getSimpleName(), "atomic", charLiteralAtom.charLiteral()));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            case Ast.Atom.logicAtom logicAtom -> {
                symbolTable.define("logicalOperator", new SymbolData(logicAtom.getClass().getSimpleName(),"operator", logicAtom.logicVal()));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            case Ast.Atom.boolAtom boolAtom -> {
                symbolTable.define("boolean", new SymbolData(boolAtom.getClass().getSimpleName(),"atomic", boolAtom.bool()));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            case Ast.Atom.addSubAtom addSubAtom -> {
                symbolTable.define("additionSubtractionOperator", new SymbolData(addSubAtom.getClass().getSimpleName(),"operator", addSubAtom.op()));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            case Ast.Atom.mulDivAtom mulDivAtom -> {
                symbolTable.define("multiplyDivideOperator", new SymbolData(mulDivAtom.getClass().getSimpleName(),"operator", mulDivAtom.op()));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            case Ast.Atom.relAtom relAtom -> {
                symbolTable.define("relativeOperator", new SymbolData(relAtom.getClass().getSimpleName(),"operator", relAtom.relOp()));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            case Ast.Atom.starAtom starAtom -> {
                symbolTable.define("pointerStar", new SymbolData(starAtom.getClass().getSimpleName(),"counter", starAtom.ptrStar()));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            case Ast.Atom.typeAtom typeAtom -> {
                symbolTable.define("type", new SymbolData(typeAtom.getClass().getSimpleName(),"atomic", typeAtom.type()));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            case Ast.Atom.nodeAtom nodeAtom -> {
                symbolTable.define(nodeAtom.nodeType(), new SymbolData(nodeAtom.getClass().getSimpleName(),"node", nodeAtom.nodeType()));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
            }
            default -> throw new IllegalStateException("Unexpected value: " + cNode.getClass().getSimpleName());
        }
    }
}
