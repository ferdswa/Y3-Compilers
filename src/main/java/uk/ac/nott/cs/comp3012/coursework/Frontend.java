package uk.ac.nott.cs.comp3012.coursework;

import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.ast.AstBuilder;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolData;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolTable;

import java.util.*;

public class Frontend {
    ArrayList<String> listPaths = new ArrayList<>();
    public Ast runFrontend(String input){
        AstBuilder astBuilder = new AstBuilder();
        Ast.Units ast = (Ast.Units) astBuilder.buildAst(input);
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.define("units",new SymbolData("Units","nodeAtom"));
        for (Ast unit : ast) {//For unit
            SymbolTable newST = new SymbolTable(symbolTable);
            switch(unit){
                case Ast.FuncRValueUnit funcRValueUnit -> {
                    newST.define("unit"+ast.indexOf(funcRValueUnit),new SymbolData(funcRValueUnit.getClass().getSimpleName(),"nodeAtom"));
                    funcRValueUnit.forEach(subAst -> {
                        GenerateSubTrees(subAst,newST);
                    });
                }
                case Ast.FuncRVoidUnit funcRVoidUnit -> {
                    newST.define("unit"+ast.indexOf(funcRVoidUnit),new SymbolData(funcRVoidUnit.getClass().getSimpleName(),"nodeAtom"));
                    funcRVoidUnit.forEach(subAst -> {
                        GenerateSubTrees(subAst,newST);
                    });
                }
                case Ast.ProgramUnit programUnit -> {
                    newST.define("unit"+ast.indexOf(programUnit),new SymbolData(programUnit.getClass().getSimpleName(),"nodeAtom"));
                    programUnit.forEach(subAst -> {
                        GenerateSubTrees(subAst,newST);
                    });
                }
                case Ast.CustomTypeDefUnit customTypeDefUnit -> {
                    newST.define("unit"+ast.indexOf(customTypeDefUnit),new SymbolData(customTypeDefUnit.getClass().getSimpleName(),"nodeAtom"));
                    customTypeDefUnit.forEach(subAst -> {
                        GenerateSubTrees(subAst,newST);
                    });
                }
                default -> throw new IllegalStateException("Unexpected value: " + unit);
            }
        }
        //Node lookup will not be O(1) because I have spent enough time on this part. There is now a navigable tree, fully traversable up and down; parent node is symbolTable.

        return null;
    }
    /**
     * Recursively walk the generated AST and find the path to all terminals, filling in non-terminals on the way. Add terminal values to the path when discovered
     * PLAN: Replace the .getFirsts with .forEach
     *
     * @param prevST The preceding (parent) SymbolTable
     * @param cNode Current AST node to make a SymbolTable
     */
    public void GenerateSubTrees(Ast cNode, SymbolTable prevST){
        SymbolTable symbolTable = new SymbolTable(prevST);
        switch(cNode){
            case Ast.FuncDefineParams funcDefineParams -> {
                symbolTable.define("functionParamList",new SymbolData(funcDefineParams.getClass().getSimpleName(),"parameterList"));
                funcDefineParams.forEach(funcDefineParam -> {
                    GenerateSubTrees(funcDefineParam,symbolTable);
                });
            }
            case Ast.DeclareVariable declareVariable -> {
                symbolTable.define("declareVariable",new SymbolData(declareVariable.getClass().getSimpleName(),"declaration"));
                declareVariable.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });
            }
            case Ast.DeclarePointer declarePointer -> {
                symbolTable.define("declarePointer",new SymbolData(declarePointer.getClass().getSimpleName(),"declaration"));
                declarePointer.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });
            }
            case Ast.DeclareArray declareArray -> {
                symbolTable.define("declareArray",new SymbolData(declareArray.getClass().getSimpleName(),"declaration"));
                declareArray.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });
            }
            case Ast.DeclarePointerArray declPtr -> {
                symbolTable.define("declarePointerArray",new SymbolData(declPtr.getClass().getSimpleName(),"declaration"));
                declPtr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });
            }
            case Ast.InbuiltTypeSpec inbuiltTypeSpec -> {
                symbolTable.define("inbuiltType", new SymbolData(inbuiltTypeSpec.getClass().getSimpleName(),"typeSpec"));
                inbuiltTypeSpec.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });
            }
            case Ast.CustomTypeSpec customTypeSpec -> {
                symbolTable.define("customType", new SymbolData(customTypeSpec.getClass().getSimpleName(),"typeSpec"));
                customTypeSpec.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });
            }
            case Ast.NormalAssign normalAssign -> {
                symbolTable.define("assignVariable", new SymbolData(normalAssign.getClass().getSimpleName(),"assign"));
                normalAssign.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });
            }
            case Ast.ArrayAssign arrayAssign -> {
                symbolTable.define("assignArray", new SymbolData(arrayAssign.getClass().getSimpleName(),"assign"));
                arrayAssign.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });
            }
            case Ast.CustomTypeAssign customTypeAssign -> {
                symbolTable.define("assignCustomType", new SymbolData(customTypeAssign.getClass().getSimpleName(),"assign"));
                customTypeAssign.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.CustomTypeArrayAssign customTypeArrayAssign->{
                symbolTable.define("assignCustomTypeArray", new SymbolData(customTypeArrayAssign.getClass().getSimpleName(),"assign"));
                customTypeArrayAssign.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.SbrtCall sbrtCall -> {
                symbolTable.define("callSubroutine", new SymbolData(sbrtCall.getClass().getSimpleName(),"call"));
                sbrtCall.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });;}
            case Ast.IfBlock ifBlock -> {
                symbolTable.define("conditionalJump", new SymbolData(ifBlock.getClass().getSimpleName(),"ifNoElse"));
                ifBlock.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.IfElseBlock ifElseBlock -> {
                symbolTable.define("conditionalJump", new SymbolData(ifElseBlock.getClass().getSimpleName(),"ifWithElse"));
                ifElseBlock.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.IfStatement ifStatement -> {
                symbolTable.define("conditionalJump", new SymbolData(ifStatement.getClass().getSimpleName(),"ifSingleExpr"));
                ifStatement.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.DoIncr1 doIncr1 -> {
                symbolTable.define("doIncrementOne",new SymbolData(doIncr1.getClass().getSimpleName(),"do"));
                doIncr1.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.DoIncrNot1 doIncrNot1->{
                symbolTable.define("doIncrementNotOne",new SymbolData(doIncrNot1.getClass().getSimpleName(),"do"));
                doIncrNot1.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.DoWhile doWhile -> {
                symbolTable.define("doWhile",new SymbolData(doWhile.getClass().getSimpleName(),"do"));
                doWhile.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.Read read->{
                symbolTable.define("read",new SymbolData(read.getClass().getSimpleName(),"IO"));
                read.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.Write write->{
                symbolTable.define("write",new SymbolData(write.getClass().getSimpleName(),"IO"));
                write.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.AllocPtr allocPtr -> {
                symbolTable.define("allocatePointer",new SymbolData(allocPtr.getClass().getSimpleName(),"allocation"));
                allocPtr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.DeallocPtr deallocPtr -> {
                symbolTable.define("deAllocatePointer",new SymbolData(deallocPtr.getClass().getSimpleName(),"deallocation"));
                deallocPtr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.AllocPtrArray allocPtrArray -> {
                symbolTable.define("allocatePointerArray",new SymbolData(allocPtrArray.getClass().getSimpleName(),"allocation"));
                allocPtrArray.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.FuncCall funcCall -> {
                symbolTable.define("callFunction",new SymbolData(funcCall.getClass().getSimpleName(),"call"));
                funcCall.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.ElseStmt elseStmt -> {
                symbolTable.define("elseBlock",new SymbolData(elseStmt.getClass().getSimpleName(),"else"));
                elseStmt.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.DoParam doParam -> {
                symbolTable.define("doParamList",new SymbolData(doParam.getClass().getSimpleName(),"parameterList"));
                doParam.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.ReadParam readParam -> {
                symbolTable.define("readParamList",new SymbolData(readParam.getClass().getSimpleName(),"parameterList"));
                readParam.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.ArrayIndex arrayIndex -> {
                symbolTable.define("arrayIndex",new SymbolData(arrayIndex.getClass().getSimpleName(),"arrayIndexer"));
                arrayIndex.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.ArrayDef arrayDef -> {
                symbolTable.define("array",new SymbolData(arrayDef.getClass().getSimpleName(),"array"));
                arrayDef.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.ParamSubList paramSubList->{
                symbolTable.define("parameterSubList",new SymbolData(paramSubList.getClass().getSimpleName(),"parameterSubList"));
                paramSubList.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.ParamList paramList -> {
                symbolTable.define("parameterList",new SymbolData(paramList.getClass().getSimpleName(),"parameterList"));
                paramList.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.Expr expr-> {
                symbolTable.define("topLevelExpression",new SymbolData(expr.getClass().getSimpleName(),"expression"));
                expr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.LogExpr logExpr -> {
                symbolTable.define("logicalExpression",new SymbolData(logExpr.getClass().getSimpleName(),"expression"));
                logExpr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.RelExpr relExpr -> {
                symbolTable.define("relativeExpression",new SymbolData(relExpr.getClass().getSimpleName(),"expression"));
                relExpr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.ConcatExpr concatExpr ->
            {
                symbolTable.define("concatenationExpression",new SymbolData(concatExpr.getClass().getSimpleName(),"expression"));
                concatExpr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.AddSubExpr addSubExpr -> {
                symbolTable.define("additionSubtractionExpression",new SymbolData(addSubExpr.getClass().getSimpleName(),"expression"));
                addSubExpr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.MulDivExpr mulDivExpr -> {
                symbolTable.define("multiplyDivideExpression",new SymbolData(mulDivExpr.getClass().getSimpleName(),"expression"));
                mulDivExpr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.PowExpr powExpr -> {
                symbolTable.define("exponentExpression",new SymbolData(powExpr.getClass().getSimpleName(),"expression"));
                powExpr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.FieldAccessExpr fieldAccessExpr ->
            {
                symbolTable.define("fieldAccessExpression",new SymbolData(fieldAccessExpr.getClass().getSimpleName(),"expression"));
                fieldAccessExpr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.IntNum intNum -> {
                symbolTable.define("intNum",new SymbolData(intNum.getClass().getSimpleName(),"expression"));
                intNum.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.IntSExpr intSExpr -> {
                symbolTable.define("intNum",new SymbolData(intSExpr.getClass().getSimpleName(),"expression"));
                intSExpr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.ExprSExpr exprSExpr -> {
                symbolTable.define("exprSExpression",new SymbolData(exprSExpr.getClass().getSimpleName(),"expression"));
                exprSExpr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.FuncSExpr funcSExpr -> {
                symbolTable.define("funcSExpression",new SymbolData(funcSExpr.getClass().getSimpleName(),"expression"));
                funcSExpr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
            case Ast.NameSExpr nameSExpr -> {
                symbolTable.define("nameSExpression",new SymbolData(nameSExpr.getClass().getSimpleName(),"expression"));
                nameSExpr.forEach(subAst -> {
                    GenerateSubTrees(subAst,symbolTable);
                });}
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
            default -> throw new IllegalStateException("Unexpected value: " + cNode.getClass().getSimpleName());
        }
    }
}
