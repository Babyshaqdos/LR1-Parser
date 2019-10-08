package ilstu.edu;

import java.util.Scanner;
import java.util.Stack;

public class LR1 {
	Scanner scan = new Scanner(System.in);
	String input = scan.nextLine();
	String checkType;
	int index;
	String holder ="";
	Stack stack = new Stack();
	String[] terms = {"n", "+", "-","*","/","(",")","$"};
	String stacking ="";
	
	private void parseE() {
		switch(checkType) {
		case "(":
			stacking = "[" + checkType + ": 4]";
			System.out.println(stacking + input.substring(index--, index));
		default:
			for(int i =0; i<terms.length; i++) {
				if(checkType.contentEquals(terms[i])) {
					
				}
			}
			stacking = "[" + checkType + ":5]";
			System.out.println(stacking + input.substring(index--, index));
		}
	}
	
	private void parseT() {
		boolean check = false;
		for(int i=0; i<terms.length; i++) {
			if(checkType.contentEquals(terms[i])) {
				check = true;
			}
		}
		if(!check) {
			stacking += "[T:2]";
			//System.out.println(stacking);
			parseE();
		}
		else {
			stacking += "[T:9]";
			//System.out.println(stacking);
		}
	}
	private void parseF() {
		boolean check = false;
		for(int i=0; i<terms.length; i++) {
			if(checkType.contentEquals(terms[i])) {
				check = true;
			}
		}
		if(!check) {
			stacking +="[F:3]";
			parseT();
			//System.out.println(stacking);
		}
		else {
			stacking += "[F:10]";
			//System.out.println(stacking);
		}
	}

	public static void main(String[] args) {
		LR1 parser = new LR1();
		parser.readExpression();
	}
	public void readExpression() {
		input += "$";
		for(int i=0; i<input.length();i++) {
			index = i+1;
			checkType = input.substring(i,index);
			if(checkTerminal(checkType)) {
				if(!checkTerminal(holder)) {
					checkType = holder;
					//stack.push(checkType);
					System.out.println(stacking + input);
					parseE();
				}
			}
			else {
				if(checkTerminal(holder)) {
					holder =checkType;
				}
				else {
					holder += checkType;
				}
			}
			
		}
		
	}
	
	private void testingExpression() {
		
	}
	
	private boolean checkTerminal(String checkType) {
		for(int i=0; i<terms.length; i++) {
			if(checkType.contentEquals(terms[i])) {
				return true;
			}
		}
		return false;
	}
	
}
