package ilstu.edu;

import java.util.Scanner;
import java.util.Stack;

public class LR1fixing {

	Scanner keyboard = new Scanner(System.in);
	String input = keyboard.nextLine();
	int state =0;
	Stack<String> stack = new Stack<String>();
	String[] terms = {"n", "+", "-","*","/","(",")","$"};
	String checkType;
	String stacking ="[-:0]";
	stack.push(stacking);
	String holder;
	int index;
	public static void main(String[] args) {
		LR1fixing parser = new LR1fixing();
		parser.readExpression();
	}
	public void parseE(String checkType) {
		switch(state) {
		case 1:
			stack.pop();
			stacking = "[E:" + state + "]";
			stack.push(stacking);
			System.out.println(stack + input.substring(index, input.length()));
			state = 0;
			break;
		case 8:
			stacking = "[E:" + state + "]";
			stack.push(stacking);
			System.out.println(stack + input.substring(index, input.length()));
			switch(checkType) {
			case "+":
				state = 6;
			case "-":
				state = 6;
			case ")":
				state = 11;
			}
		}
	}

	public void parseT(String checkType) {
		switch(state) {
		case 2:
			stack.pop();
			String stacking = "[T:" + state + "]";
			stack.push(stacking);
			System.out.println(stack + input.substring(index, input.length()));
			state = 1;
			parseE(checkType);
		case 9:
			stacking = "[T:" + state + "]";
			stack.push(stacking);
			System.out.println(stack + input.substring(index, input.length()));
			state = 7;

		}

	}
	public void parseF(String checkType) {
		if(state == 5){
			state = 3;
			stacking = "[F:" + state + "]";
			stack.pop();
			stack.push(stacking);
			System.out.println(stack + input.substring(index, input.length()));
			state = 2;
			parseT(checkType);
		}
		else {
			switch(checkType) {
			case "+":
				state = 6;
				stacking = "[" + checkType + ":" + state + "]";
				stack.push(stacking);
				System.out.println(stack + input.substring(index, input.length()));
			case "-":
				state = 6;
				stacking = "[" + checkType + ":" + state + "]";
				stack.push(stacking);
				System.out.println(stack + input.substring(index, input.length()));
			case "*":
				state = 7;
				stacking = "[" + checkType + ":" + state + "]";
				stack.push(stacking);
				System.out.println(stack + input.substring(index, input.length()));
				parseT(checkType);
			case"/":
				state = 7;
				stacking = "[" + checkType + ":" + state + "]";
				stack.push(stacking);
				System.out.println(stack + input.substring(index, input.length()));
				parseT(checkType);
			default:
				state = 0;
			}
		}
	}

	public void readExpression() {
		for(int i = 0; i<input.length(); i++) {
			index = i+1;
			String checkType = input.substring(i, index);
			//System.out.println(checkType);
			if(checkTerminal(checkType)) {
				if(state == 0) {
					state = 4;
					stacking += "[" + checkType + ":" + state + "]";
					stack.push(stacking);
					System.out.println(stack + input.substring(index, input.length()));
				}
				else {
					stacking += "[" + holder + ":" + state + "]";
					stack.push(stacking);
					System.out.println(stack + checkType +input.substring(index, input.length()));
					parseF(holder);
					holder = 0;
					parseF(checkType);
				}
			}
			else {
				state = 5;
				holder += checkType;
			}
		}
	}

	private boolean checkTerminal(String checkType) {
		for(int i =0; i < terms.length; i++) {
			if(checkType.contentEquals(terms[i])) {
				return true;
				}
			}
		return false;
		}

}
