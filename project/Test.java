package project;

public class Test {
	public static void main(String[] args) {
		Instruction inst = new Instruction((byte) 20, 5);
		System.out.println(Instruction.noArgument(inst));
		System.out.println(Instruction.numOnes(525));
		Instruction.checkParity(inst);
	}
}
