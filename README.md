## 2D-Shape-Drawing-DesktopApp-Design-Patterns-Project-Java

* Java desktop application for drawing 2D shapes. Java Swing used as a GUI toolkit.

* App has the following specification/functionalities:
  - Drawing, modifying, deleting shapes (Point, Line, Circle, Rectangle, Donut, Hexagon).
  - Selection & manipulation with multiple shapes.
  - Undo/Redo functionality for every action that is applied on GUI (JPanel within Java Swing).
  - Auto generating & showing log of all actions that are executed while working with application. 
  - Saving/auto writting log of all actions to a text file.
  - Saving painting to a file (Serialization).
  - Showing current active edge & internal color for shapes while working in the app.
  - To Front, To Back, Bring To Front, Bring To Back functionalites. Moving selected shape along the Z axis.
  
* Design patterns implemented:
  - MVC design pattern - project is structured according Model-View-Controller architecutural pattern.
  - Adapter pattern - providing compatible interface for Hexagon object(shape).
  - Command pattern - creating command object that encapsulates all information needed to perform an action on objects/shapes.
  - Prototype pattern - hiding complexity of making new instances(objects/shapes) from client. clone() method.
  - Strategy pattern - applies when user can choose between saving log of commands to a text file, or saving a painting to a file. Decides which algorithm implementation is going to be used at runtime, depends on user action.
  - Observer pattern - used for defining subscriptional mechanism to notify, in this app button objects, about any events that happen to the specific object they're observing. Managing state of button objects.
