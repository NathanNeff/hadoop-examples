package solution.mr;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import solution.domain.MapperFunction;


public class IdentityMapper extends Mapper<Text, Text, Text, Text> {

	
  /**
   * Example input line:
   * 96.7.4.14 - - [24/Apr/2011:04:20:11 -0400] "GET /cat.jpg HTTP/1.1" 200 12433
   *
   */
  @Override
  public void map(Text key, Text value, Context context)
      throws IOException, InterruptedException {
  
 	  context.write( key, value);
  
  }
}
