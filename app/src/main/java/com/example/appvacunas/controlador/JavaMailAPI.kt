package com.example.appvacunas.controlador

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

open class JavaMailAPI(mContext: Context, mEmail: String, mSubject: String, mMessage: String) : AsyncTask<Void?, Void?, Void?>() {

    private val mContext: Context
    private var mSession: Session? = null
    private val mEmail: String
    private val mSubject: String
    private val mMessage: String
    private var mProgressDialog: ProgressDialog? = null

    override fun onPreExecute() {
        super.onPreExecute()
        //Show progress dialog while sending email
        mProgressDialog =
            ProgressDialog.show(mContext, "Enviando Solicitud", "Por favor espere...", false, false)
    }

    override fun onPostExecute(aVoid: Void?) {
        super.onPostExecute(aVoid)
        //Dismiss progress dialog when message successfully send
        mProgressDialog!!.dismiss()

        //Show success toast
        Toast.makeText(mContext, "Solicitud enviada", Toast.LENGTH_SHORT).show()
    }

    override fun doInBackground(vararg p0: Void?): Void? {
        //Creating properties
        val props = Properties()
        //Configuring properties for gmail
        props.put("mail.smtp.host", "smtp.gmail.com")
        props.put("mail.smtp.socketFactory.port", "465")
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
        props.put("mail.smtp.auth", "true")
        props.put("mail.smtp.port", "465")

        //Creating a new session
        mSession = Session.getDefaultInstance(props,
            object : Authenticator() {
                //Authenticating the password
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(Constants.EMAIL, Constants.PASSWORD)
                }
            })
        try {
            val mm = MimeMessage(mSession)
            mm.setFrom(InternetAddress(Constants.EMAIL))
            mm.addRecipient(Message.RecipientType.TO, InternetAddress(mEmail))
            mm.subject = mSubject
            //mm.setText(mMessage)
            //test
            val multipart: Multipart = MimeMultipart()
            val messageBodyPart: BodyPart = MimeBodyPart()
            val htmlText = "<H1>Solicitud de vacuna</H1> <br>" +
                    ""+ mMessage +"<br>"
            messageBodyPart.setContent(htmlText, "text/html")
            multipart.addBodyPart(messageBodyPart)
            mm.setContent(multipart)
            /*test
            val texto: BodyPart = MimeBodyPart()
            texto.setText(mMessage)
            val adjunto: BodyPart = MimeBodyPart()
            adjunto.dataHandler = DataHandler(FileDataSource(""))
            adjunto.fileName = "clinica_hipnosis.jpg"
            val multiParte = MimeMultipart()
            multiParte.addBodyPart(texto)
            multiParte.addBodyPart(adjunto)

            mm.setContent(multiParte)*/

            //Sending email
            Transport.send(mm)

//            BodyPart messageBodyPart = new MimeBodyPart();
//
//            messageBodyPart.setText(message);
//
//            Multipart multipart = new MimeMultipart();
//
//            multipart.addBodyPart(messageBodyPart);
//
//            messageBodyPart = new MimeBodyPart();
//
//            DataSource source = new FileDataSource(filePath);
//
//            messageBodyPart.setDataHandler(new DataHandler(source));
//
//            messageBodyPart.setFileName(filePath);
//
//            multipart.addBodyPart(messageBodyPart);

//            mm.setContent(multipart);
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
        return null
    }


    //Constructor
    init {
        this.mContext = mContext
        this.mEmail = mEmail
        this.mSubject = mSubject
        this.mMessage = mMessage
    }


}