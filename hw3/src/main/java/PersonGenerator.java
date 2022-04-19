public class PersonGenerator {
    public Person generatePerson(Integer id) {
        String balance = "$" + (Math.random() * 10000);
        Integer age = (int) (Math.random() * 130);
        String name = "Alex" + (Math.random() * 130) + (Math.random() * 130) + (Math.random() * 130) + (Math.random() * 130);
        String gender = Math.random() * 100 > 50 ? "female" : "male";
        String street = "Andreas Schmidt\n" +
                "\n" +
                "Hauptstraße 18a\n" +
                "\n" + (Math.random() * 130) + (Math.random() * 130) + (Math.random() * 130) + (Math.random() * 130) +
                " Braunschweig\n" +
                "\n" +
                "Deutschland";
        return new Person(id.toString(), balance, age, name, gender, street);
    }
}
