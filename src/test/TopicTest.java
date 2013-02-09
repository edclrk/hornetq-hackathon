import com.sun.corba.se.impl.orb.ParserTable;
import org.junit.Test;

import javax.jms.*;
import javax.naming.InitialContext;
import java.org.hornetq.common.example.HornetQExample;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: clarkee
 * Date: 2/9/13
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class TopicTest {

    @Test
    public void runExampleTest() throws Exception {
       boolean result =  runExample();
        assertTrue(result);
    }
    public boolean runExample() throws Exception
    {
        Connection connection = null;
        InitialContext initialContext = null;
        try
        {
            // /Step 1. Create an initial context to perform the JNDI lookup.
            initialContext = getContext("jnp://localhost:1099");

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

            // Step 7. Create a JMS Message Consumer
            MessageConsumer messageConsumer1 = session.createConsumer(topic);

            // Step 8. Create a JMS Message Consumer
            MessageConsumer messageConsumer2 = session.createConsumer(topic);

            // Step 9. Create a Text Message
            TextMessage message = session.createTextMessage("This is a text message");

            System.out.println("Sent message: " + message.getText());

            // Step 10. Send the Message
            producer.send(message);

            // Step 11. Start the Connection
            connection.start();

            // Step 12. Receive the message
            TextMessage messageReceived = (TextMessage)messageConsumer1.receive();

            System.out.println("Consumer 1 Received message: " + messageReceived.getText());

            // Step 13. Receive the message
            messageReceived = (TextMessage)messageConsumer2.receive();

            System.out.println("Consumer 2 Received message: " + messageReceived.getText());

            return true;
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

    protected InitialContext getContext(final String  serverId) throws Exception
    {
        System.out.println("using " + serverId + " for jndi");
//        HornetQExample.log.info("using " + args[serverId] + " for jndi");
        Properties props = new Properties();
        props.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
        props.put("java.naming.provider.url", serverId);
        props.put("java.naming.factory.url.pkgs","org.jboss.naming:org.jnp.interfaces");
        return new InitialContext(props);
    }


}
