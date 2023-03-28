# Assignment feedback

- Marked By Bohan

## Domain Modelling 15%
- GPS is not really necessary
- Proof read your UML Diagram, you have two LaptopDevices instead of a Handheldd device
- Attributes should be private, notated by - on the UML diagram
- Missing arguments an dreturn values for getMaxRange() in the devices
- File should not be abstract
- Satellite should be abstract
- Try not to have overlapping lines on the diagram
- Subclasses should be notated with a triangle
- has-a relationships should have a diamond and cardinalities
- It would be better to have an Entity class
- BlackoutController should be on the UML diagram
- Missing FileCarrier on the UML diagram
- Relay satellite does not have files

## Coupling & Cohesion 20%
- getInfo() should be a method in satellite/device
- Why is there a static list of files in the File class
- GPD, FileCarrier are not necessary

## Code Quality 5%
- Use `for (Class a : collection)` if you don't need the index
- Prefer switch statements when possible
- A lot of delete me comments around

## Blogging 5%
- Blog posts should be linked in the gitlab repo

## Testing 5%
- Could use a few more tests
