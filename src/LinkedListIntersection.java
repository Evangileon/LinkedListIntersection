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
	 * @param n length of list append to the intersection point of list1 and
	 *            list2, intersection point included
	 */
	static void insectionAtTailAndExtend(Node list1, Node list2, int n) {
		if(list1 == null || list2 == null || n < 1) {
			return;
		}
		
		// traverse to last node of list1
		while(list1.next != null) {
			list1 = list1.next;
		}
		
		// traverse to last node of list2
		while(list2.next != null) {
			list2 = list2.next;
		}
		
		Node newList = buildList(n);
		list1.next = newList;
		list2.next = newList;
	}
	
	/**
	 * Does list1 and list2 have intersection point?
	 * @param list1
	 * @param list2
	 * @return Yes-true/No-false
	 */
	static boolean doTheyMeet(Node list1, Node list2) {
		if(list1 == null || list2 == null) {
			return false;
		}
		
		int step = 1;
		boolean isList1Turn = true;
		while(list1.next != null || list2.next != null) {
			if(isList1Turn) {
				for (int i = 0; i < step; i++) {
					if(list1 == list2) {
						return true;
					}
					if(list1.next == null) {
						break;
					}
					list1 = list1.next;
				}			
			} else { // It's list2's turn
				for (int i = 0; i < step; i++) {
					if(list2 == list1) {
						return true;
					}
					if (list2.next == null) {
						break;
					}
					list2 = list2.next;			
				}
			}
			
			step <<= 1; // power of two
			isList1Turn = !isList1Turn;
		}
		
		if(list1 == list2) {
			return true;
		}
		
		return false; // otherwise
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// {length of list1, length of list2, length of appended to intersection point}
		int[][] testCases = {
				{1, 1, 1},
				{1, 1, 2},
				{1, 2, 2},
				{1, 3, 3},
				{23, 34, 12},
				{234, 8753, 1324}
		};
		
		for (int[] oneCase : testCases) {
			// first test two lists before intersected
			Node list1 = buildList(oneCase[0]);
			Node list2 = buildList(oneCase[1]);
			System.out.print(Arrays.toString(oneCase));
			System.out.println(" before intersected: " + doTheyMeet(list1, list2));
			// after intersected
			insectionAtTailAndExtend(list1, list2, oneCase[2]);
			System.out.print(Arrays.toString(oneCase));
			System.out.println(" after intersected: " + doTheyMeet(list1, list2));
		}
	}

}
