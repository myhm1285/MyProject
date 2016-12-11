import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class DBConnection {

	public static void main(String[] args) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword("ENC(hdy3E36B2t4khnZCnQf0Bw==)");
		try {
			// 서울메트로 운영 DB URL
			// standard encrypt (기존)
			String dbUrl = encryptor.encrypt("jdbc:mariadb://52.78.146.78:3306/mydb");
			System.out.println("URL : " + dbUrl);
			String dbId = encryptor.encrypt("test");
			System.out.println("ID : " + dbId);
			String dbPw = encryptor.encrypt("test1234");
			System.out.println("PWD : " + dbPw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
