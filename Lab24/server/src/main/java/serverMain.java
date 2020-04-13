
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Pugna.
 */
public class serverMain {
    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException, SQLException {

        System.out.println("server working");
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "Config"
                );

    }
}

