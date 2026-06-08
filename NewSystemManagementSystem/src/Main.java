import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;
/*
Amigoscode: He has some of the absolute best, cleanest tutorials specifically combining Spring Boot and MongoDB from scratch.
Java Brains: Excellent for deeply understanding the core lifecycle concepts of Spring Boot.
Daily Code Buffer: Great for quick, practical step-by-step API building.*/

// REMEMBER TO ADD UPDATE FEATURE LATER IN THE FUTURE
public class Main {
    // Makes the service global to this class so the web Waiter can see it too
    private static StudentService service = new StudentService(); // Our "Brain"
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("====== SYSTEM BOOT ALLOCATION =======");
        System.out.println("1. Launch Standard Console Mode");
        System.out.println("2. Launch Web API Server Mode");
        System.out.print("Select Application Environment: ");

        try {
            String bootChoice = sc.nextLine();
            int mode = Integer.parseInt(bootChoice);

            if (mode == 1) {
                // Kick off your exact original console loop
                runConsoleMode(sc);
            } else if (mode == 2) {
                // Kick off the brand new Web API Server
                runWebMode();
            } else {
                System.out.println("Invalid environment selection. Shutting down.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input type. System shutdown executed.");
        } catch (IOException e) {
            System.out.println("Server configuration error: " +
                    e.getMessage());
        } finally {
            sc.close();
        }
    }

    // ---- MODE 1: YOUR ORIGINAL COMPLETE CONSOLE LOGIC ---
    public static void runConsoleMode(Scanner sc){
            boolean running = true;

        while (running){
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Delete Students");
            System.out.println("4. Search Students");
            System.out.println("0. Exit");
            System.out.println("Choose an option: ");

            try {               //  ORRR          while  (hasNextInt)
                String stringChoice = sc.nextLine();
                int choice = Integer.parseInt(stringChoice);

                switch (choice) {
                    case 1 -> {
                        try {
                        String name = getValidInput(sc, "Input Name: ");

                        System.out.println("Input Age: ");
                        String ageChoice = sc.nextLine();
                        int age = Integer.parseInt(ageChoice);

                        String course = getValidInput(sc, "Input Course: ");

                        service.addStudent(name, age, course);

                        } catch (NumberFormatException badUserDate){
                            System.out.println("Characters are not allowed in age!!! Try again.");
                        }
                    }
                    case 2 -> service.viewAllStudents();

                    case 3 -> {
                        System.out.print("Enter ID to delete: ");
                        String deleteId = sc.nextLine();
                        service.deleteStudent(deleteId);
                    }

                    case 4 -> {
                        System.out.print("Enter ID to search: ");
                        String searchId = sc.nextLine();
                        service.searchStudent(searchId);
                    }

                    case 0 -> {
                        running = false;
                        System.out.println("Exiting... Goodbye!");
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberFormatException badUserDate){
                System.out.println("Characters not allowed!!! Try again.");
            }
        }
    }

    // --- MODE 2: THE WEB API SERVER INITIALIZATION
    public static void runWebMode() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Context routing mapping directly to our custom handler
        server.createContext("/students", new WebStudentHandler());
        server.createContext("/search", new WebSearchHandler());
        server.createContext("/add", new WebAddHandler());
        server.createContext("/delete", new WebDeleteHandler());
        server.createContext("/exit", new WebExitHandler());


        server.setExecutor(null);
        System.out.println("\n[WEB MODE ACTIVE] Server initialized on Port 8000!");
        System.out.println("- View All:     http://localhost:8000/students");
        System.out.println("- Search:       http://localhost:8000/search?id=NIIT1");
        System.out.println("- Add Student:  http://localhost:8000/add?name=Ike&age=25&course=Java");
        System.out.println("- Delete:       http://localhost:8000/delete?id=NIIT1");
        System.out.println("- Close Server: http://localhost:8000/exit");
        server.start();
    }

    // --- THE WEB WAITER HANDLER ---
    static class WebStudentHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // OPTION A:  We directly call the brain's custom web layout method
            String response = service.getStudentsForWeb();

//            exchange.sendResponseHeaders(200, response.length());
//            OutputStream os = exchange.getResponseBody();
//            os.write(response.getBytes());
//            os.close();

            // Instead i will use this method
            sendResponse(exchange, response);
        }
    }

    static class WebSearchHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery(); // Grabs everything after the "?"
            String response;

            // Check if the user actually typed a query parameter
            if (query != null && query.contains("=")) {
                // Option B: Split the string "id=NIIT1" at the "=" marker
                String[] queryParts = query.split("="); //Option b is not working again just like in simple-java-api
                String searchId = queryParts[1]; // Extracts "NIIT1"

                // Ask the brain to look up the student and format the response
                response = service.searchStudentForWeb(searchId);
            } else {
                response = "Error = Please provide a student ID in the URL. Example:" +
                        " /search?id=NIIT1";
            }

            sendResponse(exchange,response);
        }
    }

    static class WebAddHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery(); // Grabs "name=Ike&age=25&course=Java"
            String response;

            try {
                if (query != null && query.contains("&")) {
                    // Split 1: Break by "&" to isolate the key-value chunks
                    String[] pairs = query.split("&");

                    // Extract the values by splitting each chunk at the "=" sign

                    /*
                    String name = pairs.split("=");
                    String ageString = pairs.split("=");
                    String course = pairs.split("=");
                    */
                    String[] nameChunks = pairs[0].split("=");
                    String name = nameChunks[1];


                    String[] ageStringChunks = pairs[1].split("=");
                    String ageString = ageStringChunks[1];

                    String[] courseChunks = pairs[2].split("=");
                    String course = courseChunks[1];



                    // Convert the age string into a real int, just like console mode!
                    int age = Integer.parseInt(ageString);

                    // Call your updated console method that now returns a string!
                    String result = service.addStudent(name, age, course);

                    response = "- [Web API Client Entry Success]\n\n" + result;
                } else {
                    response = "Error: Missing parameters. Format format: /add?name=YourName&age=20&course=Java";
                }
            } catch (NumberFormatException e) {
                response = "Error: Age must be a valid number string. Digits only!";
            } catch (ArrayIndexOutOfBoundsException e) {
                response = "Error: Invalid data format supplied in URL query parameters.";
            }

            sendResponse(exchange, response);
        }
    }

    static class WebDeleteHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException{
            String query = exchange.getRequestURI().getQuery();
            String response;

            if (query != null && query.contains("=")) {
                String [] queryParts = query.split("=");
                String deleteId = queryParts[1];

                // Calls updated method which returns the string result
                response = "- [Web API Deletion Request]\n\n" + service.deleteStudent(deleteId);
            } else {
                response = "Error: Please provide an ID to delete. Example: /delete?id=NIIT1";
            }
            sendResponse(exchange,response);
        }
    }

    static class WebExitHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws  IOException{
            String response = "- Server shutdown command received. Closing Environment. Goodbye!";
            sendResponse(exchange, response);

            System.out.println("\n[SYSTEM] Shutdown initiated via remote web client request.");
            // Short delay to allow the browser to completely render the response before dropping the execution thread
            try {Thread.sleep(1000); }
            catch (InterruptedException ignored) {}
            System.exit(0);
        }
    }

    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    public static String getValidInput(Scanner sc, String prompt){
        String input;
        while (true){
            System.out.println(prompt); // ex: "Input Name:
            input = sc.nextLine();

            if (input.contains("@@")){
                System.out.println("Error: Wrong input, try again without '@@");
            } else if (input.trim().isEmpty()){
                System.out.println("Error: Input cannot be empty.");
            } else {
                return input; // Success! Return the clean string
            }
        }
    }
}

// ANOTHER WAY TO DO EXCEPTION HANDLING FOR OPTIONS

/* int choice = -1; // Default safe value

System.out.print("Choose an option: ");
String input = sc.nextLine(); // Read the whole input as text

try {
    choice = Integer.parseInt(input); // Try converting text to a number
} catch (NumberFormatException e) {
    // If they typed letters, this block catches it!
    System.out.println("Error: Please enter a valid number, not text.");
    continue; // Skips the switch statement and restarts the loop
}

switch (choice) {
    // Your cases (1, 2, 3, 4, 0) stay exactly the same here!
}*/

