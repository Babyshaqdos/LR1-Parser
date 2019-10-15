/*Nick Souligne
 * nsoulig
 * Assignment 2 IT 327
 * Copyright Nick Souligne
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class LR1numerodos {
	//Defining and declaring variables and stacks
	String subString2;
	String subString;
	Object fml = "(";
	String subString3;
	int index;
	String holder ="";
	Stack<String> printStack = new Stack<String>();
	Stack<String> opStack = new Stack<String>();
	Stack operand = new Stack();
	String[] terms = {"n", "+", "-","*","/","(",")","$"};
	String stacking ="";
	int state = 0;
	String[] symbols = {"F", "E", "T"};
	
	public static void main(String[] args) throws IOException {
		LR1numerodos LR1 = new LR1numerodos();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = reader.readLine();
		LR1.readExpression(input);


	}

	
	//This method will take the user input and check it against the grammar rules
	public void readExpression(String input) {
		input += "$";
		int index2;
		for(int i=0; i<input.length();i++) {
			index = i+1;
			index2 = index+1;
			String inputHold = input;
			inputHold += " ";
			subString = input.substring(i,index);
			subString2 = inputHold.substring(index, index2);
			subString3 = input.substring(index, input.length());

			if(!checkTerminal(subString)) {
				holder += subString;
				if(checkTerminal(subString2)) {
					parseStack(holder, subString2);
					if(state == 5) {
						if(subString2.contains("$")) {
							state = 10;
							parseStack(holder, subString2);
							}
						else {
						parseStack(holder, subString2);
						}
					}
					if (state ==3) {
						if(subString2.contains("*")) {
							state = 9;
							parseStack(holder, subString2);
							state = 7;
						}
						else {
						parseStack(holder, subString2);
						}
					}
					if(state ==2) {
						parseStack(holder, subString2);
					}
					holder ="";
					}
			}
			else {
				parseStack(subString, subString2);
			}
			}	
	}	
	//This method checks that the tokens of the input are terminal symbol or not
	private boolean checkTerminal(String checkType) {
		for(int i=0; i<terms.length; i++) {
			if(checkType.contentEquals(terms[i])) {
				return true;
			}
		}
		return false;
	}
	//This method will set the stacks and change the state depending on the token that
	//is passed in.
	public void parseStack(String checkType, String subString2) {
		switch(state) {
		case 0:
			if(checkType.equals(fml)) {
				state = 4;
				opStack.push(checkType);
				stacking = "[" + checkType + ":" + state + "]";
				printStack.push(stacking);
				System.out.println(printStack + subString3);
				break;
			}
			else {
				state = 5;
				opStack.push(checkType);
				stacking = "[" + checkType + ":" + state + "]";
				printStack.push(stacking);
				checkType = "F";
				System.out.println(printStack + subString3);
				break;
			}
		case 1:
			if(checkType =="$") {
				break;
			}
			else {
				state =6;
				operand.push(checkType);
				stacking = "[" + checkType +":" + state + "]";
				printStack.push(stacking);
				System.out.println(printStack + subString3);
				if(checkTerminal(subString2)) {
					state = 7;
				}
				else {
				state =0;
				}
				break;
			}
		case 2:
			if(subString2 == "*") {
				state = 7;
				break;
			}
			else if (subString2 == "/") {
				state = 7;
				break;
			}
			else {
				state = 1;
				checkType = "E";
				stacking = "[" + checkType + ":" + state + "]";
				printStack.pop();
				printStack.push(stacking);
				System.out.println(printStack + subString3);
				break;
			}
		case 3:
			if(subString2 == "*") {
				checkType = "T";
				state = 9;
				printStack.pop();
				stacking = "[" + checkType + ":" + state + "]";
				printStack.push(stacking);
				System.out.println(printStack + subString3);
				break;
			}
			else {
				checkType = "T";
				state = 2;
				printStack.pop();
				stacking = "[" + checkType + ":" + state + "]";
				printStack.push(stacking);
				System.out.println(printStack + subString3);
				break;
			}
		case 4:
			if(checkTerminal(checkType)) {
				state = 4;
				switch(subString2) {
				case "+":
					state = 6;
					stacking = "[" + checkType + ":" + state + "]";
					printStack.push(stacking);
					break;
				case "-":
					state = 6;
					stacking = "[" + checkType + ":" + state + "]";
					printStack.push(stacking);
					break;
				case "*":
					state = 2;
					stacking = "[" + checkType + ":" + state + "]";
					printStack.push(stacking);
					break;
				case "/":
					state = 2;
					stacking = "[" + checkType + ":" + state + "]";
					printStack.push(stacking);
					break;
				}
			}
			else {
				state = 3;
				checkType = "F";
				stacking = "[" + checkType + ":" + state + "]";
				printStack.push(stacking);
				System.out.println(printStack + subString3);
				break;
			}	
		case 5:
			if(subString2 == "$") {
				stacking = "[" + checkType + ":" + state + "]";
				printStack.push(stacking);
				state = 10;
				break;
			}
			else {
				state = 3;
				checkType = "F";
				stacking = "[" + checkType + ":" + state + "]";
				printStack.pop();
				printStack.push(stacking);
				System.out.println(printStack + subString3);
				break;
			}
		case 6:
			if(checkType == "(") {
				state = 4;
			}
			else if(subString2 == "*") {
				state =9;
				checkType = "T";
				stacking = "[" + checkType + ":" + state + "]";
				printStack.push(stacking);
				break;
			}
			else if (subString2 == "/") {
				state = 9;
				checkType = "T";
				stacking = "[" + checkType + ":" + state + "]";
				printStack.push(stacking);
				break;
			}
			else {
				checkType = "F";
				state = 3;
				break;
			}
		case 7:
			if(checkType == "(") {
				state = 4;
			}
			if(subString2 == "$") {
				state = 10;
				checkType = "F";
				stacking = "[" + checkType + ":" + state + "]";
				printStack.push(stacking);
				break;
			}
			else {
				stacking = "[" + checkType + ":" + state + "]";
				printStack.push(stacking);
				operand.push(checkType);
				System.out.println(printStack + subString3);
				state = 0;
				break;
			}
		case 8:
			if(checkType == "+") {
				state = 6;
				break;
			}
			else if (checkType == "-") {
				state = 6;
				break;
			}
			else {
				state = 11;
				break;
			}
		case 9:
			if (checkType == "*") {
				state = 7;
				break;
			}
			else if (checkType == "/") {
				state = 7;
				break;
			}
			else {
				checkType = "T";
				printStack.pop();
				stacking = "[" + checkType + ":" + state + "]";
				printStack.push(stacking);
				System.out.println(printStack + subString3);
				break;
			}
		case 10:
			checkType = "F";
			stacking = "[" + checkType +":"+state+"]";
			printStack.pop();
			printStack.push(stacking);
			System.out.println(printStack + subString3);
			state = 11;
			break;
		case 11:
			solveStack(opStack);
			break;
		}
	}

//This method will solve the stack and print the value if the expression is valid
public void solveStack(Stack opStack) {
	int value =0;
	for(int i =0; i <= operand.size(); i++) {
		String opHolder = operand.pop().toString();
		if(opHolder.contains("*")) {
			String operand = opStack.pop().toString();
			value = Integer.parseInt(operand);
			String operand2 = opStack.pop().toString();
			value *= Integer.parseInt(operand2);
			opStack.push(value);
		}
		if(opHolder.contains("/")) {
			String operand = opStack.pop().toString();
			value = Integer.parseInt(operand);
			String operand2 = opStack.pop().toString();
			value /= Integer.parseInt(operand2);
			opStack.push(value);
		}
		if(opHolder.contains("+")) {
			String operand = opStack.pop().toString();
			value = Integer.parseInt(operand);
			String operand2 = opStack.pop().toString();
			value += Integer.parseInt(operand2);
			opStack.push(value);
		}
		if(opHolder.contains("-")) {
			String operand = opStack.pop().toString();
			value = Integer.parseInt(operand);
			String operand2 = opStack.pop().toString();
			value -= Integer.parseInt(operand2);
			opStack.push(value);
		}
	}
	System.out.println("This is a valid expression, value is :" + value);
	
}
}

