package uk.ac.nott.cs.comp3012.coursework;

import uk.ac.nott.cs.comp3012.coursework.ast.Ast;

/**
 * Interface for any class that can walk an AST.
 *
 * @param <T> type that the visit methods should return
 */
public interface AstVisitor<T> {
    T visit(Ast ast);

}
