package controllers;

import models.DetailTemplateModel;
import models.Part;
import models.PartsModel;
import models.ProductTemplate;
import models.ProductTemplateParts;
import models.ProductTemplatePartsModel;
import models.TemplateModel;
import views.EditProductTemplatePartsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller to edit a template
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class EditProductTemplatePartsController implements ActionListener {
   
   /**
    * The edit template view
    */
   private EditProductTemplatePartsView editProductTemplatePartsView;
   
   /**
    * The parts model
    */
   private PartsModel partsModel;
   
   /**
    * The template Model
    */
   private TemplateModel templateModel;
   
   /**
    * Strings that may be edited
    */
   private String templateNumber;
   private String description;
   private String quantity;
   
   /**
    * The product template parts model
    */
   private ProductTemplatePartsModel templatePartsModel;
   
   /**
    * The template
    */
   private ProductTemplate template;
   
   /**
    * The detail template model
    */
   private DetailTemplateModel detailTemplateModel;
   
   /**
    * The product template part
    */
   private ProductTemplateParts templatePart;
   
   /**
    * Constructor
    * @param templateModel
    * @param template
    */
   public EditProductTemplatePartsController(TemplateModel templateModel, ProductTemplate template, DetailTemplateModel detailTemplateModel,
         ProductTemplatePartsModel templatePartsModel, ProductTemplateParts templatePart, PartsModel partsModel) {
      this.template = template;
      this.partsModel = partsModel;
      this.templatePart = templatePart;
      this.templatePartsModel = templatePartsModel;
      this.detailTemplateModel = detailTemplateModel;
      editProductTemplatePartsView = new EditProductTemplatePartsView(this.template, this.templatePart, detailTemplateModel, templatePartsModel, editTemplatePartsMouseListener);
      this.templateModel = templateModel;
      editProductTemplatePartsView.registerListeners(this);
      templateNumber = editProductTemplatePartsView.getTemplateNumber();
      description = editProductTemplatePartsView.getDescription();
   }
   
   /**
    * Watches for buttons pressed on the edit template view
    * @param e : action performed
    */
   public void actionPerformed(ActionEvent e) {
      String actionCommand = e.getActionCommand();
      
      if(actionCommand.equals("Edit")) {
         if(!(this.templateNumber.equals(editProductTemplatePartsView.getTemplateNumber()))) {
            templateModel.editPart(templateNumber, description, template);
         }
         if(!(this.description.equals(editProductTemplatePartsView.getDescription()))) {
            templateModel.editPart(templateNumber, description, template);
         }
         if(!(this.quantity.equals(editProductTemplatePartsView.getDescription()))) { //issue
            int quantityInt = Integer.parseInt(quantity);
            templatePartsModel.editTemplatePartQuantity(templatePart, quantityInt);
         }
      }
      else if(actionCommand.equals("Add")) {
         @SuppressWarnings("unused")
         AddPartToTemplatePartController addPartToTemplatePartController = new AddPartToTemplatePartController(partsModel, 
               templatePartsModel, this.templatePart, detailTemplateModel);
         
      }
      else if(actionCommand.equals("Remove")) {
         if(this.editProductTemplatePartsView.deletePart()) {
        	 System.out.println(detailTemplateModel.getPartId(editProductTemplatePartsView.getSelectedRow()));
        	 for(Part p : detailTemplateModel.getParts()){System.out.println(p.getIdNumber());}
            templatePartsModel.removeTemplatePart(templatePart.getProductTemplateId(), detailTemplateModel.getPartId(editProductTemplatePartsView.getSelectedRow()),
            		detailTemplateModel);
            System.out.println("after remove call");
         }
      }
      else if(actionCommand.equals("Cancel")) {
         this.editProductTemplatePartsView.closeWindow();
      }
   }
   
   /**
    * Watches for mouse clicks on the parts view
    */
   MouseListener editTemplatePartsMouseListener = new MouseAdapter() {
      boolean isAlreadyOneClick;
      @Override
      public void mouseClicked(MouseEvent e) {
         if(isAlreadyOneClick && (editProductTemplatePartsView.getSelectedRow() == editProductTemplatePartsView.getLastSelectedRow())) {
            @SuppressWarnings("unused")
            DetailPartController detailPartController = new DetailPartController
                  (partsModel, partsModel.getPart(editProductTemplatePartsView.getSelectedRow()));
            isAlreadyOneClick = false;
         }
         else {
            isAlreadyOneClick = true;
            Timer t = new Timer("doubleClickTimer", false);
            System.out.println("working");
            t.schedule(new TimerTask() {
               @Override
               public void run() {
                  isAlreadyOneClick = false;
               }
            }, 500);
         }
      }
   };
}
