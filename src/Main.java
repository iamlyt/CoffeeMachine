import machine.MachineState;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MachineState machine = new MachineState(400, 540, 120, 9, 550);

        while (machine.isOn()) {
            machine.handleInput(scanner.next());
        }
    }
}
