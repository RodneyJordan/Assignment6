package models;


public class ProductTemplateParts {
	
	private int productTemplateId;	//FK to ProductTemplate
	private int partId;				//FK to Part
	private int quantity;			//# of parts consumed to construct an instance of product

	public ProductTemplateParts(int productTemplateId, int partId, int quantity) {
		this.productTemplateId = productTemplateId;
		this.partId = partId;
		this.quantity = quantity;
	}

	/*
	 * ACCESSORS
	 */
	public int getProductTemplateId(){ return this.productTemplateId; }
	public int getPartId(){ return this.partId; }
	public int getQuantity(){ return this.quantity; }

	/*
	 * MUTATORS
	 */
	public void setProductTemplateId(int productTemplateId) { this.productTemplateId = productTemplateId; }
	public void setPartId(int partId) { this.partId = partId; }
	public void setQuantity(int quantity) { this.quantity = quantity; }
	
	public boolean equals(Object obj) {
		if(!(obj instanceof ProductTemplateParts)) {
			return false;
		}
			
		ProductTemplateParts other = (ProductTemplateParts) obj;
			
		return this.productTemplateId == (other.productTemplateId);
	}
}