package pl.decerto.mule.internal.source.results;

public class DropboxChange {

	private String name;

	private String path;

	public DropboxChange() {
	}

	public DropboxChange(String name, String path) {
		this.name = name;
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}