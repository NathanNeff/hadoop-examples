package hadoop.examples.kafka;

import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.common.Node;


/**
 * Utility class to open an admin connection and sleep.
 * run this program more than once to see multiple instances.
 */
public class AdminClientExamples implements AutoCloseable {

    private AdminClient adminClient;

    public AdminClientExamples(Properties myProps)  {

        Properties properties = new Properties();
        properties.putAll(myProps);
        adminClient = AdminClient.create(properties);

    }
    
    public void listBrokers() {

        // Get brokers' IDs
        DescribeClusterResult dcr = adminClient.describeCluster();

        Collection<Node> nodes;
        try {
            nodes = dcr.nodes().get();
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        Node[] nodesArray = nodes.toArray(new Node[0]);
        for (Node n : nodesArray) {
            System.out.printf("Node ID: %d, HostID %s\n", 
                    n.id(),
                    n.host());
        }
        System.out.println(nodesArray);

    }

    public static void main(String args[]) {

        if (args.length < 1) {
            System.out.println("Usage: AdminClientExamples <brokerlist> ");
            System.exit(0);
        }

        // List of Message Brokers
        String bootstrap = args[0];
        
        Properties properties = new Properties();
        properties.setProperty("metadata.max.age.ms", "3000");
        properties.setProperty("bootstrap.servers", bootstrap);

        try(AdminClientExamples tool = new AdminClientExamples(properties)) {
            tool.listBrokers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        adminClient.close();
    }

}
