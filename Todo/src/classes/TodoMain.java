package classes;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TodoMain {

	public static void main(String[] args) {

		TodoList todoList = new TodoList();
		Scanner inScann = new Scanner(System.in);
		System.out.println("Actions are: exit add del rename mark unmark find list cleardone save load");
		exiting: while (true) {
			String inAction = inScann.next();
			switch (inAction) {
			case "exit": {
				System.out.println("exiting");
				break exiting;
			}
			case "add": {
				System.out.println("Type in the todo item name to add");
				String addItem = inScann.next();
				if (addItem.length() != 0) { // it's not an empty string.

					todoList.todoItems.add(new TodoItem(addItem));
					System.out.println("Added " + addItem);
				}
				break;
			}
			case "del": {
				System.out.println("Type in the todo item name to delete");
				String delItem = inScann.next();
				for (TodoItem item : todoList.todoItems) {
					if (item.action.equalsIgnoreCase(delItem)) {
						todoList.todoItems.remove(item);
						System.out.println("Removed " + delItem);
					}
				}
				break;
			}
			case "rename": {
				int index = 0;
				do {
					boolean inputMismatch = true;
					while (inputMismatch) {
						try {
							System.out.println("Type in the todo item index to rename");
							Scanner inScann1 = new Scanner(System.in);
							index = inScann1.nextInt();
							inputMismatch = false;
						}
						catch (InputMismatchException ime) {
							System.out.println("typed index was wrong.");
							inputMismatch = true;
						}
					}
				}
				while (index < 0 || index > (todoList.todoItems.size() - 1));
				System.out.println("Type in the todo items new name");
				String renameItem = inScann.next();
				System.out.println(todoList.todoItems.get(index).action + " renamed to " + renameItem);
				todoList.todoItems.get(index).action = renameItem;
				break;
			}
			case "mark":
			case "unmark": {
				int index = 0;
				do {
					boolean inputMismatch = true;
					while (inputMismatch) {
						try {
							System.out.println("Type in the todo item index to " + inAction);
							Scanner inScann2 = new Scanner(System.in);
							index = inScann2.nextInt();
							inputMismatch = false;
						} catch (InputMismatchException ime) {
							System.out.println("typed index was wrong.");
							inputMismatch = true;
						}
					}
				} while (index < 0 || index > (todoList.todoItems.size() - 1));
				todoList.todoItems.get(index).done = inAction.equals("mark");
				String x = (todoList.todoItems.get(index).done == true) ? "x" : " ";
				System.out.println("[" + todoList.todoItems.get(index).action + "] [" + x + "]");
				break;
			}
			case "find": {
				System.out.println("Type in the todo item to find");
				String findItem = inScann.next();
				boolean found = false;
				for (TodoItem item : todoList.todoItems) {
					if (item.action.equalsIgnoreCase(findItem)) {
						System.out.println(findItem + " be finded among todoList");
						found = true;
						break;
					}
				}
				if (!found) {
					System.out.println(findItem + " NOT be finded among todoList");
				}
				break;
			}
			case "list": {
				System.out.println(todoList.todoItems.size() + " items in todo");

//				 int counter = 0;
//				 for (TodoItem item : todoList.todoItems) {
//				 System.out.println(counter++ + " : " + item);
//				 }
				int[] counter = { 0 };
				todoList.todoItems.forEach(item -> System.out.println(counter[0]++ + " : " + item));
				break;
			}
			case "cleardone": {
				if (todoList.todoItems.removeIf((item) -> item.done)) {
					System.out.println("Removed done items");
				}
				break;
			}
			case "save": {
				// Using XmlIO to save the file, errors are unexpected (write
				// protected files)
				try {
					XmlIO.saveObject("todolist.xml", todoList);
					break;
				} catch (IOException ex) {
					Logger.getLogger(TodoMain.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			case "load": {
				// Loading with XmlIO, in this case the file might be missing.
				try {
					todoList = XmlIO.loadObject("todolist.xml", TodoList.class);
				} catch (IOException ex) {
					System.out.println("Could not load todolist.xml");
				}
				break;
			}
			default:
				System.out.println("Unknown command " + inAction);
				break;
			}
		}

	}
}
