import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class DBConnection {

	public static void main(String[] args) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword("ENC(hdy3E36B2t4khnZCnQf0Bw==)");
		try {
			// 서울메트로 운영 DB URL
			// standard encrypt (기존)
			String dbUrl = encryptor.encrypt("jdbc:mariadb://192.168.10.113:12007/mydb");
			System.out.println("URL : " + dbUrl);
			String dbId = encryptor.encrypt("cs");
			System.out.println("ID : " + dbId);
			String dbPw = encryptor.encrypt("cscosquare");
			System.out.println("PWD : " + dbPw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
