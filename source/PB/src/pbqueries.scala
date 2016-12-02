

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.hadoop.util
import org.apache.spark.sql.SQLContext
import java.io.FileWriter
import java.io.File
import org.apache.spark.sql.catalyst.expressions
import org.apache.spark.mllib.clustering.{LDA}

object pbqueries {
  def main(args: Array[String]) {
    val sc = new SparkContext("local[2]", "PbSpark")
    val sqlContext = new SQLContext(sc)
    /*
    val tweets = sqlContext.jsonFile("C:\\Users\\putha\\Desktop\\sujitha\\prin of big data\\project\\Sports_tweets.json")
    tweets.registerTempTable("testtweets")
*/
    println("hello ");

    // val textFile = sc.textFile("C:\\Users\\putha\\Desktop\\sujitha\\prin of big data\\project\\Sports_tweets.json");

    /*
    //Query1 - Retweet Query

     val tweets = sqlContext.jsonFile("C:\\Users\\putha\\Desktop\\sujitha\\prin of big data\\project\\Sports_tweets.json")
    tweets.registerTempTable("testtweets")

    def time[A](f: => A) = {
      val s = System.nanoTime
      val ret = f
      println("Execution time for analysing retweeted count is : " + (System.nanoTime - s) / 1e9 + " sec")
      ret
    }

    time {
      // Retweet query
      val re_tweet_query = sqlContext.sql("select user.name as name, retweeted_status.retweet_count as cnt from testtweets where user.name is not NULL order by cnt desc limit 20")
      re_tweet_query.show(false)
      re_tweet_query.save("retweeted_queries", "json")
    }
    */

    /*
    // Query 2- Source prefer

    val textFile = sc.textFile("C:\\Users\\putha\\Desktop\\sujitha\\prin of big data\\project\\Sports_tweets.json");

    def time[A](f: => A) = {
      val s = System.nanoTime
      val ret = f
      println("Execution time for analysing which source user prefers is : " + (System.nanoTime - s) / 1e9 + " sec")
      ret
    }
    time{
      val android= (textFile.filter(line => line.contains("Twitter for Android")).count())
      val iphone= (textFile.filter(line => line.contains("Twitter for iPhone")).count())
      val ifttt= (textFile.filter(line => line.contains("IFTTT")).count())
      val roundteam= (textFile.filter(line => line.contains("RoundTeam")).count())
      val twitterfeed= (textFile.filter(line => line.contains("twitterfeed")).count())
      val webClient= (textFile.filter(line => line.contains("Twitter Web Client")).count())
      val ipad= (textFile.filter(line => line.contains("Twitter for iPad")).count())
      val instagram= (textFile.filter(line => line.contains("Instagram")).count())
      val tweetdeck= (textFile.filter(line => line.contains("TweetDeck")).count())
      val hootsuite= (textFile.filter(line => line.contains("Hootsuite")).count())
      val facebook= (textFile.filter(line => line.contains("Facebook")).count())
      val paper= (textFile.filter(line => line.contains("Paper.li")).count())
      val vine= (textFile.filter(line => line.contains("Vine - Make a Scene")).count())
      val sports= (textFile.filter(line => line.contains("Sports Teller")).count())

      println(("Number of Android users : %s\n Number of iPhone users : %s \n " +
        "Number of IFTTT users : %s \n Number of RoundTeam users : %s \n " +
        "Number of twitterfeed users : %s \n Number of Web users : %s\n Number of iPad users : %s \n " +
        "Number of Instagram users : %s \n Number of TweetDeck users : %s \n Number of Hootsuite users : %s \n " +
        "Number of Facebook users : %s \n Number of Paper.li users : %s \n Number of Vine - Make a Scene users : %s \n " +
        "Number of Sports Teller users : %s \n").format(android,iphone,ifttt,roundteam,twitterfeed,webClient,ipad,instagram,tweetdeck,hootsuite,facebook,paper,vine,sports))

    }
    */

/*
    // Query 3- Sports Anakysis

    val textFile = sc.textFile("C:\\Users\\putha\\Desktop\\sujitha\\prin of big data\\project\\Sports_tweets.json");
    def time[A](f: => A) = {
      val s = System.nanoTime
      val ret = f
      println("Execution time for analysing comments on various sports is : " + (System.nanoTime - s) / 1e9 + " sec")
      ret
    }

    time{
      val cricket= (textFile.filter(line => line.contains("#cricket")).count())
      val Soccer= (textFile.filter(line => line.contains("#soccer")).count())
      val AmericanFootball= (textFile.filter(line => line.contains("#AmericanFootball")).count())
      val Basketball= (textFile.filter(line => line.contains("#Basketball")).count())
      val Olympics= (textFile.filter(line => line.contains("#Olympics")).count())
      val Rugby= (textFile.filter(line => line.contains("#Rugby")).count())
      val Golf= (textFile.filter(line => line.contains("#Golf")).count())
      val Baseball= (textFile.filter(line => line.contains("#Baseball")).count())
      val Tennis= (textFile.filter(line => line.contains("#Tennis")).count())
      val IceHockey= (textFile.filter(line => line.contains("#IceHockey")).count())
      val Volleyball= (textFile.filter(line => line.contains("#Volleyball")).count())

      println(("Number of comments on cricket are : %s \n Number of comments on soccer are : %s \n " +
        "Number of comments on AmericanFootball are : %s \n Number of comments on Basketball are : %s \n " +
        "Number of comments on Olympics are : %s \n Number of comments on Rugby are : %s \n Number of comments on Golf are : %s \n " +
        "Number of comments on Baseball are : %s \n Number of comments on Tennis are : %s \nNumber of comments on IceHockey are : %s \n " +
        "Number of comments on Volleyball are : %s").format(cricket,Soccer,AmericanFootball,Basketball,Olympics,Rugby,Golf,Baseball,Tennis,IceHockey,Volleyball))
    }
    */

    /*
    // Query 4 - Comparing hashtags with blackboard

    val tweets = sqlContext.jsonFile("C:\\Users\\putha\\Desktop\\sujitha\\prin of big data\\project\\Sports_tweets.json")
    tweets.registerTempTable("testtweets")

    val anlysis = sqlContext.jsonFile("C:\\Users\\putha\\Desktop\\sujitha\\prin of big data\\project\\blackboard_hashtags.json")
    anlysis.registerTempTable("hashtags")
    anlysis.withColumnRenamed("test","_corrupt_record")

    // Renaming the column name with tag
    val df = anlysis.toDF().withColumnRenamed("_corrupt_record", "tag")
    df.registerTempTable("hash1")

    //selecting the hashtags presnt in hash1 and counts
    val blackboard_hashtag = sqlContext.sql("select hash1.tag,count(testtweets.text) as count from testtweets join hash1 on testtweets.text like concat ('%',hash1.tag,'%') group by hash1.tag order by count desc limit 10")
    blackboard_hashtag.show(false)
    blackboard_hashtag.save("blackboard_hashtag", "json")
*/

    //Query5 -
      // Reads json file and stores in a variable
      val tweets = sqlContext.jsonFile("C:\\Users\\putha\\Desktop\\sujitha\\prin of big data\\project\\Sports_tweets.json")
      tweets.registerTempTable("testtweets")
      val s8 = sqlContext.sql("select place.country_code as location,count(*) as count from testtweets where place.country_code is not NULL and text like '%soccer%' group by place.country_code order by count desc limit 10")
      s8.show(false)
      /*
      val userid = s8.first().getLong(1)
      println("Selected user id is:"+userid)

      val request = new HttpGet("https://api.twitter.com/1.1/users/show.json?user_id="+userid)
      consumer.sign(request)
      val client = new DefaultHttpClient()
      val response = client.execute(request)

      println(response.getStatusLine().getStatusCode());
      println(IOUtils.toString(response.getEntity().getContent()))
      */

    /*
    //Map Query

    val tweets = sqlContext.jsonFile("C:\\Users\\putha\\Desktop\\sujitha\\prin of big data\\project\\Sports_tweets.json")
    tweets.registerTempTable("testtweets")

    val location = sqlContext.sql("select place.country_code as location,count(*) as cnt from testtweets where place.country_code is not NULL and text like '%sport%' group by place.country_code order by cnt desc limit 10")
    location.show()
   // location.save("location","json")
*/

  }

}
