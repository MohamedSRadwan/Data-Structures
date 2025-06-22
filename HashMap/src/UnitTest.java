import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {

    @Test
    public void test(){
        MyHashMap<Integer, String> map = new MyHashMap<>(10);
        map.put(1, "Hello");
        map.put(2, "World");
        map.put(3, "!");
        map.put(4, "!");
        map.put(5, "f");
        map.put(6, "f");
        map.put(7, "f");
        System.out.println(map);
        assertFalse(map.isEmpty());
        assertEquals(7, map.size());
        assertEquals("Hello", map.get(1));
        assertEquals("World", map.get(2));
        assertEquals("!", map.get(3));
        assertEquals("!", map.get(4));
        assertEquals("f", map.get(5));
        assertEquals("f", map.get(6));
        assertEquals("f", map.get(7));
        assertTrue(map.containsKey(1));
        assertTrue(map.containsKey(2));
        assertTrue(map.containsKey(3));
        assertTrue(map.containsKey(4));
        assertTrue(map.containsKey(5));
        assertTrue(map.containsKey(6));
        assertTrue(map.containsKey(7));
        assertFalse(map.containsKey(8));
        map.clear();
        assertTrue(map.isEmpty());
    }
}
