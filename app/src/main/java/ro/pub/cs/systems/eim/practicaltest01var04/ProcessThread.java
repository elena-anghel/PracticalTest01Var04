package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class ProcessThread extends Thread{

    private boolean isRunning = true;
    private final Context context;
    private final Random random = new Random();
    private final String nume = "";
    private final String grupa = "";

    public ProcessThread(Context context, String nume, String grupa) {
        this.context = context;
    }

    @Override
    public void run() {
        while (isRunning) {
            sleep();
            sendMessage();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(Constants.SLEEP_TIME);
        } catch (InterruptedException interruptedException) {
            Log.d("ProcessThread", "Thread has stopped!");
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA, nume + " " + grupa);
        context.sendBroadcast(intent);
    }

    public void stopThread() {
        isRunning = false;
    }
}

