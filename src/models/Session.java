package models;

import java.util.ArrayList;

public class Session {
	
	private User user;
	private AuthenticationGateway authGate;
	
	/*
	 * PERMISSIONS
	 * 
	 * View Prod Temp				|	Prod Mgr, Admin
	 * Add/Edit Prod Temp			|	Prod Mgr, Admin
	 * Delete Prod Temp				|	Prod Mgr, Admin
	 * Create Products				|	Prod Mgr, Admin
	 * View Inventory				|	All
	 * Add/Edit Inv Items			|	Inv Mgr, Admin
	 * Delete Inv Item				|	Admin
	 * View Parts					|	All
	 * Add/Edit Parts				|	Inv Mgr, Admin
	 * Delete Parts					|	Admin
	 */
	private boolean canViewProductTemplates;		// done
	private boolean canAddProductTemplates;			// done
	private boolean canEditProductTemplates;		//done
	private boolean canDeleteProductTemplates;		//done
	private boolean canCreateProducts;				//
	private boolean canViewInventoryItems;			// done
	private boolean canAddInventoryItems;			//done
	private boolean canEditInventoryItems;			//done
	private boolean canDeleteInventoryItems;		//done
	private boolean canViewParts;					//done
	private boolean canAddParts;					//done
	private boolean canEditParts;					//done
	private boolean canDeleteParts;					//done
	
	public Session(){
		user = new User();
	}
	
	public Session(User user){
		this.user = user;
	}
	
	public Session(String fullName, String email){
		this.user = new User(fullName, email);
	}
	
	public Session(String email) {
		authGate = new AuthenticationGateway();
		this.user = authGate.getUser(email);
		setRolePermissions();
	}
	
	public User getUser() {
		return this.user;
	}
	
	
	private void setRolePermissions(){
		ArrayList<Integer> permissions;
		//call auth gateway to get role permissions
//		permissions = authGate.getRolePolicy(user.getRoleId());
		
		//then set permission booleans here
		//gonna do this in a piss-poor hardcoded way because lazy, also time constraints
		for(int p : authGate.getRolePolicy(user.getRoleId())){
			switch(p){
			case 1: this.canViewProductTemplates = true;
				break;
			case 2: this.canAddProductTemplates = true;
				break;
			case 11: this.canEditProductTemplates = true;
				break;
			case 3: this.canDeleteProductTemplates = true;
				break;
			case 4: this.canCreateProducts = true;
				break;
			case 5: this.canViewInventoryItems = true;
				break;
			case 6: this.canAddInventoryItems = true;
				break;
			case 12: this.canEditInventoryItems = true;
				break;
			case 7: this.canDeleteInventoryItems = true;
				break;
			case 8: this.canViewParts = true;
				break;
			case 9: this.canAddParts = true;
				break;
			case 13: this.canEditParts = true;
				break;
			case 10: this.canDeleteParts = true;
				break;
			}
		}
		
		/*
		 //debugging
		for(int i : authGate.getRolePolicy(user.getRoleId())){
			System.out.println(i);
		}
		
		System.out.println(canViewProductTemplates);
		System.out.println(canAddProductTemplates);
		System.out.println(canEditProductTemplates);
		System.out.println(canDeleteProductTemplates);
		System.out.println(canCreateProducts);
		System.out.println(canViewInventoryItems);
		System.out.println(canAddInventoryItems);
		System.out.println(canEditInventoryItems);
		System.out.println(canDeleteInventoryItems);
		System.out.println(canViewParts);
		System.out.println(canAddParts);
		System.out.println(canEditParts);
		System.out.println(canDeleteParts);
		*/
	}

	/*
	 * ACCESSORS
	 */
	
	public boolean isCanViewProductTemplates() {
		return canViewProductTemplates;
	}

	public boolean isCanAddProductTemplates() {
		return canAddProductTemplates;
	}

	public boolean isCanEditProductTemplates() {
		return canEditProductTemplates;
	}

	public boolean isCanDeleteProductTemplates() {
		return canDeleteProductTemplates;
	}

	public boolean isCanCreateProducts() {
		return canCreateProducts;
	}

	public boolean isCanViewInventoryItems() {
		return canViewInventoryItems;
	}

	public boolean isCanAddInventoryItems() {
		return canAddInventoryItems;
	}

	public boolean isCanEditInventoryItems() {
		return canEditInventoryItems;
	}

	public boolean isCanDeleteInventoryItems() {
		return canDeleteInventoryItems;
	}

	public boolean isCanViewParts() {
		return canViewParts;
	}

	public boolean isCanAddParts() {
		return canAddParts;
	}

	public boolean isCanEditParts() {
		return canEditParts;
	}

	public boolean isCanDeleteParts() {
		return canDeleteParts;
	}
	
}