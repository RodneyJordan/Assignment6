package controllers;

import models.PartsModel;
import models.ProductTemplate;
import models.ProductTemplateParts;
import models.ProductTemplatePartsModel;
import models.DetailTemplateModel;
import models.TemplateModel;
import views.DetailProductTemplatePartsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller for the detailed template view
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public class DetailProductTemplatePartsController implements ActionListener {
   
   /**
    * The detail template view
    */
   private DetailProductTemplatePartsView detailProductTemplatePartsView;
   
   /**
    * The template
    */
   private ProductTemplate template;
   
   /**
    * Product Template Parts Model
    */
   private ProductTemplatePartsModel productTemplatePartsModel;
   
   /**
    * Product Template Part
    */
   private ProductTemplateParts productTemplatePart;
   
   /**
    * Parts model
    */
   private PartsModel partsModel;
   
   /**
    * Template model
    */
   TemplateModel templateModel;
   
   /**
    * Detail Template Model
    */
   DetailTemplateModel detailTemplateModel;
   
   /**
    * List of part id numbers
    */
   private ArrayList<Integer> partIds = new ArrayList<Integer>();
   private ArrayList<Integer> quantity = new ArrayList<Integer>();
   
   /**
    * int for iteration through for loop
    */
   private int i;
   
   /**
    * Constructor
    */
   public DetailProductTemplatePartsController(ProductTemplate template, PartsModel partsModel,
         ProductTemplatePartsModel productTemplatePartsModel, TemplateModel templateModel) {
      this.template = template;
      this.templateModel = templateModel;
      this.partsModel = partsModel;
      this.productTemplatePartsModel = productTemplatePartsModel; 
      for(i = 0; i < this.productTemplatePartsModel.getList().size(); i++) {
         if(this.productTemplatePartsModel.getTemplatePart(i).getProductTemplateId() == (this.template.getId())) {
            this.partIds.add(this.productTemplatePartsModel.getTemplatePart(i).getPartId());
            this.quantity.add(this.productTemplatePartsModel.getTemplatePart(i).getQuantity());
            this.productTemplatePart = this.productTemplatePartsModel.getTemplatePart(i);
         }
      }
      this.detailTemplateModel = new DetailTemplateModel(template.getId(), productTemplatePartsModel, this.partsModel, this.partIds, this.quantity);
      detailProductTemplatePartsView = new DetailProductTemplatePartsView(this.template, detailTemplateModel, productTemplatePartsModel, templatePartsMouseListener);
      this.detailProductTemplatePartsView.registerListeners(this);
   }
   
   /**
    * Watches for buttons pressed on the detail template view
    * @param e : button that has been pressed
    */
   public void actionPerformed(ActionEvent e) {
      String actionCommand = e.getActionCommand();
      
      if(actionCommand.equals("Add Part")) {
         @SuppressWarnings("unused")
         AddPartToTemplatePartController addPartToTemplatePartController = 
               new AddPartToTemplatePartController(partsModel, productTemplatePartsModel, productTemplatePart, detailTemplateModel);
         
      }
      else if(actionCommand.equals("Edit")) {
         this.detailProductTemplatePartsView.closeWindow();
         EditProductTemplatePartsController editProductTemplatePartsController = new EditProductTemplatePartsController(templateModel, template,
        		 detailTemplateModel, productTemplatePartsModel, productTemplatePart, partsModel);
      }
      else if(actionCommand.equals("Cancel")) {
         this.detailProductTemplatePartsView.closeWindow();
      }
   }
   
   /**
    * Watches for mouse clicks on the parts view
    */
   MouseListener templatePartsMouseListener = new MouseAdapter() {
      boolean isAlreadyOneClick;
      @Override
      public void mouseClicked(MouseEvent e) {
         if(isAlreadyOneClick && (detailProductTemplatePartsView.getSelectedRow() == detailProductTemplatePartsView.getLastSelectedRow())) {
            @SuppressWarnings("unused")
            DetailPartController detailPartController = new DetailPartController
                  (partsModel, partsModel.getPart(detailProductTemplatePartsView.getSelectedRow()));
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
