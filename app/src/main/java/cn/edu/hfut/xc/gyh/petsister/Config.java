package cn.edu.hfut.xc.gyh.petsister;

/**
 * Created by gyh on 17-3-2.
 */

public class Config {
    public static String ServerIp="192.168.2.102";
    public static int ServerPort=9956;
    public static String PicHost="http://"+ServerIp+"/test/pic.php";
    public static String NewestPic="http://"+ServerIp+"/test/newpic.php";
    public static String LoginHost="http://"+ServerIp+"/test/login.php";
    public static String TempHost="http://"+ServerIp+"/test/tp.php";
    public static String WeightHost="http://"+ServerIp+"/test/weight.php";
    public static String VoiceHost="http://"+ServerIp+"/test/voice.php";
    public static String Analysis="http://"+ServerIp+"/test/analysis.php";
    public static String TakePhoto="getPicture";
    public static String FeedUp="zhuan";
    public static String GetWeight="getWeight";
    public static String sendV="sentvoice";
}
