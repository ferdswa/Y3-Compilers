package uk.ac.nott.cs.comp3012.coursework;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.ast.AstBuilder;
import uk.ac.nott.cs.comp3012.coursework.tam.TacGenerator;
import uk.ac.nott.cs.comp3012.coursework.types.TypeChecker;
import uk.ac.nott.cs.comp3012.coursework.util.SymbolTable;

/**
 * Base class for a compiler.
 */
public class Compiler {

    // backend to use
    private final Backend backend;
    SymbolTable parent = new SymbolTable();
    /**
     * Construct a new compiler.
     *
     * @param backend  backend to use
     */
    public Compiler(Backend backend) {
        this.backend = backend;
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Must provide both source file and target file");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];

        Backend backend = new   Backend();
        Compiler compiler = new Compiler(backend);
        compiler.runCompiler(inputFile, outputFile);
    }

    /**
     * Compile the source program in {@code inputFile} and emit the compiled code to
     * {@code outputFile}.
     *
     * @param inputFile  file to read source from
     * @param outputFile file to write target code to
     * @throws IOException if there is an error reading or writing
     */
    public void runCompiler(String inputFile, String outputFile) throws IOException {
        StringBuilder programText = new StringBuilder();
        List<String> lines = Files.readAllLines(Path.of(inputFile));
        for(String line : lines) {
            line += ' ';
            programText.append(line);
        }

        //Build the AST
        AstBuilder astBuilder = new AstBuilder(this.parent);
        Ast.Units program = (Ast.Units) astBuilder.buildAst(programText.toString());
        parent = astBuilder.getIntSymbolTable();
        program.forEach(pUnit -> {//Check that all program units have bodies
            if(pUnit instanceof ArrayList<?>){
                if(((ArrayList<?>) pUnit).size()<3){
                    throw new RuntimeException("Program units without bodies detected");
                }
            }
        });
        for(SymbolTable puST : parent.getChildren()){//Check that program units start and end with the same ID
            AtomicInteger numberOfDifferentBlockNames = new AtomicInteger();
            puST.getSymbols().values().forEach(vName -> {
                if(vName.type.equals("unitID"))
                    numberOfDifferentBlockNames.getAndIncrement();
            });
            if(numberOfDifferentBlockNames.get()!=1){
                throw new RuntimeException("Syntax Error: Program units must start and end with the same identifier");
            }
            System.out.printf("SymbolTable %s: %s%n",puST.getSymbols().values().iterator().next().scope,puST.getSymbols());
        }
        //Ensure all types match
        TypeChecker typeChecker = new TypeChecker(parent);
        System.out.println("TypeChecker: "+typeChecker.visitProgram(program));
        TacGenerator tg = new TacGenerator();
        tg.visitMulDivOp(new Ast.Atom.mulDivAtom("*"));


        byte[] code = backend.runBackend(program);

        try (BufferedOutputStream out = new BufferedOutputStream(
            new FileOutputStream(outputFile))) {
            for (byte b : code) {
                out.write(b);
            }
        }
    }

//    /**
//     * A compiler frontend converts the source program to an IR.
//     */
//    @FunctionalInterface
//    public interface Frontend {
//        /**
//         * Generate IR for a source program.
//         *
//         * @param programText program to process
//         * @return the IR
//         */
//        Ast runFrontend(String programText);
//    }
//    /**
//     * A compiler backend converts IR of a program into bytes of machine code.
//     */
//    @FunctionalInterface
//    public interface Backend {
//
//        /**
//         * Generate machine code from an IR.
//         *
//         * @param program IR to transform
//         * @return the bytes
//         */
//        byte[] runBackend(Ast program);
//    }
}
