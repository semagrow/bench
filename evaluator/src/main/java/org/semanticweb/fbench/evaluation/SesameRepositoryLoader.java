package org.semanticweb.fbench.evaluation;


import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openrdf.model.Graph;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.model.impl.GraphImpl;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.sail.federation.Federation;
import org.semanticweb.fbench.Config;
import org.semanticweb.fbench.provider.MemoryStoreProvider;
import org.semanticweb.fbench.provider.NativeStoreFiller;
import org.semanticweb.fbench.provider.NativeStoreRepository;
import org.semanticweb.fbench.provider.RepositoryProvider;
import org.semanticweb.fbench.provider.SPARQLProvider;
import org.semanticweb.fbench.provider.SingleNativeRepository;
import org.semanticweb.fbench.report.ReportStream;




/**
 * Load sesame SailRepository instances based on the dataConfig. See demos for examples.
 * 
 * @author mz, as
 */
public class SesameRepositoryLoader {

	public static Logger log = LoggerFactory.getLogger(SesameRepositoryLoader.class);
	
//	public static SailRepository loadRepositories(ReportStream report) throws Exception {
	public static Repository loadRepositories(ReportStream report) throws Exception {
		Federation fed = new Federation();
		final Graph graph = new GraphImpl();
		final String dataConfig = Config.getConfig().getDataConfig();
		RDFParser parser = Rio.createParser(RDFFormat.N3);
		RDFHandler handler = new RDFHandler(){


			public void endRDF() throws RDFHandlerException {						
			}


			public void handleComment(String arg0){
			}


			public void handleNamespace(String arg0, String arg1){
			}


			public void handleStatement(Statement arg0)
					throws RDFHandlerException {
				graph.add(arg0);
			}


			public void startRDF() throws RDFHandlerException {
			}
			
		};
		
		parser.setRDFHandler(handler);
		
		parser.parse(new FileReader(dataConfig), "http://fluidops.org/config#");
		Iterator<Statement> iter = graph.match(null, new URIImpl("http://fluidops.org/config#store"), null);
		Statement s;
		Resource repNode;
		Value repType;
		List<Repository> repositories = new LinkedList<Repository>();
		
		while (iter.hasNext()){
			s = iter.next();
			repNode = s.getSubject();
			repType = s.getObject();
			
			// special cases: no federation needed, just a single local store
			if (repType.equals(new LiteralImpl("SingleNative"))){
				long datasetLoadStart = System.currentTimeMillis();
				SingleNativeRepository rep = new SingleNativeRepository();
				Repository res = rep.load(graph, repNode);
				long datasetLoadDuration = System.currentTimeMillis()-datasetLoadStart;
				report.reportDatasetLoadTime(rep.getId(graph, repNode), repNode.stringValue(), rep.getLocation(graph, repNode), repType.stringValue(), datasetLoadDuration);
				return res;
			}
			if (repType.equals(new LiteralImpl("SingleBigOWLim"))){
				long datasetLoadStart = System.currentTimeMillis();
				RepositoryProvider rep;
				try {
					Class<?> bigOwlimClass = Class.forName("org.semanticweb.fbench.provider.SingleBigOWLimRepository");
					rep = (RepositoryProvider)bigOwlimClass.newInstance();
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("ClassNotFoundException 'org.semanticweb.fbench.provider.SingleBigOWLimRepository': probably fbench-bigowlim-ext.jar is missing on the classpath. See documentation for further information." );
				}
				Repository res = rep.load(graph, repNode);
				long datasetLoadDuration = System.currentTimeMillis()-datasetLoadStart;
				report.reportDatasetLoadTime(rep.getId(graph, repNode), repNode.stringValue(), rep.getLocation(graph, repNode), repType.stringValue(), datasetLoadDuration);
				return res;
			}
			
			// load the respective repository into the federation
			Repository rep = loadRepository(graph, repNode, repType, report);
			repositories.add(rep);
		}
		
		if (repositories.size() == 1)
			return repositories.iterator().next();
		else {
			for (Repository rep : repositories)
				fed.addMember(rep);
		}
		
		return new SailRepository(fed);
	}
	
	private static Repository loadRepository(Graph graph, Resource repNode, Value repType, ReportStream report) throws Exception {
		RepositoryProvider repProvider;
		long datasetLoadStart = System.currentTimeMillis();
		if (repType.equals(new LiteralImpl("Native"))){
			repProvider = new NativeStoreFiller();
		}
		else if (repType.equals(new LiteralImpl("BigOWLim"))){
			try {
				Class<?> bigOwlimClass = Class.forName("org.semanticweb.fbench.provider.BigOwlimStoreFiller");
				repProvider = (RepositoryProvider)bigOwlimClass.newInstance();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("ClassNotFoundException 'org.semanticweb.fbench.provider.BigOwlimStoreFiller': probably fbench-bigowlim-ext.jar is missing on the classpath. See documentation for further information." );
			}
		}
		else if (repType.equals(new LiteralImpl("BigOWLimRepo"))){
			try {
				Class<?> bigOwlimClass = Class.forName("org.semanticweb.fbench.provider.BigOwlimRepository");
				repProvider = (RepositoryProvider)bigOwlimClass.newInstance();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("ClassNotFoundException 'org.semanticweb.fbench.provider.BigOwlimRepository': probably fbench-bigowlim-ext.jar is missing on the classpath. See documentation for further information." );
			}
		}
		else if (repType.equals(new LiteralImpl("NativeStore"))){
			repProvider = new NativeStoreRepository();
		}
		else if (repType.equals(new LiteralImpl("SPARQLEndpoint"))){
			repProvider = new SPARQLProvider();
		}
		else if (repType.equals(new LiteralImpl("Memory"))){
			repProvider = new MemoryStoreProvider();
		}
		else {
			// try to load the provider class
			try {
				repProvider = (RepositoryProvider)Class.forName(repType.stringValue()).newInstance();				
			} catch (Exception e) {
				log.error("Error loading repository provider for " + repType.stringValue(), e);
				throw new RuntimeException("Repository type not supported: " + repType.stringValue());
			}
		}
			
		
		Repository rep = repProvider.load(graph, repNode);
		long datasetLoadDuration = System.currentTimeMillis()-datasetLoadStart;
		report.reportDatasetLoadTime(repProvider.getId(graph, repNode), repNode.stringValue(), repProvider.getLocation(graph, repNode), repType.stringValue(), datasetLoadDuration);
		
		return rep;
	}
}
