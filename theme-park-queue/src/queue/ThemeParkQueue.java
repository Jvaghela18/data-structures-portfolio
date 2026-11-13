package queue;

import edu.rutgers.cs112.node.LLNode;

/**
 * CS112 Queue Lab
 * 
 * Implement enqueue(), dequeue(), interleaveQueue() using a linked-list based queue of strings
 * to represent a line of people waiting for a theme park attraction
 * 
 * This Queue uses two pointers, one to store the front of the line and one to store the back. 
 * Each person is represented by a node which holds a string. 
 * 
 * frontOfLine -> Rider -> Rider -> endOfLine ->
 *     ^                                             ^
 * dequeue from here                        enqueue here
 * 
 * @author Colin Sullivan
 */
public class ThemeParkQueue {
    private LLNode<String> frontOfLine;
    private LLNode<String> endOfLine;
    private int lineLength;

    /**
     * Constructor which initializes the linked list queue as empty
     */
    public ThemeParkQueue() {
        // DO NOT MODIFY
        frontOfLine = null;
        endOfLine = null;
        lineLength = 0;
    }

    /**
     * Enqueue a new Rider to the end of the queue 
     * 
     * @param toAdd String value to enqueue in a Rider node
     */
    public void enqueue(String toAdd) {
        LLNode<String> newNode = new LLNode<String>(toAdd);
        if (frontOfLine == null) {
            frontOfLine = newNode;
            endOfLine = newNode;
        } else {
            endOfLine.setNext(newNode);
            endOfLine = newNode;
        }
        lineLength++;
    }

    /**
     * Dequeue the Rider at the front of the queue 
     * 
     * @return Rider which was at the front of the queue
     */
    public String dequeue() {
        if (frontOfLine == null) {
            return null;
        }
        String data = frontOfLine.getData();
        frontOfLine = frontOfLine.getNext();
        if (frontOfLine == null) {
            endOfLine = null;
        }
        lineLength--;
        return data;
    }

    /**
     * Interleave another queue into the current one
     *
     * Riders alternate, starting with the rider at the front of this queue, then the rider
     * at the front of the other queue, and so on. Once one of the two queues runs out, the
     * riders still waiting in the remaining queue all follow at the end
     *
     * The other queue is left empty and should have a lineLength of 0 once this method ends
     *
     * Build this out of the enqueue() and dequeue() methods you already wrote, rather than
     * relinking nodes yourself. You can create as many ThemeParkQueue objects as you need
     *
     * @param otherQueue The other line of riders, which gets emptied into this one
     */
    public void interleaveQueue(ThemeParkQueue otherQueue){
        ThemeParkQueue result = new ThemeParkQueue();
        while (this.getLineLength() > 0 || otherQueue.getLineLength() > 0) {
            if (this.getLineLength() > 0) {
                result.enqueue(this.dequeue());
            }
            if (otherQueue.getLineLength() > 0) {
                result.enqueue(otherQueue.dequeue());
            }
        }
        // Move the interleaved riders back into this queue.
        while (result.getLineLength() > 0) {
            this.enqueue(result.dequeue());
        }
        // otherQueue is already empty: its dequeue() calls drained it.
    }

    /**
     * Getter method for length of line
     * 
     * @return number of riders in line (lineLength)
     */
    public int getLineLength() {
        // DO NOT EDIT
        return lineLength;
    }

    /**
     * Getter method for front of line
     * 
     * @return return reference to the front of the line
     */
    public LLNode<String> getFront() {
        // DO NOT EDIT
        return frontOfLine;
    }

    /**
     * Getter method for end of line
     * 
     * @return return reference to the end of the line
     */
    public LLNode<String> getEnd(){
        return endOfLine;
    }

    /**
     * DO NOT EDIT
     * Represents the queue contents as a String
     */
    public String toString() {
        StringBuilder q = new StringBuilder();
        q.append("lineLength=");
        q.append(this.getLineLength());
        q.append(", ");
        q.append("{ ");
        LLNode<String> qString = frontOfLine;

        boolean first = true;
        while (qString != null) {
            if (!first) {
                q.append(", ");
            }
            q.append(qString.getData());
            first = false;
            qString = qString.getNext();
        }
        q.append(" }");
        return q.toString();
    }

}
