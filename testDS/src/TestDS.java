import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestDS {
    SingleLinkedList list;
    com.MohamedSRadwan.github.datastructures.redblackbst.RedBlackBST<Integer, String> tree;
    MyHashMap<Integer, String> map;

    public static void main(String[] args) {
        TestDS t = new TestDS();

        try {
            //  Read all commands into memory first
            List<String> commands = t.readCommands("testdataLL.txt");

            // Test linked list
            long timeLL = t.testLL(commands);
            System.out.println("Linked List: " + timeLL + " ms");

            commands = t.readCommands("testdatakv.txt");

            // Test tree
            long timeBST = t.testBST(commands);
            System.out.println("Tree: " + timeBST + " ms");

            // Test hashmap
            long timeMap = t.testMap(commands);
            System.out.println("HashMap: " + timeMap + " ms");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> readCommands(String filePath) throws IOException {
        List<String> commands = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                commands.add(line);
            }
        }
        return commands;
    }

    public long testLL(List<String> commands) {
        list = new SingleLinkedList();

        long start = System.nanoTime();

        for (String line : commands) {
            String[] split = line.split(" ");
            switch (split[0]) {
                case "put" -> list.add(split[2]);
                case "get" -> list.get(Integer.parseInt(split[1]));
                case "contains" -> list.contains(split[1]);
                case "delete" -> list.remove(Integer.parseInt(split[1]));
            }
        }

        return (System.nanoTime() - start) / 1_000_000; // Convert to milliseconds
    }

    public long testBST(List<String> commands) {
        tree = new com.MohamedSRadwan.github.datastructures.redblackbst.RedBlackBST<>();
        long start = System.nanoTime();
        for (String line : commands) {
            String[] split = line.split(" ");
            switch (split[0]) {
                case "put" -> tree.put(Integer.parseInt(split[1]), split[2]);
                case "get" -> tree.get(Integer.parseInt(split[1]));
                case "contains" -> tree.contains(Integer.parseInt(split[1]));
                case "delete" -> tree.delete(Integer.parseInt(split[1]));
            }
        }
        return (System.nanoTime() - start) / 1_000_000; // Convert to milliseconds
    }

    public long testMap(List<String> commands) {
        map = new MyHashMap<>();
        long start = System.nanoTime();
        for(String line : commands){
            String[] split = line.split(" ");
            switch (split[0]) {
                case "put" -> map.put(Integer.parseInt(split[1]), split[2]);
                case "get" -> map.get(Integer.parseInt(split[1]));
                case "contains" -> map.containsKey(Integer.parseInt(split[1]));
                case "delete" -> map.remove(Integer.parseInt(split[1]));
            }
        }
        return (System.nanoTime() - start) / 1_000_000; // Convert to milliseconds
    }
}