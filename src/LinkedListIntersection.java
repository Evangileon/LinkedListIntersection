import java.util.Arrays;

public class LinkedListIntersection {

	public static class Node {
		int data;
		Node next;

		public Node() {
			data = 0;
			next = null;
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
			p.next = new Node();
			p = p.next;
		}

		return root;
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
	 * @param n position of cycle point
	 */
	static void constructCycle(Node list, int n) {
		Node node = list;
		// find the target node to which the tail pointer
		for (int i = 0; i < n; i++) {
			if(node.next == null) {
				return;
			}
			node = node.next;
		}
		// find the tail
		Node tail = node;
		while(tail.next != null) {
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

		while ((p.next != null || q.next != null)
				&& (p.next != null || qCycle == false)
				&& (q.next != null || pCycle == false)) {
			//Node start = p;
			for (int i = 0; i < step; i++) {
				if (p == q) {
					return true;
				}
				if (p.next == null) {
					break;
				}
				p = p.next;
				/*
				 * if(p == start) { return false; }
				 */
			}
			step <<= 1; // power of two
			Node swap = p;
			p = q;
			q = swap;
			boolean swapCycle = pCycle;
			pCycle = qCycle;
			qCycle = swapCycle;
		}

		if (p == q) {
			return true;
		}

		return false; // otherwise
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// {length of list1, length of list2, length of appended to intersection
		// point}
		int[][] testCases = { { 1, 1, 1 }, { 1, 1, 2 }, { 1, 2, 2 },
				{ 1, 3, 3 }, { 23, 34, 12 }, { 234, 8753, 1324 } };

		System.out
				.println("Test case format: {length of list1, length of list2, length of appended to intersection point}}");

		for (int[] oneCase : testCases) {
			// first test two lists before intersected
			Node list1 = buildList(oneCase[0]);
			Node list2 = buildList(oneCase[1]);
			System.out.print(Arrays.toString(oneCase));
			System.out.println(" before intersected: "
					+ doTheyMeet(list1, list2));
			// after intersected
			insectionAtTailAndExtend(list1, list2, oneCase[2]);
			System.out.print(Arrays.toString(oneCase));
			System.out.println(" after intersected: "
					+ doTheyMeet(list1, list2));
		}
	}

}
