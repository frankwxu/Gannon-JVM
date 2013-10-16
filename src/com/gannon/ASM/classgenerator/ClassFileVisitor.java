package com.gannon.asm.classgenerator;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.gannon.asm.components.BClass;
import com.gannon.asm.components.BMethod;

public class ClassFileVisitor extends ClassVisitor {
	BClass myClass = new BClass();

	public ClassFileVisitor() {
		super(Opcodes.ASM4);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		myClass.setClassName(name);
		myClass.setSuperClass(superName);
		myClass.setVersion(version);
		myClass.setClassAccess(access);

	}

	@Override
	public void visitSource(String source, String debug) {
		myClass.setSourceFile(source);

	}

	@Override
	public void visitOuterClass(String owner, String name, String desc) {
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return null;
	}

	@Override
	public void visitAttribute(Attribute attr) {
	}

	@Override
	public void visitInnerClass(String name, String outerName,
			String innerName, int access) {
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		System.out.println(" Field " + desc + " " + name);
		return null;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		BMethod currentMethod = new BMethod(access, name, desc);
		myClass.addMethod(currentMethod);
		MethodVisitor oriMv = new ClassMethodVisitor(Opcodes.ASM4,
				currentMethod);
		//System.out.println("method signature "+desc);
		return oriMv;
	}

	@Override
	public void visitEnd() {
		for(BMethod method: myClass.getMethods()){
			method.updateJumpLabel();
		}
	}

	public BClass getMyClass() {
		return myClass;
	}

	public void setMyClass(BClass myClass) {
		this.myClass = myClass;
	}

	public void display() {

	}

}
