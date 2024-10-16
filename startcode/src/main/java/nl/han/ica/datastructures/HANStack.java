package nl.han.ica.datastructures;

public class HANStack<T> implements IHANStack<T> {
    HANLinkedList<T> stack;

    public HANStack() {
        stack = new HANLinkedList<>();
    }

    @Override
    public void push(T value) {
        if (stack.getSize() > 0) {
            stack.insert(stack.getSize() - 1, value);
        } else {
            stack.addFirst(value);
        }
    }

    @Override
    public T pop() {
        T val = stack.get(stack.getSize() - 1);
        stack.delete(stack.getSize() - 1);
        return val;
    }

    @Override
    public T peek() {
        return stack.get(stack.getSize() - 1);
    }
}
