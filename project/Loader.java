package project;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;


public class Loader {
	public static String load(Machine machine, File file) throws Exception {
		if(machine == null || file == null) return null;
		int numInstr = 0;
		boolean readingCode = true;
		try (DataInputStream input = new DataInputStream(new FileInputStream(file))) {
			while(true) {
				int x = input.readInt();
				if(readingCode && (x == -1)) readingCode = false;
				else if(readingCode){
						numInstr++;
						int y = input.readInt();
						machine.addCode(new Instruction((byte)x, y));
				} 
				else {
						int z = input.readInt();
						machine.setData(x, z);
				}
			}	
		}
		catch (EOFException e) {
			return "" + numInstr;
		} catch (FileNotFoundException e1) {
			return("File " + file.getName() + " Not Found");
		} catch (Exception e) {
			throw e;
		}
			
	}
	
	public static void main(String[] args) throws Exception {
		Machine machine = new Machine(() -> System.exit(0));
		int str = Integer.parseInt(Loader.load(machine, new File("factorial.pexe")));
		
		/*
		for(int i = 0; i<str; i++) {
			System.out.println(machine.getCode(i).getText());
		}
		
		System.out.println(Arrays.toString(machine.getData(0,20)));
		 */
		
		machine.setPC(0);
		while(true) {
			System.out.println(machine.getCode(machine.getPC()).getText());
			machine.step();
			System.out.println(Arrays.toString(machine.getData(0,20)));
		}

	}
}
