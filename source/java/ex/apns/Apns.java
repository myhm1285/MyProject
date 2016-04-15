import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotifications;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Apns {

	private final static Logger logger = LoggerFactory.getLogger(asdf.class);

	public static void main(String[] args) throws JSONException, CommunicationException, KeystoreException {
		// TODO Auto-generated method stub
		// APNS
		PushNotificationPayload payload = PushNotificationPayload.complex();
		payload.addAlert("test");

		PushedNotifications notifications = Push.payload(payload, "C:\\160328\\dev\\apns.p12", "cscosquare!", true,
				"c4a2bc9a4c9685c8f35c32d8c20c9da5bd87f41b466f2fdadc2026a405fdb4a1");
		try {
			logger.info("---APNS Message Send Result : {}", ((notifications == null || notifications.size() == 0 || !notifications.get(0)
					.isSuccessful()) ? "FAIL" : "SUCCESS"));
			logger.info("{}", notifications);
		} catch (Exception e) {
		}
	}

}
