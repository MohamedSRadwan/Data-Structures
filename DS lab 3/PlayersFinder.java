import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class PlayersFinder implements IPlayersFinder{
    //maximum size for the array
    int MAXSIZE = 100;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String line = sc.nextLine(); // read the first line
        int n = Integer.parseInt(line.split(",")[0].trim()); // take the first integer only

        StringBuilder[] photo = new StringBuilder[n]; // I used stringBuilder to allow modification
        for (int i = 0; i < n; i++){ // read n lines
            photo[i] = new StringBuilder(sc.nextLine().trim());
        }
        int team = sc.nextInt();
        sc.nextLine();
        int threshold = sc.nextInt();
        sc.close();

        PlayersFinder pf = new PlayersFinder(); // instantiate the class

        // Calculation of the players points
        java.awt.Point[] total = pf.findPlayers(photo, team, threshold); // total has a size of MAXSIZE

        // get valid points
        int count = 0;
        for (Point p : total) // count valid points
            if (p != null)
                count++;

        java.awt.Point[] players = new java.awt.Point[count]; // make array of size count
        System.arraycopy(total, 0, players, 0, count); // copy the first 'count' elements to players

        // sorting the array
        Arrays.sort(players, Comparator.comparingInt((Point p) -> p.x) // comparator that sorts according to the x coordinates first, then y
                .thenComparingInt(p -> p.y));

        // output
        System.out.print('[');
        for (int i = 0; i < players.length; i++) {
            if (i != 0) System.out.print(", ");

            System.out.print("(" + players[i].x + ", " + players[i].y + ")");
        }
        System.out.print(']');
    }

    // helper class to store information of a group of points
    private class BoundingBox{

        int size, maxX, maxY, minX, minY;
        BoundingBox(int x, int y){
            size = 0;
            maxX = minX = x;
            maxY = minY = y;
        }

        void update(int x, int y){
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            size++;
        }
    }

    // Recursive function to count same color pixels in the photo
    // returns the number of pixels in the group
    private int countGroup(StringBuilder[] photo, int x, int y, int team, BoundingBox box) {
        if (x < 0 || x >= photo.length || y < 0 || y >= photo[0].length()) return 0; // photo out of bounds
        if ((int)photo[x].charAt(y) - '0' != team) return 0; // not required color

        photo[x].setCharAt(y, '*'); // mark pixel as read

        box.update(x, y); // update information of the group

        return 1 +
                countGroup(photo, x, y + 1, team, box) +
                countGroup(photo, x, y - 1, team, box) +
                countGroup(photo, x + 1, y, team, box) +
                countGroup(photo, x - 1, y, team, box);
    }

    public java.awt.Point[] findPlayers(StringBuilder[] photo, int team, int threshold){

        int pixelSize = 4; // because it is 2x2 grid
        int groups = 0;
        java.awt.Point[] players = new Point[MAXSIZE];

        // check all pixels for groups
        for(int i = 0; i < photo.length; i++){
            for(int j = 0; j < photo[i].length(); j++){

                if (photo[i].charAt(j) - '0' == team) { // only pixels of the required type are considered
                    BoundingBox box = new BoundingBox(i, j);

                    // count the group of the current pixel
                    countGroup(photo, i, j, team, box);

                    int area = box.size * pixelSize; // calculate area

                    if (area >= threshold) { // filter the area
                        int centerX = (2 * box.minY + 2 * box.maxY + 2) / 2; // multiplying by 2 because it is a 2x2 grid
                        int centerY = (2 * box.minX + 2 * box.maxX + 2) / 2;
                        players[groups] = new java.awt.Point(centerX, centerY); // add point to the array
                        groups++;
                    }
                }
            }
        }
        return players;
    }
}
