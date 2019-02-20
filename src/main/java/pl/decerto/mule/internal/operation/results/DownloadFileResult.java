package pl.decerto.mule.internal.operation.results;

import com.dropbox.core.v2.files.FileMetadata;

public class DownloadFileResult {

	private FileMetadata fileMetadata;

	private byte[] file;

	public DownloadFileResult(FileMetadata fileMetadata, byte[] file) {
		this.fileMetadata = fileMetadata;
		this.file = file;
	}

	public FileMetadata getFileMetadata() {
		return fileMetadata;
	}

	public byte[] getFile() {
		return file;
	}
}
