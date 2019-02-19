package pl.decerto.mule.api.connection;


import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class DropboxConnection {

	private final Logger LOGGER = LoggerFactory.getLogger(DropboxConnection.class);
	private static final String CLIENT_IDENTIFIER = "mulesoftCilient";

	private DbxClientV2 client;

	public DropboxConnection(String token) {
		try {
			client = connectWithToken(token);
		} catch (IOException e) {
			LOGGER.error("Error while connecting", e);
		}
	}

	public DbxClientV2 getClient() {
		return client;
	}

	public void disconnectClient() {
		this.client = null;
	}

	private DbxClientV2 connectWithToken(String token) throws IOException {
		DbxRequestConfig config = DbxRequestConfig.newBuilder(CLIENT_IDENTIFIER).build();
		return new DbxClientV2(config, token);
	}

}
