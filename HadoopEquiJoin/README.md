
Installing:

    $sudo apt install openjdk-8-jdk-headless

    Set Open-jdk path as JAVA_HOME
    add JAVA_HOME="<open-jdk path>" in /etc/environment
    > source /etc/environment
    > export JAVA_HOME=<open-jdk path>
    Verify by > echo $JAVA_HOME
    
    Setup ssh so that passwordless ssh login happens from Master to master and slaves if any and vice-versa.

    Install Hadoop and core-site.xml and add masters and slaves /etc/hadoop/slaves. 
    Run > ./bin/hadoop namenode -format
    Run > ./sbin/start-dfs.sh

Running:

    mvn clean install

    Check if target folder and check if EquiJoin-1.0-SNAPSHOT.jar is created

    Navigate to hadoop bin folder 

    >./hadoop jar < path to equijoin jar file> EquiJoin < hdfs path to input folder> < hdfs path to output folder> 

    You might encounter  Output directory < hdfs path to output folder>  already exists

    >./hadoop fs -rm -f -r < hdfs path to output folder>

    To verfiy results 

    >./hadoop fs -cat < hdfs path to output folder>/part-r-00000< part-no >
