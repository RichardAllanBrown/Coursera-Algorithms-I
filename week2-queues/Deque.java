import java.util.Iterator;
import java.util.NoSuchElementException;

/** Deque is a generic double ended queue / stack hybrid where
 * new items can be added or removed from either end. */
public class Deque<Item> implements Iterable<Item> {
	
	private Node first = null;
	private Node last = null;
	private int size = 0;
	
	private class Node {
		Item item;
		Node next;
		Node previous;
	}
	
	/** Constructor that creates a new Deque */
	public Deque() { }
	
	/** Returns true when the Deque is empty */
	public boolean isEmpty() {
		return (this.size == 0);
	}
	
	/** Returns the size of the Deque */
	public int size() {
		return this.size;
	}
	
	/** Adds an item to the front of the Deque */
	public void addFirst(Item item) {
		if (item == null) {
			throw new NullPointerException("Cannot add null items");
		}
		
		//Create a new node
		Node newNode = new Node();
		newNode.item = item;
		
		this.size++;
		
		//It there is only 1 node, first and last point to it
		if (size == 1) {
			this.last = newNode;
			this.first = newNode; 
			
		} else {
			//rearrange the pointers using a temporary pointer
			Node tempFirst = this.first;
			this.first = newNode;
			newNode.next = tempFirst;
			tempFirst.previous = newNode;
		}
	}
	
	/** Adds an item to the end of the Deque */
	public void addLast(Item item) {
		if (item == null) {
			throw new NullPointerException("Cannot add null items");
		}
		
		//Create a new node
		Node newNode = new Node();
		newNode.item = item;
		
		this.size++;
		
		//If there is only 1 node, first and last point to it
		if (size == 1) {
			this.first = newNode;
			this.last = newNode;
			
		} else {
			//Insert new node at rear
			Node tempLast = this.last;
			this.last = newNode;
			newNode.previous = tempLast;
			tempLast.next = newNode;
		}
	}
	
	/** Removes the item from the front of the Deque */
	public Item removeFirst() {
		if (size() == 0) {
			throw new NoSuchElementException("Deque is empty");
		}
		
		//Fetch item to return
		Item item = this.first.item;
		
		this.size--;
		
		//Reorder pointers
		this.first = this.first.next;
		
		if (size() == 0) {
			this.last = null;
		} else {
			this.first.previous = null;
		}
		
		return item;
	}
	
	/** Returns the item from the rear of the Deque */
	public Item removeLast() {
		if (size() == 0) {
			throw new NoSuchElementException("Deque is empty");
		}
		
		//Fetch item to return
		Item item = this.last.item;
		
		this.size--;
		
		//Rearrange pointers
		this.last = this.last.previous;
		
		if (size() == 0) {
			this.first = null;
		} else {
			this.last.next = null;
		}
		
		return item;
	}
	
	/** Returns an iterator object to allow iteration through all items */
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item> {
		private Node current = first;
		
		/** Returns true if there is an item next in the deque */
		@Override
		public boolean hasNext() {
			return (current != null);
		}

		/** returns the current item and increments the pointer */
		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more objects to iterate through");
			}
			
			Item item = current.item;
			current = current.next;
			
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Iterator remove function not supported.");
		}
	}
}
