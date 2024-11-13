// import java.util.ArrayList;
// import java.util.List;

// class BullyProcess {
// int id;
// boolean active;
// boolean isCoordinator;

// public BullyProcess(int id) {
// this.id = id;
// this.active = true; // All processes start as active
// this.isCoordinator = false;
// }

// public void deactivate() {
// this.active = false; // Simulate a process crash
// }

// public void electCoordinator(List<BullyProcess> processes) {
// System.out.println("Process " + id + " starts the election.");
// boolean newCoordinatorElected = false;

// for (BullyProcess p : processes) {
// if (p.id > this.id && p.active) {
// System.out.println("Process " + id + " is bullied by process " + p.id);
// return; // Stop the election if a higher-ID process is active
// }
// }

// If no higher-ID process is active, current process becomes the coordinator
// this.isCoordinator = true;
// newCoordinatorElected = true;
// System.out.println("Process " + id + " is elected as the new coordinator.");
// }
// }

// public class BullyAlgorithm {
// public static void main(String[] args) {
// Create processes with unique IDs
// List<BullyProcess> processes = new ArrayList<>();
// processes.add(new BullyProcess(1));
// processes.add(new BullyProcess(2));
// processes.add(new BullyProcess(3));
// processes.add(new BullyProcess(4));
// processes.add(new BullyProcess(5));

// Deactivate process 5 to simulate failure of the coordinator
// processes.get(2).deactivate();

// Process 2 detects failure of process 5 and initiates an election
// processes.get(3).electCoordinator(processes);
// }
// }
