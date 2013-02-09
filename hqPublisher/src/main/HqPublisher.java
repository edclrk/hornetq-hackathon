package main;

import javax.jms.*;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created with IntelliJ IDEA.
 * User: clarkee
 * Date: 2/9/13
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class HqPublisher {
    public static void main(String[] args) throws Exception {
        Connection connection = null;
        InitialContext initialContext = null;
        try
        {
            // /Step 1. Create an initial context to perform the JNDI lookup.
            initialContext = JmsHelper.getContext("jnp://localhost:1099");

            // Step 2. perform a lookup on the topic
            Topic topic = (Topic)initialContext.lookup("/topic/exampleTopic");

            // Step 3. perform a lookup on the Connection Factory
            ConnectionFactory cf = (ConnectionFactory)initialContext.lookup("/ConnectionFactory");

            // Step 4. Create a JMS Connection
            connection = cf.createConnection();

            // Step 5. Create a JMS Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Step 6. Create a Message Producer
            MessageProducer producer = session.createProducer(topic);



            // Step 11. Start the Connection
            connection.start();

            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

            while (true){
                String s = bufferRead.readLine();

                // Step 9. Create a Text Message
                TextMessage message = session.createTextMessage(s);

                // Step 10. Send the Message
                producer.send(message);

                System.out.println("Sent message: " + message.getText());
            }


        }
        finally
        {
            // Step 14. Be sure to close our JMS resources!
            if (connection != null)
            {
                connection.close();
            }

            // Also the initialContext
            if (initialContext != null)
            {
                initialContext.close();
            }
        }
    }
}
