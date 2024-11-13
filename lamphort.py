
class LamportClock:
    def __init__(self, process_id):
        self.clock = 0
        self.process_id = process_id
    
    def local_event(self):
        # Increment clock for a local event
        self.clock += 1
        print(f"Process {self.process_id} - Local event | Clock: {self.clock}")

    def send_event(self):
        # Increment clock for sending a message
        self.clock += 1
        print(f"Process {self.process_id} - Send event | Clock: {self.clock}")
        return self.clock
    
    def receive_event(self, sender_clock):
        # Update clock on receiving a message
        self.clock = max(self.clock, sender_clock) + 1
        print(f"Process {self.process_id} - Receive event | Clock: {self.clock}")

# Example usage
if __name__ == "__main__":
    # Two processes with separate Lamport clocks
    process_A = LamportClock(process_id="A")
    process_B = LamportClock(process_id="B")
    
    # Simulating events in each process
    process_A.local_event()            # Local event in A
    clock_A = process_A.send_event()    # A sends a message
    process_B.receive_event(clock_A)    # B receives the message from A
    
    process_B.local_event()             # Local event in B
    clock_B = process_B.send_event()    # B sends a message
    process_A.receive_event(clock_B)    # A receives the message from B