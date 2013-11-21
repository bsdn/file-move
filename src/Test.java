import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Test {
	/**
	 * @param args
	 */
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {

	}

	private static void chufa() {
		long s = 234234;
		long b = 12323;
		float f = Float.parseFloat(130693922 + "")
				/ Float.parseFloat(130693999 + "");
		System.out.println(f);
	}

	private static void swing() {
		JFrame f = new JFrame();
		Container c = f.getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.CENTER));
		final JLabel jLabel = new JLabel();
		jLabel.setText("label");
		JButton bt = new JButton();
		bt.setText("change");
		bt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		c.add(jLabel);
		c.add(bt);
		f.setSize(500, 300);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static void pattern() {
		@SuppressWarnings("unused")
		Pattern p1 = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\-\\s]?((((0?"
						+ "[13578])|(1[02]))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))"
						+ "|(((0?[469])|(11))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|"
						+ "(0?2[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12"
						+ "35679])|([13579][01345789]))[\\-\\-\\s]?((((0?[13578])|(1[02]))"
						+ "[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))"
						+ "[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\-\\s]?((0?["
						+ "1-9])|(1[0-9])|(2[0-8]))))))");
		Pattern p = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-]?((((0?"
						+ "[13578])|(1[02]))[\\-]?((0?[1-9])|([1-2][0-9])|(3[01])))"
						+ "|(((0?[469])|(11))[\\-]?((0?[1-9])|([1-2][0-9])|(30)))|"
						+ "(0?2[\\-]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12"
						+ "35679])|([13579][01345789]))[\\-]?((((0?[13578])|(1[02]))"
						+ "[\\-]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))"
						+ "[\\-]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]?((0?["
						+ "1-9])|(1[0-9])|(2[0-8]))))))");
		String s = "0000-01-20";
		System.out.println(s + " " + p.matcher(s).matches());
		s = "2004/0/29";
		System.out.println(s + " " + p.matcher(s).matches());
		s = "2004/04/31";
		System.out.println(s + " " + p.matcher(s).matches());
		s = "2004/04/30";
		System.out.println(s + " " + p.matcher(s).matches());
		s = "2004/04/30";
		System.out.println(s + " " + p.matcher(s).matches());
		s = "2004/09/30";
		System.out.println(s + " " + p.matcher(s).matches());
	}

	private int age;
	private Integer age1;
	String name;
	private String code;
	private boolean b = true;

	public boolean isB() {
		return b;
	}

	public void setB(boolean b) {
		this.b = b;
	}

	Test test;

	Test() {
	}

	Test(String name, String code, int age, Integer age1, Test test) {
		this.name = name;
		this.code = code;
		this.age = age;
		this.age1 = age1;
		this.test = test;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Integer getAge1() {
		return age1;
	}

	public void setAge1(Integer age1) {
		this.age1 = age1;
	}

	@Override
	public String toString() {
		return Test.class.getName() + " [" + this.getName() + ","
				+ this.getAge() + "," + this.getAge1() + "," + this.getCode()
				+ "]";
	}

}
