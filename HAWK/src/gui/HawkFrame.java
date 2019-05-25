package gui;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTree;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
//import javax.swing.text.StyledDocument;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.BadLocationException;
//import javax.swing.text.TabSet;
//import javax.swing.text.TabStop;

import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
// import javax.swing.event.TreeModelEvent;
// import javax.swing.event.TreeModelListener;
// import javax.swing.event.TreeExpansionEvent;
// import javax.swing.event.TreeExpansionListener;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

// import compiler.src.hawkcompiler.HawkCompiler;
import gui.JTabbedPaneCloseButton;
import gui.BackgroundPanel;
import hawkcompiler.HawkCompiler;

public class HawkFrame extends JFrame implements TreeSelectionListener/*, TreeExpansionListener*/ {

	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
	// private JPanel fileTreePanel;

//	private JTextArea textArea = new JTextArea();
	private JTextArea terminal = new JTextArea();

	private JTextPane textPane;

	private JLabel menuLabel = new JLabel(new ImageIcon("img/menu.png"));
	private JLabel openLabel = new JLabel(new ImageIcon("img/open.png"));
	private JLabel writeLabel = new JLabel(new ImageIcon("img/write.png"));
	private JLabel exitLabel = new JLabel(new ImageIcon("img/exit.png"));
	private JLabel titlebarLabel = new JLabel(new ImageIcon("img/titlebar.png"));
	private JLabel closeLabel = new JLabel(new ImageIcon("img/close.png"));
	private JLabel minimizeLabel = new JLabel(new ImageIcon("img/minimize.png"));
	private JLabel maximizeLabel = new JLabel(new ImageIcon("img/maximize.png"));

	private String openFileString = "untitled";
	private MouseListener myMouse = new MouseListener();

	private JTabbedPane tabbedPane = new JTabbedPaneCloseButton();

	private JScrollPane scrollingArea;
	private JScrollPane fileTreePane;

	private JTree tree;

	private JMenuBar menuBar1 = new JMenuBar();
	private JMenu fileMenu = new JMenu("FILE");
	private JMenuItem newItem = new JMenuItem("NEW");
	private JMenuItem openItem = new JMenuItem("OPEN");
	private JMenuItem saveItem = new JMenuItem("SAVE");
	private JMenuItem closeItem = new JMenuItem("CLOSE");
//	private JMenuItem i5 = new JMenuItem("NEW");


//	private JMenuBar menuBar2 = new JMenuBar();
	private JMenu programMenu = new JMenu("PROGRAM");
	// private JMenuItem j1 = new JMenuItem("COMPILE");
	private JMenuItem runItem = new JMenuItem("RUN");
	private JMenuItem terminateItem = new JMenuItem("TERMINATE");

	private HashMap<String, File> filenameToFileMap = new HashMap<String, File>();

	private boolean isFileTreeAdded = false;

	private HawkCompiler hawkCompiler = new HawkCompiler();
  
	public HawkFrame() {

		mainPanel = new BackgroundPanel("img/background.png");
		mainPanel.setLayout(null);
		add(mainPanel);
		
		openLabel.setBounds(440, 575, 172, 119);
		mainPanel.add(openLabel);
		
		writeLabel.setBounds(610, 575, 172, 119);
		mainPanel.add(writeLabel);

		exitLabel.setBounds(770, 575, 172, 119);
		mainPanel.add(exitLabel);

		menuLabel.setBounds(380,30,622,725);
		mainPanel.add(menuLabel);
		
		addListener();
  	}

	public void addListener() {

		openLabel.addMouseListener(myMouse);
		writeLabel.addMouseListener(myMouse);
		exitLabel.addMouseListener(myMouse);
		closeLabel.addMouseListener(myMouse);
		minimizeLabel.addMouseListener(myMouse);
		maximizeLabel.addMouseListener(myMouse);
	}
  
	public void removeObjects() {

		mainPanel.removeAll();
		repaint();
		revalidate();
	}

	public void addTitleBar() {

		closeLabel.setBounds(6, 6, 13, 12);
		mainPanel.add(closeLabel);
		
		minimizeLabel.setBounds(23, 6, 13,12);
		mainPanel.add(minimizeLabel);
		
		maximizeLabel.setBounds(40, 6, 13,12);
		mainPanel.add(maximizeLabel);
		
	    titlebarLabel.setBounds(0,0,1366, 24);
	    mainPanel.add(titlebarLabel);
	}
  
  	public void addEditor() {

	    final StyleContext cont = StyleContext.getDefaultStyleContext();
	    final AttributeSet dataTypes = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(30, 144, 255));
	    final AttributeSet voidKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(190, 19, 42));
	    final AttributeSet loopKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 204, 51));
	    final AttributeSet conditionalKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(12, 142, 126));
	    final AttributeSet booleanKeyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255, 247, 0));
	    final AttributeSet attrWhite = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(240, 240, 240));
	    
	    DefaultStyledDocument doc = new DefaultStyledDocument() {

			private static final long serialVersionUID = 1L;

			public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {

	        	// if(str.contains("("))
	         //    	super.insertString(offset, str + ")", a);
	         //    else if(str.contains("{"))
	         //    	super.insertString(offset, str + "}", a);
	         //    else if(str.contains("["))
	         //    	super.insertString(offset, str + "]", a);
	         //    else
	            super.insertString(offset, str, a);

	            String text = getText(0, getLength());
	            int before = findLastNonWordChar(text, offset);
	            if (before < 0) before = 0;
	            int after = findFirstNonWordChar(text, offset + str.length());
	            int wordL = before;
	            int wordR = before;

	            while (wordR <= after) {
	                if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
	                    if (text.substring(wordL, wordR).matches("(\\W)*(program|boolean|char|float|int|string)"))
	                        setCharacterAttributes(wordL, wordR - wordL, dataTypes, false);
	                    else if(text.substring(wordL, wordR).matches("(\\W)*(void|exit|nil|null)")) // nil or null not sure
	                    	setCharacterAttributes(wordL, wordR - wordL, voidKeyword, false);
	                    else if(text.substring(wordL, wordR).matches("(\\W)*(for|do|while|repeat|times|break)"))
	                    	setCharacterAttributes(wordL, wordR - wordL, loopKeyword, false);
	                    else if(text.substring(wordL, wordR).matches("(\\W)*(if|else|elsif|incase|is)"))
	                    	setCharacterAttributes(wordL, wordR - wordL, conditionalKeyword, false);
	                    else if(text.substring(wordL, wordR).matches("(\\W)*(true|false|TRUE|FALSE)")) // di ak sure hehe
	                    	setCharacterAttributes(wordL, wordR - wordL, booleanKeyword, false);
	                    else
	                        setCharacterAttributes(wordL, wordR - wordL, attrWhite, false);
	                    wordL = wordR;
	                }
	                wordR++;
	            }
	        }

	        public void remove (int offs, int len) throws BadLocationException {

	            super.remove(offs, len);

	            String text = getText(0, getLength());
	            int before = findLastNonWordChar(text, offs);
	            if (before < 0) before = 0;
	            int after = findFirstNonWordChar(text, offs);

	            if (text.substring(before, after).matches("(\\W)*(program|boolean|char|float|int|string)"))
	                setCharacterAttributes(before, after - before, dataTypes, false);
	            else if(text.substring(before, after).matches("(\\W)*(void|exit|nil|null)")) // nil or null not sure
	            	setCharacterAttributes(before, after - before, voidKeyword, false);
	            else if(text.substring(before, after).matches("(\\W)*(for|do|while|repeat|times|break)"))
	            	setCharacterAttributes(before, after - before, loopKeyword, false);
	            else if(text.substring(before, after).matches("(\\W)*(if|else|elsif|incase|is|default)"))
	            	setCharacterAttributes(before, after - before, conditionalKeyword, false);
	             else if(text.substring(before, after).matches("(\\W)*(true|false|TRUE|FALSE)")) // di ak sure hehe
	            	setCharacterAttributes(before, after - before, booleanKeyword, false);
	            else
	                setCharacterAttributes(before, after - before, attrWhite, false);
	        }
	    };

	    LookAndFeel previousLookAndFeel = UIManager.getLookAndFeel();
	    try {
	    	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

	    	textPane = new JTextPane(doc);
	    	textPane.setBackground(new Color(20, 20, 20));
	    	textPane.setForeground(new Color(240, 240, 240));
	    	textPane.setFont(new Font("Consolas", Font.PLAIN, 18));
	    	textPane.setCaretColor(new Color(240, 240, 240));

	    	UIManager.setLookAndFeel(previousLookAndFeel);
	    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) { }
	    
		JLayeredPane lp = getLayeredPane();
		scrollingArea = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    lp.add(scrollingArea);
	    
		tabbedPane.addTab(openFileString, scrollingArea);  
		tabbedPane.setBounds(305,50,620,712);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.addMouseListener(myMouse);
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);

		DefaultCaret caret = (DefaultCaret)textPane.getCaret();
	    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
  	}

	public void addFileTree(File selectedFile) {

		JLayeredPane layeredPane = getLayeredPane();

		// if (fileTreePane != null) {
		// 	fileTreePane.removeAll();
		// 	fileTreePane = null;
		// }

		try {
			String fileDirectory = selectedFile.getParent();
			String[] parentFolder = fileDirectory.replaceAll(Pattern.quote("\\"), "\\\\").split("\\\\");

			DefaultMutableTreeNode top = new DefaultMutableTreeNode(parentFolder[parentFolder.length - 1]);
			createNodes(top, selectedFile);
			DefaultTreeModel treeModel = new DefaultTreeModel(top);
			// treeModel.addTreeModelListener(this);

			tree = new JTree(treeModel);
			tree.setEditable(true);
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			tree.addTreeSelectionListener(this);
			// tree.addMouseListener(myMouse);
			// tree.addTreeExpansionListener(this);

			// fileTreePanel = new JPanel();
			// fileTreePanel.setBackground(new Color(20, 20, 20));
			// fileTreePanel.add(tree);

			fileTreePane = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			fileTreePane.setBounds(5, 80, 300, 682);
			layeredPane.add(fileTreePane);
		} catch (NullPointerException ex) {
			// fileTreePane = new JScrollPane(new JPanel(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			// fileTreePane.setBounds(5, 80, 300, 682);
		}
		
		// layeredPane.add(fileTreePane);
	}

	public void addMenu() {

		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(closeItem);
		menuBar1.add(fileMenu);

		mainPanel.add(menuBar1);
		menuBar1.setBounds(0, 25, 115, 25);

		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				openFile(false);
			}
		});

		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				saveFile();
			}
		});


		closeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				tabbedPane.removeTabAt(1);
			}
		});

		// programMenu.add(j1);
		programMenu.add(runItem);
		programMenu.add(terminateItem);
		menuBar1.add(programMenu);

		runItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
//					System.out.println(openFileString);
					hawkCompiler.compile("files/bin/" + openFileString, terminal);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

//		mainPanel.add(menuBar2);
//		menuBar2.setBounds(46, 25, 80, 25);

		JPanel textEditorPanel = new JPanel(null);
		textEditorPanel.setBounds(0,0,1366,768);
		textEditorPanel.setBackground(new Color(68,109,107));
		textEditorPanel.add(tabbedPane);
		mainPanel.add(textEditorPanel);
	}

	public void addConsole() {

		JLayeredPane lp = getLayeredPane();
		JScrollPane scrollingArea2 = new JScrollPane(terminal, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		scrollingArea2.setBounds(926,80,435,682);
		terminal.setBackground(new Color(20,20,20));
		terminal.setEditable(false);
		lp.add(scrollingArea2);

		DefaultCaret caret = (DefaultCaret)terminal.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		terminal.setFont(new Font("Consolas", Font.PLAIN, 15));
		terminal.setForeground(new Color(240,240,240));

		terminal.append("Hawk Programming Console [Version 1.0]\n(c) 2018 Seven Deadly Sins. All rights reserved.\n\n");
	}
 
	private class MouseListener extends MouseAdapter {

		public void mouseClicked(MouseEvent event) {

			if(event.getSource() == exitLabel) {
				System.exit(0);
			}

			if(event.getSource() == closeLabel) {
				System.exit(0);
			}

			if(event.getSource() == minimizeLabel) {
				setState(ICONIFIED);
			}

			if(event.getSource() == openLabel) {
				openFile(true);
			}

			if(event.getSource() == writeLabel) {
				removeObjects();
				addTitleBar();
				addMenu();
				addEditor();
				addConsole();
				addFileTree(null);
			}

			/*if (event.getSource() == tree) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

				if (node == null) {
					return;
				}

				if (node.isLeaf()) {
					terminal.append("\nleaf");
				}
			}*/

			if(event.getSource() == tabbedPane) {

			}
		}

		public void mouseEntered(MouseEvent event) {

			if(event.getSource() == exitLabel) {
				exitLabel.setIcon(new ImageIcon("img/exitInv.png"));
			}

			if(event.getSource() == openLabel) {
				openLabel.setIcon(new ImageIcon("img/openInv.png"));
			}

			if(event.getSource() == writeLabel) {
				writeLabel.setIcon(new ImageIcon("img/writeInv.png"));
			}
		}

		public void mouseExited(MouseEvent event) {

			if(event.getSource() == exitLabel) {
				exitLabel.setIcon(new ImageIcon("img/exit.png"));
			}

			if(event.getSource() == openLabel) {
				openLabel.setIcon(new ImageIcon("img/open.png"));
			}

			if(event.getSource() == writeLabel) {
				writeLabel.setIcon(new ImageIcon("img/write.png"));
			}
		}
	}	  

  
	private void appendToPane(JTextPane tp, String msg, Color c) {

		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Consolas");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}

	public void openFile(boolean isOpenedFromMainMenu) {

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("files/bin"));
		chooser.setMultiSelectionEnabled(true);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".hawk", "hawk");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);

		if(returnVal == JFileChooser.APPROVE_OPTION) {
			// File selectedFile = chooser.getSelectedFile();
			// openFileString = selectedFile.getName();
			// String filePath = selectedFile.getParent();
			// System.out.println("Path: " + filePath);

			ArrayList<String> list = new ArrayList<String>();
			File[] selectedFile = chooser.getSelectedFiles();
			for(File files : selectedFile){
			  list.add(files.getName());	
	          System.out.println(files);
			}

			if (isOpenedFromMainMenu) {
				removeObjects();
				addTitleBar();
				addMenu();
				addConsole();
			}
				// addFileTree(selectedFile);
			for(File files : selectedFile) {
				if (!isFileTreeAdded) {
					addFileTree(files);
					isFileTreeAdded = true;
				}
				openFileString = files.getName();
				addEditor();
				appendFileContentsToPane();
			}
		}
	}

	private int findLastNonWordChar (String text, int index) {
		while (--index >= 0) {
			if (String.valueOf(text.charAt(index)).matches("\\W")) {
				break;
			}
		}
		return index;
	}

	private int findFirstNonWordChar (String text, int index) {
		while (index < text.length()) {
			if (String.valueOf(text.charAt(index)).matches("\\W")) {
				break;
			}
			index++;
		}
		return index;
	}

	private void createNodes(DefaultMutableTreeNode top, File selectedFile) {

		DefaultMutableTreeNode folder = null;
		DefaultMutableTreeNode file = null;
		ArrayList<DefaultMutableTreeNode> filesArray = new ArrayList<DefaultMutableTreeNode>();

		File[] files = selectedFile.getParentFile().listFiles();

		isDir(files, null, top, filesArray);
		addFiles(files, null, top, filesArray, 0);
	}

	public void isDir(File[] files, DefaultMutableTreeNode node, DefaultMutableTreeNode top, ArrayList<DefaultMutableTreeNode> filesArray) {

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode(files[i].getName());
				filesArray.add(tempNode);

				if (node == null) {
					top.add(tempNode);
				} else {
					node.add(tempNode);
				}

				if (files[i].listFiles().length == 0) {
					tempNode.add(new DefaultMutableTreeNode(null));
				} else {
					isDir(files[i].listFiles(), tempNode, top, filesArray);
				}
			}
		}
	}

	public void addFiles(File[] files, DefaultMutableTreeNode node, DefaultMutableTreeNode top, ArrayList<DefaultMutableTreeNode> filesArray, int index) {

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				DefaultMutableTreeNode tempNode = filesArray.get(index);
				index++;

				if (files[i].listFiles().length != 0) {
					addFiles(files[i].listFiles(), tempNode, top, filesArray, index);
				}
			} else {
				if (node == null) {
					top.add(new DefaultMutableTreeNode(files[i].getName()));
				} else {
					node.add(new DefaultMutableTreeNode(files[i].getName()));
				}
				filenameToFileMap.put(files[i].getName(), files[i]);
			}
		}
		index--;
	}

	private void appendFileContentsToPane() {

		try {
			BufferedReader br = new BufferedReader(new FileReader(("files/bin/" + openFileString)));
			String line = null;
			while ((line = br.readLine()) != null) {
				for(String temp : line.split(" ")) {
					appendToPane(textPane, temp + " ", Color.BLACK);
				}
				appendToPane(textPane, "\n", Color.BLACK);
			}
		} catch(Exception IO) { }
	}

	private void saveFile() {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("files/bin"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".hawk", "hawk");
		fileChooser.setFileFilter(filter);

		int choice = fileChooser.showSaveDialog(null);
		if (choice == JFileChooser.APPROVE_OPTION) {
			File chosenFile = fileChooser.getSelectedFile();
			// terminal.append("\n Saved at: " + chosenFile.getAbsolutePath());
			// terminal.append("\n File exists? " + chosenFile.exists());

			if (!chosenFile.exists()) {
				chosenFile = new File(chosenFile.getAbsolutePath());
			}
			
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(chosenFile));
				writer.write(textPane.getDocument().getText(0, textPane.getDocument().getLength()));
				writer.close();
			} catch (IOException | BadLocationException ex) { }

			if (tree == null) {
				addFileTree(chosenFile);

				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if (tabbedPane.getTitleAt(i).equals("untitled")) {
						tabbedPane.setTitleAt(i, chosenFile.getName());
					}
				}
			} else {
				// tree = null;
				// fileTreePane.removeAll();
				// fileTreePane = null;
				addFileTree(chosenFile);
			}
		}
	}

	public void valueChanged(TreeSelectionEvent event) {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

		if (node == null) {
			return;
		}

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
			String fileName = (String)nodeInfo;

			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				// terminal.append("\n" + fileName + " | " + tabbedPane.getTitleAt(i));
				if (fileName.equals(tabbedPane.getTitleAt(i))) {
					tabbedPane.setSelectedIndex(i);
					break;
				} else {
					if (i == tabbedPane.getTabCount() - 1) {
						File file = filenameToFileMap.get(fileName);
						openFileString = fileName;
						addEditor();
						appendFileContentsToPane();
					}
				}
			}
		}
	}
}