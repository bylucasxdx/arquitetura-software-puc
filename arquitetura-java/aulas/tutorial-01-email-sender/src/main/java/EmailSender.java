
public class EmailSender {
	
	public static void main(String[] args) {
		final String fromEmail = "xxxxxx@gmail.com";
		final String password = "xxxxxxx";
		final String toEmail = "xxxxx@gmail.com";
		
		System.out.println("Iniciando email send");
		
		EmailConfig config = new EmailConfig();
		
		config.sendEmail(fromEmail, password, toEmail, "Teste", "Conteúdo");
	}
}
