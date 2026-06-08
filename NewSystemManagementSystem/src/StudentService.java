import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class StudentService {
    public StudentService() {
        loadStudents();
    }
    private List<Student> students = new ArrayList<>();
    private int nextId = 1;

    public String addStudent(String name, int age, String course){
        String generatedId = "NIIT" + nextId;

        Student newStudent = new Student(name, age, course, generatedId);

        students.add(newStudent);

        nextId++;

        saveStudents();

        String successMessage = "Student added successfully with ID: " + generatedId;

        System.out.println(successMessage);

        return  successMessage;
    }

    public void viewAllStudents(){
        if (students.isEmpty()) {
            System.out.println("No students found");
        }
        else {
            System.out.println("\n==== STUDENTS LIST ====\n");
            for (int i = 0; i < students.size(); i++){
                Student stn = students.get(i);
                System.out.println("ID: " + stn.getId() +
                        " | Name: " + stn.getName() +
                        " | Course: " + stn.getCourse());
                System.out.println("---------------------" +
                        "--------------------------------------");
            }


        }
    }

    public String deleteStudent(String id) {
        boolean found = false;
        String message = "";

        for (int i = 0; i < students.size(); i++){
            if (students.get(i).getId().equalsIgnoreCase(id)) {
                students.remove(i);
                found = true;
                message = "Student with ID " + id + " has been deleted.";
                System.out.println(message);
                saveStudents();
                System.out.println("Data saved successfully");
                break;  // Stop looking once we find and delete them
            }
        }
        if (!found){
            message = "Error: Student with ID" + id + " not found.";
            System.out.println(message);
        }

        return message;
    }

    public void searchStudent(String id){
        boolean found = false;

        for (int i = 0; i < students.size(); i++){
            if (students.get(i).getId().equalsIgnoreCase(id)){
                Student stn = students.get(i);
                System.out.println("ID: " + stn.getId() +
                        " | Name: " + stn.getName() +
                        " | Course: " + stn.getCourse());
                found = true;
                break;
            }
        }

        if (!found){
            System.out.println("Error: Student with ID " + id + " not found.");
        }
    }

    public void saveStudents(){
        try (PrintWriter writer = new PrintWriter(new FileWriter("students.txt"))){
            for (Student st : students){
                // Formating the string: name@@age@@course@@id
                String line = st.getName() + "@@" +
                        st.getAge()  + "@@" +
                        st.getCourse() + "@@" +
                        st.getId();
                writer.println(line);
            }
            System.out.println("Data saved successfully to student.txt!");
        } catch (IOException e){
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public void loadStudents(){
        File file = new File("students.txt");

        // If the file doesn't exist yet (first time running),
        // just stop silently
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                // Split the line by "@@" into an array of Strings
                String[] data = line.split("@@");

                // data[0] = Name, data[1] = Age, data[2] = Course, data[3] = ID
                String name = data[0];
                int age = Integer.parseInt(data[1]); // Convert String "20" to int 20
                String course = data[2];
                String id = data[3];

                // Rebuild the student object and add it to the list
                Student student = new Student(name, age, course, id);
                students.add(student);

                // Update nextId tracker so new students get the
                // correct next ID number
                // Example: if ID is "NIIT3", extract the "3" and
                //  make nextId = 4
                int idNum = Integer.parseInt(id.replace("NIIT", ""));
                if (idNum >= nextId) {
                    nextId = idNum + 1;
                }
            }
            System.out.println("Data loaded successfully from students.txt!");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    public String getStudentsForWeb(){
        if (students.isEmpty()) {
            return "No students found in the registry.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("--- Live Web Student Registry ---\n\n");

        for (int i = 0; i < students.size(); i++) {
            Student stn = students.get(i);
            sb.append("ID: ").append(stn.getId())
                    .append(" | Name: ").append(stn.getName())
                    .append(" | Age: ").append(stn.getAge())
                    .append(" | Course: ").append(stn.getCourse())
                    .append("\n---------------------------------------\n");
        }
        return  sb.toString();
    }

    public String searchStudentForWeb(String id){
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equalsIgnoreCase(id)) {
                Student stn = students.get(i);
                return "--- Student Found ---\n\n" +
                        "ID: " + stn.getId() + "\n" +
                        "Name: " + stn.getName() + "\n" +
                        "Age: " + stn.getAge() + "\n" +
                        "Course: " + stn.getCourse() + "\n";
            }
        }
        return "Error: Student with ID " + id + " not found.";

        // If you have noticed this method is oddly similar to the
        // searchStudent() method 3 methods above this method.

        // This method "searchStudentForWeb()" is making this code
        // less concise but at least is makes it clear and readable

        // So i want to just point out some difference between this methods
        // one is for console mode
        // the other one is for web mode
        // one has void method
        // the other one has String method

        // This method "searchStudentForWeb()" method is just for
        // study sake so that is why i am not going to create any
        // similar methods like this. For instance addStudentForWeb
        // or deleteStudentForWeb, or updateStudentForWeb
        // I am not creating any method like those in this project
    }


}
