class HostelRoom {

    String roomNo;
    int beds;
    int occupied;

    public HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    public void allot(String name) {
        if (occupied < beds) {
            occupied++;
            System.out.println(name + " allotted to room " + roomNo);
        }
    }
}

public class step33 {

    public static HostelRoom findAvailableRoom(HostelRoom[] rooms) {

        for (HostelRoom room : rooms) {
            if (room.occupied < room.beds) {
                return room;
            }
        }

        return null;
    }

    public static void safeAllot(HostelRoom[] rooms, String studentName) {

        HostelRoom availableRoom = findAvailableRoom(rooms);

        if (availableRoom != null) {
            availableRoom.allot(studentName);
        } else {
            System.out.println("No rooms available for " + studentName);
        }
    }

    public static void main(String[] args) {

        HostelRoom[] rooms1 = {
                new HostelRoom("C-214", 3, 2),
                new HostelRoom("C-507", 2, 2)
        };

        safeAllot(rooms1, "Divya");

        HostelRoom[] rooms2 = {
                new HostelRoom("C-214", 3, 3),
                new HostelRoom("C-507", 2, 2)
        };

        safeAllot(rooms2, "Divya");
    }
}
