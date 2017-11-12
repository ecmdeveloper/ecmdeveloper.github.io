/**
 *
 */
package client;

import java.io.ByteArrayInputStream;
import java.util.Date;

import com.filenet.api.collection.ContentElementList;
import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;

/**
 * @author ricardo.belfor
 *
 */
public class CreateDocument {

	public void create(Object parent) {
		Folder folder = (Folder) parent;
		Document document = createDocument(folder.getObjectStore());
		folder.file(document, AutoUniqueName.AUTO_UNIQUE, null,
				DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE).save(
				RefreshMode.NO_REFRESH);
	}

	private Document createDocument(ObjectStore objectStore) {
		Document document = Factory.Document.createInstance(objectStore, "Document");
		document.getProperties().putValue("DocumentTitle", getDocumentTitle() );
		document.set_ContentElements( getContentElements() );
		document.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION );
		document.save(RefreshMode.REFRESH);
		return document;
	}

	private String getDocumentTitle() {
		return "Console Project Test " + (new Date()).getTime();
	}

	@SuppressWarnings("unchecked")
	private ContentElementList getContentElements() {
		ContentElementList contentElementList = Factory.ContentElement.createList();
		contentElementList.add( createStringContent("Hello, World!") );
		return contentElementList;
	}

	private ContentTransfer createStringContent(String data) {
		ContentTransfer content = Factory.ContentTransfer.createInstance();
		content.set_RetrievalName( "data.txt" );
		content.setCaptureSource( new ByteArrayInputStream( data.getBytes() ) );
		content.set_ContentType( "text/plain" );
		return content;
	}
}
