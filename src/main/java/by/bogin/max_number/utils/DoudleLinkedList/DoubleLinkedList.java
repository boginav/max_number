package by.bogin.max_number.utils.DoudleLinkedList;


public class DoubleLinkedList  {
    Node head;
    Node tail;
    int size = 0;

    // Метод для добавления числа по убыванию
    public void addWithSorting(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = tail = newNode;
        } else if (head.data <= data) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null && current.next.data > data) {
                current = current.next;
            }
            newNode.next = current.next;
            if (current.next != null) {
                current.next.prev = newNode;
            } else {
                tail = newNode;
            }
            current.next = newNode;
            newNode.prev = current;
        }
        size++;
    }
    // Метод для получения размера списка
    public int getSize() {
        return size;
    }

    // Метод для получения значения по индексу
    public int getValueByIndex(int index) {

            Node current = head;
            int count = 0;
            while (current != null) {
                if (count == index) {
                    return current.data;
                }
                count++;
                current = current.next;
            }
      throw new IndexOutOfBoundsException("Index out of list range") ;

    }

    // Метод вывода списка в консоль
    public void display() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}


