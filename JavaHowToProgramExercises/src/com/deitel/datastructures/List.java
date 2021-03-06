// Grzegorz Ko�czak, 27//28/30.08.2016
// Exercise number 21.6/9/20/21/26 page 946/948/950
// Exercise from Java:How to program 10th edition

package com.deitel.datastructures;

// class to represent one node in a list
class ListNode<T> {
	// package access members; List can access these directly
	T data; // data for this node
	ListNode<T> nextNode; // reference to the next node in the list

	// constructor creates a ListNode that refers to object
	ListNode(T object) {
		this(object, null);
	}

	// constructor creates ListNode that refers to the specified
	// object and to the next ListNode
	ListNode(T object, ListNode<T> node) {
		data = object;
		nextNode = node;
	}

	// return reference to data in node
	T getData() {
		return data;
	}

	// return reference to next node in list
	ListNode<T> getNext() {
		return nextNode;
	}

	void setNextNode(ListNode<T> nextNode) {
		this.nextNode = nextNode;
	}

} // end class ListNode<T>

// class List definition
public class List<T> {
	private ListNode<T> firstNode;
	private ListNode<T> lastNode;
	private String name; // string like "list" used in printing

	// constructor creates empty List with "list" as the name
	public List() {
		this("list");
	}

	// constructor creates an empty List with a name
	public List(String listName) {
		name = listName;
		firstNode = lastNode = null;
	}

	ListNode<T> getFirstNode() {
		return firstNode;
	}

	void setFirstNode(ListNode<T> firstNode) {
		this.firstNode = firstNode;
	}

	ListNode<T> getLastNode() {
		return lastNode;
	}

	void setLastNode(ListNode<T> lastNode) {
		this.lastNode = lastNode;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	public void insert(int index, T data) {
		if (isEmpty()) {
			throw new EmptyListException();
		} else {
			if (index != 0) {
				ListNode<T> node = firstNode;
				for (int i = 0; i < index - 1; i++) {
					if (node == null) {
						System.err.println("Out of list!");
						return;
					} else {
						node = node.nextNode;
					}
				}
				node.nextNode = new ListNode<T>(data, node.nextNode);
			} else {
				firstNode = new ListNode<T>(data, firstNode);
			}
		}
	}

	public void delete(T data) {
		if (firstNode.data == data){
			firstNode = firstNode.nextNode;
		} else {
			ListNode<T> node = searchNodeToDelete(data, firstNode);
			if (node != null){
				System.out.println(node.data);
				node.nextNode = node.nextNode.nextNode;
			} else {
				System.err.println("Value not found!");
			}
		}
	}

	private ListNode<T> searchNodeToDelete(T data, ListNode<T> node) {
		if (node.nextNode.data == data) {
			return node;
		} else if (node.nextNode != lastNode) {
			return searchNodeToDelete(data, node.nextNode);
		} else {
			return null;
		}
	}

	// insert item at front of List
	public void insertAtFront(T insertItem) {
		if (isEmpty()) // firstNode and lastNode refer to same object
			firstNode = lastNode = new ListNode<T>(insertItem);
		else // firstNode refers to new node
			firstNode = new ListNode<T>(insertItem, firstNode);
	}

	// insert item at end of List
	public void insertAtBack(T insertItem) {
		if (isEmpty()) // firstNode and lastNode refer to same object
			firstNode = lastNode = new ListNode<T>(insertItem);
		else // lastNode's nextNode refers to new node
			lastNode = lastNode.nextNode = new ListNode<T>(insertItem);
	}

	// remove first node from List
	public T removeFromFront() throws EmptyListException {
		if (isEmpty()) // throw exception if List is empty
			throw new EmptyListException(name);

		T removedItem = firstNode.data; // retrieve data being removed

		// update references firstNode and lastNode
		if (firstNode == lastNode)
			firstNode = lastNode = null;
		else
			firstNode = firstNode.nextNode;

		return removedItem; // return removed node data
	} // end method removeFromFront

	public int size() {
		ListNode<T> current = firstNode;
		int count = 0;
		while (!isEmpty() && current != null) {
			current = current.nextNode;
			count++;
		}
		return count;
	}

	// remove last node from List
	public T removeFromBack() throws EmptyListException {
		if (isEmpty()) // throw exception if List is empty
			throw new EmptyListException(name);

		T removedItem = lastNode.data; // retrieve data being removed

		// update references firstNode and lastNode
		if (firstNode == lastNode)
			firstNode = lastNode = null;
		else // locate new last node
		{
			ListNode<T> current = firstNode;

			// loop while current node does not refer to lastNode
			while (current.nextNode != lastNode)
				current = current.nextNode;

			lastNode = current; // current is new lastNode
			current.nextNode = null;
		}

		return removedItem; // return removed node data
	}

	// determine whether list is empty
	public boolean isEmpty() {
		return firstNode == null; // return true if list is empty
	}

	// output list contents
	public void print() {
		if (isEmpty()) {
			System.out.printf("Empty %s%n", name);
			return;
		}

		System.out.printf("The %s is: ", name);
		ListNode<T> current = firstNode;

		// while not at end of list, output current node's data
		while (current != null) {
			System.out.printf("%s ", current.data);
			current = current.nextNode;
		}

		System.out.println();
	}

	public void printListBackwards() {
		printNode(firstNode);
	}

	private void printNode(ListNode<T> node) {
		if (node == lastNode) {
			System.out.printf("The %s backward is: %s ", name, node.data);
		} else {
			printNode(node.nextNode);
			System.out.printf("%s ", node.data);
		}

	}

	public static <T> List<T> reverseCopy(List<T> list1) {
		List<T> reversed = new List<>();
		while (!list1.isEmpty()) {
			reversed.insertAtFront(list1.removeFromFront());
		}
		return reversed;
	}

	public T search(T data) {
		ListNode<T> node = searchNodes(data, firstNode);
		return node == null ? null : node.data;
	}

	private ListNode<T> searchNodes(T data, ListNode<T> node) {
		if (node.data == data) {
			return node;
		} else if (node != lastNode) {
			return searchNodes(data, node.nextNode);
		} else {
			return null;
		}
	}
} // end class List<T>
