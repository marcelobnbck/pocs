import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamOperations {

    public static void main(String[] args) {

        // 1. Creating a Stream
        List<Person> people = new ArrayList<>(Arrays.asList(
                new Person("Maria", 30, "Sao Leopoldo", Arrays.asList("reading", "hiking")),
                new Person("Joao", 17, "Sapucaia do Sul", Arrays.asList("gaming", "coding")),
                new Person("Jose", 25, "Sao Leopoldo", Arrays.asList("cooking", "reading", "photography")),
                new Person("Juvenal", 40, "Novo Hamburgo", Arrays.asList("cycling", "hiking")),
                new Person("Jhennyfer", 19, "Sapucaia do Sul", Arrays.asList("painting", "gaming")),
                new Person("Praxedes", 22, "Sao Leopoldo", Arrays.asList("swimming", "cooking"))
        ));

        System.out.println("\nOriginal List of People:");
        people.forEach(System.out::println);

        // 2. filter(Predicate)
        List<Person> adults = people.stream()
                .filter(person -> person.getAge() >= 18) // Lambda as Predicate
                .collect(Collectors.toList()); // Collect results into a List
        System.out.println("Adults: " + adults);

        // Get people from Sao Leopoldo
        List<Person> slResidents = people.stream()
                .filter(p -> p.getCity().equals("Sao Leopoldo"))
                .collect(Collectors.toList());
        System.out.println("Sao Leopoldo Residents: " + slResidents);

        // 3. map(Function)
        // Get names of all people
        List<String> names = people.stream()
                .map(Person::getName) // Method reference as Function
                .collect(Collectors.toList());
        System.out.println("All Names: " + names);

        // Get ages of all people
        List<Integer> ages = people.stream()
                .map(Person::getAge) // Method reference as Function
                .collect(Collectors.toList());
        System.out.println("All Ages: " + ages);

        // Get names of adults from Sao Leopoldo
        List<String> adultSLNames = people.stream()
                .filter(p -> p.getAge() >= 18 && p.getCity().equals("Sao Leopoldo"))
                .map(Person::getName) // Transform Person object to String name
                .collect(Collectors.toList());
        System.out.println("Names of Adults from Sao Leopoldo: " + adultSLNames);

        // 4. flatMap(Function that returns a Stream)

        // Get all hobbies from all people, without duplicates
        Set<String> allUniqueHobbies = people.stream()
                .flatMap(person -> person.getHobbies().stream()) // Transforms each Person into a Stream of their hobbies
                .collect(Collectors.toSet()); // Collect into a Set to ensure uniqueness
        System.out.println("All Unique Hobbies: " + allUniqueHobbies);

        // Get all hobbies from people aged 20 or older, with duplicates allowed
        List<String> hobbiesOfAdults = people.stream()
                .filter(person -> person.getAge() >= 20)
                .flatMap(person -> person.getHobbies().stream()) // Each person's hobbies become part of the main stream
                .collect(Collectors.toList());
        System.out.println("Hobbies of people aged 20+: " + hobbiesOfAdults);

        // 5. collect(Collector)

        // Collect into a List (already seen above)
        List<String> allCities = people.stream()
                .map(Person::getCity)
                .distinct() // Remove duplicate cities
                .collect(Collectors.toList());
        System.out.println("Unique Cities: " + allCities);

        // Collect into a Set (already seen above)
        Set<String> uniqueNames = people.stream()
                .map(Person::getName)
                .collect(Collectors.toSet());
        System.out.println("Unique Names: " + uniqueNames);

        // Collect into a Map: Map names to ages
        Map<String, Integer> nameToAgeMap = people.stream()
                .collect(Collectors.toMap(Person::getName, Person::getAge));
        System.out.println("Name to Age Map: " + nameToAgeMap);

        // Collect into a Map: Group people by city
        Map<String, List<Person>> peopleByCity = people.stream()
                .collect(Collectors.groupingBy(Person::getCity));
        System.out.println("People Grouped by City:");
        peopleByCity.forEach((city, personList) ->
                System.out.println("  " + city + ": " + personList
                        .stream()
                        .map(Person::getName)
                        .collect(Collectors.joining(", ")))
        );

        // Collect into a Map: Count people by city
        Map<String, Long> cityCounts = people.stream()
                .collect(Collectors.groupingBy(Person::getCity, Collectors.counting()));
        System.out.println("City Counts: " + cityCounts);

    }
}
