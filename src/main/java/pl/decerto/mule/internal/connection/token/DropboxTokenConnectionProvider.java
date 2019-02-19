package pl.decerto.mule.internal.connection.token;

import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.decerto.mule.api.connection.DropboxConnection;

@Alias("token")
public class DropboxTokenConnectionProvider implements PoolingConnectionProvider<DropboxConnection> {

	private final Logger LOGGER = LoggerFactory.getLogger(DropboxTokenConnectionProvider.class);

	@Parameter
	@DisplayName("token")
	private String token;

	@Override
	public DropboxConnection connect() {
		return new DropboxConnection(token);
	}

	@Override
	public void disconnect(DropboxConnection connection) {
		connection.disconnectClient();
	}

	@Override
	public ConnectionValidationResult validate(DropboxConnection connection) {
		return ConnectionValidationResult.success();
	}
}
