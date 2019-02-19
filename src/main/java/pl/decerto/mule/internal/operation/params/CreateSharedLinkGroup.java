package pl.decerto.mule.internal.operation.params;

import com.dropbox.core.v2.sharing.RequestedVisibility;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;

public class CreateSharedLinkGroup {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
	private SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(DATE_FORMAT);

	@Parameter
	@DisplayName("Path to file")
	private String path;

	@Parameter
	@Optional
	private RequestedVisibility requestedVisibility;

	@Parameter
	@Optional
	private String linkPassword;

	@Parameter
	@Optional
	@Example(DATE_FORMAT)
	private String expirationDate;

	public String getPath() {
		return path;
	}

	public RequestedVisibility getRequestedVisibility() {
		return requestedVisibility;
	}

	public String getLinkPassword() {
		return linkPassword;
	}

	public Date getExpirationDate() throws ParseException {
		if (expirationDate == null) {
			return null;
		}
		return DATE_TIME_FORMAT.parse(expirationDate);
	}
}
