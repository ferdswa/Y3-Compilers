package uk.ac.nott.cs.comp3012.coursework.tam;

public enum TamPrimitive {
    id(0),
    not(1),
    and(2),
    or(3),
    succ(4),
    pred(5),
    neg(6),
    add(7),
    sub(8),
    mult(9),
    div(10),
    mod(11),
    lt(12),
    le(13),
    ge(14),
    gt(15),
    eq(16),
    ne(17),
    eol(18),
    eof(19),
    get(20),
    put(21),
    geteol(22),
    puteol(23),
    getint(24),
    putint(25),
    New(26),
    dispose(27);

    public final int value;

    TamPrimitive(int value) {
        this.value = value;
    }
}
