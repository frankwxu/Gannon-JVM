package com.gannon.asm.classgenerator;

import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;

import com.gannon.asm.components.BClass;

public class BClassGenerator {
	/**
	 * This method reads the .class file and converts it to Bytecode class, i.e., BClass
	 * The bytecode class is accessible in bFactory.
	 */
	public static BClass getBClass(String className) {
		ClassFileVisitor cp = new ClassFileVisitor();
		ClassReader cr = null;
		InputStream in = BClassGenerator.class.getResourceAsStream(className);

		try {
			cr = new ClassReader(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		cr.accept(cp, 0);

		return cp.getMyClass();
	}

}
