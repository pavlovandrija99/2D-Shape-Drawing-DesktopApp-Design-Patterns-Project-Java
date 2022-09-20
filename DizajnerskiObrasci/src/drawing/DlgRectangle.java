package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgRectangle extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtWidth;
	private JTextField txtHeight;
	private boolean confirm;
	

	private Color innerColor = Color.WHITE;
	private Color borderColor = Color.BLACK;
	private boolean innerColorConfirmation;
	private boolean borderColorConfirmation;
	private JTextField txtCoordinateXupperLeft;
	private JTextField txtCoordinateYupperLeft;
	
	private JButton btnRectangleInnerColor;
	private JButton btnRectangleBorderColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgRectangle dialog = new DlgRectangle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgRectangle() {
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblCoordinateXupperLeft = new JLabel("Coordinate X:");
			GridBagConstraints gbc_lblCoordinateXupperLeft = new GridBagConstraints();
			gbc_lblCoordinateXupperLeft.insets = new Insets(0, 0, 5, 5);
			gbc_lblCoordinateXupperLeft.gridx = 2;
			gbc_lblCoordinateXupperLeft.gridy = 0;
			contentPanel.add(lblCoordinateXupperLeft, gbc_lblCoordinateXupperLeft);
		}
		{
			txtCoordinateXupperLeft = new JTextField();
			GridBagConstraints gbc_txtCoordinateXupperLeft = new GridBagConstraints();
			gbc_txtCoordinateXupperLeft.gridwidth = 3;
			gbc_txtCoordinateXupperLeft.insets = new Insets(0, 0, 5, 0);
			gbc_txtCoordinateXupperLeft.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCoordinateXupperLeft.gridx = 7;
			gbc_txtCoordinateXupperLeft.gridy = 0;
			contentPanel.add(txtCoordinateXupperLeft, gbc_txtCoordinateXupperLeft);
			txtCoordinateXupperLeft.setColumns(10);
		}
		{
			JLabel lblCoordinateYupperLeft = new JLabel("Coordinate Y:");
			GridBagConstraints gbc_lblCoordinateYupperLeft = new GridBagConstraints();
			gbc_lblCoordinateYupperLeft.insets = new Insets(0, 0, 5, 5);
			gbc_lblCoordinateYupperLeft.gridx = 2;
			gbc_lblCoordinateYupperLeft.gridy = 1;
			contentPanel.add(lblCoordinateYupperLeft, gbc_lblCoordinateYupperLeft);
		}
		{
			txtCoordinateYupperLeft = new JTextField();
			GridBagConstraints gbc_txtCoordinateYupperLeft = new GridBagConstraints();
			gbc_txtCoordinateYupperLeft.gridwidth = 3;
			gbc_txtCoordinateYupperLeft.insets = new Insets(0, 0, 5, 5);
			gbc_txtCoordinateYupperLeft.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtCoordinateYupperLeft.gridx = 7;
			gbc_txtCoordinateYupperLeft.gridy = 1;
			contentPanel.add(txtCoordinateYupperLeft, gbc_txtCoordinateYupperLeft);
			txtCoordinateYupperLeft.setColumns(10);
		}
		{
			JLabel lblWidth = new JLabel("Width:");
			GridBagConstraints gbc_lblWidth = new GridBagConstraints();
			gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
			gbc_lblWidth.gridx = 2;
			gbc_lblWidth.gridy = 2;
			contentPanel.add(lblWidth, gbc_lblWidth);
		}
		{
			txtWidth = new JTextField();
			txtWidth.setText("");
			GridBagConstraints gbc_txtWidth = new GridBagConstraints();
			gbc_txtWidth.insets = new Insets(0, 0, 5, 0);
			gbc_txtWidth.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtWidth.gridx = 9;
			gbc_txtWidth.gridy = 2;
			contentPanel.add(txtWidth, gbc_txtWidth);
			txtWidth.setColumns(10);
		}
		{
			JLabel lblHeight = new JLabel("Height:");
			GridBagConstraints gbc_lblHeight = new GridBagConstraints();
			gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
			gbc_lblHeight.gridx = 2;
			gbc_lblHeight.gridy = 3;
			contentPanel.add(lblHeight, gbc_lblHeight);
		}
		{
			txtHeight = new JTextField();
			GridBagConstraints gbc_txtHeight = new GridBagConstraints();
			gbc_txtHeight.insets = new Insets(0, 0, 5, 0);
			gbc_txtHeight.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtHeight.gridx = 9;
			gbc_txtHeight.gridy = 3;
			contentPanel.add(txtHeight, gbc_txtHeight);
			txtHeight.setColumns(10);
		}
		{
			btnRectangleInnerColor = new JButton("Inner Color");
			btnRectangleInnerColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					innerColor = JColorChooser.showDialog(null, "Chose a color.", innerColor);
					btnRectangleInnerColor.setBackground(innerColor);
					innerColorConfirmation = true;
				}
			});
			GridBagConstraints gbc_btnRectangleInnerColor = new GridBagConstraints();
			gbc_btnRectangleInnerColor.insets = new Insets(0, 0, 0, 5);
			gbc_btnRectangleInnerColor.gridx = 2;
			gbc_btnRectangleInnerColor.gridy = 5;
			contentPanel.add(btnRectangleInnerColor, gbc_btnRectangleInnerColor);
		}
		{
			btnRectangleBorderColor = new JButton("Border Color");
			btnRectangleBorderColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borderColor = JColorChooser.showDialog(null, "Chose a color.", borderColor);
					btnRectangleBorderColor.setBackground(borderColor);
					borderColorConfirmation = true;
				}
			});
			GridBagConstraints gbc_btnRectangleBorderColor = new GridBagConstraints();
			gbc_btnRectangleBorderColor.insets = new Insets(0, 0, 0, 5);
			gbc_btnRectangleBorderColor.gridx = 4;
			gbc_btnRectangleBorderColor.gridy = 5;
			contentPanel.add(btnRectangleBorderColor, gbc_btnRectangleBorderColor);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						confirm = true;
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	
	
	public JButton getBtnRectangleInnerColor() {
		return btnRectangleInnerColor;
	}

	public void setBtnRectangleInnerColor(JButton btnRectangleInnerColor) {
		this.btnRectangleInnerColor = btnRectangleInnerColor;
	}

	public JButton getBtnRectangleBorderColor() {
		return btnRectangleBorderColor;
	}

	public void setBtnRectangleBorderColor(JButton btnRectangleBorderColor) {
		this.btnRectangleBorderColor = btnRectangleBorderColor;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public JTextField getTxtCoordinateXupperLeft() {
		return txtCoordinateXupperLeft;
	}

	public void setTxtCoordinateXupperLeft(JTextField txtCoordinateXupperLeft) {
		this.txtCoordinateXupperLeft = txtCoordinateXupperLeft;
	}

	public JTextField getTxtCoordinateYupperLeft() {
		return txtCoordinateYupperLeft;
	}

	public void setTxtCoordinateYupperLeft(JTextField txtCoordinateYupperLeft) {
		this.txtCoordinateYupperLeft = txtCoordinateYupperLeft;
	}

	public JTextField getTxtWidth() {
		return txtWidth;
	}

	public void setTxtWidth(JTextField txtWidth) {
		this.txtWidth = txtWidth;
	}

	public JTextField getTxtHeight() {
		return txtHeight;
	}

	public void setTxtHeight(JTextField txtHeight) {
		this.txtHeight = txtHeight;
	}

	public boolean isInnerColorConfirmation() {
		return innerColorConfirmation;
	}

	public void setInnerColorConfirmation(boolean innerColorConfirmation) {
		this.innerColorConfirmation = innerColorConfirmation;
	}

	public boolean isBorderColorConfirmation() {
		return borderColorConfirmation;
	}

	public void setBorderColorConfirmation(boolean borderColorConfirmation) {
		this.borderColorConfirmation = borderColorConfirmation;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}
	
}
