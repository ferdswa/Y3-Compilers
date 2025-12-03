package uk.ac.nott.cs.comp3012.coursework;

import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.tam.TamGenerator;
import uk.ac.nott.cs.comp3012.coursework.tam.TamInstruction;

public class Backend {
    public byte[] runBackend(Ast program){
        TamGenerator tamGenerator = new TamGenerator();
        TamInstruction.InstructionList instructionList = (TamInstruction.InstructionList) tamGenerator.visitProgram((Ast.Units) program);
        System.out.println(instructionList.toString());
        return instructionList.toByteArray();
    }
}
