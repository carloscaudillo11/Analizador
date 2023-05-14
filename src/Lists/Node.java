package Lists;

public class Node<T> {
    private T object;
    private Node<T> next;

    public Node(T object) {
        this.object = object;
        this.next = null;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public T getObject() {
        return object;
    }

    public Node<T> getNext() {
        return next;
    }
}