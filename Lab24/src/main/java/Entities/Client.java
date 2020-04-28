package Entities;
/**
 * @author Rares.
 */
import javax.persistence.Entity;

/**
 * @author Rares.
 */
@Entity
public class Client extends BaseEntity<Integer> {

    private int clientId;
    private String name;
    private int age;

    public Client( String initName, int initAge)
    {
        name=initName;
        age=initAge;
    }

    public Client() {

    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + getId() +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
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
