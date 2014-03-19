package solution;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;
import org.apache.hadoop.mapreduce.Job;

import solution.mr.CountReducer;
import solution.mr.LogMonthMapper;
import solution.mr.IdentityMapper;
import solution.mr.SumReducer;
import solution.mr.WordMapper;

public class ProcessLogs {

  public static void main(String[] args) throws Exception {

	Configuration conf = new Configuration();
	  
    if (args.length != 2) {
      System.out.printf("Usage: ProcessLogs <input dir> <output dir>\n");
      System.exit(-1);
    }

    Path inputPath = new Path(args[0]);
    Path partitionFile = new Path(args[1] + "_partitions.lst");
    Path outputStage = new Path(args[1] + "_staging");
    Path outputOrder = new Path(args[1]);

    // Configure job to prepare for sampling
    Job sampleJob = new Job(conf, "TotalOrderSortingStage");
    sampleJob.setJarByClass(ProcessLogs.class);

    // Use the mapper implementation with zero reduce tasks
    sampleJob.setMapperClass(LogMonthMapper.class);
    sampleJob.setNumReduceTasks(0);

    sampleJob.setOutputKeyClass(Text.class);
    sampleJob.setOutputValueClass(Text.class);

    TextInputFormat.setInputPaths(sampleJob, inputPath);

    // Set the output format to a sequence file
    sampleJob.setOutputFormatClass(SequenceFileOutputFormat.class);
    SequenceFileOutputFormat.setOutputPath(sampleJob, outputStage);

    // Submit the job and get completion code.
    int code = sampleJob.waitForCompletion(true) ? 0 : 1;
    
    
    if (code == 0) {
        Job orderJob = new Job(conf, "Process Logs");
        orderJob.setJarByClass(ProcessLogs.class);
 
        // Here, use the identity mapper to output the key/value pairs in
        // the SequenceFile
        orderJob.setMapperClass(IdentityMapper.class);
        orderJob.setReducerClass(CountReducer.class);

        // Set the number of reduce tasks to an appropriate number for the
        // amount of data being sorted
        orderJob.setNumReduceTasks(10);

        // Use Hadoop's TotalOrderPartitioner class
        orderJob.setPartitionerClass(TotalOrderPartitioner.class);

        // Set the partition file
        TotalOrderPartitioner.setPartitionFile(orderJob.getConfiguration(),
                partitionFile);

        orderJob.setMapOutputKeyClass(Text.class);
        orderJob.setMapOutputValueClass(Text.class);
     
        orderJob.setOutputKeyClass(Text.class);
        orderJob.setOutputValueClass(IntWritable.class);
 

        // Set the input to the previous job's output
        orderJob.setInputFormatClass(SequenceFileInputFormat.class);
        SequenceFileInputFormat.setInputPaths(orderJob, outputStage);

        // Set the output path to the command line parameter
        TextOutputFormat.setOutputPath(orderJob, outputOrder);

        // Set the separator to an empty string
        orderJob.getConfiguration().set(
                "mapred.textoutputformat.separator", "");

        // Use the InputSampler to go through the output of the previous
        // job, sample it, and create the partition file
        InputSampler.writePartitionFile(orderJob,
                new InputSampler.RandomSampler(1, 10000));

        // Submit the job
        code = orderJob.waitForCompletion(true) ? 0 : 2;
    }

    // Clean up the partition file and the staging directory
    // FileSystem.get(new Configuration()).delete(partitionFile, false);
    // FileSystem.get(new Configuration()).delete(outputStage, true);

    System.exit(code);
    
 
  }
}
