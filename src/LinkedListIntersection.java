/**
 * @author Jun Yu
 * @date 09/18/2014
 */

/**
 * Description of Algorithm
 * 
 * Proceed two lists alternately by the steps of 2, 4, 8 ... (power of 2) sequence,
 * In the process, store the last node accessed by previous run separately for two lists.
 * While in iteration, check whether current node equals to stored node.
 * Termination Condition: 1. Both lists reach the end if no cycles. 
 * 						OR 2. One reach the end, while another one has a cycle
 * 						OR 3. Acknowledged that both lists have cycles, and stored node in cycle,
 * 							but can not find the stored node in another cycle. 
 */

/**
 * Proof of Correctness
 * 
 * There are three case:
 * 1. Neither lists have cycles
 *    Stored node will stop at the end of the lists, if they equal, then two lists 
 *    have intersection, otherwise no intersection
 * 2. One have cycle, and another have no cycle
 *    The stored will stop at the end of the one that have no cycle.
 *    One acknowledges that another list has a cycle, they can't have intersection
 * 3. Both lists have cycles
 *    Search any node from one cycle on another cycle, if exists, then they have
 *    intersection, otherwise(loop back to start point), no intersection.
 */

/**
 * Running time = O(m1 + m2)
 * Case 1:
 *    In the case they don't meet, firstly, assume the length of list
 *    after intersection point is infinite, 
 * Case 2:
 *    
 */

import java.util.Arrays;

public class LinkedListIntersection {

	public static class Node {
		int data;
		Node next;

		public Node() {
			data = 0;
			next = null;
		}

		public Node(int val) {
			data = val;
		}
	}

	/**
	 * Build a singly linked list with length without header
	 * 
	 * @param length
	 *            of the list
	 * @return first node of the list
	 */
	static Node buildList(int length) {
		if (length <= 0) {
			return null;
		}

		Node root = new Node();
		Node p = root;
		for (int i = 1; i < length; i++) {
			p.next = new Node(i);
			p = p.next;
		}

		return root;
	}

	static void printList(Node list) {
		while (list != null) {
			System.out.print(list.data + " ");
			list = list.next;
		}
		System.out.println();
	}

	/**
	 * Traverse to the tail of two list, and set their next to first node of
	 * newly created list with length n
	 * 
	 * @param list1
	 * @param list2
	 * @param n
	 *            length of list append to the intersection point of list1 and
	 *            list2, intersection point included
	 */
	static void insectionAtTailAndExtend(Node list1, Node list2, int n) {
		if (list1 == null || list2 == null || n < 1) {
			return;
		}

		// traverse to last node of list1
		while (list1.next != null) {
			list1 = list1.next;
		}

		// traverse to last node of list2
		while (list2.next != null) {
			list2 = list2.next;
		}

		Node newList = buildList(n);
		list1.next = newList;
		list2.next = newList;
	}

	/**
	 * Connect the tail of list to nth node of the list, if n >= length of the
	 * list, do nothing
	 * 
	 * @param list
	 * @param n
	 *            position of cycle point
	 */
	static void constructCycle(Node list, int n) {
		Node node = list;
		// find the target node to which the tail pointer
		for (int i = 0; i < n; i++) {
			if (node.next == null) {
				return;
			}
			node = node.next;
		}
		// find the tail
		Node tail = node;
		while (tail.next != null) {
			tail = tail.next;
		}
		// connect
		tail.next = node;
	}

	/**
	 * Does list1 and list2 have intersection point?
	 * 
	 * @param list1
	 * @param list2
	 * @return Yes-true/No-false
	 */
	static boolean doTheyMeet(Node list1, Node list2) {
		if (list1 == null || list2 == null) {
			return false;
		}

		int step = 1;
		Node p = list1;
		Node q = list2;
		boolean pCycle = false; // Does list1 have a cycle
		boolean qCycle = false; // Does list2 have a cycle
		// while not both two lists ends
		while ((p.next != null || q.next != null)) {
			Node start = p;
			for (int i = 0; i < step; i++) {
				if (p == q) {
					return true; // meet
				}

				if (p.next == null) {
					// Current list doesn't have a cycle
					if (qCycle == true) {
						// If another list have a cycle, they
						// can't have intersection
						return false;
					}
					// p has no cycle and current p reaches the end of
					// the list, no need to proceed in current list
					break; 
				}

				if (p.next == start) { 
					// p has traversed the whole cycle and
					// back two starting point,
					// also means list that p represents has a cycle
					if (pCycle == true && qCycle == true) {
						// If both p and q are already in cycles
						// then q can not be found in p's cycle,
						// that they have no intersection
						return false; 
					}
					pCycle = true;
				}

				p = p.next;
			}
			step <<= 1; // multiplied by 2
			Node swap = p;
			p = q;
			q = swap; // swap p and q
			boolean swapCycle = pCycle;
			pCycle = qCycle;
			qCycle = swapCycle; // swap the boolean variable corresponding to p
								// and q
		}
		// From now on, neither two lists have cycle
		if (p == q) { // meet
			return true;
		}

		return false; // otherwise
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// {length of list1, length of list2, length of appended to intersection
		// point, postion of cycle begin}
		// Test
		// 1. Test two lists with length case[0], case[1] respectively, without cycle or intersection.
		// 2. Test two lists intersected at tail and append list with length case[2]
		// 3. Test two lists intersected, and add cycle.
		// 4. Test list1 have cycle, and two lists have no intersection
		// 5  Test list2 have cycle, and two lists have no intersection
		int[][] testCases = { { 2, 3, 2, 3 }, { 1, 1, 1, 0 }, { 1, 1, 2, 1 },
				{ 1, 2, 2, 2 }, { 1, 3, 3, 0 }, { 23, 34, 12, 10 },
				{ 234, 873, 1324, 1000 }, {1234,29, 1000, 2000} };

		System.out
				.println("Test case format: {length of list1, length of list2, length of appended to intersection point, postion of cycle begin}}");

		for (int[] oneCase : testCases) {
			// first test two lists before intersected
			Node list1 = buildList(oneCase[0]);
			Node list2 = buildList(oneCase[1]);
			System.out.print(Arrays.toString(oneCase));
			System.out.println(" before intersected, do they meet? "
					+ doTheyMeet(list1, list2));
			// after intersected
			insectionAtTailAndExtend(list1, list2, oneCase[2]);
			System.out.print(Arrays.toString(oneCase));
			System.out.println(" after intersected, do they meet? "
					+ doTheyMeet(list1, list2));
			// constructCycle(list1, 100);
			constructCycle(list2, oneCase[3]);
			System.out.print(Arrays.toString(oneCase));
			boolean result = doTheyMeet(list1, list2);
			System.out
					.println(" after construct cycle and intersected, do they meet? "
							+ result);

			// rebuild
			list1 = buildList(oneCase[0]);
			list2 = buildList(oneCase[1]);
			constructCycle(list1, oneCase[3]);
			System.out.print(Arrays.toString(oneCase));
			System.out
					.println(" Only list1 has cycle, and not intersected with list2, do they meet? "
							+ doTheyMeet(list1, list2));

			// rebuild
			list1 = buildList(oneCase[0]);
			list2 = buildList(oneCase[1]);
			constructCycle(list2, oneCase[3]);
			System.out.print(Arrays.toString(oneCase));
			System.out
					.println(" Only list2 has cycle, and not intersected with list2, do they meet? "
							+ doTheyMeet(list1, list2));
		}
	}

}
