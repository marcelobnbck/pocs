import java.util.List;
import java.util.Objects;

public class Person {
    private String name;
    private int age;
    private String city;
    private List<String> hobbies; // List of hobbies for flatMap demo

    public Person(String name, int age, String city, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.hobbies = hobbies;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCity() { return city; }
    public List<String> getHobbies() { return hobbies; }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", city='" + city + "', hobbies=" + hobbies + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && name.equals(person.name) && city.equals(person.city) && hobbies.equals(person.hobbies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, city, hobbies);
    }
}
