package jsf.course.utils;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class MessageBB {
	
	@Inject
	FacesContext fcscontext;
	
	@Inject
	Flash flash;
	
	public void preRenderMsg() {
		
		if(flash.containsKey("message")) {
			String message = (String) flash.get("message");
			fcscontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
		}
	}
	
}
