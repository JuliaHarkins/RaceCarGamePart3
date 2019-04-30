package game;

import java.io.Serializable;

public class ImageFiles implements Serializable{


	private static final long serialVersionUID = 6107371807181394237L;
	String _imageName;
	String _fileLocation;
	/*
	 * stores the images information
	 */
	public ImageFiles(String name, String fileLocation){

		_imageName= name;
		_fileLocation = fileLocation;
	}
	/*
	 * The location of the file
	 */
	public String getFileLocation() {
		return _fileLocation;
	}
	/*
	 * the name of the file
	 */
	public String getImageName() {
		return _imageName;
	}

}
