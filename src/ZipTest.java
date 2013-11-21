import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTest {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		File f = new File(
				"D:\\devtools\\apache-tomcat-7.0.40\\apache-tomcat-7.0.40\\bin\\startup.bat");
		FileInputStream fis = new FileInputStream(f);
		BufferedInputStream bis = new BufferedInputStream(fis);
		byte[] buf = new byte[8192];
		int len;
		File o = new File(f.getAbsolutePath() + ".zip");
		if (o.exists()) {
			o.createNewFile();
		}
		System.out.println(o.getAbsolutePath());
		FileOutputStream fos = new FileOutputStream(o);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		ZipOutputStream zos = new ZipOutputStream(bos);// 压缩包
		ZipEntry ze = new ZipEntry(f.getName());// 这是压缩包名里的文件名
		zos.putNextEntry(ze);// 写入新的 ZIP 文件条目并将流定位到条目数据的开始处

		while ((len = bis.read(buf)) != -1) {
			zos.write(buf, 0, len);
			zos.flush();
		}
		bis.close();
		zos.close();
	}

}
