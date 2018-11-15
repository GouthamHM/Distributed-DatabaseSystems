Logic:

    Map: Map each key to row
        Eg: {2: 'R, 2, Don, Larson, Newark, 555-3221',
            2: 'S, 2, 20000, 1800, part1',
            4: 'S, 4, 22000, 7000, part1'}

    Reduce: Obtain all values for a key and combine each row from table1 with each row in table2 
    Exclude elemnts which have only 1 element in values list.
        Eg: Input 
            {2: ['R, 2, Don, Larson, Newark, 555-3221','S, 2, 20000, 1800, part1']
            4:  ['S, 4, 22000, 7000, part1']}
            Output:
            R, 2, Don, Larson, Newark, 555-3221,S, 2, 20000, 1800, part1

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

    >./hadoop jar < path to equijoin jar file> equijoin < hdfs path to input folder> < hdfs path to output folder> 

    You might encounter  Output directory < hdfs path to output folder>  already exists

    >./hadoop fs -rm -f -r < hdfs path to output folder>

    To verfiy results 

    >./hadoop fs -cat < hdfs path to output folder>/part-r-00000< part-no >
