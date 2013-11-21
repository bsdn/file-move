package cn.bsdn.fileutils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;

/**
 * @author Lucas
 * 
 */
public class Backup extends SwingWorker<String, Object> {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String sourceDir;
	String destDir;
	long fromDate;
	JLabel msgLabel;
	static int fileCount = 0;
	JProgressBar jpb;
	long totalSize;
	long processSize = 0;
	long start;

	public Backup(String sourceDir, String destDir, long fromDate,
			JLabel msgLabel, JProgressBar jpb) {
		this.sourceDir = sourceDir;
		this.destDir = destDir;
		this.fromDate = fromDate;
		this.msgLabel = msgLabel;
		this.jpb = jpb;
	}

	/**
	 * @param args
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ParseException, IOException {
	}

	public void store(String sourceFile, String destiFile) throws IOException {
		File sourcefile = new File(sourceFile);
		if (sourcefile.isDirectory()) {
			File childrenFiles[] = sourcefile.listFiles();
			if (childrenFiles.length > 0) {
				for (File f : childrenFiles) {
					store(f.getAbsolutePath(), destiFile);
				}
				if (!sourceFile.equals(this.getSourceDir())) {
					if (sourcefile.lastModified() >= this.fromDate) {
						Backup.fileCount++;
						publish("备份文件" + sourcefile.getAbsolutePath() + "到"
								+ destiFile);
					}
				}
			} else {
				if (sourceFile.equals(this.getSourceDir())) {
					publish("执行中...");
				}
				doCopyEmptyDictoryOrFile(sourcefile, msgLabel);
			}
		} else {
			doCopyEmptyDictoryOrFile(sourcefile, msgLabel);
		}
	}

	private void doCopyEmptyDictoryOrFile(File file, JLabel msgLabel) {
		if (file.lastModified() >= this.fromDate) {
			Backup.fileCount++;
			String bk = getDestiFilePath(file);
			if (file.isDirectory()) {
				try {
					FileUtils.copyDirectory(file, new File(bk));
				} catch (IOException e) {
					e.printStackTrace();
					publish("文件[" + file.getName() + "]不存在或不允许访问！");
					throw new RuntimeException(e.getMessage());
				}
				String msg = "备份文件" + file.getAbsolutePath() + "到" + bk;
				publish(msg);
				System.out.println(msg);
			} else {
				File bkFile = new File(bk);
				try {
					FileUtils.copyFile(file, bkFile);
				} catch (IOException e) {
					e.printStackTrace();
					publish("文件[" + file.getName() + "]不存在或不允许访问！");
					throw new RuntimeException(e.getMessage());
				}
				long bkFileSize = FileCalculator.getFileSize(bkFile, fromDate);
				String msg = bkFileSize + ";备份文件" + file.getAbsolutePath()
						+ "到" + bk;
				publish(msg);
				System.out.println(msg);
			}
		}
	}

	private String getDestiFilePath(File file) {
		String fromDirTemp = sourceDir.substring(3);
		String replaceFrom1 = sourceDir.substring(0, 1);
		String replaceFrom2 = fromDirTemp;
		if (replaceFrom2.contains("\\")) {
			replaceFrom2 = replaceFrom2.replace("\\", "\\\\");
		}
		String endDirTemp = destDir.substring(3);
		String replaceEnd1 = destDir.substring(0, 1);
		String replaceEnd2 = endDirTemp;
		if (replaceEnd2.contains("\\")) {
			replaceEnd2 = replaceEnd2.replace("\\", "\\\\");
		}
		String bk1 = file.getAbsolutePath().replaceFirst(replaceFrom1,
				replaceEnd1);
		String bk = bk1.replaceFirst(replaceFrom2, replaceEnd2);
		return bk;
	}

	public String getSourceDir() {
		return sourceDir;
	}

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	@Override
	protected String doInBackground() throws Exception {
		Backup.fileCount = 0;
		start = new Date().getTime();
		msgLabel.setText("正在计算进度，请稍候...");
		try {
			totalSize = FileCalculator.getDirectorySize(new File(sourceDir),
					fromDate);
		} catch (Exception e) {
			e.printStackTrace();
			return ("计算文件大小出错，请确保当前用户具有要备份目录的访问权限。");
		}
		store(sourceDir, destDir);
		long end = new Date().getTime();
		long minutes = (end - start) / 60000;
		long seconds = (end - start) / 1000 % 60;
		long mims = (end - start) % 1000;
		System.out.println("备份完成：" + start + "-" + end);
		return "共备份" + Backup.fileCount + "个文件，耗时" + minutes + "分" + seconds
				+ "秒" + mims + "毫秒。";
	}

	@Override
	protected void process(List<Object> chunks) {
		for (Object chunk : chunks) {
			String chunksStr[] = ((String) chunk).split(";");
			if (chunksStr.length == 2) {
				processSize += Long.parseLong(chunksStr[0]);
				double pro = Double.parseDouble(processSize + "")
						/ Double.parseDouble(this.totalSize + "");
				jpb.setValue((int) (pro * 10000));
				msgLabel.setText(chunksStr[1]);
			} else {
				msgLabel.setText(chunksStr[0]);
			}
		}
	}

	@Override
	protected void done() {
		if (!isCancelled()) {
			try {
				msgLabel.setText(get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	public long getFromDate() {
		return fromDate;
	}

	public void setFromDate(long fromDate) {
		this.fromDate = fromDate;
	}

}
