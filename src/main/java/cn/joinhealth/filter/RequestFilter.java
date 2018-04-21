package cn.joinhealth.filter;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by shilei on 2018/4/16.
 */
@Provider
@PreMatching
public class RequestFilter implements ContainerRequestFilter {

    @Context
    private HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext creq) throws IOException{
        System.out.println("REQUSEST---------------------");
        System.out.println("<" + creq.getMethod() + ">---" + creq.getUriInfo().getAbsolutePath());
    }
}
