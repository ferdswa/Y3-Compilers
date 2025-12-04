package uk.ac.nott.cs.comp3012.coursework.tam;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;

public interface TamInstruction {

    /**
     * Represent this instruction as an array of bytes in TAM bytecode format.
     *
     * @return the bytes
     */
    byte[] toByteArray();

    int size();

    record Instruction(TamOpcode op, TamRegister r, int n, int d) implements TamInstruction {

        public byte[] toByteArray() {
            return new byte[]{(byte) ((op.value << 4) | r.value), (byte) n,
                    (byte) ((d & 0xff00) >>> 8), (byte) (d & 0xff),};
        }

        @Override
        public int size() {
            return 1;
        }
    }

    final class InstructionList extends ArrayList<Instruction> implements TamInstruction {

        public InstructionList() {
            super();
        }

        public InstructionList(Collection<Instruction> elems) {
            super(elems);
        }

        @Override
        public byte[] toByteArray() {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (Instruction instr : this) {
                out.writeBytes(instr.toByteArray());
            }
            return out.toByteArray();
        }

        @Override
        public int size() {
            int size = 0;
            for (Instruction instr : this) {
                size += instr.size();
            }
            return size;
        }
    }

}
