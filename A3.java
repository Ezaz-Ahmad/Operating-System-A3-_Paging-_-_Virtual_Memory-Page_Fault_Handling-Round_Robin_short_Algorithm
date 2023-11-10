import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class A3 {

    public static void main(String[] args) throws Exception {
        if(args.length > 2) {
            for(int i = 2; i < args.length; i++) {
                File tempFile = new File(args[i]);
                // it check if the given file exists or not
                if(!tempFile.exists()) {
                    System.out.println("File Does Not Exist!");
                    return;
                }
            }
            // Creating an instance of this class to run the main program logic
            A3 A3 = new A3();
            A3.run(args);
        }
        else {
            System.out.println("Error in Command Line Arguments!");
            return;
        }
    }
    // Method to execute the main simulation
    private void run(String[] args) throws Exception {
        ArrayList<Process> fixed_Process = new ArrayList<>();
        ArrayList<Process> variable_Process = new ArrayList<>();
        // For each file name provided as an argument, It create Process objects
        for(int i = 2; i < args.length; i++) {
            fixed_Process.add(new Process(args[i], readFile(args[i])));
            variable_Process.add(new Process(args[i], readFile(args[i])));
        }

        // Createing the simulation for both fixed and variable strategies
        Fixed fixedCPU = new Fixed(Integer.valueOf(args[0]), Integer.valueOf(args[1]), fixed_Process, "Fixed-Local");
        fixedCPU.run();
        Variable variableCPU = new Variable(Integer.valueOf(args[0]), Integer.valueOf(args[1]), variable_Process, "Variable-Global");
        variableCPU.run();

        // Printtting out the reports for both simulations
        System.out.println(fixedCPU.get_Sim_Report());
        System.out.println(variableCPU.get_Sim_Report());
    }

    // Method to read the file and convert its content to a queue of page instructions
    private Queue<Integer> readFile(String filename) throws Exception {
        Queue<Integer> page_Instructions = new LinkedList<>();
        Scanner scanner = new Scanner(new File(filename));

        try {
            while (scanner.hasNext() && !scanner.next().equals("name:")) { }
            if (scanner.hasNext()) {
                scanner.next();
            }
            while (scanner.hasNext()) {
                String next = scanner.next();
                if (next.equals("end;")) {
                    break;
                }
                if (next.equals("page:") && scanner.hasNext()) {
                    int pageNum = Integer.valueOf(scanner.next().replace(";", ""));
                    if (page_Instructions.size() == 50) {
                        break;
                    }
                    page_Instructions.add(pageNum);
                }
            }
        } finally {
            scanner.close();
        }
        return page_Instructions;
    }
}
