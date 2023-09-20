package com.journaldev.spring.controller;	
import java.io.ByteArrayOutputStream;
    import java.io.File;
    import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
    import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

    import org.apache.pdfbox.pdmodel.PDDocument;
    import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
    import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
    import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
    import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
    import org.apache.pdfbox.text.PDFTextStripper;
    import org.apache.pdfbox.text.TextPosition;

    public class PDFhighlight extends PDFTextStripper {
    	
    	public String text = "";
    	public String type = "";

        public PDFhighlight()  throws IOException {
            super();
        }
        
        public PDFhighlight(String textToHighlight, String type)  throws IOException {
        	super();
        	this.setText(textToHighlight);
        	this.setType(type);
        }

     /*   public static void main(String[] args)  throws IOException {
			
            PDDocument document = null;
            String fileName = "/Users/mqzhu/apache-tomcat-9.0.69/tmpFiles/saturdayprelimsdeepprogram_032348.pdf";
            try {
                document = PDDocument.load( new File(fileName) ); 
                PDFTextStripper stripper = new PDFhighlight();
                stripper.setSortByPosition( true );

                stripper.setStartPage( 0 );
                stripper.setEndPage( document.getNumberOfPages() );

                Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
                stripper.writeText(document, dummy);

                File file1 = new File("/Users/mqzhu/apache-tomcat-9.0.69/tmpFiles/FinalPDF.pdf");
                document.save(file1);
            }
            finally {
                if( document != null ) {
                    document.close();
                }
            }
        }
        */
        
        public ByteArrayOutputStream getFileWithHighlightedText(byte[] fileBytes, String highlightText, String type) throws InvalidPasswordException, IOException {
       	 PDDocument document = null;
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         
            try {
                document = PDDocument.load(fileBytes); 
                PDFTextStripper stripper = new PDFhighlight(highlightText, type);
                //PDFTextStripper stripper = new PDFhighlight();

                stripper.setSortByPosition( true );

                stripper.setStartPage( 0 );
                stripper.setEndPage( document.getNumberOfPages() );

                Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
                stripper.writeText(document, dummy);
                
                
                document.save(byteArrayOutputStream);
                
                
            }
            finally {
                if( document != null ) {
                    document.close();
                }
            }
            return byteArrayOutputStream;
       }
        
        public void highlightText(byte[] fileBytes, String highlightText, String type) throws InvalidPasswordException, IOException {
        	 PDDocument document = null;
             try {
                 document = PDDocument.load(fileBytes); 
                 PDFTextStripper stripper = new PDFhighlight(highlightText, type);
                 //PDFTextStripper stripper = new PDFhighlight();

                 stripper.setSortByPosition( true );

                 stripper.setStartPage( 0 );
                 stripper.setEndPage( document.getNumberOfPages() );

                 Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
                 stripper.writeText(document, dummy);
                 
                 String fileLocalPath = "/Users/mqzhu/apache-tomcat-9.0.69/tmpFiles/FinalPDF1.pdf";
                 
                 String saveToLocaFilePath = "/Users/mqzhu/apache-tomcat-9.0.69/tmpFiles/pdfHighlight.pdf";
                 
                 //save the processed pdf to local server fileLocalPath
                 File file1 = new File(fileLocalPath);
                 document.save(file1); 
                 
                 // Or save to outputStream and return the outputStream
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                 document.save(byteArrayOutputStream);
                 
                 //document.save(OutputStream output);
                 
                 //save the server pdf to local computer to saveToLocaFilePath
                 InputStream in = new File(fileLocalPath).toURI().toURL().openStream();
                 Files.copy(in, Paths.get(saveToLocaFilePath), StandardCopyOption.REPLACE_EXISTING);
             }
             finally {
                 if( document != null ) {
                     document.close();
                 }
             }
        }

        /**
         * Override the default functionality of PDFTextStripper.writeString()
         */

        @Override
        protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
            boolean isFound = false;

            float posXInit1  = 0, 
                    posXEnd1   = 0, 
                    posYInit1  = 0,
                    posYEnd1   = 0,
                    width1     = 0, 
                    height1    = 0, 
                    fontHeight1 = 0;

            //String[] criteria = {"KYA-KY"};
            String[] criteria = {this.getText()};

            for (int i = 0; i < criteria.length; i++) {
                if (string.contains(criteria[i])) {
                    isFound = true;
                } 
            }
            if (isFound) {

                

                  posXInit1 = textPositions.get(0).getXDirAdj(); 
                  posXEnd1  = textPositions.get(textPositions.size() - 1).getXDirAdj() + textPositions.get(textPositions.size() - 1).getWidth();
                  posYInit1 = textPositions.get(0).getPageHeight() - textPositions.get(0).getYDirAdj();
                  posYEnd1  = textPositions.get(0).getPageHeight() - textPositions.get(textPositions.size() - 1).getYDirAdj();
                  width1    = textPositions.get(0).getWidthDirAdj();
                  height1   = textPositions.get(0).getHeightDir();

               // highlight given last name
                  if ( this.getType().equalsIgnoreCase("name")) {
                	  posXInit1 -= 10;
                	  posXEnd1 += 110;
                  }

                  // hightlight given team name
                  if ( this.getType().equalsIgnoreCase("team")) {

                	  posXInit1 -= 105;
                	  posXEnd1 += 45;
                  }

                float quadPoints[] = {posXInit1, posYEnd1 + height1 + 2, posXEnd1, posYEnd1 + height1 + 2, posXInit1, posYInit1 - 2, posXEnd1, posYEnd1 - 2};

                List<PDAnnotation> annotations = document.getPage(this.getCurrentPageNo() - 1).getAnnotations();
                PDAnnotationTextMarkup highlight = new PDAnnotationTextMarkup(PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT);

                PDRectangle position = new PDRectangle();
                position.setLowerLeftX(posXInit1);
                position.setLowerLeftY(posYEnd1);
                position.setUpperRightX(posXEnd1);
                position.setUpperRightY(posYEnd1 + height1);

                highlight.setRectangle(position);

                // quadPoints is array of x,y coordinates in Z-like order (top-left, top-right, bottom-left,bottom-right) 
                // of the area to be highlighted

                highlight.setQuadPoints(quadPoints);

                PDColor yellow = new PDColor(new float[]{1, 1, 1 / 255F}, PDDeviceRGB.INSTANCE);
                highlight.setColor(yellow);
                annotations.add(highlight);
            }
        }

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

    }