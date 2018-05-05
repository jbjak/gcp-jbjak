package gcp.jbjak.iotfitness.util;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

// [START example]
public class CloudStorageHelper {

	private static Storage storage = null;

	// [START init]
	static {
		storage = StorageOptions.getDefaultInstance().getService();
	}
	// [END init]

	// [START getFileUrl]
	/**
	 * Extracts the file payload from an HttpServletRequest, checks that the file
	 * extension is supported and uploads the file to Google Cloud Storage.
	 */
	public String getFileUrl(HttpServletRequest req, HttpServletResponse resp, final String bucket)
			throws IOException, ServletException {
		
		Part filePart = req.getPart("file");
		final String fileName = filePart.getSubmittedFileName();
		String fileUrl = req.getParameter("fileUrl");
		// Check extension of file
		if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
			final String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
			String[] allowedExt = { "csv", "CSV" };
			for (String s : allowedExt) {
				if (extension.equals(s)) {
					return this.uploadFile(filePart, bucket).getMediaLink();
				}
			}
			String msg = "Upload must be a CSV file!";
			System.err.println(msg);
			System.err.flush();
		}
		return fileUrl;
	}
	// [END getFileUrl]

	// [START uploadFile]
	/**
	 * Uploads a file to Google Cloud Storage to the bucket specified in the
	 * BUCKET_NAME environment variable, appending a timestamp to beginning of the
	 * uploaded filename.
	 */
	@SuppressWarnings("deprecation")
	public BlobInfo uploadFile(Part filePart, final String bucketName) throws IOException {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd-HHmmssSSS.");
		DateTime dt = DateTime.now(DateTimeZone.UTC);
		String dtString = dt.toString(dtf);
		final String fileName = dtString + filePart.getSubmittedFileName();

		// the inputstream is closed by default, so we don't need to close it here
		BlobInfo blobInfo = storage.create(BlobInfo.newBuilder(bucketName, fileName)
				// Modify access list to allow all users with link to read file
				.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))).build(),
				filePart.getInputStream());
		// return the public download link
		return blobInfo;
	}
	// [END uploadFile]

	// [START deleteFile]
	/**
	 * Deletes a file to Google Cloud Storage to the bucket specified in the
	 * BUCKET_NAME environment variable
	 */
	@SuppressWarnings("deprecation")
	public void deleteFile(BlobInfo blobInfo) throws IOException {

		//storage.delete(blobInfo);
		return;
	}
	// [END deleteFile]

}