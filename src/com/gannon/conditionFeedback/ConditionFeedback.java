package com.gannon.conditionFeedback;

import java.util.ArrayList;

import com.gannon.ASM.BytecodeClassGenerator.BClassGenerator;
import com.gannon.ASM.BytecodeComponent.BBlock;
import com.gannon.ASM.BytecodeComponent.BClass;
import com.gannon.ASM.BytecodeComponent.BMethod;
import com.gannon.Main.InterfaceAPISingleton;
import com.gannon.jvm.instructions.BInstruction;

public class ConditionFeedback {
	
	public static void main(String [] args) {

		BClassGenerator clasGen = new BClassGenerator(
				"Hello.class");
		clasGen.getBClass();
		BClass loadedClass = clasGen.getbFactory().getBClass();
		InterfaceAPISingleton.getInstance().setbClass(loadedClass);
		
		BMethod selectedMethod = loadedClass.getMethods().get(3);
		
		ArrayList<BBlock> blockList = selectedMethod.getBlockList();
		
		for (BBlock block : blockList) {
			
			for (BInstruction instr : block.getInstructions()) {
				instr.execute(null);
				
			}
		}
	}

}
