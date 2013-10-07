package keepass2android.javafilestorage;

import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public interface JavaFileStorage {
	
	public static final String PROCESS_NAME_SELECTFILE = "SELECT_FILE";
	public static final String PROCESS_NAME_FILE_USAGE_SETUP = "FILE_USAGE_SETUP";

	public static final String EXTRA_PROCESS_NAME = "EXTRA_PROCESS_NAME";
	public static final String EXTRA_PATH = "fileName"; //match KP2A PasswordActivity Ioc-Path Extra key
	public static final String EXTRA_IS_FOR_SAVE = "IS_FOR_SAVE";
	public static final String EXTRA_ERROR_MESSAGE = "EXTRA_ERROR_MESSAGE";

	
public interface FileStorageSetupInitiatorActivity
{
	void startSelectFileProcess(String path, boolean isForSave, int requestCode);
	void startFileUsageProcess(String path, int requestCode);
	void onImmediateResult(int requestCode, int result,	Intent intent);
	Activity getActivity();
}

public interface FileStorageSetupActivity
{
	String getPath();
	String getProcessName();
	//int getRequestCode();
	boolean isForSave();
	Bundle getState();	
}
	

public class FileEntry {
	public String path;
	public boolean isDirectory;
	public long lastModifiedTime;
	public boolean canRead;
	public boolean canWrite;
	public long sizeInBytes;
	
	public FileEntry()
	{
		isDirectory = false;
		canRead = canWrite = true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (canRead ? 1231 : 1237);
		result = prime * result + (canWrite ? 1231 : 1237);
		result = prime * result + (isDirectory ? 1231 : 1237);
		result = prime * result
				+ (int) (lastModifiedTime ^ (lastModifiedTime >>> 32));
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + (int) (sizeInBytes ^ (sizeInBytes >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileEntry other = (FileEntry) obj;
		if (canRead != other.canRead)
			return false;
		if (canWrite != other.canWrite)
			return false;
		if (isDirectory != other.isDirectory)
			return false;
		if (lastModifiedTime != other.lastModifiedTime)
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (sizeInBytes != other.sizeInBytes)
			return false;
		return true;
	}
	
	
}
	
	//public boolean tryConnect(Activity activity);
	
	//public void onResume();
	
	//public void onActivityResult(Activity activity, final int requestCode, final int resultCode, final Intent data);
    	
	//public boolean isConnected();

	public static int MAGIC_NUMBER_JFS = 874345; 
	public static int RESULT_FULL_FILENAME_SELECTED = MAGIC_NUMBER_JFS+1;
	public static int RESULT_FILECHOOSER_PREPARED = MAGIC_NUMBER_JFS+2;
	public static int RESULT_FILEUSAGE_PREPARED = MAGIC_NUMBER_JFS+3;
	
	public boolean requiresSetup(String path);

	public void startSelectFile(FileStorageSetupInitiatorActivity activity, boolean isForSave, int requestCode);
	
	public void prepareFileUsage(FileStorageSetupInitiatorActivity activity, String path, int requestCode);
	
	public String getProtocolId();
	
	public boolean checkForFileChangeFast(String path, String previousFileVersion) throws Exception;
	
	public String getCurrentFileVersionFast(String path);
	
	public InputStream openFileForRead(String path) throws Exception;
	
	public void uploadFile(String path, byte[] data, boolean writeTransactional) throws Exception;
	
	public void createFolder(String path) throws Exception;
	
	public List<FileEntry> listFiles(String dirName) throws Exception;
	
	public FileEntry getFileEntry(String filename) throws Exception;
	
	public void delete(String path) throws Exception;
	
	public void onCreate(FileStorageSetupActivity activity, Bundle savedInstanceState);
	public void onResume(FileStorageSetupActivity activity);
	public void onStart(FileStorageSetupActivity activity);
	public void onActivityResult(FileStorageSetupActivity activity, int requestCode, int resultCode, Intent data);
	
}