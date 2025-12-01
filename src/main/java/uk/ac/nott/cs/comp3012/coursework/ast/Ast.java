package uk.ac.nott.cs.comp3012.coursework.ast;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Base interface type for all AST classes. Modify it, delete it, or do whatever you want with it.
 */
public interface Ast{
    sealed interface Atom extends Ast{
        //numbers
        record numAtom(int i) implements Atom {

        }
        record binNumAtom(int bin) implements Atom {

        }
        record octNumAtom(int oct) implements Atom {

        }
        record hexNumAtom(int hex) implements Atom {

        }
        record realAtom(float f) implements Atom {

        }
        //character literals
        record charLiteralAtom(String charLiteral) implements Atom {

        }

        //names
        record nameAtom(String name) implements Atom {

        }
        //logicals
        record logicOpAtom(String logicVal) implements Atom {

        }
        record boolAtom(boolean bool) implements Atom {

        }
        //operators
        record addSubAtom(String op) implements Atom {

        }
        record mulDivAtom(String op) implements Atom {

        }
        record relAtom(String relOp) implements Atom {

        }
        record starAtom(String ptrStar) implements Atom {

        }
        //types (Called typeSpec)
        record typeAtom(String type) implements Atom {

        }
        //statements
        record nodeAtom(String nodeType) implements Atom {

        }

    }
    class CustomTypeSpec extends ArrayList<Ast> implements Ast {
        public CustomTypeSpec() {super();}
        public CustomTypeSpec(Collection<? extends Ast> elems) {super(elems);}
    }
    class InbuiltTypeSpec extends ArrayList<Ast> implements Ast {
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

    class ArraySExpr extends ArrayList<Ast> implements Ast {
        public ArraySExpr() {
            super();
        }
        public ArraySExpr(Collection<? extends Ast> elems) {
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

    class IntSExpr extends ArrayList<Ast> implements Ast {
        public IntSExpr() {
            super();
        }
        public IntSExpr(Collection<? extends Ast> elems) {
            super(elems);
        }
    }

    class NameSExpr extends ArrayList<Ast> implements Ast {
        public NameSExpr() {
            super();
        }
        public NameSExpr(Collection<? extends Ast> elems) {
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
    class ReadParam extends ArrayList<Ast> implements Ast {
        public ReadParam() {
            super();
        }
        public ReadParam(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class ArrayIndex extends ArrayList<Ast> implements Ast {
        public ArrayIndex() {super();}
        public ArrayIndex(Collection<? extends Ast> elems) {super(elems);}
    }
    class ParamSubList extends ArrayList<Ast> implements Ast {
        public ParamSubList() {
            super();
        }
        public ParamSubList(Collection<? extends Ast> elems) {
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
    class DoParam extends ArrayList<Ast> implements Ast {
        public DoParam() {
            super();
        }
        public DoParam(Collection<? extends Ast> elems) {
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
    class ElseStmt extends ArrayList<Ast> implements Ast {
        public ElseStmt() {super();}
        public ElseStmt(Collection<? extends Ast> elems) {super(elems);}
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
    class FuncDefineParams extends ArrayList<Ast> implements Ast {
        public FuncDefineParams() {super();}
        public FuncDefineParams(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    //Blocks - A block is a list of declarations followed by a list of statements, except CustomTypeDeclBlock which only has declarations
    class CustomTypeDefUnit extends ArrayList<Ast> implements Ast {
        public CustomTypeDefUnit() {super();}
        public CustomTypeDefUnit(Collection<? extends Ast> elems) {super(elems);}
    }
    class SbrtUnit extends ArrayList<Ast> implements Ast {
        public SbrtUnit() {
            super();
        }
        public SbrtUnit(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class FuncRVoidUnit extends ArrayList<Ast> implements Ast {
        public FuncRVoidUnit() {
            super();
        }
        public FuncRVoidUnit(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class FuncRValueUnit extends ArrayList<Ast> implements Ast {
        public FuncRValueUnit() {
            super();
        }

        public FuncRValueUnit(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class ProgramUnit extends ArrayList<Ast> implements Ast {
        public ProgramUnit() {
            super();
        }
        public ProgramUnit(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    class NameUnit  extends ArrayList<Ast> implements Ast {
        public NameUnit() {
            super();
        }
        public NameUnit(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
    //Program is a list of blocks
    final class Units extends ArrayList<Ast> implements Ast {
        public Units() {
            super();
        }
        public Units(Collection<? extends Ast> elems) {
            super(elems);
        }
    }
}
