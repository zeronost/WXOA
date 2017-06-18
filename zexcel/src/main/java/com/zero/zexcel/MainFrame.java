package com.zero.zexcel;

import java.awt.Color;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "ZExcel";

	private static final LayoutManager DEFAULT_LAYOUT = null;

	private JPanel contentPane = new JPanel();

	private JTextField sourcePath = new JTextField();

	private JTextField keyPath = new JTextField();

	private JButton analysis = new JButton("Start Analysis");

	private JTextArea console = new JTextArea();

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
		frame.setPreferredSize(new Dimension(800, 600));
		frame.initIcon();
		frame.applyContent();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
	}

	protected void initIcon() {
		ImageIcon icon = new ImageIcon(getClass().getResource("./ui/icon.png"));
		this.setIconImage(icon.getImage());
	}

	private static void initTheme() throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new SubstanceNebulaBrickWallLookAndFeel());
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
		applyConsole();
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

	private void applyConsole() {
		console.setBounds(new Rectangle(new Point(0, 0), new Dimension(770, 300)));
		console.setEditable(false);
		console.setBackground(Color.WHITE);
		JScrollPane scroll = new JScrollPane(console);
		scroll.setBounds(new Rectangle(new Point(10, 250), new Dimension(770, 300)));
		contentPane.add(scroll);
		Console print = new Console(System.out, console);
		System.setOut(print);
		System.setErr(print);
	}

	private static void applyDefaultLayout(Container container) {
		if (null == container)
			return;
		container.setLayout(DEFAULT_LAYOUT);
	}

	private class analysisAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			final String s = sourcePath.getText().trim();
			final String k = keyPath.getText().trim();
			if ("".equals(s) || "".equals(k)) {
				System.out.println("Please select source excels path and key word file");
				return;
			}
			System.out.println("Start analysis... ");
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						new CoreProcessor().process(s, k);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
