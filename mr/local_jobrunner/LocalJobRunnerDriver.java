import static org.apache.hadoop.mapred.Task.Counter.MAP_INPUT_RECORDS;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

// Example of deleting data that exists on local
// filesystem, only if the FS is the local filesystem
// Try running it with -fs file:/// and then without -fs file:///
public class LocalJobRunnerDriver extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {

		String input, output;
		if (args.length == 2) {
			input = args[0];
			output = args[1];
		} else {
			input = "/home/training/localdata";
			output = "/home/training/localoutput/badguys";
		}
		JobConf conf = new JobConf(getConf(), ImageCounter.class);

		FileSystem fs = FileSystem.get(conf);

		if (fs instanceof LocalFileSystem) {
			// LOOK OUT!!!!!!!
			Path outputPath = new Path(output);
			if (fs.exists(outputPath)) {
				System.out.println("I'm deleting " + outputPath);
				fs.delete(outputPath, true);
			}
			else {
				
				System.out.println("Not deleting");
			}
		}
                else {
                        System.out.println("We're not using a local filesystem");
                }

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new ImageCounter(), args);
		System.exit(exitCode);
	}
}
