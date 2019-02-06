package pl.decerto.mule.internal.source.results;

public class DropboxFileChange extends DropboxChange{

	private long size;

	public DropboxFileChange(String name, String path, long size) {
		super(name, path);
		this.size = size;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
