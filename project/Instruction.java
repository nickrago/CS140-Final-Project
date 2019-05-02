package project;

import static java.util.Map.entry;

import java.util.Map;
import java.util.Set;

public class Instruction {
	byte opcode;
	int arg;
	
	public Instruction(byte o, int a) {
		this.opcode = o;
		this.arg = a;
	}
	
	public static boolean noArgument(Instruction instr) {
		return (instr.opcode < 24);
	}
	
	static int numOnes(int k) {
		String convertedK = Integer.toUnsignedString(k,2);
		int retVal = 0;
		for(int i = 0; i<convertedK.length(); i++) if(convertedK.substring(i,i+1).equals("1")) retVal++;
		return retVal;
	}
	
	static void checkParity(Instruction instr) {
		if((numOnes(instr.opcode) % 2) != 0) throw new ParityCheckException("This instruction is corrupted");
	}
	
	public static final Map<Integer, String> MNEMONICS = Map.ofEntries (
			entry(0, "NOP"), entry(1, "NOT"), entry(2, "HALT"), entry(3, "JUMP"), entry(4, "JMPZ"), entry(5, "LOD"), entry(6, "STO"), entry(7, "AND"), entry(8, "CMPL"), entry(9, "CMPZ"), entry(10, "ADD"), entry(11, "SUB"), entry(12, "MUL"), entry(13, "DIV")
		);
	public static final Map<String, Integer> OPCODES = Map.ofEntries (
			entry("NOP", 0), entry("NOT", 1), entry("HALT", 2), entry("JUMP", 3), entry("JMPZ", 4), entry("LOD", 5), entry("STO", 6), entry("AND", 7), entry("CMPL", 8), entry("CMPZ", 9), entry("ADD", 10), entry("SUB", 11), entry("MUL", 12), entry("DIV", 13)
	);
	public static final Set<String> JMP_MNEMONICS = Set.of("JUMP", "JMPZ");
	public static final Set<String> NO_ARG_MNEMONICS = Set.of("NOP", "NOT", "HALT"); 
	public static final Set<String> IND_MNEMONICS = Set.of("STO", "CMPL", "CMPZ");
	public static final Set<String> IMM_IND_MNEMONICS = Set.of("LOD", "ADD", "SUB", "MUL", "DIV", "AND");
	
	public String getText() {
		StringBuilder build = new StringBuilder();
		build.append(MNEMONICS.get(opcode/8));
		build.append("  ");
		int flags = opcode & 6;
		if(flags == 2) build.append('M');
		if(flags == 4) build.append('N');
		if(flags == 6) build.append('J');
		build.append(Integer.toString(arg, 16));
		return build.toString().toUpperCase();
	}
	
	public String getBinHex() {
		StringBuilder build = new StringBuilder();
		String s = "00000000" + Integer.toString(opcode,2);
		build.append(s.substring(s.length()-8));
		build.append("  ");
		build.append(Integer.toHexString(arg));
		return build.toString().toUpperCase();
	}
	
}
