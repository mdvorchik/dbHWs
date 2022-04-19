public class Person {
    private String id;
    private String balance;
    private Integer age;
    private String name;
    private String gender;
    private String address;

    public Person(String id, String balance, Integer age, String name, String gender, String address) {
        this.id = id;
        this.balance = balance;
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getBalance() {
        return balance;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", balance='" + balance + '\'' +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
