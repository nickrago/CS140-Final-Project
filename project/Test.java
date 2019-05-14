package project;

public class Test {
	public static void main(String[] args)
	{
		Instruction one = new Instruction((byte) 00101011, 5);
		System.out.println(Instruction.noArgument(one));
		Instruction.checkParity(one);
		Instruction two = new Instruction((byte) 01010110, 15);
		System.out.println(Instruction.noArgument(two));
		Instruction.checkParity(two);
		Instruction three = new Instruction((byte) 01010111, 8);
		System.out.println(Instruction.noArgument(three));
		//Instruction.checkParity(three); throws error properly
		System.out.println(Instruction.numOnes(12));
		System.out.println(Instruction.numOnes(7));
	}
}
