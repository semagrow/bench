package org.semanticweb.fbench.provider;

import org.openrdf.model.Graph;
import org.openrdf.model.Resource;
import org.openrdf.repository.Repository;

/**
 * Interface for all Sesame repository providers
 * 
 * @author mz, as
 */
public interface RepositoryProvider {

	Repository load(Graph graph, Resource repNode) throws Exception;
	
	String getLocation(Graph graph, Resource repNode);
	
	String getId(Graph graph, Resource repNode);
}
