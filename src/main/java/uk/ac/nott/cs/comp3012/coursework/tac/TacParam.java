package uk.ac.nott.cs.comp3012.coursework.tac;

public sealed interface TacParam {
    record Value(int value) implements TacParam {}
    record Variable(String value) implements TacParam {}
    record Constant(int value) implements TacParam {}
    record StrConst(String value) implements TacParam {}
}
