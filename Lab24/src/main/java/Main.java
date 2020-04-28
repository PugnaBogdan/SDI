import Ui.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Rares.
 */
public class Main {
    public static void main(String args[]) throws Exception {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "Config"
                );

        UserInterface ui = context.getBean(UserInterface.class);
        ui.runConsole();
    }
}
