
import java.util.*;
import java.io.*;

public class pagere {
    public static void fifo(int[] refs, int size) {
        Queue<Integer> memory = new LinkedList<>();
        int hits = 0;
        for (int ref : refs) {
            if (!memory.contains(ref)) {
                if (memory.size() == size) {
                    memory.poll();
                }
                memory.add(ref);
            } else {
                hits++;
            }
            System.out.println(ref + " " + memory);
        }
        int misses = refs.length - hits;
        System.out.println("Page Faults: " + misses + "Page hits: " + hits);
    }

    public static void lru(int[] refs, int size) {
        List<Integer> memory = new ArrayList<>();
        int hits = 0;
        for (int ref : refs) {
            if (memory.contains(ref)) {
                hits++;
                memory.remove((Integer) ref);
                memory.add(ref);

            } else {
                if (memory.size() == size) {
                    memory.remove(0);
                }
                memory.add(ref);
            }
            System.out.println(ref + " " + memory);
        }
        int misses = refs.length - hits;
        System.out.println("Page Faults: " + misses + "Page hits: " + hits);
    }

    public static void optimal(int[] refs, int size) {
        List<Integer> memory = new ArrayList<>();
        int hits = 0;

        for (int i = 0; i < refs.length; i++) {
            int ref = refs[i];
            if (memory.contains(ref)) {
                hits++;
            } else {
                if (memory.size() == size) {
                    int maxGap = -1;
                    int pageToReplace = 0;
                    for (int page : memory) {
                        int gap = findNextOccurence(refs, i, page);
                        if (gap > maxGap) {
                            maxGap = gap;
                            pageToReplace = page;
                        }
                    }
                    memory.remove((Integer) pageToReplace);
                }
            }
            System.out.println(ref + " " + memory);
        }
        int misses = refs.length - hits;
        System.out.println("Page Faults: " + misses + "Page hits: " + hits);
    }

    private static int findNextOccurence(int[] refs, int s, int page) {
        for (int i = s + 1; i < refs.length; i++) {
            if (refs[i] == page) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;
        int f;
        System.out.println("Enter array size : ");
        n = sc.nextInt();

        System.out.println("Enter no of frame size : ");
        f = sc.nextInt();

        System.out.println("Enter Values : ");
        int[] refs = new int[n];
        for (int i = 0; i < n; i++) {
            refs[i] = sc.nextInt();
        }
        int choice;
        do {
            System.out.println("*MENU*");
            System.out.println("1. FIFO");
            System.out.println("2. LRU");
            System.out.println("3. Optimal");
            System.out.println("Enter the your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    fifo(refs, f);
                    break;

                case 2:
                    lru(refs, f);
                    break;

                case 3:
                    optimal(refs, f);
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Invalid Argument");
                    break;
            }
        } while (choice != 4);
    }
}
