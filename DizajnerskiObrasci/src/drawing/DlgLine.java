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
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.JTextField;

public class DlgLine extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Color innerColor = Color.BLACK;
    private boolean innerColorConfirmation;
    private boolean confirm;

	private JTextField txtCoordinateXstartPoint;
    private JTextField txtCoordinateYstartPoint;
    private JTextField txtCoordinateXendPoint;
    private JTextField txtCoordinateYendPoint;
    
	private JButton btnInnerColor;
	
	
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgLine dialog = new DlgLine();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgLine() {
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			btnInnerColor = new JButton("Color");
			btnInnerColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					innerColor = JColorChooser.showDialog(null, "Chose a color.", innerColor);
					btnInnerColor.setBackground(innerColor);
					innerColorConfirmation = true;
				}
			});
			{
				JLabel lblLine = new JLabel("Start point coordinate X:");
				GridBagConstraints gbc_lblLine = new GridBagConstraints();
				gbc_lblLine.insets = new Insets(0, 0, 5, 5);
				gbc_lblLine.gridx = 3;
				gbc_lblLine.gridy = 1;
				contentPanel.add(lblLine, gbc_lblLine);
			}
			{
				txtCoordinateXstartPoint = new JTextField();
				GridBagConstraints gbc_txtCoordinateXstartPoint = new GridBagConstraints();
				gbc_txtCoordinateXstartPoint.insets = new Insets(0, 0, 5, 0);
				gbc_txtCoordinateXstartPoint.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtCoordinateXstartPoint.gridx = 8;
				gbc_txtCoordinateXstartPoint.gridy = 1;
				contentPanel.add(txtCoordinateXstartPoint, gbc_txtCoordinateXstartPoint);
				txtCoordinateXstartPoint.setColumns(10);
			}
			{
				JLabel lblLine1 = new JLabel("Start point coordinate Y:");
				GridBagConstraints gbc_lblLine1 = new GridBagConstraints();
				gbc_lblLine1.insets = new Insets(0, 0, 5, 5);
				gbc_lblLine1.gridx = 3;
				gbc_lblLine1.gridy = 2;
				contentPanel.add(lblLine1, gbc_lblLine1);
			}
			{
				txtCoordinateYstartPoint = new JTextField();
				GridBagConstraints gbc_txtCoordinateYstartPoint = new GridBagConstraints();
				gbc_txtCoordinateYstartPoint.insets = new Insets(0, 0, 5, 0);
				gbc_txtCoordinateYstartPoint.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtCoordinateYstartPoint.gridx = 8;
				gbc_txtCoordinateYstartPoint.gridy = 2;
				contentPanel.add(txtCoordinateYstartPoint, gbc_txtCoordinateYstartPoint);
				txtCoordinateYstartPoint.setColumns(10);
			}
			{
				JLabel lblLine2 = new JLabel("End point coordinate X:");
				GridBagConstraints gbc_lblLine2 = new GridBagConstraints();
				gbc_lblLine2.insets = new Insets(0, 0, 5, 5);
				gbc_lblLine2.gridx = 3;
				gbc_lblLine2.gridy = 3;
				contentPanel.add(lblLine2, gbc_lblLine2);
			}
			{
				txtCoordinateXendPoint = new JTextField();
				GridBagConstraints gbc_txtCoordinateXendPoint = new GridBagConstraints();
				gbc_txtCoordinateXendPoint.insets = new Insets(0, 0, 5, 0);
				gbc_txtCoordinateXendPoint.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtCoordinateXendPoint.gridx = 8;
				gbc_txtCoordinateXendPoint.gridy = 3;
				contentPanel.add(txtCoordinateXendPoint, gbc_txtCoordinateXendPoint);
				txtCoordinateXendPoint.setColumns(10);
			}
			{
				JLabel lblLine3 = new JLabel("End point coordinate Y:");
				GridBagConstraints gbc_lblLine3 = new GridBagConstraints();
				gbc_lblLine3.insets = new Insets(0, 0, 5, 5);
				gbc_lblLine3.gridx = 3;
				gbc_lblLine3.gridy = 4;
				contentPanel.add(lblLine3, gbc_lblLine3);
			}
			{
				txtCoordinateYendPoint = new JTextField();
				GridBagConstraints gbc_txtCoordinateYendPoint = new GridBagConstraints();
				gbc_txtCoordinateYendPoint.insets = new Insets(0, 0, 5, 0);
				gbc_txtCoordinateYendPoint.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtCoordinateYendPoint.gridx = 8;
				gbc_txtCoordinateYendPoint.gridy = 4;
				contentPanel.add(txtCoordinateYendPoint, gbc_txtCoordinateYendPoint);
				txtCoordinateYendPoint.setColumns(10);
			}
			GridBagConstraints gbc_btnInnerColor = new GridBagConstraints();
			gbc_btnInnerColor.insets = new Insets(0, 0, 0, 5);
			gbc_btnInnerColor.gridx = 3;
			gbc_btnInnerColor.gridy = 5;
			contentPanel.add(btnInnerColor, gbc_btnInnerColor);
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
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	
	
	
	public JTextField getTxtCoordinateXstartPoint() {
		return txtCoordinateXstartPoint;
	}

	public void setTxtCoordinateXstartPoint(JTextField txtCoordinateXstartPoint) {
		this.txtCoordinateXstartPoint = txtCoordinateXstartPoint;
	}

	public JTextField getTxtCoordinateYstartPoint() {
		return txtCoordinateYstartPoint;
	}

	public void setTxtCoordinateYstartPoint(JTextField txtCoordinateYstartPoint) {
		this.txtCoordinateYstartPoint = txtCoordinateYstartPoint;
	}

	public JTextField getTxtCoordinateXendPoint() {
		return txtCoordinateXendPoint;
	}

	public void setTxtCoordinateXendPoint(JTextField txtCoordinateXendPoint) {
		this.txtCoordinateXendPoint = txtCoordinateXendPoint;
	}

	public JTextField getTxtCoordinateYendPoint() {
		return txtCoordinateYendPoint;
	}

	public void setTxtCoordinateYendPoint(JTextField txtCoordinateYendPoint) {
		this.txtCoordinateYendPoint = txtCoordinateYendPoint;
	}

	public boolean isInnerColorConfirmation() {
		return innerColorConfirmation;
	}

	public void setInnerColorConfirmation(boolean innerColorConfirmation) {
		this.innerColorConfirmation = innerColorConfirmation;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	
	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
	}
	
	
	public boolean isConfirm() {
			return confirm;
		}

	public void setConfirm(boolean confirm) {
			this.confirm = confirm;
		}
	
}
