package assignment;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Mike Pavia
 * 10/16/21
 * cps 231
 *List implementation with array data 
 * @param <T>
 */


public class AList<T> implements IterableListInterface<T> {

	private T[] list;

	private int numberOfEntries;

	// constant for the default capacity
	private static final int DEFAULT_CAPACITY = 25;

	// Add the new data fields
	// Set a limit on the maximum number of entries a single Bag can hold
	private static final int MAX_CAPACITY = 10000;


	// Set the default value of the integrity flag to false
	private boolean integrityOK = false;

	// Step 2.2
	private void checkCapacity(int capacity) {
		if (capacity > MAX_CAPACITY) {
			// Throw a runtime exception
			throw new IllegalStateException("Attempt to create a bag whose capacity exceeds the allowed maximum.");
		}
	}

	/**
	 * Throw an exception if the integrity flag is false
	 */
	private void checkIntegrity() {
		if (!this.integrityOK) {
			// Throw a runtime exception
			throw new IllegalStateException("The Bag object is corrupt.");
		}
	}

	/**
	 * Constructor that takes the capacity as an argument
	 * @param capacity
	 */
	public AList(int capacity) {
		// Add security update to the constructor
		this.integrityOK = false;


		// check that the desired capacity is below the max allowed
		checkCapacity(capacity);


		//  the cast is safe since the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] data = (T[])(new Object[capacity]);
		this.list = data;

		// initialize the number of entries
		this.numberOfEntries = 0;

		// set integrityOK to true
		// Assert: Integrity is OK
		this.integrityOK = true;

	}

	/**
	 * No-Arg Constructor
	 */
	public AList() {
		this(DEFAULT_CAPACITY);
	}

	private boolean isFull() {
		return this.numberOfEntries >= this.list.length;
	}



	private void doubleCapacity() {

		int newLength = 2 * list.length;
		checkCapacity(newLength);


		//  Create a new container based on the actual number of objects in the Bag.
		// OK to suppress the warning since the new array is filled with null values
		@SuppressWarnings("unchecked")
		T[] data = (T[])(new Object[newLength]);

		//  Copy the values from bag to result
		for (int i=0; i<this.numberOfEntries; i++) {

			data[i] = list[i]; 
		}

		// Copy the reference back to our bag data.
		this.list = data;

	} // end doubleCapacity



	@Override
	public void add(T newEntry) {
		// Update for integrity security
		this.checkIntegrity();

		// Check for array full
		if (this.isFull()) {
			doubleCapacity();
		} // end if


		// Assertion: result is true and there is room in the array
		// add the new object at index numberOfEntries
		this.list[this.numberOfEntries] = newEntry;

		// increment numberOfEntries
		this.numberOfEntries++;
	}

	@Override
	public void add(int newPosition, T newEntry) {
		// check the bounds of newPosition
		if (newPosition < 0 || newPosition > this.getLength()) {
			throw new IndexOutOfBoundsException();
		}

		// check integrity since we need to access the array
		this.checkIntegrity();


		// check the capacity
		if (this.isFull()) {
			doubleCapacity();
		} 


		// make room for the new entry in the array.
		for (int dst=this.numberOfEntries; dst>newPosition; dst--) {
			this.list[dst] = this.list[dst-1];
		}

		// add the new entry here.
		this.list[newPosition] = newEntry;

		// Increment number of entries
		this.numberOfEntries++;

	}

	@Override
	public T remove(int givenPosition) {

		checkIntegrity();

		if ((givenPosition >= 0) && (givenPosition <= getLength())) {
			T result = list[givenPosition];

			if(givenPosition < numberOfEntries) {
				removeGap(givenPosition);

			}
			list[numberOfEntries] = null;
			numberOfEntries--;
			return result;
		}else throw new IndexOutOfBoundsException("Can't remove entry at that index.");



		//		T result = null;	
		//		
		//		if (givenPosition >= 0 || givenPosition > getLength()-1) {
		//			throw new IndexOutOfBoundsException("Can't remove entry at that index.");
		//		}
		//		else if ((givenPosition >= 0) && (givenPosition <= numberOfEntries)) {
		//		    result = list[givenPosition];
		//			numberOfEntries--;
		//			return result;
		//		}
		//		
	}


	@Override
	public void clear() {
		//this.list = null;
		this.numberOfEntries = 0;


	}

	@Override
	public T replace(int givenPosition, T newEntry) {
		if ((givenPosition >= 0) && (givenPosition <= numberOfEntries)) {
			T desiredEntry = this.getEntry(givenPosition);
			T originalEntry = desiredEntry;
			list[givenPosition] = newEntry;
			return originalEntry;

		}else 
			throw new IndexOutOfBoundsException("Index out of bounds.");
	} // end replace 

	@Override
	public T getEntry(int givenPosition) {
		// core of this method.		
		T result = this.list[givenPosition];
		return result;
	}

	@Override
	public T[] toArray() {
		// Update for integrity security
		this.checkIntegrity();

		@SuppressWarnings("unchecked")
		T[] result = (T[])(new Object[this.numberOfEntries]);

		// Copy the values from bag to result
		for (int i=0; i<result.length; i++) {

			result[i] = list[i]; 
		}

		// return the result
		return result;
	} // end toArray

	@Override
	public boolean contains(T anEntry) {


		// Check integrity first
		this.checkIntegrity();

		// Loop through the array and compare anEntry with each item
		boolean found = false;
		for (int i=0; (!found && i<this.numberOfEntries); i++) {
			found = anEntry.equals(this.list[i]);
		}

		// return the count
		return found;


	} // end contains

	@Override
	public int getLength() {
		return this.numberOfEntries;
	}

	@Override
	public boolean isEmpty() {
		return (this.numberOfEntries==0);

	} 

	private void removeGap (int givenPosition) {
		int removedIndex = givenPosition;
		for(int index = removedIndex; index < numberOfEntries; index++) {
			list[index] = list[index + 1];
		}
	} // end remove gap

	@Override
	public Iterator<T> iterator() {
		return new InnerIterator();
		// tried to implement iterator for InnerIterator and it gave errors to methods not sure why.
	}	


	private class InnerIterator implements Iterator<T>  {



		//private IterableListInterface<T> list;	
		
		// variable to hold the position of the next element in the list 
		private int nextPosition;
		// needed for the remove method to see if the next element was found 
		private boolean wasNextCalled;

		/**
		 * constructor for the innerIterator
		 */
		private InnerIterator() {
			nextPosition=0;
			wasNextCalled = false;
		}// end default constructor.
		
		
		

		/**
		 * checks to see list has a next vale and returns true if it does and false if it does not.
		 * @return
		 */
		public boolean hasNext() {

			if (nextPosition < AList.this.getLength()) {
				return true;
			}else return false;

		} // end of has next 



		/**
		 * returns next element in the iteration
		 * @return
		 */
		public T next() {
			if (hasNext()) {
				wasNextCalled = true;
				nextPosition++;
				return AList.this.getEntry(nextPosition-1);
			}else throw new NoSuchElementException("illegal call to next() itterator is after end of list");

		}// end of next
		
		/**
		 * removes the last element called by this iterator
		 */
		public void remove() {
			if (wasNextCalled) {
				AList.this.remove(nextPosition-1);
				nextPosition--;
				wasNextCalled = false;
			}else throw new IllegalStateException("Illegal call to remove next() was not called");
		}// end of remove 




	}// end of inner class InnerIterator









}