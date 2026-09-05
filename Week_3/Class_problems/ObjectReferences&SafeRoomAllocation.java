class HostelRoom {
    private String roomNo;
    private int beds;
    private int occupied;

    public HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    public boolean allot(String name) {
        if (occupied < beds) {
            occupied++;
            return true;
        }
        return false;
    }

    public boolean isAvailable() {
        return occupied < beds;
    }

    public String getRoomNo() {
        return roomNo;
    }
}

public class RoomAllocationService {

    public static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        if (rooms == null) return null;
        for (HostelRoom room : rooms) {
            if (room != null && room.isAvailable()) {
                return room;
            }
        }
        return null;
    }

    /*
     * REFERENCE PASSING EXPLANATION:
     * Passing 'HostelRoom[] rooms' into methods copies the reference pointing to the 
     * array on the heap; it does not clone or duplicate the rooms.
     * Both caller and callee reference the identical HostelRoom instances. Any mutation 
     * (such as calling room.allot()) directly changes the original object.
     */
    public static void safeAllot(HostelRoom[] rooms, String studentName) {
        HostelRoom room = findAvailableRoom(rooms);

        if (room != null) {
            room.allot(studentName);
            System.out.println(studentName + " allotted to room " + room.getRoomNo());
        } else {
            System.out.println("No rooms available for " + studentName);
        }
    }

    public static void main(String[] args) {
        // Test 1: An available room exists
        HostelRoom[] roomsWithSpace = {
            new HostelRoom("C-214", 3, 2),
            new HostelRoom("C-507", 2, 2)
        };
        safeAllot(roomsWithSpace, "Divya");

        // Test 2: All rooms are full
        HostelRoom[] fullyOccupiedRooms = {
            new HostelRoom("C-214", 3, 3),
            new HostelRoom("C-507", 2, 2)
        };
        safeAllot(fullyOccupiedRooms, "Divya");
    }
}