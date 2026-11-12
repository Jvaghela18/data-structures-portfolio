package stack;

import edu.rutgers.cs112.LL.LLNode;

/**
 * The class that will be filled out and submitted
 * The push() and pop() methods need to be implemented
 * @author Sulaiman Hasan
 */
public class PancakeStack {
    private LLNode<String> topPancake; // reference to the top of the stack
    private int numPancakes; // how many pancakes are in the stack

    /**
     * Default constructor, used by the Driver
     * Creates a new stack and starts it as null
     */
    public PancakeStack(){
        // DO NOT edit or remove 
        topPancake = null;
        numPancakes = 0;
    }

    /**
     * Add a new pancake to the top of the stack
     * Create a new node with pancakeType as the data, and make this node the top of the stack
     * 
     * @param pancakeType The flavor data for the new node being pushed
     */
    public void push(String pancakeType){
        LLNode<String> newNode = new LLNode<>(pancakeType);
        newNode.setNext(topPancake);
        topPancake = newNode;
        numPancakes++;
    }

    /**
     * Tells us if there are nodes present in the stack or not
     * @return true if no nodes are present in the stack, otherwise false
     */
    public boolean isEmpty(){
        return topPancake == null;
    }

    /**
     * Remove (eat) the pancake at the top of the stack, and return the pancake type of the removed node
     * If the stack is already empty, then return null
     * 
     * @return The pancake type of the node which was removed, or null if there were no nodes
     */
    public String pop(){
        if (isEmpty()) {
            return null;
        }
        String top = topPancake.getData();
        topPancake = topPancake.getNext();
        numPancakes--;
        return top;
    }

    /**
     * After executing this method, the same stack reference
     * should contain the contents of the original stack but in reverse order
     * 
     * You can create as many PancakeStack objects as needed to complete this method 
     * You can call the push(), pop(), and isEmpty() methods on these PancakeStack objects
     * 
     */
    public void reverse(){
        PancakeStack temp = new PancakeStack();
        while (!isEmpty()) {
            temp.push(pop());
        }
        topPancake = temp.getStack();
        numPancakes = temp.getNumPancakes();
    }

    /**
     * Used by the Driver
     * @returns The nodes in the stack
     */
    public LLNode<String> getStack(){
        // DO NOT edit or remove
        return topPancake;
    }

    /**
     * Used by the Driver
     * @returns the number of pancakes in the stack
     */
    public int getNumPancakes(){
        // DO NOT edit or remove
        return numPancakes;
    }

}
