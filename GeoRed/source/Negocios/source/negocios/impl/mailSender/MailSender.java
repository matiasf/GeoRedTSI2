package negocios.impl.mailSender;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

	private static final String MAIL_ADMIN_PROP = "mailAdmin";
	private static final String PASSSWORD_PROP = "password";
	private static final String SMTP_SERVER_PROP = "servidorSMTP";
	private static final String PORT_PROP = "puertoEnvio";

	String mailReceptor = null;
	String asunto = null;
	String cuerpo = null;

	public MailSender(String mailReceptor, String asunto, String cuerpo) {
		this.mailReceptor = mailReceptor;
		this.asunto = asunto;
		this.cuerpo = cuerpo;
	}

	public void send() throws MessagingException {
		Properties props = new Properties();
		Properties mailProps = new Properties();
		try {
			mailProps.load(this.getClass().getResourceAsStream(
					"/META-INF/adminMail.properties"));
			String miCorreo = mailProps.getProperty(MAIL_ADMIN_PROP);
			String servidorSMTP = mailProps.getProperty(SMTP_SERVER_PROP);
			String puertoEnvio = mailProps.getProperty(PORT_PROP);
			props.put("mail.smtp.user", miCorreo);
			props.put("mail.smtp.host", servidorSMTP);
			props.put("mail.smtp.port", puertoEnvio);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.socketFactory.port", puertoEnvio);
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
			Authenticator auth = new autentificadorSMTP();
			Session session = Session.getInstance(props, auth);
			MimeMessage msg = new MimeMessage(session);
			msg.setText(cuerpo);
			msg.setSubject(asunto);
			msg.setFrom(new InternetAddress("noreply@geored.com.uy"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailReceptor));
			Transport.send(msg);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class autentificadorSMTP extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			Properties mailProps = new Properties();
			try {
				mailProps.load(this.getClass().getResourceAsStream(
						"/META-INF/adminMail.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String miCorreo = mailProps.getProperty(MAIL_ADMIN_PROP);
			String password = mailProps.getProperty(PASSSWORD_PROP);
			return new PasswordAuthentication(miCorreo, password);
		}
	}
}
