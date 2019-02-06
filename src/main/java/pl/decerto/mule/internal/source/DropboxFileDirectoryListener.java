package pl.decerto.mule.internal.source;

import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.runtime.operation.Result;
import pl.decerto.mule.internal.source.results.DropboxChange;
import pl.decerto.mule.internal.source.results.DropboxFileChange;

@DisplayName("On create or update file")
@Alias(value = "dropbox-file-listener")
public class DropboxFileDirectoryListener extends DropboxDirectoryListener {

	@Override
	protected Result<DropboxChange, DropboxChange> createResult(Metadata metadata) {
		FileMetadata fileMetadata = (FileMetadata) metadata;
		DropboxFileChange dropboxChange = new DropboxFileChange(metadata.getName(), metadata.getPathDisplay(), fileMetadata.getSize());
		return Result.<DropboxChange, DropboxChange>builder()
				.output(dropboxChange)
				.build();
	}

	@Override
	protected boolean isMetadataEventType(Metadata metadata) {
		return metadata instanceof FileMetadata;
	}
}
