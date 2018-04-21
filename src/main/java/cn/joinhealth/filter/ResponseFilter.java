package cn.joinhealth.filter;


import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by shilei on 2018/4/18.
 */
@Provider
public class ResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException{
        System.out.println("RESPONSE-----------------------------");
        System.out.println(requestContext.getUriInfo().getAbsolutePath());
    }
}
