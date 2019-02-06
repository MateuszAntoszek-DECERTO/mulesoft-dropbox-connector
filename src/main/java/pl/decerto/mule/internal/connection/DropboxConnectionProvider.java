package pl.decerto.mule.internal.connection;

import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropboxConnectionProvider implements PoolingConnectionProvider<DropboxTokenConnection> {

	private final Logger LOGGER = LoggerFactory.getLogger(DropboxConnectionProvider.class);

	@Parameter
	@DisplayName("token")
	private String token;

	@Override
	public DropboxTokenConnection connect() {
		return new DropboxTokenConnection(token);
	}

	@Override
	public void disconnect(DropboxTokenConnection connection) {
		connection.disconectClient();
	}

	@Override
	public ConnectionValidationResult validate(DropboxTokenConnection connection) {
		return ConnectionValidationResult.success();
	}
}
