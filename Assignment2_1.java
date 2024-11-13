
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Assignment2_1 {

    void fcfs(int n, int p[], int at[], int bt[], int wt[], int tat[], int ct[]) {
        wt[0] = 0;
        ct[0] = bt[0];
        tat[0] = bt[0];

        for (int i = 1; i < n; i++) {
            ct[i] = ct[i - 1] + bt[i];
            tat[i] = ct[i] - at[i];
            wt[i] = tat[i] - bt[i];
        }
        int awt = 0;
        int atat = 0;
        for (int i = 0; i < n; i++) {
            awt += wt[i];
            atat += tat[i];
        }
        float w = (float) awt / n;
        float t = (float) atat / n;
        display(n, p, at, bt, wt, tat, ct);
        System.out.println("\nAverage Waiting Time - " + w + "\nAverage Turnaround Time - " + t);
    }

    void sjf(int n, int p[], int at[], int bt[], int wt[], int tat[], int ct[]) {
        int completed = 0; // Number of processes completed
        int currentTime = 0; // Current time
        boolean[] visited = new boolean[n]; // To track which processes have been visited
        int[] remainingTime = new int[n]; // Remaining burst time for each process

        // Initialize remainingTime array
        for (int i = 0; i < n; i++) {
            remainingTime[i] = bt[i];
        }

        while (completed < n) {
            int shortestProcess = -1;
            int shortestBurst = Integer.MAX_VALUE;

            // Find the process with the shortest remaining burst time that has arrived
            for (int i = 0; i < n; i++) {
                if (!visited[i] && at[i] <= currentTime && remainingTime[i] < shortestBurst) {
                    shortestBurst = remainingTime[i];
                    shortestProcess = i;
                }
            }

            if (shortestProcess == -1) {
                currentTime++;
                continue;
            }

            // Execute the shortest job for 1 time unit
            remainingTime[shortestProcess]--;
            currentTime++;

            // Check if the process is completed
            if (remainingTime[shortestProcess] == 0) {
                ct[shortestProcess] = currentTime;
                tat[shortestProcess] = ct[shortestProcess] - at[shortestProcess];
                wt[shortestProcess] = tat[shortestProcess] - bt[shortestProcess];
                visited[shortestProcess] = true;
                completed++;
            }
        }

        int awt = 0;
        int atat = 0;
        for (int i = 0; i < n; i++) {
            awt += wt[i];
            atat += tat[i];
        }

        float w = (float) awt / n;
        float t = (float) atat / n;
        display(n, p, at, bt, wt, tat, ct);
        System.out.println("\nAverage Waiting Time - " + w + "\nAverage Turnaround Time - " + t);
    }

    void priority(int n, int p[], int at[], int bt[], int wt[], int tat[], int ct[]) {
        int completed = 0; // Number of processes completed
        int currentTime = 0; // Current time
        boolean[] visited = new boolean[n]; // To track which processes have been visited
        int[] remainingTime = new int[n]; // Remaining burst time for each process

        // Initialize remainingTime array
        for (int i = 0; i < n; i++) {
            remainingTime[i] = bt[i];
        }

        while (completed < n) {
            int highestPriorityProcess = -1;
            int highestPriority = Integer.MAX_VALUE;

            // Find the process with the highest priority that has arrived
            for (int i = 0; i < n; i++) {
                if (!visited[i] && at[i] <= currentTime && p[i] < highestPriority) {
                    highestPriority = p[i];
                    highestPriorityProcess = i;
                }
            }

            if (highestPriorityProcess == -1) {
                currentTime++;
                continue;
            }

            // Execute the highest priority process until completion
            ct[highestPriorityProcess] = currentTime + bt[highestPriorityProcess];
            tat[highestPriorityProcess] = ct[highestPriorityProcess] - at[highestPriorityProcess];
            wt[highestPriorityProcess] = tat[highestPriorityProcess] - bt[highestPriorityProcess];
            visited[highestPriorityProcess] = true;
            completed++;
            currentTime += bt[highestPriorityProcess];
        }

        int awt = 0;
        int atat = 0;
        for (int i = 0; i < n; i++) {
            awt += wt[i];
            atat += tat[i];
        }

        float w = (float) awt / n;
        float t = (float) atat / n;
        display(n, p, at, bt, wt, tat, ct);
        System.out.println("\nAverage Waiting Time - " + w + "\nAverage Turnaround Time - " + t);
    }

    void roundRobin(int n, int p[], int at[], int bt[], int quantum) {
        int[] remainingTime = new int[n]; // Remaining burst time for each process
        int[] ct = new int[n]; // Completion time for each process
        int[] tat = new int[n]; // Turnaround time for each process
        int[] wt = new int[n]; // Waiting time for each process
        int currentTime = 0; // Current time
        Queue<Integer> readyQueue = new ArrayDeque<>(); // Queue to store ready processes

        // Initialize remainingTime array
        for (int i = 0; i < n; i++) {
            remainingTime[i] = bt[i];
            readyQueue.offer(i); // Add all processes to the ready queue
        }

        while (!readyQueue.isEmpty()) {
            int currentProcess = readyQueue.poll();
            int executionTime = Math.min(quantum, remainingTime[currentProcess]);

            // Execute the process for executionTime
            remainingTime[currentProcess] -= executionTime;
            currentTime += executionTime;

            // Check if the process is completed
            if (remainingTime[currentProcess] == 0) {
                ct[currentProcess] = currentTime;
                tat[currentProcess] = ct[currentProcess] - at[currentProcess];
                wt[currentProcess] = tat[currentProcess] - bt[currentProcess];
            } else {
                // Process is not completed, add back to the ready queue
                readyQueue.offer(currentProcess);
            }

        }

        // Calculate averages
        int awt = 0;
        int atat = 0;
        for (int i = 0; i < n; i++) {
            awt += wt[i];
            atat += tat[i];
        }

        float w = (float) awt / n;
        float t = (float) atat / n;
        display(n, p, at, bt, wt, tat, ct);
        System.out.println("\nAverage Waiting Time - " + w + "\nAverage Turnaround Time - " + t);
    }

    void display(int n, int p[], int at[], int bt[], int wt[], int tat[], int ct[]) {
        System.out
                .println(" Process No. || Arr. Time || Burst Time || Compl. Time || Turnaround Time || Waiting Time\n");
        for (int i = 0; i < n; i++) {
            System.out.printf("     %d     |     %d     |    %d    |    %d    |    %d    |    %d\n",
                    p[i], at[i], bt[i], ct[i], tat[i], wt[i]);
        }
    }

    public static void main(String[] args) {
        Assignment2_1 a = new Assignment2_1();
        int ch;
        Scanner sc = new Scanner(System.in);
        int n;
        System.out.println("Enter number of processes - ");
        n = sc.nextInt();
        int p[] = new int[n];
        int at[] = new int[n];
        int bt[] = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter process no - ");
            p[i] = sc.nextInt();
            System.out.println("Enter arrival time - ");
            at[i] = sc.nextInt();
            System.out.println("Enter burst time - ");
            bt[i] = sc.nextInt();
        }
        do {
            System.out.println(
                    "\n\nSelect Scheduling Algorithm - \n1.FCFS Algorithm\n2.SJF Algorithm\n3.Priority Algorithm\n4.Round Robin Algorithm\nEnter choice : ");
            ch = sc.nextInt();
            switch (ch) {
                case 1:
                    a.fcfs(n, p, at, bt, new int[n], new int[n], new int[n]);
                    break;
                case 2:
                    a.sjf(n, p, at, bt, new int[n], new int[n], new int[n]);
                    break;
                case 3:
                    a.priority(n, p, at, bt, new int[n], new int[n], new int[n]);
                    break;
                case 4:
                    int quantum;
                    System.out.println("Enter time quantum for Round Robin - ");
                    quantum = sc.nextInt();
                    a.roundRobin(n, p, at, bt, quantum);
                    break;
                default:
                    System.out.println("Invalid Choice !");
                    break;
            }
        } while (ch < 5);
    }
}
