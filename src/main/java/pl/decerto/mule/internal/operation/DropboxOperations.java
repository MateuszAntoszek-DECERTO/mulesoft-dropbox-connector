package pl.decerto.mule.internal.operation;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.Metadata;
import java.util.List;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import pl.decerto.mule.api.connection.DropboxConnection;

public class DropboxOperations {

	@MediaType(ANY)
	public List<Metadata> listFilesInFolder(@Optional String folderName,
			@Connection DropboxConnection connection) throws DbxException {
		return connection.getClient()
				.files()
				.listFolder(folderName == null ? "" : folderName)
				.getEntries();
	}
}
