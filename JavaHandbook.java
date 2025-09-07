import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaHandbook {

    public static void main(String[] args) {
        // --- 1. Lists (ArrayList) ---
        // A dynamic array that can grow and shrink in size.
        System.out.println("--- Lists (ArrayList) ---");

        // Initialization: Create an empty list of Strings.
        List<String> fruits = new ArrayList<String>();
        
        // Usage: Adding elements to the list.
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        
        // Usage: Accessing an element by index.
        System.out.println("First fruit: " + fruits.get(0));

        // Usage: Removing an element by value.
        fruits.remove("Banana");
        
        // Usage: Checking the size of the list.
        System.out.println("Current list size: " + fruits.size());

        // Usage: Iterating through the list.
        System.out.println("All fruits in the list:");
        for (String fruit : fruits) {
            System.out.println("- " + fruit);
        }
        System.out.println();
        
        // --- 2. Maps (HashMap) ---
        // Stores key-value pairs, where keys are unique.
        System.out.println("--- Maps (HashMap) ---");

        // Initialization: Create an empty map with String keys and Integer values.
        Map<String, Integer> studentScores = new HashMap<>();

        // Usage: Adding key-value pairs.
        studentScores.put("Alice", 95);
        studentScores.put("Bob", 88);
        studentScores.put("Charlie", 92);

        // Usage: Retrieving a value by key.
        System.out.println("Alice's score: " + studentScores.get("Alice"));
        
        // Usage: Checking if a key exists.
        System.out.println("Does Bob exist? " + studentScores.containsKey("Bob"));

        // Usage: Iterating over the map's keys and values.
        System.out.println("All student scores:");
        for (Map.Entry<String, Integer> entry : studentScores.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println();

        // --- 3. Strings ---
        // An immutable sequence of characters.
        System.out.println("--- Strings ---");

        // Initialization:
        String greeting = "Hello, World!";
        
        // Usage: Getting the length of a string.
        System.out.println("Length of string: " + greeting.length());
        
        // Usage: Converting case.
        System.out.println("Uppercase: " + greeting.toUpperCase());
        System.out.println("Lowercase: " + greeting.toLowerCase());

        // Usage: Finding a substring.
        System.out.println("Index of 'World': " + greeting.indexOf("World"));
        
        // Usage: Extracting a part of the string.
        System.out.println("Substring from index 7: " + greeting.substring(7));
        
        // Usage: Comparing strings (use .equals() instead of ==).
        String s1 = "Java";
        String s2 = "java";
        System.out.println("s1 equals s2? " + s1.equals(s2)); // false
        System.out.println("s1 equals s2 (ignore case)? " + s1.equalsIgnoreCase(s2)); // true
        System.out.println();
        
        // --- 4. Numbers and Math ---
        // Using common mathematical operations.
        System.out.println("--- Numbers and Math ---");

        int a = 10;
        double b = 25.0;

        // Usage: Simple arithmetic.
        System.out.println("Sum of a and b: " + (a + b));
        
        // Usage: Square root.
        System.out.println("Square root of b: " + Math.sqrt(b));
        
        // Usage: Power.
        System.out.println("a to the power of 2: " + Math.pow(a, 2));

        // Usage: Generating a random number between 0.0 and 1.0.
        System.out.println("Random number: " + Math.random());
        System.out.println();
        
        // --- 5. Printing to Console ---
        System.out.println("--- Printing to Console ---");

        // Usage: println() prints the text and moves to the next line.
        System.out.println("This is a line.");
        
        // Usage: print() prints the text without a new line.
        System.out.print("This is a partial line.");
        System.out.println(" This is the rest of the line.");
        
        // Usage: printf() for formatted printing.
        String name = "Gemini";
        int age = 1;
        System.out.printf("My name is %s and I am %d year old.\n", name, age);
        
    }
}
