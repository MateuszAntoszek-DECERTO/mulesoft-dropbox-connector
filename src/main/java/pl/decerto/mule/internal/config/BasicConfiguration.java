package pl.decerto.mule.internal.config;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.Sources;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import pl.decerto.mule.internal.connection.oauth.DropboxOAuthConnectionProvider;
import pl.decerto.mule.internal.connection.token.DropboxTokenConnectionProvider;
import pl.decerto.mule.internal.operation.DropboxOperations;
import pl.decerto.mule.internal.source.DropboxDeleteDirectoryListener;
import pl.decerto.mule.internal.source.DropboxFileDirectoryListener;
import pl.decerto.mule.internal.source.DropboxFolderDirectoryListener;

@Operations(DropboxOperations.class)
@ConnectionProviders({DropboxTokenConnectionProvider.class, DropboxOAuthConnectionProvider.class})
@Sources({DropboxFileDirectoryListener.class, DropboxDeleteDirectoryListener.class, DropboxFolderDirectoryListener.class})
public class BasicConfiguration {

}
