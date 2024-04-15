package ModuleFour;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class GroupingSymbolChecker {

    public static void main(String[] args) {
        // Check if the command-line argument is provided
        if (args.length != 1) {
            System.out.println("Usage: java GroupingSymbolChecker <source-code-file>");
            return;
        }

        // Get the file name from the command-line argument
        String fileName = args[0];
        try {
            // Call the method to check grouping symbols in the file
            if (checkGroupingSymbols(fileName)) {
                System.out.println("The grouping symbols in the file are correct.");
            } else {
                System.out.println("The grouping symbols in the file are incorrect.");
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    // Method to check grouping symbols in the file
    private static boolean checkGroupingSymbols(String fileName) throws IOException {
        try (// Create a reader to read the file
		BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			// Create a stack to keep track of opening symbols
			Stack<Character> stack = new Stack<>();

			// Initialize line number counter
			int lineNumber = 1;
			String line;
			// Read each line from the file
			while ((line = reader.readLine()) != null) {
			    // Check each character in the line
			    for (char c : line.toCharArray()) {
			        // If the character is an opening symbol, push it onto the stack
			        if (c == '(' || c == '{' || c == '[') {
			            stack.push(c);
			        }
			        // If the character is a closing symbol
			        else if (c == ')' || c == '}' || c == ']') {
			            // If the stack is empty or the closing symbol does not match the top opening symbol, report an error
			            if (stack.isEmpty() || !isMatchingPair(stack.pop(), c)) {
			                System.out.println("Error: Mismatched grouping symbols at line " + lineNumber);
			                return false;
			            }
			        }
			    }
			    // Increment line number
			    lineNumber++;
			}

			// Check if there are unmatched opening symbols remaining on the stack
			if (!stack.isEmpty()) {
			    System.out.println("Error: Unmatched grouping symbols at the end of file");
			    return false;
			}

			// Close the reader
			reader.close();
		}
        // Return true if all grouping symbols are correctly matched
        return true;
    }

    // Method to check if two symbols form a matching pair
    private static boolean isMatchingPair(char left, char right) {
        return (left == '(' && right == ')') ||
               (left == '{' && right == '}') ||
               (left == '[' && right == ']');
    }
}