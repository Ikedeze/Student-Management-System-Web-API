public class Student {
    private String name;
    private int age;
    private String course;
    private String id;

    public Student(String name, int age, String course, String id) {
        this.name = name;
        this.age = age;
        this.course = course;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCourse() {
        return course;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setId(String id) {
        this.id = id;
    }
}
