import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {

    @Test
    public void test(){
        MyHashMap map = new MyHashMap(10);
        map.put(1, "Hello");
        map.put(2, "World");
        map.put(3, "!");
        map.put(4, "!");
        map.put(5, "f");
        map.put(6, "f");
        map.put(7, "f");
        System.out.println(map.toString());
        assertFalse(map.isEmpty());
        assertEquals(7, map.size());
        assertEquals("Hello", map.get(1));
        assertEquals("World", map.get(2));
        assertEquals("!", map.get(3));
        assertEquals("!", map.get(4));
        assertEquals("f", map.get(5));
        assertEquals("f", map.get(6));
        assertEquals("f", map.get(7));
        map.clear();
        assertTrue(map.isEmpty());
    }
}
