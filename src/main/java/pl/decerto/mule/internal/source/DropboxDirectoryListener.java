package pl.decerto.mule.internal.source;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderGetLatestCursorResult;
import com.dropbox.core.v2.files.ListFolderLongpollResult;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import java.util.concurrent.TimeUnit;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.source.PollContext;
import org.mule.runtime.extension.api.runtime.source.PollingSource;
import org.mule.runtime.extension.api.runtime.source.SourceCallbackContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.decerto.mule.internal.config.BasicConfiguration;
import pl.decerto.mule.internal.connection.DropboxTokenConnection;
import pl.decerto.mule.internal.source.results.DropboxChange;

public abstract class DropboxDirectoryListener extends PollingSource<DropboxChange, DropboxChange> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DropboxChange.class);

	@Connection
	private ConnectionProvider<DropboxTokenConnection> connection;

	@Config
	private BasicConfiguration config;

	@Parameter
	@Optional
	private String folderPath = "";

	private String cursor;

	private DbxClientV2 client;

	@Override
	protected void doStart() throws ConnectionException {
		client = connection.connect().getClient();
		try {
			cursor = getLatestCursor(client, folderPath);
		} catch (DbxException e) {
			throw new ConnectionException("Error while connecting to Dropbox", e);
		}
	}

	@Override
	protected void doStop() {
	}

	@Override
	public void poll(PollContext<DropboxChange, DropboxChange> pollContext) {
		if (pollContext.isSourceStopping()) {
			return;
		}
		try {
			ListFolderLongpollResult result = client.files().listFolderLongpoll(cursor, TimeUnit.SECONDS.toSeconds(30));
			if (result.getChanges()) {
				cursor = printChanges(cursor, pollContext);
			}
		} catch (DbxException e) {
			LOGGER.error("Dropbox pool exception ", e);
		}
	}

	@Override
	public void onRejectedItem(Result<DropboxChange, DropboxChange> result, SourceCallbackContext sourceCallbackContext) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Change rejected for processing: " + result.getOutput());
		}
	}

	protected static String getLatestCursor(DbxClientV2 dbxClient, String path)
			throws DbxException {
		ListFolderGetLatestCursorResult result = dbxClient.files().listFolderGetLatestCursorBuilder(path)
				.withIncludeDeleted(true)
				.withIncludeMediaInfo(false)
				.withRecursive(true)
				.start();
		return result.getCursor();
	}

	private String printChanges(String cursor, PollContext<DropboxChange, DropboxChange> pollContext) throws DbxException {
		while (true) {
			ListFolderResult result = client.files().listFolderContinue(cursor);
			for (Metadata metadata : result.getEntries()) {
				if (isMetadataEventType(metadata)) {
					acceptItem(pollContext, metadata);
				}
			}
			cursor = result.getCursor();
			if (!result.getHasMore()) {
				break;
			}
		}
		return cursor;
	}

	private void acceptItem(PollContext<DropboxChange, DropboxChange> pollContext, Metadata metadata) {
		pollContext.accept(item -> {
			item.setResult(createResult(metadata));
			item.setId(metadata.getName());
		});
	}

	protected abstract Result<DropboxChange, DropboxChange> createResult(Metadata metadata);

	protected abstract boolean isMetadataEventType(Metadata metadata);
}