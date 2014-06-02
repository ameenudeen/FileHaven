package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.GroupGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;

public class FileUploadService
{
	public static String CONTENT_TYPE_APPLICATION_OCTETSTREAM = "application/octet-stream";
	public static String CONTENT_TYPE_APPLICATION_MSWORD = "application/msword";
	public static String CONTENT_TYPE_APPLICATION_ZIP = "application/zip";
	public static String CONTENT_TYPE_APPLICATION_PDF = "application/pdf";
	public static String CONTENT_TYPE_APPLICATION_XGZIP = "application/x-gzip";
	public static String CONTENT_TYPE_APPLICATION_XCOMPRESSED = "application/x-compressed";
	public static String CONTENT_TYPE_IMAGE_JPEG = "image/jpeg";
	public static String CONTENT_TYPE_IMAGE_PNG = "image/png";
	public static String CONTENT_TYPE_IMAGE_GIF = "image/gif";
	public static String CONTENT_TYPE_IMAGE_BMP = "image/bmp";
	public static String CONTENT_TYPE_IMAGE_TIFF = "image/tiff";
	public static String CONTENT_TYPE_TEXT_PLAIN = "text/plain";
	public static String CONTENT_TYPE_TEXT_RTF = "text/rtf";
	public static String CONTENT_TYPE_AUDIO_MPEG = "audio/mpeg";
	public static String CONTENT_TYPE_PDF = "application/pdf";
	
	/**
	 * Connection details to Amazon S3.
	 */
	private S3Service service;
	private String accessKey;
	private String secretKey;
	private String bucket;

	/**
	 * Make sure the connection is established the first time a call is attempted.
	 */
	private boolean refreshConnection = true;

	/**
	 * Connects to S3 with the given accessKey and secretKey and also sets the default bucket to
	 * upload stuff to.
	 * 
	 * @param accessKey
	 *            The access key to connect to S3 with.
	 * @param secretKey
	 *            The secret key to connect to S3 with.
	 * @param bucket
	 *            The default bucket to use for default.
	 */

	public FileUploadService()
	{
		setAccessKey("");
		setSecretKey("");
		setBucket("filehavendata");
		doRefreshConnection();
	}

	public FileUploadService(String accessKey, String secretKey, String bucket)
	{
		setAccessKey(accessKey);
		setSecretKey(secretKey);
		setBucket(bucket);
		doRefreshConnection();
	}

	/**
	 * Refresh the connection to S3.
	 */
	private void doRefreshConnection()
	{
		/* Refresh the connection to S3. */
		try
		{
			service = new RestS3Service(new AWSCredentials(accessKey, secretKey));
		}
		catch (S3ServiceException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Uploads the given resource to the pre-specified bucket.
	 * 
	 * @param path
	 *            The path where to store the resource inside the bucket. A null-value will put the
	 *            file into the root folder.
	 * @param filename
	 *            The name to store the resource as.
	 * @param file
	 *            The resource to store inside the bucket.
	 * @param contentType
	 *            The file's content-type, as specified in the constants of this class.
	 * @return True, if the upload succeeded.
	 */
	public String upload(String path, String filename, InputStream file, String contentType)
	{
		return upload(bucket, path, filename, file, contentType);
	}

	/**
	 * Uploads the given resource to the specified bucket. Does not replace the pre-specified bucket
	 * as this is a one-time operation.
	 * Please note that the uploaded file will be set with the ACL "PublicRead" and is therefore
	 * accessible to everyone.
	 * 
	 * @param bucket
	 *            The target bucket inside S3. Needs to exist.
	 * @param path
	 *            The path where to store the resource inside the bucket. A null-value will put the
	 *            file into the root folder.
	 * @param filename
	 *            The name to store the resource as.
	 * @param file
	 *            The resource to store inside the bucket.
	 * @param contentType
	 *            The file's content-type, as specified in the constants of this class.
	 * @return pathToFile, the url of the file
	 */
	public String upload(String bucket, String path, String filename, InputStream is, String contentType)
	{
		/* Refresh the connection, if necessary. */
		if (isRefreshConnection())
			doRefreshConnection();
		/*
		 * The content length HAS to be set because we're not providing a File to the
		 * PutObjectRequest but a stream.
		 * Uploading stuff to S3 requires the amount of data to be specified before the upload. If
		 * that is not done then we'd have to buffer the
		 * entire stream (=File) before the upload. Setting the content length circumvents that.
		 */

		/* Proceed with the actual upload. */
		try
		{
			String prefix = FilenameUtils.getBaseName(filename);
			String suffix = FilenameUtils.getExtension(filename);

			File file = File.createTempFile(prefix, suffix);
			OutputStream outputStream = new FileOutputStream(file);
			IOUtils.copy(is, outputStream);
			outputStream.close();
			S3Object object = new S3Object(file);
			object.setName(sanitizePath(path) + filename);

			S3Bucket publicBucket = service.getBucket(bucket);
			AccessControlList bucketAcl = service.getBucketAcl(publicBucket);
			bucketAcl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ);
			publicBucket.setAcl(bucketAcl);
			object.setAcl(bucketAcl);
			service.putObject(publicBucket, object);
			String pathtToFile = "https://" + publicBucket.getName() + "." + service.getEndpoint() + "/" + object.getKey();
			System.out.println("View public object contents here:" + pathtToFile);
			return pathtToFile;
		}
		catch (S3ServiceException ase)
		{
			ase.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return "";
	}

	public InputStream loadFile(String filename) throws ServiceException
	{
		S3Object obj;
		try
		{
			obj = service.getObject(bucket, sanitizeFilename(filename));
			return obj.getDataInputStream();
		}
		catch (S3ServiceException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Sanitizes the path and makes sure there is no "/" at the front and that there is a "/" at the
	 * end of the path.
	 * 
	 * @param path
	 *            The path to clean and fix. If a null-value is given nothing is done as that means
	 *            that the file to be uploaded needs to be
	 *            put into the root folder.
	 * @return The cleaned up path.
	 */
	private String sanitizePath(String path)
	{
		if (path == null)
			return "";
		else if (path.startsWith("/"))
			path = path.substring(1);

		return path.endsWith("/") ? path : path + "/";
	}

	private String sanitizeFilename(String filename)
	{
		return sanitizePath(filename);
	}

	/**
	 * Deletes the file from the configured bucket.
	 * 
	 * @param pathAndFilename
	 *            The full path and filename of the file to delete.
	 * @return True, if the object is deleted or if there was no object at the given location.
	 */
	public boolean delete(String pathAndFilename)
	{

		/* Refresh the connection, if necessary. */

		if (isRefreshConnection())
			doRefreshConnection();

		try
		{
			service.deleteObject(bucket, pathAndFilename);
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Deletes the file from the specified bucket.
	 * 
	 * @param bucket
	 *            The bucket to delete the file from.
	 * @param pathAndFilename
	 *            The full path and filename of the file to delete.
	 * @return True, if the object is deleted or if there was no object at the given location.
	 */
	public boolean delete(String bucket, String pathAndFilename)
	{
		/* Refresh the connection, if necessary */
		if (isRefreshConnection())
			doRefreshConnection();

		try
		{
			service.deleteObject(bucket, pathAndFilename);
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
		}

		return true;
	}

	public void moveFile(String source, String dest)
	{
		try
		{
			S3Bucket bucketObj = new S3Bucket(bucket);

			S3Object object = new S3Object(sanitizeFilename(dest));
			AccessControlList accessControlList = new AccessControlList();
			accessControlList.setOwner(bucketObj.getOwner());
			accessControlList.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ);
			object.setAcl(accessControlList);

			service.renameObject(bucketObj.getName(), sanitizeFilename(source), object);

		}
		catch (ServiceException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param accessKey
	 *            the accessKey to set
	 */
	public void setAccessKey(String accessKey)
	{
		this.accessKey = accessKey;
		setRefreshConnection(true);
	}

	/**
	 * @param secretKey
	 *            the secretKey to set
	 */
	public void setSecretKey(String secretKey)
	{
		this.secretKey = secretKey;
		setRefreshConnection(true);
	}

	/**
	 * @param bucket
	 *            the bucket to set
	 */
	public void setBucket(String bucket)
	{
		this.bucket = bucket;
		setRefreshConnection(true);
	}

	/**
	 * @return the refreshConnection
	 */
	public boolean isRefreshConnection()
	{
		return refreshConnection;
	}

	/**
	 * @param refreshConnection
	 *            the refreshConnection to set
	 */
	public void setRefreshConnection(boolean refreshConnection)
	{
		this.refreshConnection = refreshConnection;
	}
	
}
