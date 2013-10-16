package com.gannon.asm.components;

/**
 * This class is a part of data structure created to organize the information
 * about the class being visited.
 * This Class will have the information MAXSTACK Alocated and MAXLOCAL Variables
 *
 *
 * @param maxStack
 *            Max Stack Value for method
 * @param maxLocal
 *            Max Local Variables for method
 * **/
public class BStackMaxLocals {
    private int maxStack;
    private int maxLocal;

	public BStackMaxLocals() {
		super();
	}

	public BStackMaxLocals(int maxStack, int maxLocal) {
		super();
		this.maxStack = maxStack;
		this.maxLocal = maxLocal;
	}

	public int getMaxStack() {
		return maxStack;
	}

	public void setMaxStack(int maxStack) {
		this.maxStack = maxStack;
	}

	public int getMaxLocal() {
		return maxLocal;
	}

	public void setMaxLocal(int maxLocal) {
		this.maxLocal = maxLocal;
	}

	/**Display the MAXSTACK and MAXLOCAL**/
	public void display(){
		System.out.println("Stack="+ maxStack + "  Locals="+ maxLocal + "  Args_size=");
	}
}
