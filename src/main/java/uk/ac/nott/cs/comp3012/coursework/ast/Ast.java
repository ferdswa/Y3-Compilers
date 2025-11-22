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
        //types
        record typeAtom(String t) implements Atom {

        }
    }
    class IntNum extends ArrayList<Ast> implements Ast {
        public IntNum() {super();}
        public IntNum(Collection<?extends Ast> elems) {super(elems);}
    }

}
