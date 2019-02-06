package pl.decerto.mule.internal.extension;

import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import pl.decerto.mule.internal.config.BasicConfiguration;


@Xml(prefix = "dropbox")
@Extension(name = "Dropbox", vendor = "decerto")
@Configurations(BasicConfiguration.class)
public class DropboxExtension {

}
