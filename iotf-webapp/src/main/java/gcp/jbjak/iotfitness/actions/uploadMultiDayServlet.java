//[START all]
package gcp.jbjak.iotfitness.actions;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

import gcp.jbjak.iotfitness.util.CloudStorageHelper;

//Imports the Google Cloud client library
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@MultipartConfig
@WebServlet(name = "uploadMultiDay", value = "/uploadMultiDay")

public class uploadMultiDayServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String msg = new String("");
		CloudStorageHelper storageHelper = new CloudStorageHelper();

		try {

			String bucket = getServletContext().getInitParameter("upload.bucket");

			if (bucket != null) {

				String fileUrl = storageHelper.getFileUrl(req, resp, bucket);

				if (fileUrl != null) {
					msg = "### Uploaded File (" + fileUrl + ") to storage bucket: " + bucket;
					System.out.println(msg);
					System.out.flush();
				} else {
					msg = "Invalid upload file!";
					System.err.println(msg);
					System.err.flush();
				}

			} else {

				msg = "Invalid storage bucket!";
				System.err.println(msg);
				System.err.flush();
			}

			req.getRequestDispatcher("/home.jsp").forward(req, resp);

		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
// [END all]
