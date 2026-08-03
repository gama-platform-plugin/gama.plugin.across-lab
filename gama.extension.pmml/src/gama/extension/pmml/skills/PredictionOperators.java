package gama.extension.pmml.skills;

import static gama.api.utils.files.FileUtils.constructAbsoluteFilePath;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.EvaluatorUtil;
import org.jpmml.evaluator.LoadingModelEvaluatorBuilder;
import org.xml.sax.SAXException;

import gama.annotations.doc;
import gama.annotations.no_test;
import gama.annotations.operator;
import gama.api.GAMA;
import gama.api.exceptions.GamaRuntimeException;
import gama.api.gaml.types.Types;
import gama.api.runtime.scope.IScope;
import gama.api.types.map.GamaMapFactory;
import gama.api.types.map.IMap;
import gama.extension.pmml.types.PMMLEvaluator;
import jakarta.xml.bind.JAXBException;

public class PredictionOperators {
	
	
	
	@operator(value="load_eval_prediction",
			can_be_const = false)
	@doc ("Loads the PMML evaluator saved in the given file and returns it as an object of type unknown.")
	@no_test
	public static Evaluator loadEvaluator(final IScope scope,final String fileName) {
		
		try {
			var evaluatorFile = new File(constructAbsoluteFilePath(scope, fileName, true));
			return new LoadingModelEvaluatorBuilder().load(evaluatorFile).build();
		} catch (IOException | ParserConfigurationException | SAXException | JAXBException e) {
			e.printStackTrace();
			GAMA.reportAndThrowIfNeeded(scope, GamaRuntimeException.create(e, scope), false);
			return null;
		}
	}
	

	@operator(value="verify_evaluator",
			doc = @doc("Verifies the evaluator. Returns false if the evaluator hasn't been loaded yet or if the verification failed.")
			)
	public static Boolean verifyEvaluator(final IScope scope, final PMMLEvaluator evaluator) {
		try {
			var evalVerified = evaluator.verify();
			return evalVerified != null;				
		}
		catch (Exception ex) {
			GAMA.reportAndThrowIfNeeded(scope, GamaRuntimeException.create(ex, scope), false);
			return false;
		}
	}

	
	@operator(value="evaluate",
			doc = @doc("Evaluates a model given a set of parameters. Returns a map of the field names associated with their values. In case of error or if the evaluator doesn't exist, returns an empty map.")
			)
	public static IMap<String, ?> evaluateEvaluator(final IScope scope, final PMMLEvaluator evaluator, IMap arguments){
		
		if (arguments == null) {
			GAMA.reportAndThrowIfNeeded(scope, GamaRuntimeException.warning("Value for the `arguments` facet is nil or not a map", scope), false);
			return GamaMapFactory.create(Types.STRING, Types.NO_TYPE);
		}
		
		try {
			var jpmml_results = evaluator.evaluate(arguments);
			var results = EvaluatorUtil.decodeAll(jpmml_results);
			return GamaMapFactory.create(scope, Types.STRING, Types.NO_TYPE, results);			
		}
		catch (Exception ex) {
			GAMA.reportAndThrowIfNeeded(scope, GamaRuntimeException.create(ex, scope), false);
			return GamaMapFactory.create(Types.STRING, Types.NO_TYPE);
		}

		
	}

	

}
