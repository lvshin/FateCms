package fate.webapp.blog.resolver;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

public class ServletFileUploadExt extends ServletFileUpload{

	public ServletFileUploadExt() {
        super();
    }

    public ServletFileUploadExt(FileItemFactory fileItemFactory) {
        super(fileItemFactory);
    }
	
	@Override
    public List<FileItem> parseRequest(HttpServletRequest request)
    throws FileUploadException {
        return parseRequest(new ServletRequestContext(request));
    }
}
