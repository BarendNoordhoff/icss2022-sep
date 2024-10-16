package nl.han.ica.datastructures;

public class HANQueue<T> implements IHANQueue<T>{
    HANLinkedList<T> q;

    public HANQueue() {
        q = new HANLinkedList<>();
    }

    @Override
    public void clear() {
        q.clear();
    }

    @Override
    public boolean isEmpty() {
        return q.getSize() == 0;
    }

    @Override
    public void enqueue(T value) {
        q.insert(q.size,value);
    }

    @Override
    public T dequeue() {
        T val = q.getFirst();
        q.removeFirst();
        return val;
    }

    @Override
    public T peek() {
        return q.getFirst();
    }

    @Override
    public int getSize() {
        return q.getSize();
    }
}
