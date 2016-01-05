# Assignment 6

#### Team
* Rodney Jordan
* Jacob Pagano

#### Code Notes
+ Gateways - one is for parts, one for items, one for templates, template parts, authentication; they are named respectively
+ Detail views are accessed with a double-click on the list item, or by selecting the item from the list and clicking "Edit"; same for "Add/Delete part" buttons
+ Each template detail view displays a TemplateParts list view for the respective template
+ NEW: Role based authorization has been implemented with DB support
+ NEW: Create product implemented with transaction rollback support on failed execution
+ NEW: Access controll checklist in PDF form in the root folder

#### Program Flow
The initial window is the inventory item list. From here editing, adding, deleting item buttons are accessible.
A final button labeled "Parts" is also accessible from the inventory list. This button opens the parts list view.
From the parts list view editing, adding, deleting part buttons are accessible.

We chose to open new windows so that each list can be viewable at the same time with independent viewing size.
This would be helpful in a real-use situation where a user might be using dual-monitors; one window on each monitor.
