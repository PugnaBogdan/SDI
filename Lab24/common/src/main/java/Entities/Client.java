package Entities;

/**
 * @author Rares.
 */
public class Client extends BaseEntity<Integer> {

    private int clientId;
    private String name;
    private int age;

    public Client(int initId, String initName, int initAge)
    {
        clientId=initId;
        name=initName;
        age=initAge;
    }

    public Client() {

    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public Integer getId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
