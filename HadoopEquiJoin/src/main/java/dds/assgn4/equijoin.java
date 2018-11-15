package dds.assgn4;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class equijoin {

  public static class EquiJoinKeyColumnMapper
       extends Mapper<Object, Text, Object, Text>{
    
    private Text word = new Text();
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      //Split by each line
      StringTokenizer itr = new StringTokenizer(value.toString(),"\n");
      while (itr.hasMoreTokens()) {
        word.set(itr.nextToken());
        //Split each element of line
        String[] rowElemnts = word.toString().split(", ");
        //Key: JoinColumnValue and Value: Entrie line/row
        context.write(new Text(rowElemnts[1]), word);
      }
    }
  }

  public static class EquiJoinReducer
       extends Reducer<Text,Text,Text,Text> {
    private Text result = new Text();

    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
      String output_str = "";
      int index = -1;
      String table1_name = "";
      String table2_name = "";
      List<String> table1_rows = new ArrayList<String>();
      List<String> table2_rows = new ArrayList<String>();
      //Loop through values and add in 2 different lists
      for (Text val : values) {
        index++;
        String[] tokens = val.toString().split(", ");
        if (table1_name.equals("")){
          table1_name = tokens[0];
        }else if (table2_name.equals("") && !(tokens[0].equals(table1_name))){
          table2_name = tokens[0]; 
        }
        if (tokens[0].equals(table1_name)){
          table1_rows.add(val.toString());
        }
        else if (tokens[0].equals(table2_name)){
          table2_rows.add(val.toString());
        }
      }
    //Nested loop to join the table
    for (String val1: table1_rows){
      for (String val2: table2_rows){
        output_str = output_str+val2+", "+val1+"\n";
      }
    }
    //Add only if there are more than 1 value for a key from Mapper
    if(index>0){
        output_str = output_str.trim();
        result.set(new Text(output_str));
        context.write(null,result);
    }else{
      return; 
    }
   }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "equi join");
    job.setJarByClass(equijoin.class);
    job.setMapperClass(EquiJoinKeyColumnMapper.class);
    job.setReducerClass(EquiJoinReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[1]));
    FileOutputFormat.setOutputPath(job, new Path(args[2]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}