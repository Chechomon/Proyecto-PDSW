package com.BoomECI.controlador.managedbeans;

import com.BoomECI.entidades.Estudiante;
import com.BoomECI.entidades.Grafo;
import com.BoomECI.entidades.Materia;
import com.BoomECI.entidades.PlanDeEstudios;
import com.BoomECI.entidades.SolicitudCancelacion;
import com.BoomECI.javamail.core.Email;
import com.BoomECI.javamail.core.EmailConfiguration;
import com.BoomECI.javamail.core.EmailSender;
import com.BoomECI.javamail.core.SimpleEmail;
import com.BoomECI.javamail.core.SimpleEmailSender;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import com.BoomECI.logica.services.ExcepcionServiciosCancelaciones;
import com.BoomECI.logica.services.ParserGrafo;
import com.BoomECI.logica.services.ServiciosCancelaciones;
import com.BoomECI.logica.services.ServiciosCancelacionesFactory;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.BoomECI.seguridad.bean.ShiroLoginBean;
import java.util.logging.Logger;
import javax.faces.bean.ManagedProperty;
import javax.mail.MessagingException;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;



@ManagedBean(name="beanSolicitudEstudiante")
@SessionScoped
public class EstudianteBean implements Serializable{
    
    @ManagedProperty(value = "#{loginBean}")
    private ShiroLoginBean seguridad;
    
    private final ServiciosCancelaciones servCanc = ServiciosCancelacionesFactory.getInstance().getServiciosCancelaciones();
    private Date fechaCancelacion;                         
    private List<SolicitudCancelacion> solicitudes;
    private PlanDeEstudios planDeEstudios;
    private List<Materia> materiasCursadas;
    private List<Materia> materiasActuales;
    private Estudiante estudianteActual;
    private List<Materia> materiasSeleccionadas;
    private String[] justificacionesCancelacion;
    private int creditosRestantes;
    private List<SolicitudCancelacion> solicitudesEstudiante;
    private TabView tablaMaterias;
    private int creditosCarrera;
    private DualListModel<String> materias;
    private List<String> materiasActualesString;
    private List<String> materiasSeleccionadasString;
    private List<List<String>> proyeccion;
    private int anoGraduacion;
    private TreeNode root;
    private int ciclo;
    private Grafo grafo;
    
    private static final Logger LOG = Logger.getLogger(EstudianteBean.class.getName());
    
    
    public EstudianteBean() throws ExcepcionServiciosCancelaciones{
        
        fechaCancelacion = new Date();
        
        
            
    }
    

    

    
    
    
    public List<Materia> conversorStringToSeleccionadas(){
        List<Materia> mt = new ArrayList<>();
        List<String> matSelec = getMaterias().getTarget();
        for(String i: matSelec){
            mt.add(grafo.getMateria(i));
        }
        return mt;
    }
    
    public List<String> conversorActualesToString(){
        List<String> mt = new ArrayList<>();
        
        for(int i = 0; i<materiasActuales.size(); i++){
            mt.add(materiasActuales.get(i).getNemonico());
        }
       
        return mt;
    }
    
    
    
    public List<String> conversorSeleccionadasToString(){
        List<String> mt = new ArrayList<>();
        
        for(int i = 0; i<materiasSeleccionadas.size(); i++){
            mt.add(materiasSeleccionadas.get(i).getNemonico());
        }
       
        return mt;
    }

    public List<SolicitudCancelacion> getSolicitudesEstudiante() {
        return solicitudesEstudiante;
    }

    public void setSolicitudesEstudiante(List<SolicitudCancelacion> solicitudesEstudiante) {
        this.solicitudesEstudiante = solicitudesEstudiante;
    }

    public DualListModel<String> getMaterias() {
        return materias;
    }

    public void setMaterias(DualListModel<String> materias) {
        this.materias = materias;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }
    
    

    public int getAnoGraduacion() {
        return anoGraduacion;
    }

    public void setAnoGraduacion(int anoGraduacion) {
        this.anoGraduacion = anoGraduacion;
    }
    
    

    public List<String> getMateriasActualesString() {
        return materiasActualesString;
    }

    public void setMateriasActualesString(List<String> materiasActualesString) {
        this.materiasActualesString = materiasActualesString;
    }

    public List<List<String>> getProyeccion() {
        return proyeccion;
    }

    public void setProyeccion(List<List<String>> proyeccion) {
        this.proyeccion = proyeccion;
    }
    
    

    public List<String> getMateriasSeleccionadasString() {
        return materiasSeleccionadasString;
    }

    public void setMateriasSeleccionadasString(List<String> materiasSeleccionadasString) {
        this.materiasSeleccionadasString = materiasSeleccionadasString;
    }
    
    
    
    public ServiciosCancelaciones getServCanc(){
        return servCanc;
    }

    public int getCreditosRestantes() {
        return creditosRestantes;
    }
    public ShiroLoginBean getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(ShiroLoginBean seguridad) {
        this.seguridad = seguridad;
    }
    public void setCreditosRestantes(int creditosRestantes) {
        this.creditosRestantes = creditosRestantes;
    }
    
    
    public Estudiante getEstudianteActual() throws ExcepcionServiciosCancelaciones {
        if(estudianteActual==null){
            estudianteActual= servCanc.consultarEstudiante(Integer.parseInt(seguridad.getUsername()));
            materiasCursadas = estudianteActual.getMateriasCursadas();
            materiasActuales = estudianteActual.getMateriasActuales();
            planDeEstudios = estudianteActual.getPlanDeEstudios();
            solicitudesEstudiante = estudianteActual.getSolicitudes();
            creditosCarrera = estudianteActual.getPlanDeEstudios().getNumeroDeCreditosTotales();
            tablaMaterias = new TabView();
            materiasActualesString = new ArrayList<>();
            materiasSeleccionadasString = new ArrayList<>();
            materiasActualesString = conversorActualesToString();
            materias = new DualListModel<>(materiasActualesString, materiasSeleccionadasString);
            solicitudes = servCanc.consultarTodasLasSolicitudesCancelacion();
        }
        return estudianteActual;
    }

    public void setEstudianteActual(Estudiante estudianteActual) {
        this.estudianteActual = estudianteActual;
    }

    public int getCreditosCarrera() {
        return creditosCarrera;
    }

    public void setCreditosCarrera(int creditosCarrera) {
        this.creditosCarrera = creditosCarrera;
    }
    

    public List<Materia> getMateriasSeleccionadas() {
        return materiasSeleccionadas;
    }

    public void setMateriasSeleccionadas(List<Materia> materiasSeleccionadas) {
        this.materiasSeleccionadas = materiasSeleccionadas;
    }

    public Grafo getGrafo() {
        return grafo;
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    }

    
    public List<Materia> getMateriasActuales(){
        return materiasActuales;
    }

    public void setMateriasActuales(List<Materia> materiasActuales) {
        this.materiasActuales = materiasActuales;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public String[] getJustificacionesCancelacion() {
        return justificacionesCancelacion;
    }

    public void setJustificacionesCancelacion(String[] justificacionesCancelacion) {
        this.justificacionesCancelacion = justificacionesCancelacion;
    }
    
   public void cancelarMaterias() throws ExcepcionServiciosCancelaciones, MessagingException{
       for(int i=0;i<justificacionesCancelacion.length;i++){
           InputTextarea m = (InputTextarea) tablaMaterias.getChildren().get(i).findComponent("input"+i);
           justificacionesCancelacion[i] = (String) m.getValue();
           solicitudesEstudiante.get(i).setJustificacion(justificacionesCancelacion[i]);
           solicitudes.add(solicitudesEstudiante.get(i));
           servCanc.agregarSolicitudCancelacionEstudiante(solicitudesEstudiante.get(i));
       }
       
       Email email = new SimpleEmail(estudianteActual.getCorreo(), estudianteActual.getConsejero().getCorreo(), "SOLICITUD DE CANCELACION ACONSEJADO", "Buen dia profesor "+estudianteActual.getConsejero().getNombre()+ ", la presente es para informarle"
                                                                                                                 + " que voy a realizar un proceso de cancelacion, espero pronta respuesta para acordar la reunion estipulada por el reglamento."
               + "\n\nCordialmente,\n"+estudianteActual.getNombre()+"\n"+estudianteActual.getCodigo()+"\n"+estudianteActual.getIdentificacion());
        EmailSender sender = new SimpleEmailSender(new EmailConfiguration());
        try {
            sender.send(email);
            System.out.println("Sent message successfully!");
        } catch (MessagingException e) {
            System.err.println("Message not sent!");
            e.printStackTrace();
        }
       
   }
    
    public void analizarSolicitud() throws ExcepcionServiciosCancelaciones{
       ParserGrafo p = ServiciosCancelacionesFactory.getInstance().getParserGrafo();
       grafo = p.convertStringToGrafo(planDeEstudios.getGrafo());
       tablaMaterias = new TabView();
       tablaMaterias.setId("myTabPanel");
       materiasSeleccionadasString = getMaterias().getTarget();
       materiasSeleccionadas = conversorStringToSeleccionadas();
       justificacionesCancelacion = new String[materiasSeleccionadasString.size()];
       for(int i = 0; i < materiasSeleccionadasString.size(); i++){
           Tab tab = new Tab();
           tab.setTitle(materiasSeleccionadasString.get(i));
           InputTextarea ita = new InputTextarea();
           ita.setId("input"+i);
           ita.setValue(justificacionesCancelacion[i]);
           ita.setMaxlength(400);
           ita.setCounterTemplate("{0} restantes.");
           ita.setTitle("¿Por qué cancelar esta asignatura?");
           ita.setRows(6);
           ita.setCols(40);
           ita.setAutoResize(true);
           ita.setCounter("display"+i);
           ita.setStyle("font-size: 80%; font-weight:normal; font-style: Tahoma;");
           OutputLabel ol1 = new OutputLabel();
           ol1.setValue("Justificacion:            ");
           ol1.setStyle("font-style: Tahoma;font-size: 80%; font-weight:bold ; margin-rigth: 100% ;");
           OutputLabel ol = new OutputLabel();
           ol.setId("display"+i);
           ol.setStyle("font-style: Tahoma; font-size: 70%");
           tab.getChildren().add(ol1);
           tab.getChildren().add(ita);
           tab.getChildren().add(ol);
           tablaMaterias.getChildren().add(tab);
       }
       RequestContext context = RequestContext.getCurrentInstance();
       Boolean esMenor = grafo.getSemestre(estudianteActual) <3? false: null;
       
       solicitudesEstudiante = new ArrayList();
       for(int i=0; i < materiasSeleccionadasString.size(); i++){
           solicitudesEstudiante.add(new SolicitudCancelacion(fechaCancelacion,"Pendiente", solicitudes.size()+(i+2), null, "", esMenor , false, materiasSeleccionadasString.get(i), estudianteActual.getCodigo()));
       }
       
       
       
       creditosRestantes = servCanc.consultarImpacto(materiasSeleccionadasString, estudianteActual, grafo);
       proyeccion = servCanc.calcularProyeccion(estudianteActual, materiasSeleccionadasString, grafo);
       
       root = new DefaultTreeNode("Proyeccion", null);
       for(int i=0; i<proyeccion.size(); i++){
           TreeNode semestre = null;
           if (i==0){
               semestre = new DefaultTreeNode("Para el próximo semestre:", root);
           }
           else{
               semestre = new DefaultTreeNode("Para dentro de "+(i+1)+" Semestres", root);
           }
           for(int j=0; j<proyeccion.get(i).size(); j++){
               TreeNode materia = new DefaultTreeNode(proyeccion.get(i).get(j), semestre);
           }
       }
       anoGraduacion = (fechaCancelacion.getYear()+1900) + (int)(proyeccion.size()/2);
       ciclo = proyeccion.size()/2;
       if ((ciclo*2)==proyeccion.size()){
           ciclo=2;
           
       }
       else{
           ciclo=1;
           anoGraduacion+=1;
       }
       
       
    }

    public PlanDeEstudios getPlanDeEstudios() {
        return planDeEstudios;
    }

    public void setPlanDeEstudios(PlanDeEstudios planDeEstudios) {
        this.planDeEstudios = planDeEstudios;
    }

    public List<Materia> getMateriasCursadas() {
        return materiasCursadas;
    }

    public void setMateriasCursadas(List<Materia> materiasCursadas) {
        this.materiasCursadas = materiasCursadas;
    }

    public TabView getTablaMaterias() {
        return tablaMaterias;
    }

    public void setTablaMaterias(TabView tablaMaterias) {
        this.tablaMaterias = tablaMaterias;
    }
    
    

    public List<SolicitudCancelacion> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudCancelacion> solicitudes) {
        this.solicitudes = solicitudes;
    }
    
    
    public String finalizar(){
        return "esperarsolicitud.xhtml";
    }
    
    public String irAtras(){
        return "serviciocancelaciones.xhtml";
    }

}
