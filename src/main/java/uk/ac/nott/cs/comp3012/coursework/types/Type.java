package uk.ac.nott.cs.comp3012.coursework.types;

import java.util.List;

public sealed interface Type permits Type.BaseType, Type.ArrayType {

    enum BaseType implements Type {
        Number, Logical, Character, OK
    }

    record ArrayType(Type base, List<Integer> dims) implements Type {

    }
}
