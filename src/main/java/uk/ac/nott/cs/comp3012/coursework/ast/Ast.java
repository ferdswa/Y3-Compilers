package uk.ac.nott.cs.comp3012.coursework.ast;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Base interface type for all AST classes. Modify it, delete it, or do whatever you want with it.
 */
public interface Ast {
    interface Atom extends Ast{
        //numbers
        record numAtom(int i) implements Atom {

        }
        record binNumAtom(String bin) implements Atom {

        }
        record octNumAtom(String oct) implements Atom {

        }
        record hexNumAtom(String hex) implements Atom {

        }
        //character literals
        record charLiteralAtom(String charLiteral) implements Atom {

        }

        //names
        record nameAtom(String s) implements Atom {

        }
        //logicals
        record logicAtom(String l) implements Atom {

        }
        //operators
        record addSubAtom(char c) implements Atom {

        }
        record mulDivAtom(char c) implements Atom {

        }
        record relAtom(String r) implements Atom {

        }
        record starAtom(char c) implements Atom {

        }
        //types (Called typeSpec)
        record typeAtom(String t) implements Atom {

        }
    }
    class CustomTypeSpec extends ArrayList <Ast> implements Ast {
        public CustomTypeSpec() {super();}
        public CustomTypeSpec(Collection<? extends Ast> elems) {super(elems);}
    }
    class InbuiltTypeSpec extends ArrayList <Ast> implements Ast {
        public InbuiltTypeSpec() {super();}
        public InbuiltTypeSpec(Collection<? extends Ast> elems) {super(elems);}
    }
    class IntNum extends ArrayList<Ast> implements Ast {
        public IntNum() {
            super();
        }

        public IntNum(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class ArrayDef extends ArrayList<Ast> implements Ast {
        public ArrayDef() {
            super();
        }
        public ArrayDef(Collection<? extends Ast> elems) {
            super(elems);
        }
    }

    class ExprSExpr extends ArrayList<Ast> implements Ast {
        public ExprSExpr() {
            super();
        }
        public ExprSExpr(Collection<? extends Ast> elems) {
            super(elems);
        }
    }

    class FuncSExpr extends ArrayList<Ast> implements Ast {
        public FuncSExpr() {
            super();
        }
        public FuncSExpr(Collection<? extends Ast> elems) {
            super(elems);
        }
    }

    class ParamList extends ArrayList<Ast> implements Ast {
        public ParamList() {
            super();
        }
        public ParamList(Collection<? extends Ast> elems) {
            super(elems);
        }
    }

    //Asts for expressions
    class FieldAccessExpr extends ArrayList<Ast> implements Ast {
        public FieldAccessExpr() {
            super();
        }
        public FieldAccessExpr(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class PowExpr extends ArrayList<Ast> implements Ast {
        public PowExpr() {
            super();
        }
        public PowExpr(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class MulDivExpr extends ArrayList<Ast> implements Ast {
        public MulDivExpr() {
            super();
        }

        public MulDivExpr(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class AddSubExpr extends ArrayList<Ast> implements Ast {
        public AddSubExpr() {
            super();
        }
        public AddSubExpr(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class ConcatExpr extends ArrayList<Ast> implements Ast {
        public ConcatExpr() {
            super();
        }
        public ConcatExpr(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class RelExpr extends ArrayList<Ast> implements Ast {
        public RelExpr() {
            super();
        }
        public RelExpr(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class LogExpr extends ArrayList<Ast> implements Ast {
        public LogExpr() {
            super();
        }
        public LogExpr(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class Expr extends ArrayList<Ast> implements Ast {
        public Expr() {
            super();
        }
        public Expr(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    //Statements
    class FuncCall  extends ArrayList<Ast> implements Ast {
        public FuncCall() {
            super();
        }
        public FuncCall(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class DeallocPtr extends ArrayList<Ast> implements Ast {
        public DeallocPtr() {
            super();
        }
        public DeallocPtr(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class AllocPtrArray  extends ArrayList<Ast> implements Ast {
        public AllocPtrArray() {
            super();
        }
        public AllocPtrArray(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class AllocPtr  extends ArrayList<Ast> implements Ast {
        public AllocPtr() { super();}
        public AllocPtr(Collection<? extends Ast> elems) { super(elems);}
    }
    class Write extends ArrayList<Ast> implements Ast {
        public Write() {
            super();
        }
        public Write(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class Read  extends ArrayList<Ast> implements Ast {
        public Read() {
            super();
        }
        public Read(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class DoWhile extends ArrayList<Ast> implements Ast {
        public DoWhile() {
            super();
        }
        public DoWhile(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class DoIncr1 extends ArrayList<Ast> implements Ast {
        public DoIncr1() {
            super();
        }
        public DoIncr1(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class DoIncrNot1 extends ArrayList<Ast> implements Ast {
        public DoIncrNot1() {
            super();
        }
        public DoIncrNot1(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class IfStatement extends ArrayList<Ast> implements Ast {
        public IfStatement() {
            super();
        }
        public IfStatement(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class IfElseBlock extends ArrayList<Ast> implements Ast {
        public IfElseBlock() {
            super();
        }
        public IfElseBlock(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class IfBlock extends ArrayList<Ast> implements Ast {
        public IfBlock() {
            super();
        }
        public IfBlock(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class SbrtCall extends ArrayList<Ast> implements Ast {
        public SbrtCall() {
            super();
        }
        public SbrtCall(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class CustomTypeArrayAssign  extends ArrayList<Ast> implements Ast {
        public CustomTypeArrayAssign() {super();}
        public CustomTypeArrayAssign(Collection<? extends Ast> elems) {super(elems);}
    }
    class CustomTypeAssign  extends ArrayList<Ast> implements Ast {
        public CustomTypeAssign() {
            super();
        }
        public  CustomTypeAssign(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class ArrayAssign  extends ArrayList<Ast> implements Ast {
        public ArrayAssign() {
            super();
        }
        public ArrayAssign(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class NormalAssign  extends ArrayList<Ast> implements Ast {
        public NormalAssign() {
            super();
        }
        public NormalAssign(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    //Declarations
    class DeclarePointerArray  extends ArrayList<Ast> implements Ast {
        public DeclarePointerArray() {
            super();
        }
        public DeclarePointerArray(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class DeclarePointer  extends ArrayList<Ast> implements Ast {
        public DeclarePointer() {
            super();
        }
        public DeclarePointer(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class DeclareArray  extends ArrayList<Ast> implements Ast {
        public DeclareArray() {
            super();
        }
        public DeclareArray(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class DeclareVariable  extends ArrayList<Ast> implements Ast {
        public DeclareVariable() {
            super();
        }
        public DeclareVariable(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class FuncDeclareParamList  extends ArrayList<Ast> implements Ast {
        public FuncDeclareParamList() {super();}
        public FuncDeclareParamList(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    //Blocks - A block is a list of declarations followed by a list of statements, except CustomTypeDeclBlock which only has declarations
    class CustomTypeDeclBlock extends ArrayList<Ast> implements Ast {
        public CustomTypeDeclBlock() {super();}
        public CustomTypeDeclBlock(Collection<? extends Ast> elems) {super(elems);}
    }
    class SbrtBlock  extends ArrayList<Ast> implements Ast {
        public SbrtBlock() {
            super();
        }
        public SbrtBlock(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class VoidFuncBlock  extends ArrayList<Ast> implements Ast {
        public VoidFuncBlock() {
            super();
        }
        public VoidFuncBlock(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class ReturnFuncBlock  extends ArrayList<Ast> implements Ast {
        public ReturnFuncBlock() {
            super();
        }

        public ReturnFuncBlock(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class ProgramBlock  extends ArrayList<Ast> implements Ast {
        public ProgramBlock() {
            super();
        }
        public ProgramBlock(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    //Program is a list of blocks
    class BlockList  extends ArrayList<Ast> implements Ast {
        public BlockList() {
            super();
        }
        public BlockList(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
}
