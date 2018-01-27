package com.devstrongzhao.andrloid.l.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RemoteViews;

import static android.app.Notification.PRIORITY_HIGH;

/**
 * 通知
 * 锁定屏幕通知
 * Android 5.0 中的锁定屏幕可以显示通知。用户可以通过“Settings” 选择是否允许在安全的锁定屏幕上显示敏感的通知内容。
 * <p>
 * 您的应用可以控制在安全锁定屏幕上显示的通知中可见信息的详细程度。要控制可见性级别，请调用 setVisibility()，然后指定以下值之一：
 * <p>
 * VISIBILITY_PRIVATE：显示通知图标等基本信息，但隐藏通知的完整内容。
 * VISIBILITY_PUBLIC：显示通知的完整内容。
 * VISIBILITY_SECRET：不显示任何内容，甚至不显示通知图标。
 * 当可视性级别为 VISIBILITY_PRIVATE 时，您还可以提供隐藏个人详情的删减版通知内容。
 * 例如，短信应用可能会显示一条通知，指出“您有3 条新短信”，但是隐藏了短信内容和发件人。
 * 要提供此替换版本的通知，请先使用 Notification.Builder 创建替换通知。创建专用通知对象时，
 * 请通过 setPublicVersion() 方法为其附加替换通知。
 */


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private int NOTIFICATION_ID_BASIC = 1;
    private int NOTIFICATION_ID_COLLAPSE = 2;
    private int NOTIFICATION_ID_HEADSUP = 3;
    private int NOTIFICATION_ID_VISIBILITY = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.send_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basicNotify();
            }
        });
        findViewById(R.id.head_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headsupNotify(view);
            }
        });
        findViewById(R.id.collapsed_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedNotify();
            }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("Notification for Visibility Test");
        switch (i) {
            case R.id.public_rb:
                if (getSDK() >= Build.VERSION_CODES.LOLLIPOP) {
                    // 使用新特性
                    builder.setVisibility(Notification.VISIBILITY_PUBLIC)
                            .setContentText("Public")
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setLargeIcon(
                                    BitmapFactory.decodeResource(
                                            MainActivity.this.getResources(),
                                            R.mipmap.ic_launcher_round));
                } else {
                    // 用其他替代方式
                    builder.setContentText("public_subject")
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setLargeIcon(
                                    BitmapFactory.decodeResource(
                                            MainActivity.this.getResources(),
                                            R.mipmap.ic_launcher_round));
                }

                break;
            case R.id.private_rb:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setVisibility(Notification.VISIBILITY_PRIVATE);
                    builder.setContentText("Private");
                    builder.setSmallIcon(R.mipmap.ic_launcher_round);
                    builder.setLargeIcon(
                            BitmapFactory.decodeResource(
                                    MainActivity.this.getResources(),
                                    R.mipmap.ic_launcher_round));
                } else {
                    // 用其他替代方式
                    builder.setContentText("private_subject")
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setLargeIcon(
                                    BitmapFactory.decodeResource(
                                            MainActivity.this.getResources(),
                                            R.mipmap.ic_launcher_round));
                }

                break;
            case R.id.secret_rb:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setVisibility(Notification.VISIBILITY_SECRET);
                    builder.setContentText("Secret");
                    builder.setSmallIcon(R.mipmap.ic_launcher_round);
                    builder.setLargeIcon(
                            BitmapFactory.decodeResource(
                                    MainActivity.this.getResources(),
                                    R.mipmap.ic_launcher_round));
                } else {
                    // 用其他替代方式
                    builder.setContentText("secret_subject")
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setLargeIcon(
                                    BitmapFactory.decodeResource(
                                            MainActivity.this.getResources(),
                                            R.mipmap.ic_launcher_round));
                }


                break;
        }
        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_ID_VISIBILITY, builder.build());
    }

    public void basicNotify() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.baidu.com"));
        // 构造PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, 0);
        // 创建Notification对象
        Notification.Builder builder = new Notification.Builder(this);
        // 设置Notification的各种属性
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(
                getResources(), R.mipmap.ic_launcher_round));
        builder.setContentTitle("Basic Notifications");
        builder.setContentText("I am a basic notification");
        builder.setSubText("it is really basic");
        // 通过NotificationManager来发出Notification
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(
                        NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID_BASIC,
                builder.build());
    }

    public void collapsedNotify() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.sina.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_launcher));
        // 通过RemoteViews来创建自定义的Notification视图
        RemoteViews contentView =
                new RemoteViews(getPackageName(),
                        R.layout.notification);
        contentView.setTextViewText(R.id.textView,
                "show me when collapsed");

        Notification notification = builder.build();
        notification.contentView = contentView;
        // 通过RemoteViews来创建自定义的Notification视图
        RemoteViews expandedView =
                new RemoteViews(getPackageName(),
                        R.layout.notification_expanded);
        notification.bigContentView = expandedView;

        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_ID_COLLAPSE, notification);
    }

    public void headsupNotify(View view) {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setContentTitle("Headsup Notification")
                .setContentText("I am a Headsup notification.");

        if (getSDK() >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(Notification.CATEGORY_MESSAGE);
        }
        builder.setWhen(System.currentTimeMillis());
        Intent push = new Intent();
        push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        push.setClass(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, push, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentText("Heads-Up Notification on Android 5.0")
                .setFullScreenIntent(pendingIntent, true);

        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_ID_HEADSUP, builder.build());
    }


    /**
     * 获取Android发布的版本
     */
    public static String getVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取SDK的API Level
     *
     * @return
     */
    public static int getSDK() {
        return android.os.Build.VERSION.SDK_INT;
    }


//    public void testNotify(){
//        Notification.Builder builder = new Notification.Builder(this)
//                .setSmallIcon(R.drawable.ic_launcher)
//                .setPriority(Notification.PRIORITY_DEFAULT)
//                .setContentTitle("Headsup Notification")
//                .setContentText("I am a Headsup notification.");
//
//        builder.setCategory(Notification.CATEGORY_ALARM);
//        builder.setPriority(PRIORITY_HIGH );
//    }

}
