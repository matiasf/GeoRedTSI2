package org.jboss.tools.example.richfaces.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.tools.example.richfaces.model.Empresa;
import org.jboss.tools.example.richfaces.model.Member;
import org.jboss.tools.example.richfaces.service.MemberRegistration;
import org.richfaces.cdi.push.Push;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class EmpresaController {

   public static final String PUSH_CDI_TOPIC = "pushCdi";

   @Inject
   private FacesContext facesContext;

   @Inject
   private MemberRegistration memberRegistration;

   @Inject
   @Push(topic = PUSH_CDI_TOPIC) Event<String> pushEvent;

   private Empresa newEmpresa;
   private Empresa empresa;

   @Produces
   @Named
   public Empresa getNewEmpresa() {
      return newEmpresa;
   }

   @Produces
   @Named
   public Empresa getEmpresa() {
      return empresa;
   }

   public void setMember(Empresa empresa) {
      this.empresa = empresa;
   }

   public String altaEmpresa() throws Exception {
      //memberRegistration.register(newMember);
      //facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful"));
      //pushEvent.fire(String.format("New member added: %s (id: %d)", newMember.getName(), newMember.getId()));
      //initNewMember();
      return "login";
   }

   @PostConstruct
   public void initNewEmpresa() {
      empresa = new Empresa();
   }
}
