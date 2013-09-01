import java.util.Iterator;
import java.util.NoSuchElementException;

/** RandomizedQueue is a generic array-implemented queue that
 * will dequeue items at random. It will also iterate at random. */
public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item[] queue;
	private int size;
	
	/** Constructor to create a new randomised queue. */
	public RandomizedQueue() {
		int defaultSize = 1;
		
		this.queue = (Item[]) new Object[defaultSize];
		this.size = 0;
	}
	
	/** Returns true if the queue is empty. */
	public boolean isEmpty() {
		return (this.size == 0);
	}
	
	/** Returns the size of the queue. */
	public int size() {
		return this.size;
	}
	
	/** Adds a new item to the queue. */
	public void enqueue(Item item) {
		if (item == null) {
			throw new NullPointerException("Cannot enqueue null objects.");
		}
		
		if (this.size == queue.length) {
			Item[] resizedQueue = (Item[]) new Object[queue.length * 2];
			
			for(int i = 0; i < queue.length; i++) {
				resizedQueue[i] = queue[i];
			}
			
			this.queue = resizedQueue;
		}
		
		queue[size] = item;
		
		this.size++;
	}
	
	/** Removes and returns an item at random from the queue. */
	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException("Queue is currently empty.");
		}
		
		int rand = getRandomOccupiedIndex();
		Item dequeued = queue[rand];
		
		this.size--;
		
		queue[rand] = queue[this.size];
		queue[this.size] = null;
		
		if (this.queue.length > 4 && this.size <= queue.length / 4) {
			Item [] resizedQueue = (Item[]) new Object[queue.length / 2];
			
			for(int i = 0; i < this.size; i++) {
				resizedQueue[i] = queue[i];
			}
			
			this.queue = resizedQueue;
		}
		
		return dequeued;
	}
	
	/** Returns an item at random without deleting it. */
	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException("Queue is currently empty.");
		}
		
		return queue[getRandomOccupiedIndex()];
	}
	
	/** Returns an integer of a random index which is not null. */
	private int getRandomOccupiedIndex() {
		while(true) {
			int rand = StdRandom.uniform(this.size);
			if(queue[rand] != null) {
				return rand;
			}
		}
	}
	
	/** Returns an iterator object to allow random iteration through queue. */
	public Iterator<Item> iterator() {
		return new ListIterator(queue, size);
	}
	
	private class ListIterator implements Iterator<Item> {
		
		private Item[] iteratorQueue;
		private int iteratorIndex = 0;
		
		public ListIterator(Item[] queue, int size) {
			
			iteratorQueue = (Item[]) new Object[size];
			
			//Copy items into iterator queue
			for(int i = 0; i < iteratorQueue.length; i++) {
				iteratorQueue[i] = queue[i];
			}
			
			//Knuth shuffle the iterator queue
			for(int j = 1; j < iteratorQueue.length; j++) {
				int swapIndex = StdRandom.uniform(j + 1);
				
				Item temp = iteratorQueue[j];
				iteratorQueue[j] = iteratorQueue[swapIndex];
				iteratorQueue[swapIndex] = temp;
			}
		}
		
		@Override
		public boolean hasNext() {
			return (iteratorIndex < iteratorQueue.length);
		}

		@Override
		public Item next() {
			if(!hasNext()) {
				throw new NoSuchElementException("No more objects to iterate through");
			}
			
			Item item = iteratorQueue[iteratorIndex];
			iteratorIndex++;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove method not supported");
		}
	}
}
