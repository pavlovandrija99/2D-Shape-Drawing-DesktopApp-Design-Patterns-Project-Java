package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import adapter.HexagonAdapter;
import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.DeselectShapeCmd;
import command.RemoveShapeCmd;
import command.SelectShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import drawing.DlgCircle;
import drawing.DlgDelete;
import drawing.DlgDonut;
import drawing.DlgHexagon;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import observer.ButtonsObserver;
import observer.ObservableButtons;
import shapes.Circle;
import shapes.Donut;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import shapes.Shape;
import strategy.SaveLog;
import strategy.SaveManager;
import strategy.SavePainting;

public class DrawingController {

	private DrawingModel model;
	private DrawingFrame frame;

	private Point startPoint = null;
	private Color circleInnerColor, circleBorderColor, pointColor, rectangleInnerColor, rectangleBorderColor,
			lineInnerColor, donutInnerColor, donutBorderColor, hexagonInnerColor, hexagonBorderColor;

	private Stack<Command> undoStack = new Stack<Command>();
	private Stack<Command> redoStack = new Stack<Command>();

	private Command command;

	private int undoCounter = 0;
	private int redoCounter = 0;

	private ArrayList<Shape> selectedShapesList = new ArrayList<>(0);
	private ArrayList<Shape> undoShapesList = new ArrayList<>(0);
	private ArrayList<Shape> redoShapesList = new ArrayList<>(0);

	private ArrayList<String> logStringList = new ArrayList<>(0);
	private int logStringListCounter = 0;

	private ButtonsObserver buttonsObserver;

	private ObservableButtons observableButtons = new ObservableButtons();

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		this.buttonsObserver = new ButtonsObserver(this.frame);
		this.observableButtons.addPropertyChangeListener(this.buttonsObserver);
	}

	public void mouseClicked(MouseEvent e) {

		if (frame.getSelect() == 1) {
			drawPoint(e);
			enableDisableButtons();
		} else if (frame.getSelect() == 2) {
			drawLine(e);
			enableDisableButtons();
		} else if (frame.getSelect() == 3) {
			drawCircle(e);
			enableDisableButtons();
		} else if (frame.getSelect() == 4) {
			drawRectangle(e);
			enableDisableButtons();
		} else if (frame.getSelect() == 5) {
			drawDonut(e);
			enableDisableButtons();
		} else if (frame.getSelect() == 6) {
			drawHexagon(e);
			enableDisableButtons();
		} else if (frame.getSelect() == 7) {
			selectShape(e);
		}
	}

	// --------------DRAW
	// METODE----------------------------------------------------------------------------

	public void drawPoint(MouseEvent e) {

		DlgPoint dlgPoint = new DlgPoint();

		dlgPoint.getTxtCoordinateX().setText(Integer.toString(e.getX()));
		dlgPoint.getTxtCoordinateY().setText(Integer.toString(e.getY()));

		dlgPoint.getTxtCoordinateX().setEnabled(false);
		dlgPoint.getTxtCoordinateY().setEnabled(false);

		dlgPoint.getBtnPointColor().setBackground(frame.getCurrentBorderColor());

		dlgPoint.setVisible(true);

		if (dlgPoint.isConfirm()) {

			Point shape = new Point(e.getX(), e.getY(), false, Color.BLACK);

			if (dlgPoint.isColorConfirmation()) {
				pointColor = dlgPoint.getColor();
				shape.setColor(pointColor);

			} else {
				pointColor = frame.getCurrentBorderColor();
				shape.setColor(pointColor);
			}

			command = new AddShapeCmd(model, shape);
			command.execute();

			this.frame.getTextArea().append(command.toString());

			undoCounter++;

			undoStack.push(command);
			redoStack.clear();

			checkUndoRedoButtons();
			enableDisableButtons();

			frame.repaint();
		}
	}

	public void drawLine(MouseEvent e) {

		if (startPoint == null) {

			startPoint = new Point(e.getX(), e.getY());

		} else {

			DlgLine dlgLine = new DlgLine();

			dlgLine.getTxtCoordinateXstartPoint().setText(Integer.toString(startPoint.getX()));
			dlgLine.getTxtCoordinateYstartPoint().setText(Integer.toString(startPoint.getY()));
			dlgLine.getTxtCoordinateXendPoint().setText(Integer.toString(e.getX()));
			dlgLine.getTxtCoordinateYendPoint().setText(Integer.toString(e.getY()));

			dlgLine.getTxtCoordinateXstartPoint().setEnabled(false);
			dlgLine.getTxtCoordinateYstartPoint().setEnabled(false);
			dlgLine.getTxtCoordinateXendPoint().setEnabled(false);
			dlgLine.getTxtCoordinateYendPoint().setEnabled(false);

			dlgLine.getBtnInnerColor().setBackground(frame.getCurrentBorderColor());

			dlgLine.setVisible(true);

			if (dlgLine.isConfirm()) {

				Line shape = new Line(startPoint, new Point(e.getX(), e.getY()), false, Color.BLACK);

				if (dlgLine.isInnerColorConfirmation()) {
					shape.setColor(dlgLine.getInnerColor());

				} else {
					shape.setColor(frame.getCurrentBorderColor());
				}

				command = new AddShapeCmd(model, shape);
				command.execute();

				this.frame.getTextArea().append(command.toString());

				undoCounter++;

				undoStack.push(command);
				redoStack.clear();

				checkUndoRedoButtons();
				enableDisableButtons();

				frame.repaint();
			}

			startPoint = null;
		}
	}

	public void drawCircle(MouseEvent e) {

		Point p = new Point(e.getX(), e.getY());

		DlgCircle dlgCircle = new DlgCircle();
		dlgCircle.getTxtCoordinateXcir().setText(Integer.toString(p.getX()));
		dlgCircle.getTxtCoordinateYcir().setText(Integer.toString(p.getY()));

		dlgCircle.getTxtCoordinateXcir().setEnabled(false);
		dlgCircle.getTxtCoordinateYcir().setEnabled(false);

		dlgCircle.getBtnInnerColor().setBackground(frame.getCurrentInnerColor());
		dlgCircle.getBtnColor().setBackground(frame.getCurrentBorderColor());

		dlgCircle.setVisible(true);

		if (dlgCircle.isConfirm()) {

			try {

				int radius = Integer.parseInt(dlgCircle.getTxtRadius().getText().toString());

				if (radius > 0) {

					Circle shape = new Circle(p, radius, false, Color.BLACK, Color.WHITE);

					if (dlgCircle.isInnerColorConfirmation()) {

						circleInnerColor = dlgCircle.getInnerColor();
						shape.setInnerColor(circleInnerColor);

					} else {
						circleInnerColor = frame.getCurrentInnerColor();
						shape.setInnerColor(circleInnerColor);
					}

					if (dlgCircle.isBorderColorConfirmation()) {

						circleBorderColor = dlgCircle.getBorderColor();
						shape.setColor(circleBorderColor);

					} else {
						circleBorderColor = frame.getCurrentBorderColor();
						shape.setColor(circleBorderColor);
					}

					command = new AddShapeCmd(model, shape);
					command.execute();

					this.frame.getTextArea().append(command.toString());

					undoCounter++;

					undoStack.push(command);
					redoStack.clear();

					checkUndoRedoButtons();
					enableDisableButtons();

					frame.repaint();

				} else {
					JOptionPane.showMessageDialog(null, "Radius must be greater than 0!");
				}

			} catch (Exception NumberFormatException) {
				JOptionPane.showMessageDialog(null, "Invalid values, try again!");
			}
		}
	}

	public void drawRectangle(MouseEvent e) {

		Point upperLeftPoint = new Point(e.getX(), e.getY());

		DlgRectangle dlgRectangle = new DlgRectangle();

		dlgRectangle.getTxtCoordinateXupperLeft().setText(Integer.toString(upperLeftPoint.getX()));
		dlgRectangle.getTxtCoordinateYupperLeft().setText(Integer.toString(upperLeftPoint.getY()));

		dlgRectangle.getTxtCoordinateXupperLeft().setEnabled(false);
		dlgRectangle.getTxtCoordinateYupperLeft().setEnabled(false);

		dlgRectangle.getBtnRectangleInnerColor().setBackground(frame.getCurrentInnerColor());
		dlgRectangle.getBtnRectangleBorderColor().setBackground(frame.getCurrentBorderColor());

		dlgRectangle.setVisible(true);

		if (dlgRectangle.isConfirm()) {

			try {

				int width = Integer.parseInt(dlgRectangle.getTxtWidth().getText().toString());
				int height = Integer.parseInt(dlgRectangle.getTxtHeight().getText().toString());

				if (width > 0 && height > 0) {
					Rectangle shape = new Rectangle(upperLeftPoint, width, height, false, Color.BLACK, Color.WHITE);

					if (dlgRectangle.isInnerColorConfirmation()) {
						rectangleInnerColor = dlgRectangle.getInnerColor();
						shape.setInnerColor(rectangleInnerColor);

					} else {
						rectangleInnerColor = frame.getCurrentInnerColor();
						shape.setInnerColor(rectangleInnerColor);
					}

					if (dlgRectangle.isBorderColorConfirmation()) {
						rectangleBorderColor = dlgRectangle.getBorderColor();
						shape.setColor(rectangleBorderColor);

					} else {
						rectangleBorderColor = frame.getCurrentBorderColor();
						shape.setColor(rectangleBorderColor);
					}

					command = new AddShapeCmd(model, shape);
					command.execute();

					this.frame.getTextArea().append(command.toString());

					undoCounter++;

					undoStack.push(command);
					redoStack.clear();

					checkUndoRedoButtons();
					enableDisableButtons();

					frame.repaint();

				} else {
					JOptionPane.showMessageDialog(null, "Widht and height must be greater than 0!");
				}

			} catch (Exception NumberFormatException) {
				JOptionPane.showMessageDialog(null, "Invalid values, try again!");
			}

		}
	}

	public void drawDonut(MouseEvent e) {

		Point center = new Point(e.getX(), e.getY());

		DlgDonut dlgDonut = new DlgDonut();

		dlgDonut.getTxtCoordinateXdon().setText(Integer.toString(center.getX()));
		dlgDonut.getTxtCoordinateYdon().setText(Integer.toString(center.getY()));

		dlgDonut.getTxtCoordinateXdon().setEnabled(false);
		dlgDonut.getTxtCoordinateYdon().setEnabled(false);

		dlgDonut.getBtnBorderColor().setBackground(frame.getCurrentBorderColor());
		dlgDonut.getBtnInnerColor().setBackground(frame.getCurrentInnerColor());

		dlgDonut.setVisible(true);

		if (dlgDonut.confirm) {

			try {

				int radius = Integer.parseInt(dlgDonut.getTxtRadius().getText().toString());
				int innerRadius = Integer.parseInt(dlgDonut.getTxtInnerRadius().getText().toString());

				if (radius > 0 && innerRadius > 0) {
					if (radius > innerRadius) {
						Donut shape = new Donut(center, radius, innerRadius, false, Color.BLACK, Color.WHITE);

						if (dlgDonut.isInnerColorConfirmation()) {
							donutInnerColor = dlgDonut.getInnerColor();
							shape.setInnerColor(donutInnerColor);

						} else {
							donutInnerColor = frame.getCurrentInnerColor();
							shape.setInnerColor(donutInnerColor);
						}

						if (dlgDonut.isBorderColorConfirmation()) {
							donutBorderColor = dlgDonut.getBorderColor();
							shape.setColor(donutBorderColor);

						} else {
							donutBorderColor = frame.getCurrentBorderColor();
							shape.setColor(donutBorderColor);
						}

						command = new AddShapeCmd(model, shape);
						command.execute();

						this.frame.getTextArea().append(command.toString());

						undoCounter++;

						undoStack.push(command);
						redoStack.clear();

						checkUndoRedoButtons();
						enableDisableButtons();

						frame.repaint();

					} else {
						JOptionPane.showMessageDialog(null, "Radius must be greater than inner radius!");
					}

				} else {
					JOptionPane.showMessageDialog(null, "Radius and inner radius must be greater than 0!");
				}

			} catch (Exception NumberFormatException) {
				JOptionPane.showMessageDialog(null, "Invalid values, try again!");
			}
		}

	}

	public void drawHexagon(MouseEvent e) {

		Point p = new Point(e.getX(), e.getY());

		DlgHexagon dlgHexagon = new DlgHexagon();
		dlgHexagon.getTxtCoordinateX().setText(Integer.toString(p.getX()));
		dlgHexagon.getTxtCoordinateY().setText(Integer.toString(p.getY()));

		dlgHexagon.getTxtCoordinateX().setEnabled(false);
		dlgHexagon.getTxtCoordinateY().setEnabled(false);

		dlgHexagon.getBtnHexagonInnerColor().setBackground(frame.getCurrentInnerColor());
		dlgHexagon.getBtnHexagonBorderColor().setBackground(frame.getCurrentBorderColor());

		dlgHexagon.setVisible(true);

		if (dlgHexagon.isConfirm()) {

			try {

				int radius = Integer.parseInt(dlgHexagon.getTxtHexagonRadius().getText().toString());

				if (radius > 0) {

					HexagonAdapter hexagon = new HexagonAdapter(p, radius, false, Color.BLACK, Color.WHITE);

					if (dlgHexagon.isInnerColorConfirmation()) {
						hexagonInnerColor = dlgHexagon.getInnerColor();
						hexagon.setHexagonInnerColor(hexagonInnerColor);

					} else {
						hexagonInnerColor = frame.getCurrentInnerColor();
						hexagon.setHexagonInnerColor(hexagonInnerColor);
					}

					if (dlgHexagon.isBorderColorConfirmation()) {
						hexagonBorderColor = dlgHexagon.getBorderColor();
						hexagon.setHexagonBorderColor(hexagonBorderColor);

					} else {
						hexagonBorderColor = frame.getCurrentBorderColor();
						hexagon.setHexagonBorderColor(hexagonBorderColor);
					}

					command = new AddShapeCmd(model, hexagon);
					command.execute();

					this.frame.getTextArea().append(command.toString());

					undoCounter++;

					undoStack.push(command);
					redoStack.clear();

					checkUndoRedoButtons();
					enableDisableButtons();

					frame.repaint();

				} else {
					JOptionPane.showMessageDialog(null, "Radius must be greater than 0!");
				}

			} catch (Exception NumberFormatException) {
				JOptionPane.showMessageDialog(null, "Invalid values, try again!");
			}
		}
	}

//----------------------------------MODIFY------------------------------------------------------------------------
	public void modifyShape() {
		for (int i = 0; i < model.getShapeList().size(); i++) {

			if (model.getShape(i) instanceof Point) {

				if (model.getShape(i).isSelected()) {

					DlgPoint dlgPoint = new DlgPoint();

					Point p = (Point) model.getShape(i);

					dlgPoint.getTxtCoordinateX().setText(Integer.toString(p.getX()));
					dlgPoint.getTxtCoordinateY().setText(Integer.toString(p.getY()));

					dlgPoint.getBtnPointColor().setBackground(p.getColor());

					dlgPoint.setVisible(true);

					if (dlgPoint.isConfirm()) {

						try {

							int x = Integer.parseInt(dlgPoint.getTxtCoordinateX().getText());
							int y = Integer.parseInt(dlgPoint.getTxtCoordinateY().getText());

							if (x > 0 && y > 0) {

								Point point = new Point(x, y, true);

								pointColor = p.getColor();

								if (dlgPoint.isColorConfirmation()) {

									point.setColor(dlgPoint.getColor());

								} else {

									point.setColor(pointColor);
								}

								command = new UpdatePointCmd(p, point);
								command.execute();

								this.frame.getTextArea().append(command.toString());

								undoStack.push(command);
								redoStack.clear();

								undoCounter++;

								checkUndoRedoButtons();
								enableDisableButtons();

								frame.repaint();

							} else {
								JOptionPane.showMessageDialog(null, "X and Y must be greater than 0!");
							}
						}

						catch (Exception NumberFormatException) {
							JOptionPane.showMessageDialog(null, "Invalid values, try again!");
						}
					}
				}
			}

			else if (model.getShape(i) instanceof Line) {

				if (model.getShape(i).isSelected()) {

					DlgLine dlgLine = new DlgLine();

					Line l = (Line) model.getShape(i);

					dlgLine.getTxtCoordinateXstartPoint().setText(Integer.toString(l.getStartPoint().getX()));
					dlgLine.getTxtCoordinateYstartPoint().setText(Integer.toString(l.getStartPoint().getY()));
					dlgLine.getTxtCoordinateXendPoint().setText(Integer.toString(l.getEndPoint().getX()));
					dlgLine.getTxtCoordinateYendPoint().setText(Integer.toString(l.getEndPoint().getY()));

					dlgLine.getBtnInnerColor().setBackground(l.getColor());

					dlgLine.setVisible(true);

					if (dlgLine.isConfirm()) {

						try {

							int startX = Integer.parseInt(dlgLine.getTxtCoordinateXstartPoint().getText());
							int startY = Integer.parseInt(dlgLine.getTxtCoordinateYstartPoint().getText());
							int endX = Integer.parseInt(dlgLine.getTxtCoordinateXendPoint().getText());
							int endY = Integer.parseInt(dlgLine.getTxtCoordinateYendPoint().getText());

							if (startX > 0 && startY > 0 && endX > 0 && endY > 0) {

								Line line = new Line(new Point(startX, startY), new Point(endX, endY), true);

								lineInnerColor = l.getColor();

								if (dlgLine.isInnerColorConfirmation()) {

									line.setColor(dlgLine.getInnerColor());

								} else {
									line.setColor(lineInnerColor);
								}

								command = new UpdateLineCmd(l, line);
								command.execute();

								this.frame.getTextArea().append(command.toString());

								undoCounter++;

								undoStack.push(command);
								redoStack.clear();

								checkUndoRedoButtons();
								enableDisableButtons();

								frame.repaint();

							} else {
								JOptionPane.showMessageDialog(null,
										"Start point and end point coordinates must be greater than 0!");
							}
						} catch (Exception NumberFormatException) {
							JOptionPane.showMessageDialog(null, "Invalid values, try again!");
						}

					}
				}
			}

			else if (model.getShape(i) instanceof Rectangle) {

				if (model.getShape(i).isSelected()) {

					DlgRectangle dlgRectangle = new DlgRectangle();

					Rectangle r = (Rectangle) model.getShape(i);

					dlgRectangle.getTxtHeight().setText(Integer.toString(r.getHeight()));
					dlgRectangle.getTxtWidth().setText(Integer.toString(r.getWidth()));
					dlgRectangle.getTxtCoordinateXupperLeft().setText(Integer.toString(r.getUpperLeftPoint().getX()));
					dlgRectangle.getTxtCoordinateYupperLeft().setText(Integer.toString(r.getUpperLeftPoint().getY()));

					dlgRectangle.getBtnRectangleInnerColor().setBackground(r.getInnerColor());
					dlgRectangle.getBtnRectangleBorderColor().setBackground(r.getColor());

					dlgRectangle.setVisible(true);

					if (dlgRectangle.isConfirm()) {

						try {

							int x = Integer.parseInt(dlgRectangle.getTxtCoordinateXupperLeft().getText());
							int y = Integer.parseInt(dlgRectangle.getTxtCoordinateYupperLeft().getText());
							int width = Integer.parseInt(dlgRectangle.getTxtWidth().getText());
							int height = Integer.parseInt(dlgRectangle.getTxtHeight().getText());

							if (width > 0 && height > 0) {

								Rectangle rectangle = new Rectangle(new Point(x, y), width, height, true);

								rectangleBorderColor = r.getColor();
								rectangleInnerColor = r.getInnerColor();

								if (dlgRectangle.isBorderColorConfirmation()) {

									rectangle.setColor(dlgRectangle.getBorderColor());

								} else {
									rectangle.setColor(rectangleBorderColor);
								}

								if (dlgRectangle.isInnerColorConfirmation()) {

									rectangle.setInnerColor(dlgRectangle.getInnerColor());
								} else {
									rectangle.setInnerColor(rectangleInnerColor);
								}

								command = new UpdateRectangleCmd(r, rectangle);
								command.execute();

								this.frame.getTextArea().append(command.toString());

								undoCounter++;

								undoStack.push(command);
								redoStack.clear();

								checkUndoRedoButtons();
								enableDisableButtons();

								frame.repaint();

							} else {
								JOptionPane.showMessageDialog(null, "Width and Height must be greater than 0!");
							}

						} catch (Exception NumberFormatException) {
							JOptionPane.showMessageDialog(null, "Invalid values, try again!");
						}

					}

				}
			} else if (model.getShape(i) instanceof HexagonAdapter) {

				if (model.getShape(i).isSelected()) {

					DlgHexagon dlgHexagon = new DlgHexagon();

					HexagonAdapter hexagon = (HexagonAdapter) model.getShape(i);
					;

					dlgHexagon.getTxtCoordinateX().setText(Integer.toString(hexagon.getHexagonCenter().getX()));
					dlgHexagon.getTxtCoordinateY().setText(Integer.toString(hexagon.getHexagonCenter().getY()));
					dlgHexagon.getTxtHexagonRadius().setText(Integer.toString(hexagon.getHexagonRadius()));

					dlgHexagon.getBtnHexagonInnerColor().setBackground(hexagon.getHexagonInnerColor());
					dlgHexagon.getBtnHexagonBorderColor().setBackground(hexagon.getHexagonBorderColor());

					dlgHexagon.setVisible(true);

					if (dlgHexagon.isConfirm()) {

						try {

							int x = Integer.parseInt(dlgHexagon.getTxtCoordinateX().getText());
							int y = Integer.parseInt(dlgHexagon.getTxtCoordinateY().getText());
							int r = Integer.parseInt(dlgHexagon.getTxtHexagonRadius().getText());

							if (r > 0) {

								HexagonAdapter newHexagon = new HexagonAdapter(new Point(x, y), r, true);

								hexagonInnerColor = hexagon.getHexagonInnerColor();
								hexagonBorderColor = hexagon.getHexagonBorderColor();

								if (dlgHexagon.isInnerColorConfirmation()) {

									newHexagon.setHexagonInnerColor(dlgHexagon.getInnerColor());

								} else {
									newHexagon.setHexagonInnerColor(hexagonInnerColor);

								}

								if (dlgHexagon.isBorderColorConfirmation()) {

									newHexagon.setHexagonBorderColor(dlgHexagon.getBorderColor());

								} else {
									newHexagon.setHexagonBorderColor(hexagonBorderColor);

								}

								command = new UpdateHexagonCmd(hexagon, newHexagon);
								command.execute();

								this.frame.getTextArea().append(command.toString());

								undoCounter++;

								undoStack.push(command);
								redoStack.clear();

								checkUndoRedoButtons();
								enableDisableButtons();

								frame.repaint();

							} else {
								JOptionPane.showMessageDialog(null, "Radius must be greater than 0!");
							}

						} catch (Exception NumberFormatException) {
							JOptionPane.showMessageDialog(null, "Invalid values, try again!");
						}
					}
				}

			} else if (model.getShape(i) instanceof Donut) {

				if (model.getShape(i).isSelected()) {

					DlgDonut dlgDonut = new DlgDonut();

					Donut d = (Donut) model.getShape(i);

					dlgDonut.getTxtCoordinateXdon().setText(Integer.toString(d.getCenter().getX()));
					dlgDonut.getTxtCoordinateYdon().setText(Integer.toString(d.getCenter().getY()));
					dlgDonut.getTxtInnerRadius().setText(Integer.toString(d.getInnerRadius()));
					dlgDonut.getTxtRadius().setText(Integer.toString(d.getRadius()));

					dlgDonut.getBtnBorderColor().setBackground(d.getColor());
					dlgDonut.getBtnInnerColor().setBackground(d.getInnerColor());

					dlgDonut.setVisible(true);

					if (dlgDonut.confirm) {

						try {

							int x = Integer.parseInt(dlgDonut.getTxtCoordinateXdon().getText().toString());
							int y = Integer.parseInt(dlgDonut.getTxtCoordinateYdon().getText().toString());
							int r = Integer.parseInt(dlgDonut.getTxtRadius().getText().toString());
							int innerRadius = Integer.parseInt(dlgDonut.getTxtInnerRadius().getText().toString());

							if (r > innerRadius) {

								Donut donut = new Donut(new Point(x, y), r, innerRadius, true);

								donutInnerColor = d.getInnerColor();
								donutBorderColor = d.getColor();

								if (dlgDonut.isInnerColorConfirmation()) {

									donut.setInnerColor(dlgDonut.getInnerColor());

								} else {
									donut.setInnerColor(donutInnerColor);
								}

								if (dlgDonut.isBorderColorConfirmation()) {

									donut.setColor(dlgDonut.getBorderColor());

								} else {
									donut.setColor(donutBorderColor);
								}

								command = new UpdateDonutCmd(d, donut);
								command.execute();

								this.frame.getTextArea().append(command.toString());

								undoCounter++;

								undoStack.push(command);
								redoStack.clear();

								checkUndoRedoButtons();
								enableDisableButtons();

								frame.repaint();

							} else {
								JOptionPane.showMessageDialog(null, "Radius must be greater than inner radius!");
							}

						} catch (Exception NumberFormatException) {
							JOptionPane.showMessageDialog(null, "Invalid values, try again!");
						}

					}

				}
			} else if (model.getShape(i) instanceof Circle) {

				if (model.getShape(i).isSelected()) {

					DlgCircle dlgCircle = new DlgCircle();

					Circle c = (Circle) model.getShape(i);

					dlgCircle.getTxtCoordinateXcir().setText(Integer.toString(c.getCenter().getX()));
					dlgCircle.getTxtCoordinateYcir().setText(Integer.toString(c.getCenter().getY()));
					dlgCircle.getTxtRadius().setText(Integer.toString(c.getRadius()));

					dlgCircle.getBtnInnerColor().setBackground(c.getInnerColor());
					dlgCircle.getBtnColor().setBackground(c.getColor());

					dlgCircle.setVisible(true);

					if (dlgCircle.isConfirm()) {

						try {

							int x = Integer.parseInt(dlgCircle.getTxtCoordinateXcir().getText().toString());
							int y = Integer.parseInt(dlgCircle.getTxtCoordinateYcir().getText().toString());
							int r = Integer.parseInt(dlgCircle.getTxtRadius().getText());

							if (r > 0) {

								Circle circle = new Circle(new Point(x, y), r, true);

								circleInnerColor = c.getInnerColor();
								circleBorderColor = c.getColor();

								if (dlgCircle.isInnerColorConfirmation()) {

									circle.setInnerColor(dlgCircle.getInnerColor());

								} else {
									circle.setInnerColor(circleInnerColor);
								}

								if (dlgCircle.isBorderColorConfirmation()) {

									circle.setColor(dlgCircle.getBorderColor());

								} else {
									circle.setColor(circleBorderColor);
								}

								command = new UpdateCircleCmd(c, circle);
								command.execute();

								this.frame.getTextArea().append(command.toString());

								undoCounter++;

								undoStack.push(command);
								redoStack.clear();

								checkUndoRedoButtons();
								enableDisableButtons();

								frame.repaint();

							} else {
								JOptionPane.showMessageDialog(null, "Radius must be greater than 0!");
							}

						} catch (Exception NumberFormatException) {
							JOptionPane.showMessageDialog(null, "Invalid values, try again!");
						}

					}
				}
			}
		}
	}

// ---------------------------------SELECT------------------------------------------------------
	public void selectShape(MouseEvent e) {

		Shape selectedShape = null;
		Shape shape = null;

		Iterator<Shape> it = this.model.getShapeList().iterator();

		Command command = null;

		while (it.hasNext()) {
			shape = it.next();

			if (shape.contains(e.getX(), e.getY())) {
				selectedShape = shape;
			}

		}

		if (selectedShape != null) {
			if (selectedShape.isSelected()) {
				command = new DeselectShapeCmd(this, selectedShape);
				command.execute();
				redoStack.clear();
				this.frame.getTextArea().append(command.toString());
				undoStack.push(command);
			} else {
				command = new SelectShapeCmd(this, selectedShape);
				command.execute();
				redoStack.clear();
				this.frame.getTextArea().append(command.toString());
				undoStack.push(command);
			}
			undoCounter++;
		}

		checkUndoRedoButtons();
		enableDisableButtons();
		frame.repaint();
	}

	// ----------------------------------DELETE-------------------------------------------------------------------------
	public void deleteShape() {

		Shape shape;
		int selectedShapesListLength = this.selectedShapesList.size();

		DlgDelete dlgDelete = new DlgDelete();
		dlgDelete.setVisible(true);

		if (dlgDelete.isConfirm()) {

			for (int i = 0; i < selectedShapesListLength; i++) {
				shape = this.selectedShapesList.get(0);
				command = new RemoveShapeCmd(this.model, shape, this.model.getShapeList().indexOf(shape));
				command.execute();
				this.frame.getTextArea().append(command.toString());
				this.selectedShapesList.remove(shape);
				this.undoShapesList.add(shape);
				undoStack.push(command);
				undoCounter++;
			}

			redoStack.clear();
			frame.repaint();

			checkUndoRedoButtons();
			enableDisableButtons();
		}
	}
	
	
	public void undo() {
		command = undoStack.peek();
		
		if(command instanceof RemoveShapeCmd) {
			while (command instanceof RemoveShapeCmd) {
				command.unexecute();
				this.redoShapesList.add(this.undoShapesList.get(this.undoShapesList.size() - 1));
				this.selectedShapesList.add(this.undoShapesList.get(this.undoShapesList.size() - 1));
				this.undoShapesList.remove(this.undoShapesList.size() - 1);
				this.frame.getTextArea().append("Undo " + undoStack.peek().toString());
				undoCounter--;
				redoCounter++;
				undoStack.pop();
				redoStack.push(command);
				command = undoStack.peek();
			}
		} else {
			command.unexecute();
			this.frame.getTextArea().append("Undo " + undoStack.peek().toString());
			undoCounter--;
			redoCounter++;
			undoStack.pop();
			redoStack.push(command);
		}
		frame.repaint();
		checkUndoRedoButtons();
		enableDisableButtons();
	}
	
	
	public void redo() {
		command = redoStack.peek();
		
		if(command instanceof RemoveShapeCmd) {
			while(command instanceof RemoveShapeCmd) {
				command.execute();
				this.undoShapesList.add(this.redoShapesList.get(this.redoShapesList.size() - 1));
				this.selectedShapesList.remove(this.redoShapesList.get(this.redoShapesList.size() - 1));
				this.redoShapesList.remove(this.redoShapesList.size() - 1);
				this.frame.getTextArea().append("Redo " + redoStack.peek().toString());
				undoCounter++;
				redoCounter--;
				redoStack.pop();
				undoStack.push(command);
				if(!redoStack.isEmpty()) {
					command = redoStack.peek();
				} else {
					command = null;
				}
				
			}
		} else {
			command.execute();
			this.frame.getTextArea().append("Redo " + redoStack.peek().toString());
			undoCounter++;
			redoCounter--;
			redoStack.pop();
			undoStack.push(command);			
		} 
		frame.repaint();
		checkUndoRedoButtons();
		enableDisableButtons();
	}
	
	/*public void undo() {
		command = undoStack.peek();
		command.unexecute();
		if (command instanceof RemoveShapeCmd) {
			this.redoShapesList.add(this.undoShapesList.get(this.undoShapesList.size() - 1));
			this.selectedShapesList.add(this.undoShapesList.get(this.undoShapesList.size() - 1));
			this.undoShapesList.remove(this.undoShapesList.size() - 1);
		}
		this.frame.getTextArea().append("Undo " + undoStack.peek().toString());
		undoCounter--;
		redoCounter++;
		frame.repaint();
		undoStack.pop();
		redoStack.push(command);
		checkUndoRedoButtons();
		enableDisableButtons();
	} */

	
	/*public void redo() {
		command = redoStack.peek();
		command.execute();
		if (command instanceof RemoveShapeCmd) {
			this.undoShapesList.add(this.redoShapesList.get(this.redoShapesList.size() - 1));
			this.selectedShapesList.remove(this.redoShapesList.get(this.redoShapesList.size() - 1));
			this.redoShapesList.remove(this.redoShapesList.size() - 1);
		}
		this.frame.getTextArea().append("Redo " + redoStack.peek().toString());
		undoCounter++;
		redoCounter--;
		frame.repaint();
		redoStack.pop();
		undoStack.push(command);
		checkUndoRedoButtons();
		enableDisableButtons();
	} */

	public void checkUndoRedoButtons() {
		if (undoCounter < 1) {
			frame.getBtnUndo().setEnabled(false);
		} else {
			frame.getBtnUndo().setEnabled(true);
		}

		if (redoCounter < 1 || redoStack.isEmpty()) {
			frame.getBtnRedo().setEnabled(false);
		} else {
			frame.getBtnRedo().setEnabled(true);
		}
	}

	public void toFront() {
		Shape shape = this.selectedShapesList.get(0);
		Command toFrontCmd = new ToFrontCmd(this.model.getShapeList(), shape);
		toFrontCmd.execute();
		this.frame.getTextArea().append(toFrontCmd.toString());
		undoStack.push(toFrontCmd);
		undoCounter++;
		redoStack.clear();
		frame.repaint();
		checkUndoRedoButtons();
		enableDisableButtons();
	}

	public void toBack() {
		Shape shape = this.selectedShapesList.get(0);
		Command toBackCmd = new ToBackCmd(this.model.getShapeList(), shape);
		toBackCmd.execute();
		this.frame.getTextArea().append(toBackCmd.toString());
		undoStack.push(toBackCmd);
		undoCounter++;
		redoStack.clear();
		frame.repaint();
		checkUndoRedoButtons();
		enableDisableButtons();
	}

	public void bringToFront() {
		Shape shape = this.selectedShapesList.get(0);
		Command bringToFrontCmd = new BringToFrontCmd(this.model.getShapeList(), shape);
		bringToFrontCmd.execute();
		this.frame.getTextArea().append(bringToFrontCmd.toString());
		undoStack.push(bringToFrontCmd);
		undoCounter++;
		redoStack.clear();
		frame.repaint();
		checkUndoRedoButtons();
		enableDisableButtons();
	}

	public void bringToBack() {
		Shape shape = this.selectedShapesList.get(0);
		Command bringToBackCmd = new BringToBackCmd(this.model.getShapeList(), shape);
		bringToBackCmd.execute();
		frame.getTextArea().append(bringToBackCmd.toString());
		undoStack.push(bringToBackCmd);
		undoCounter++;
		redoStack.clear();
		frame.repaint();
		checkUndoRedoButtons();
		enableDisableButtons();
	}

	public void enableDisableButtons() {

		if (this.model.getShapeList().size() != 0) {
			this.observableButtons.setBtnSelectEnabled(true);

			if (this.selectedShapesList.size() == 0) {
				this.observableButtons.setBtnModifyEnabled(false);
				this.observableButtons.setBtnDeleteEnabled(false);

				this.observableButtons.setBtnToFrontEnabled(false);
				this.observableButtons.setBtnToBackEnabled(false);
				this.observableButtons.setBtnBringToFronEnabled(false);
				this.observableButtons.setBtnBringToBackEnabled(false);
			} else if (this.selectedShapesList.size() == 1) {
				this.observableButtons.setBtnModifyEnabled(true);
				this.observableButtons.setBtnDeleteEnabled(true);
				checkEnableForButtonsToBackToFrontBringToFrontBringToBack();
			} else {
				this.observableButtons.setBtnModifyEnabled(false);
				this.observableButtons.setBtnDeleteEnabled(true);

				this.observableButtons.setBtnToFrontEnabled(false);
				this.observableButtons.setBtnToBackEnabled(false);
				this.observableButtons.setBtnBringToFronEnabled(false);
				this.observableButtons.setBtnBringToBackEnabled(false);
			}
		} else {
			this.observableButtons.setBtnSelectEnabled(false);
			this.observableButtons.setBtnModifyEnabled(false);
			this.observableButtons.setBtnDeleteEnabled(false);

			this.observableButtons.setBtnToFrontEnabled(false);
			this.observableButtons.setBtnToBackEnabled(false);
			this.observableButtons.setBtnBringToFronEnabled(false);
			this.observableButtons.setBtnBringToBackEnabled(false);
		}

	}

	public void checkEnableForButtonsToBackToFrontBringToFrontBringToBack() {
		Iterator<Shape> it = this.model.getShapeList().iterator();
		Shape shape;

		while (it.hasNext()) {
			shape = it.next();

			if (shape.isSelected()) {
				if (this.model.getShapeList().size() == 1) {
					this.observableButtons.setBtnToFrontEnabled(false);
					this.observableButtons.setBtnToBackEnabled(false);
					this.observableButtons.setBtnBringToFronEnabled(false);
					this.observableButtons.setBtnBringToBackEnabled(false);
				} else {
					if (shape.equals(this.model.getShape(this.model.getShapeList().size() - 1))) {
						this.observableButtons.setBtnToFrontEnabled(false);
						this.observableButtons.setBtnToBackEnabled(true);
						this.observableButtons.setBtnBringToFronEnabled(false);
						this.observableButtons.setBtnBringToBackEnabled(true);
					} else if (shape.equals(this.model.getShape(0))) {
						this.observableButtons.setBtnToFrontEnabled(true);
						this.observableButtons.setBtnToBackEnabled(false);
						this.observableButtons.setBtnBringToFronEnabled(true);
						this.observableButtons.setBtnBringToBackEnabled(false);
					} else {
						this.observableButtons.setBtnToFrontEnabled(true);
						this.observableButtons.setBtnToBackEnabled(true);
						this.observableButtons.setBtnBringToFronEnabled(true);
						this.observableButtons.setBtnBringToBackEnabled(true);
					}
				}
			}
		}
	}

	public void savePainting() throws IOException, NotSerializableException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save painting");
		FileNameExtensionFilter fileNamefilter = new FileNameExtensionFilter(".bin", "bin");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(fileNamefilter);

		int proceed = fileChooser.showSaveDialog(null);

		if (proceed == JFileChooser.APPROVE_OPTION) {
			File paintingFileToSave = fileChooser.getSelectedFile();
			String filePath = paintingFileToSave.getAbsolutePath();
			
			File logToSave;

			if (!filePath.endsWith(".bin") && !filePath.contains(".")) {
				paintingFileToSave = new File(filePath + ".bin");
				logToSave = new File(filePath + ".txt");
			}

			// imeFajla.bin
			String fileName = paintingFileToSave.getPath();

			if (fileName.substring(fileName.lastIndexOf("."), fileName.length()).contains(".bin")) {
				System.out.println("Painting file: \"" + paintingFileToSave.getName() + "\" saved successfully!");
				fileName = paintingFileToSave.getAbsolutePath().substring(0, fileName.lastIndexOf(".")) + ".txt";
				logToSave = new File(fileName);
				
				SaveManager savePainting = new SaveManager(new SavePainting());
				savePainting.save(model, paintingFileToSave);
				
				SaveManager saveLog = new SaveManager(new SaveLog());
				saveLog.save(frame, logToSave);

			} else {
				JOptionPane.showMessageDialog(null, "Wrong file extension");
			}
		}
	}

	public void openPainting() throws IOException, ClassNotFoundException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter fileNamefilter = new FileNameExtensionFilter(".bin", "bin");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(fileNamefilter);
		fileChooser.setDialogTitle("Open painting");

		int proceed = fileChooser.showOpenDialog(null);

		if (proceed == JFileChooser.APPROVE_OPTION) {
			File paintingToLoad = fileChooser.getSelectedFile();
			loadPainting(paintingToLoad);
		}
	}

	@SuppressWarnings("unchecked")
	public void loadPainting(File paintingToLoad) throws FileNotFoundException, IOException, ClassNotFoundException {
		this.frame.getTextArea().setText("");
		
		File logFileToLoad = new File(paintingToLoad.getAbsolutePath().replace("bin", "txt"));
		// If file does not exist, then also length() method will consider it empty
		if(logFileToLoad.length() == 0) {
			System.out.println("\"" + paintingToLoad.getName() + "\" file is empty!");
			return;
		}
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(logFileToLoad));
		
		String stringLine;

		while ((stringLine = bufferedReader.readLine()) != null) {
			this.frame.getTextArea().append(stringLine + "\n");
		}
		bufferedReader.close();
		
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(paintingToLoad));

		try {

			this.model.getShapeList().addAll((ArrayList<Shape>) objectInputStream.readObject());
			objectInputStream.close();

		} catch (InvalidClassException ice) {
			ice.printStackTrace();
		} catch (SocketTimeoutException ste) {
			ste.printStackTrace();
		} catch (EOFException eofe) {
			eofe.printStackTrace();
		} catch (IOException exc) {
			exc.printStackTrace();
		}

		this.frame.getView().repaint();
	}

	public void saveLog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save log");
		FileNameExtensionFilter fileNamefilter = new FileNameExtensionFilter(".txt", "txt");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(fileNamefilter);

		if (fileChooser.showSaveDialog(frame.getParent()) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String filePath = file.getAbsolutePath();
			File logFileToSave = new File(filePath + ".txt");

			System.out.println("Log file: \"" + logFileToSave.getName() + "\" saved succesfully!");

			SaveManager manager = new SaveManager(new SaveLog());
			manager.save(frame, logFileToSave);
		}
		this.frame.getView().repaint();
	}

	public void openLog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter fileNamefilter = new FileNameExtensionFilter(".txt", "txt");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(fileNamefilter);
		fileChooser.setDialogTitle("Open log");

		int proceed = fileChooser.showOpenDialog(null);

		if (proceed == JFileChooser.APPROVE_OPTION) {
			File logToLoad = fileChooser.getSelectedFile();
			loadLog(logToLoad);
		}

	}

	private void loadLog(File logToLoad) {
		try {
			this.frame.getTextArea().setText("");
			
			// If file does not exist, then also length() method will consider it empty
			if(logToLoad.length() == 0) {
				System.out.println("\"" + logToLoad.getName() + "\" file is empty!");
				return;
			}
			
			BufferedReader br = new BufferedReader(new FileReader(logToLoad));
			String stringLine;
			/* read log line by line */
			while ((stringLine = br.readLine()) != null) {
				this.logStringList.add(stringLine);
			}
			br.close();
			this.frame.getBtnLoadNext().setEnabled(true);

			System.out.println("Commands from log file:");
			for (int i = 0; i < logStringList.size(); i++) {
				System.out.println(logStringList.get(i));
			}

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void loadNext() {
		Shape loadingShape = null;

		if (logStringListCounter < this.logStringList.size()) {

			String logString = this.logStringList.get(logStringListCounter);

			if (logString.contains("Point")) {
				int x = Integer.parseInt(logString.substring(logString.indexOf("(") + 1, logString.indexOf(",")));
				int y = Integer.parseInt(logString.substring(logString.indexOf(",") + 2, logString.indexOf(")")));
				int pointColor = Integer
						.parseInt(logString.substring(logString.lastIndexOf("(") + 1, logString.lastIndexOf(")")));
				loadingShape = new Point(x, y);
				loadingShape.setColor(new Color(pointColor));

			} else if (logString.contains("Line")) {
				Point startPoint = new Point();
				Point endPoint = new Point();
				startPoint.setX(
						Integer.parseInt(logString.substring(logString.indexOf("(") + 1, logString.indexOf(","))));
				startPoint.setY(
						Integer.parseInt(logString.substring(logString.indexOf(",") + 2, logString.indexOf(")"))));

				endPoint.setX(Integer.parseInt(
						logString.substring(findIndexOf(2, '(', logString) + 1, findIndexOf(2, ',', logString))));
				endPoint.setY(Integer.parseInt(
						logString.substring(findIndexOf(2, ',', logString) + 2, findIndexOf(2, ')', logString))));

				int lineColor = Integer.parseInt(
						logString.substring(findIndexOf(3, '(', logString) + 1, findIndexOf(3, ')', logString)));
				loadingShape = new Line(startPoint, endPoint);
				loadingShape.setColor(new Color(lineColor));

			} else if (logString.contains("Circle")) {
				Point center = new Point();
				int radius, innerColor, borderColor;

				center.setX(Integer.parseInt(logString.substring(logString.indexOf("(") + 1, logString.indexOf(","))));
				center.setY(Integer.parseInt(logString.substring(logString.indexOf(",") + 2, logString.indexOf(")"))));
				radius = Integer.parseInt(logString.substring(logString.indexOf("=") + 1, findIndexOf(3, ',', logString)));
				innerColor = Integer.parseInt(
						logString.substring(findIndexOf(2, '(', logString) + 1, findIndexOf(2, ')', logString)));
				borderColor = Integer.parseInt(
						logString.substring(findIndexOf(3, '(', logString) + 1, findIndexOf(3, ')', logString)));

				loadingShape = new Circle(center, radius, false, new Color(borderColor), new Color(innerColor));

			} else if (logString.contains("Rectangle")) {
				Point upperLeftPoint = new Point();
				int height, width, innerColor, borderColor;

				upperLeftPoint.setX(
						Integer.parseInt(logString.substring(logString.indexOf("(") + 1, logString.indexOf(","))));
				upperLeftPoint.setY(Integer.parseInt(logString.substring(logString.indexOf(",") + 2,
						logString.indexOf(")")))); /* Ovako radi za start index (",")+2 */
				height = Integer.parseInt(logString.substring(logString.indexOf("=") + 1, findIndexOf(3,',', logString)));
				width = Integer
						.parseInt(logString.substring(findIndexOf(2, '=', logString) + 1, findIndexOf(4, ',', logString)));
				innerColor = Integer.parseInt(
						logString.substring(findIndexOf(2, '(', logString) + 1, findIndexOf(2, ')', logString)));
				borderColor = Integer.parseInt(
						logString.substring(findIndexOf(3, '(', logString) + 1, findIndexOf(3, ')', logString)));

				loadingShape = new Rectangle(upperLeftPoint, width, height, false, new Color(borderColor),
						new Color(innerColor));

			} else if (logString.contains("Donut")) {
				Point center = new Point();
				int radius, innerRadius, innerColor, borderColor;

				center.setX(Integer.parseInt(logString.substring(logString.indexOf("(") + 1, logString.indexOf(","))));
				center.setY(Integer.parseInt(logString.substring(logString.indexOf(",") + 2, logString.indexOf(")"))));
				radius = Integer.parseInt(logString.substring(logString.indexOf("=") + 1, findIndexOf(3, ',', logString)));
				innerRadius = Integer
						.parseInt(logString.substring(findIndexOf(2, '=', logString) + 1, findIndexOf(4, ',', logString)));
				innerColor = Integer.parseInt(
						logString.substring(findIndexOf(2, '(', logString) + 1, findIndexOf(2, ')', logString)));
				borderColor = Integer.parseInt(
						logString.substring(findIndexOf(3, '(', logString) + 1, findIndexOf(3, ')', logString)));

				loadingShape = new Donut(center, radius, innerRadius, false, new Color(borderColor),
						new Color(innerColor));

			} else if (logString.contains("Hexagon")) {
				Point center = new Point();
				int radius, innerColor, borderColor;

				center.setX(Integer.parseInt(logString.substring(logString.indexOf("(") + 1, logString.indexOf(","))));
				center.setY(Integer.parseInt(logString.substring(logString.indexOf(",") + 2, logString.indexOf(")"))));
				radius = Integer.parseInt(logString.substring(logString.indexOf("=") + 1, findIndexOf(3, ',', logString)));
				innerColor = Integer.parseInt(
						logString.substring(findIndexOf(2, '(', logString)+ 1, findIndexOf(2, ')', logString)));
				borderColor = Integer.parseInt(
						logString.substring(findIndexOf(3, '(', logString) + 1, findIndexOf(3, ')', logString)));

				loadingShape = new HexagonAdapter(center, radius, false, new Color(borderColor), new Color(innerColor));
			}

			if (logString.contains("Added")) {

				AddShapeCmd addCmd;

				if (logString.contains("Undo")) {
					addCmd = (AddShapeCmd) undoStack.peek();
					addCmd.unexecute();
					undoStack.pop();
					redoStack.push(addCmd);
					this.frame.getTextArea().append("Undo " + addCmd.toString());

				} else if (logString.contains("Redo")) {
					addCmd = (AddShapeCmd) redoStack.peek();
					addCmd.execute();
					redoStack.pop();
					undoStack.push(addCmd);
					this.frame.getTextArea().append("Redo " + addCmd.toString());

				} else {
					addCmd = new AddShapeCmd(this.model, loadingShape);
					addCmd.execute();
					undoStack.push(addCmd);
					redoStack.clear();
					this.frame.getTextArea().append(addCmd.toString());
				}

			} else if (logString.contains("Selected")) {

				SelectShapeCmd selectCmd;

				if (logString.contains("Undo")) {
					selectCmd = (SelectShapeCmd) undoStack.peek();
					selectCmd.unexecute();
					undoStack.pop();
					redoStack.push(selectCmd);
					this.frame.getTextArea().append("Undo " + selectCmd.toString());

				} else if (logString.contains("Redo")) {
					selectCmd = (SelectShapeCmd) redoStack.peek();
					selectCmd.execute();
					redoStack.pop();
					undoStack.push(selectCmd);
					this.frame.getTextArea().append("Redo " + selectCmd.toString());

				} else {
					loadingShape = this.model.getShapeList().get(this.model.getShapeList().indexOf(loadingShape));
					selectCmd = new SelectShapeCmd(this, loadingShape);
					selectCmd.execute();
					undoStack.push(selectCmd);
					redoStack.clear();
					this.frame.getTextArea().append(selectCmd.toString());
				}

			} else if (logString.contains("Deselect")) {

				DeselectShapeCmd deselectShape;

				if (logString.contains("Undo")) {
					deselectShape = (DeselectShapeCmd) undoStack.peek();
					deselectShape.unexecute();
					undoStack.pop();
					redoStack.push(deselectShape);
					this.frame.getTextArea().append("Undo " + deselectShape.toString());

				} else if (logString.contains("Redo")) {
					deselectShape = (DeselectShapeCmd) redoStack.peek();
					deselectShape.execute();
					redoStack.pop();
					undoStack.push(deselectShape);
					this.frame.getTextArea().append("Redo " + deselectShape.toString());

				} else {
					loadingShape = this.selectedShapesList.get(this.selectedShapesList.indexOf(loadingShape));
					deselectShape = new DeselectShapeCmd(this, loadingShape);
					deselectShape.execute();
					undoStack.push(deselectShape);
					this.frame.getTextArea().append(deselectShape.toString());
				}

			} else if (logString.contains("Remove")) {

				RemoveShapeCmd removeCmd;

				if (logString.contains("Undo")) {
					
					removeCmd = (RemoveShapeCmd)undoStack.peek();
					
					removeCmd.unexecute();
					this.redoShapesList.add(this.undoShapesList.get(this.undoShapesList.size() - 1));
					this.selectedShapesList.add(this.undoShapesList.get(this.undoShapesList.size() - 1));
					this.undoShapesList.remove(this.undoShapesList.size() - 1);
					undoStack.pop();
					redoStack.push(removeCmd);
					this.frame.getTextArea().append("Undo " + removeCmd.toString());
					

				} else if (logString.contains("Redo")) {
					
					removeCmd = (RemoveShapeCmd)redoStack.peek();
					removeCmd.execute();
					this.undoShapesList.add(this.redoShapesList.get(this.redoShapesList.size() - 1));
					this.selectedShapesList.remove(this.redoShapesList.get(this.redoShapesList.size() - 1));
					this.redoShapesList.remove(this.redoShapesList.size() - 1);
					redoStack.pop();
					undoStack.push(removeCmd);
					this.frame.getTextArea().append("Redo " + removeCmd.toString());
				}
				  else {
					loadingShape = this.selectedShapesList.get(0);
					removeCmd = new RemoveShapeCmd(this.model, loadingShape,
							this.model.getShapeList().indexOf(loadingShape));
					removeCmd.execute();
					this.selectedShapesList.remove(loadingShape);
					this.undoShapesList.add(loadingShape);
					undoStack.push(removeCmd);
					redoStack.clear();
					this.frame.getTextArea().append(removeCmd.toString());
				}

			} else if (logString.contains("Modified")) {

				if (loadingShape instanceof Point) {

					UpdatePointCmd updatePointCmd;

					if (logString.contains("Undo")) {

						updatePointCmd = (UpdatePointCmd) undoStack.peek();
						updatePointCmd.unexecute();
						undoStack.pop();
						redoStack.push(updatePointCmd);
						this.frame.getTextArea().append("Undo " + updatePointCmd.toString());

					} else if (logString.contains("Redo")) {

						updatePointCmd = (UpdatePointCmd) redoStack.peek();
						updatePointCmd.execute();
						redoStack.pop();
						undoStack.push(updatePointCmd);
						this.frame.getTextArea().append("Redo " + updatePointCmd.toString());

					} else {
						loadingShape = this.selectedShapesList.get(0);
						Point newPointState = new Point();
						int newPointStateColor;

						newPointState.setX(Integer.parseInt(
								logString.substring(findIndexOf(3, '(', logString) + 1, findIndexOf(3, ',', logString))));
						newPointState.setY(Integer.parseInt(
								logString.substring(findIndexOf(3, ',', logString) + 2, findIndexOf(3, ')', logString))));
						newPointStateColor = Integer.parseInt(logString.substring(findIndexOf(4, '(', logString) + 1,
								findIndexOf(4, ')', logString)));
						newPointState.setColor(new Color(newPointStateColor));

						updatePointCmd = new UpdatePointCmd((Point) loadingShape, newPointState);
						updatePointCmd.execute();
						undoStack.push(updatePointCmd);
						redoStack.clear();
						this.frame.getTextArea().append(updatePointCmd.toString());
					}

				} else if (loadingShape instanceof Line) {

					UpdateLineCmd updateLineCmd;

					if (logString.contains("Undo")) {

						updateLineCmd = (UpdateLineCmd) undoStack.peek();
						updateLineCmd.unexecute();
						undoStack.pop();
						redoStack.push(updateLineCmd);
						this.frame.getTextArea().append("Undo " + updateLineCmd.toString());

					} else if (logString.contains("Redo")) {

						updateLineCmd = (UpdateLineCmd) redoStack.peek();
						updateLineCmd.execute();
						redoStack.pop();
						undoStack.push(updateLineCmd);
						this.frame.getTextArea().append("Redo " + updateLineCmd.toString());

					} else {
						loadingShape = this.selectedShapesList.get(0);
						Point p1 = new Point();
						Point p2 = new Point();
						int lineColor;

						p1.setX(Integer.parseInt(
								logString.substring(findIndexOf(4, '(', logString) + 1, findIndexOf(4, ',', logString))));
						p1.setY(Integer.parseInt(logString.substring(findIndexOf(4, ',', logString) + 2,
								findIndexOf(4, ')', logString))));

						p2.setX(Integer.parseInt(
								logString.substring(findIndexOf(5, '(', logString) + 1, findIndexOf(5, ',', logString))));
						p2.setY(Integer.parseInt(
								logString.substring(findIndexOf(5, ',', logString) + 2, findIndexOf(5, ')', logString))));

						lineColor = Integer.parseInt(logString.substring(findIndexOf(6, '(', logString) + 1,
								findIndexOf(6, ')', logString)));

						Line newLineState = new Line(p1, p2);
						newLineState.setColor(new Color(lineColor));

						updateLineCmd = new UpdateLineCmd((Line) loadingShape, newLineState);
						updateLineCmd.execute();
						undoStack.push(updateLineCmd);
						redoStack.clear();
						this.frame.getTextArea().append(updateLineCmd.toString());
					}

				} else if (loadingShape instanceof HexagonAdapter) {

					UpdateHexagonCmd updateHexagonCmd;

					if (logString.contains("Undo")) {

						updateHexagonCmd = (UpdateHexagonCmd) undoStack.peek();
						updateHexagonCmd.unexecute();
						undoStack.pop();
						redoStack.push(updateHexagonCmd);
						this.frame.getTextArea().append("Undo " + updateHexagonCmd.toString());

					} else if (logString.contains("Redo")) {

						updateHexagonCmd = (UpdateHexagonCmd) redoStack.peek();
						updateHexagonCmd.execute();
						redoStack.pop();
						undoStack.push(updateHexagonCmd);
						this.frame.getTextArea().append("Redo " + updateHexagonCmd.toString());

					} else {
						loadingShape = this.selectedShapesList.get(0);
						Point center = new Point();
						int radius, innerColor, borderColor;

						center.setX(Integer.parseInt(
								logString.substring(findIndexOf(4, '(', logString) + 1, findIndexOf(5, ',', logString))));
						center.setY(Integer.parseInt(
								logString.substring(findIndexOf(5, ',', logString) + 2, findIndexOf(4, ')', logString))));

						radius = Integer.parseInt(
								logString.substring(findIndexOf(2, '=', logString) + 1, findIndexOf(7, ',', logString)));
						innerColor = Integer.parseInt(logString.substring(findIndexOf(5, '(', logString) + 1,
								findIndexOf(5, ')', logString)));
						borderColor = Integer.parseInt(logString.substring(findIndexOf(6, '(', logString) + 1,
								findIndexOf(6, ')', logString)));

						HexagonAdapter newHexagonState = new HexagonAdapter(center, radius, true,
								new Color(borderColor), new Color(innerColor));

						updateHexagonCmd = new UpdateHexagonCmd((HexagonAdapter) loadingShape, newHexagonState);
						updateHexagonCmd.execute();
						undoStack.push(updateHexagonCmd);
						redoStack.clear();
						this.frame.getTextArea().append(updateHexagonCmd.toString());
					}
				}

				else if (loadingShape instanceof Donut) {

					UpdateDonutCmd updateDonutCmd;

					if (logString.contains("Undo")) {

						updateDonutCmd = (UpdateDonutCmd) undoStack.peek();
						updateDonutCmd.unexecute();
						undoStack.pop();
						redoStack.push(updateDonutCmd);
						this.frame.getTextArea().append("Undo " + updateDonutCmd.toString());

					} else if (logString.contains("Redo")) {

						updateDonutCmd = (UpdateDonutCmd) redoStack.peek();
						updateDonutCmd.execute();
						redoStack.pop();
						undoStack.push(updateDonutCmd);
						this.frame.getTextArea().append("Redo " + updateDonutCmd.toString());

					} else {
						loadingShape = this.selectedShapesList.get(0);
						Point center = new Point();
						int radius, innerRadius, innerColor, borderColor;

						center.setX(Integer.parseInt(
								logString.substring(findIndexOf(4, '(', logString) + 1, findIndexOf(6, ',', logString))));
						center.setY(Integer.parseInt(
								logString.substring(findIndexOf(6, ',', logString) + 2, findIndexOf(4, ')', logString))));

						radius = Integer.parseInt(
								logString.substring(findIndexOf(3, '=', logString) + 1, findIndexOf(8, ',', logString)));
						innerRadius = Integer.parseInt(
								logString.substring(findIndexOf(4, '=', logString) + 1, findIndexOf(9, ',', logString)));
						innerColor = Integer.parseInt(logString.substring(findIndexOf(5, '(', logString) + 1,
								findIndexOf(5, ')', logString)));
						borderColor = Integer.parseInt(logString.substring(findIndexOf(6, '(', logString) + 1,
								findIndexOf(6, ')', logString)));

						Donut newDonutState = new Donut(center, radius, innerRadius, true, new Color(borderColor),
								new Color(innerColor));
						updateDonutCmd = new UpdateDonutCmd((Donut) loadingShape, newDonutState);
						updateDonutCmd.execute();
						undoStack.push(updateDonutCmd);
						redoStack.clear();
						this.frame.getTextArea().append(updateDonutCmd.toString());
					}

				}

				else if (loadingShape instanceof Circle) {

					UpdateCircleCmd updateCircleCmd;

					if (logString.contains("Undo")) {

						updateCircleCmd = (UpdateCircleCmd) undoStack.peek();
						updateCircleCmd.unexecute();
						undoStack.pop();
						redoStack.push(updateCircleCmd);
						this.frame.getTextArea().append("Undo " + updateCircleCmd.toString());

					} else if (logString.contains("Redo")) {

						updateCircleCmd = (UpdateCircleCmd) redoStack.peek();
						updateCircleCmd.execute();
						redoStack.pop();
						undoStack.push(updateCircleCmd);
						this.frame.getTextArea().append("Redo " + updateCircleCmd.toString());

					} else {
						loadingShape = this.selectedShapesList.get(0);
						Point center = new Point();
						int radius, innerColor, borderColor;

						center.setX(Integer.parseInt(
								logString.substring(findIndexOf(4, '(', logString) + 1, findIndexOf(5, ',', logString))));
						center.setY(Integer.parseInt(
								logString.substring(findIndexOf(5, ',', logString) + 2, findIndexOf(4, ')', logString))));

						radius = Integer.parseInt(
								logString.substring(findIndexOf(2, '=', logString) + 1, findIndexOf(7, ',', logString)));
						innerColor = Integer.parseInt(logString.substring(findIndexOf(5, '(', logString) + 1,
								findIndexOf(5, ')', logString)));
						borderColor = Integer.parseInt(logString.substring(findIndexOf(6, '(', logString) + 1,
								findIndexOf(6, ')', logString)));

						Circle newCircleState = new Circle(center, radius, true, new Color(borderColor),
								new Color(innerColor));

						updateCircleCmd = new UpdateCircleCmd((Circle) loadingShape, newCircleState);
						updateCircleCmd.execute();
						undoStack.push(updateCircleCmd);
						redoStack.clear();
						this.frame.getTextArea().append(updateCircleCmd.toString());
					}

				} else if (loadingShape instanceof Rectangle) {

					UpdateRectangleCmd updateRectangleCmd;

					if (logString.contains("Undo")) {

						updateRectangleCmd = (UpdateRectangleCmd) undoStack.peek();
						updateRectangleCmd.unexecute();
						undoStack.pop();
						redoStack.push(updateRectangleCmd);
						this.frame.getTextArea().append("Undo " + updateRectangleCmd.toString());

					} else if (logString.contains("Redo")) {

						updateRectangleCmd = (UpdateRectangleCmd) redoStack.peek();
						updateRectangleCmd.execute();
						redoStack.pop();
						undoStack.push(updateRectangleCmd);
						this.frame.getTextArea().append("Redo " + updateRectangleCmd.toString());

					} else {
						loadingShape = this.selectedShapesList.get(0);
						Point upperLeftPoint = new Point();
						int height, width, innerColor, borderColor;

						upperLeftPoint.setX(Integer.parseInt(
								logString.substring(findIndexOf(4, '(', logString) + 1, findIndexOf(6, ',', logString))));
						upperLeftPoint.setY(Integer.parseInt(
								logString.substring(findIndexOf(6, ',', logString) + 2, findIndexOf(4, ')', logString))));

						height = Integer.parseInt(
								logString.substring(findIndexOf(3, '=', logString) + 1, findIndexOf(8, ',', logString)));
						width = Integer.parseInt(
								logString.substring(findIndexOf(4, '=', logString) + 1, findIndexOf(9, ',', logString)));
						innerColor = Integer.parseInt(logString.substring(findIndexOf(5, '(', logString) + 1,
								findIndexOf(5, ')', logString)));
						borderColor = Integer.parseInt(logString.substring(findIndexOf(6, '(', logString) + 1,
								findIndexOf(6, ')', logString)));

						Rectangle newRectangleState = new Rectangle(upperLeftPoint, width, height, true,
								new Color(borderColor), new Color(innerColor));

						updateRectangleCmd = new UpdateRectangleCmd((Rectangle) loadingShape, newRectangleState);
						updateRectangleCmd.execute();
						undoStack.push(updateRectangleCmd);
						redoStack.clear();
						this.frame.getTextArea().append(updateRectangleCmd.toString());

					}

				}

			} else if (logString.contains("Moved to back")) {

				ToBackCmd toBackCmd;

				if (logString.contains("Undo")) {
					toBackCmd = (ToBackCmd) undoStack.peek();
					toBackCmd.unexecute();
					undoStack.pop();
					redoStack.push(toBackCmd);
					this.frame.getTextArea().append("Undo " + toBackCmd.toString());

				} else if (logString.contains("Redo")) {
					toBackCmd = (ToBackCmd) redoStack.peek();
					toBackCmd.execute();
					redoStack.pop();
					undoStack.push(toBackCmd);
					this.frame.getTextArea().append("Redo " + toBackCmd.toString());

				} else {
					loadingShape = this.selectedShapesList.get(0);
					toBackCmd = new ToBackCmd(this.model.getShapeList(), loadingShape);
					toBackCmd.execute();
					undoStack.push(toBackCmd);
					redoStack.clear();
					this.frame.getTextArea().append(toBackCmd.toString());
				}

			} else if (logString.contains("Moved to front")) {

				ToFrontCmd toFrontCmd;

				if (logString.contains("Undo")) {
					toFrontCmd = (ToFrontCmd) undoStack.peek();
					toFrontCmd.unexecute();
					undoStack.pop();
					redoStack.push(toFrontCmd);
					this.frame.getTextArea().append("Undo " + toFrontCmd.toString());

				} else if (logString.contains("Redo")) {
					toFrontCmd = (ToFrontCmd) redoStack.peek();
					toFrontCmd.execute();
					redoStack.pop();
					undoStack.push(toFrontCmd);
					this.frame.getTextArea().append("Redo " + toFrontCmd.toString());

				} else {
					loadingShape = this.selectedShapesList.get(0);
					toFrontCmd = new ToFrontCmd(this.model.getShapeList(), loadingShape);
					toFrontCmd.execute();
					undoStack.push(toFrontCmd);
					redoStack.clear();
					this.frame.getTextArea().append(toFrontCmd.toString());
				}

			} else if (logString.contains("Bringed to back")) {

				BringToBackCmd bringToBackCmd;

				if (logString.contains("Undo")) {
					bringToBackCmd = (BringToBackCmd) undoStack.peek();
					bringToBackCmd.unexecute();
					undoStack.pop();
					redoStack.push(bringToBackCmd);
					this.frame.getTextArea().append("Undo " + bringToBackCmd.toString());

				} else if (logString.contains("Redo")) {
					bringToBackCmd = (BringToBackCmd) redoStack.peek();
					bringToBackCmd.execute();
					redoStack.pop();
					undoStack.push(bringToBackCmd);
					this.frame.getTextArea().append("Redo " + bringToBackCmd.toString());

				} else {
					loadingShape = this.selectedShapesList.get(0);
					bringToBackCmd = new BringToBackCmd(this.model.getShapeList(), loadingShape);
					bringToBackCmd.execute();
					undoStack.push(bringToBackCmd);
					redoStack.clear();
					this.frame.getTextArea().append(bringToBackCmd.toString());
				}

			} else if (logString.contains("Bringed to front")) {

				BringToFrontCmd bringToFrontCmd;

				if (logString.contains("Undo")) {
					bringToFrontCmd = (BringToFrontCmd) undoStack.peek();
					bringToFrontCmd.unexecute();
					undoStack.pop();
					redoStack.push(bringToFrontCmd);
					this.frame.getTextArea().append("Undo " + bringToFrontCmd.toString());

				} else if (logString.contains("Redo")) {
					bringToFrontCmd = (BringToFrontCmd) redoStack.peek();
					bringToFrontCmd.execute();
					redoStack.pop();
					undoStack.push(bringToFrontCmd);
					this.frame.getTextArea().append("Redo " + bringToFrontCmd.toString());

				} else {
					loadingShape = this.selectedShapesList.get(0);
					bringToFrontCmd = new BringToFrontCmd(this.model.getShapeList(), loadingShape);
					bringToFrontCmd.execute();
					undoStack.push(bringToFrontCmd);
					redoStack.clear();
					this.frame.getTextArea().append(bringToFrontCmd.toString());
				}
			}

			logStringListCounter++;
			this.frame.repaint();

		} else {
			this.frame.getBtnLoadNext().setEnabled(false);
		}

	}
	
	
	public int findIndexOf(int n, char c, String s) {
		int occurr = 0;
		for(int i = 0; i < s.length(); i++) {
			
			if(s.charAt(i) == c) {
				occurr += 1;
			}
			
			if(occurr == n) {
				return i;
			}
		}
		return -1;
	}

	

	public ArrayList<Shape> getSelectedShapesList() {
		return this.selectedShapesList;
	}
}
