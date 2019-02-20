package pl.decerto.mule.internal.operation;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.RelocationResult;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import pl.decerto.mule.api.connection.DropboxConnection;
import pl.decerto.mule.api.services.DropboxApiService;
import pl.decerto.mule.internal.operation.params.CreateSharedLinkGroup;
import pl.decerto.mule.internal.operation.results.DownloadFileResult;

public class DropboxOperations {

	private DropboxApiService dropboxApiService = new DropboxApiService();

	@MediaType(ANY)
	public List<Metadata> listFilesInFolder(@Optional String folderName,
			@Connection DropboxConnection connection) throws DbxException {
		if (folderName == null) {
			return dropboxApiService.listFilesInFolder(connection, "");
		}
		return dropboxApiService.listFilesInFolder(connection, folderName);
	}

	@MediaType(ANY)
	public FileMetadata uploadFile(@Connection DropboxConnection connection,
			@Optional String destinationPath, TypedValue<InputStream> content) throws DbxException, IOException {
		return dropboxApiService.uploadFile(connection, destinationPath, content);
	}

	@MediaType(ANY)
	public SharedLinkMetadata createSharedLink(@Connection DropboxConnection connection,
			@ParameterGroup(name = "Group") CreateSharedLinkGroup group) throws DbxException, ParseException {
		return dropboxApiService.createSharedLink(connection, group);
	}

	@MediaType(ANY)
	public DownloadFileResult downloadFile(@Connection DropboxConnection connection, String filePath) throws IOException, DbxException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		FileMetadata fileMetadata = dropboxApiService.downloadFile(connection, filePath, stream);
		return new DownloadFileResult(fileMetadata, stream.toByteArray());
	}

	@MediaType(ANY)
	public RelocationResult moveFile(@Connection DropboxConnection connection,
			String sourcePath, String destinationPath) throws DbxException {
		return dropboxApiService.moveFile(connection, sourcePath, destinationPath);
	}

	@MediaType(ANY)
	public RelocationResult copyFile(@Connection DropboxConnection connection,
			String sourcePath, String destinationPath) throws DbxException {
		return dropboxApiService.copyFile(connection, sourcePath, destinationPath);
	}
}
