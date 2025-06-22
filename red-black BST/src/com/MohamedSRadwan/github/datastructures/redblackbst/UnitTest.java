package com.MohamedSRadwan.github.datastructures.redblackbst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {

    @Test
    public void treeTest() {

        RedBlackBST<Integer, String> tree = new RedBlackBST<> ();
        tree.put(1, "A");tree.put(2, "B");tree.put(3, "C");tree.put(4, "D");
        tree.put(5, "E");tree.put(6, "F");tree.put(7, "G");tree.put(8, "H");
        tree.put(9, "I");

        tree.printTree();

        assertEquals("A", tree.get(1));
        assertEquals("B", tree.get(2));
        assertEquals("C", tree.get(3));
        assertEquals("D", tree.get(4));
        assertEquals("E", tree.get(5));
        assertEquals("F", tree.get(6));
        assertEquals("G", tree.get(7));
        assertEquals("H", tree.get(8));
        assertEquals("I", tree.get(9));

        tree.delete(1);
        assertNull(tree.get(1));
        assertFalse(tree.contains(1));

        assertEquals(8, tree.size());
        assertFalse(tree.isEmpty());

        tree.deleteMax();
        assertNull(tree.get(9));

        tree.delete(3);
        assertNull(tree.get(3));
        assertEquals(6, tree.size());

        tree.deleteMin();
        assertNull(tree.get(2));

        assertTrue(tree.contains(4));

        tree.delete(4);
        assertNull(tree.get(4));

        tree.delete(8);
        assertNull(tree.get(8));

        tree.delete(6);
        assertNull(tree.get(6));

        tree.delete(5);
        assertNull(tree.get(5));
        assertEquals(1, tree.size());
        assertFalse(tree.isEmpty());

        tree.deleteMax();
        assertNull(tree.get(7));
        assertEquals(0, tree.size());
        assertTrue(tree.isEmpty());

        assertNull(tree.get(10));
        assertNull(tree.get(-1));
        assertNull(tree.get(0));
        tree.clear();
        assertTrue(tree.isEmpty());
    }

    @Test
    public void testHeight() {
        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        tree.put((int) 'S',  "S");
        tree.put((int) 'E',  "E");
        tree.put((int) 'A',  "A");
        tree.put((int) 'R',  "R");
        tree.put((int) 'C',  "C");
        tree.put((int) 'H',  "H");
        tree.put((int) 'M',  "M");
        tree.put((int) 'P',  "P");
        tree.put((int) 'L',  "L");

        assertEquals(3, tree.height());
        tree.printTree();
    }

    @Test
    public void testFloorCeiling() {
        RedBlackBST<Integer, String> tree = new RedBlackBST<>();
        tree.put((int) 'S',  "S");
        tree.put((int) 'E',  "E");
        tree.put((int) 'A',  "A");
        tree.put((int) 'R',  "R");
        tree.put((int) 'C',  "C");
        tree.put((int) 'H',  "H");
        tree.put((int) 'M',  "M");
        tree.put((int) 'X',  "X");

        assertEquals("A", tree.get(tree.floor((int) 'A')));
        assertEquals("E", tree.get(tree.floor((int) 'G')));
        assertEquals("H", tree.get(tree.ceiling((int) 'G')));
        assertNull(tree.get(tree.ceiling((int) 'Y')));

        assertEquals("X", tree.get(tree.floor((int) 'Y')));
        tree.printTree();
    }

}