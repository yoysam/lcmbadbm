package edu.touro.mco152.bm.externalsys;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.api.ApiTestResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.persist.DiskRun;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * A Slack Manager for BadBM that just knows how to send a string msg to a pre-designated channel
 * using hard-coded authentication token.
 * Usage:
 *          SlackManager slackmgr = new SlackManager("myAppName");
 *          Boolean worked = slackmgr.postMsg2OurChannel(":smile: Benchmark completed");
 */
public class SlackManager implements Observer {
    private static Slack slack = null;  // obtain/keep one copy of expensive item
    /**
     * Token for use with Slack API, representing info about our bot/app/channel.
     * If the token is a bot token, it starts with `xoxb-` while if it's a user token, it starts with `xoxp-`
     * This should really come from one of the badbm properties files, or from System.getenv()
     */
    private final String token = "hbrrhfdh";
    /**
     * The channel we will send all our messages to
     */
    private final String ourChannel = "mco152_auto_notifications";
    private String appName = "App";

    public SlackManager() {
        // disallow private constructor
    }

    /**
     * Construct a Slack Manager for our application, to be used for sending messages to Slack
     *
     * @param appName - pass a sring like BadBM, or whatever name you want to appear in msgs
     */
    public SlackManager(String appName) {
        this.appName = appName;

        if (slack == null)
            slack = Slack.getInstance();

        // auto-validate environment
        try {
            ApiTestResponse testResponse = slack.methods().apiTest(r -> r.foo("bar"));
            if (!testResponse.isOk()) {
                System.err.println("SlackManager: Problem with auto-validation of Slack: "
                        + testResponse.getError());
            }
        } catch (IOException | SlackApiException exc) {
            System.err.println("SlackManager: Problem with auto-validation of Slack");
            exc.printStackTrace();
        }
    }
@Override
public void update(Observable o, Object run)  {
        SlackManager slackmgr = new SlackManager("BadBM");
        Boolean worked;
        // Boolean worked = slackmgr.postMsg2OurChannel(":cry: Benchmark failed");
    if (run instanceof DiskRun){
        double max=Double.parseDouble(((DiskRun) run).getMax());
        double ave=Double.parseDouble(((DiskRun) run).getAvg());

        Double offset=ave*.03;
        if (max>ave+offset)
        worked=slackmgr.postMsg2OurChannel(":smile: Something is wrong the max benchmark is " + max + " is off by more than 3 percent of the average " + ave );


        else
         worked = slackmgr.postMsg2OurChannel(":smile: Benchmark completed the aravage benchmark is " + ave + " The max is " + max);
        System.out.println("Retcode from test sending msg is " + worked);
    }}

    /**
     * Post slack message to our pre-defined channel using predefined credential token
     *
     * @param msg - String with message to post, can contain emojis like :smile:
     * @return True if successful
     */
    public Boolean postMsg2OurChannel(String msg) {
        // Initialize an API Methods client with the given token
        MethodsClient methods = slack.methods(this.token);

        String postText = String.format("Automated message from <%s>%s: %s",
                System.getProperty("user.name"), appName, msg);

        // Build a request object
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(ourChannel)
                .text(postText)
                .build();

        // Get a response as a Java object
        ChatPostMessageResponse response = null;
        try {
            response = methods.chatPostMessage(request);
        } catch (IOException | SlackApiException exc) {
            System.err.println("SlackManager: Problem with execution of chatPostMessage");
            exc.printStackTrace();
            return false;
        }


        if (response.isOk()) {
            return true;
        } else {
            System.err.println("SlackManager: Problem with Response = " + response);
            return false;
        }
    }
}

