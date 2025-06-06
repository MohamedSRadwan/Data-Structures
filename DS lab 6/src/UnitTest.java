import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {

    /** testing array implementation
     * tests isEmpty when queue is empty
     * enqueue beyond maximum size throws error
     * dequeue
     * size
     * isEmpty when queue is full
     */
    @Test
    public void testArrayQueueWithValidSize() {
        ArrayQueue queue = new ArrayQueue(3);
        assertTrue(queue.isEmpty());
        queue.enqueue(10);
        assertEquals(1, queue.size());
        queue.enqueue(20);
        queue.enqueue(30);
        queue.enqueue(40);
        assertEquals(4, queue.size());
        assertFalse(queue.isEmpty());
        assertThrows(RuntimeException.class, () -> queue.enqueue(50));
    }

    /** testing linked list implementation
    * testing isEmpty
    * enqueue
    * size
    * dequeue
    * throwing error on dequeue on empty queue
    */
    @Test
    public void testLinkedQueue() {
        LinkedListQueue queue = new LinkedListQueue();
        assertTrue(queue.isEmpty());
        queue.enqueue(10);
        assertEquals(1, queue.size());
        assertEquals(10, queue.dequeue());
        assertTrue(queue.isEmpty());
        assertThrows(RuntimeException.class, queue::dequeue);
    }

    /** testing array implementation
     * testing enqueue, size, dequeue, toString
     */
    @Test
    public void testArrayQueue() {
        ArrayQueue queue = new ArrayQueue(2);
        queue.enqueue("A");
        queue.enqueue("B");
        assertEquals(2, queue.size());
        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.dequeue());
        assertEquals("[]", queue.toString());
    }
}