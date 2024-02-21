import java.util.Arrays;

public class Main {
    public static class MinHeap<T extends Comparable<T>> {
        private Object[] heap;
        private int size;
        
        public MinHeap(int capacity) {
            heap = new Object[capacity];
            size = 0;
        }
        
        public MinHeap(T[] array) {
            size = array.length;
            heap = Arrays.copyOf(array, size);
            for (int i = getParent(size - 1); i >= 0; i--) {
                heapify(i);
            }
        }
        
        public void insert(T value) {
            if (size >= heap.length) {
                resizeHeap();
            }
            heap[size++] = value;
            int current = size - 1;
            while (current != 0 && compareValues(heap[current], heap[getParent(current)]) < 0) {
                swapValues(current, getParent(current));
                current = getParent(current);
            }
        }
        
        public T peek() {
            if (size == 0) {
                throw new IllegalStateException("Heap is empty");
            }
            return (T) heap[0];
        }
        
        public T pop() {
            T min = peek();
            heap[0] = heap[size - 1];
            size--;
            heapify(0);
            return min;
        }
        
        private void heapify(int i) {
            int left = getLeftChild(i);
            int right = getRightChild(i);
            int smallest = i;
            if (left < size && compareValues(heap[left], heap[i]) < 0) {
                smallest = left;
            }
            if (right < size && compareValues(heap[right], heap[smallest]) < 0) {
                smallest = right;
            }
            if (smallest != i) {
                swapValues(i, smallest);
                heapify(smallest);
            }
        }
        
        private void resizeHeap() {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
        
        private int getParent(int i) {
            return (i - 1) >>> 1;
        }
        
        private int getLeftChild(int i) {
            return (i << 1) + 1;
        }
        
        private int getRightChild(int i) {
            return (i << 1) + 2;
        }
        
        private void swapValues(int i, int j) {
            Object temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
        
        private int compareValues(Object o1, Object o2) {
            return ((T) o1).compareTo((T) o2);
        }
    }
    
    public static void main(String[] args) {
        MinHeap<Integer> heap = new MinHeap<>(new Integer[]{9, 5, 7, 1, 3});
        System.out.println("Heap: " + Arrays.toString(heap.heap)); // Heap: [1, 3, 7, 5, 9]
        
        heap.insert(2);
        System.out.println("Inserted 2: " + Arrays.toString(heap.heap)); // Inserted 2: [1, 3, 2, 5, 9, 7, null, null, null, null]
        
        System.out.println("Peek: " + heap.peek()); // Peek: 1
        
        System.out.println("Popped: " + heap.pop()); // Popped: 1
        System.out.println("Heap after pop: " + Arrays.toString(heap.heap)); // Heap after pop: [2, 3, 7, 5, 9, 7, null, null, null, null]
    }
}
