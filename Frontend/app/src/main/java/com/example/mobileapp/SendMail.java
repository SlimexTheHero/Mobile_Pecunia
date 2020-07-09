package com.example.mobileapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail extends AsyncTask<Void,Void,Void> {

    private static final String USER = "pecuniastudentprojekt@gmail.com";
    private static final String PASSWORD = "filipbrunoPecunia";

    //Declaring Variables
    private Context context;
    private Session session;

    //Information to send email
    private String email;
    private String subject;
    private String message;
    private String filename;
    private String filepath;

    //Class Constructor
    public SendMail(Context context, String email, String subject, String message){
        //Initializing variables
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    public SendMail(Context context, String email, String subject, String message, String filepath){
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.filepath = filepath;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        Toast.makeText(context,"Sending Message",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Showing a success message
        Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER, PASSWORD);
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);
            Multipart mp = new MimeMultipart();

            if(!filepath.isEmpty()) {
                MimeBodyPart messagebp = new MimeBodyPart();
                messagebp.setContent(mm, "application/pdf");
                MimeBodyPart attachbp = new MimeBodyPart();
                attachbp.attachFile(new File(filepath));
                mp.addBodyPart(messagebp);
                mp.addBodyPart(attachbp);
                mm.setContent(mp);
            }

            //Setting sender address
            mm.setFrom(new InternetAddress(USER));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject(subject);
            //Adding message
            mm.setText(message);




            //Sending email
            Transport.send(mm);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}