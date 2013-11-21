package cn.bsdn.fileutils;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static String sourceDir = null;
	private static String destDir = null;
	private static int monthCount = 1;

	Frame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setSize(new Dimension(500, 320));
		this.setResizable(false);
		this.setTitle("DMS备份工具");
		this.setLocationRelativeTo(null);
		this.setIconImage(getImageFromJar("/" + Frame.Icon.BkTool.getIconPath()));
		Container container = this.getContentPane();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(getDummyPanel(40, 40));
		container.add(getSoucePanel());
		container.add(getDestPanel());
		container.add(getSelectDatePanel());
		container.add(getExecutePanel());
	}

	private JPanel getDummyPanel(int width, int height) {
		JPanel jPanel = new JPanel();
		jPanel.setMinimumSize(new Dimension(width, height));
		jPanel.setPreferredSize(new Dimension(width, height));
		jPanel.setMaximumSize(new Dimension(width, height));
		return jPanel;
	}

	private JPanel getSoucePanel() {
		JPanel jPanel = new JPanel();
		jPanel.setLayout(null);
		JLabel sourceJLabel = new JLabel("原目录：");
		sourceJLabel.setBounds(new Rectangle(new Point(42, 0), new Dimension(
				70, 30)));
		jPanel.add(sourceJLabel);
		final JTextField sourceField = new JTextField();
		sourceField.setBounds(new Rectangle(new Point(100, 0), new Dimension(
				330, 30)));
		sourceField.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					JFileChooser jfc = new JFileChooser();
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int result = jfc.showOpenDialog(null);
					if (result == JFileChooser.APPROVE_OPTION) {
						sourceField.setText(jfc.getSelectedFile()
								.getAbsolutePath());
						sourceDir = jfc.getSelectedFile().getAbsolutePath();
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		jPanel.add(sourceJLabel);
		jPanel.add(sourceField);
		return jPanel;
	}

	private JPanel getDestPanel() {
		JPanel jPanel = new JPanel();
		jPanel.setLayout(null);
		JLabel destJLabel = new JLabel("目标目录：");
		destJLabel.setBounds(new Rectangle(new Point(30, 0), new Dimension(70,
				30)));
		final JTextField destField = new JTextField();
		destField.setBounds(new Rectangle(new Point(100, 0), new Dimension(330,
				30)));
		jPanel.add(destJLabel);
		jPanel.add(destField);
		destField.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					JFileChooser jfc = new JFileChooser();
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int result = jfc.showOpenDialog(null);
					if (result == JFileChooser.APPROVE_OPTION) {
						destField.setText(jfc.getSelectedFile()
								.getAbsolutePath());
						destDir = jfc.getSelectedFile().getAbsolutePath();
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		return jPanel;
	}

	private JPanel getSelectDatePanel() {
		JPanel jPanel = new JPanel();
		jPanel.setLayout(null);
		JComboBox selectDateBox = new JComboBox();
		selectDateBox.addItem("最近一个月");
		selectDateBox.addItem("最近一年");
		selectDateBox.addItem("全部");
		selectDateBox.setBounds(new Rectangle(new Point(100, 0), new Dimension(
				330, 30)));
		selectDateBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if (obj instanceof JComboBox) {
					JComboBox selectDateBox = (JComboBox) obj;
					String select = (String) selectDateBox.getSelectedItem();
					if ("最近一个月".equals(select)) {
						monthCount = 1;
					} else if ("最近一年".equals(select)) {
						monthCount = 12;
					} else if ("全部".equals(select)) {
						monthCount = 12000;
					}
				}
			}
		});
		jPanel.add(selectDateBox);
		return jPanel;
	}

	private JPanel getExecutePanel() {
		final JPanel jPanel = new JPanel();
		jPanel.setLayout(null);
		jPanel.setPreferredSize(new Dimension(500, 80));
		final JLabel msgLabel = new JLabel();
		msgLabel.setName("msgLabel");
		msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		msgLabel.setBounds(new Rectangle(new Point(0, 15), new Dimension(500,
				30)));
		jPanel.add(msgLabel);
		final JProgressBar jpb = new JProgressBar();
		jpb.setMinimum(0);
		jpb.setMaximum(10000);
		JButton backup = new JButton("运行");
		backup.setBounds(new Rectangle(new Point(200, 50), new Dimension(100,
				35)));
		backup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sourceDir != null && destDir != null) {
					jpb.setBounds(new Rectangle(new Point(100, 1),
							new Dimension(330, 15)));
					jPanel.add(jpb);
					Calendar now = Calendar.getInstance();
					now.add(Calendar.MONTH, -monthCount);
					long fromDate = now.getTime().getTime();
					msgLabel.setText("正在计算进度，请稍候...");
					new Backup(sourceDir, destDir, fromDate, msgLabel, jpb)
							.execute();
				} else {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							msgLabel.setText("原目录和目标目录均不能为空");
						}
					});
				}
			}
		});
		jPanel.add(backup);
		ImageIcon backupIcon = getImageIconFromJar("/"
				+ Icon.Backup.getIconPath());
		backup.setIcon(backupIcon);
		return jPanel;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Frame().setVisible(true);
			}
		});
	}

	private static ImageIcon getImageIconFromJar(String path) {
		ImageIcon imageIcon = null;
		Image image = getImageFromJar(path);
		if (image != null)
			imageIcon = new ImageIcon(image);
		return imageIcon;
	}

	private static Image getImageFromJar(String path) {
		Image image = null;
		InputStream inputstream = Frame.class.getResourceAsStream(path);
		if (inputstream != null) {
			ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
			byte abyte0[] = null;
			try {
				abyte0 = new byte[inputstream.available()];
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				for (int i = 0; (i = inputstream.read(abyte0)) >= 0;) {
					bytearrayoutputstream.write(abyte0, 0, i);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			image = Toolkit.getDefaultToolkit().createImage(
					bytearrayoutputstream.toByteArray());
		}
		return image;
	}

	enum Icon {
		Backup("cn/bsdn/resources/icons/hd_backups_24.png"), Restore(
				"cn/bsdn/resources/icons/restore.png"), BkTool(
				"cn/bsdn/resources/icons/lightbrown_backup.png");
		Icon(String iconPath) {
			this.iconPath = iconPath;
		}

		private String iconPath;

		public String getIconPath() {
			return iconPath;
		}
	}

}
