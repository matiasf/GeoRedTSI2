package negocios.impl.mailSender;

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

	final String miCorreo = "siniestromuppet@gmail.com";
	final String miContraseña = "elpacode9comdir";
	final String servidorSMTP = "smtp.gmail.com";
	final String puertoEnvio = "465";
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

	}

	private class autentificadorSMTP extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(miCorreo, miContraseña);
		}
	}
}
