package uk.ac.nott.cs.comp3012.coursework;

import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.tam.TamGenerator;
import uk.ac.nott.cs.comp3012.coursework.tam.TamInstruction;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolTable;

public class Backend {
    public byte[] runBackend(Ast program, SymbolTable symbols){
        TamGenerator tamGenerator = new TamGenerator(symbols);
        TamInstruction.InstructionList instructionList = (TamInstruction.InstructionList) tamGenerator.visitProgram((Ast.Units) program);
        System.out.println(instructionList.toString());
        System.out.println(tamGenerator.getSymbolTable().getChildren().getFirst().getSymbols());
        return instructionList.toByteArray();
    }
}
