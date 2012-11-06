package negocios.impl.mailSender;

import java.io.IOException;
import java.util.Properties;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;



public class MailSender2 {

	private static final String MAIL_ADMIN_PROP = "mailAdmin";
	private static final String PASSSWORD_PROP = "password";
	private static final String SMTP_SERVER_PROP = "servidorSMTP";
	private static final String PORT_PROP = "puertoEnvio";

	String mailReceptor = null;
	String asunto = null;
	String cuerpo = null;

	public MailSender2(String mailReceptor, String asunto, String cuerpo) {
		this.mailReceptor = mailReceptor;
		this.asunto = asunto;
		this.cuerpo = cuerpo;
	}

	public void send() throws IOException, MessagingException, NamingException {
		Properties props = System.getProperties();
		Properties mailProps = new Properties();
		mailProps.load(this.getClass().getResourceAsStream(
				"/META-INF/adminMail.properties"));
		String miCorreo = mailProps.getProperty(MAIL_ADMIN_PROP);
		String servidorSMTP = mailProps.getProperty(SMTP_SERVER_PROP);
		String puertoEnvio = mailProps.getProperty(PORT_PROP);
		String pass = mailProps.getProperty(PASSSWORD_PROP);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", servidorSMTP);
		props.put("mail.smtp.user", miCorreo);
		props.put("mail.smtp.password", PASSSWORD_PROP);
		props.put("mail.smtp.port", puertoEnvio);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.EnableSSL.enable", "true");
		InitialContext ctx = new InitialContext();
		Session session = (javax.mail.Session) ctx.lookup("java:jboss/mail/Default");
		MimeMessage msg = new MimeMessage(session);
		String encodingOptions = "text/html; charset=UTF-8";
		msg.setHeader("Content-Type", encodingOptions);
		msg.setHeader("Content-Transfer-Encoding", "quoted-printable");
		msg.setFrom(new InternetAddress(miCorreo));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailReceptor));
		msg.setSubject("Some subject", "UTF-8");
		msg.setText(mailReceptor, "UTF-8");

		Transport transport = session.getTransport("smtps");
		transport.connect(servidorSMTP, miCorreo, pass);
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
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
