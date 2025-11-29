package uk.ac.nott.cs.comp3012.coursework;

import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.ast.AstBuilder;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolData;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolTable;

import java.util.*;

public class Frontend {
    SymbolTable parent = new SymbolTable();
    public Ast runFrontend(String input){
        AstBuilder astBuilder = new AstBuilder(this.parent);
        Ast.Units ast = (Ast.Units) astBuilder.buildAst(input);
        parent = astBuilder.getIntSymbolTable();
        System.out.println(parent.getChildren().getFirst().getSymbols());
        return null;
    }

    /**
     * Recursively walk the generated AST and find the path to all terminals auto-flattens expressions
     * PLAN: Replace the .getFirsts with .forEach
     *
     * @param prevST The preceding (parent) SymbolTable
     * @param cNode Current AST node to make a SymbolTable
     */
    public void GenerateSubTables(Ast cNode, SymbolTable prevST){
        SymbolTable symbolTable = new SymbolTable();
        switch(cNode){
            case Ast.FuncDefineParams funcDefineParams -> {
                funcDefineParams.forEach(funcDefineParam -> {
                    GenerateSubTables(funcDefineParam,prevST);
                });
            }
            case Ast.DeclareVariable declareVariable -> {
                declareVariable.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });
            }
            case Ast.DeclarePointer declarePointer -> {
                declarePointer.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });
            }
            case Ast.DeclareArray declareArray -> {
                declareArray.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });
            }
            case Ast.DeclarePointerArray declPtr -> {
                declPtr.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });
            }
            case Ast.InbuiltTypeSpec inbuiltTypeSpec -> {
                inbuiltTypeSpec.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });
            }
            case Ast.CustomTypeSpec customTypeSpec -> {
                customTypeSpec.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });
            }
            case Ast.NormalAssign normalAssign -> {
                normalAssign.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });
            }
            case Ast.ArrayAssign arrayAssign -> {
                arrayAssign.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });
            }
            case Ast.CustomTypeAssign customTypeAssign -> {
                customTypeAssign.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.CustomTypeArrayAssign customTypeArrayAssign->{
                customTypeArrayAssign.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.SbrtCall sbrtCall -> {
                sbrtCall.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });;}
            case Ast.IfBlock ifBlock -> {
                ifBlock.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.IfElseBlock ifElseBlock -> {
                ifElseBlock.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.IfStatement ifStatement -> {
                ifStatement.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.DoIncr1 doIncr1 -> {
                doIncr1.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.DoIncrNot1 doIncrNot1->{
                doIncrNot1.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.DoWhile doWhile -> {
                doWhile.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.Read read->{
                read.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.Write write->{
                write.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.AllocPtr allocPtr -> {
                allocPtr.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.DeallocPtr deallocPtr -> {
                deallocPtr.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.AllocPtrArray allocPtrArray -> {
                allocPtrArray.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.FuncCall funcCall -> {
                funcCall.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.ElseStmt elseStmt -> {
                elseStmt.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.DoParam doParam -> {
                doParam.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.ReadParam readParam -> {
                readParam.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.ArrayIndex arrayIndex -> {
                arrayIndex.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.ArrayDef arrayDef -> {
                arrayDef.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.ParamSubList paramSubList->{
                paramSubList.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.ParamList paramList -> {
                paramList.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.Expr expr-> {
                expr.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.LogExpr logExpr -> {
                    logExpr.forEach(subAst -> {
                        GenerateSubTables(subAst,prevST);
                    });}
            case Ast.RelExpr relExpr -> {
                    relExpr.forEach(subAst -> {
                        GenerateSubTables(subAst, prevST);
                    });
                }
            case Ast.ConcatExpr concatExpr ->
            {
                    concatExpr.forEach(subAst -> {
                        GenerateSubTables(subAst,prevST);
                    });
            }
            case Ast.AddSubExpr addSubExpr -> {
                    addSubExpr.forEach(subAst -> {
                        GenerateSubTables(subAst,prevST);
                    });}
            case Ast.MulDivExpr mulDivExpr -> {
                    mulDivExpr.forEach(subAst -> {
                        GenerateSubTables(subAst,prevST);
                    });
                }
            case Ast.PowExpr powExpr -> {
                    powExpr.forEach(subAst -> {
                        GenerateSubTables(subAst,prevST);
                    });
                }
            case Ast.FieldAccessExpr fieldAccessExpr ->
            {
                fieldAccessExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });
            }
            case Ast.IntNum intNum -> {
                intNum.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.IntSExpr intSExpr -> {
                intSExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.ExprSExpr exprSExpr -> {
                exprSExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.FuncSExpr funcSExpr -> {
                funcSExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.NameSExpr nameSExpr -> {
                nameSExpr.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });}
            case Ast.Atom.nameAtom nameAtom -> {
                symbolTable.define(nameAtom.name(), new SymbolData(nameAtom.getClass().getSimpleName(),"name",nameAtom.name(),symbolTable.getParent().getScopeName()));
                symbolTable.setScopeName(symbolTable.getParent().getScopeName());
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
            case Ast.NameUnit nameUnit -> {
                nameUnit.forEach(subAst -> {
                    GenerateSubTables(subAst,prevST);
                });
            }
            default -> throw new IllegalStateException("Unexpected value: " + cNode.getClass().getSimpleName());
        }
    }
}
