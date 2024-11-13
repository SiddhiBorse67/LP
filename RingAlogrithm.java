import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Process {
    int id;
    boolean active;

    public Process(int id) {
        this.id = id;
        this.active = true; // All processes start as active
    }

    public void deactivate() {
        this.active = false; // Mark process as inactive if it crashes
    }
}

class RingElection {
    private List<Process> processes;

    public RingElection(List<Process> processes) {
        this.processes = processes;
    }

    public void startElection(int initiatorId) {
        System.out.println("Process " + initiatorId + " initiates the election.");
        List<Integer> electionRing = new ArrayList<>();
        int initiatorIndex = -1;

        // Find the initiator process
        for (int i = 0; i < processes.size(); i++) {
            if (processes.get(i).id == initiatorId && processes.get(i).active) {
                initiatorIndex = i;
                break;
            }
        }

        // Traverse the ring and add active processes to the election list
        int currentIndex = initiatorIndex;
        do {
            Process currentProcess = processes.get(currentIndex);
            if (currentProcess.active) {
                electionRing.add(currentProcess.id);
                System.out.println("Process " + currentProcess.id + " is part of the election.");
            }

            // Move to the next process in the ring
            currentIndex = (currentIndex + 1) % processes.size();

        } while (currentIndex != initiatorIndex);

        // Elect the process with the highest ID as the coordinator
        int newCoordinatorId = Collections.max(electionRing);
        System.out.println("Process " + newCoordinatorId + " is elected as the new coordinator.");
    }
}

public class RingAlogrithm {
    public static void main(String[] args) {
        // Create processes with unique IDs
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1));
        processes.add(new Process(2));
        processes.add(new Process(3));
        processes.add(new Process(4));
        processes.add(new Process(5));

        // Deactivate process 4 to simulate failure
        processes.get(3).deactivate();

        // Start the ring election
        RingElection election = new RingElection(processes);
        election.startElection(2); // Process 2 initiates the election
    }
}
