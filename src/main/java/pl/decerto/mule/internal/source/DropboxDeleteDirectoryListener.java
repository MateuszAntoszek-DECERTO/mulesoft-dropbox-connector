package pl.decerto.mule.internal.source;

import com.dropbox.core.v2.files.DeletedMetadata;
import com.dropbox.core.v2.files.Metadata;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.runtime.operation.Result;
import pl.decerto.mule.internal.source.results.DropboxChange;

@DisplayName("On delete")
@Alias(value = "dropbox-delete-listener")
public class DropboxDeleteDirectoryListener extends DropboxDirectoryListener {

	@Override
	protected Result<DropboxChange, DropboxChange> createResult(Metadata metadata) {
		DropboxChange dropboxChange = new DropboxChange(metadata.getName(), metadata.getPathDisplay());
		return Result.<DropboxChange, DropboxChange>builder()
				.output(dropboxChange)
				.build();
	}

	@Override
	public boolean isMetadataEventType(Metadata metadata) {
		return metadata instanceof DeletedMetadata;
	}
}
