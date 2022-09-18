package hr.fer.oprpp1.custom.collections;

public class Demo {
	public static void main(String[] args) {
		SimpleHashtable<String, Integer> testTable = new SimpleHashtable<>(2);

        testTable.put("Ivana", 2);
        testTable.put("Ante", 2);
        testTable.put("Jasna", 2);
        testTable.put("Kristina", 2);
        testTable.put("Ivana", 5); // overwrites old grade for Ivana
        testTable.put("Josip", 100);

        testTable.remove("Ante");

        System.out.println(testTable.containsKey("Ante"));
        System.out.println(testTable.containsKey("Ivana"));
	}
}
