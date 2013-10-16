package com.gannon.asm.components;

/**
 * This class is a part of data structure created to organize the information
 * about the class being visited. This Class will have the information of method
 * local variables.
 *
 *
 * @param name
 *            Name of the Local Variable
 * @param desc
 *            Type of the variable, e.g., int, string
 * @param signature
 *            Signature of the variable
 * @param index
 *            Index of the local variable,
 *
 *            e.g., triangleType(int a,int b,int c)
 *
 * **/
public class BLocalVariable {
	private String name; //a, b, c
	private String desc; // int, int, int
	private String signature;
	private int index; //0,1,2

	public BLocalVariable() {
		super();
	}

	public BLocalVariable(String name, String desc, String signature, int index) {
		super();
		this.name = name;
		this.desc = desc;
		this.signature = signature;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public String getSignature() {
		return signature;
	}

	public int getIndex() {
		return index;
	}

	/** Display the Local Varible Details **/
	public void display() {
		System.out.println("LOCAL VARIABLE " + name + " " + desc + " "
				+ signature + " " + index);
	}

}
