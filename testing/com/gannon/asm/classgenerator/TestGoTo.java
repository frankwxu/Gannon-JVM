package com.gannon.asm.classgenerator;

public class TestGoTo {
	
	public int triangleType(int a, int b, int c) {
		return 4;
//		if ((a < b + c) && (b < a + c) && (c < a + b)) {
//			if ((a == b) && (b == c))
//				return 1;
//			else if (a != b && a != c && b != c)
//				return 2;
//			else
//				return 3;
//		} else {
//			return 4;
//		}
	}
	
	public void MethodA() {
		MethodB();
	}
	
	public void MethodB() {
		MethodC();
	}
	
	public void MethodC() {
		
		triangleType(4, 4, 4);
		
	}
	
}
