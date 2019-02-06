package pl.decerto.mule.internal.connection;


import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class DropboxTokenConnection {

	private final Logger LOGGER = LoggerFactory.getLogger(DropboxTokenConnection.class);

	DbxClientV2 client;

	public DropboxTokenConnection(String token) {
		try {
			client = connectWithToken(token);
		} catch (IOException e) {
			LOGGER.error("Error while connecting", e);
		}
	}

	public DbxClientV2 getClient() {
		return client;
	}

	public void disconectClient() {
		this.client = null;
	}

	private DbxClientV2 connectWithToken(String token) throws IOException {
		DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
		return new DbxClientV2(config, token);
	}

}
