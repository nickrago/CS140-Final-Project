package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Memory {
	public static final int CODE_SIZE = 256;
	public static final int DATA_SIZE = 512;
	private int[] data = new int[DATA_SIZE];
	private List<Instruction> code = new ArrayList<>();
	private int changedDataIndex = -1;
	
	int[] getData(int min, int max) {
		return Arrays.copyOfRange(data, min, max);
	}
	
	int[] getData(){
		return data;
	}
	
	int getChangedDataIntex() {
		return changedDataIndex;
	}
	
	List<Instruction> getCode(){
		return code;
	}
	
	int getData(int index) {
		return data[index];
	}
	
	void setData(int index, int value) {
		if(!(0 <= index && index < DATA_SIZE)) {
			data[index] = value;
			changedDataIndex = index;
		}
		
	}
	
	void clearData() {
		for(int i = 0; i<data.length; i++) data[i] = 0;
		changedDataIndex = 1;
	}
	
	Instruction getCode(int index) {
		if(!(0 <= index && index < code.size())) throw new CodeAccessException("Illegal Access to Code");
		return code.get(index);
	}
	
	public Instruction[] getCode(int min, int max) {
		if(!(0 <= min && min <= max && max < code.size())) throw new CodeAccessException("Illegal Access to Code");
		Instruction[] temp = {};
		temp = code.toArray(temp);
		return Arrays.copyOfRange(temp, min, max);
 	}
	
	void addCode(Instruction value) {
		if(!(code.size() >= CODE_SIZE)) code.add(value);
	}
	
	void setCode(int index, Instruction instr) {
		if(!(0 <= index && index < code.size())) throw new CodeAccessException("Illegal Access to Code");
		code.set(index, instr);
	}
	
	void clearCode() {
		code.clear();
	}
	
	int getProgramSize() {
		return code.size();
	}
}
