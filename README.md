## Bark

Bark is a Data Quality solution for distributed data systems at any scale in both streaming or batch data context. It provides a framework process for defining data quality model, executing data quality measurement, automating data profiling and validation, as well as a unified data quality visualization across multiple data systems. You can access our home page [here](https://ebay.github.io/DQSolution/).


### Contact us
[DL-eBay-bark-dev@ebay.com](mailto://DL-eBay-bark-dev@ebay.com)

[Slack](https://ebay-eng.slack.com/messages/ebaysf-bark/)

### How to run in docker
1. Install [docker](https://www.docker.com)
2. Pull pre-built docker image directly and tag it as bark-env
    ```
    docker pull lliu13/bark-env
    docker tag lliu13/bark-env bark-env
    ```
    Optionally, you can build docker image yourself.
    First, build image [bark-base-env](https://github.com/eBay/DQSolution/docker/bark-base)
    ```
    docker build -t bark-base-env .
    ```
    Second, build image [bark-env](https://github.com/eBay/DQSolution/docker/bark)
    ```
    docker build -t bark-env .
    ```
3. Run docker image bark-env
    ```
    docker run -it -h sandbox --name bark -m 8G --memory-swap -1 \
    -p 40022:22 -p 47077:7077 -p 48088:8088 -p 48040:8040 -p 48042:8042 \
    -p 48080:8080 -p 27017:27017 bark-env bash
    ```
4. Now you can visit bark UI
    ```
    http://[your docker host IP address]:48080/
    ```

### How to build
1. git clone the repository of https://github.com/eBay/DQSolution
2. run "mvn install"

### How to deploy and run at local
1. Install jdk (1.7 or later versions)
2. Install Tomcat (7.0 or later versions)
3. Install MongoDB and import the collections
	```
	mongorestore /db:unitdb0 /dir:<dir of bark-doc>/db/unitdb0
	```

4. Install [Hadoop](http://mirror.stjschools.org/public/apache/hadoop/common/hadoop-2.7.2/hadoop-2.7.2.tar.gz) (2.7 or later versions), you can get some help [here](https://hadoop.apache.org/docs/r2.7.2/hadoop-project-dist/hadoop-common/SingleCluster.html).  
Make sure you have the permission to use command "hadoop".   
Create an empty directory in hdfs as your hdfs path, and then create running and history directory in it
    ```
    hadoop fs -mkdir <your hdfs path>
    hadoop fs -mkdir <your hdfs path>/running
    hadoop fs -mkdir <your hdfs path>/history
    ```
5. Install [Spark](http://www.webhostingjams.com/mirror/apache/spark/spark-2.0.0/spark-2.0.0-bin-hadoop2.7.tgz) (version 2.0.0), if you want to install Pseudo Distributed/Single Node Cluster, you can get some help [here](http://why-not-learn-something.blogspot.com/2015/06/spark-installation-pseudo.html).  
Make sure you have the permission to use command "spark-shell".
6. Install [Hive](http://mirrors.koehn.com/apache/hive/hive-2.1.0/apache-hive-2.1.0-bin.tar.gz) (version 2.1.0), you can get some help [here](https://cwiki.apache.org/confluence/display/Hive/GettingStarted#GettingStarted-RunningHive).  
Make sure you have the permission to use command "hive".
7. Create a working directory, and it will be **your local path** now.
8. In your local path, put your data into Hive.  
First, you need to create some directories in hdfs
    ```
    hadoop fs -mkdir /tmp
    hadoop fs -mkdir /user/hive/warehouse
    hadoop fs -chmod g+w /tmp
    hadoop fs -chmod g+w /user/hive/warehouse
    ```
Then, run the following command in **your local path**
    ```
    schematool -dbType derby -initSchema
    ```
Now you can put your data into Hive by running "hive" here. You can get sample data [here](https://github.com/eBay/DQSolution/tree/master/bark-doc/hive), then put into hive as following commands

    ```
    CREATE TABLE movie_source (
      movieid STRING,
      title STRING,
      genres STRING)
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY '\;'
    STORED AS TEXTFILE;

    LOAD DATA LOCAL INPATH '<your data path>/MovieLensSample_Source.dat' OVERWRITE INTO TABLE movie_source;

    CREATE TABLE movie_target (
      movieid STRING,
      title STRING,
      genres STRING)
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY '\;'
    STORED AS TEXTFILE;

    LOAD DATA LOCAL INPATH '<your data path>/MovieLensSample_Target.dat' OVERWRITE INTO TABLE movie_target;
    ```
    
    If you use hive command mode to input data, remember to create _SUCCESS file in hdfs table path as following
    
    ```
    hadoop fs -touchz /user/hive/warehouse/movie_source/_SUCCESS
    hadoop fs -touchz /user/hive/warehouse/movie_target/_SUCCESS
    ```
9. You can create your own model, build your jar file, and put it in **your local path**.  
(If you want to use our default models, please skip this step)
10. Currently we need to run the jobs automatically by script files, you need to set your own parameters in the script files and run it. You can edit the [demo script files](https://github.com/eBay/DQSolution/tree/master/bark-doc/hive/script/) as following

    [env.sh](https://github.com/eBay/DQSolution/tree/master/bark-doc/hive/script/env.sh)
    ```
    HDFS_WORKDIR=<your hdfs path>/running
    ```

    [bark_jobs.sh](https://github.com/eBay/DQSolution/tree/master/bark-doc/hive/script/bark_jobs.sh)
    ```
    spark-submit --class com.ebay.bark.Accu33 --master yarn --queue default --executor-memory 512m --num-executors 10 bark-models-0.0.1-SNAPSHOT.jar  $lv1dir/cmd.txt $lv1dir/
    spark-submit --class com.ebay.bark.Vali3 --master yarn --queue default --executor-memory 512m --num-executors 10 bark-models-0.0.1-SNAPSHOT.jar  $lv1dir/cmd.txt $lv1dir/
    ```

    These commands submit the jobs to spark, if you want to try your own model or modify some parameters, please edit it. 
    If you want to use your own model, change "bark-models-0.0.1-SNAPSHOT.jar" to "your path/your model.jar", and change the class name.  
    
    Put these script files in **your local path**, run bark_regular_run.sh as following
    ```
    nohup ./bark_regular_run.sh &
    ```

11. Open [application.properties](https://github.com/eBay/DQSolution/tree/master/bark-core/src/main/resources/application.properties) file, read the comments and specify the properties correctly. Or you can edit it as following
    ```
    env=prod
    job.local.folder=<your local path>/tmp
    job.hdfs.folder=<your hdfs path>
    job.hdfs.runningfoldername=running
    job.hdfs.historyfoldername=history
    ```
    If you set the properties as above, you need to make sure the directory "tmp" exists in your local path
12. Build the whole project and deploy bark-core/target/ROOT.war to tomcat
    ```
    mvn install -DskipTests
    ```
13. Then you can review the RESTful APIs through http://localhost:8080/api/v1/application.wadl

### How to develop
In dev environment, you can run backend REST service and frontend UI seperately. The majority of the backend code logics are in the [bark-core](https://github.com/eBay/DQSolution/tree/master/bark-core) project. So, to start backend, please import maven project Bark into eclipse, right click ***bark-core->Run As->Run On Server***

To start frontend, please follow up the below steps.

1. Open **bark-ui/js/services/services.js** file

2. Specify **BACKEND_SERVER** to your real backend server address, below is an example

    ```
    var BACKEND_SERVER = 'http://localhost:8080'; //dev env
    //var BACKEND_SERVER = 'http://localhost:8080/ROOT'; //dev env
    ```

3. Open a command line, run the below commands in root directory of **bark-ui**

   - npm install
   - bower install
   - npm start

4. Then the UI will be opened in browser automatically, please follow the [userGuide](https://github.com/eBay/DQSolution/tree/master/bark-doc/userguide.md), enjoy your journey!

**Note**: The front-end UI is still under development, you can only access some basic features currently.


### Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for details on how to contribute code, documentation, etc.
