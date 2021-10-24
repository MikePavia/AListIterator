package assignment;
import java.util.Iterator;

/**
 * CPS231 - Spring 2021
 * 
 * Internal Iterator Test Program
 * 
 * @author Adam Divelbiss
 *
 */
public class InternalIteratorTests {
	public static void main(String[] args) 
	{
		testIteratorOperations();
		testIllegalOps();
		System.out.println("\n\nDone.");
	}  // end main

	public static void testIteratorOperations()
	{
		System.out.println("\n======================================================\n");
		System.out.println("Testing add to end to create the list Jess, Jim, Josh");
		IterableListInterface<String> nameList = new AList<>();
		nameList.add("Jess");
		nameList.add("Jim");
		nameList.add("Josh");
		displayList(nameList);
		System.out.println("\n------------------------\n");

		System.out.println("Testing ListIterator's methods hasNext, next:");
		Iterator<String> traverse = nameList.iterator();

		System.out.printf("%-16s %-10s %s\n","hasNext",			traverse.hasNext(),			"should be true");
		System.out.printf("%-16s %-10s %s\n","next",			traverse.next(),			"should be Jess");
		System.out.println();
		
		System.out.println("\n------------------------\n");

		System.out.println("Testing add to end to create the list \n"
				+ "15 25 35 45 55 65 75 85 95");
		IterableListInterface<String> myList = new AList<>();

		myList.add("15");
		myList.add("25");
		myList.add("35");
		myList.add("45");
		myList.add("55");
		myList.add("65");
		myList.add("75");
		myList.add("85");
		myList.add("95");
		
		System.out.println("\n\nUsing ADT list operations, the list contains ");
		displayList(myList);
		
		System.out.println("\n------------------------\n");
		System.out.println("Testing ListIterator's hasNext and next methods:");

		System.out.println("\nList should contain\n15 25 35 45 55 65 75 85 95");
		System.out.println("\nUsing ADT list operations, the list contains ");
		displayList(myList);

		System.out.println("\nUsing ListIterator methods, the list contains");
		Iterator<String> myIterator = myList.iterator();
		while (myIterator.hasNext()) {
			System.out.print(myIterator.next() + " ");
		}
		System.out.println();
		System.out.println("\n------------------------\n");

		myIterator = myList.iterator();
		System.out.println("Removing current entry : " + myIterator.next() + " should be 15");
		myIterator.remove();  // Remove entry 1

		System.out.println("\nList should contain\n25 35 45 55 65 75 85 95");
		System.out.println("\nUsing ADT list operations, the list contains ");
		displayList(myList);
		System.out.println("\n------------------------\n");

		System.out.println("Removing current entry: " + myIterator.next() + " should be 25");
		myIterator.remove();

		System.out.println("\nList should contain\n35 45 55 65 75 85 95");
		System.out.println("\nUsing ADT list operations, the list contains ");
		displayList(myList);
		System.out.println("\n------------------------\n");

		// Advance twice
		System.out.println("Skipping over " + myIterator.next() + " should be 35");
		System.out.println();
		System.out.println("Removing current entry: " + myIterator.next() + " should be 45");
		myIterator.remove();

		System.out.println("\nList should contain\n35 55 65 75 85 95");
		System.out.println("\nUsing ADT list operations, the list contains ");
		displayList(myList);
		System.out.println("\n------------------------\n");

		// Advance 3 times
		System.out.println("Skipping over " + myIterator.next() + " should be 55");
		System.out.println("Skipping over " + myIterator.next() + " should be 65");
		System.out.println("Skipping over " + myIterator.next() + " should be 75");
		System.out.println();
		System.out.println("Removing current entry: " + myIterator.next() + " should be 85");
		myIterator.remove();

		System.out.println("\nList should contain\n35 55 65 75 95");
		System.out.println("\nUsing ADT list operations, the list contains ");
		displayList(myList);
		System.out.println("\n------------------------\n");

		System.out.println("next() should return 95 : " + myIterator.next());
		System.out.println();
		System.out.println("hasNext() should return false : " + myIterator.hasNext());		

		System.out.println("\n------------------------\n");
		System.out.println("The iterator is at the beginning of the list.\n" +
				"Testing Iterator's hasNext and next methods\n" +
				"by traversing forward:\n");

		System.out.println("Using Iterator methods, the list contains");
		Iterator<String> yourIterator = myList.iterator();

		while (yourIterator.hasNext()) {
			System.out.print(yourIterator.next() + " ");
		}
		System.out.println();
		
		System.out.println("\nUsing ADT list operations, the list contains ");
		displayList(myList);
		System.out.println("\n------------------------\n");
	} // end testIteratorOperations

	public static void testIllegalOps()
	{
		System.out.println("\n======================================================\n");
		System.out.println("\nTesting sequences of operations on a new list:\n");

		IterableListInterface<String> myList = new AList<String>();

		myList.add("Jamie");
		myList.add("Doug");
		myList.add("Jill");

		System.out.println("List should contain\nJamie Doug Jill \n ");
		System.out.println("List actually contains");
		displayList(myList);

		System.out.println();

		System.out.print("\nThe following sequence is illegal and causes an exception: ");

		// Choose one sequence to test; disable the others
		System.out.println("next, remove, remove\n");
		System.out.println("GETTING AN EXCEPTION ON THE NEXT LINE IS A GOOD THING.");
		Iterator<String> traverse = myList.iterator();
		traverse.next();
		traverse.remove();
		traverse.remove();


		System.out.println("\n======================================================\n");
	} // end testIllegalOps

	public static void displayList(ListInterface<String> aList)
	{
		int numberOfEntries = aList.getLength();
		for (int position = 0; position < numberOfEntries; ++position)
			System.out.print(aList.getEntry(position) + " ");
		System.out.println();
	}  // end displayList
}
