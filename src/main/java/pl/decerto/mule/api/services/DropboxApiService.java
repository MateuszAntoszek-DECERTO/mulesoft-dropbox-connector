package pl.decerto.mule.api.services;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.RelocationResult;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;
import org.mule.runtime.api.metadata.TypedValue;
import pl.decerto.mule.api.connection.DropboxConnection;
import pl.decerto.mule.internal.operation.params.CreateSharedLinkGroup;

public class DropboxApiService {

	public List<Metadata> listFilesInFolder(DropboxConnection connection, String folderName) throws DbxException {
		return connection.getClient()
				.files()
				.listFolder(folderName)
				.getEntries();
	}

	public FileMetadata uploadFile(DropboxConnection connection, String destinationPath, TypedValue<InputStream> content) throws DbxException, IOException {
		return connection.getClient().files()
				.upload(destinationPath)
				.uploadAndFinish(content.getValue());
	}

	public SharedLinkMetadata createSharedLink(DropboxConnection connection, CreateSharedLinkGroup group) throws DbxException, ParseException {
		SharedLinkSettings settings = new SharedLinkSettings(
				group.getRequestedVisibility(),
				group.getLinkPassword(),
				group.getExpirationDate()
		);

		return connection.getClient().sharing()
				.createSharedLinkWithSettings(group.getPath(), settings);
	}

	public FileMetadata downloadFile(DropboxConnection connection, String filePath, OutputStream stream) throws DbxException, IOException {
		return connection.getClient().files()
				.download(filePath)
				.download(stream);
	}

	public RelocationResult moveFile(DropboxConnection connection, String sourcePath, String destinationPath) throws DbxException {
		return connection.getClient().files()
				.moveV2(sourcePath, destinationPath);
	}

	public RelocationResult copyFile(DropboxConnection connection, String sourcePath, String destinationPath) throws DbxException {
		return connection.getClient().files()
				.copyV2(sourcePath, destinationPath);
	}
}
