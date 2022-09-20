package mvc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import shapes.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Font;

public class DrawingFrame extends JFrame {  		
	
	private DrawingView view = new DrawingView();
	
	private DrawingController controller;
	
	private JPanel contentPane;
	
	private int select;
	
	private JToggleButton tglbtnSelect;
	
	private JButton btnModify, btnDelete, btnUndo, btnRedo, btnCurrentBorderColor, btnCurrentInnerColor, btnToFront, btnToBack, btnBringToFront, btnBringToBack;
	
	private Color currentBorderColor = Color.BLACK;
	private Color currentInnerColor = Color.WHITE;

	private boolean isCurrentBorderColorConfirmation, isCurrentInnerColorConfirmation;
	
	private JTextArea textArea;
	private JMenuBar menuBar;
	private JMenu jMenu;
	private JMenuItem menuItemSavePainting;
	private JMenuItem menuItemOpenPainting;
	private JMenuItem menuItemSaveLog;
	private JMenuItem menuItemOpenLog;
	private JButton btnLoadNext;



	public DrawingFrame() {
		setTitle("Pavlov Andrija IT73-2018");
		setBounds(100, 100, 1000, 650);
		
		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		setJMenuBar(menuBar);
		
		jMenu = new JMenu("File");
		jMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
		menuBar.add(jMenu);
		
		menuItemSavePainting = new JMenuItem("Save painting");
		menuItemSavePainting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.savePainting();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		jMenu.add(menuItemSavePainting);
		
		menuItemOpenPainting = new JMenuItem("Open painting");
		menuItemOpenPainting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.openPainting();
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		jMenu.add(menuItemOpenPainting);
		
		menuItemSaveLog = new JMenuItem("Save log");
		menuItemSaveLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveLog();
			}
		});
		jMenu.add(menuItemSaveLog);
		
		menuItemOpenLog = new JMenuItem("Open log");
		menuItemOpenLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.openLog();
			}
		});
		jMenu.add(menuItemOpenLog);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		ButtonGroup btnGroup = new ButtonGroup();
		
		JPanel pnlNorth = new JPanel();
		
		JLabel lblDrawing = new JLabel("DRAWING");
		
		JPanel pnlWest = new JPanel();
		
		JToggleButton tglbtnPoint = new JToggleButton("Point");
		tglbtnPoint.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				select = 1;
				btnCurrentInnerColor.setEnabled(false);
			}
		});
		btnGroup.add(tglbtnPoint);
		
		JToggleButton tglbtnLine = new JToggleButton("Line");
		tglbtnLine.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				select = 2;
				btnCurrentInnerColor.setEnabled(false);
			}
		});
		btnGroup.add(tglbtnLine);
		
		JToggleButton tglbtnCircle = new JToggleButton("Circle");
		tglbtnCircle.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				select = 3;
				btnCurrentInnerColor.setEnabled(true);
			}
		});
		btnGroup.add(tglbtnCircle);
		
		JToggleButton tglbtnRectangle = new JToggleButton("Rectangle");
		tglbtnRectangle.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				select = 4;
				btnCurrentInnerColor.setEnabled(true);
			}
		});
		
		btnCurrentBorderColor = new JButton("Border Color");
		btnCurrentBorderColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentBorderColor = JColorChooser.showDialog(null,"Chose a color.", currentBorderColor);
				btnCurrentBorderColor.setBackground(currentBorderColor);
				isCurrentBorderColorConfirmation = true;
			}
		});
		btnCurrentBorderColor.setBackground(this.currentBorderColor);
		btnGroup.add(tglbtnRectangle);
		
		JToggleButton tglbtnDonut = new JToggleButton("Donut");
		tglbtnDonut.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				select = 5;
				btnCurrentInnerColor.setEnabled(true);
			}
		});
		btnGroup.add(tglbtnDonut);
		
		JToggleButton tglbtnHexagon = new JToggleButton("Hexagon");
		tglbtnHexagon.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				select = 6;
				btnCurrentInnerColor.setEnabled(true);
			}
		});
		
		btnCurrentInnerColor = new JButton("Inner Color");
		btnCurrentInnerColor.setBackground(currentInnerColor);
		btnCurrentInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentInnerColor = JColorChooser.showDialog(null,"Chose a color.", currentInnerColor);
				btnCurrentInnerColor.setBackground(currentInnerColor);
				isCurrentInnerColorConfirmation = true;
			}
		});
		btnGroup.add(tglbtnHexagon);
		
		JScrollPane scrollPaneDraw = new JScrollPane();
		
		tglbtnSelect = new JToggleButton("Select");
		tglbtnSelect.setEnabled(false);
		btnGroup.add(tglbtnSelect);
		tglbtnSelect.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				select = 7;
			}
		});
		
		btnModify = new JButton("Modify");
		btnModify.setEnabled(false);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.modifyShape();
			}
		});
		
		btnToFront = new JButton("Shape To Front");
		btnToFront.setEnabled(false);
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.toFront();
			}
		});
		
		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.deleteShape();
			}
		});
		
		btnToBack = new JButton("Shape to Back");
		btnToBack.setEnabled(false);
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
			}
		});
		
		btnUndo = new JButton("Undo");
		btnUndo.setEnabled(false);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.undo();
			}
		});
		
		btnRedo = new JButton("Redo");
		btnRedo.setEnabled(false);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.redo();
			}
		});
		
		btnBringToFront = new JButton("Shape Bring To Front");
		btnBringToFront.setEnabled(false);
		btnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.bringToFront();
			}
		});
		
		btnBringToBack = new JButton("Shape Bring To Back");
		btnBringToBack.setEnabled(false);
		btnBringToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToBack();
			}
		});
		view.setBackground(Color.WHITE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(pnlWest, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(view, GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE))
						.addComponent(pnlNorth, GroupLayout.PREFERRED_SIZE, 774, GroupLayout.PREFERRED_SIZE))
					.addGap(0))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(pnlNorth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlWest, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
						.addComponent(view, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)))
		);
		
		btnLoadNext = new JButton("Load Next");
		btnLoadNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadNext();
			}
		});
		btnLoadNext.setEnabled(false);
		GroupLayout gl_pnlWest = new GroupLayout(pnlWest);
		gl_pnlWest.setHorizontalGroup(
			gl_pnlWest.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlWest.createSequentialGroup()
					.addGap(45)
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.LEADING)
						.addComponent(btnUndo)
						.addComponent(tglbtnSelect)
						.addComponent(btnModify)
						.addComponent(btnDelete)
						.addComponent(btnRedo))
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlWest.createSequentialGroup()
							.addGap(40)
							.addGroup(gl_pnlWest.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnToBack)
								.addComponent(btnToFront)))
						.addGroup(gl_pnlWest.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_pnlWest.createParallelGroup(Alignment.LEADING)
								.addComponent(btnBringToBack)
								.addComponent(btnBringToFront))))
					.addContainerGap(48, Short.MAX_VALUE))
				.addComponent(scrollPaneDraw, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
				.addGroup(gl_pnlWest.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlWest.createSequentialGroup()
							.addComponent(tglbtnCircle)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tglbtnRectangle))
						.addGroup(gl_pnlWest.createSequentialGroup()
							.addGap(5)
							.addComponent(tglbtnPoint)
							.addGap(18)
							.addComponent(tglbtnLine))
						.addGroup(gl_pnlWest.createSequentialGroup()
							.addComponent(tglbtnDonut)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tglbtnHexagon)))
					.addGap(18)
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnLoadNext, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnCurrentInnerColor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnCurrentBorderColor, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		gl_pnlWest.setVerticalGroup(
			gl_pnlWest.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlWest.createSequentialGroup()
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlWest.createSequentialGroup()
							.addGroup(gl_pnlWest.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlWest.createParallelGroup(Alignment.BASELINE)
									.addComponent(tglbtnLine)
									.addComponent(tglbtnPoint))
								.addGroup(gl_pnlWest.createSequentialGroup()
									.addGap(12)
									.addComponent(btnCurrentBorderColor)))
							.addGroup(gl_pnlWest.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlWest.createSequentialGroup()
									.addGap(34)
									.addGroup(gl_pnlWest.createParallelGroup(Alignment.BASELINE)
										.addComponent(tglbtnDonut)
										.addComponent(tglbtnHexagon)))
								.addGroup(gl_pnlWest.createSequentialGroup()
									.addGap(18)
									.addComponent(btnCurrentInnerColor)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnLoadNext))))
						.addGroup(gl_pnlWest.createSequentialGroup()
							.addGap(34)
							.addGroup(gl_pnlWest.createParallelGroup(Alignment.BASELINE)
								.addComponent(tglbtnCircle)
								.addComponent(tglbtnRectangle))))
					.addGap(16)
					.addComponent(scrollPaneDraw, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.BASELINE)
						.addComponent(tglbtnSelect)
						.addComponent(btnToFront))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnModify)
						.addComponent(btnToBack))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDelete)
						.addComponent(btnBringToFront))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlWest.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnBringToBack)
						.addComponent(btnUndo))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRedo)
					.addContainerGap())
		);
		
		textArea = new JTextArea();
		scrollPaneDraw.setViewportView(textArea);
		pnlWest.setLayout(gl_pnlWest);
		GroupLayout gl_pnlNorth = new GroupLayout(pnlNorth);
		gl_pnlNorth.setHorizontalGroup(
			gl_pnlNorth.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlNorth.createSequentialGroup()
					.addGap(362)
					.addComponent(lblDrawing))
		);
		gl_pnlNorth.setVerticalGroup(
			gl_pnlNorth.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlNorth.createSequentialGroup()
					.addGap(5)
					.addComponent(lblDrawing))
		);
		pnlNorth.setLayout(gl_pnlNorth);
		contentPane.setLayout(gl_contentPane);
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.mouseClicked(arg0);
			}
		});	
	}


	public DrawingView getView() {
		return this.view;
	}
	
	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	
	public JButton getBtnCurrentBorderColor() {
		return btnCurrentBorderColor;
	}

	public void setBtnCurrentBorderColor(JButton btnCurrentBorderColor) {
		this.btnCurrentBorderColor = btnCurrentBorderColor;
	}

	public JButton getBtnCurrentInnerColor() {
		return btnCurrentInnerColor;
	}

	public void setBtnCurrentInnerColor(JButton btnCurrentInnerColor) {
		this.btnCurrentInnerColor = btnCurrentInnerColor;
	}
	
	public Color getCurrentBorderColor() {
		return currentBorderColor;
	}

	public void setCurrentBorderColor(Color currentBorderColor) {
		this.currentBorderColor = currentBorderColor;
	}

	public Color getCurrentInnerColor() {
		return currentInnerColor;
	}

	public void setCurrentInnerColor(Color currentInnerColor) {
		this.currentInnerColor = currentInnerColor;
	}

	public boolean isCurrentBorderColorConfirmation() {
		return isCurrentBorderColorConfirmation;
	}

	public void setCurrentBorderColorConfirmation(boolean isCurrentBorderColorConfirmation) {
		this.isCurrentBorderColorConfirmation = isCurrentBorderColorConfirmation;
	}

	public boolean isCurrentInnerColorConfirmation() {
		return isCurrentInnerColorConfirmation;
	}

	public void setCurrentInnerColorConfirmation(boolean isCurrentInnerColorConfirmation) {
		this.isCurrentInnerColorConfirmation = isCurrentInnerColorConfirmation;
	} 

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(JButton btnDelete) {
		this.btnDelete = btnDelete;
	}

	public JButton getBtnModify() {
		return btnModify;
	}

	public void setBtnModify(JButton btnModify) {
		this.btnModify = btnModify;
	}

	public JToggleButton getTglbtnSelect() {
		return tglbtnSelect;
	}

	public void setTglbtnSelect(JToggleButton tglbtnSelect) {
		this.tglbtnSelect = tglbtnSelect;
	}
	
	public int getSelect() {
		return select;
	}

	public void setSelect(int select) {
		this.select = select;
	}

	
	public JButton getBtnUndo() {
		return btnUndo;
	}

	public void setBtnUndo(JButton btnUndo) {
		this.btnUndo = btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public void setBtnRedo(JButton btnRedo) {
		this.btnRedo = btnRedo;
	}
	
	public JButton getBtnToFront() {
		return btnToFront;
	}


	public void setBtnToFront(JButton btnToFront) {
		this.btnToFront = btnToFront;
	}



	public JButton getBtnToBack() {
		return btnToBack;
	}



	public void setBtnToBack(JButton btnToBack) {
		this.btnToBack = btnToBack;
	}



	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}



	public void setBtnBringToFront(JButton btnBringToFront) {
		this.btnBringToFront = btnBringToFront;
	}



	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}



	public void setBtnBringToBack(JButton btnBringToBack) {
		this.btnBringToBack = btnBringToBack;
	}
	
	
	public JTextArea getTextArea() {
		return textArea;
	}



	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	public JButton getBtnLoadNext() {
		return btnLoadNext;
	}
}
