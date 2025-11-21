package uk.ac.nott.cs.comp3012.coursework.ast;

import uk.ac.nott.cs.comp3012.coursework.NottscriptVisitor;

/**
 * Base interface type for all AST classes. Modify it, delete it, or do whatever you want with it.
 */
public interface Ast {
    interface Atom extends Ast{
        record numAtom(int i) implements Atom {

        }
        record strAtom(String s) implements Atom {

        }
    }

}
