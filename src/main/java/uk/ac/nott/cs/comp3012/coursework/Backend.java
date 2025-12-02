package uk.ac.nott.cs.comp3012.coursework;

import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.tam.TacGenerator;
import uk.ac.nott.cs.comp3012.coursework.tam.TamInstruction;

import java.util.ArrayList;

public class Backend {
    public byte[] runBackend(Ast program){
        TacGenerator tacGenerator = new TacGenerator();
        TamInstruction.InstructionList instructionList = (TamInstruction.InstructionList) tacGenerator.visitProgram((Ast.Units) program);
        System.out.println(instructionList.toString());
        return instructionList.toByteArray();
    }
}
