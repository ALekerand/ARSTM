/*
 * package com.ARSTM.etats;
 * 
 * import java.io.ByteArrayOutputStream; import java.io.File; import
 * java.io.FileNotFoundException; import java.io.FileOutputStream; import
 * java.io.IOException; import java.io.OutputStream; import
 * java.io.Serializable; import java.net.MalformedURLException; import
 * java.net.URL; import java.util.ArrayList; import java.util.List;
 * 
 * import javax.faces.context.FacesContext; import
 * javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Scope; import
 * org.springframework.jms.IllegalStateException; import
 * org.springframework.stereotype.Component;
 * 
 * import com.ARSTM.managedBean.EtatMatiereBean; import
 * com.ARSTM.managedBean.MatiereBean; import com.ARSTM.model.Rattacher; import
 * com.ARSTM.service.Iservice; import com.itextpdf.text.BaseColor; import
 * com.itextpdf.text.Document; import com.itextpdf.text.DocumentException;
 * import com.itextpdf.text.Element; import com.itextpdf.text.Font; import
 * com.itextpdf.text.Image; import com.itextpdf.text.PageSize; import
 * com.itextpdf.text.Paragraph; import com.itextpdf.text.Phrase; import
 * com.itextpdf.text.Rectangle; import com.itextpdf.text.pdf.PdfPCell; import
 * com.itextpdf.text.pdf.PdfPTable; import com.itextpdf.text.pdf.PdfWriter;
 * import com.lowagie.text.Cell;
 * 
 * @Component
 * 
 * @Scope("request") public class EtatMatierebySection implements Serializable {
 *//**
	* 
	*/
/*
 * private static final long serialVersionUID = 1L; private File repectoire;
 * private String nomFichier;
 * 
 * @Autowired private Iservice service;
 * 
 * @Autowired private EtatMatiereBean etatMatiereBean;
 * 
 * 
 * private static Font normalText = new Font(Font.FontFamily.TIMES_ROMAN, 8,
 * Font.NORMAL);
 * 
 * private static Font normalTextGras = new Font(Font.FontFamily.TIMES_ROMAN, 8,
 * Font.BOLD);
 * 
 * private static Font normalTitle = new Font(Font.FontFamily.TIMES_ROMAN, 9,
 * Font.BOLD);
 * 
 * private static Font smallText = new Font(Font.FontFamily.COURIER, 6,
 * Font.NORMAL);
 * 
 * public static final String logoRessource1 =
 * "http://localhost:8080/GIVAC/resource/images/logo_artm.jpg";
 * 
 * 
 * 
 * // Méthode générales avec ouverture dans le navigateur public void
 * genererFiche() throws IOException { // Créer le dosier de stockage des
 * fichier générés repectoire = new File("c:/GIVAC_RAPPORTS");
 * repectoire.mkdirs();
 * 
 * 
 * 
 * HttpServletResponse response = (HttpServletResponse)
 * FacesContext.getCurrentInstance().getExternalContext() .getResponse();
 * HttpServletRequest request = (HttpServletRequest)
 * FacesContext.getCurrentInstance().getExternalContext() .getRequest(); try {
 * Document document = new Document(PageSize.A4, 1, 1, 30, 1); nomFichier =
 * "rapport.pdf"; PdfWriter.getInstance(document, new
 * FileOutputStream(repectoire + "/" + nomFichier));
 * 
 * // step 2 ByteArrayOutputStream baos = new ByteArrayOutputStream();
 * PdfWriter.getInstance(document, baos); PdfWriter.getInstance(document, new
 * FileOutputStream(repectoire + "/" + nomFichier));
 * 
 * document.open(); addContent(document); document.close();
 * 
 * // setting some response headers response.setHeader("Expires", "0");
 * response.setHeader("Cache-Control",
 * "must-revalidate, post-check=0, pre-check=0"); //
 * response.setHeader("Pragma", "public"); // setting the content // type
 * response.setContentType("application/pdf"); // the contentlength
 * response.setContentLength(baos.size()); // write // ByteArrayOutputStream to
 * // the ServletOutputStream OutputStream os = response.getOutputStream();
 * baos.writeTo(os); os.flush(); os.close();
 * 
 * // ouvrirFicher(); } catch (FileNotFoundException e) { // TODO Auto-generated
 * catch block System.out.println("Erreur1"); e.printStackTrace(); } catch
 * (DocumentException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); System.out.println("Erreur2");
 * 
 * } catch (IllegalStateException e) { System.out.
 * println("getOutputStream() a déjà été appelé pour cette réponse. lol c'est une erreure que je dois gérer. ALekerand"
 * ); } }
 * 
 * 
 * 
 * private void addContent(Document document) throws DocumentException,
 * MalformedURLException, IOException { creerLogo(document);
 * createEteteDoc(document); informationRapport(document);
 * chargerMatieres(document); }
 * 
 *//*******************
	 * Les méthodes qui suivent sont toutes appélées dans la méthode générales
	 *******************//*
							 * 
							 * public void creerLogo(Document document) throws MalformedURLException,
							 * IOException, DocumentException { PdfPTable tableauLogo = new PdfPTable(2);
							 * PdfPCell cell;
							 * 
							 * // Logo ARSTM Image logoARSTM = Image.getInstance(new URL(logoRessource1));
							 * // logoARSTM.scalePercent(50f); cell = new PdfPCell(logoARSTM); // cell = new
							 * PdfPCell(new Phrase("LOGO ICI")); cell.setBorder(Rectangle.NO_BORDER);
							 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); tableauLogo.addCell(cell);
							 * 
							 * // Logo PSP //Image logoPSP = Image.getInstance(new URL(logoRessource2)); //
							 * logoPSP.scalePercent(20f); cell = new PdfPCell(new Phrase("Ann�e Scolaire: "
							 * +etatMatiereBean.getAnneesScolaire().getLibAnneeScolaire(), normalTextGras));
							 * cell.setBorder(Rectangle.NO_BORDER);
							 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT); tableauLogo.addCell(cell);
							 * tableauLogo.setSpacingAfter(20); document.add(tableauLogo); }
							 * 
							 * public void createEteteDoc(Document document) throws DocumentException {
							 * String titredocement = "ETAT DES MATIERES PAR SECTION"; PdfPTable tabEntete =
							 * new PdfPTable(1); tabEntete.setWidthPercentage(40);
							 * 
							 * PdfPCell cell = new PdfPCell(new Phrase(titredocement, normalTitle));
							 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							 * cell.setVerticalAlignment(Element.ALIGN_CENTER); tabEntete.addCell(cell);
							 * tabEntete.setSpacingAfter(10); document.add(tabEntete); }
							 * 
							 * private void informationRapport(Document document) throws DocumentException {
							 * 
							 * // Creation du tableau général PdfPTable tableauGrand = new PdfPTable(5);
							 * //tableauGrand.setWidthPercentage(100);
							 * 
							 * tableauGrand.setWidths(new int[] { 28, 8, 28, 8, 28});
							 * tableauGrand.getDefaultCell().setBorder(Cell.NO_BORDER); PdfPCell cell;
							 * PdfPCell cellSeparateur = new PdfPCell();
							 * cellSeparateur.setBorder(Rectangle.NO_BORDER);
							 * 
							 * // colonne1 PdfPTable tableBloc1 = new PdfPTable(2);
							 * tableBloc1.getDefaultCell().setBorder(Cell.NO_BORDER); tableBloc1.addCell(new
							 * Phrase("ECOLE: ", normalTextGras)); cell = new PdfPCell(new
							 * Phrase(etatMatiereBean.getChoosedEcole().getNomEcole(), normalText));
							 * cell.setBorder(Rectangle.NO_BORDER);
							 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); tableBloc1.addCell(cell);
							 * 
							 * tableauGrand.addCell(tableBloc1); tableauGrand.addCell(cellSeparateur);
							 * 
							 * // Colonne2 PdfPTable tableBloc2 = new PdfPTable(2);
							 * tableBloc2.getDefaultCell().setBorder(Cell.NO_BORDER); tableBloc2.addCell(new
							 * Phrase("FILIERE:", normalTextGras)); cell = new PdfPCell(new
							 * Phrase(etatMatiereBean.getChoosedFiliere().getNomFiliere(), normalText));
							 * cell.setBorder(Rectangle.NO_BORDER);
							 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); tableBloc2.addCell(cell);
							 * 
							 * tableauGrand.addCell(tableBloc2); tableauGrand.addCell(cellSeparateur);
							 * 
							 * // Colonne 3 PdfPTable tableBloc3 = new PdfPTable(2);
							 * tableBloc3.getDefaultCell().setBorder(Cell.NO_BORDER); tableBloc3.addCell(new
							 * Phrase("SECTION:", normalTextGras)); cell = new PdfPCell(new
							 * Phrase(etatMatiereBean.getChoosedSection().getNomSection(), normalText));
							 * cell.setBorder(Rectangle.NO_BORDER);
							 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); tableBloc3.addCell(cell);
							 * 
							 * tableauGrand.addCell(tableBloc3);
							 * 
							 * // Prise en compte du du tableau tableauGrand.setSpacingAfter(20);
							 * document.add(tableauGrand); }
							 * 
							 * public void chargerMatieres(Document document) throws DocumentException {
							 * 
							 * //Mettre le titre du tableau ici Paragraph titreTablo = new Paragraph(new
							 * Phrase("LISTE ET DETAILS DES MATIERES",normalTitle));
							 * titreTablo.setAlignment(Element.ALIGN_CENTER); titreTablo.setSpacingAfter(5);
							 * document.add(titreTablo);
							 * 
							 * PdfPTable tableauMatiere = new PdfPTable(5); PdfPCell cell;
							 * 
							 * //Ent�te 1eme colonne cell = new PdfPCell(new Phrase("CODE", normalTitle));
							 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							 * cell.setBackgroundColor(BaseColor.LIGHT_GRAY); tableauMatiere.addCell(cell);
							 * 
							 * //Ent�te 2eme colonne cell = new PdfPCell(new Phrase("MATIERE",
							 * normalTitle)); cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							 * cell.setBackgroundColor(BaseColor.LIGHT_GRAY); tableauMatiere.addCell(cell);
							 * 
							 * //Ent�te 3eme colonne cell = new PdfPCell(new Phrase("ABREVIATION",
							 * normalTitle)); cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							 * cell.setBackgroundColor(BaseColor.LIGHT_GRAY); tableauMatiere.addCell(cell);
							 * 
							 * //Ent�te 4eme colonne cell = new PdfPCell(new Phrase("COEF", normalTitle));
							 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							 * cell.setBackgroundColor(BaseColor.LIGHT_GRAY); tableauMatiere.addCell(cell);
							 * 
							 * //Ent�te 5eme colonne cell = new PdfPCell(new Phrase("VOL. Horaire(h)",
							 * normalTitle)); cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							 * cell.setBackgroundColor(BaseColor.LIGHT_GRAY); tableauMatiere.addCell(cell);
							 * 
							 * //Ajouter d'�l�ments dans le tableau for (Rattacher varRatach :
							 * etatMatiereBean.getListeRattacherSection()) { // colonne1 cell = new
							 * PdfPCell(new Phrase(varRatach.getCodeMatLmd(), normalText));
							 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							 * tableauMatiere.addCell(cell);
							 * 
							 * // colonne2 cell = new PdfPCell(new
							 * Phrase(varRatach.getMatiere().getLibMatiere(), normalText));
							 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							 * tableauMatiere.addCell(cell);
							 * 
							 * // colonne3 cell = new PdfPCell(new
							 * Phrase(varRatach.getMatiere().getAbrevMatiere(), normalText));
							 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							 * tableauMatiere.addCell(cell);
							 * 
							 * // colonne4 cell = new PdfPCell(new Phrase(""+varRatach.getCoefMatiere(),
							 * normalText)); cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							 * tableauMatiere.addCell(cell);
							 * 
							 * // colonne5 cell = new PdfPCell(new Phrase(""+varRatach.getVolumeHoraire(),
							 * normalText)); cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							 * tableauMatiere.addCell(cell); }
							 * 
							 * //Fin du tableau on y ajoute les totaux cell = new PdfPCell(new Phrase(""));
							 * cell.setColspan(2); cell.setBorder(Rectangle.NO_BORDER);
							 * tableauMatiere.addCell(cell);
							 * 
							 * cell = new PdfPCell(new Phrase("TOTAL", normalTextGras));
							 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							 * cell.setBorder(Rectangle.NO_BORDER); tableauMatiere.addCell(cell);
							 * 
							 * //Total coefficient cell = new PdfPCell(new
							 * Phrase(""+etatMatiereBean.getCoefTotal(), normalTitle));
							 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							 * cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
							 * tableauMatiere.addCell(cell);
							 * 
							 * //Total Volume Horaire cell = new PdfPCell(new
							 * Phrase(""+etatMatiereBean.getVhTotal(), normalTitle));
							 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							 * cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
							 * tableauMatiere.addCell(cell);
							 * 
							 * document.add(tableauMatiere); //document.add(tabVHT); }
							 * 
							 * //*********************ACCESSEURS ICI**********************
							 * 
							 * public Iservice getService() { return service; }
							 * 
							 * public void setService(Iservice service) { this.service = service; }
							 * 
							 * 
							 * 
							 * public EtatMatiereBean getEtatMatiereBean() { return etatMatiereBean; }
							 * 
							 * public void setEtatMatiereBean(EtatMatiereBean etatMatiereBean) {
							 * this.etatMatiereBean = etatMatiereBean; }
							 * 
							 * 
							 * }
							 */