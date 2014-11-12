import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;

public class SimpleDriver extends Configured implements Tool {
  public static void main(String[] args) throws Exception {
      int exitCode = ToolRunner.run(new Configuration(), new SimpleDriver(), args);
  }
  public int run (String [] args) throws Exception {
    if (args.length != 2) {
      System.out.printf(
          args.length + " - Usage: WordCount <input dir> <output dir>\n");
      System.exit(-1);
    }

    // Example of "new" way to instantiate Job
    Job job = Job.getInstance(getConf());
    job.setJarByClass(SimpleDriver.class);
    job.setJobName("New Job constuctor example");

    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    boolean success = job.waitForCompletion(true);
    return success ? 0 : 1;
  }
}
