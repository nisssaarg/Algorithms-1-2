import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {


    private int length;

    private Item items[];


    public RandomizedQueue() {                // construct an empty randomized queue

        // java cannot create a generic array, so we should use cast to implements this

        items = (Item[]) new Object[2];

        length = 0;

    }


    public boolean isEmpty() {                // is the queue empty?

        return length == 0;

    }


    public int size() {                       // return the number of items on the queue

        return length;

    }


    private void resize(int n) {

        Item newItems[] = (Item[]) new Object[n];

        for (int i = 0; i < length; i++) {

            newItems[i] = items[i];

        }

        items = newItems;

    }


    public void enqueue(Item item) {          // add the item

        if (item == null)

            throw new NullPointerException();

        if (length == items.length)

            resize(items.length * 2);

        items[length] = item;

        length++;

    }


    public Item dequeue() {                   // delete and return a random item

        if (isEmpty())

            throw new NoSuchElementException();

        int random = StdRandom.uniform(length);

        Item item = items[random];

        if (random != length - 1)

            items[random] = items[length - 1];


        // don't forget to put the newArray[newLength] to be null to avoid Loitering

        // Loitering: Holding a reference to an object when it is no longer needed.

        items[length - 1] = null;

        length--;

        if (length > 0 && length == items.length / 4)

            resize(items.length / 2);

        return item;

    }


    public Item sample() {                    // return (but do not delete) a random item

        if (isEmpty())

            throw new NoSuchElementException();

        int random = StdRandom.uniform(length);

        Item item = items[random];

        return item;

    }


    public Iterator<Item> iterator() {        // return an independent iterator over items in random order

        return new QueueIterator();

    }


    private class QueueIterator implements Iterator<Item> {


        // Must copy the item in items[] into a new Array

        // Because when create two independent iterators to same randomized queue, the original one

        // has been changed and the second one will lead to false result.

        private int index = 0;

        private int newLength = length;

        private Item newArray[] = (Item[]) new Object[length];


        private QueueIterator() {

            for (int i = 0; i < newLength; i++) {

                newArray[i] = items[i];

            }

        }


        public boolean hasNext() {

            return index <= newLength - 1;

        }


        public Item next() {

            if (newArray[index] == null)

                throw new NoSuchElementException();

//            Item item = items[index];

//            index++;

//            return item;

            int random = StdRandom.uniform(newLength);

            Item item = newArray[random];

            if (random != newLength - 1)

                newArray[random] = newArray[newLength - 1];

            newLength--;

            newArray[newLength] = null;

//            if (newLength > 0 && newLength == newArray.length / 4)

//                resize(newArray.length / 2);

            return item;

        }


        public void remove() {

            throw new UnsupportedOperationException();

        }

    }
    public static void main(String[] args) {
    	
    }
}