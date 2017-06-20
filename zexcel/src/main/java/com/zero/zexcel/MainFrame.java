package com.zero.zexcel;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "ZExcel";

	private static final LayoutManager DEFAULT_LAYOUT = null;

	private JPanel contentPane = new JPanel();

	private JTextField sourcePath = new JTextField();

	private JTextField keyPath = new JTextField();

	private JButton analysis = new JButton("Start Process");
	
	private JProgressBar progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
	
	private static MainFrame frame;

	private MainFrame() {
		super(NAME);
	}

	public static JFrame CreateGUI() throws UnsupportedLookAndFeelException {
		initTheme();
		initFrame();
		return frame;
	}

	private static void initFrame() {
		if (frame == null) {
			frame = new MainFrame();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800, 350));
		frame.initIcon();
		frame.applyContent();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
	}

	protected void initIcon() {
		ImageIcon icon = new ImageIcon(getClass().getResource("ui/icon.png"));
		this.setIconImage(icon.getImage());
	}

	private static void initTheme() throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new SubstanceBusinessBlueSteelLookAndFeel());
		setDefaultLookAndFeelDecorated(true);
	}

	private void applyContent() {
		applyPanel();
		this.add(contentPane);
	}

	private void applyPanel() {
		applyDefaultLayout(contentPane);
		applyTextField();
		applyButton();
		applyProgessBar();
	}

	private void applyTextField() {
		JLabel l_source = new JLabel("Source Excel Folder:");
		l_source.setBounds(new Rectangle(new Point(40, 50), new Dimension(120, 25)));
		sourcePath.setBounds(new Rectangle(new Point(170, 50), new Dimension(500, 25)));
		contentPane.add(l_source);
		contentPane.add(sourcePath);
		JLabel l_key = new JLabel("KeyWord Excel:");
		l_key.setBounds(new Rectangle(new Point(40, 150), new Dimension(120, 25)));
		keyPath.setBounds(new Rectangle(new Point(170, 150), new Dimension(500, 25)));
		contentPane.add(l_key);
		contentPane.add(keyPath);
	}

	private void applyButton() {
		analysis.setBounds(new Rectangle(new Point(340, 200), new Dimension(120, 25)));
		analysis.addActionListener(new analysisAction());
		contentPane.add(analysis);
	}
	
	private void applyProgessBar(){
		progressBar.setBounds(new Rectangle(new Point(0, 299), new Dimension(800, 18)));
		progressBar.setStringPainted(true);
		progressBar.setString("");
		progressBar.setVisible(false);
		contentPane.add(progressBar);
		Console print = new Console(System.out, progressBar);
		System.setOut(print);
		System.setErr(print);
	}

	private static void applyDefaultLayout(Container container) {
		if (null == container)
			return;
		container.setLayout(DEFAULT_LAYOUT);
	}
	
	private void onAnalisisStart(){
		final String s = sourcePath.getText().trim();
		final String k = keyPath.getText().trim();
		if ("".equals(s) || "".equals(k)) {
			System.out.println("Please select source excels path and key word file");
			return;
		}
		beginAnalisisTask(s,k);
	}
	
	private Object beginAnalisisTask(final String s, final String k){
		try {
			disableProcess();
			System.out.println("Start process... ");
			new CoreProcessor(this, s, k).process();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void initProgressBar(int min, int max){
		this.progressBar.setMinimum(min);
		this.progressBar.setMaximum(max);
	}
	
	public void setProgressValue(int i){
		this.progressBar.setValue(i);
	}
	
	private void disableProcess(){
		this.analysis.setEnabled(false);
		this.progressBar.setValue(0);
		this.progressBar.setVisible(true);
	}
	
	public void enableProcess(){
		this.analysis.setEnabled(true);
		this.progressBar.setValue(0);
		this.progressBar.setVisible(false);
	}
	
	private class analysisAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			onAnalisisStart();
		}
	}
}
