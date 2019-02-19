package pl.decerto.mule.internal.connection.oauth;

import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.connectivity.oauth.AuthorizationCode;
import org.mule.runtime.extension.api.connectivity.oauth.AuthorizationCodeState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.decerto.mule.api.connection.DropboxConnection;

@AuthorizationCode(
		authorizationUrl = "https://www.dropbox.com/1/oauth2/authorize",
		accessTokenUrl = "https://api.dropbox.com/1/oauth2/token",
		accessTokenExpr = "#[read(payload,\"application/json\").access_token]",
		refreshTokenExpr = "#[read(payload,\"application/json\").refresh_token]",
		expirationExpr = "#[read(payload,\"application/json\").expires_in]")
@Alias("Oauth")
public class DropboxOAuthConnectionProvider implements PoolingConnectionProvider<DropboxConnection> {

	private final Logger LOGGER = LoggerFactory.getLogger(DropboxOAuthConnectionProvider.class);

	private AuthorizationCodeState state;

	@Override
	public DropboxConnection connect() {
		if (state.getAccessToken() == null) {
			throw new RuntimeException("No accessToken");
		}

		return new DropboxConnection(state.getAccessToken());
	}

	@Override
	public void disconnect(DropboxConnection connection) {
		state = null;
		connection.disconnectClient();
	}

	@Override
	public ConnectionValidationResult validate(DropboxConnection connection) {
		return ConnectionValidationResult.success();
	}
}
