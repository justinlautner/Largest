import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

/*
 * Homework 2
 * CSC 388
 * Justin Lautner
 * Student Number: 679361959​
 */

public class Largest {
	
	//Initialize head of linked list
	private static LinkedListNode head;
	
	public static void main(String[] args) {
		//Call methods to run program
		input();
		printLinkedList();
		
		//Preparing variables for recursion
		LinkedListNode node = head;
		//Initialize sum as sum of all nodes in the data set
		int sum = 0;
		while (node.next != null) {
			sum += node.item;
			node = node.next;
		}
		//While loop skips last node, this rectifies that
		sum += node.item;
		final int initialSum = sum;
		
		//Set return of zeroSums() to int in order to change the output based on result
		int largestZeroSumSubset = zeroSums(size() + 1, sum, initialSum, true);
		if (largestZeroSumSubset == 0) {
			System.out.print("This data set contains " + largestZeroSumSubset + " zero-sum subsets.");
		}
		else {
			System.out.print("The largest zero-sum subset of this data set contains " + largestZeroSumSubset + " integers.");
		}
	}
	
	private static void input() {
		//Load input file and split it into tokens to become int linked list nodes
		//Check for IO exception, missing file
		try {
			String input = new String(Files.readAllBytes(Paths.get("in.txt")));
			StringTokenizer st = new StringTokenizer(input);
			while(st.hasMoreTokens()) {
				add(Integer.parseInt(st.nextToken()));
			}
		} catch (IOException e) {
			System.out.print("Theres nothing here... :(");
		}
	}
	
	private static void add(int item) {
		LinkedListNode node = new LinkedListNode(item);
		//Add first token to head node
		if (head == null) {
			head = node;
		}
		//Add subsequent tokens to chain
		else {
			LinkedListNode headNode = head;
			while (headNode.next != null) {
				headNode = headNode.next;
			}
			headNode.next = node;
		}
	}
	
	private static void printLinkedList() {
		LinkedListNode currentNode = head;
		String s = "Your set of data is: ";
    	
    	//Iterate through linked list and print it, to show the user what the input was
		while (currentNode != null)
    	{
    		s += Integer.toString(currentNode.item);
    		
    		if (currentNode.next != null)
    		{
    			s += ", ";
    		}
    		
    		currentNode = currentNode.next;
    	}
    	System.out.println(s + ".");
	}
	
	private static int zeroSums(int sumSetSize, int sum, int initialSum, boolean hasElements) {
		//subset found
		if (sum == 0) {
			return sumSetSize;
		}
		
		//base case, no subset found
		if (!hasElements) {
			return 0;
		}
		
		//Resets hasElements to false, so that it does not endlessly loop through the method as true
		hasElements = false;
		LinkedListNode node = head;
		//currHighest ensures that the while loop subtracts the highest node that does not surpass 0 and subtracts it
		int currHighest = 0;
		//initialSum ensures that if the sum is greater than 0 the objective is to descend, whereas if the sum is less than 0 it is to ascend
		if (initialSum < 0) {
			while (node.next != null) {
				//Ensures that the node will bring the sum closer to zero and is higher than the last
				if (sum - node.item > sum && sum - node.item <= 0 && node.item < currHighest) {
					currHighest = node.item;
					hasElements = true;
				}
				node = node.next;
				//This ensures that the last node is not skipped, as the while loop would break before actually using it
				if (node.next == null) {
					if (sum - node.item > sum && sum - node.item <= 0 && node.item < currHighest) {
						currHighest = node.item;
						hasElements = true;
					}
				}
			}
		}
		else {
			while (node.next != null) {
				//Ensures that the node will bring the sum closer to zero and is higher than the last
				if (sum - node.item < sum && sum - node.item >= 0 && node.item > currHighest) {
					currHighest = node.item;
					hasElements = true;
				}
				node = node.next;
				//This ensures that the last node is not skipped, as the while loop would break before actually using it
				if (node.next == null) {
					if (sum - node.item < sum && sum - node.item >= 0 && node.item > currHighest) {
						currHighest = node.item;
						hasElements = true;
					}
				}
			}
		}
		
		//Subtracts 1 from the sumSetSize for each node not included, and subtracts currHighest from the sum to attempt to reach 0
		return zeroSums(sumSetSize - 1, sum - currHighest, initialSum, hasElements);
	}
	
	//This is used to ensure that the sumSetSize is initialized as an arbitrary sized list
	private static int size() {
		int size = 0;
		for (LinkedListNode node = head; node.next != null; node = node.next) {
			size++;
		}
		return size;
	}
	
	private static class LinkedListNode {
		
		//Create node and link it to next
		LinkedListNode next;
		int item;
		
		//Initialize node
		LinkedListNode(int item){
			this.item = item;
		}
	}
}