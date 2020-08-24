import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {


    private int N;

    private Node front;

    private Node end;


    private class Node {

        private Item item;

        private Node next;

        private Node prev;

    }


    /**

     * Initializes an empty Deque.

     */

    public Deque() {                          // construct an empty deque

        N = 0;

        front = null;

        end = null;

    }


    /**

     *

     * @return whether the Deque is empty

     */

    public boolean isEmpty() {                // is the deque empty?

        // cannot use front == null to check whether the Deque is Empty.

        // Because when the Deque is empty and you add the first item into it. before the if statement

        // you have already let front.item = item. So it's not empty now.

        // return front == null;

        return N == 0;

    }


    /**

     * return the number of items in the Deque

     *

     * @return the number of items in the Deque

     */

    public int size() {                       // return the number of items on the deque

        return N;

    }


    /**

     * add item into the front of the Deque

     *

     * @param item

     * @throws NullPointerException if the input item is null

     */

    public void addFirst(Item item) {         // insert the item at the front

        if (item == null)

            throw new NullPointerException();

        Node newFront = front;

        front = new Node();

        front.prev = null;

        front.item = item;

        if (isEmpty()) {

            front.next = null;

            end = front;

        } else {

            front.next = newFront;

            newFront.prev = front;

        }

        N++;

    }


    public void addLast(Item item) {           // insert the item at the end

        if (item == null)

            throw new NullPointerException();

        Node newEnd = end;

        end = new Node();

        end.next = null;

        end.item = item;

        if (isEmpty()) {

            end.prev = null;

            front = end;

        } else {

            newEnd.next = end;

            end.prev = newEnd;

        }

        N++;

    }


    public Item removeFirst() {               // delete and return the item at the front

        if (isEmpty())

            throw new NoSuchElementException();

        Item item = front.item;

        // Should consider if there are just one node in the Linkedlist

        if(N == 1){

            front = null;

            end = null;

        }else{

            front = front.next;

            front.prev = null;

        }

        N--;

        return item;

    }


    public Item removeLast() {                // delete and return the item at the end

        if (isEmpty())

            throw new NoSuchElementException();

        Item item = end.item;

        if(N == 1){

            front = null;

            end = null;

        }else{

            end = end.prev;

            end.next = null;

        }

        N--;

        return item;

    }


    /**

     * Returns an iterator to this stack that iterates through the items in LIFO order.

     *

     * @return an iterator to this stack that iterates through the items in LIFO order.

     */

    public Iterator<Item> iterator() {        // return an iterator over items in order from front to end

        return new ListIterator();

    }


    // an iterator, doesn't implement remove() since it's optional

    private class ListIterator implements Iterator<Item> {

        private Node current = front;


        public boolean hasNext() {

            return current != null;

        }


        public Item next() {

            // check whether the current is null, not current->next is null!

            if (current == null)

                throw new NoSuchElementException();

            Item item = current.item;

            current = current.next;

            return item;

        }


        //

        public void remove() {

            throw new UnsupportedOperationException();

        } 

    }
    public static void main(String[] args) {
    	
    }
}