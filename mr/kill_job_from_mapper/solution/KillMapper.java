package solution;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapred.JobID;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.RunningJob;

public class KillMapper extends Mapper<LongWritable, Text, Text, Text> {
        
        @Override 
        public void map(LongWritable key, Text val, Context context) 
                throws IOException, InterruptedException {

                String line = val.toString();
                if (! line.equals("100")) {
                        killJob(context, line);
                }
        }

        private void killJob(Context context, String line) {
                RunningJob job;
                JobClient jc;

                System.out.println("I'm taking my ball and going home!");
                // Good luck finding this Mapper if there's 100 Mappers.
                // Probably should use a counter to indicate which mapper killed the job
                context.setStatus("I'm bringing down this job, because I found:  '" + line +
                        "', yours truly,  " + context.getTaskAttemptID());

                try {
                        jc = new JobClient(context.getConfiguration());
                } catch (Exception e) {
                        System.err.println("Error getting the JobClient:  " + e.getStackTrace());
                        return;
                }

                JobID jobId = JobID.downgrade(context.getJobID());
                System.out.println("Downgraded Job ID is:  " + jobId);
                System.out.println("Regular Job ID is:  " + context.getJobID());

                try {
                        job = jc.getJob(jobId);
                        job.killJob();

                } catch (Exception e) {
                        System.err.println("Error while getting job or killing job" + e);
                        e.printStackTrace(System.err);
                }
        }


}
