package solution;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class TryKill extends Configured implements Tool {

        public static void main(String[] args) throws Exception {

                int exitCode = ToolRunner.run(new Configuration(), new TryKill(), args);
                System.exit(exitCode);
        }

        @Override
        public int run(String[] args) throws Exception {

                if (args.length != 1) {
                        System.out.printf("Usage: TryKill <input dir>\n");
                        System.exit(-1);
                }

                Job job = new Job(getConf());
                job.setJarByClass(TryKill.class);
                if (null == job.getJobName()) {
                        job.setJobName("TryKill");
                }
                job.setNumReduceTasks(0);

                NLineInputFormat.setInputPaths(job, new Path(args[0]));
                job.setInputFormatClass(NLineInputFormat.class);

                job.setOutputFormatClass(NullOutputFormat.class);

                job.setMapperClass(KillMapper.class);

                boolean success = job.waitForCompletion(true);
                return success ? 0 : 1;
        }

}
