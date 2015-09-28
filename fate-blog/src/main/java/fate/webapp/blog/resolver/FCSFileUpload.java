package fate.webapp.blog.resolver;

import org.apache.commons.fileupload.FileItemFactory;

public class FCSFileUpload extends FCSFileUploadBase{

	// ----------------------------------------------------------- Data members

    /**
     * The factory to use to create new form items.
     */
    private FileItemFactory fileItemFactory;

    // ----------------------------------------------------------- Constructors

    /**
     * Constructs an uninitialised instance of this class.
     *
     * A factory must be
     * configured, using <code>setFileItemFactory()</code>, before attempting
     * to parse requests.
     *
     * @see #FileUpload(FileItemFactory)
     */
    public FCSFileUpload() {
        super();
    }

    /**
     * Constructs an instance of this class which uses the supplied factory to
     * create <code>FileItem</code> instances.
     *
     * @see #FileUpload()
     * @param fileItemFactory The factory to use for creating file items.
     */
    public FCSFileUpload(FileItemFactory fileItemFactory) {
        super();
        this.fileItemFactory = fileItemFactory;
    }

    // ----------------------------------------------------- Property accessors

    /**
     * Returns the factory class used when creating file items.
     *
     * @return The factory class for new file items.
     */
    @Override
    public FileItemFactory getFileItemFactory() {
        return fileItemFactory;
    }

    /**
     * Sets the factory class to use when creating file items.
     *
     * @param factory The factory class for new file items.
     */
    @Override
    public void setFileItemFactory(FileItemFactory factory) {
        this.fileItemFactory = factory;
    }
}
