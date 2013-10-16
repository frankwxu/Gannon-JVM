package com.gannon.asm.components;

import java.util.ArrayList;

/**
 * This class is a part of data structure created to organize the information
 * about the class being visited. This Class will have the information of class.
 *
 *
 * @param fullClassName
 *            Name of the class visited
 * @param superClass
 *            Name of the super class of the class visited
 * @param sourceFile
 *            Name of the source File (.java) of the class visited.
 * @param classAccess
 *            Class access value
 * @param version
 *            Java version of the file, it is generally helpful for JVM to
 *            understand if file is executable on it or not.
 * **/
public class BClass {
	private String fullClassName;
	private String superClass;
	private String sourceFile;
	private int classAccess;
	private int version;

	/**
	 * List of method objects. It will store the method objects created for the
	 * class.
	 **/
	private ArrayList<BMethod> methodList = new ArrayList<BMethod>();

	public BClass() {
		super();
	}

	public BClass(String className) {
		super();
		this.fullClassName = className;
	}

	public String getClassName() {
		return fullClassName;
	}

	public String getSuperClass() {
		return superClass;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public int getClassAccess() {
		return classAccess;
	}

	public int getVersion() {
		return version;
	}

	public void addMethod(BMethod method) {
		methodList.add(method);
	}

	public ArrayList<BMethod> getMethods() {
		return methodList;
	}

	public BMethod getMethod(String methodName) {
		for (BMethod aMethod : methodList) {
			if (aMethod.getName().equals(methodName)) {
				return aMethod;
			}
		}
		System.out.println(methodName+" is not found: null from BClass!");
		return null;
	}

	public void setMethods(ArrayList<BMethod> methods) {
		this.methodList = methods;
	}

	public void setClassName(String className) {
		this.fullClassName = className;
	}

	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public void setClassAccess(int classAccess) {
		this.classAccess = classAccess;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Integer getIndexOf(String MethodName) {
		int returnVal = 0;
		int index = 0;
		for (BMethod aMethod : methodList) {
			if (aMethod.getName() == MethodName) {
				returnVal = index;
				break;
			}
			index++;
		}

		return returnVal;
	}

	public String getShortClassName() {
		int index=fullClassName.lastIndexOf('/');
		return fullClassName.substring(index+1);
	}
}